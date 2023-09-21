package zhanuzak.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import zhanuzak.dto.request.RestaurantRequest;
import zhanuzak.enums.RestaurantType;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity

@Table(name = "restaurants")
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_seg")
    @SequenceGenerator(name = "restaurant_seg", allocationSize = 1)
    private Long id;
    private String name;
    private String location;
    @Column(name = "rest_type")
    @Enumerated(EnumType.STRING)
    private RestaurantType restType;
    @Column(name = "number_of_employees")
    private int numberOfEmployees;
    private int service;
    @OneToMany(mappedBy = "restaurant",cascade = ALL)
    private List<User> users;
    @OneToMany(mappedBy = "restaurant",cascade = ALL)
    private List<MenuItem> menuItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public RestaurantType getRestType() {
        return restType;
    }

    public void setRestType(RestaurantType restType) {
        this.restType = restType;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public static Restaurant build(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurantRequest.location());
        restaurant.setRestType(restaurantRequest.restType());
        restaurant.setNumberOfEmployees(restaurantRequest.numberOfEmployees());
        restaurant.setService(restaurantRequest.service());
        return restaurant;
    }

}
