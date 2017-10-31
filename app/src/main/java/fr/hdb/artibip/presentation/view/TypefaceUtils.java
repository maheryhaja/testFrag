package fr.hdb.artibip.presentation.view;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class TypefaceUtils {

    public static void overrideFont(Context context) {
        try {
            final Typeface customFontTypeface;
            customFontTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Regular.ttf");
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
                Map<String, Typeface> newMap = new HashMap<String, Typeface>();
                newMap.put("sans-serif", customFontTypeface);
                try {
                    final Field staticField = Typeface.class.getDeclaredField("sSystemFontMap");
                    staticField.setAccessible(true);
                    staticField.set(null, newMap);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    final Field staticField = Typeface.class.getDeclaredField("SERIF");
                    staticField.setAccessible(true);
                    staticField.set(null, customFontTypeface);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                    Log.e("FONT UNLOAD", "NoSuchFieldException");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    Log.e("FONT UNLOAD", "IllegalAccessException");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}