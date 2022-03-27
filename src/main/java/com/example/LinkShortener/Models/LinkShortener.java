package com.example.LinkShortener.Models;

import javax.persistence.*;

@Entity
@Table(name = "links")
public class LinkShortener {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_link")
    private String fullLink;

    @Column(name = "short_link")
    private String shortLink;

    protected LinkShortener() {}

    public LinkShortener(String fullLink){
        this.fullLink = fullLink;
        CodeGenerator codeGenerator = new CodeGenerator();
        shortLink = codeGenerator.generateNewLink();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getFullLink() {
        return fullLink;
    }

    public void setFullLink(String fullLink) {
        this.fullLink = fullLink;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }
}
