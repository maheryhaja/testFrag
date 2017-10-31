package fr.hdb.artibip.donnee.dto.event;

public class EventSetEtablissementDto {
    private int position;

    public EventSetEtablissementDto(int position){
        this.position=position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
