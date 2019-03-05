package realestate.domain.dtos.bindings;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OfferFindDto {
	
	private BigDecimal familyBudget;
	private String apartmentType;
	private String familyName;
	
	@NotNull
	@DecimalMin("0.001")
	public BigDecimal getFamilyBudget() {
		return familyBudget;
	}
	
	public void setFamilyBudget(BigDecimal familyBudget) {
		this.familyBudget = familyBudget;
	}
	
	@NotNull
	@NotEmpty
	public String getApartmentType() {
		return apartmentType;
	}
	
	public void setApartmentType(String apartmentType) {
		this.apartmentType = apartmentType;
	}
	
	@NotNull
	@NotEmpty
	public String getFamilyName() {
		return familyName;
	}
	
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
}
