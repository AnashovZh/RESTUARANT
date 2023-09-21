package zhanuzak.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zhanuzak.dto.request.SubCategoryRequest;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.dto.response.SubCategoryResponse;
import zhanuzak.exceptions.exception.NotFoundException;
import zhanuzak.models.Category;
import zhanuzak.models.SubCategory;
import zhanuzak.repository.CategoryRepository;
import zhanuzak.repository.SubCategoryRepository;
import zhanuzak.repository.dao.SubCategoryJdbcTemplate;
import zhanuzak.service.SubCategoryService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryJdbcTemplate subCategoryJdbcTemplate;

    @Override
    public List<SubCategoryResponse> getAll() {
        return subCategoryRepository.getAll();
    }

    @Override
    public SimpleResponse save(Long id, SubCategoryRequest subCategoryRequest) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Category with id:" + id + " not found!!!"));
        SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryRequest.getName());
        subCategory.setCategory(category);
        SubCategory save = subCategoryRepository.save(subCategory);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved subCategory with name:" + save.getName())
                .build();
    }

    @Override
    public SubCategoryResponse getById(Long id) {
        return subCategoryJdbcTemplate.findById(id).orElseThrow(() ->
                new NotFoundException("SubCategory with id:" + id + " not found !!!"));

    }

    @Override
    public SimpleResponse update(Long id, SubCategoryRequest subCategoryRequest) {
        return subCategoryJdbcTemplate.update(id, subCategoryRequest);
    }

    @Override
    public SimpleResponse delete(Long id) {
        return subCategoryJdbcTemplate.delete(id);
    }
}
