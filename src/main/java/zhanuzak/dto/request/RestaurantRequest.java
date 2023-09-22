package zhanuzak.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import zhanuzak.enums.RestaurantType;

import java.math.BigDecimal;

@Builder
public record RestaurantRequest(@NotNull String name, @NotNull String location,
                                @NotNull RestaurantType restType,
                                @NotNull
                                @JsonProperty("numberOfEmployees")
                                int numberOfEmployees,
                                @NotNull
                                @JsonProperty("service")
                                BigDecimal service) {
}
