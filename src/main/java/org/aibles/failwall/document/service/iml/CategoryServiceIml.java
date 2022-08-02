package org.aibles.failwall.document.service.iml;

import org.aibles.failwall.document.dto.request.CategoryReqDto;
import org.aibles.failwall.document.dto.response.CategoryResDto;
import org.aibles.failwall.document.model.Category;
import org.aibles.failwall.document.repository.CategoryRepository;
import org.aibles.failwall.document.service.CategoryService;
import org.aibles.failwall.exception.FailWallBusinessException;
import org.aibles.failwall.util.paging.PagingRes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceIml implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceIml(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryResDto createCategory(CategoryReqDto newCategory) {
        Category category = modelMapper.map(newCategory, Category.class);
        categoryRepository.save(category);

        return modelMapper.map(category, CategoryResDto.class);
    }

    @Override
    public PagingRes getCategories(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        return PagingRes.of(categoryPage.map(category -> modelMapper.map(category, CategoryResDto.class)));
    }

    @Override
    public CategoryResDto deleteCategory(int id) {
        return categoryRepository.findById(id)
                .map(category -> modelMapper.map(category, CategoryResDto.class))
                .orElseThrow(() -> new FailWallBusinessException("Category Not Found", HttpStatus.NOT_FOUND));
    }
}
