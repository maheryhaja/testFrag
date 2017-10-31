package fr.hdb.artibip.presentation.fragment.photo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
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
import fr.hdb.artibip.commun.imagepicker.ImagePicker;
import fr.hdb.artibip.donnee.dto.constantes.login.Role;
import fr.hdb.artibip.donnee.dto.constantes.permission.Permission;
import fr.hdb.artibip.donnee.dto.constantes.photo.Photo;
import fr.hdb.artibip.donnee.dto.event.EventSetPhotoDto;
import fr.hdb.artibip.presentation.activity.MainActivity;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.presentation.fragment.client.commentaire.ClientCommentaireFragment_;
import fr.hdb.artibip.presentation.fragment.facturationfinale.FacturationFragment_;
import fr.hdb.artibip.service.applicatif.date.DateSA;
import fr.hdb.artibip.service.applicatif.date.DateSAImpl;
import fr.hdb.artibip.service.applicatif.image.ImageSA;
import fr.hdb.artibip.service.applicatif.image.ImageSAImpl;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import me.relex.circleindicator.CircleIndicator;
import static fr.hdb.artibip.presentation.activity.MainActivity.FACTURATION_POST;
import static fr.hdb.artibip.presentation.activity.MainActivity.PAGE_CURRENT;
import static fr.hdb.artibip.presentation.activity.MainActivity.clientPager;
import static fr.hdb.artibip.presentation.activity.MainActivity.listFragment;


@EFragment(R.layout.fragment_photo_client)
public class PhotoFragment extends GenericFragment{

    @ViewById(R.id.text_question_photo)
    TextView text;

    @ViewById(R.id.list_view_photo)
    ViewPager listViewPhoto;

    @ViewById(R.id.indicator)
    CircleIndicator indicator;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferences;

    @Bean(DateSAImpl.class)
    DateSA dateSA;

    @Bean(ImageSAImpl.class)
    ImageSA imageSA;

    @ViewById(R.id.button_valider)
    Button buttonValider;

    @ViewById(R.id.button_take_photo)
    ImageView myImage;

    @ViewById(R.id.scroll_photo_fragment)
    ScrollView scrollView;

    private static final int PICK_IMAGE_ID = 234;
    private static final int OK = 2;
    private Bitmap mBitmap;

    @AfterViews
    void afterView(){

        if ( ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            marshmallowReadPemissionCheck();
        }
        setApplicationDesgin();
    }

