package zhanuzak.repository.dao;

import zhanuzak.dto.request.SubCategoryRequest;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.dto.response.SubCategoryResponse;
import zhanuzak.models.SubCategory;

import java.util.Optional;

public interface SubCategoryJdbcTemplate {
    Optional<SubCategoryResponse>findById(Long id);
    SimpleResponse update(Long id, SubCategoryRequest subCategoryRequest);


    SimpleResponse delete(Long id);
}
