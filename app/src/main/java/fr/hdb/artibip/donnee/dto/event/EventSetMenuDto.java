package fr.hdb.artibip.donnee.dto.event;


public class EventSetMenuDto {

    public final int position;

    public final int menu;

    public EventSetMenuDto(int position, int menu) {
        this.position = position;
        this.menu = menu;
    }
}
