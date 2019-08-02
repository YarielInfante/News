package com.upday.News.repository;

import com.upday.News.entity.Keyword;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface KeywordRepository extends PagingAndSortingRepository<Keyword, Long> {
}
