package com.gabbar925.photoeditios.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.gabbar925.photoeditios.editor.featuresfoto.puzzle.Callback;
import com.gabbar925.photoeditios.editor.featuresfoto.puzzle.PuzzleView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtils {
    private static final String TAG = "FileUtils";

    public static String getFolderName(String str) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), str);
        if (file.exists() || file.mkdirs()) {
            return file.getAbsolutePath();
        }
        return "";
    }

    private static boolean isSDAvailable() {
        return Environment.getExternalStorageState().equals("mounted");
    }


    public static File saveBitmapAsFile(Bitmap bitmap) {
        FileOutputStream fileOutputStream;
        String file = Environment.getExternalStorageDirectory().toString();
        File file2 = new File(file + "/PhotoEditor");
        if (!file2.exists()) {
            file2.mkdirs();
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            File file3 = new File(file + "/PhotoEditor/" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date()) + ".jpg");
            file3.createNewFile();
            fileOutputStream = new FileOutputStream(file3);
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                try {
                    fileOutputStream.close();
                } catch (IOException unused) {
                }
                return file3;
            } catch (Exception e) {
                e = e;
                try {
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException unused2) {
                        }
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream2 = fileOutputStream;
                    if (fileOutputStream2 != null) {
                    }
                    throw th;
                }
            }
        } catch (Exception e2) {

            fileOutputStream = null;

            if (fileOutputStream != null) {
            }
            return null;
        } catch (Throwable th2) {

            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException unused3) {
                }
            }
            return null;
        }
    }

    public static File getNewFile(Context context, String str) {
        String str2;
        String format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        if (isSDAvailable()) {
            str2 = getFolderName(str) + File.separator + format + ".jpg";
        } else {
            str2 = context.getFilesDir().getPath() + File.separator + format + ".jpg";
        }
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        return new File(str2);
    }

    public static Bitmap createBitmap(PuzzleView puzzleView, int i) {
        puzzleView.clearHandling();
        puzzleView.invalidate();
        Bitmap createBitmap = Bitmap.createBitmap(i, (int) (((float) i) / (((float) puzzleView.getWidth()) / ((float) puzzleView.getHeight()))), Bitmap.Config.ARGB_8888);
        puzzleView.draw(new Canvas(createBitmap));
        return createBitmap;
    }

    public static Bitmap createBitmap(PuzzleView puzzleView) {
        puzzleView.clearHandling();
        puzzleView.invalidate();
        Bitmap createBitmap = Bitmap.createBitmap(puzzleView.getWidth(), puzzleView.getHeight(), Bitmap.Config.ARGB_8888);
        puzzleView.draw(new Canvas(createBitmap));
        return createBitmap;
    }


    public static void savePuzzle(PuzzleView puzzleView, File file, int i, Callback callback) throws FileNotFoundException {
        FileOutputStream fileOutputStream;
        Bitmap bitmap;
        Bitmap bitmap2 = null;
        try {
            bitmap = createBitmap(puzzleView);
            try {
                fileOutputStream = new FileOutputStream(file);
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, i, fileOutputStream);
                    if (!file.exists()) {
                        Log.e(TAG, "notifySystemGallery: the file do not exist.");
                        if (bitmap != null) {
                            bitmap.recycle();
                        }
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        MediaStore.Images.Media.insertImage(puzzleView.getContext().getContentResolver(), file.getAbsolutePath(), file.getName(), (String) null);
                        puzzleView.getContext().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
                        if (callback != null) {
                            callback.onSuccess();
                        }
                        if (bitmap != null) {
                            bitmap.recycle();
                        }
                        try {
                            fileOutputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                } catch (FileNotFoundException e3) {

                    bitmap2 = bitmap;
                    try {

                        if (callback != null) {
                        }
                        if (bitmap2 != null) {
                        }
                        if (fileOutputStream != null) {
                        }
                    } catch (Throwable th) {
                        th = th;
                        bitmap = bitmap2;
                        if (bitmap != null) {
                        }
                        if (fileOutputStream != null) {
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                }
            } catch (FileNotFoundException e4) {

                fileOutputStream = null;
                bitmap2 = bitmap;

                if (callback != null) {
                }
                if (bitmap2 != null) {
                }
                if (fileOutputStream != null) {
                }
            } catch (Throwable th3) {

                fileOutputStream = null;
                if (bitmap != null) {
                }
                if (fileOutputStream != null) {
                }

            }
        } catch (Throwable th4) {

            bitmap = null;
            fileOutputStream = null;
            if (bitmap != null) {
            }
            if (fileOutputStream != null) {
            }

        }
    }

    public static Bitmap getResizedBitmap(Bitmap bitmap, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        float width = ((float) i) / ((float) bitmap.getWidth());
        float height = ((float) i2) / ((float) bitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.setScale(width, height, 0.0f, 0.0f);
        Canvas canvas = new Canvas(createBitmap);
        canvas.setMatrix(matrix);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, new Paint(2));
        bitmap.recycle();
        return createBitmap;
    }
}
