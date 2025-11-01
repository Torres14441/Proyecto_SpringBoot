package com.springboot.jpa.services;

import com.springboot.jpa.entities.Localization;
import com.springboot.jpa.repositories.LocalizationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LocalizationServiceImp implements LocalizationService {


    private final LocalizationRepository lcrepository;

    public LocalizationServiceImp( LocalizationRepository lcrepository) {
        this.lcrepository = lcrepository;
    }
    @Override
    public List<Localization> findAll() {
        return lcrepository.findAll();
    }

    @Override
    public Optional<Localization> findByInternCode(String internCode) {
        return lcrepository.findByInternCode(internCode);
    }


    @Override
    public Optional<Localization> findById(Long id) {
        return lcrepository.findById(id);
    }

    @Override
    public Localization save(Localization localization) {
        log.info("saving localization into database");
        return lcrepository.save(localization);
    }

    @Override
    public Optional<Localization> update(Long id, Localization localization) {
        Optional<Localization> localizationOptional = lcrepository.findById(id);
        if (localizationOptional.isPresent()) {
            Localization lcDB = localizationOptional.orElseThrow();

            lcDB.setInternCode(localization.getInternCode());
            log.info("Updating inventory with id: {}", id);
            return Optional.of(lcrepository.save(lcDB));
        }
        return localizationOptional;
    }

    @Override
    public Optional<Localization> delete(Long id) {
        Optional<Localization> localizationOptional = lcrepository.findById(id);
        localizationOptional.ifPresent(lcDB -> {
            log.info("Deleting localization with id: {}", id);
            lcrepository.delete(lcDB);
        });
        return localizationOptional;
    }
}
