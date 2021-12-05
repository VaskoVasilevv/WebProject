package vasil.cardealer.services;

import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import vasil.cardealer.base.BaseTest;
import vasil.cardealer.models.entity.Log;
import vasil.cardealer.models.service.LogServiceModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class  LogServiceTest extends BaseTest {

    @Autowired
    private LogService service;
    @Autowired
    private ModelMapper modelMapper;


    @Test
    public void testCreateLogWhenItIsOk() {
        String str = "2021-11-11 11:11";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        Log log = new Log();
        log.setUsername("Paco");
        log.setTime(dateTime);
        log.setDescription("long description");

        LogServiceModel model = service.seedLogInDb(modelMapper.map(log, LogServiceModel.class));

        assertEquals("Paco", model.getUsername());
        assertEquals(dateTime, model.getTime());
        assertEquals("long description", model.getDescription());


    }
}
