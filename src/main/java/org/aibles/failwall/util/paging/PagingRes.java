package org.aibles.failwall.util.paging;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PagingRes<T> {
    private List<T> pageData;
    private int pageNumber;
    private int pageSize;
    private long total;

    private PagingRes(Page<T> page){
        pageData = page.getContent();
        pageNumber = page.getNumber() + 1;
        pageSize = page.getSize();
        total = page.getTotalElements();
    }

    public static <T> PagingRes<T> of (Page<T> page){
        return new PagingRes<>(page);
    }
}
