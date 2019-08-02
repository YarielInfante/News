package com.upday.News.repository;

import com.upday.News.entity.Article;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {
}
