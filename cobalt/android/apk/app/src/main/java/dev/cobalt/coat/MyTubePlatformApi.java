// Copyright 2026 MyTube by Luffy.
// Licensed under the Apache License, Version 2.0.
// See starboard/LICENSE for the license text.

package dev.cobalt.coat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Rational;
import android.view.Display;
import android.view.Surface;
import androidx.core.content.FileProvider;
import dev.cobalt.media.VideoSurfaceView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipFile;
import javax.net.ssl.HttpsURLConnection;

/**
 * MyTube-maintained Android platform operations used by the legacy H5VCC
 * compatibility bridge. This class deliberately has no TizenTube runtime or
 * service dependency.
 */
final class MyTubePlatformApi {
  private static final String TAG = "MyTubePlatformApi";
  private static final long MAX_APK_BYTES = 100L * 1024L * 1024L;
  private static final int MAX_REDIRECTS = 5;
  private static final ExecutorService INSTALL_EXECUTOR =
      Executors.newSingleThreadExecutor();
  private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

  private MyTubePlatformApi() {}

  static boolean installAppFromURL(Context context, String source) {
    if (context == null || !isHttpsUrl(source)) {
      return false;
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        && !context.getPackageManager().canRequestPackageInstalls()) {
      try {
        Intent settingsIntent = new Intent(
            Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
            Uri.parse("package:" + context.getPackageName()));
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(settingsIntent);
      } catch (Exception e) {
        android.util.Log.e(TAG, "Unable to open unknown-app installation settings", e);
      }
      return false;
    }

    INSTALL_EXECUTOR.execute(() -> downloadAndLaunch(context, source));
    return true; // The download was queued; Android installation still requires user approval.
  }

