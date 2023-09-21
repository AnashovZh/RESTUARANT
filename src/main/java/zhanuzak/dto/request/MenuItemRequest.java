package zhanuzak.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zhanuzak.models.MenuItem;

import java.math.BigDecimal;
@Builder
@NoArgsConstructor
@Getter
@Setter
public class MenuItemRequest {
    @NotNull
    private String name;

    private String image;
    @NotNull
    private BigDecimal price;
    @NotNull
    private String description;
    @NotNull
    private boolean isVegetarian;

    public MenuItemRequest(@NotNull String name, String image, @NotNull BigDecimal price, @NotNull String description, @NotNull boolean isVegetarian) {

        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegetarian = isVegetarian;
    }
    public static MenuItemRequest build(MenuItem menuItem){
        MenuItemRequest menuItemRequest=new MenuItemRequest();
        menuItemRequest.setName(menuItem.getName());
        menuItemRequest.setImage(menuItem.getImage());
        menuItemRequest.setPrice(menuItem.getPrice());
        menuItemRequest.setDescription(menuItem.getDescription());
        menuItemRequest.setVegetarian(menuItem.isVegetarian());
        return menuItemRequest;
    }
}
