package com.damoim.restapi.boards.entity;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author gisung go
 * @since 2021-02-22
 */
@Embeddable
@Getter
@Setter
public class DamoimTag {

    private String tag;

    protected DamoimTag() {
    }

    public DamoimTag(String tag) {
        this.tag = tag;
    }
}
