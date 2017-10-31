package fr.hdb.artibip.presentation.fragment.photo;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import com.wang.avi.AVLoadingIndicatorView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import fr.hdb.artibip.R;
import fr.hdb.artibip.donnee.dto.constantes.user.UserType;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.service.technique.picasso.PicassoUtils;


@EFragment(R.layout.liste_item_photo)
public class ListPhotoFragment extends GenericFragment {

    @ViewById(R.id.image_photo)
    protected ImageView imagePhoto;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferences;

    @ViewById(R.id.avi_loading_image)
    AVLoadingIndicatorView indicator;

    private int position;
    private Bitmap bitmap;
    private boolean clickable= true;
    private String url;

    @AfterViews
    void afterViews(){
        indicator.setVisibility(View.VISIBLE);
        if(preferences.getRole().equals(UserType.CLIENT)){
           if(isStatusTermineeClient()){
               clickable= false;
               try{
                   if(url != null) {
                       PicassoUtils.startLoadingImage(getContext()
                               ,url
                               ,imagePhoto
                               ,indicator
                       );
                   } else {
                       indicator.setVisibility(View.GONE);
                   }
               } catch (Exception e){
                   indicator.setVisibility(View.GONE);
               }
           }else {
               indicator.setVisibility(View.GONE);
               imagePhoto.setImageBitmap(this.bitmap);
           }
        }else{
            if(isStatutsTraiterArtisan()){
                clickable= false;
                try{
                    if(url != null) {
                        PicassoUtils.startLoadingImage(getContext()
                                ,url
                                ,imagePhoto
                                ,indicator
                        );
                    } else {
                        indicator.setVisibility(View.GONE);
                    }
                } catch (Exception e){
                    indicator.setVisibility(View.GONE);
                }
            }else{
                indicator.setVisibility(View.GONE);
                imagePhoto.setImageBitmap(this.bitmap);
            }
        }

    }

    @Click(R.id.image_photo)
    void onLongClickImage(){
        if(clickable) {
            showImageDialog(position, this.bitmap);
        }else{
            if((BitmapDrawable) imagePhoto.getDrawable()!=null) {
                BitmapDrawable drawable = (BitmapDrawable) imagePhoto.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                showImageDialogFacture(position, bitmap);
            }
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public boolean isClickable() {
        return clickable;
    }
    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

}
