package org.aibles.failwall.document.controller;

import org.aibles.failwall.authentication.payload.UserPrincipal;
import org.aibles.failwall.document.dto.request.DocumentReqDto;
import org.aibles.failwall.document.dto.response.BasicDocumentResDto;
import org.aibles.failwall.document.dto.response.DetailDocumentResDto;
import org.aibles.failwall.document.service.DocumentService;
import org.aibles.failwall.util.paging.PagingRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<DetailDocumentResDto> createDocument(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                               @RequestBody DocumentReqDto documentReqDto){
        return new ResponseEntity<>(documentService.createDocument(userPrincipal,documentReqDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")

    public ResponseEntity<DetailDocumentResDto> getDocument(@PathVariable long id){
        return new ResponseEntity<>(documentService.getDocument(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PagingRes<BasicDocumentResDto>> getAllDocuments(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return new ResponseEntity<>(documentService.getDocuments(pageNum, pageSize), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                 @PathVariable long id){
        return new ResponseEntity<>(documentService.deleteDocument(userPrincipal, id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetailDocumentResDto> updateDocument(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                              @PathVariable long id,
                                                              @RequestBody DocumentReqDto documentReqDto){
        return new ResponseEntity<>(documentService.updateDocument(userPrincipal, id, documentReqDto), HttpStatus.OK);
    }
}
