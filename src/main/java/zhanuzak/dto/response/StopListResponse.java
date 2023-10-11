package zhanuzak.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class StopListResponse {
    private Long id;
    private String reason;
    private LocalDate date;

    public StopListResponse(Long id, String reason, LocalDate date) {
        this.id = id;
        this.reason = reason;
        this.date = date;
    }
}
