package org.aibles.failwall.document.service;

import org.aibles.failwall.authentication.payload.UserPrincipal;
import org.aibles.failwall.document.dto.request.DocumentReqDto;
import org.aibles.failwall.document.dto.response.BasicDocumentResDto;
import org.aibles.failwall.document.dto.response.DetailDocumentResDto;
import org.aibles.failwall.util.paging.PagingRes;

public interface DocumentService {
    DetailDocumentResDto createDocument(UserPrincipal userPrincipal, DocumentReqDto documentReqDto);
    DetailDocumentResDto getDocument(long id);
    PagingRes<BasicDocumentResDto> getDocuments(int pageNum, int pageSize);
    String deleteDocument(UserPrincipal userPrincipal, long id);
    DetailDocumentResDto updateDocument(UserPrincipal userPrincipal, long id, DocumentReqDto documentReqDto);
}
