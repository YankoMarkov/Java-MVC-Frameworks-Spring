package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PartServiceTest {
	
	@Autowired
	private PartRepository partRepository;
	private PartService partService;
	private ModelMapper modelMapper;
	private PartServiceModel toBeSaved;
	
	@Before
	public void init() {
		this.modelMapper = new ModelMapper();
		this.partService = new PartServiceImpl(this.partRepository, this.modelMapper);
		this.toBeSaved = new PartServiceModel();
		this.toBeSaved.setName("Engine");
		this.toBeSaved.setPrice(BigDecimal.valueOf(1500));
	}
	
	@Test
	public void savePart_savePartWithCorrectValues_returnCorrect() {
		
		PartServiceModel actual = this.partService.savePart(this.toBeSaved);
		PartServiceModel expected = this.modelMapper.map(this.partRepository.findAll().get(0), PartServiceModel.class);
		
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.getPrice(), actual.getPrice());
	}
	
	@Test(expected = Exception.class)
	public void savePart_savePartWithNullValues_returnException() {
		
		this.toBeSaved.setName(null);
		this.toBeSaved.setPrice(null);
		
		this.partService.savePart(this.toBeSaved);
	}
	
	@Test
	public void editPart_editPartWithCorrectValues_returnCorrect() {
		
		PartServiceModel actual = this.partService.savePart(this.toBeSaved);
		actual.setName("Tire");
		actual.setPrice(BigDecimal.valueOf(500));
		PartServiceModel expected = this.partService.editPart(actual);
		
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.getPrice(), actual.getPrice());
	}
	
	@Test(expected = Exception.class)
	public void editPart_editPartWithNullValues_returnException() {
		
		PartServiceModel actual = this.partService.savePart(this.toBeSaved);
		actual.setName(null);
		actual.setPrice(null);
		
		this.partService.editPart(actual);
	}
	
	@Test
	public void deletePart_deletePartWithCorrectId_returnCorrect() {
		
		PartServiceModel actual = this.partService.savePart(this.toBeSaved);
		PartServiceModel expected = this.partService.deletePart(actual.getId());
		
		Assert.assertEquals(expected.getId(), actual.getId());
	}
	
	@Test(expected = Exception.class)
	public void deletePart_deletePartWithIncorrectId_returnException() {
		
		this.partService.deletePart("a");
	}
	
	@Test
	public void findPartById_findPartWithCorrectId_returnCorrect() {
		
		PartServiceModel actual = this.partService.savePart(this.toBeSaved);
		PartServiceModel expected = this.partService.findPartById(actual.getId());
		
		Assert.assertEquals(expected.getId(), actual.getId());
	}
	
	@Test(expected = Exception.class)
	public void findPartById_findPartWithIncorrectId_returnException() {
		
		this.partService.findPartById("a");
	}
}
