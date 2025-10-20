package com.springboot.jpa.services;

import com.springboot.jpa.entities.Localization;
import com.springboot.jpa.repositories.LocalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalizationServiceImp implements LocalizationService {

    @Autowired
    private LocalizationRepository LCrepository;

    @Override
    public List<Localization> findAll() {
        return LCrepository.findAll();
    }

    @Override
    public Optional<Localization> findByInternCode(String internCode) {
        return LCrepository.findByInternCode(internCode);
    }


    @Override
    public Optional<Localization> findById(Long id) {
        return LCrepository.findById(id);
    }

    @Override
    public Localization save(Localization localization) {
        return LCrepository.save(localization);
    }

    @Override
    public Optional<Localization> update(Long id, Localization localization) {
        Optional<Localization> localizationOptional = LCrepository.findById(id);
        if (localizationOptional.isPresent()) {
            Localization lcDB = localizationOptional.orElseThrow();

            lcDB.setInternCode(localization.getInternCode());
            return Optional.of(LCrepository.save(lcDB));
        }
        return localizationOptional;
    }

    @Override
    public Optional<Localization> delete(Long id) {
        Optional<Localization> localizationOptional = LCrepository.findById(id);
        localizationOptional.ifPresent(lcDB -> {
            LCrepository.delete(lcDB);
        });
        return localizationOptional;
    }
}