  private static boolean isHttpsUrl(String value) {
    if (value == null || value.trim().isEmpty()) {
      return false;
    }
    try {
      URI uri = URI.create(value);
      return "https".equalsIgnoreCase(uri.getScheme())
          && uri.getHost() != null
          && !uri.getHost().isEmpty()
          && uri.getUserInfo() == null;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  private static void downloadAndLaunch(Context context, String source) {
    File apkFile = null;
    try {
      apkFile = File.createTempFile("mytube-install-", ".apk", context.getFilesDir());
      downloadHttpsFile(source, apkFile);
      verifyApkContainer(apkFile);
      File completedFile = apkFile;
      MAIN_HANDLER.post(() -> launchInstaller(context, completedFile));
    } catch (Exception e) {
      if (apkFile != null) {
        //noinspection ResultOfMethodCallIgnored
        apkFile.delete();
      }
      android.util.Log.e(TAG, "MyTube app download or validation failed", e);
    }
  }

  private static void downloadHttpsFile(String source, File destination) throws IOException {
    URL current = new URL(source);
    for (int redirects = 0; redirects <= MAX_REDIRECTS; redirects++) {
      if (!isHttpsUrl(current.toString())) {
        throw new IOException("Only HTTPS download URLs are allowed");
      }

      HttpsURLConnection connection = (HttpsURLConnection) current.openConnection();
      connection.setConnectTimeout(15000);
      connection.setReadTimeout(20000);
      connection.setInstanceFollowRedirects(false);
      connection.setRequestMethod("GET");
      try {
        int status = connection.getResponseCode();
        if (status == HttpURLConnection.HTTP_MOVED_PERM
            || status == HttpURLConnection.HTTP_MOVED_TEMP
            || status == HttpURLConnection.HTTP_SEE_OTHER
            || status == 307
            || status == 308) {
          String location = connection.getHeaderField("Location");
          if (location == null || redirects == MAX_REDIRECTS) {
            throw new IOException("Invalid or excessive download redirects");
          }
          current = new URL(current, location);
          continue;
        }
        if (status != HttpURLConnection.HTTP_OK) {
          throw new IOException("Download server returned HTTP " + status);
        }

        int declaredLength = connection.getContentLength();
        if (declaredLength > MAX_APK_BYTES) {
          throw new IOException("APK exceeds the 100 MiB download limit");
        }

        long total = 0;
        try (InputStream input = connection.getInputStream();
             OutputStream output = new FileOutputStream(destination, false)) {
          byte[] buffer = new byte[8192];
          int count;
          while ((count = input.read(buffer)) != -1) {
            total += count;
            if (total > MAX_APK_BYTES) {
              throw new IOException("APK exceeds the 100 MiB download limit");
            }
            output.write(buffer, 0, count);
          }
          output.flush();
        }
        if (total == 0) {
          throw new IOException("The downloaded file is empty");
        }
        return;
      } finally {
        connection.disconnect();
      }
    }
    throw new IOException("Download redirect limit exceeded");
  }

  private static void verifyApkContainer(File apkFile) throws IOException {
    try (ZipFile zip = new ZipFile(apkFile)) {
      if (zip.getEntry("AndroidManifest.xml") == null) {
        throw new IOException("Downloaded file is not a valid APK container");
      }
    }
  }

  private static void launchInstaller(Context context, File apkFile) {
    try {
      Uri contentUri = FileProvider.getUriForFile(
          context, context.getPackageName() + ".fileprovider", apkFile);
      Intent installIntent = new Intent(Intent.ACTION_VIEW);
      installIntent.setDataAndType(contentUri, "application/vnd.android.package-archive");
      installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
          | Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(installIntent);
    } catch (Exception e) {
      android.util.Log.e(TAG, "Unable to launch Android package installer", e);
      //noinspection ResultOfMethodCallIgnored
      apkFile.delete();
    }
  }

  static String getVersion(Context context) {
    try {
      PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      return info.versionName == null ? "unknown" : info.versionName;
    } catch (PackageManager.NameNotFoundException e) {
      android.util.Log.e(TAG, "Unable to read MyTube package version", e);
      return "unknown";
    }
  }

  static String getBrandAndModel(Context context) {
    String deviceName = Settings.Global.getString(
        context.getContentResolver(), "device_name");
    if (deviceName != null && !deviceName.trim().isEmpty()) {
      return deviceName;
    }
    String manufacturer = Build.MANUFACTURER == null ? "" : Build.MANUFACTURER.trim();
    String model = Build.MODEL == null ? "" : Build.MODEL.trim();
    String combined = (manufacturer + " " + model).trim();
    return combined.isEmpty() ? "Android" : combined;
  }

  static String getArchitecture() {
    return Build.SUPPORTED_ABIS != null && Build.SUPPORTED_ABIS.length > 0
        ? Build.SUPPORTED_ABIS[0] : "unknown";
  }

  static void setFrameRate(Context context, Activity activity, float frameRate) {
    if (Build.VERSION.SDK_INT < 30 || activity == null
        || Float.isNaN(frameRate) || Float.isInfinite(frameRate) || frameRate < 0) {
      return;
    }

    Display display = activity.getWindowManager().getDefaultDisplay();
    DisplayManager displayManager =
        (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
    if (display == null || displayManager == null) {
      return;
    }

    int strategy = Surface.CHANGE_FRAME_RATE_ALWAYS;
    if (Build.VERSION.SDK_INT >= 31) {
      boolean seamless = false;
      for (float rate : display.getMode().getAlternativeRefreshRates()) {
        if (Math.abs(rate - frameRate) < 0.1f || isMultiple(rate, frameRate, 0.1f)) {
          seamless = true;
          break;
        }
      }

      int preference = displayManager.getMatchContentFrameRateUserPreference();
      if (preference == DisplayManager.MATCH_CONTENT_FRAMERATE_NEVER) {
        return;
      }
      if (preference == DisplayManager.MATCH_CONTENT_FRAMERATE_SEAMLESS_ONLY) {
        if (!seamless) {
          return;
        }
        strategy = Surface.CHANGE_FRAME_RATE_ONLY_IF_SEAMLESS;
      }
    }

    // Use the active video Surface directly so this platform helper does not
    // depend on the non-Starboard CobaltActivity implementation.
    Surface videoSurface = VideoSurfaceView.getCurrentSurface();
    if (videoSurface == null) {
      return;
    }
    // A zero frame rate clears the previous preference; preserve that reset path.
    if (frameRate == 0f) {
      videoSurface.setFrameRate(0f, Surface.FRAME_RATE_COMPATIBILITY_DEFAULT);
      return;
    }
    if (Build.VERSION.SDK_INT == 30) {
      videoSurface.setFrameRate(
          frameRate, Surface.FRAME_RATE_COMPATIBILITY_FIXED_SOURCE);
    } else {
      videoSurface.setFrameRate(
          frameRate, Surface.FRAME_RATE_COMPATIBILITY_FIXED_SOURCE, strategy);
    }
  }

  private static boolean isMultiple(float refreshRate, float fps, float tolerance) {
    if (fps <= 0) {
      return false;
    }
    float ratio = refreshRate / fps;
    return Math.abs(ratio - Math.round(ratio)) < (tolerance / fps);
  }

  static void enterPictureInPicture(Activity activity) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O || activity == null
        || activity.isFinishing() || activity.isDestroyed()) {
      return;
    }
    if (activity.getPackageManager().hasSystemFeature(
        PackageManager.FEATURE_PICTURE_IN_PICTURE)) {
      PictureInPictureParams params = new PictureInPictureParams.Builder()
          .setAspectRatio(new Rational(16, 9))
          .build();
      activity.enterPictureInPictureMode(params);
    }
  }

  static boolean hasSystemFeature(Context context, String featureName) {
    return context != null && featureName != null
        && context.getPackageManager().hasSystemFeature(featureName);
  }

  static void showAlertDialog(Activity activity, String title, String message) {
    if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
      return;
    }
    activity.runOnUiThread(() -> {
      if (!activity.isFinishing() && !activity.isDestroyed()) {
        new AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, null)
            .show();
      }
    });
  }
}
