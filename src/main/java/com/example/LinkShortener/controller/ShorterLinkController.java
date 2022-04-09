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
        return shorterLinkService.createNewShortLink(fullLink);
    }

    @GetMapping(path = "/{shortLink}")
    public ResponseEntity<Void> redirect(@PathVariable String shortLink){
        return shorterLinkService.redirectToFullLink(shortLink);
    }

    @PostMapping(path = "/getFullByShort")
    public ResponseEntity<LinkEntity> getFullLink(String shortLink){
        return shorterLinkService.getFullLinkByShortLink(shortLink);
    }
}
