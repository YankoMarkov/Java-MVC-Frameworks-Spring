package realestate.domain.dtos.bindings;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OfferRegisterDto {
	
	private BigDecimal apartmentRent;
	private String apartmentType;
	private BigDecimal agencyCommission;
	
	@NotNull
	@DecimalMin("0.0001")
	public BigDecimal getApartmentRent() {
		return apartmentRent;
	}
	
	public void setApartmentRent(BigDecimal apartmentRent) {
		this.apartmentRent = apartmentRent;
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
	@DecimalMin("0")
	@DecimalMax("100")
	public BigDecimal getAgencyCommission() {
		return agencyCommission;
	}
	
	public void setAgencyCommission(BigDecimal agencyCommission) {
		this.agencyCommission = agencyCommission;
	}
}
