package com.example.magicalartifactmanagement.System.Exception;

public class ArtifactNotFoundException extends RuntimeException {

    public ArtifactNotFoundException(Integer id) {
        super("Could not find the artifact with id: " + id + "!");
    }
}