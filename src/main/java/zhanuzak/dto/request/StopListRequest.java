package zhanuzak.dto.request;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record StopListRequest(String reason, LocalDate date,String menuItemName) {

}
