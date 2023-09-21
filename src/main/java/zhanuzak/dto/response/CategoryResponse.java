package zhanuzak.dto.response;

import lombok.Getter;
import lombok.Setter;
import zhanuzak.models.Category;
@Getter
@Setter
public class CategoryResponse {
    private Category category;

    public CategoryResponse(Category category) {
        this.category = category;
    }
}
