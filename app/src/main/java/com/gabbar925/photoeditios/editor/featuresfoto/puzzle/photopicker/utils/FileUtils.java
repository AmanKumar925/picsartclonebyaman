package com.gabbar925.photoeditios.editor.featuresfoto.puzzle.photopicker.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.util.Comparator;

public class FileUtils {
    private static final boolean DEBUG = false;
    public static final String HIDDEN_PREFIX = ".";
    public static final String MIME_TYPE_APP = "application/*";
    public static final String MIME_TYPE_AUDIO = "audio/*";
    public static final String MIME_TYPE_IMAGE = "image/*";
    public static final String MIME_TYPE_TEXT = "text/*";
    public static final String MIME_TYPE_VIDEO = "video/*";
    public static final String PARAMETER_SHARE_DIALOG_CONTENT_VIDEO = "video";
    static final String TAG = "FileUtils";
    public static final String WEB_DIALOG_PARAM_MEDIA = "media";
    public static Comparator<File> sComparator = new C07681();
    public static FileFilter sDirFilter = new C07703();
    public static FileFilter sFileFilter = new C07692();

    static class C07681 implements Comparator<File> {
        C07681() {
        }

        public int compare(File file, File file2) {
            return file.getName().toLowerCase().compareTo(file2.getName().toLowerCase());
        }
    }

    static class C07692 implements FileFilter {
        C07692() {
        }

        public boolean accept(File file) {
            return file.isFile() && !file.getName().startsWith(FileUtils.HIDDEN_PREFIX);
        }
    }

    static class C07703 implements FileFilter {
        C07703() {
        }

        public boolean accept(File file) {
            return file.isDirectory() && !file.getName().startsWith(FileUtils.HIDDEN_PREFIX);
        }
    }

    private FileUtils() {
    }

    public static String getExtension(String str) {
        if (str == null) {
            return null;
        }
        int lastIndexOf = str.lastIndexOf(HIDDEN_PREFIX);
        return lastIndexOf >= 0 ? str.substring(lastIndexOf) : "";
    }

    public static boolean isLocal(String str) {
        return str != null && !str.startsWith("http://") && !str.startsWith("https://");
    }

    public static boolean isMediaUri(Uri uri) {
        return WEB_DIALOG_PARAM_MEDIA.equalsIgnoreCase(uri.getAuthority());
    }

    public static Uri getUri(File file) {
        if (file != null) {
            return Uri.fromFile(file);
        }
        return null;
    }

    public static File getPathWithoutFilename(File file) {
        if (file == null) {
            return null;
        }
        if (file.isDirectory()) {
            return file;
        }
        String name = file.getName();
        String absolutePath = file.getAbsolutePath();
        String substring = absolutePath.substring(0, absolutePath.length() - name.length());
        if (substring.endsWith("/")) {
            substring = substring.substring(0, substring.length() - 1);
        }
        return new File(substring);
    }

