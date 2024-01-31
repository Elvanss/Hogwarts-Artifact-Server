package com.example.magicalartifactmanagement.Controller;

import com.example.magicalartifactmanagement.DTO.ATOConverter.ArtifactDtoToArtifactConverter;
import com.example.magicalartifactmanagement.DTO.ArtifactDTO;
import com.example.magicalartifactmanagement.DTO.ClassConverter.ArtifactToArtifactDTOConverter;
import com.example.magicalartifactmanagement.Model.Artifact;
import com.example.magicalartifactmanagement.Service.ArtifactService;
import com.example.magicalartifactmanagement.System.State.Result;
import com.example.magicalartifactmanagement.System.State.StatusCode;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/artifacts")

public class ArtifactController {


    private final ArtifactService artifactService;
    private final ArtifactToArtifactDTOConverter artifactToArtifactDTOConverter;

    private final ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter;

    public ArtifactController(ArtifactService artifactService, ArtifactToArtifactDTOConverter artifactToArtifactDTOConverter, ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter) {
        this.artifactService = artifactService;
        this.artifactToArtifactDTOConverter = artifactToArtifactDTOConverter;
        this.artifactDtoToArtifactConverter = artifactDtoToArtifactConverter;
    }

    @GetMapping("/{artifactId}")
    public ResponseEntity<Result> findArtifactById(@PathVariable Integer artifactId) {
        Artifact foundArtifact = this.artifactService.findById(artifactId);
        ArtifactDTO artifactDTO = this.artifactToArtifactDTOConverter.convert(foundArtifact);
        Result result = new Result(true, StatusCode.SUCCESS, "Find One Success", artifactDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Result> findAllArtifacts() {
        List<Artifact> artifactDTOList = this.artifactService.findAll();
        List<ArtifactDTO> convertedDTOs = artifactDTOList
                .stream()
                .map(foundArtifact -> this.artifactToArtifactDTOConverter.convert(foundArtifact))
                .collect(Collectors.toList());

//        for (Artifact foundArtifact : artifactDTOList) {
//            ArtifactDTO convert = this.artifactToArtifactDTOConverter.convert(foundArtifact);
//            convertedDTOs.add(convert);
//        }


        Result result = new Result(true, StatusCode.SUCCESS, "Find All Success", convertedDTOs);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Result> addArtifact (@Valid @RequestBody ArtifactDTO artifactDTO) {
        //Convert artifactDto to artifact
        Artifact newArtifact = this.artifactDtoToArtifactConverter.convert(artifactDTO);
        Artifact convertedArtifact = this.artifactService.addArtifact(newArtifact);
        ArtifactDTO savedArtifactDto = this.artifactToArtifactDTOConverter.convert(newArtifact);
        Result result = new Result(true, StatusCode.SUCCESS, "Add the new artifact successfully!", savedArtifactDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{artifactId}")
    public ResponseEntity<Result> updateArtifact (@PathVariable Integer artifactId, @Valid @RequestBody ArtifactDTO artifactDTO) {
        Artifact update = this.artifactDtoToArtifactConverter.convert(artifactDTO);
        Artifact updatedArtifact = this.artifactService.update(artifactId, update);
        ArtifactDTO updatedArtifactDto = this.artifactToArtifactDTOConverter.convert(updatedArtifact);
        Result result = new Result(true, StatusCode.SUCCESS, "Updated artifact successfully!", updatedArtifactDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{artifactId}")
    public Result deleteArtifact(@PathVariable Integer artifactId){
        this.artifactService.delete(artifactId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }
}
