package fr.hdb.artibip.donnee.dto.event;


import android.graphics.Bitmap;

public class EventSetSignatureDto {

    public final Bitmap signature;

    public EventSetSignatureDto(Bitmap signature) {
        this.signature = signature;
    }
}
