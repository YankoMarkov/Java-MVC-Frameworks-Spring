package exodia.domain.models.services;

import java.util.Set;

public class UserServiceModel {
	
	private String id;
	private String username;
	private String password;
	private String email;
	private Set<DocumentServiceModel> documents;
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Set<DocumentServiceModel> getDocuments() {
		return this.documents;
	}
	
	public void setDocuments(Set<DocumentServiceModel> documents) {
		this.documents = documents;
	}
}
