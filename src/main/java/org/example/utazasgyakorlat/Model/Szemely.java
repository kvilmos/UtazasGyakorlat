package org.example.utazasgyakorlat.Model;

import jakarta.persistence.*;

@Entity
@Table(name="szemelyek")
public class Szemely {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) // AUTO_INCREMENT
    private Long id;
    private String nev;
    private String cim;
    private int kor;
    private double suly;

}
