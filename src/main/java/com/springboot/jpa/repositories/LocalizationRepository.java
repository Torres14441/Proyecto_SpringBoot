package com.springboot.jpa.repositories;

import com.springboot.jpa.entities.Localization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalizationRepository extends JpaRepository<Localization,Long> {
    Optional<Localization> findByInternCode(String internCode);
}
