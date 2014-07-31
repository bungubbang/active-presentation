package com.active.presentation.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1000742
 * Email: sungyong.jung@sk.com
 * Date: 2014. 7. 25.
 */
@Entity
public class Tag {

    @Id @GeneratedValue
    private Long id;

    private String name;

    public Tag() {}

    public Tag(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
