package zhanuzak.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import zhanuzak.dto.request.StopListRequest;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.dto.response.StopListResponse;
import zhanuzak.service.StopListService;

import java.util.List;

@RestController
@RequestMapping("/api/stopListApi")
@RequiredArgsConstructor
@Tag(name = "StopListApi")
public class StopListApi {
    private final StopListService stopListService;

    @PermitAll
    @GetMapping
    List<StopListResponse> getAll() {
        return stopListService.getAll();
    }

    @Secured({"ADMIN","CHEF"})
    @PostMapping
    SimpleResponse save(@RequestBody StopListRequest stopListRequest) {
        return stopListService.save(stopListRequest);
    }




}
