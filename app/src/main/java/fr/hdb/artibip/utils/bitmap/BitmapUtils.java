package fr.hdb.artibip.utils.bitmap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import fr.hdb.artibip.utils.asynctask.AsyncHelper;


public class BitmapUtils {

    static final String TAG = "ImageBitmapUtils";

    float getBitmapScalingFactor(Activity activity, Bitmap bm) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        return ((float) screenWidth / (float) bm.getWidth());
    }

    public static Bitmap ScaleBitmap(Bitmap bm, float scalingFactor) {
        int scaleHeight = (int) (bm.getHeight() * scalingFactor);
        int scaleWidth = (int) (bm.getWidth() * scalingFactor);
        return Bitmap.createScaledBitmap(bm, scaleWidth, scaleHeight, true);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveBitmapToFile(Context context, Bitmap b, String picName) {
        FileOutputStream fos;
        try {
            String name = String.valueOf(picName.hashCode());
            fos = context.openFileOutput(name, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap loadBitmap(Context context, final File picName) {
        Bitmap b = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        try {
            b = BitmapFactory.decodeFile(picName.getAbsolutePath(), options);
        } catch (Exception e) {
            b = null;
        }
        return b;
    }

    public static Bitmap loadNotifBitmap(Context context, String picName) {
        Bitmap b = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        b = BitmapFactory.decodeFile(picName, options);
        return b;
    }

    public static void saveBitmapLocally(final Context activity, final Bitmap bm, final String name) {
        new AsyncHelper<String>() {

            @Override
            protected String background() throws Exception {
                saveBitmapToFile(activity, bm, name);
                return String.valueOf(name.hashCode());
            }

            @Override
            protected void success(String result) {
                Toast.makeText(activity, "pic saved successfully : " + result, Toast.LENGTH_SHORT).show();
            }
        }.launch(activity);
    }
}
