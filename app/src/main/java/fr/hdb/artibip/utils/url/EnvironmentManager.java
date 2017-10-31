package fr.hdb.artibip.utils.url;

import android.content.Context;
import fr.hdb.artibip.R;


public class EnvironmentManager {

    /**
     * tester if production
     *
     * @param context
     * @return true si c'est un environnement de production
     */
    public static boolean isProd(Context context) {
        return context.getResources().getBoolean(R.bool.is_prod);
    }

    public static boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.is_tablette);
    }
}