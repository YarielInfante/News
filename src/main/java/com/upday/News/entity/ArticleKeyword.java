package com.upday.News.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleKeyword {

    @Id
    private long id;
    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;
    @ManyToOne
    @JoinColumn(name = "keyword_id", referencedColumnName = "id")
    private Keyword keyword;
}
