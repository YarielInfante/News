package com.upday.News.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

/**
 * Represents a Keyword Entity.
 *
 * @see Entity
 * @see lombok
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "keyword")
public class Keyword implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String keyword;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "keyword")
    private Collection<ArticleKeyword> articleKeywordCollection;


    public Keyword(long id) {
        this.id = id;
    }

    public Keyword(long id, @NotNull @Size(min = 1, max = 255) String keyword) {
        this.id = id;
        this.keyword = keyword;
    }
}
