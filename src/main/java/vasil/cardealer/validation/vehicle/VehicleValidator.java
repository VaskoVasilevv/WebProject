package vasil.cardealer.validation.vehicle;

import vasil.cardealer.models.service.VehicleAddServiceModel;

public interface VehicleValidator {
    boolean isValid(VehicleAddServiceModel serviceModel);
}