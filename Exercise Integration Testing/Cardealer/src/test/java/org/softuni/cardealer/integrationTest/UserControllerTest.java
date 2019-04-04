package org.softuni.cardealer.integrationTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.cardealer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository mockUserRepository;

    @Before
    public void EmptyDB(){
        this.mockUserRepository.deleteAll();
    }

    @Test
    public void login_returnCorrectView() throws Exception {
        mockMvc
                .perform(get("/users/login"))
                .andExpect(view().name("login"));
    }

    @Test
    public void register_returnCorrectView() throws Exception {
        mockMvc
                .perform(get("/users/register"))
                .andExpect(view().name("register"));
    }

    @Test
    public void register_registerUserCorrectly() throws Exception {
        mockMvc
                .perform(post("/users/register")
                        .param("username", "pesho")
                        .param("password", "123")
                        .param("confirmPassword", "123")
                        .param("email", "p@p.p"));

        Assert.assertEquals(1, this.mockUserRepository.count());
    }

    @Test
    public void register_registerRedirectCorrectly() throws Exception {
        mockMvc
                .perform(post("/users/register")
                        .param("username", "pesho")
                        .param("password", "123")
                        .param("confirmPassword", "123")
                        .param("email", "p@p.p"))
                .andExpect(view().name("redirect:login"));
    }
}
