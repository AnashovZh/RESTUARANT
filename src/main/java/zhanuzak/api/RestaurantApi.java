package zhanuzak.api;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import zhanuzak.dto.request.RestaurantRequest;
import zhanuzak.dto.response.RestaurantResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@Tag(name = "RestaurantApi")
public class RestaurantApi {
    private final RestaurantService restaurantService;



    @GetMapping
    @Secured("ADMIN")
    List<RestaurantResponse> getAll() {
        return restaurantService.getAll();
    }

    @GetMapping("/{id}")
    @PermitAll
    RestaurantResponse getById(@PathVariable Long id) {
        return restaurantService.getById(id);
    }

    @PostMapping("/save")
    @PermitAll
    SimpleResponse save(@RequestBody @Valid RestaurantRequest restaurantRequest) {
        return restaurantService.save(restaurantRequest);
    }


}
