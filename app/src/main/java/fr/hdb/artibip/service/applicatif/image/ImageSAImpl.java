package fr.hdb.artibip.service.applicatif.image;

import android.graphics.Bitmap;
import android.util.Base64;
import org.androidannotations.annotations.EBean;
import java.io.ByteArrayOutputStream;


@EBean(scope = EBean.Scope.Singleton)
public class ImageSAImpl implements ImageSA{
    /**
     * Encodage d'une Bitmap en base 64
     * @param bitmap : fichier à encoder
     * @return : String contenant l'encodage
     */
    @Override
    public String getEncoded64ImageString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }

}
