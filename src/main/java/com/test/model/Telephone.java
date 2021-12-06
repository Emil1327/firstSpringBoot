package com.test.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "telephone")
public class Telephone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "model")
    private String model;

    @JsonBackReference
    @OneToOne(mappedBy = "telephone")
    private User user;

    public Telephone() {
    }

    public Telephone(int id, String name, String model) {
        this.id = id;
        this.name = name;
        this.model = model;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telephone telephone = (Telephone) o;
        return id == telephone.id && Objects.equals(name, telephone.name) && Objects.equals(model, telephone.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, model);
    }

    @Override
    public String toString() {
        return "Telephone{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
