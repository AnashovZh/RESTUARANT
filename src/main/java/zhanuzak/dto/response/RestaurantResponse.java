package zhanuzak.dto.response;

import lombok.*;
import zhanuzak.enums.RestaurantType;
import zhanuzak.models.Restaurant;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor


public class RestaurantResponse {//r.id,r.name,r.location,r.restType,r.numberOfEmployees,r.service
    private Long id;
    private String name;
    private String location;
    private RestaurantType restType;
    private int numberOfEmployees;
    private BigDecimal service;

    public RestaurantResponse(Long id, String name, String location,
                              RestaurantType restType, int numberOfEmployees,
                              BigDecimal service) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.restType = restType;
        this.numberOfEmployees = numberOfEmployees;
        this.service = service;
    }

    public RestaurantResponse build(Restaurant restaurant) {
        RestaurantResponse restaurantResponse = new RestaurantResponse();
        restaurantResponse.setId(restaurant.getId());
        restaurantResponse.setName(restaurant.getName());
        restaurantResponse.setLocation(restaurant.getLocation());
        restaurantResponse.setRestType(restaurant.getRestType());
        restaurantResponse.setNumberOfEmployees(restaurant.getNumberOfEmployees());
        restaurantResponse.setService(restaurant.getService());
        return restaurantResponse;
    }
}
