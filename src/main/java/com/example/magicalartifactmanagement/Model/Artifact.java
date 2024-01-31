package com.example.magicalartifactmanagement.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Artifact")
public class Artifact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="id")
    private Integer id;

    @Column(name ="name")
    private String name;

    @Column(name ="description")
    private String description;

    @Column(name ="image_url")
    private String imageUrl;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "owner", referencedColumnName = "id")
    private Wizard owner;
}
