package zhanuzak.repository.dao;

import zhanuzak.dto.request.RestaurantRequest;
import zhanuzak.dto.response.RestaurantResponse;
import zhanuzak.dto.response.SimpleResponse;

import java.util.Optional;

public interface RestaurantJdbcTemplate {

    Optional<RestaurantResponse> getById(Long id);
}
