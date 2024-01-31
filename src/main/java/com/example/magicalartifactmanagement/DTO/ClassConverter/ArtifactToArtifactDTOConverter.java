package com.example.magicalartifactmanagement.DTO.ClassConverter;

import com.example.magicalartifactmanagement.DTO.ArtifactDTO;
import com.example.magicalartifactmanagement.Model.Artifact;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtifactToArtifactDTOConverter implements Converter<Artifact, ArtifactDTO> {

    private final WizardToWizardDTOConverter wizardToWizardDTOConverter;

    public ArtifactToArtifactDTOConverter(WizardToWizardDTOConverter wizardToWizardDTOConverter) {
        this.wizardToWizardDTOConverter = wizardToWizardDTOConverter;
    }

    @Override
    public ArtifactDTO convert(Artifact source) {
        ArtifactDTO artifactDTO = new ArtifactDTO(
                source.getId(),
                source.getName(),
                source.getDescription(),
                source.getImageUrl(),
                source.getOwner() != null
                        ? this.wizardToWizardDTOConverter.convert(source.getOwner())
                        : null
        );
        return artifactDTO;
    }
}

