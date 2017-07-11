package com.example.ramiro.projectefinal.RecyclerView;



public class Contact {
    private String icon;
    private String usuari;
    private String puntuacio;


    public Contact(String icon, String usuari, String puntuacio){
        this.icon = icon;
        this.usuari = usuari;
        this.puntuacio = puntuacio;

    }

    Contact(){

    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUsuari() {
        return usuari;
    }

    public void setUsuari(String usuari) {
        this.usuari = usuari;
    }

    public String getPuntuacio() {
        return puntuacio;
    }

    public void setPuntuacio(String puntuacio) {
        this.puntuacio = puntuacio;
    }


}
