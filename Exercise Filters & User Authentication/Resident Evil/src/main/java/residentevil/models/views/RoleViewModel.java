package residentevil.models.views;

import residentevil.anotations.ValidateAuthority;

public class RoleViewModel {
	
	private String authority;
	
	@ValidateAuthority(acceptedValues = {"USER", "MODERATOR", "ADMIN"}, message = "Invalid dataType")
	public String getAuthority() {
		return this.authority;
	}
	
	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
