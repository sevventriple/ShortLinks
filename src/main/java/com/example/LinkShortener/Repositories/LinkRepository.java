package com.example.LinkShortener.Repositories;

import com.example.LinkShortener.Models.LinkShortener;
import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends CrudRepository<LinkShortener, Long> {
    LinkShortener findByShortLink(String shortLink);
    LinkShortener findByFullLink(String fullLink);
}
