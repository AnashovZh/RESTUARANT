package zhanuzak.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private List<SubCategoryResponse>subCategoryResponses;
    public CategoryResponse(Long id, String name, List<SubCategoryResponse> subCategoryResponses) {
        this.id = id;
        this.name = name;
        this.subCategoryResponses = subCategoryResponses;
    }
    public CategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
