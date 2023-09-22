package zhanuzak.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zhanuzak.dto.request.ChequeRequest;
import zhanuzak.dto.response.ChequeResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.service.ChequeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cheques")
public class ChequeApi {

    private final ChequeService chequeService;

    @GetMapping
    @PermitAll
    List<ChequeResponse> getAll() {
        return chequeService.getAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    SimpleResponse save(@RequestBody ChequeRequest chequeRequest) {
        return chequeService.save(chequeRequest);
    }

    @PermitAll
    @GetMapping("/{id}")
    ChequeResponse getById(@PathVariable Long id) {
        return chequeService.getById(id);
    }
}
