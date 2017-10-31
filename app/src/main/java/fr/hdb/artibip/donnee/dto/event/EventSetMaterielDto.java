package fr.hdb.artibip.donnee.dto.event;


public class EventSetMaterielDto {
    private int position;
    public EventSetMaterielDto(int pos){
        this.position=pos;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
