package com.alice.wsprojektuppgift.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "wand")
public class WandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String wood;
    private String core;
    private double length;

    public WandEntity() {
    }

    public WandEntity(String wood, String core, double length) {
        this.wood = wood;
        this.core = core;
        this.length = length;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
