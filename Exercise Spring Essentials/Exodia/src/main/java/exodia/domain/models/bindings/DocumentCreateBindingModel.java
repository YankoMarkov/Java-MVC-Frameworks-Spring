package exodia.domain.models.bindings;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DocumentCreateBindingModel {
	
	private String title;
	private String content;
	
	@NotNull
	@NotEmpty(message = "missing title")
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotNull
	@NotEmpty(message = "missing content")
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
