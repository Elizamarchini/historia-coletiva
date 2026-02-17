package com.marchini.historiacaotica.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime data;

    public Contribution() {}

    public Contribution(String texto) {
        this.texto = texto;
    }

    public Long getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
