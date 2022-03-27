package com.example.LinkShortener.controllers;

import com.example.LinkShortener.Models.LinkShortener;
import com.example.LinkShortener.Repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class ShorterLinkController {
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

            linkRepository.save(linkShortener);
        }

        return linkShortener;
    }

    @GetMapping(path = "/{hash}")
    public ResponseEntity redirect(@PathVariable String hash){
        LinkShortener linkShortener = linkRepository.findByShortLink(hash);

        if(linkShortener != null){
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(linkShortener.getFullLink())).build();
        }
        else{
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping(path = "/full")
    public LinkShortener getFullLink(String shortLink){
        LinkShortener linkShortener = linkRepository.findByShortLink(shortLink);
        return linkShortener;
    }
}
