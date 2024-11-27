package org.example.utazasgyakorlat.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "szalloda")
public class Szalloda {

    @Id
    private String az;
    @Column(name="nev")
    private String nev;
    @Column(name="besorolas")
    private int besorolas;
    @Column(name="helyseg_az")
    private int helyseg_az;
    @Column(name="tengerpart_tav")
    private int tengerpart_tav;
    @Column(name="repter_tav")
    private int repter_tav;
    @Column(name="felpanzio")
    private boolean felpanzio;

    @ManyToOne
    @JoinColumn(name = "helyseg_az", insertable = false, updatable = false)
    private Helyseg helyseg;

    @OneToMany(mappedBy = "szalloda", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Tavasz> tavaszList;

    public Helyseg getHelyseg() {
        return helyseg;
    }

    public void setHelyseg(Helyseg helyseg) {
        this.helyseg = helyseg;
    }

    public List<Tavasz> getTavaszList() {
        return tavaszList;
    }

    public void setTavaszList(List<Tavasz> tavaszList) {
        this.tavaszList = tavaszList;
    }

    public String getAz() {
        return az;
    }

    public void setAz(String az) {
        this.az = az;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getBesorolas() {
        return besorolas;
    }

    public void setBesorolas(int besorolas) {
        this.besorolas = besorolas;
    }

    public int getHelyseg_az() {
        return helyseg_az;
    }

    public void setHelyseg_az(int helyseg_az) {
        this.helyseg_az = helyseg_az;
    }

    public int getTengerpart_tav() {
        return tengerpart_tav;
    }

    public void setTengerpart_tav(int tengerpart_tav) {
        this.tengerpart_tav = tengerpart_tav;
    }

    public int getRepter_tav() {
        return repter_tav;
    }

    public void setRepter_tav(int repter_tav) {
        this.repter_tav = repter_tav;
    }

    public boolean isFelpanzio() {
        return felpanzio;
    }

    public void setFelpanzio(boolean felpanzio) {
        this.felpanzio = felpanzio;
    }
}
