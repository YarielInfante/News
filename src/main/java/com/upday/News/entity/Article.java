package com.upday.News.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String header;
    @Column(name = "short_description")
    private String shortDescription;
    @Column(name = "publish_date")
    private Date publishDate;
    private String author;
    @OneToMany(mappedBy = "keyword")
    private Collection<ArticleKeyword> keywords;

}
