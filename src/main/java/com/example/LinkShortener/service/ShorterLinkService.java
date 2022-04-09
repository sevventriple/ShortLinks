package com.example.LinkShortener.service;

import com.example.LinkShortener.constant.Constant;
import com.example.LinkShortener.model.LinkEntity;
import com.example.LinkShortener.repository.LinkRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Slf4j
@AllArgsConstructor
@Service
public class ShorterLinkService {
    static String generatedShortLink;

    LinkRepository linkRepository;

    private boolean isLinkValid(String fullLink){
        if(fullLink.startsWith(Constant.URL_SCHEMES[0]) || fullLink.startsWith(Constant.URL_SCHEMES[1])){
            return true;
        }
        else {
            log.info("Link \"{}\" is not valid", fullLink);
            return false;
        }
    }

    private static String generateShortLink(LinkEntity lastSavedLinkEntity){
        if(lastSavedLinkEntity == null){
            generatedShortLink = "aaaaaa";
            return generatedShortLink;
        }

        int indexOfLastInsertIntoShortLink = (int) (lastSavedLinkEntity.getId() / Constant.NUMBER_OF_LINK_GENERATION_CHARACTERS);
        int indexOfLastSymbolInsertedIntoShortLink = (int) (lastSavedLinkEntity.getId() % Constant.NUMBER_OF_LINK_GENERATION_CHARACTERS) - 1;
        String buffer = lastSavedLinkEntity.getShortLink();

        generatedShortLink = buffer.substring(0, indexOfLastInsertIntoShortLink) +
                Constant.LINK_GENERATION_CHARACTERS.charAt(indexOfLastSymbolInsertedIntoShortLink)
                + buffer.substring(indexOfLastInsertIntoShortLink + 1);
        return generatedShortLink;
    }

    private LinkEntity createLinkEntityWithSave(String fullLink){
        LinkEntity linkEntity = linkRepository.findByFullLink(fullLink);
        if(isLinkValid(fullLink) && linkEntity == null){
            LinkEntity lastSavedLinkEntity = linkRepository.findTopByOrderByIdDesc();
            String shortLink = generateShortLink(lastSavedLinkEntity);
            linkEntity = new LinkEntity().builder()
                    .shortLink(shortLink)
                    .fullLink(fullLink)
                    .build();

            linkRepository.save(linkEntity);
        }

        return linkEntity;
    }

    public ResponseEntity<LinkEntity> createNewShortLink(String fullLink){
        LinkEntity linkEntity = createLinkEntityWithSave(fullLink);
        if(linkEntity == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(linkEntity, HttpStatus.OK);
    }

    public ResponseEntity<LinkEntity> getFullLinkByShortLink(String shortLink){
        LinkEntity linkEntity = linkRepository.findByShortLink(shortLink);
        if(linkEntity != null){
            return new ResponseEntity<>(linkEntity, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Void> redirectToFullLink(String shortLink){
        LinkEntity linkEntity = linkRepository.findByShortLink(shortLink);
        if(linkEntity != null){
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(linkEntity.getFullLink())).build();
        }
        return ResponseEntity.notFound().build();
    }
}
