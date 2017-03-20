package com.lorusso.luca.labeling;

import java.util.ArrayList;



public class Protocol {
    private String idProtocol;
    private String nome;
    private String descrizione;
    ArrayList<Exercise> attività;

    public Protocol(String idProtocol, String nome, String descrizione, ArrayList<Exercise> attività) {
        this.idProtocol = idProtocol;
        this.nome = nome;
        this.descrizione = descrizione;
        this.attività = attività;
    }

    public String getIdProtocol() {
        return idProtocol;
    }

    public void setIdProtocol(String idProtocol) {
        this.idProtocol = idProtocol;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "idProtocol='" + idProtocol + '\'' +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", attività=" + attività +
                '}';
    }


}
