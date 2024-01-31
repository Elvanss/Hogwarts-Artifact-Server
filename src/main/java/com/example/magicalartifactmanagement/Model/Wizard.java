package com.example.magicalartifactmanagement.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Wizard")
public class Wizard implements Serializable {

    @Id
    @Column(name = "id", unique = true)
    private Integer id;

    @Column(name = "name")
    private String name;

    @JsonBackReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "owner")
    private List<Artifact> artifactList;

    public void addArtifact(Artifact artifact) {
        artifact.setOwner(this);
        this.artifactList.add(artifact);
    }

    public Integer getNumberOfArtifacts() {
        return this.getArtifactList().size();
    }
}
