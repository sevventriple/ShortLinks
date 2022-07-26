package com.example.LinkShortener.service;

import com.example.LinkShortener.model.LinkEntity;

public interface ShorterLinkService {
    LinkEntity createLinkEntityWithSave(String fullLink);

    LinkEntity findByShortLink(String shortLink);
}
