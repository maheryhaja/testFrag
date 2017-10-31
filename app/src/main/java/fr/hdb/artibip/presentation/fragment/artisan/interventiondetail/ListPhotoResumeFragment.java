package fr.hdb.artibip.presentation.fragment.artisan.interventiondetail;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import com.wang.avi.AVLoadingIndicatorView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import fr.hdb.artibip.R;
import fr.hdb.artibip.presentation.fragment.GenericFragment;
import fr.hdb.artibip.service.technique.picasso.PicassoUtils;

@EFragment(R.layout.list_item_photo_resume)
public class ListPhotoResumeFragment extends GenericFragment{

    @ViewById(R.id.image_photo)
    ImageView imagePhoto;
    @ViewById(R.id.avi_loading_image)
    AVLoadingIndicatorView indicator;
    private String url;
    private int position;

    @AfterViews
    void afterViews() {
        indicator.setVisibility(View.VISIBLE);
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
    }

    @Click(R.id.image_photo)
    void onLongClickImage(){
        if((BitmapDrawable) imagePhoto.getDrawable()!=null) {
            BitmapDrawable drawable = (BitmapDrawable) imagePhoto.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            showImageDialogFacture(position, bitmap);
        }
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

}


