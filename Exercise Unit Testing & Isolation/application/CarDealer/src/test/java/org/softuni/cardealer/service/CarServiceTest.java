package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.models.service.CarServiceModel;
import org.softuni.cardealer.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CarServiceTest {

    @Autowired
    private CarRepository carRepository;
    private ModelMapper modelMapper;
    private CarService carService;
    private CarServiceModel toBeSaved;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.carService = new CarServiceImpl(this.carRepository, this.modelMapper);
        this.toBeSaved = new CarServiceModel();
        this.toBeSaved.setMake("BMW");
        this.toBeSaved.setModel("M5");
        this.toBeSaved.setTravelledDistance(1000L);
    }

    @Test
    public void saveCar_saveCarWithCorrectValues_returnCorrect() {

        CarServiceModel actual = this.carService.saveCar(this.toBeSaved);
        CarServiceModel expected = this.modelMapper.map(this.carRepository.findAll().get(0), CarServiceModel.class);

        Assert.assertEquals(expected.getMake(), actual.getMake());
        Assert.assertEquals(expected.getModel(), actual.getModel());
        Assert.assertEquals(expected.getTravelledDistance(), actual.getTravelledDistance());
    }

    @Test(expected = Exception.class)
    public void saveCar_saveCarWithNullValues_returnException() {

        this.toBeSaved.setMake(null);
        this.toBeSaved.setModel(null);
        this.toBeSaved.setTravelledDistance(null);

        this.carService.saveCar(this.toBeSaved);
    }

    @Test
    public void editCar_editCarWithCorrectValues_returnCorrect() {

        CarServiceModel actual = this.carService.saveCar(this.toBeSaved);
        actual.setModel("VW");
        actual.setMake("A5");
        actual.setTravelledDistance(500L);
        CarServiceModel expected = this.carService.editCar(actual);

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getMake(), actual.getMake());
        Assert.assertEquals(expected.getModel(), actual.getModel());
        Assert.assertEquals(expected.getTravelledDistance(), actual.getTravelledDistance());
    }

    @Test(expected = Exception.class)
    public void editCar_editCarWithNullValues_returnException() {

        CarServiceModel actual = this.carService.saveCar(this.toBeSaved);
        actual.setModel(null);
        actual.setMake(null);
        actual.setTravelledDistance(null);

        this.carService.editCar(actual);
    }

    @Test
    public void deleteCar_deleteCarWithCorrectId_returnCorrect() {

        CarServiceModel actual = this.carService.saveCar(this.toBeSaved);
        CarServiceModel expected = this.carService.deleteCar(actual.getId());

        Assert.assertEquals(expected.getId(), actual.getId());
    }

    @Test(expected = Exception.class)
    public void deleteCar_deleteCarWithIncorrectId_returnException() {

        this.carService.deleteCar("a");
    }

    @Test
    public void findCarById_findCarWithCorrectId_returnCorrect() {

        CarServiceModel actual = this.carService.saveCar(this.toBeSaved);
        CarServiceModel expected = this.carService.findCarById(actual.getId());

        Assert.assertEquals(expected.getId(), actual.getId());
    }

    @Test(expected = Exception.class)
    public void findCarById_findCarWithIncorrectId_returnException() {

        this.carService.findCarById("a");
    }
}
