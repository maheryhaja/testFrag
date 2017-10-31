package fr.hdb.artibip.presentation.fragment.menu;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import java.util.ArrayList;
import java.util.List;
import de.greenrobot.event.EventBus;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.constantes.menu.Menu;
import fr.hdb.artibip.donnee.dto.constantes.popup.PopUpType;
import fr.hdb.artibip.donnee.dto.constantes.user.UserType;
import fr.hdb.artibip.donnee.dto.event.EventSetMenuDto;
import fr.hdb.artibip.presentation.activity.MainActivity;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.fragment.client.BesoinFragment_;
import fr.hdb.artibip.presentation.fragment.client.etablissementliste.EtablissementListFragment_;
import fr.hdb.artibip.presentation.fragment.user.ModificationInfoGeneraleFragment_;
import fr.hdb.artibip.presentation.fragment.user.passwordinfo.PasswordChangeFragment_;
import fr.hdb.artibip.presentation.view.DrawerAdapter;
import fr.hdb.artibip.presentation.view.NavDrawerItem;
import fr.hdb.artibip.service.applicatif.artisan.listedemande.ArtisanDemandeSA;
import fr.hdb.artibip.service.applicatif.artisan.listedemande.ArtisanDemandeSAImpl;
import fr.hdb.artibip.service.applicatif.client.listDemande.ClientDemandeSA;
import fr.hdb.artibip.service.applicatif.client.listDemande.ClientDemandeSAImpl;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.utils.network.NetworkManager;


@EFragment(R.layout.fragment_drawer)
public class DrawerFragment extends GenericFragment {

    private static String[] titlesItem = null;
    private String role;
    public static DrawerFragment myInstance;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferences;

    @ViewById(R.id.nav_header_role)
    TextView userRole;

    @ViewById(R.id.nav_header_email)
    TextView userEmail;

    @ViewById(R.id.list_menu)
    ListView menuList;

    private boolean isSms;
    private String password;

    @Bean(ClientDemandeSAImpl.class)
    protected ClientDemandeSA clientDemandeSA;

    @Bean(ArtisanDemandeSAImpl.class)
    protected ArtisanDemandeSA artisanDemandeSA;

    // Keep all menu icon in array
    Integer[] mThumbClientIconIds = {
            R.drawable.ico_my_request
            ,R.drawable.ico_request
            ,R.drawable.picto_lock
            ,R.drawable.ico_request
            ,R.drawable.ico_request
            ,R.drawable.ico_request
            ,R.drawable.ico_request
            , R.drawable.picto_home
            , R.drawable.ico_power_off
    };

    Integer[] mThumbEmployeIconIds = {
            R.drawable.ico_my_request
            ,R.drawable.picto_lock
            ,R.drawable.ico_request
            , R.drawable.picto_home
            , R.drawable.ico_power_off
    };

    // Keep all menu index in array
    Integer[] indexMenuEmploye = {
            Menu.DEMANDE_EN_COURS
            ,Menu.MON_COMPTE
            ,Menu.DEMANDES
            , Menu.CGV
            , Menu.DECONNEXION
    };

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @AfterViews
    void afterViews() {
        role = preferences.getRole();
        userRole.setText(role);
        userEmail.setText(preferences.getEmail());
        setUpMenu();
        myInstance = this;
    }

    public List<NavDrawerItem> getData(String user) {
        List<NavDrawerItem> data = new ArrayList<>();
        // preparing navigation drawer items
        for (int i = 0; i < titlesItem.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(titlesItem[i]);
            if(user.equalsIgnoreCase(UserType.CLIENT)){
                navItem.setImageRessource(mThumbClientIconIds[i]);
                navItem.setIndexMenu(i);
            }else {
                navItem.setImageRessource(mThumbEmployeIconIds[i]);
                navItem.setIndexMenu(indexMenuEmploye[i]);
            }
            data.add(navItem);
        }
        return data;
    }

