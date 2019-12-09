package com.example.mobilestefjan;

public class Jezelf {
    String Achternaam, Voornaam, Saldo, Wachtwoord;

    public String getWachtwoord() {
        return Wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        Wachtwoord = wachtwoord;
    }

    public Jezelf(String achternaam, String voornaam, String saldo, String wachtwoord) {
        Achternaam = achternaam;
        Voornaam = voornaam;
        Saldo = saldo;
        Wachtwoord = wachtwoord;
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
