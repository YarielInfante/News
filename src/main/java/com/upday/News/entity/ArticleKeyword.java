package com.upday.News.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Represents the relationship between Article entity and Keyword entity.
 *
 * @see Article
 * @see Keyword
 * @see Entity
 * @see lombok
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
    @ManyToOne
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    public ArticleKeyword(Keyword keyword) {
        this.keyword = keyword;
    }

    public ArticleKeyword(Article article, Keyword keyword) {
        this.article = article;
        this.keyword = keyword;
    }
}
