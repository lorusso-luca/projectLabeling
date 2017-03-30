package com.lorusso.luca.labeling;

import java.io.Serializable;
import java.util.ArrayList;


public class Protocol implements Serializable {
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

    public ArrayList<Exercise> getAttività() {
        return attività;
    }

    public void setAttività(ArrayList<Exercise> attività) {
        this.attività = attività;
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

    public ArrayList<Protocol> createProtocol() {
        String id = "1";
        String nome = "esempio";
        String descrizione = "Protocollo dedicato alla camminata e seduta";
        Exercise e1 = new Exercise("camminata", 10);
        Exercise e2 = new Exercise("seduti", 20);
        ArrayList<Exercise> e = new ArrayList<Exercise>();
        e.add(e1);
        e.add(e2);
        Protocol p1 = new Protocol(id, nome, descrizione, e);
        ArrayList<Protocol> p = new ArrayList<Protocol>();
        p.add(p1);


        return p;
    }


}
