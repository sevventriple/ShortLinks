package com.example.LinkShortener.service.impl;

import com.example.LinkShortener.exception.InvalidUrlException;
import com.example.LinkShortener.model.LinkEntity;
import com.example.LinkShortener.repository.LinkRepository;
import com.example.LinkShortener.service.ShorterLinkService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hashids.Hashids;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class ShorterLinkServiceImpl implements ShorterLinkService {
    private LinkRepository linkRepository;

    @Override
    public LinkEntity createLinkEntityWithSave(String fullLink) {
        LinkEntity linkEntity = linkRepository.findByFullLink(fullLink);
        if (linkEntity != null) {
            return linkEntity;
        }
        validateUrl(fullLink);
        linkEntity = LinkEntity.builder()
                .fullLink(fullLink)
                .shortLink(generateShortLink(fullLink))
                .build();
        linkRepository.save(linkEntity);
        return linkEntity;
    }

    @Override
    public LinkEntity findByShortLink(String shortLink) {
        return linkRepository.findByShortLink(shortLink);
    }

    private String generateShortLink(String fullLink) {
        Hashids hashids = new Hashids(fullLink);
        return hashids.encode(1);
    }

    private void validateUrl(String url) {
        if (!url.matches("(https|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]+")) {
            log.warn("{} - is not correct url", url);
            throw new InvalidUrlException();
        }
    }
}
