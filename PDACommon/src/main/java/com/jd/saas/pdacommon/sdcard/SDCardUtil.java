package com.jd.saas.pdacommon.sdcard;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.jd.saas.pdacommon.application.MyApplication;
import com.jd.saas.pdacommon.dir.DirConfig;
import com.jd.saas.pdacommon.log.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * sd卡操作
 *
 * @author majiheng
 */
public class SDCardUtil {

    private static final String TAG = "SDCardUtil";

    public static boolean isExistSdCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static String mkdirs(String dir) {
        File file = mkdirsFile(dir);
        if (!file.exists()) {
            if (file.mkdirs()) {
                return file.getAbsolutePath();
            }
        }
        return file.getAbsolutePath();
    }

    public static File mkdirsFile(String dir) {
        String path = getParentDir() + File.separator + dir;
        return new File(path);
    }

    private static String createDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return "";
            }
        }
        return dir;
    }

    public static String getParentDir() {
        return getSDCardBaseDir() + File.separator + DirConfig.projectRoot;
    }

    public static String getCachePath(String dir) {
        File cacheDir = MyApplication.getInstance().getExternalCacheDir();
        if (null != cacheDir) {
            return createDir(cacheDir.getAbsolutePath() + File.separator + dir);
        }
        return "";
    }

    public static String getFilesPath(String dir) {
        File cacheDir = MyApplication.getInstance().getExternalFilesDir(null);
        if (null != cacheDir) {
            return createDir(cacheDir.getAbsolutePath() + File.separator + dir);
        }
        return "";
    }

    public static void assets2SD(Context context, String path, String name) {
        File file = new File(path, name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Logger.e(TAG, "file create fail");
            }
        } else {
            return;
        }
        InputStream is = null;
        OutputStream os = null;
        try {
            is = context.getAssets().open(name);
            os = new FileOutputStream(file);
            byte[] bytes = new byte[2048];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            Logger.e(TAG, "copy fail");
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                Logger.e(TAG, "file operation fail");
            }
        }
    }

    private static String getSDCardBaseDir() {
        if (isExistSdCard()) {
            return Environment.getExternalStorageDirectory().getPath();
        }
        return null;
    }

    public static String saveFileToCustomDir(byte[] data, String dir, String fileName) {
        BufferedOutputStream bos = null;
        try {
            File file = new File(getFilesPath(dir), fileName);
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(data);
            bos.flush();
            return file.getAbsolutePath();
        } catch (Exception e) {
            Logger.e(TAG, "file operation fail");
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                Logger.e(TAG, "file operation fail");
            }
        }
        return null;
    }


    public static byte[] loadFileFromSDCard(String fileDir) {
        BufferedInputStream bis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            bis = new BufferedInputStream(
                    new FileInputStream(new File(fileDir)));
            byte[] buffer = new byte[8 * 1024];
            int c;
            while ((c = bis.read(buffer)) != -1) {
                baos.write(buffer, 0, c);
                baos.flush();
            }
            return baos.toByteArray();
        } catch (Exception e) {
            Logger.e(TAG, "file operation fail");
        } finally {
            try {
                baos.close();
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                Logger.e(TAG, "file operation fail");
            }
        }
        return null;
    }

    public static File getCacheMovieDir(Context context) {
        String dir = Environment.DIRECTORY_MOVIES;
        return new File(context.getExternalCacheDir(), dir);
    }
}