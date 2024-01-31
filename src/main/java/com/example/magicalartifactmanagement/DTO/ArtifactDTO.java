package com.example.magicalartifactmanagement.DTO;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

//also create the class instead of record
public record ArtifactDTO (Integer id,
                           @NotEmpty(message = "Name is required")
                                   @Length(min =1, max = 16)
                           String name,
                           @NotEmpty
                           String description,
                           @NotEmpty
                           String imageUrl,
                           WizardDTO owner) {
}
