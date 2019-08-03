package com.upday.News.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ArticleAuthorPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "article_id")
    private long articleId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "author_id")
    private long authorId;


}
