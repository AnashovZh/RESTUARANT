package zhanuzak.service;

import zhanuzak.dto.request.RestaurantRequest;
import zhanuzak.dto.response.RestaurantResponse;
import zhanuzak.dto.response.SimpleResponse;

import java.util.List;

public interface RestaurantService {
    List<RestaurantResponse> getAll();

    SimpleResponse save(RestaurantRequest restaurantRequest);

    RestaurantResponse getById(Long id);
}
