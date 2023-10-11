package zhanuzak.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter

public class ChequeResponse {
    private Long id;
    private String fullName;
    private List<MenuItemResponse> menuItemResponses;
    private LocalDate createdAt;
    private BigDecimal priceAverage;
    private BigDecimal service;
    private BigDecimal grandTotal;
//    rs.getLong("id"),
//                rs.getString("fullName"),
//                rs.getString("menuItemResponse"),
//                rs.getDate("createdAt"),
//                rs.getBigDecimal("service"),
//                rs.getBigDecimal("grandTotal"));


    public ChequeResponse(Long id, String fullName, List<MenuItemResponse> menuItemResponses,
                          LocalDate createdAt, BigDecimal service, BigDecimal grandTotal) {
        this.id = id;
        this.fullName = fullName;
        this.menuItemResponses = menuItemResponses;
        this.createdAt = createdAt;
        this.service = service;
        this.grandTotal = grandTotal;
    }

    public ChequeResponse(Long id, LocalDate createdAt, BigDecimal priceAverage) {
        this.id = id;
        this.createdAt = createdAt;
        this.priceAverage = priceAverage;
    }


    public ChequeResponse(Long id, String fullName, List<MenuItemResponse> menuItemResponses, LocalDate createdAt, BigDecimal priceAverage, BigDecimal service, BigDecimal grandTotal) {
        this.id = id;
        this.fullName = fullName;
        this.menuItemResponses = menuItemResponses;
        this.createdAt = createdAt;
        this.priceAverage = priceAverage;
        this.service = service;
        this.grandTotal = grandTotal;
    }

    public ChequeResponse(Long id, String fullName, LocalDate createdAt,
                          BigDecimal priceAverage) {
        this.id = id;
        this.fullName = fullName;
        this.createdAt = createdAt;
        this.priceAverage = priceAverage;
    }

    public ChequeResponse() {
    }


}
