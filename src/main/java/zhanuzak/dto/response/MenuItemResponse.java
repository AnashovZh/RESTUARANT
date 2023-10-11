package zhanuzak.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zhanuzak.models.MenuItem;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
public class MenuItemResponse {
    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
    private String description;
    private boolean isVegetarian;
    private String categoryName;
    private String subCategoryName;

    public MenuItemResponse(Long id, String name, String image, BigDecimal price, String description, boolean isVegetarian, String categoryName, String subCategoryName) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegetarian = isVegetarian;
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
    }

    public MenuItemResponse(Long id, String name, String image, BigDecimal price, String description, boolean isVegetarian) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegetarian = isVegetarian;
    }

    public MenuItemResponse() {
    }

    public static MenuItemResponse buildStatic(MenuItem menuItem){
        MenuItemResponse menuItemResponse=new MenuItemResponse();
        menuItemResponse.setId(menuItem.getId());
        menuItemResponse.setName(menuItem.getName());
        menuItemResponse.setImage(menuItem.getImage());
        menuItemResponse.setPrice(menuItem.getPrice());
        menuItemResponse.setDescription(menuItem.getDescription());
        menuItemResponse.setVegetarian(menuItem.isVegetarian());
        return menuItemResponse;
    }
}
