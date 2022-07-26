package com.example.LinkShortener.controller.impl;

import com.example.LinkShortener.controller.ShorterLinkController;
import com.example.LinkShortener.model.LinkEntity;
import com.example.LinkShortener.service.impl.ShorterLinkServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@AllArgsConstructor
@RestController
public class ShorterLinkControllerImpl implements ShorterLinkController {
    private ShorterLinkServiceImpl shorterLinkService;

    @Override
    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LinkEntity> createShortLink(String fullLink) {
        LinkEntity linkEntity = shorterLinkService.createLinkEntityWithSave(fullLink);
        return linkEntity != null
                ? new ResponseEntity<>(linkEntity, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    @GetMapping(path = "/{shortLink}")
    public ResponseEntity<Void> redirect(@PathVariable String shortLink) {
        LinkEntity linkEntity = shorterLinkService.findByShortLink(shortLink);
        return linkEntity != null
                ? ResponseEntity.status(HttpStatus.FOUND).location(URI.create(linkEntity.getFullLink())).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    @PostMapping(path = "/getFullByShort")
    public LinkEntity getFullLink(String shortLink) {
        return shorterLinkService.findByShortLink(shortLink);
    }
}
