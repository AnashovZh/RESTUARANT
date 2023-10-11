package zhanuzak.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import zhanuzak.dto.request.MenuItemRequest;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "menu_items")
@NoArgsConstructor

public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_item_seq")
    @SequenceGenerator(name = "menu_item_seq", allocationSize = 1)
    private Long id;
    @Column(unique = true)
    private String name;
    private String image;
    private BigDecimal price;
    private String description;
    @Column(name = "is_vegetarian")
    private boolean isVegetarian;
    @ManyToOne
    private Restaurant restaurant;
    @ManyToMany(cascade = {MERGE, REFRESH, DETACH})
    private List<Cheque> cheques;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.PERSIST, CascadeType.REFRESH})
    private SubCategory subCategory;
    @OneToOne(mappedBy = "menuItem", cascade = {CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.PERSIST, CascadeType.REFRESH})
    private StopList stopList;

    public MenuItem(Long id, String name, String image, BigDecimal price,
                    String description, boolean isVegetarian) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegetarian = isVegetarian;
    }

    public static MenuItem buildStatic(MenuItemRequest menuItemRequest) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemRequest.getName());
        menuItem.setImage(menuItemRequest.getImage());
        menuItem.setPrice(menuItemRequest.getPrice());
        menuItem.setDescription(menuItemRequest.getDescription());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        return menuItem;
    }

    public MenuItem build(MenuItemRequest menuItemRequest) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemRequest.getName());
        menuItem.setImage(menuItemRequest.getImage());
        menuItem.setPrice(menuItemRequest.getPrice());
        menuItem.setDescription(menuItemRequest.getDescription());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        return menuItem;
    }


}