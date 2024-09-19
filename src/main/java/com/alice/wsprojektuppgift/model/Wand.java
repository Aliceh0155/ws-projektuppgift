package com.alice.wsprojektuppgift.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wand")
public class Wand {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    private String wood;
    private String core;
    private double length;

    public String getWood() {
        return wood;
    }

    public void setWood(String wood) {
        this.wood = wood;
    }

    public String getCore() {
        return core;
    }

    public void setCore(String core) {
        this.core = core;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

}
