package zhanuzak.service;

import zhanuzak.dto.request.SubCategoryRequest;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.dto.response.SubCategoryResponse;

import java.util.List;

public interface SubCategoryService {
    List<SubCategoryResponse> getAll();

    SimpleResponse save(Long id, SubCategoryRequest subCategoryRequest);


    SubCategoryResponse getById(Long id);

    SimpleResponse update(Long id, SubCategoryRequest subCategoryRequest);

    SimpleResponse delete(Long id);
}