    private void marshmallowReadPemissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && getActivity().checkSelfPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Permission.READ_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Permission.READ_STORAGE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        } else {
            showErrorDialog(getString(R.string.information), getString(R.string.read_storage));
        }
    }

    /*
    * DessinÃƒÂ© l'ecran
     */
    void setApplicationDesgin(){
        if(preferences.getRole().equals(Role.CLIENT)){
            PAGE_CURRENT = 3;
        } else {
            PAGE_CURRENT = 6;
        }
        clientPager.setCurrentItem(PAGE_CURRENT,true);
        //Avec toolbar
        setToolbarVisibility(true,"DEMANDE DU "+ dateSA.toDayWithoutYear(),false);
        //Pas de footer
        setHeaderVisibility(true,true);
        setFooterVisibility(true);
        if(preferences.getRole().equals(Role.CLIENT)) {
            text.setText(getString(R.string.besoin_photo_client));
        }else{
            text.setText(getString(R.string.besoin_photo));
        }
        // load photos if needed
        if (listFragment != null) {
            clickableImage(true);
            initPhotos(0);
        } else {
            listFragment = new ArrayList<ListPhotoFragment>();
        }
    }

    @Click(R.id.layout_prise_photo_client)
    void takePhoto() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            marshmallowReadPemissionCheck();
        } else {
            Intent chooseImageIntent = ImagePicker.getPickImageIntent(getActivity());
            startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
        }
    }

    @Click(R.id.button_valider)
    void onClickValider() {

        try {
            if(preferences.getRole().equals(Role.CLIENT)) {
                if(getMainActivity() != null) {
                    if(listFragment!= null && listFragment.size()>0){
                        String imageEncoded = convertAllImages();
                        getMainActivity().getInterventionPost().setPhotoIntervention(imageEncoded);
                    }else{
                        getMainActivity().getInterventionPost().setPhotoIntervention("");
                    }
                }
                PAGE_CURRENT = 4;
                replaceFragment(R.id.main_container, new ClientCommentaireFragment_(), true, true);
            }else{
                if(listFragment!= null && listFragment.size()>0){
                    String imageEncoded = convertAllImages();
                    FACTURATION_POST.setPhotos(convertAllImages());
                }else{
                    FACTURATION_POST.setPhotos("");
                    setBitmaps(null);
                }
                getDataAndShowFacturation();
            }
        } catch (Exception e){
            showErrorDialog(getString(R.string.information), getString(R.string.encodage_photo));
        }
    }

    void getDataAndShowFacturation(){
        FacturationFragment_ facturationFragment= new FacturationFragment_();
        if(getMainActivity() == null){
            return;
        }
        if (getMainActivity().getTauxHoraire()!= null){
            facturationFragment.setTva(getMainActivity().getTauxHoraire().getTauxTVA());
        }

        if(getMainActivity().getHoraire().getLibelle()!= null){
            facturationFragment.setPlageHoraire(getMainActivity().getHoraire().getLibelle().toUpperCase());
        }

        if(getMainActivity().getForfait().getDesignation() != null){
            facturationFragment.setDureeDeplacement(getMainActivity().getForfait().getDesignation().toUpperCase());
        }

        if(getMainActivity().getForfait().getDureeIntervetion()!=null){
            facturationFragment.setDureeExact(getMainActivity().getForfait().getDureeIntervetion().toUpperCase());
        }

        facturationFragment.setDureeintervention(getMainActivity().getForfait().getMinuteDureeIntervention());
        facturationFragment.setCoutDeplacement(getMainActivity().getForfait().getTaux());
        facturationFragment.setTauxHoraire(getMainActivity().getTauxHoraire().getTauxHoraire());
        replaceFragment(R.id.main_container,facturationFragment,true,true);
    }

    private String convertAllImages() {
        List<Bitmap> bitmapList = new ArrayList<>();
        String allImagesEncoded = "";
        int counter = 0;
        for (ListPhotoFragment photo: listFragment) {
            Bitmap bitmap = photo.getBitmap();
            bitmapList.add(bitmap);
            String imageEncoded = imageSA.getEncoded64ImageString(bitmap);
            allImagesEncoded += imageEncoded;
            counter++;
            if (counter < listFragment.size()){
                allImagesEncoded += Photo.SEPARATOR;
            }
        }
        setBitmaps(bitmapList);
        return allImagesEncoded;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(EventSetPhotoDto event){
        MainActivity.photoAdapter.removeItem(event.getPos(), listViewPhoto, listFragment, indicator);
        //decrementer la position des fragment qui sont sup ÃƒÂ  celui supprimÃƒÂ©
        for(int i= 0 ; i < listFragment.size(); i++){
            if(i >= event.getPos() ){
                int posi = listFragment.get(i).getPosition();
                listFragment.get(i).setPosition(posi-1);
            }
        }
        initPhotos(event.getPos());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_ID:
                if (resultCode == Activity.RESULT_OK) {
                    mBitmap  = ImagePicker.getImageFromResult(getActivity(), resultCode, data);

                    ListPhotoFragment_ lf = new ListPhotoFragment_();
                    lf.setBitmap(mBitmap);
                    lf.setPosition(listFragment.size());
                    MainActivity.listFragment.add(lf);
                    initPhotos(listFragment.size());
                    break;
                }
            default:
                break;
        }
    }

    void initPhotos(int position) {
        MainActivity.photoAdapter = new CustomPagerAdapter(getChildFragmentManager(), listFragment);
        listViewPhoto.setAdapter(MainActivity.photoAdapter);
        indicator.setViewPager(listViewPhoto);
        listViewPhoto.setCurrentItem(position);
    }

    void clickableImage(boolean clickable){
        for(int i = 0; i< listFragment.size(); i++){
            listFragment.get(i).setClickable(clickable);
        }
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

}
