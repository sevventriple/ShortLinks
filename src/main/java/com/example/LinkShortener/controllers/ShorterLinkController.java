package com.example.LinkShortener.controllers;

import com.example.LinkShortener.Models.LinkShortener;
import com.example.LinkShortener.Repositories.LinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class ShorterLinkController {
    Logger logger = LoggerFactory.getLogger(ShorterLinkController.class);

    private final LinkRepository linkRepository;

    @Autowired
    public ShorterLinkController(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public LinkShortener createShortLink(String fullLink){
        LinkShortener linkShortener = linkRepository.findByFullLink(fullLink);

        if(linkShortener == null){
            while (true){
                linkShortener = new LinkShortener(fullLink);

                if(linkRepository.findByShortLink(linkShortener.getShortLink()) == null){
                    break;
                }
            }
            logger.info(linkShortener.toString());

            linkRepository.save(linkShortener);
        }

        return linkShortener;
    }

    @GetMapping(path = "/{shortLink}")
    public ResponseEntity redirect(@PathVariable String shortLink){
        LinkShortener linkShortener = linkRepository.findByShortLink(shortLink);

        if(linkShortener != null){
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(linkShortener.getFullLink())).build();
        }
        else{
            logger.info("short link: " + shortLink + ", was not found");
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping(path = "/full")
    public LinkShortener getFullLink(String shortLink){
        LinkShortener linkShortener = linkRepository.findByShortLink(shortLink);

        if(linkShortener == null){
            logger.warn("Object was not found");
        }

        return linkShortener;
    }
}
