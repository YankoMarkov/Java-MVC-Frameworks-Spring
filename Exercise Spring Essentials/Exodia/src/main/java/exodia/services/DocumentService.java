package exodia.services;

import exodia.domain.models.services.DocumentServiceModel;

import java.util.List;

public interface DocumentService {
	
	DocumentServiceModel saveDocument(DocumentServiceModel documentService);
	
	boolean deleteDocument(String id);
	
	List<DocumentServiceModel> getAllDocuments();
	
	DocumentServiceModel getDocumentById(String id);
	
	DocumentServiceModel getDocumentByTitle(String title);
	
	boolean documentExist(String title);
}