    public static String getMimeType(File file) {
        String extension = getExtension(file.getName());
        if (extension != null) {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1));
        }
        return null;
    }

    public static String getMimeType(Context context, Uri uri) {
        Log.d("tag", "mimetype:" + uri);
        return getMimeType(new File(getPath(context, uri)));
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    public static String getDataColumn(Context context, Uri uri, String str, String[] strArr) {
        Cursor cursor;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, (String) null);
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                        if (cursor != null) {
                            cursor.close();
                        }
                        return string;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                    }
                    throw th;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th2) {

            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
    }


    public static String getNameColumn(Context context, Uri uri, String str, String[] strArr) {
        Cursor cursor;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{"_display_name"}, str, strArr, (String) null);
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        String string = cursor.getString(cursor.getColumnIndexOrThrow("_display_name"));
                        if (cursor != null) {
                            cursor.close();
                        }
                        return string;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                    }
                    throw th;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th2) {

            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
    }

    public static String getPath(Context context, Uri uri) {
        Uri uri2;
        boolean z = Build.VERSION.SDK_INT >= 19;
        if (Build.VERSION.SDK_INT < 19) {
            return null;
        }
        if (!z || !DocumentsContract.isDocumentUri(context, uri)) {
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                if (isGooglePhotosUri(uri)) {
                    return uri.getLastPathSegment();
                }
                return getDataColumn(context, uri, (String) null, (String[]) null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            } else {
                return null;
            }
        } else if (isExternalStorageDocument(uri)) {
            String[] split = DocumentsContract.getDocumentId(uri).split(":");
            if (!"primary".equalsIgnoreCase(split[0])) {
                return null;
            }
            return Environment.getExternalStorageDirectory() + "/" + split[1];
        } else if (isDownloadsDocument(uri)) {
            return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), (String) null, (String[]) null);
        } else {
            if (!isMediaDocument(uri)) {
                return null;
            }
            String str = DocumentsContract.getDocumentId(uri).split(":")[0];
            if ("image".equals(str)) {
                uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if (PARAMETER_SHARE_DIALOG_CONTENT_VIDEO.equals(str)) {
                uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else {
                uri2 = "audio".equals(str) ? MediaStore.Audio.Media.EXTERNAL_CONTENT_URI : null;
            }
            return getDataColumn(context, uri2, "_id=?", new String[]{});
        }
    }

    public static File getFile(Context context, Uri uri) {
        String path;
        if (uri == null || (path = getPath(context, uri)) == null || !isLocal(path)) {
            return null;
        }
        return new File(path);
    }

    public static String getReadableFileSize(int i) {
        float f;
        DecimalFormat decimalFormat = new DecimalFormat("###.#");
        String str = " KB";
        if (i > 1024) {
            f = (float) (i / 1024);
            if (f > 1024.0f) {
                f /= 1024.0f;
                if (f > 1024.0f) {
                    f /= 1024.0f;
                    str = " GB";
                } else {
                    str = " MB";
                }
            }
        } else {
            f = 0.0f;
        }
        return String.valueOf(decimalFormat.format((double) f) + str);
    }

    public static Bitmap getThumbnail(Context context, File file) {
        return getThumbnail(context, getUri(file), getMimeType(file));
    }

    public static Bitmap getThumbnail(Context context, Uri uri) {
        return getThumbnail(context, uri, getMimeType(context, uri));
    }


    //TODO
    public static Bitmap getThumbnail(Context context, Uri uri, String str) {
        Cursor cursor;
        Bitmap thumbnail = null;
        Bitmap bitmap = null;
        if (!isMediaUri(uri)) {
            Log.e(TAG, "You can only retrieve thumbnails for images and videos.");
        } else if (uri != null) {
            ContentResolver contentResolver = context.getContentResolver();
            try {
                cursor = contentResolver.query(uri, (String[]) null, (String) null, (String[]) null, (String) null);
                try {
                    if (cursor.moveToFirst()) {
                        int i = cursor.getInt(0);
                        if (str.contains(PARAMETER_SHARE_DIALOG_CONTENT_VIDEO)) {
                            thumbnail = MediaStore.Video.Thumbnails.getThumbnail(contentResolver, (long) i, 1, (BitmapFactory.Options) null);
                        } else if (str.contains(MIME_TYPE_IMAGE)) {
                            thumbnail = MediaStore.Images.Thumbnails.getThumbnail(contentResolver, (long) i, 1, (BitmapFactory.Options) null);
                        }
                        bitmap = thumbnail;
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (Exception unused) {
                    if (cursor != null) {
                    }
                    return bitmap;
                } catch (Throwable unused2) {
                    if (cursor != null) {
                    }
                    return bitmap;
                }
            } catch (Exception unused3) {
                cursor = null;
                if (cursor != null) {
                    cursor.close();
                }
                return bitmap;
            } catch (Throwable unused4) {
                cursor = null;
                if (cursor != null) {
                    cursor.close();
                }
                return bitmap;
            }
        }
        return bitmap;
    }

    public static Intent createGetContentIntent() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("*/*");
        intent.addCategory("android.intent.category.OPENABLE");
        return intent;
    }
}
