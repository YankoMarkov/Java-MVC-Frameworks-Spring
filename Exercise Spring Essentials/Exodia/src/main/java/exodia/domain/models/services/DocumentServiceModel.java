package exodia.domain.models.services;

public class DocumentServiceModel {
	
	private String id;
	private String title;
	private String content;
	private UserServiceModel user;
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public UserServiceModel getUser() {
		return this.user;
	}
	
	public void setUser(UserServiceModel user) {
		this.user = user;
	}
}
