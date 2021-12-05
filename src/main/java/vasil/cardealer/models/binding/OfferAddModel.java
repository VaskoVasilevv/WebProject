package vasil.cardealer.models.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vasil.cardealer.models.entity.Vehicle;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
public class OfferAddModel {

    private String id;
    private String createdOn;
    private String ValidUntil;
    private BigDecimal price;
    private String description;
    private Vehicle vehicle;
}
