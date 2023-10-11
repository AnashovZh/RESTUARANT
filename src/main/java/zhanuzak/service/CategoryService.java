package zhanuzak.service;

import zhanuzak.dto.request.CategoryRequest;
import zhanuzak.dto.response.CategoryResponse;
import zhanuzak.dto.response.SimpleResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAll();

    SimpleResponse save(CategoryRequest categoryRequest);

    CategoryResponse getById(Long id);

    SimpleResponse update(Long id, CategoryRequest categoryRequest);

    SimpleResponse delete(Long id);

    CategoryResponse getByIdWithSubCategory(Long id);
}
