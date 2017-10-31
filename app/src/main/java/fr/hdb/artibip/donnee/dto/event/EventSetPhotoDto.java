package fr.hdb.artibip.donnee.dto.event;



public class EventSetPhotoDto {
    private int pos;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public EventSetPhotoDto(int pos){
        this.pos=pos;
    }
}
