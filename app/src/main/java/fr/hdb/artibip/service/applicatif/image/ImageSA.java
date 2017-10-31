package fr.hdb.artibip.service.applicatif.image;

import android.graphics.Bitmap;


public interface ImageSA {
    /**
     * Encodage d'une Bitmap en base 64
     * @param bitmap : fichier à encoder
     * @return : String contenant l'encodage
     */
    String getEncoded64ImageString(Bitmap bitmap);
}
