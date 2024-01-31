package com.example.magicalartifactmanagement.Service;

import com.example.magicalartifactmanagement.Model.Artifact;
import com.example.magicalartifactmanagement.Model.Wizard;
import com.example.magicalartifactmanagement.Repository.ArtifactRepository;
import com.example.magicalartifactmanagement.Repository.WizardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class WizardServiceTest {

    @Mock
    WizardRepository wizardRepository;

    @InjectMocks
    WizardService wizardService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findWizardById() {
        //Given
        Artifact newArtifact = new Artifact();
        newArtifact.setId(999);
        newArtifact.setName("new");
        newArtifact.setDescription("123");
        newArtifact.setImageUrl("image");
        List<Artifact> artifactList = new ArrayList<>();
        artifactList.add(newArtifact);
        //Create the wizard (Test) with created artifact
        Wizard wizard = new Wizard(1, "Evan", artifactList);
        //Make sure the wizard returnt the wizard object when repo search for id 1
        given(wizardRepository.findById(1)).willReturn(Optional.of(wizard));

        //When


        //Then

    }

    @Test
    void getAllWizard() {
    }

    @Test
    void addWizard() {
    }

    @Test
    void updateWizard() {
    }

    @Test
    void delete() {
    }
}