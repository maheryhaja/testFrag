package fr.hdb.artibip.presentation.view;

/**
 * .
 */

public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private int imageRessource;
    private int indexMenu;

    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title, int imageRessource,int indexMenu) {
        this.showNotify = showNotify;
        this.title = title;
        this.imageRessource = imageRessource;
        this.indexMenu = indexMenu;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageRessource() {
        return imageRessource;
    }

    public void setImageRessource(int imageRessource) {
        this.imageRessource = imageRessource;
    }

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        this.indexMenu = indexMenu;
    }
}
