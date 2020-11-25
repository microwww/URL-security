package com.github.microwww.security.serve.controller;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

public class PageDATA<T> {

    final PageImpl<T> page;
    public static final PageDATA EMPTY = new PageDATA(Collections.EMPTY_LIST, PageRequest.of(0, 10), 0);

    public PageDATA(List<T> list, Pageable pageable, long total) {
        page = new PageImpl(list, pageable, total);
    }

    public int getTotalPages() {
        return page.getTotalPages();
    }

    public long getTotalElements() {
        return page.getTotalElements();
    }

    public long getPageNumber() {
        return page.getPageable().getPageNumber();
    }

    public long getPageSize() {
        return page.getPageable().getPageSize();
    }

    public boolean isLast() {
        return page.isLast();
    }

    public boolean isFirst() {
        return page.isFirst();
    }

    public List<T> getContent() {
        return page.getContent();
    }
}
