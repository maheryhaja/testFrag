package fr.hdb.artibip.commun.constantes.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import org.androidannotations.annotations.EApplication;

import fr.hdb.artibip.commun.constantes.Constante;


@EApplication
public class DClicElecApplication extends Application {

    private int idIntervention = Constante.DEFAULT_INTERVENTION_ID;
    private String password = "";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public int getIdIntervention() {
        return idIntervention;
    }

    public void setIdIntervention(int idIntervention) {
        this.idIntervention = idIntervention;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void resetAll() {
        idIntervention = Constante.DEFAULT_INTERVENTION_ID;
        password = "";
    }
}
