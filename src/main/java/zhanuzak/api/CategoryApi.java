package zhanuzak.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zhanuzak.dto.request.CategoryRequest;
import zhanuzak.dto.response.CategoryResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryApi {

    //    @Qualifier("CImpl")
    private final CategoryService categoryService;

    @PermitAll
    @GetMapping
    List<CategoryResponse> getAll() {
        System.out.println();
        return categoryService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping
    SimpleResponse save(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.save(categoryRequest);
    }

    @PermitAll
    @GetMapping("/{id}")
    CategoryResponse getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PermitAll
    @GetMapping("/withSubCategory/{id}")
    CategoryResponse getByIdWithSubCategory(@PathVariable Long id) {
        return categoryService.getByIdWithSubCategory(id);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{id}")
    SimpleResponse update(@PathVariable Long id,
                          @RequestBody CategoryRequest categoryRequest) {
        return categoryService.update(id, categoryRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse delete(@PathVariable Long id) {
        return categoryService.delete(id);
    }
}
