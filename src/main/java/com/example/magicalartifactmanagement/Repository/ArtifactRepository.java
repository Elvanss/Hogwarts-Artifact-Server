package com.example.magicalartifactmanagement.Repository;

import com.example.magicalartifactmanagement.Model.Artifact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtifactRepository extends JpaRepository<Artifact, Integer> {

}
