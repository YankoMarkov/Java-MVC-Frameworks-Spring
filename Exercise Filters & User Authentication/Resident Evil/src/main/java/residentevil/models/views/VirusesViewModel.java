package residentevil.models.views;

import java.time.LocalDate;

public class VirusesViewModel {
	
	private String id;
	private String name;
	private String magnitude;
	private LocalDate releasedOn;
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMagnitude() {
		return this.magnitude;
	}
	
	public void setMagnitude(String magnitude) {
		this.magnitude = magnitude;
	}
	
	public LocalDate getReleasedOn() {
		return this.releasedOn;
	}
	
	public void setReleasedOn(LocalDate releasedOn) {
		this.releasedOn = releasedOn;
	}
}
