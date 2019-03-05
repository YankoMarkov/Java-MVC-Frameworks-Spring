package exodia.services;

import exodia.domain.models.services.DocumentServiceModel;
import exodia.domain.entities.Document;
import exodia.repositories.DocumentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {
	
	private final DocumentRepository documentRepository;
	private final ModelMapper modelMapper;
	
	@Autowired
	public DocumentServiceImpl(DocumentRepository documentRepository, ModelMapper modelMapper) {
		this.documentRepository = documentRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public DocumentServiceModel saveDocument(DocumentServiceModel documentService) {
		Document document = null;
		try {
			document = this.modelMapper.map(documentService, Document.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		this.documentRepository.saveAndFlush(document);
		return this.modelMapper.map(document, DocumentServiceModel.class);
	}
	
	@Override
	public boolean deleteDocument(String id) {
		try {
			this.documentRepository.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public List<DocumentServiceModel> getAllDocuments() {
		List<Document> documents = this.documentRepository.findAll();
		if (documents == null) {
			return null;
		}
		return documents.stream()
				.map(document -> {
					if (document.getTitle().length() > 12) {
						document.setTitle(document.getTitle().substring(0, 12) + "...");
					}
					return this.modelMapper.map(document, DocumentServiceModel.class);
				})
				.collect(Collectors.toList());
	}
	
	@Override
	public DocumentServiceModel getDocumentById(String id) {
		Document document = this.documentRepository.findById(id).orElse(null);
		if (document == null) {
			return null;
		}
		return this.modelMapper.map(document, DocumentServiceModel.class);
	}
	
	@Override
	public DocumentServiceModel getDocumentByTitle(String title) {
		Document document = this.documentRepository.findByTitle(title).orElse(null);
		if (document == null) {
			return null;
		}
		return this.modelMapper.map(document, DocumentServiceModel.class);
	}
	
	@Override
	public boolean documentExist(String title) {
		return getDocumentByTitle(title) != null;
	}
}
