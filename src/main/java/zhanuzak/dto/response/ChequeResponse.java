package zhanuzak.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class ChequeResponse {
    private Long id;
    private String fullName;
    private List<MenuItemResponse> menuItemResponses;
    private LocalDate createdAt;
    private BigDecimal priceAverage;
    private BigDecimal service;
    private BigDecimal grandTotal;

    public ChequeResponse(Long id, LocalDate createdAt,
                          BigDecimal priceAverage) {
        this.id=id;
        this.createdAt=createdAt;
        this.priceAverage = priceAverage;
    }
}
