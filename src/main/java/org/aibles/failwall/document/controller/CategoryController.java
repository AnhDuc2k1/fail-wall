package org.aibles.failwall.document.controller;


import org.aibles.failwall.document.dto.request.CategoryReqDto;
import org.aibles.failwall.document.dto.response.CategoryResDto;
import org.aibles.failwall.document.service.CategoryService;
import org.aibles.failwall.util.paging.PagingRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<PagingRes> getCategories(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
               return new ResponseEntity<>(categoryService.getCategories(pageNum, pageSize), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryResDto> createCategory(@RequestBody CategoryReqDto categoryReqDto){
        return new ResponseEntity<>(categoryService.createCategory(categoryReqDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResDto> deleteCategory(@PathVariable int id){
        return new ResponseEntity<>(categoryService.deleteCategory(id), HttpStatus.OK);
    }
}
