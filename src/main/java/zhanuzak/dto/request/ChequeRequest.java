package zhanuzak.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChequeRequest {
    private LocalDate createdAt;
    private List<String>menuItemNames;

    public ChequeRequest(LocalDate createdAt, List<String> menuItemNames) {
        this.createdAt = createdAt;
        this.menuItemNames = menuItemNames;
    }
}
