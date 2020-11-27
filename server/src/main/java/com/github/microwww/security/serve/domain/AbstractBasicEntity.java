package com.github.microwww.security.serve.domain;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractBasicEntity implements Serializable {

    @Id
    @Column(columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
