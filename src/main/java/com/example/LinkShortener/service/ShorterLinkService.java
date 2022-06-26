package com.example.LinkShortener.service;

import com.example.LinkShortener.constant.Constant;
import com.example.LinkShortener.model.LinkEntity;
import com.example.LinkShortener.repository.LinkRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hashids.Hashids;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

@Slf4j
@AllArgsConstructor
@Service
public class ShorterLinkService {
    LinkRepository linkRepository;

    private String generateShortLink(LinkEntity linkEntity){
        Hashids hashids = new Hashids(linkEntity.getFullLink());
        return hashids.encode(linkEntity.getId());
    }

    public LinkEntity createLinkEntityWithSave(String fullLink){
        LinkEntity linkEntity = linkRepository.findByFullLink(fullLink);
        if(!fullLink.matches("(https|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]+")){
            log.warn("{} - is not correct url", fullLink);
            return null;
        }
        if(linkEntity != null){
            return linkEntity;
        }
        linkEntity = new LinkEntity();
        linkRepository.save(linkEntity);
        linkEntity.setFullLink(fullLink);
        linkEntity.setShortLink(generateShortLink(linkEntity));
        linkRepository.save(linkEntity);
        return linkEntity;
    }

    public LinkEntity findByShortLink(String shortLink){
        return linkRepository.findByShortLink(shortLink);
    }
}
