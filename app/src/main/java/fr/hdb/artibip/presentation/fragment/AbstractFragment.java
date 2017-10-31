package fr.hdb.artibip.presentation.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.ws.post.resumerinfoclient.ResumerInfoClientPostDto;
import fr.hdb.artibip.presentation.activity.AccueilActivity;
import fr.hdb.artibip.presentation.activity.GenericActivity;
import fr.hdb.artibip.presentation.activity.MainActivity;
import fr.hdb.artibip.utils.keyboard.KeyboardManager;

import static fr.hdb.artibip.presentation.activity.GenericActivity.getCurrentActivity;

/**
 * Created by lovasoa_arnaud on 11/07/2017.
 */
public class AbstractFragment extends Fragment {

    public static Boolean isMain = false;

    /**
     * remplace le fragment actuel par un nouveau fragment
     *
     * @param id             : id de la vue sur laquelle le nouveau fragment doit etre mis
     * @param fragment       : le nouveau fragment a mettre
     * @param animated       : true si on doit animer le remplacement, false sinon
     * @param addToBackStack : true si on doit permettre le retour au fragment precedent, false sinon
     */

    protected void replaceFragment(int id, Fragment fragment, boolean animated, boolean addToBackStack) {
        if(fragment == null) return;
        try {
            FragmentTransaction transaction = getCurrentActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(id, fragment);
            if (addToBackStack) {
                transaction.addToBackStack(null);
            }
            transaction.commitAllowingStateLoss();
        } catch (NullPointerException e) {
            return;
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
        try {
            FragmentTransaction transaction = getCurrentActivity().getSupportFragmentManager().beginTransaction();
            if (animated) {
                transaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.stay, R.anim.stay, R.anim.slide_out_bottom);
            }
            transaction.add(id, fragment);
            if (addToBackStack) {
                transaction.addToBackStack(null);
            }
            transaction.commitAllowingStateLoss();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * retirer de l'ecran ce fragment
     *
     * @param animated : true si on doit animer la suppression du fragment, false sinon
     */
    protected void pop(boolean animated) {
        try {
            FragmentManager manager = getCurrentActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if (animated) {
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
            }
            transaction.remove(this);
            transaction.commitAllowingStateLoss();
            manager.popBackStack();
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * afficher le loader
     *
     */
    protected void showLoader() {
        ((GenericActivity) getActivity()).showLoader();
    }

    /**
     * Stopper le loader
     *
     */
    protected void hideLoader(){
        if(getActivity() != null)
            ((GenericActivity) getActivity()).hideLoader();
    }

    protected void showErrorDialog(String title, String message){
        if(getActivity() != null)
            ((GenericActivity) getActivity()).showErrorDialog(title, message);
    }

    protected void showSuccesDialog(String title, String message, View.OnClickListener action){
        if(getActivity() != null)
            ((GenericActivity) getActivity()).showSuccesDialog(title, message,action);
    }

    protected void getCgv() {
        if(getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getCgv();
        } else {
            ((AccueilActivity) getActivity()).getCgv();
        }
    }

    /**
     * Afficher ou non le header
     * @param visibility : true si on doit afficher le header, false sinon
     * @param buttonVisibility : true si on doit afficher le bouton, false sinon
     */
    protected void setHeaderVisibility(boolean visibility, boolean buttonVisibility){
        if(getActivity() == null){
            return;
        }
        LinearLayout mainHeader = (LinearLayout) getActivity().findViewById(R.id.layout_main_header);
        if(mainHeader == null){
            return;
        }
        if(visibility){
            mainHeader.setVisibility(View.VISIBLE);
        }else
            mainHeader.setVisibility(View.GONE);

        setMainButtonIcon(buttonVisibility);
    }

    protected void setHeaderVisibility(boolean visibility){
        if(getActivity() == null){
            return;
        }
        LinearLayout mainHeader = (LinearLayout) getActivity().findViewById(R.id.layout_main_header);
        if(mainHeader == null){
            return;
        }
        if(visibility){
            mainHeader.setVisibility(View.VISIBLE);
        }else
            mainHeader.setVisibility(View.GONE);
    }

    /**
     * Afficher ou non le footer
     * @param visibility : true si on doit afficher le header, false sinon
     */
    protected void setFooterVisibility(boolean visibility){
        if(getActivity() == null){
            return;
        }
        RelativeLayout mainFooter = (RelativeLayout) getActivity().findViewById(R.id.layout_main_footer);
        if(mainFooter == null){
            return;
        }
        if(visibility)
            mainFooter.setVisibility(View.VISIBLE);
        else
            mainFooter.setVisibility(View.GONE);
    }

    /**
     * Afficher ou non le toolbar
     * @param visibility : true si on doit afficher le toolbar, false sinon
     * @param toolbarTitle : le texte dans le toolbar
     * @param logoHeaederVisibility : true si on doit afficher le logo dans le toolbar
     */
    protected void setToolbarVisibility(boolean visibility,String toolbarTitle, boolean logoHeaederVisibility){
        if(getActivity() == null){
            return;
        }
        LinearLayout mainToolbar = (LinearLayout) getActivity().findViewById(R.id.toolbar);
        if(mainToolbar == null){
            return;
        }
        if(!visibility){
            mainToolbar.setVisibility(View.INVISIBLE);
        } else {
            TextView textTitle = (TextView) getActivity().findViewById(R.id.text_toolbar_title);
            textTitle.setText(toolbarTitle);
            setLogoToolbarVisibility(logoHeaederVisibility);
            mainToolbar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Afficher ou non le logo dans le toolbar
     * @param visibility : true si on doit afficher le logo dans le toolbar, false sinon
     */
    protected void setLogoToolbarVisibility(boolean visibility){
        if(getActivity() == null){
            return;
        }
        ImageView logoHeader = (ImageView) getActivity().findViewById(R.id.image_toolbar_logo_header);
        if(logoHeader == null){
            return;
        }
        if(visibility)
            logoHeader.setVisibility(View.VISIBLE);
        else
            logoHeader.setVisibility(View.GONE);
    }

    /**
     * Afficher ou non le bouton back
     *
     * @param visibility : true si on doit afficher le bouton, false sinon
     */
    protected void setMainButtonIcon(boolean visibility){
        if(getActivity() == null){
            return;
        }
        KeyboardManager.hideSoftKeyboard(getActivity());
        if(visibility) {
            setBackIcon();
        }else {
            setMenuIcon();
        }
    }

    protected void setMenuIcon(){
        if(getActivity() == null){
            return;
        }
        isMain = true;
        ImageView menuButton = (ImageView) getActivity().findViewById(R.id.button_main_menu);
        menuButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.bt_menu));
        setUpMenuButton();
    }


    /*
    * Clique sur le bouton main (retour) dans le header
    */
    protected void setBackIcon(){
        if(getActivity() == null){
            return;
        }
        isMain = false;
        ImageView menuButton = (ImageView) getActivity().findViewById(R.id.button_main_menu);
        menuButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.bt_back));
        setUpMenuButton();
    }

    protected void setUpMenuButton(){
        try{
            final RelativeLayout menuButton = (RelativeLayout) getActivity().findViewById(R.id.main_toolbar_button_main_menu);
            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isMain){
                        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                        drawerLayout.openDrawer(GravityCompat.START);
                    } else {
                        (getActivity()).onBackPressed();
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    protected void setResumerInfoClientPostDto(ResumerInfoClientPostDto resumerInfoClientPostDto) {
        ((AccueilActivity)getActivity()).setResumerInfoClientPostDto(resumerInfoClientPostDto);
    }

    protected ResumerInfoClientPostDto getResumerInfoClientPostDto() {
        return  ((AccueilActivity)getActivity()).getResumerInfoClientPostDto();
    }

}
