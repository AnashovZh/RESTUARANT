package zhanuzak.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zhanuzak.dto.request.RestaurantRequest;
import zhanuzak.dto.response.RestaurantResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.exceptions.exception.NotFoundException;
import zhanuzak.models.Restaurant;
import zhanuzak.repository.RestaurantRepository;
import zhanuzak.repository.dao.RestaurantJdbcTemplate;
import zhanuzak.service.RestaurantService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantJdbcTemplate restaurantJdbcTemplate;

    @Override
    public List<RestaurantResponse> getAll() {
        return restaurantRepository.getAll();
    }

    @Override
    public SimpleResponse save( RestaurantRequest restaurantRequest) {
        Restaurant restaurant=new Restaurant();
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurantRequest.location());
        restaurant.setRestType(restaurantRequest.restType());
        restaurant.setNumberOfEmployees(restaurantRequest.numberOfEmployees());
        restaurant.setService(restaurantRequest.service());
        Restaurant save = restaurantRepository.save(restaurant);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Restaurant with name:" + save.getName() + " successfully saved ☺")
                .build();
    }

    @Override
    public RestaurantResponse getById(Long id) {
        return restaurantJdbcTemplate.getById(id).orElseThrow(()->
                new NotFoundException("Restaurant with id:"+id+" not found !!!"));
    }
}
