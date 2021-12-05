package vasil.cardealer.models.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vasil.cardealer.models.entity.Vehicle;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OfferViewModel {

    private String id;
    private String createdOn;
    private String validUntil;
    private BigDecimal price;
    private String description;
    private Vehicle vehicle;

}