    private void setUpMenu(){
        setMenuIcon();
        setUpMenuButton();
        final String userType = role;
        if(role.equalsIgnoreCase(UserType.CLIENT))
            titlesItem = getActivity().getResources().getStringArray(R.array.nav_drawer_labels_client);
        else
            titlesItem = getActivity().getResources().getStringArray(R.array.nav_drawer_labels_employe);

        if(menuList != null){
            DrawerAdapter drawerAdapter = new DrawerAdapter(getActivity(), getData(userType));
            menuList.setAdapter(drawerAdapter);
            menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    NavDrawerItem navDrawerItem = getData(userType).get(i);
                    if(navDrawerItem == null) {
                        return;
                    }
                    if (NetworkManager.isNetworkAvailable(getActivity())) {
                        isSms = false;
                        fragmentToShow(navDrawerItem);
                    }
                    else {
                        showErrorDialog(getString(R.string.information), getString(R.string.error_internet));
                    }
                }
            });
        }
    }

    public void fragmentToShow(NavDrawerItem navDrawerItem){
        try {
            closeMenu();
            switch(navDrawerItem.getIndexMenu()) {
                case Menu.DEMANDE_EN_COURS:
                    showDemandeEnCour();
                    break;
                case Menu.NOUVELLE_DEMANDE:
                    showFragment(new BesoinFragment_());
                    break;
                case Menu.MON_COMPTE :
                    showInfo();
                    break;
                case Menu.DEMANDES:
                    getMainActivity().showDemandeList(preferences.getRole());
                    break;
                case Menu.ARTISANS_FAVOIRS :
                    break;
                case Menu.CLASSEMENT_ARTISANS :
                    break;
                case Menu.LISTE_ETABLISSEMENTS :
                    EtablissementListFragment_ etablissementList = new EtablissementListFragment_();
                    etablissementList.setFromMenu(true);
                    showFragment(etablissementList);
                   break;
                case Menu.DECONNEXION :
                    showCustomDialog(getString(R.string.information), getString(R.string.deconnexion_message), PopUpType.DECONNEXION, -1);
                    break;
                case Menu.CGV :
                    closeMenu();
                    getCgv();
                    break;
                default:
                    break;
            }
            isSms = false;
        }catch (Exception e) {
        }
    }

    public void showDemandeEnCour(){
        if(getMainActivity()!= null){
            showLoader();
            if(role.equalsIgnoreCase(UserType.CLIENT)) {
                getMainActivity().getDemandeCouranteClient(false);
            } else {
                getMainActivity().getDemandeCouranteArtisan(false);
            }
        }
    }

    public void showInfo(){
        if (preferences.getRole().equalsIgnoreCase(UserType.CLIENT)) {
            showFragment(new ModificationInfoGeneraleFragment_());
        }else{
            PasswordChangeFragment_ passwordChangeFragment = new PasswordChangeFragment_();
            passwordChangeFragment.setResetPassword(!TextUtils.isEmpty(preferences.getPassword()));
            passwordChangeFragment.setOldPassword(preferences.getPassword());
            showFragment(passwordChangeFragment);
        }
    }

    public void showFragment(GenericFragment fragment){
        closeMenu();
        replaceFragment(R.id.main_container, fragment, false , true);
    }

    @Click(R.id.rest_menu)
    void onClikRestMenu(){
        closeMenu();
    }

    public void onEventMainThread(EventSetMenuDto event) {
        menuList.setItemChecked(event.position, true);
        NavDrawerItem navDrawerItem = new NavDrawerItem();
        navDrawerItem.setIndexMenu(event.menu);
        fragmentToShow(navDrawerItem);
    }

    MainActivity getMainActivity() {
        try {
            if (getActivity() != null) {
                return (MainActivity) getActivity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public boolean isSms() {
        return isSms;
    }
    public void setSms(boolean sms) {
        isSms = sms;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
