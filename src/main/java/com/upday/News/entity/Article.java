package com.upday.News.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

/**
 * Represents an Article entity.
 *
 * @see Entity
 * @see lombok
 */
@Entity
@Getter
@Setter
@Builder
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
    private String text;
    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<ArticleKeyword> keywords;

}
