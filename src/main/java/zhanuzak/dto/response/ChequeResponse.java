package zhanuzak.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter

@Setter
@NoArgsConstructor

public class ChequeResponse {
    private String fullName;
    private List<MenuItemResponse> menuItemResponses;
    private BigDecimal priceAverage;
    private int service;
    private BigDecimal grandTotal;

    public ChequeResponse(String fullName, List<MenuItemResponse> menuItemResponses,
                          BigDecimal priceAverage, int service, BigDecimal grandTotal) {
        this.fullName = fullName;
        this.menuItemResponses = menuItemResponses;
        this.priceAverage = priceAverage;
        this.service = service;
        this.grandTotal = grandTotal;
    }
}
