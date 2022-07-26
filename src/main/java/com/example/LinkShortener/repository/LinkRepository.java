package com.example.LinkShortener.repository;

import com.example.LinkShortener.model.LinkEntity;
import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends CrudRepository<LinkEntity, Long> {
    LinkEntity findByShortLink(String shortLink);

    LinkEntity findByFullLink(String fullLink);
}
