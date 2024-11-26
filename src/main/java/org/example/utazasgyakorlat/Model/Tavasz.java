package org.example.utazasgyakorlat.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name="tavasz")
public class Tavasz {
    @Id
    private int sorszam;
    @Column(name="szalloda_az")
    private String szalloda_az;
    @Column(name="indulas")
    private Timestamp indulas;
    @Column(name="idotartam")
    private int idotartam;
    @Column(name="ar")
    private int ar;

    @ManyToOne
    @JoinColumn(name = "szalloda_az", insertable = false, updatable = false)
    @JsonBackReference
    private Szalloda szalloda;

    public Szalloda getSzalloda() {
        return szalloda;
    }

    public void setSzalloda(Szalloda szalloda) {
        this.szalloda = szalloda;
    }

    public int getSorszam() {
        return sorszam;
    }

    public void setSorszam(int sorszam) {
        this.sorszam = sorszam;
    }

    public String getSzalloda_az() {
        return szalloda_az;
    }

    public void setSzalloda_az(String szalloda_az) {
        this.szalloda_az = szalloda_az;
    }

    public Timestamp getIndulas() {
        return indulas;
    }

    public void setIndulas(Timestamp indulas) {
        this.indulas = indulas;
    }

    public int getIdotartam() {
        return idotartam;
    }

    public void setIdotartam(int idotartam) {
        this.idotartam = idotartam;
    }

    public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }
}
