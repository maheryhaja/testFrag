package fr.hdb.artibip.presentation.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import java.util.List;

import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.DemandeArtisanDto;
import fr.hdb.artibip.donnee.dto.DemandeClientDto;
import fr.hdb.artibip.donnee.dto.constantes.popup.PopUpType;
import fr.hdb.artibip.donnee.dto.ws.artisan.resumeintervention.ResumeInterventionResponseDto;
import fr.hdb.artibip.presentation.activity.MainActivity;

public class GenericFragment extends AbstractFragment {

    private static ProgressDialog progress;

    protected void closeMenu() {
        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    protected void showCustomDialog(String title, String message, PopUpType popUpType, int position) {
        if(getActivity() != null)
        ((MainActivity) getActivity()).showCustomDialog(title, message, popUpType, position);
    }

    protected void showImageDialog(int position, Bitmap bitmap) {
        if(getActivity() != null)
        ((MainActivity) getActivity()).showImageDialog(position, bitmap);
    }
    protected void showImageDialogFacture(int position, Bitmap bitmap) {
        if(getActivity() != null)
        ((MainActivity) getActivity()).showImageDialogFacture(position, bitmap);
    }

    protected void showMenuDialog(String title, String message){
        if(getActivity() != null)
        ((MainActivity) getActivity()).showMenuDialog(title, message);
    }

    protected void showEtabDialog(String title, String message){
        if(getActivity() != null)
        ((MainActivity) getActivity()).showEtabDialog(title, message);
    }

    /**
     * afficher une barre de progresseion
     *
     * @param message : le message à afficher dans la barre de progression
     */
    protected void showProgress(String message) {
        dismissProgress();
        progress = new ProgressDialog(getActivity());
        progress.setMessage(message);
        progress.setCancelable(false);
        progress.show();
    }

    /**
     * retirer la barre de progression de l'ecran
     */
    protected void dismissProgress() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

    /**
     * test si le device est connecté à internet
     */
    public boolean isConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity.getActiveNetworkInfo() != null) {
            if (connectivity.getActiveNetworkInfo().isConnected()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Afficher un Toast
     */
    protected void showToast(String message) {
        if(getActivity() != null){
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    protected void deconnect() {
        ((MainActivity)getActivity()).deconnect();
    }

    protected void marshmallowPemissionChecker(String persmission, int type) {
        ((MainActivity)getActivity()).marshmallowPemissionChecker(persmission, type);
    }

    protected int getIdIntervention() {
        return MainActivity.idInterventionArtisan;
    }

    protected void setIdIntervention(int idIntervention) {
        MainActivity.idInterventionArtisan = idIntervention;
    }

    protected boolean isStatutsTraiterArtisan() {
        return ((MainActivity)getActivity()).isStatutsTraiterArtisan();
    }

    protected void setStatutsTraiterArtisan(boolean statuts) {
       ((MainActivity)getActivity()).setStatutsTraiterArtisan(statuts);
    }


    protected boolean isBackToDetail() {
        return ((MainActivity)getActivity()).isBackToDetail();
    }

    protected void setBackToDetail(boolean backToDetail) {
        ((MainActivity)getActivity()).setBackToDetail(backToDetail);
    }


    protected ResumeInterventionResponseDto getResumeInterventionResponseDto() {
        return ((MainActivity)getActivity()).getResumeInterventionResponseDto();
    }

    protected void setResumeInterventionResponseDto(ResumeInterventionResponseDto resumeInterventionResponseDto) {
        ((MainActivity)getActivity()).setResumeInterventionResponseDto(resumeInterventionResponseDto);
    }


    protected boolean isStatusTermineeClient() {
        return ((MainActivity)getActivity()).isStatusTermineeClient();
    }

    protected void setStatusTermineeClient(boolean statuts) {
        ((MainActivity)getActivity()).setStatusTermineeClient(statuts);
    }

    protected List<Bitmap> getBitmaps() {
        return  ((MainActivity)getActivity()).getBitmaps();
    }

    protected void setBitmaps(List<Bitmap> bitmaps) {
        ((MainActivity)getActivity()).setBitmaps(bitmaps);
    }

    @Override
    protected void getCgv() {
        ((MainActivity)getActivity()).getCgv();
    }

    /** DEMANDES CLIENT */
    public List<DemandeClientDto> getTousDesDemandesDto() {
        return ((MainActivity)getActivity()).getTousDesDemandesDto();
    }

    public void setTousDesDemandesDto(List<DemandeClientDto> tousDesDemandesDto) {
        ((MainActivity)getActivity()).setTousDesDemandesDto(tousDesDemandesDto);
    }

    public DemandeClientDto getDemandeEnCoursDto() {
        return ((MainActivity)getActivity()).getDemandeEnCoursDto();
    }

    public void setDemandeEnCoursDto(DemandeClientDto demandeEnCoursDto) {
        ((MainActivity)getActivity()).setDemandeEnCoursDto(demandeEnCoursDto);
    }

    public void setImageEncours(String imageEncours){
        ((MainActivity)getActivity()).setImageEncours(imageEncours);
    }

    public void setImageRefuse(String imageRefuse){
        ((MainActivity)getActivity()).setImageRefuse(imageRefuse);
    }

    public String getNumConseiller(){
        return ((MainActivity)getActivity()).getNumConseiller();
    }

    public void setNumConseiller(String numConseiller){
         ((MainActivity)getActivity()).setNumConseiller(numConseiller);
    }

    public String getImageEncours(){
        return ((MainActivity)getActivity()).getImageEncours();
    }

    public String getImageRefuse(){
        return ((MainActivity)getActivity()).getImageRefuse();
    }

    /** DEMANDES ARTISAN */

    public List<DemandeArtisanDto> getDemandesArtisan() {
        return ((MainActivity)getActivity()).getDemandesArtisan();
    }

    public void setDemandesArtisan(List<DemandeArtisanDto> demandesArtisan) {
        ((MainActivity)getActivity()).setDemandesArtisan(demandesArtisan);
    }

    public DemandeArtisanDto getDemandeEnCoursArtisan() {
        return ((MainActivity)getActivity()).getDemandeEnCoursArtisan();
    }

    public void setDemandeEnCoursArtisan(DemandeArtisanDto demandeEnCoursArtisan) {
        ((MainActivity)getActivity()).setDemandeEnCoursArtisan(demandeEnCoursArtisan);
    }
}
