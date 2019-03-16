package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.models.service.CustomerServiceModel;
import org.softuni.cardealer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CustomerServiceTest {

    @Autowired
    private CustomerRepository customerRepository;
    private CustomerService customerService;
    private ModelMapper modelMapper;
    private CustomerServiceModel toBeSaved;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.customerService = new CustomerServiceImpl(this.customerRepository, this.modelMapper);
        this.toBeSaved = new CustomerServiceModel();
        this.toBeSaved.setName("Pesho");
        this.toBeSaved.setBirthDate(LocalDate.of(1990, 10, 20));
        this.toBeSaved.setYoungDriver(true);
    }

    @Test
    public void saveCustomer_saveCustomerWithCorrectValues_returnCorrect() {

        CustomerServiceModel actual = this.customerService.saveCustomer(this.toBeSaved);
        CustomerServiceModel expected = this.modelMapper.map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);

        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getBirthDate(), actual.getBirthDate());
    }

    @Test(expected = Exception.class)
    public void saveCustomer_saveCustomerWithNullValues_returnException() {

        this.toBeSaved.setName(null);
        this.toBeSaved.setBirthDate(null);

        this.customerService.saveCustomer(this.toBeSaved);
    }

    @Test
    public void editCustomer_editCustomerWithCorrectValues_returnCorrect() {

        CustomerServiceModel actual = this.customerService.saveCustomer(this.toBeSaved);
        actual.setName("Ivan");
        actual.setBirthDate(LocalDate.of(1995, 11, 15));
        CustomerServiceModel expected = this.customerService.editCustomer(actual);

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getBirthDate(), actual.getBirthDate());
    }

    @Test(expected = Exception.class)
    public void editCustomer_editCustomerWithNullValues_returnException() {

        CustomerServiceModel actual = this.customerService.saveCustomer(this.toBeSaved);
        actual.setName(null);
        actual.setBirthDate(null);

        this.customerService.editCustomer(actual);
    }

    @Test
    public void deleteCustomer_deleteCustomerWithCorrectId_returnCorrect() {

        CustomerServiceModel actual = this.customerService.saveCustomer(this.toBeSaved);
        CustomerServiceModel expected = this.customerService.deleteCustomer(actual.getId());

        Assert.assertEquals(expected.getId(), actual.getId());
    }

    @Test(expected = Exception.class)
    public void deleteCustomer_deleteCustomerWithIncorrectId_returnException() {

        this.customerService.deleteCustomer("a");
    }

    @Test
    public void findCustomerById_findCustomerWithCorrectId_returnCorrect() {

        CustomerServiceModel actual = this.customerService.saveCustomer(this.toBeSaved);
        CustomerServiceModel expected = this.customerService.findCustomerById(actual.getId());

        Assert.assertEquals(expected.getId(), actual.getId());
    }

    @Test(expected = Exception.class)
    public void findCustomerById_findCustomerWithIncorrectId_returnException() {

        this.customerService.findCustomerById("a");
    }
}
