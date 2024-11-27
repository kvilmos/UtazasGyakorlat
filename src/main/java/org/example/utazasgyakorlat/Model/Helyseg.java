package org.example.utazasgyakorlat.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="helyseg")
public class Helyseg {
    @Id
    private int az;
    @Column(name = "nev")
    private String  nev;
    @Column(name="orszag")
    private String orszag;

    @OneToMany(mappedBy = "helyseg", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Szalloda> szallodaList;

    public List<Szalloda> getSzallodaList() {
        return szallodaList;
    }

    public void setSzallodaList(List<Szalloda> szallodaList) {
        this.szallodaList = szallodaList;
    }

    public int getAz() {
        return az;
    }

    public void setAz(int az) {
        this.az = az;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getOrszag() {
        return orszag;
    }

    public void setOrszag(String orszag) {
        this.orszag = orszag;
    }
}
