package zhanuzak.repository.dao.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.response.GlobalSearchResponse;
import zhanuzak.dto.response.MenuItemResponse;
import zhanuzak.exceptions.exception.NotFoundException;
import zhanuzak.repository.dao.MenuItemJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
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
        String sql = """
                 select id as id, name as name, image as image, price as price,description 
                 as description, is_vegetarian as isVegetarian from menu_items where id=?
                """;
        return jdbcTemplate.query(sql, this::rowMapper).stream().findFirst();
    }

    private GlobalSearchResponse rowMapper2(ResultSet rs, int rowName) throws SQLException {

        return new GlobalSearchResponse(
                rs.getLong("id"),
                rs.getString("menuItemName"),
                rs.getBigDecimal("price"),
                rs.getString("categoryName"),
                rs.getString("subCategoryName")
        );
    }

    @Override
    public GlobalSearchResponse globalSearch2(String search) {
        String sql = """
                SELECT
                    m.id AS id,
                    m.name AS menuItemName,
                    m.price AS price,
                    c.name AS categoryName,
                    s.name AS subCategoryName
                FROM
                    menu_items m
                JOIN
                    sub_categories s ON m.sub_category_id = s.id
                JOIN
                    categories c ON s.category_id = c.id
                WHERE
                    m.name LIKE :search
                ;
                """;
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("search", "%" + search + "%");


        return jdbcTemplate.query(sql, this::rowMapper2).stream().findFirst().orElseThrow(() ->
                new NotFoundException("Not found menuItem with name" + search));
    }

    @Override
    public List<MenuItemResponse> searchByName(String search) {

        String sql = "SELECT id, name, image, price, description, is_vegetarian " +
                "FROM menuItems " +
                "WHERE name ILIKE :word";

        RowMapper<MenuItemResponse> rowMapper = (rs, rowNum) -> MenuItemResponse.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .image(rs.getString("image"))
                .price(rs.getBigDecimal("price"))
                .description(rs.getString("description"))
                .isVegetarian(rs.getBoolean("is_vegetarian"))
                .build();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("word", "%" + search + "%");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate =
                new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }
}

