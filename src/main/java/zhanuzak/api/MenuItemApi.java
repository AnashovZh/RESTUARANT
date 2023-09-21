package zhanuzak.api;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import zhanuzak.dto.request.MenuItemRequest;
import zhanuzak.dto.response.MenuItemResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.service.MenuItemService;

import java.util.List;

@RestController
@RequestMapping("/api/menuItems")
@RequiredArgsConstructor
public class MenuItemApi {
    private final MenuItemService menuItemService;

    @GetMapping
    @PermitAll
    List<MenuItemResponse> getAll() {
        return menuItemService.getAll();
    }

    @GetMapping("/{id}")
    @PermitAll
    MenuItemResponse getById(@PathVariable Long id) {
        return menuItemService.getById(id);
    }

    @PostMapping("/subCategory/{id}")
    @Secured({"ADMIN", "CHEF"})
    SimpleResponse save(@RequestBody @Valid MenuItemRequest menuItemRequest,
                        @PathVariable Long id) {
        return menuItemService.save(menuItemRequest, id);
    }

    @PutMapping("/{id}")
    @Secured("ADMIN")
    SimpleResponse update(@PathVariable Long id,
                          @RequestBody @Valid MenuItemRequest menuItemRequest) {
        return menuItemService.update(id, menuItemRequest);
    }

    @Secured("ADMIN")
    @DeleteMapping("/{id}")
    SimpleResponse delete(@PathVariable Long id){
        return menuItemService.delete(id);
    }

}
