package vasil.cardealer.web.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class HomeControllerTest  {

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void testIndexShouldReturnCorrectPage() throws Exception {
        this.mockMvc
                .perform(get("/"))
                .andExpect(view().name("home/index"));
    }

    @Test
    @WithMockUser("spring")
    public void testHomeShouldReturnCorrectPage() throws Exception {
        this.mockMvc
                .perform(get("/home"))
                .andExpect(view().name("home/home"));
    }
}