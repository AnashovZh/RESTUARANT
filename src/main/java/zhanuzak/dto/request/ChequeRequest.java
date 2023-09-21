package zhanuzak.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ChequeRequest {
    private BigDecimal priceAverage;
    private LocalDate createdAt;

    public ChequeRequest(BigDecimal priceAverage, LocalDate createdAt) {
        this.priceAverage = priceAverage;
        this.createdAt = createdAt;
    }
}
