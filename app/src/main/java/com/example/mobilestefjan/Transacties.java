package com.example.mobilestefjan;

import java.sql.Blob;

public class Transacties {
    String Omschrijving, Bedrag, Datum;
    byte[] Foto;

    public byte[] getFoto() {
        return Foto;
    }

    public void setFoto(byte[] foto) {
        Foto = foto;
    }

    public Transacties(String _omsch, String _bedrag, String _datum, byte[] foto){
        Omschrijving=_omsch;
        Bedrag=_bedrag+" EUR";
        Datum=_datum;
        Foto=foto;
    }

    public Transacties(){}

    public String getOmschrijving() {
        return Omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        Omschrijving = omschrijving;
    }

    public String getBedrag() {
        return Bedrag;
    }

    public void setBedrag(String bedrag) {
        Bedrag = bedrag;
    }

    public String getDatum() {
        return Datum;
    }

    public void setDatum(String datum) {
        Datum = datum;
    }
}
