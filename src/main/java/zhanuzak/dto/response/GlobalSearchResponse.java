package zhanuzak.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
public class GlobalSearchResponse {
    private Long id;
    private String menuItemName;
    private BigDecimal price;
    private String categoryName;
    private String subCategoryName;

    public GlobalSearchResponse(Long id,String menuItemName, BigDecimal price,
                                String categoryName, String subCategoryName) {
        this.id=id;
        this.menuItemName = menuItemName;
        this.price = price;
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
    }
}
