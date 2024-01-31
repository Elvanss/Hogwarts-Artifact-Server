package com.example.magicalartifactmanagement.DTO.ClassConverter;

import com.example.magicalartifactmanagement.DTO.ArtifactDTO;
import com.example.magicalartifactmanagement.DTO.WizardDTO;
import com.example.magicalartifactmanagement.Model.Wizard;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WizardToWizardDTOConverter implements Converter<Wizard, WizardDTO> {


    @Override
    public WizardDTO convert(Wizard source) {
        WizardDTO wizardDTO = new WizardDTO(source.getId(),
                                            source.getName(),
                                            source.getNumberOfArtifacts());
        return wizardDTO;
    }

}
