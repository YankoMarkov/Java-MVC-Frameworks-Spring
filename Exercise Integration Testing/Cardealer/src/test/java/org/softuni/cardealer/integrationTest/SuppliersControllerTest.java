package org.softuni.cardealer.integrationTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SuppliersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SupplierRepository supplierRepository;

    @Before
    public void EmptyDB(){
        this.supplierRepository.deleteAll();
    }

    @Test
    @WithMockUser("spring")
    public void add_saveEntityProperly() throws Exception {
        this.mockMvc
                .perform(post("/suppliers/add")
                        .param("name", "Peho")
                        .param("isImporter", "true")
                );
        this.mockMvc
                .perform(post("/suppliers/add")
                        .param("name", "Gosho")
                        .param("isImporter", "false")
                );
        Supplier actual = this.supplierRepository.findByName("Gosho").orElse(null);

        Assert.assertEquals(2, this.supplierRepository.count());
        assert actual != null;
        Assert.assertEquals("Gosho", actual.getName());
        Assert.assertFalse(actual.getIsImporter());
    }

    @Test
    @WithMockUser("spring")
    public void add_redirectCorrectly() throws Exception {
        this.mockMvc
                .perform(post("/suppliers/add")
                        .param("name", "Peho")
                        .param("isImporter", "true")
                )
                .andExpect(view().name("redirect:all"));
    }

    @Test
    @WithMockUser("spring")
    public void edit_worksCorrect() throws Exception {
        Supplier first = new Supplier();
        first.setName("Pesho");
        first.setIsImporter(false);

        Supplier second = new Supplier();
        second.setName("Gosho");
        second.setIsImporter(true);

        first = this.supplierRepository.saveAndFlush(first);
        second = this.supplierRepository.saveAndFlush(second);

        this.mockMvc
                .perform(post("/suppliers/edit/" + first.getId())
                        .param("name", "Ivan")
                        .param("isImporter", "true"));

        this.mockMvc
                .perform(post("/suppliers/edit/" + second.getId())
                        .param("name", "Marin")
                        .param("isImporter", "false"));

        Supplier firstActual = this.supplierRepository.findById(first.getId()).orElse(null);
        Supplier secondActual = this.supplierRepository.findById(second.getId()).orElse(null);

        assert firstActual != null;
        assert secondActual != null;
        Assert.assertEquals("Ivan", firstActual.getName());
        Assert.assertTrue(firstActual.getIsImporter());
        Assert.assertEquals("Marin", secondActual.getName());
        Assert.assertFalse(secondActual.getIsImporter());
    }

    @Test
    @WithMockUser("spring")
    public void delete_worksCorrect() throws Exception {
        Supplier first = new Supplier();
        first.setName("Pesho");
        first.setIsImporter(false);

        Supplier second = new Supplier();
        second.setName("Gosho");
        second.setIsImporter(true);

        first = this.supplierRepository.saveAndFlush(first);
        second = this.supplierRepository.saveAndFlush(second);

        this.mockMvc
                .perform(post("/suppliers/delete/" + first.getId()));

        Assert.assertEquals(1, this.supplierRepository.count());

        this.mockMvc
                .perform(post("/suppliers/delete/" + second.getId()));

        Assert.assertEquals(0, this.supplierRepository.count());
    }

    @Test(expected = Exception.class)
    @WithMockUser("spring")
    public void delete_throwException() throws Exception {
        this.mockMvc
                .perform(post("/suppliers/delete/aaa"));
    }

    @Test
    public void all_withGuestRedirectToLogin() throws Exception {
        this.mockMvc
                .perform(get("/suppliers/all"))
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser("spring")
    public void all_returnCorrectView() throws Exception {
        this.mockMvc
                .perform(get("/suppliers/all"))
                .andExpect(view().name("all-suppliers"));
    }

    @Test
    @WithMockUser("spring")
    public void all_returnCorrectAttribute() throws Exception {
        this.mockMvc
                .perform(get("/suppliers/all"))
                .andExpect(model().attributeExists("suppliers"));
    }

    @Test
    @WithMockUser("spring")
    public void fetch_worksCorrect() throws Exception {
        Supplier first = new Supplier();
        first.setName("Pesho");
        first.setIsImporter(false);

        first = this.supplierRepository.saveAndFlush(first);

        String result = this.mockMvc
                .perform(get("/suppliers/fetch"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.assertEquals("[{\"id\":\""+first.getId()+"\",\"name\":\"Pesho\",\"isImporter\":false}]", result);
    }
}
