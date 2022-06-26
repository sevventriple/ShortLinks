package com.example.LinkShortener.controller;

import com.example.LinkShortener.model.LinkEntity;
import com.example.LinkShortener.repository.LinkRepository;
import com.example.LinkShortener.service.ShorterLinkService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.net.URI;

@AllArgsConstructor
@RestController
public class ShorterLinkController {
    private ShorterLinkService shorterLinkService;

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LinkEntity> createShortLink(String fullLink){
        LinkEntity linkEntity = shorterLinkService.createLinkEntityWithSave(fullLink);
        return linkEntity != null
                ? new ResponseEntity<>(linkEntity, HttpStatus.OK)
                : new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/{shortLink}")
    public ResponseEntity<Void> redirect(@PathVariable String shortLink){
        LinkEntity linkEntity = shorterLinkService.findByShortLink(shortLink);
        return linkEntity != null
                ? ResponseEntity.status(HttpStatus.FOUND).location(URI.create(linkEntity.getFullLink())).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(path = "/getFullByShort")
    public LinkEntity getFullLink(String shortLink){
        return shorterLinkService.findByShortLink(shortLink);
    }
}
