package com.example.LinkShortener.controller;

import com.example.LinkShortener.model.LinkEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(
        name = "ShorterLinkController",
        description = "контроллер предоставляющий основные методы для работы с короткими ссылками"
)
public interface ShorterLinkController {

    @Operation(
            summary = "Создание новой сокращенной ссылки",
            description = "Позволяет создать новую сокращенную ссылку из полной"
    )
    ResponseEntity<LinkEntity> createShortLink(@Parameter(description = "Полная ссылка") String fullLink);

    @Operation(
            summary = "Переход по короткой ссылке",
            description = "Позволяет перейти по полной ссылке, к которой привязана  данная короткая ссылка"
    )
    ResponseEntity<Void> redirect(@PathVariable @Parameter(description = "Сокращенная ссылка") String shortLink);

    @Operation(
            summary = "Получить полную ссылку",
            description = "Позволяет получить полную ссылку по привязанной к ней короткой ссылке"
    )
    LinkEntity getFullLink(@Parameter(description = "Сокращенная ссылка") String shortLink);
}
