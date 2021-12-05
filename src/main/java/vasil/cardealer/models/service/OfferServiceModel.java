package vasil.cardealer.models.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vasil.cardealer.models.entity.Vehicle;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OfferServiceModel extends BaseServiceModel {

    private Vehicle vehicle;
    private String createdOn;
    private String validUntil;
    private BigDecimal price;
    private String description;
}
