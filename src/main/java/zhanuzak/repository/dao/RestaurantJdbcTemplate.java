package zhanuzak.repository.dao;

import zhanuzak.dto.response.RestaurantResponse;

import java.util.Optional;

public interface RestaurantJdbcTemplate {

    Optional<RestaurantResponse> getById(Long id);
}
