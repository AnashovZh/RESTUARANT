package zhanuzak.repository.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.response.RestaurantResponse;
import zhanuzak.enums.RestaurantType;
import zhanuzak.repository.dao.RestaurantJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RestaurantJdbcTemplateImpl implements RestaurantJdbcTemplate {
    private final JdbcTemplate jdbcTemplate;

    private RestaurantResponse rowMapper(ResultSet rs, int rowName) throws SQLException {
        String restTypeStr = rs.getString("restType");
        RestaurantType restType = RestaurantType.valueOf(restTypeStr);
        return new RestaurantResponse(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("location"),
                restType,
                rs.getInt("numberOfEmployees"),
                rs.getBigDecimal("service"));
    }
    @Override
    public Optional<RestaurantResponse> getById(Long id) {
        String sql = """
                select id ,name as name,location as location,rest_type as restType,
                number_of_employees as numberOfEmployees,service as service from restaurants r where r.id=?
                """;
        return jdbcTemplate.query(sql, this::rowMapper, id).stream().findFirst();
    }
}
