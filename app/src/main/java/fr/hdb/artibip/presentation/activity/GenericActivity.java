package fr.hdb.artibip.presentation.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import fr.hdb.artibip.R;
import fr.hdb.artibip.commun.constantes.Constante;
import fr.hdb.artibip.commun.constantes.application.DClicElecApplication;
import fr.hdb.artibip.donnee.dto.constantes.menu.Menu;
import fr.hdb.artibip.donnee.dto.event.EventSetListEtabDto;
import fr.hdb.artibip.donnee.dto.event.EventSetMenuDto;
import fr.hdb.artibip.presentation.widget.dialog.DialogManager;
import fr.hdb.artibip.utils.url.EnvironmentManager;

public class GenericActivity extends FragmentActivity {

    protected static List<GenericActivity> activities = new ArrayList<>();

    /**
     * retourne l'activity qui est actuellement à l'écran
     *
     * @return
     */
    public static GenericActivity getCurrentActivity() {
        if(activities.size()>0){
            GenericActivity fragmentActivity = activities.get(activities.size() - 1);
            if(fragmentActivity != null) {
                return fragmentActivity;
            }
            return null;
        }
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        activities.add(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onStop() {
        super.onStop();
        activities.remove(this);
    }

    /**
     * Gestion orientation selon de device
     */
    protected void setScreenOrientation() {
        if(getCurrentActivity() == null){
            return;
        }
        if (EnvironmentManager.isTablet(getCurrentActivity())) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
        else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
    }

    /**
     * afficher un nouvel activity
     *
     * @param activity : la classe de l'activity
     * @param animated : inclure une animation de transition ou pas
     */
    protected void showActivity(Class<?> activity, boolean animated) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        if (animated) {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    /**
     * remplace le fragment actuellement par un nouveau fragment
     *
     * @param id             : id de la vue sur laquelle le nouveau fragment doit etre mis
     * @param fragment       : le nouveau fragment a mettre
     * @param animated       : true si on doit animer le remplacement, false sinon
     * @param addToBackStack : true si on doit permettre le retour au fragment precedent, false sinon
     */
    protected void replaceFragment(int id, Fragment fragment, boolean animated, boolean addToBackStack) {
        if(fragment == null) return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        try{
            transaction.commitAllowingStateLoss();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * ajout d'un fragment par dessus celui qui est visible a l'ecran actuellement
     *
     * @param id             : id de la vue sur laquelle le nouveau fragment doit etre mis
     * @param fragment       : le nouveau fragment a mettre
     * @param animated       : true si on doit animer le remplacement, false sinon
     * @param addToBackStack : true si on doit permettre le retour au fragment precedent, false sinon
     */
    protected void addFragment(int id, Fragment fragment, boolean animated, boolean addToBackStack) {
        if(fragment == null) return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(id, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        try{
            transaction.commitAllowingStateLoss();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Afficher un Toast
     */
    void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void popBackStackFragment(int number) {
        if (getSupportFragmentManager() != null
                && getBackStackEntryCount() > number) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    public void clearBackStack() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public int getBackStackEntryCount() {
        return getSupportFragmentManager().getBackStackEntryCount();
    }

    protected DClicElecApplication getDClicElecApplication() {
        return (DClicElecApplication) getApplication();
    }

    protected int getIdIntervention() {
        return getDClicElecApplication().getIdIntervention();
    }

    protected void setIdIntervention(int idIntervention) {
        getDClicElecApplication().setIdIntervention(idIntervention);
    }

    protected String getPassword() {
        return getDClicElecApplication().getPassword();
    }

    protected void setPassword(String password) {
        getDClicElecApplication().setPassword(password);
    }

    protected void reset() {
        getDClicElecApplication().resetAll();
    }

    /** all dialogs */
    public void showMenuDialog(final String title, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogManager.showMessageMenu(GenericActivity.this, title, message, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new EventSetMenuDto(0, Menu.DEMANDES));
                        DialogManager.getCustomDialog().dismiss();
                    }
                });
            }
        });
    }

    public void showEtabDialog(final String title, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogManager.showEtabMenu(GenericActivity.this, title, message, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new EventSetListEtabDto());
                        DialogManager.getCustomDialog().dismiss();
                    }
                });
            }
        });
    }

    public void showErrorDialog(final String title, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogManager.showMessageCustomOk(GenericActivity.this, title, message, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        DialogManager.getCustomDialog().dismiss();
                    }
                });
            }
        });
    }

    /** ok dialogs */
    public void showSuccesDialog(final String title, final String message, final View.OnClickListener action) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogManager.showMessageCustomOk(GenericActivity.this, title, message, action);
            }
        }, Constante.DEFAULT_MILLISECOND_WAIT);
    }

    public void hideDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogManager.getCustomDialog().dismiss();
            }
        });
    }

    public void showLoader() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogManager.showLoader(GenericActivity.this);
            }
        }, Constante.DEFAULT_MILLISECOND_WAIT);
    }

    public void hideLoader() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogManager.getCustomDialog().dismiss();
            }
        });
    }

    public void showImageDialog(final int position, final Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogManager.showImageFullScreen(GenericActivity.this, position, bitmap);
            }
        });
    }
    public void showImageDialogFacture(final int position, final Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogManager.showImageFullScreenFacture(GenericActivity.this, position, bitmap);
            }
        });
    }

}
