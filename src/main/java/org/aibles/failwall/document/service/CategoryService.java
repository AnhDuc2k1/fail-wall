package org.aibles.failwall.document.service;

import org.aibles.failwall.document.dto.request.CategoryReqDto;
import org.aibles.failwall.document.dto.response.CategoryResDto;
import org.aibles.failwall.util.paging.PagingRes;

public interface CategoryService {

    CategoryResDto createCategory(CategoryReqDto newCategory);
    PagingRes getCategories(int pageNum, int pageSize);
    CategoryResDto deleteCategory(int id);
}
