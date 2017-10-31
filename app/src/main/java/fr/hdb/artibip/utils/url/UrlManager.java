package fr.hdb.artibip.utils.url;

import android.content.Context;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;


@EBean(scope = EBean.Scope.Singleton)
public class UrlManager {

    @RootContext
    protected Context mApplicationContext;

    /**
     *
     * @param id resource id
     * @return string resource
     * */
    private String getString(int id) {
        return mApplicationContext.getString(id);
    }



}
