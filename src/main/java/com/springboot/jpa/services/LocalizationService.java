package com.springboot.jpa.services;

import com.springboot.jpa.entities.Localization;

import java.util.List;
import java.util.Optional;

public interface LocalizationService {
    List<Localization> findAll();
    Optional<Localization> findByInternCode(String internCode);
    Optional<Localization> findById(Long id);
    Localization save(Localization product);
    Optional<Localization> update(Long id,Localization localization);
    Optional<Localization> delete(Long id);
}
