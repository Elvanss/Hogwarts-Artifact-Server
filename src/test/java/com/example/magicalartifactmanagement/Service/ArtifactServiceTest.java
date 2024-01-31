package com.example.magicalartifactmanagement.Service;

import com.example.magicalartifactmanagement.Model.Artifact;
import com.example.magicalartifactmanagement.Model.Wizard;
import com.example.magicalartifactmanagement.Repository.ArtifactRepository;
import com.example.magicalartifactmanagement.System.Exception.ArtifactNotFoundException;
import com.example.magicalartifactmanagement.Ultis.IdWorker;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtifactServiceTest {

    @Mock
    ArtifactRepository artifactRepository;

    @Mock
    IdWorker idWorker;

    @InjectMocks
    ArtifactService artifactService;



    private List<Artifact> artifacts;

    @BeforeEach
    void setUp() {
        this.artifacts = new ArrayList<>();
        Artifact a1 = new Artifact();
        a1.setId(1101);
        a1.setName("Deluminator");
        a1.setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.");
        a1.setImageUrl("ImageUrl");
        this.artifacts.add(a1);

        Artifact a2 = new Artifact();
        a2.setId(1102);
        a2.setName("Invisibility Cloak");
        a2.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a2.setImageUrl("ImageUrl");
        this.artifacts.add(a2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        //Given. Arrange input and targets. Define the behaviour of Mock object artifactRepository
        Artifact obj1 = new Artifact();
        obj1.setId(1101);
        obj1.setName("Sword");
        obj1.setDescription("A weapon is used to kill the monster");
        obj1.setImageUrl("ImageUrl");

        Wizard obj2 = new Wizard();
        obj2.setId(1);
        obj2.setName("Evan");

        // Set the owner of the artifact
        obj1.setOwner(obj2);

        // Define the behaviour of the mock the object (Artifact)
        given(artifactRepository.findById(1101)).willReturn(Optional.of(obj1));

        // When. Act on the target behaviour. When steps should cover the method to be tested
        Artifact returnedArtifact = artifactService.findById(1101);

        // Then. Assert expected outcomes
        assertThat(returnedArtifact.getId()).isEqualTo(obj1.getId());
        assertThat(returnedArtifact.getName()).isEqualTo(obj1.getName());
        assertThat(returnedArtifact.getDescription()).isEqualTo(obj1.getDescription());
        assertThat(returnedArtifact.getImageUrl()).isEqualTo(obj1.getImageUrl());

        //Comparing actual values which is exactly once
        verify(artifactRepository, times(1)).findById(1101);
    }

    @Test
    void testFindByIdNotFound() {
        // Given
        given(artifactRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        // When
//        Artifact returnedArtifact = artifactService.findById(1101);
        Throwable thrown = catchThrowable(()->{
            Artifact returnedArtifact = artifactService.findById(1101);
        });
        // Then
        assertThat(thrown)
                .isInstanceOf(ArtifactNotFoundException.class);
 //                .hasMessage("There is no available artifact in the wizard list!"); //get the message from exception class
        verify(artifactRepository, times(1)).findById(1101);

    }

    @Test
    void findAllSuccess() {
        //given
        given(artifactRepository.findAll()).willReturn(this.artifacts);

        //when
        List<Artifact> actualArtifacts = artifactService.findAll();

        //Then
        assertThat(actualArtifacts.size()).isEqualTo(this.artifacts.size());
        verify(artifactRepository, times(1)).findAll();
    }

    @Test
    void addArtifactSuccess() {
        //Given
        Artifact newArtifact = new Artifact();
        newArtifact.setId(999);
        newArtifact.setName("new");
        newArtifact.setDescription("123");
        newArtifact.setImageUrl("image");

        given(idWorker.nextId()).willReturn(123456);
        given(artifactRepository.save(newArtifact)).willReturn(newArtifact);

        //When
        Artifact savedArtifact = artifactService.addArtifact(newArtifact);

        //Then
        assertThat(savedArtifact.getId()).isEqualTo(999);
        assertThat(savedArtifact.getName()).isEqualTo(newArtifact.getName());
        assertThat(savedArtifact.getDescription()).isEqualTo(newArtifact.getDescription());
        assertThat(savedArtifact.getImageUrl()).isEqualTo(newArtifact.getImageUrl());

        verify(artifactRepository, times(1)).save(newArtifact);
    }

    @Test
    void testUpdateSuccess() {
        // Given
        Artifact oldArtifact = new Artifact();
        oldArtifact.setId(1101);
        oldArtifact.setName("Sword");
        oldArtifact.setDescription("A weapon is used to kill the monster");
        oldArtifact.setImageUrl("ImageUrl");

        Artifact update = new Artifact();
        update.setId(1101); //can or cannot be changed
        update.setName("SwordUpdated");
        update.setDescription("new description");
        update.setImageUrl("ImageUrlUpdated");

        given(artifactRepository.findById(1101)).willReturn(Optional.of(oldArtifact));
        given(artifactRepository.save(oldArtifact)).willReturn(oldArtifact);

        // When
        Artifact updatedArtifact = artifactService.update(1101, update);

        // Then
        assertThat(updatedArtifact.getId()).isEqualTo(oldArtifact.getId());
        assertThat(updatedArtifact.getDescription()).isEqualTo(update.getDescription());

    }
    @Test
    void testDeleteSuccess() {
        // Given
        Artifact artifact = new Artifact();
        artifact.setId(1101);
        artifact.setName("Invisibility Cloak");
        artifact.setDescription("An invisibility cloak is used to make the wearer invisible.");
        artifact.setImageUrl("ImageUrl");

        given(this.artifactRepository.findById(1101)).willReturn(Optional.of(artifact));
        doNothing().when(this.artifactRepository).deleteById(1101);

        // When
        this.artifactService.delete(1101);

        // Then
        verify(this.artifactRepository, times(1)).deleteById(1101);
    }

    @Test
    void testDeleteNotFound() {
        // Given
        given(this.artifactRepository.findById(1101)).willReturn(Optional.empty());

        // When
        assertThrows(ObjectNotFoundException.class, () -> {
            this.artifactService.delete(1101);
        });

        // Then
        verify(this.artifactRepository, times(1)).findById(1101);
    }

}