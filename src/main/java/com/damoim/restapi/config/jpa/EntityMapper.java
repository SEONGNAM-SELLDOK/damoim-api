package com.damoim.restapi.config.jpa;

/**
 * EntityMapper
 *
 * @author incheol.jung
 * @since 2021. 02. 20.
 */
public interface EntityMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);
}
