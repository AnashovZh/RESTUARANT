package zhanuzak.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import zhanuzak.dto.request.SubCategoryRequest;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.dto.response.SubCategoryResponse;
import zhanuzak.service.SubCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/subCategories")
@RequiredArgsConstructor
public class SubCategoryApi {
    private final SubCategoryService subCategoryService;

    @PermitAll
    @GetMapping
    List<SubCategoryResponse> getAll() {
        return subCategoryService.getAll();
    }

    @Secured({"ADMIN", "CHEF"})
    @PostMapping("/{id}")
    SimpleResponse save(@PathVariable Long id,
                        @RequestBody SubCategoryRequest subCategoryRequest) {
        return subCategoryService.save(id, subCategoryRequest);
    }

    @PermitAll
    @GetMapping("/{id}")
    SubCategoryResponse getById(@PathVariable Long id) {
        return subCategoryService.getById(id);
    }

    @Secured({"ADMIN", "CHEF"})
    @PutMapping("/{id}")
    SimpleResponse update(@PathVariable Long id,
                          @RequestBody SubCategoryRequest subCategoryRequest) {
        return subCategoryService.update(id, subCategoryRequest);
    }

    @Secured("ADMIN")
    @DeleteMapping("/{id}")
    SimpleResponse delete(@PathVariable Long id) {
        return subCategoryService.delete(id);
    }


}
