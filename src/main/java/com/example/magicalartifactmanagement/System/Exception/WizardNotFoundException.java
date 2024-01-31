package com.example.magicalartifactmanagement.System.Exception;

public class WizardNotFoundException extends RuntimeException{
    public WizardNotFoundException(Integer id) {
        super("There is no valid wizard with ID " + id + "!");
    }
}
