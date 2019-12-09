package com.example.mobilestefjan;

public class Jezelf {
    String Achternaam, Voornaam, Saldo;

    public Jezelf(String achternaam, String voornaam, String saldo) {
        Achternaam = achternaam;
        Voornaam = voornaam;
        Saldo = saldo;
    }

    public Jezelf(){}

    public String getAchternaam() {
        return Achternaam;
    }

    public void setAchternaam(String achternaam) {
        Achternaam = achternaam;
    }

    public String getVoornaam() {
        return Voornaam;
    }

    public void setVoornaam(String voornaam) {
        Voornaam = voornaam;
    }

    public String getSaldo() {
        return Saldo;
    }

    public void setSaldo(String saldo) {
        Saldo = saldo;
    }
}
