package zhanuzak.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SubCategoryResponse{
    private Long id;
    private String name;
    private List<MenuItemResponse> menuItemResponses;

    public SubCategoryResponse(Long id, String name, List<MenuItemResponse> menuItemResponses) {
        this.id = id;
        this.name = name;
        this.menuItemResponses = menuItemResponses;
    }


    public SubCategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }


}
