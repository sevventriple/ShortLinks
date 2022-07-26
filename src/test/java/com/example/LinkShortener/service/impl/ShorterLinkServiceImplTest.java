package com.example.LinkShortener.service.impl;

import com.example.LinkShortener.exception.InvalidUrlException;
import com.example.LinkShortener.model.LinkEntity;
import com.example.LinkShortener.repository.LinkRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShorterLinkServiceImplTest {
    @Mock
    private LinkRepository linkRepository;
    @InjectMocks
    private ShorterLinkServiceImpl shorterLinkService;

    @Test
    void createLinkEntityWithSave() {
        //Given
        String correctNonExistingUrl = "https://stackoverflow.com/";
        String existingRecord = "https://habr.com/";
        String incorrectUrl = "jgd;;;kkaf";
        LinkEntity existingLinkEntity = LinkEntity.builder()
                .shortLink("aaaaaa")
                .fullLink(existingRecord)
                .build();

        //When
        Mockito.when(linkRepository.findByFullLink(correctNonExistingUrl)).thenReturn(null);
        Mockito.when(linkRepository.findByFullLink(existingRecord)).thenReturn(existingLinkEntity);
        Mockito.when(linkRepository.save(Mockito.any())).thenReturn(null);

        //Then
        assertThrows(InvalidUrlException.class, () -> shorterLinkService.createLinkEntityWithSave(incorrectUrl));
        assertEquals(existingLinkEntity, shorterLinkService.createLinkEntityWithSave(existingRecord));
        assertNotNull(shorterLinkService.createLinkEntityWithSave(correctNonExistingUrl));
    }

    @Test
    void findByShortLink() {
        //Given
        String existingRecord = "https://existing/";
        String nonExistingRecord = "https://nonExisting/";

        //When
        Mockito.when(linkRepository.findByShortLink(existingRecord)).thenReturn(new LinkEntity());
        Mockito.when(linkRepository.findByShortLink(nonExistingRecord)).thenReturn(null);

        //Then
        assertNotNull(shorterLinkService.findByShortLink(existingRecord));
        assertNull(shorterLinkService.findByShortLink(nonExistingRecord));
    }
}