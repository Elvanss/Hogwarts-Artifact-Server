package com.example.magicalartifactmanagement.System.State;

import com.example.magicalartifactmanagement.DTO.ArtifactDTO;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private boolean flag; // Two values: true means success, false means not success

    private Integer code; // Status code. e.g., 200

    private String message; // Response message

    private Object data; // The response payload

    public Result(boolean b, int notFound, String message) {}

    /* Optional */
//    private ResponseEntity<Result> responseEntity;
//
//    public Result(boolean b, int success, String s, ArtifactDTO savedArtifactDto) {
//    }
//
//    public Result(boolean b, int success, String findAllSuccess, List<ArtifactDTO> convertedDTOs) {
//    }
//
//    public ResponseEntity<Result> toResponseEntity() {
//        return this.responseEntity;
//    }
}
