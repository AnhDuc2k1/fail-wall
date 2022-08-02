package org.aibles.failwall.document.service.iml;

import org.aibles.failwall.authentication.payload.UserPrincipal;
import org.aibles.failwall.document.dto.request.DocumentReqDto;
import org.aibles.failwall.document.dto.response.BasicDocumentResDto;
import org.aibles.failwall.document.dto.response.DetailDocumentResDto;
import org.aibles.failwall.document.model.Document;
import org.aibles.failwall.document.model.DocumentCategory;
import org.aibles.failwall.document.repository.CategoryRepository;
import org.aibles.failwall.document.repository.DocumentCategoryRepository;
import org.aibles.failwall.document.repository.DocumentRepository;
import org.aibles.failwall.document.service.DocumentService;
import org.aibles.failwall.exception.FailWallBusinessException;
import org.aibles.failwall.util.paging.PagingRes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DocumentServiceIml implements DocumentService {

    private final CategoryRepository categoryRepository;
    private final DocumentRepository documentRepository;
    private final DocumentCategoryRepository documentCategoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DocumentServiceIml(DocumentRepository documentRepository, CategoryRepository categoryRepository, DocumentCategoryRepository documentCategoryRepository, ModelMapper modelMapper) {
        this.documentRepository = documentRepository;
        this.categoryRepository = categoryRepository;
        this.documentCategoryRepository = documentCategoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DetailDocumentResDto createDocument(UserPrincipal userPrincipal, DocumentReqDto documentReqDto) {
        Document newDocument = modelMapper.map(documentReqDto, Document.class);
        newDocument.setDocumentCategories(buildSetDocumentCategories(documentReqDto.getCategoryIds()));
        newDocument.setCreatedBy(userPrincipal.getUsername());
        newDocument.setLastUpdatedBy(userPrincipal.getUsername());
        newDocument.setCreatedAt(Instant.now());
        newDocument.setLastUpdatedAt(Instant.now());

        DetailDocumentResDto documentResDto = modelMapper.map(documentRepository.save(newDocument), DetailDocumentResDto.class);
        documentResDto.setCategories(categoryRepository.findAllByIdIn(documentReqDto.getCategoryIds()));

        return  documentResDto;
    }

    @Override
    public DetailDocumentResDto getDocument(long id) {
       return documentRepository.findById(id)
               .map(document -> {
                   DetailDocumentResDto documentResDto = modelMapper.map(document, DetailDocumentResDto.class);
                   documentResDto.setCategories(documentCategoryRepository.getAllCategoriesByDocumentId(id));

                   return  documentResDto;
               })
               .orElseThrow(() -> new FailWallBusinessException("Document Not Found", HttpStatus.NOT_FOUND));

    }

    @Override
    public PagingRes<BasicDocumentResDto> getDocuments(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Document> documentPage = documentRepository.findAll(pageable);

        return PagingRes.of(documentPage.map(document -> {
            BasicDocumentResDto basicDocumentResDto = modelMapper.map(document, BasicDocumentResDto.class);
            basicDocumentResDto.setCategories(documentCategoryRepository.getAllCategoriesByDocumentId(document.getId()));
            return basicDocumentResDto;

        }));
    }

    @Override
    public String deleteDocument(UserPrincipal userPrincipal, long id) {
        return documentRepository.findById(id)
                .map(document -> {
                    validateInput(userPrincipal, document);
                    documentRepository.delete(document);
                    return "Deleted successfully";
                })
                .orElseThrow(() -> new FailWallBusinessException("Document Not Found", HttpStatus.NOT_FOUND));
    }

    @Override
    public DetailDocumentResDto updateDocument(UserPrincipal userPrincipal, long id, DocumentReqDto documentReqDto) {
        return documentRepository.findById(id).map(
                document -> {
                    validateInput(userPrincipal, document);
                    document.setDocumentCategories(buildSetDocumentCategories(documentReqDto.getCategoryIds()));
                    document.setTitle(documentReqDto.getTitle());
                    document.setContent(documentReqDto.getContent());
                    document.setLastUpdatedBy(userPrincipal.getUsername());

                    DetailDocumentResDto documentResDto = modelMapper.map(documentRepository.save(document),
                            DetailDocumentResDto.class);
                    documentResDto.setCategories(categoryRepository.findAllByIdIn(documentReqDto.getCategoryIds()));

                    return documentResDto;
                }
                ).orElseThrow(() -> {
                    throw new FailWallBusinessException("Document Not Found", HttpStatus.NOT_FOUND);
                });
    }

    private Set<DocumentCategory> buildSetDocumentCategories(Set<Integer> categoriesIds){
        Set<DocumentCategory> documentCategories = categoriesIds.stream()
                .map(categoryId -> {
                    DocumentCategory documentCategory = new DocumentCategory(categoryId);
                    return documentCategory;
                }).collect(Collectors.toSet());
        return documentCategories;
    }

    private void validateInput(UserPrincipal userPrincipal, Document document){
        String createdBy = document.getCreatedBy();
        String currentUser = userPrincipal.getUsername();
        if (!createdBy.equals(currentUser) || !userPrincipal.getAuthorities().contains("ROLE_ADMIN")){
            throw new FailWallBusinessException("Don't have permission with this document", HttpStatus.BAD_REQUEST);
        }
    }
}
