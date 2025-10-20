package com.springboot.jpa.services;

import com.springboot.jpa.entities.Category;
import com.springboot.jpa.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieServiceImp  implements CategorieService {
    @Autowired
    private CategoriesRepository catrepository;


    @Override
    public List<Category> findAll() {
        return catrepository.findAll();
    }

    @Override
    public Optional<Category> findByName(String name) {
        return catrepository.findByName(name);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return catrepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return catrepository.save(category);
    }

    @Override
    public Optional<Category> update(Long id, Category category) {
        Optional<Category> optionalCat = findById(id);
        if (optionalCat.isPresent()) {
            Category catdb = optionalCat.orElseThrow();
            catdb.setName(category.getName());

            return Optional.of(catrepository.save(catdb));
        }
        return optionalCat;
    }

    @Override
    public Optional<Category> delete(Long id) {
        Optional<Category> optionalCat = findById(id);
        optionalCat.ifPresent(catdb ->{
            catrepository.delete(catdb);
        });
        return optionalCat;
    }
}
