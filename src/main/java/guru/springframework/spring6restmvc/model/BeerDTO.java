package guru.springframework.spring6restmvc.model;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.*;
import java.time.*;
import java.util.*;

@Data
@Builder
public class BeerDTO {
    private UUID id;
    private Integer version;
    @NonNull
    @NotBlank
    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
