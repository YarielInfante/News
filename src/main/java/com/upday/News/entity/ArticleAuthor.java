package com.upday.News.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Represents the relationship between Article entity and Author entity.
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
public class ArticleAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public ArticleAuthor(Article article, Author author) {
        this.article = article;
        this.author = author;
    }
}
