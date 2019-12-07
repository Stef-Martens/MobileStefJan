package com.example.mobilestefjan;

public class Vrienden {

    String achternaam, voornaam, geld;

    public Vrienden(String _achternaam, String _voornaam, String _geld){
        achternaam=_achternaam;
        voornaam=_voornaam;
        geld=_geld;
    }
    public Vrienden(){}

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getGeld() {
        return geld;
    }

    public void setGeld(String geld) {
        this.geld = geld;
    }
}
