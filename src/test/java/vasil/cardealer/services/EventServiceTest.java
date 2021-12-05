package vasil.cardealer.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import vasil.cardealer.base.BaseTest;
import vasil.cardealer.error.EventNameAlreadyExistsException;
import vasil.cardealer.error.EventNotFoundException;
import vasil.cardealer.models.entity.Event;
import vasil.cardealer.models.service.EventAddServiceModel;
import vasil.cardealer.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class EventServiceTest extends BaseTest{

    private List<Event> events;

    @Autowired
    private EventService service;
    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private EventRepository mockEventRepository;

    @Before
    public void setupTest() {
        events = new ArrayList<>();
        when(mockEventRepository.findAll())
                .thenReturn(events);
    }

    @Test
    public void createEvent_WhenEventAlreadyExist_shouldThrow() {

        Event event = new Event();
        event.setName("Peshko");
        when(mockEventRepository.findByName(event.getName()))
                .thenReturn(Optional.of(event));
        assertThrows(
                EventNameAlreadyExistsException.class,
                () -> service.createEvent(modelMapper.map(event, EventAddServiceModel.class), "krisko"));
    }

    @Test
    public void findAllEvents_whenNoEvents_returnEmpty() {
        events.clear();
        List<EventAddServiceModel> allEvents = service.findAllEvents();
        Assert.assertTrue(allEvents.isEmpty());
    }

    @Test
    public void findOneEvents_whenOneEvents_IdAdd() {
        events.clear();

        Event event = new Event();
        event.setName("Peshko");
        event.setDate("2900-12-12");
        event.setDescription("ddd");
        event.setImageUrl("imgUrl");

        events.add(event);

        List<EventAddServiceModel> results = service.findAllEvents();
        assertEquals(1, results.size());
    }

    @Test
    public void createEventsWhenModelIsNullThrow() {

        EventAddServiceModel serviceModer = null;
        assertThrows(NullPointerException.class,
                () -> service.createEvent(serviceModer, "krisko"));

    }

    @Test
    public void createEventsWhenUserIsNullThrows() {

        EventAddServiceModel serviceModer = new EventAddServiceModel();
        serviceModer.setName("Peshko");
        serviceModer.setDate("2900-12-12");
        serviceModer.setDescription("ddd");
        serviceModer.setImageUrl("imgUrl");
        assertThrows(UsernameNotFoundException.class,
                () -> service.createEvent(serviceModer, "null"));

    }

    @Test
    public void findEventsWhenIdIsNotCorrectThrow() {
        Event event = new Event();
        event.setName("Peshko");
        when(mockEventRepository.findById(event.getName()))
                .thenReturn(Optional.of(event));
        assertThrows(
                EventNotFoundException.class,
                () -> service.findById("krisko"));

    }

    @Test(expected = Exception.class)
    public void deleteEventWithInvalidValueThrowError() {
        service.deleteEvent(null);
        verify(mockEventRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void deleteWhenExistShouldDelete() {
        String id = "1";
        Event event = new Event();
        event.setId(id);
        Mockito.when(mockEventRepository
                .findById(id))
                .thenReturn(Optional.of(event))
                .thenThrow(Exception.class);
        this.service.deleteEvent(id);
        assertThrows(Exception.class,
                () -> this.service.deleteEvent(id));
    }

    @Test(expected = EventNotFoundException.class)
    public void editOfferWhenIdNotValidShouldThrow() {
        Mockito.when(mockEventRepository.findById("id"))
                .thenThrow(new EventNotFoundException());
        EventAddServiceModel model = new EventAddServiceModel();

        service.editEvent("id", model);
    }
}
