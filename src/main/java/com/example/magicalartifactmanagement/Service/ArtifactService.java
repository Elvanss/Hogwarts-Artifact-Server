package com.example.magicalartifactmanagement.Service;

import com.example.magicalartifactmanagement.Model.Artifact;
import com.example.magicalartifactmanagement.Repository.ArtifactRepository;
import com.example.magicalartifactmanagement.System.Exception.ArtifactNotFoundException;
import com.example.magicalartifactmanagement.Ultis.IdWorker;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ArtifactService {

    private final ArtifactRepository artifactRepository;

    private final IdWorker idWorker;

    public ArtifactService(ArtifactRepository artifactRepository, IdWorker idWorker) {
        this.artifactRepository = artifactRepository;
        this.idWorker = idWorker;
    }

    //find the artifact by artifact id
    public Artifact findById(Integer artifactId) {
        return this.artifactRepository.findById(artifactId)
                .orElseThrow(()-> new ArtifactNotFoundException(artifactId));
    }

    //find all the artifact in the list
    public List<Artifact> findAll() {
        return this.artifactRepository.findAll();
    }

    public Artifact addArtifact(Artifact artifact) {
        artifact.setId(idWorker.nextId());
        return artifactRepository.save(artifact);
    }

//    public Artifact update (Integer artifactId, Artifact update) {
//        Artifact oldArtifact = this.artifactRepository.findById(artifactId).get();
//        oldArtifact.setName(update.getName());
//        oldArtifact.setDescription(update.getDescription());
//        oldArtifact.setImageUrl(update.getImageUrl());
//
//        Artifact updated = this.artifactRepository.save(oldArtifact);
//        return updated;
//    }


        public Artifact update (Integer artifactId, Artifact update) {
            return this.artifactRepository.findById(artifactId)
                    .map(oldArtifact -> {
                        oldArtifact.setName(update.getName());
                        oldArtifact.setDescription(update.getDescription());
                        oldArtifact.setImageUrl(update.getImageUrl());
                        Artifact save = this.artifactRepository.save(oldArtifact);
                        return save;
                    })
                    .orElseThrow(() -> new ArtifactNotFoundException(artifactId));

        }

    public void delete(Integer artifactId) {
        this.artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ObjectNotFoundException("artifact", artifactId));
        this.artifactRepository.deleteById(artifactId);
    }

    }



