package com.enenim.scaffold.service.dao;


import com.enenim.scaffold.model.dao.Category;
import com.enenim.scaffold.repository.dao.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public List<Category> getCategories(List<Long> ids){
        return categoryRepository.findAllById(ids);
    }

    public Category getCategory(Long id){
        return categoryRepository.findOrFail(id);
    }
}
