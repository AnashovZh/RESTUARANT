package zhanuzak.repository.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.response.MenuItemResponse;
import zhanuzak.exceptions.exception.NotFoundException;
import zhanuzak.repository.dao.MenuItemJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MenuItemJdbcTemplateImpl implements MenuItemJdbcTemplate {
    private final JdbcTemplate jdbcTemplate;

    private MenuItemResponse rowMapper(ResultSet rs, int rowName) throws SQLException {

        return new MenuItemResponse(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("image"),
                rs.getBigDecimal("price"),
                rs.getString("description"),
                rs.getBoolean("isVegetarian"));

    }

    @Override
    public List<MenuItemResponse> getAll() {
        String sql = """
                select id,name ,image ,price ,description 
                , is_vegetarian as isVegetarian from menu_items 
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return new MenuItemResponse(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("image"),
                    rs.getBigDecimal("price"),
                    rs.getString("description"),
                    rs.getBoolean("isVegetarian"));
        });
    }

    @Override
    public Optional<MenuItemResponse> getById(Long id) {
        String sql= """
                 select id as id, name as name, image as image, price as price,description 
                 as description, is_vegetarian as isVegetarian from menu_items where id=?
                """;
        return jdbcTemplate.query(sql,this::rowMapper).stream().findFirst();
    }
}
