package zhanuzak.repository.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.request.SubCategoryRequest;
import zhanuzak.dto.response.CategoryResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.dto.response.SubCategoryResponse;
import zhanuzak.repository.dao.SubCategoryJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SubCategoryJdbcTemplateImpl implements SubCategoryJdbcTemplate {
    private final JdbcTemplate jdbcTemplate;

    private SubCategoryResponse rowMapper(ResultSet rs, int rowName) throws SQLException {
        Long subcategoryId = rs.getLong("subcategory_id");
        String subcategoryName = rs.getString("subcategory_name");
        Long categoryId = rs.getLong("category_id");
        String categoryName = rs.getString("category_name");

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(categoryId);
        categoryResponse.setName(categoryName);

//        return new SubCategoryResponse(subcategoryId, subcategoryName, categoryResponse);
        return null;
    }

    @Override
    public Optional<SubCategoryResponse> findById(Long id) {
        String sql = """
                SELECT s.id AS subcategory_id, s.name AS subcategory_name, c.id AS 
                 category_id, c.name AS category_name
                FROM sub_categories s JOIN categories c ON s.category_id = c.id
                WHERE s.id = ?
                """;

        return jdbcTemplate.query(sql, this::rowMapper, id).stream().findFirst();
    }


    @Override
    public SimpleResponse update(Long id, SubCategoryRequest subCategoryRequest) {
        String sql = """
                update sub_categories set name=? where id=?
                """;
        int a = jdbcTemplate.update(sql, subCategoryRequest.getName(), id);
        if (a == 1) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("SubCategory with id:" + id + " successfully updated ☺")
                    .build();
        } else {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("SubCategory with id:" + id + " not found")
                    .build();
        }
    }

    @Override
    public SimpleResponse delete(Long id) {
        String sql = "delete from sub_categories where id=?";
        jdbcTemplate.update(sql, id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("SubCategory with id:" + id + " successfully deleted ☺")
                .build();
    }
}
