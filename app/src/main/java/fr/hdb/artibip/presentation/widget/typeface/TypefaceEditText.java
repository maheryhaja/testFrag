package fr.hdb.artibip.presentation.widget.typeface;


import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import fr.hdb.artibip.R;

public class TypefaceEditText extends EditText {

    /*
     * Caches typefaces based on their file path and name, so that they don't
     * have to be created every time when they are referenced.
     */
    private static Map<String, Typeface> mTypefaces;

    public TypefaceEditText(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }
        setTypefaceFont(context, attrs);
    }

    public TypefaceEditText(final Context context, final AttributeSet attrs,
                            final int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) {
            return;
        }
        setTypefaceFont(context, attrs);
    }

    private void setTypefaceFont(Context context, AttributeSet attrs) {
        if (mTypefaces == null) {
            mTypefaces = new HashMap<String, Typeface>();
        }
        final TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.TypefaceEditText);
        if (array != null) {
            final String typefaceAssetPath = array
                    .getString(R.styleable.TypefaceEditText_customEditTypeface);

            if (typefaceAssetPath != null) {
                Typeface typeface = null;

                if (mTypefaces.containsKey(typefaceAssetPath)) {
                    typeface = mTypefaces.get(typefaceAssetPath);
                } else {
                    AssetManager assets = context.getAssets();
                    typeface = Typeface.createFromAsset(assets,
                            typefaceAssetPath);
                    mTypefaces.put(typefaceAssetPath, typeface);
                }

                setTypeface(typeface);
            }
            array.recycle();
        }
    }
}


