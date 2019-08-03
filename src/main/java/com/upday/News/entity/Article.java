package com.upday.News.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

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
    private String text;
    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ArticleKeyword> keywords;
    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ArticleAuthor> authors;

}
