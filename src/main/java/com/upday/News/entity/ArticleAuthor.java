package com.upday.News.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleAuthor {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ArticleAuthorPK articleAuthorPK = new ArticleAuthorPK();
    @JoinColumn(name = "article_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Article article;
    @JoinColumn(name = "author_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Author author;

    public ArticleAuthor(ArticleAuthorPK articleAuthorPK) {
        this.articleAuthorPK = articleAuthorPK;
    }

    public ArticleAuthor(Article article, Author author) {
        this.article = article;
        this.author = author;
    }
}
