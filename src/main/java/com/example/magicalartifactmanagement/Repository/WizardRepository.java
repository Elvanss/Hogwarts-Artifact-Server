package com.example.magicalartifactmanagement.Repository;

import com.example.magicalartifactmanagement.Model.Wizard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WizardRepository extends JpaRepository<Wizard, Integer> {

}
