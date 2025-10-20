package com.springboot.jpa.services;

import com.springboot.jpa.entities.Inventory;
import com.springboot.jpa.entities.Localization;
import com.springboot.jpa.entities.Product;
import com.springboot.jpa.repositories.InventoryRepository;
import com.springboot.jpa.repositories.LocalizationRepository;
import com.springboot.jpa.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class InventoryServiceImp implements InventoryService {

    @Autowired
    private InventoryRepository invRepository;

    @Autowired
    private LocalizationRepository localizationRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Inventory> findAll() {
        return invRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Inventory> findById(Long id) {
        return  invRepository.findById(id);
    }

    @Override
    public Optional<Inventory> findByProductId(Long product) {
        return invRepository.findByProductId(product);
    }

    @Override
    @Transactional
    public Inventory save(Inventory inventory) {
        String cpr = inventory.getProduct().getCpr();
        Product productValidated = validateProductByCpr(cpr);
        inventory.setProduct(productValidated);


        String interncode = inventory.getLocalization().getInternCode();
        Localization localization = validateLocalization(interncode);
        inventory.setLocalization(localization);
        return invRepository.save(inventory);
    }

    @Override
    @Transactional
    public Optional<Inventory> update(Long id, Inventory inventory) {
        Optional<Inventory> invOptional = invRepository.findById(id);
        if(invOptional.isPresent()){
            Inventory invDB = invOptional.orElseThrow();
            String cpr = inventory.getProduct().getCpr();
            Product productValidated = validateProductByCpr(cpr);
            invDB.setProduct(productValidated);

            String interncode = inventory.getLocalization().getInternCode();
            Localization localization = validateLocalization(interncode);
            invDB.setLocalization(localization);


            invDB.setStockMin(inventory.getStockMin());
            invDB.setStockActual(inventory.getStockActual());

            return Optional.of(invRepository.save(invDB));
        }
        return invOptional;
    }

    @Override
    @Transactional
    public Optional<Inventory> delete(Long id) {
        Optional<Inventory> invOptional = invRepository.findById(id);
        invOptional.ifPresent(inv -> {
            invRepository.delete(inv);
        });
        return invOptional;
    }

    private Localization validateLocalization(String internCode) {
        if (internCode == null || internCode.isBlank()) {
            throw new IllegalArgumentException("La localización no puede estar vacía");
        }

        return localizationRepository.findByInternCode(internCode)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("La localización con código interno '%s' no existe en el sistema", internCode)
                ));
    }


    private Product validateProductByCpr(String cpr) {
        if (cpr == null || cpr.isBlank()) {
            throw new IllegalArgumentException("El CPR del producto no puede estar vacío.");
        }

        return productRepository.findByCpr(cpr)
                .orElseThrow(() -> new IllegalArgumentException("No existe un producto con CPR: " + cpr));
    }


}
