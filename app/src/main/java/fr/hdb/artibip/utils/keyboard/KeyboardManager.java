package fr.hdb.artibip.utils.keyboard;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;


public class KeyboardManager {

    public static void hideSoftKeyboard(Activity activity) {

        if (activity != null && activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
