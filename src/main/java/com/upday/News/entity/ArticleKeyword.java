package com.upday.News.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Represents the relationship between Article entity and Keyword entity.
 *
 * @see Article
 * @see Keyword
 * @see Entity
 * @see lombok
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "article_keyword")
public class ArticleKeyword implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ArticleKeywordPK articleKeywordPK = new ArticleKeywordPK();
    @JoinColumn(name = "article_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Article article;
    @JoinColumn(name = "keyword_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Keyword keyword;

    public ArticleKeyword(ArticleKeywordPK articleKeywordPK) {
        this.articleKeywordPK = articleKeywordPK;
    }

    public ArticleKeyword(Article article, Keyword keyword) {
        this.article = article;
        this.keyword = keyword;
    }
}
