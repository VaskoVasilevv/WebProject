package vasil.cardealer.validation.vehicle;

import org.springframework.stereotype.Service;
import vasil.cardealer.models.service.VehicleAddServiceModel;

@Service
public class VehicleValidatorImpl implements VehicleValidator {
    @Override
    public boolean isValid(VehicleAddServiceModel serviceModel) {
        return serviceModel != null &&
                serviceModel.getMaker().length() > 0 &&
                serviceModel.getModel().length() > 0 &&
                serviceModel.getYear() > 0 &&
                serviceModel.getMileage() > 0;
    }
}
