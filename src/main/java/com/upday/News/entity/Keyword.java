package com.upday.News.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Represents a Keyword Entity.
 *
 * @see Entity
 * @see lombok
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Keyword {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Size(min = 1, max = 300)
    private String keyword;

    public Keyword(@Size(min = 1, max = 300) String keyword) {
        this.keyword = keyword;
    }
}
