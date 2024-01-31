package com.example.magicalartifactmanagement.Controller;

import com.example.magicalartifactmanagement.DTO.ArtifactDTO;
import com.example.magicalartifactmanagement.Model.Artifact;
import com.example.magicalartifactmanagement.Service.ArtifactService;
import com.example.magicalartifactmanagement.System.State.StatusCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
public class ArtifactControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArtifactService artifactService;

    @Autowired
    ObjectMapper objectMapper;

    List<Artifact> artifacts;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

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

        Artifact a3 = new Artifact();
        a3.setId(1103);
        a3.setName("Elder Wand");
        a3.setDescription("The Elder Wand, known throughout history as the Deathstick or the Wand of Destiny, is an extremely powerful wand made of elder wood with a core of Thestral tail hair.");
        a3.setImageUrl("ImageUrl");
        this.artifacts.add(a3);

        Artifact a4 = new Artifact();
        a4.setId(1104);
        a4.setName("The Marauder's Map");
        a4.setDescription("A magical map of Hogwarts created by Remus Lupin, Peter Pettigrew, Sirius Black, and James Potter while they were students at Hogwarts.");
        a4.setImageUrl("ImageUrl");
        this.artifacts.add(a4);

        Artifact a5 = new Artifact();
        a5.setId(1105);
        a5.setName("The Sword Of Gryffindor");
        a5.setDescription("A goblin-made sword adorned with large rubies on the pommel. It was once owned by Godric Gryffindor, one of the medieval founders of Hogwarts.");
        a5.setImageUrl("ImageUrl");
        this.artifacts.add(a5);

        Artifact a6 = new Artifact();
        a6.setId(1106);
        a6.setName("Resurrection Stone");
        a6.setDescription("The Resurrection Stone allows the holder to bring back deceased loved ones, in a semi-physical form, and communicate with them.");
        a6.setImageUrl("ImageUrl");
        this.artifacts.add(a6);

        given(this.artifactService.findById(1101)).willReturn(this.artifacts.get(0));
    }


    @Test
    void testFindArtifactByIdSuccess() throws Exception {
        // Given
        // When and then
        this.mockMvc.perform(MockMvcRequestBuilders.get(this.baseUrl + "/artifacts/1250808601744904191")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value(1101))
                .andExpect(jsonPath("$.data.name").value("Deluminator"));
    }

    @Test
    void testFindArtifactByIdNotFound() throws Exception {
        // Given
        given(this.artifactService.findById(1103)).willReturn(null);

        // When and then
        this.mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/artifacts/1103")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find artifact with Id 1103 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void findAllArtifacts() throws Exception {
        //Given
        given(this.artifactService.findAll()).willReturn(this.artifacts);

        //When and then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/artifacts").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find all artifacts"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.artifacts.size())))
                .andExpect(jsonPath("$.data[0].id").value(1101))
                .andExpect(jsonPath("$.data[0].name").value("Deluminator"))
                .andExpect(jsonPath("$.data[1].id").value(1102))
                .andExpect(jsonPath("$.data[1].name").value("Invisibility Cloak"));
    }

    @Test
    void testAddArtifactSuccess() throws Exception {
        //Given
        ArtifactDTO artifactDTO = new ArtifactDTO(null, "Remembrall",
                                                        "desc",
                                                        "image",
                                                            null);
        String json = objectMapper.writeValueAsString(artifactDTO);

        Artifact savedArtifact = new Artifact();
        savedArtifact.setId(999);
        savedArtifact.setName("new");
        savedArtifact.setDescription("123");
        savedArtifact.setImageUrl("image");

        given(this.artifactService.addArtifact(Mockito.any(Artifact.class))).willReturn(savedArtifact);

        //When and then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/artifacts").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add artifact success"))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.name").value(savedArtifact.getName()))
                .andExpect(jsonPath("$.data.description").value(savedArtifact.getDescription()))
                .andExpect(jsonPath("$.data.imageUrl").value(savedArtifact.getImageUrl()));

    }
    @Test
    void testUpdateArtifactErrorWithNonExistentId() throws Exception {
        // Given
        ArtifactDTO artifactDto = new ArtifactDTO(1101,
                "Invisibility Cloak",
                "A new description.",
                "ImageUrl",
                null);
        String json = this.objectMapper.writeValueAsString(artifactDto);

        given(this.artifactService.update(eq(1101), Mockito.any(Artifact.class))).willThrow(new ObjectNotFoundException("artifact", 1101));

        // When and then
        this.mockMvc.perform(put(this.baseUrl + "/artifacts/1101").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find artifact with Id 1250808601744904192 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteArtifactSuccess() throws Exception {
        // Given
        doNothing().when(this.artifactService).delete(1101);

        // When and then
        this.mockMvc.perform(delete(this.baseUrl + "/artifacts/1101").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteArtifactErrorWithNonExistentId() throws Exception {
        // Given
        doThrow(new ObjectNotFoundException("artifact", 1101)).when(this.artifactService).delete(1101);

        // When and then
        this.mockMvc.perform(delete(this.baseUrl + "/artifacts/1101").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find artifact with Id 1101 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}
