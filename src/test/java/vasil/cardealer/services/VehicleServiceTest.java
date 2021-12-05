package vasil.cardealer.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import vasil.cardealer.base.BaseTest;
import vasil.cardealer.error.VehicleNotFoundException;
import vasil.cardealer.models.entity.Vehicle;
import vasil.cardealer.models.service.VehicleAddServiceModel;
import vasil.cardealer.repository.VehicleRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class VehicleServiceTest extends BaseTest {
    private List<Vehicle> vehicleList;

    @Autowired
    private VehicleService service;
    @Autowired
    ModelMapper modelMapper;

    @MockBean
    private VehicleRepository mockVehicleRepository;

    @Before
    public void setupTest() {
        vehicleList = new ArrayList<>();
        when(mockVehicleRepository.findAll())
                .thenReturn(vehicleList);
    }

    @Test
    public void testFindAllEventsWhenNoEventsThenReturnEmpty() {
        vehicleList.clear();
        List<VehicleAddServiceModel> allEvents = service.findAllVehicles();
        Assert.assertTrue(allEvents.isEmpty());
    }

    @Test
    public void testFindOneEventsWhenOneEventsThenAddId() {
        vehicleList.clear();

        Vehicle vehicle = new Vehicle();

        vehicleList.add(vehicle);

        List<VehicleAddServiceModel> results = service.findAllVehicles();
        assertEquals(1, results.size());
    }

    @Test
    public void testCreateVehicleWhenModelIsNullThenThrow() {
        VehicleAddServiceModel serviceModer = null;
        assertThrows(Exception.class,
                () -> service.create(serviceModer, "vasko"));
    }

    @Test
    public void testCreateVehicleWhenUserIsNullThenThrow() {
        VehicleAddServiceModel serviceModer = new VehicleAddServiceModel();
        assertThrows(Exception.class,
                () -> service.create(serviceModer, ""));
    }

    @Test
    public void testCreateVehicleWhenUserIsNullThenThrows() {
        assertEquals(0, service.findVehicleByUsername("").size());
    }

    @Test
    public void testFindByIdShouldNotFoundThenThrow() {
        assertThrows(VehicleNotFoundException.class,
                () -> service.findById("id"));
    }
}
