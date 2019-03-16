package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SupplierServiceTest {

    @Autowired
    private SupplierRepository supplierRepository;
    private SupplierService supplierService;
    private ModelMapper modelMapper;
    private SupplierServiceModel toBeSaved;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.supplierService = new SupplierServiceImpl(this.supplierRepository, this.modelMapper);
        this.toBeSaved = new SupplierServiceModel();
        this.toBeSaved.setName("Pesho");
        this.toBeSaved.setImporter(true);
    }

    @Test
    public void saveSupplier_saveSupplierWithCorrectValues_returnCorrect() {

        SupplierServiceModel actual = this.supplierService.saveSupplier(this.toBeSaved);
        SupplierServiceModel expected = this.modelMapper.map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);

        Assert.assertEquals(expected.getName(), actual.getName());
    }

    @Test(expected = Exception.class)
    public void saveSupplier_saveSupplierWithNullValues_returnException() {

        this.toBeSaved.setName(null);

        this.supplierService.saveSupplier(this.toBeSaved);
    }

    @Test
    public void editSupplier_editSupplierWithCorrectValues_returnCorrect() {

        SupplierServiceModel actual = this.supplierService.saveSupplier(this.toBeSaved);
        actual.setName("Ivan");
        SupplierServiceModel expected = this.supplierService.editSupplier(actual);

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
    }

    @Test(expected = Exception.class)
    public void editSupplier_editSupplierWithNullValues_returnException() {

        SupplierServiceModel actual = this.supplierService.saveSupplier(this.toBeSaved);
        actual.setName(null);

        this.supplierService.editSupplier(actual);
    }

    @Test
    public void deleteSupplier_deleteSupplierWithCorrectId_returnCorrect() {

        SupplierServiceModel actual = this.supplierService.saveSupplier(this.toBeSaved);
        SupplierServiceModel expected = this.supplierService.deleteSupplier(actual.getId());

        Assert.assertEquals(expected.getId(), actual.getId());
    }

    @Test(expected = Exception.class)
    public void deleteSupplier_deleteSupplierWithIncorrectId_returnException() {

        this.supplierService.deleteSupplier("a");
    }

    @Test
    public void findSupplierById_findSupplierWithCorrectId_returnCorrect() {

        SupplierServiceModel actual = this.supplierService.saveSupplier(this.toBeSaved);
        SupplierServiceModel expected = this.supplierService.findSupplierById(actual.getId());

        Assert.assertEquals(expected.getId(), actual.getId());
    }

    @Test(expected = Exception.class)
    public void findSupplierById_findSupplierWithIncorrectId_returnException() {

        this.supplierService.findSupplierById("a");
    }
}
