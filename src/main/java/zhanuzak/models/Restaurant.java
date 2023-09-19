package zhanuzak.models;

import jakarta.persistence.*;
import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zhanuzak.enums.RestaurantType;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "restaurants")
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "restaurant_seg")
    @SequenceGenerator(name = "restaurant_seg",allocationSize = 1)
    private Long id;
    private String name;
    private String location;
    @Column(name = "rest_type")
    @Enumerated(EnumType.STRING)
    private RestaurantType restType;
    @Column(name = "number_of_employees")
    private int numberOfEmployees;
    private int service;
    @OneToMany(mappedBy = "restaurant")
    private List<User> users;
    @OneToMany(mappedBy = "restaurant")
    private List<MenuItem>menuItems;




}
