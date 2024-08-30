package com.shoppingmall.service;

import com.shoppingmall.domain.Category;
import com.shoppingmall.repository.CategoryRepository;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<Category> findAll() { return categoryRepository.findAll(); }

    public Map<String, List<String>> getCategoryTree() {
        List<Category> categories = categoryRepository.findAll();
        Map<String, List<String>> categoryTree = new HashMap<>();

        for (Category category : categories) {
            String[] parts = category.getTitle().split("/");

            if (parts.length == 2) {
                String parentCategory = parts[0];
                String subCategory = parts[1];

                // 부모 카테고리에 하위 카테고리 리스트 추가
                categoryTree
                        .computeIfAbsent(parentCategory, k -> new ArrayList<>())
                        .add(subCategory);
            }
        }

        return categoryTree;
    }

}
