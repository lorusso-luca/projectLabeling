package com.lorusso.luca.labeling;

public class Exercise {
    private String esercizio;
    private int durata;

    public Exercise(String esercizio, int durata) {
        this.esercizio = esercizio;
        this.durata = durata;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public String getEsercizio() {

        return esercizio;
    }

    public void setEsercizio(String esercizio) {
        this.esercizio = esercizio;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "esercizio='" + esercizio + '\'' +
                ", durata='" + durata + '\'' +
                '}';
    }
}
