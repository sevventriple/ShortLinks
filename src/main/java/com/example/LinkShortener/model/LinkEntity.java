package com.example.LinkShortener.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "links")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class LinkEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_link")
    private String fullLink;

    @Column(name = "short_link")
    private String shortLink;

    public LinkEntity() {

    }




}
