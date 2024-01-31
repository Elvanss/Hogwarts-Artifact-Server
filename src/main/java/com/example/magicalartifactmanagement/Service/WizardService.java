package com.example.magicalartifactmanagement.Service;

import com.example.magicalartifactmanagement.Model.Wizard;
import com.example.magicalartifactmanagement.Repository.WizardRepository;
import com.example.magicalartifactmanagement.System.Exception.ArtifactNotFoundException;
import com.example.magicalartifactmanagement.System.Exception.WizardNotFoundException;
import org.hibernate.ObjectNotFoundException;

import java.util.List;

public class WizardService {

    private final WizardRepository wizardRepository;


    public WizardService(WizardRepository wizardRepository) {
        this.wizardRepository = wizardRepository;
    }

    // Find the wizard by ID
    public Wizard findWizardById(Integer wizardId) {
        return this.wizardRepository.findById(wizardId)
                .orElseThrow(()-> new WizardNotFoundException(wizardId));
    }

    // Find all the wizard from the list
    public List<Wizard> getAllWizard() {
        return wizardRepository.findAll();
    }

    // Add a new wizard into the list
    public Wizard addWizard(Wizard wizard) {
        wizard.setId(wizard.getId());
        wizard.setName(wizard.getName());
        return this.wizardRepository.save(wizard);

    }

    // Update the wizard information
//    public Wizard updateWizard(Integer wizardId, Wizard wizard) {
//        Wizard wizardObj = this.wizardRepository.findById(wizardId).get();
//        wizardObj.setId(wizard.getId());
//        wizardObj.setName(wizard.getName());
//        Wizard newWizard = this.wizardRepository.save(wizardObj);
//        //return the new updated wizard
//        return newWizard;
//    }

    public Wizard updateWizard(Integer wizardId, Wizard wizard) {
        return this.wizardRepository.findById(wizardId)
                .map(oldWizard ->{
                    oldWizard.setId(wizard.getId());
                    oldWizard.setName(wizard.getName());
                    Wizard updated = this.wizardRepository.save(oldWizard);
                    return updated;
                })
                .orElseThrow(() -> new ArtifactNotFoundException(wizardId));

    }

    // Delete a wizard
    public void delete(Integer wizardId) {
        this.wizardRepository.findById(wizardId)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find the wizard with number id!", wizardId));
        this.wizardRepository.deleteById(wizardId);
    }


}
