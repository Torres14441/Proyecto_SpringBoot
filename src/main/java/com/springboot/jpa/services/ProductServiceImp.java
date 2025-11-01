package com.springboot.jpa.services;

import com.springboot.jpa.entities.Category;
import com.springboot.jpa.entities.Product;
import com.springboot.jpa.entities.Type_Inventory;
import com.springboot.jpa.repositories.CategoriesRepository;
import com.springboot.jpa.repositories.ProductRepository;
import com.springboot.jpa.repositories.TypeInventoryRepository;
import jakarta.persistence.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImp implements ProductService {

    private final ProductRepository repository;

    private final TypeInventoryRepository tirepository;

    private final CategoriesRepository crepository;

    public ProductServiceImp(ProductRepository repository, TypeInventoryRepository tirepository, CategoriesRepository crepository) {
        this.repository = repository;
        this.tirepository = tirepository;
        this.crepository = crepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findByCpr(String code) {
        return repository.findByCpr(code);
    }

    @Override
    @Transactional
    public Product save(Product product) {

        String tipoP = product.getType().getName();
        Double price = validatePrice(product.getPrice());
        Type_Inventory tyexistent = validarTipo(tipoP);
        product.setType(tyexistent);
        product.setPrice(price);


        List<Category> categorias = validarCategorias(product.getCategories());
        product.setCategories(categorias);
        log.info("saving product into database");
        return repository.save(product);
    }

    @Override
    @Transactional
    public Optional<Product> update(Long id, Product product) {
        Optional <Product> productOpt = repository.findById(id);
        if(productOpt.isPresent()){
            Product productDB =productOpt.orElseThrow();


            productDB.setCpr(product.getCpr());
            productDB.setName(product.getName());
            productDB.setDescription(product.getDescription());
            Double price = validatePrice(product.getPrice());
            productDB.setPrice(price);


            String tipoP = product.getType().getName();
            Type_Inventory tyexistent = validarTipo(tipoP);
            productDB.setType(tyexistent);


            List<Category> categories = validarCategorias(product.getCategories());
            productDB.setCategories(categories);
            log.info("Updating product with id: {}", id);
            return Optional.of(repository.save(productDB));

        }
        return productOpt;
    }

    @Override
    @Transactional
    public Optional<Product> delete(Long id) {
        Optional<Product> productOptional = repository.findById(id);
        productOptional.ifPresent(productdb -> {
            log.info("Deleting product with id: {}", id);
            repository.delete(productdb);});
        return productOptional;
    }

    private Type_Inventory validarTipo(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre del tipo de inventario no puede estar vacío");
        }

        return tirepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("El tipo de inventario '%s' no existe en el sistema", name)
                ));
    }
    private List<Category> validarCategorias(List<Category> recibidas) {
        List<Category> validadas = new ArrayList<>();

        for (Category cat : recibidas) {
            String nombre = cat.getName();
            if (nombre == null || nombre.isBlank()) {
                throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío");
            }

            Category existente = crepository.findByName(nombre)
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format("La categoría '%s' no existe en el sistema", nombre)
                    ));

            validadas.add(existente);
        }

        return validadas;
    }
    private Double validatePrice(Double price) {
        if (price == null) {
            throw new IllegalArgumentException("El precio no puede ser nulo.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero.");
        }
        return price;
    }
}


