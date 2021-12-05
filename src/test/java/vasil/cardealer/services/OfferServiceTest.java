package vasil.cardealer.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import vasil.cardealer.base.BaseTest;
import vasil.cardealer.error.OfferNotFoundException;
import vasil.cardealer.models.entity.Offer;
import vasil.cardealer.models.service.OfferServiceModel;
import vasil.cardealer.repository.OfferRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OfferServiceTest extends BaseTest {
    private List<Offer> list;

    @Autowired
    private OfferService service;
    @Autowired
    ModelMapper modelMapper;

    @MockBean
    private OfferRepository offerRepository;

    @Before
    public void setupTest() {
        list = new ArrayList<>();
        when(offerRepository.findAll())
                .thenReturn(list);
    }

    @Test
    public void testFindAllEventsWhenNoEventsReturnEmpty() {
        list.clear();
        List<OfferServiceModel> allEvents = service.findAll();
        Assert.assertTrue(allEvents.isEmpty());
    }

    @Test
    public void testFindOneEventsWhenOneEventsThenAddId() {
        list.clear();

        Offer offer = new Offer();

        list.add(offer);

        List<OfferServiceModel> results = service.findAll();
        assertEquals(1, results.size());
    }

    @Test
    public void testCreateVehicleWhenModelIsNullThenThrow() {
        OfferServiceModel serviceModer = null;
        assertThrows(Exception.class,
                () -> service.create(serviceModer, "vasko"));
    }

    @Test
    public void testCreateVehicleWhenUserIsNullThenThrow() {
        OfferServiceModel serviceModer = new OfferServiceModel();
        assertThrows(Exception.class,
                () -> service.create(serviceModer, ""));
    }

    @Test
    public void testCreateVehicleWhenUserIsNullThrow() {
        assertEquals(0, service.findOfferByUsername("").size());
    }

    @Test
    public void testFindByidShouldThrow() {
        assertThrows(OfferNotFoundException.class,
                () -> service.findById("id"));
    }
    @Test(expected = Exception.class)
    public void testDeleteEventWithInvalidValueShouldThrowError() {
        service.deleteOffer(null);
        verify(offerRepository)
                .save(any());
    }
    @Test(expected = Exception.class)
    public void testDeleteWhenExistShouldDelete() {
        String id = "1";
        Offer offer = new Offer();
        offer.setId(id);
        Mockito.when(offerRepository
                .findById(id))
                .thenReturn(Optional.of(offer))
                .thenThrow(Exception.class);
        this.service.deleteOffer(id);
        assertThrows(Exception.class,
                () -> this.service.deleteOffer(id));
    }
    @Test(expected = OfferNotFoundException.class)
    public void testEditOfferWhenIdNotValidShouldThrow() {
        Mockito.when(offerRepository.findById("id"))
                .thenThrow(new OfferNotFoundException());
        OfferServiceModel model = new OfferServiceModel();

        service.editOffer("id", model);
    }

}
