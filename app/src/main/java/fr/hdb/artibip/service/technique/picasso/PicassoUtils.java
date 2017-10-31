package fr.hdb.artibip.service.technique.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wang.avi.AVLoadingIndicatorView;
import fr.hdb.artibip.R;


public class PicassoUtils {

	public static void startLoadingImage(Context mContext, String url, final ImageView image, final AVLoadingIndicatorView loading){
		String urlComplet = null;
		if(url.startsWith("http://") || url.startsWith("https://") ){
			urlComplet = url;
		}else{
			urlComplet = "http://" + url;
		}
		Picasso.with(mContext)
        .load(urlComplet)
        .placeholder(null)
        .error(R.mipmap.ic_image)
        .into(image, new Callback(){
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				loading.setVisibility(View.GONE);
			}
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				loading.setVisibility(View.GONE);
			}
        });
	}

	public static void startLoadingImageTarget(Context mContext, String url, PubListener listener){
		Target target = new ImageViewTarget(listener);
		Picasso.with(mContext)
        .load(url)
        .into(target);
	}

	private static class ImageViewTarget implements Target {
	    private PubListener mListener;
	    public ImageViewTarget( PubListener listener) {
	        this.mListener = listener;
	    }

	    @Override
	    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
	        //you can use this bitmap to load image in image view or save it in image file like the one in the above question.
			if(mListener != null) {
				mListener.onLoadImagePub(bitmap);
			}
	    }

	    @Override
	    public void onBitmapFailed(Drawable errorDrawable) {
			if(mListener != null)
				mListener.onFailImagePub();

	    }

	    @Override
	    public void onPrepareLoad(Drawable placeHolderDrawable) {
			if(mListener != null)
				mListener.onPrepareImagePub();

	    }
	}

	public interface PubListener{
		void onLoadImagePub(Bitmap bitmap);
		void onFailImagePub();
		void onPrepareImagePub();
	}
	
}
