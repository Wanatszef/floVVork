package com.flovvorkServer.flovvorkServer.entity;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDate;

@Entity
@Table(name = "document")
public class Document
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private long documentId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "document_file")
    private String documentName;

    @Column(name = "active")
    private int active;

    @Column(name = "title")
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_values_id")
    private DocumentValues documentValues;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "previous_user_id")
    private User previousUser;

    @JoinColumn(name = "expire_date")
    private LocalDate expireDate;

    @JoinColumn(name = "create_date")
    private LocalDate createDate;

    @JoinColumn(name = "update_date")
    private LocalDate updateDate;

    public Document()
    {
    }

    public Document(User user, String documentName, int active, String title, DocumentValues documentValues, User previousUser, LocalDate expireDate, LocalDate createDate, LocalDate updateDate) {
        this.user = user;
        this.documentName = documentName;
        this.active = active;
        this.title = title;
        this.documentValues = documentValues;
        this.previousUser = previousUser;
        this.expireDate = expireDate;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public long getDocument_id() {
        return documentId;
    }

    public void setDocument_id(long document_id) {
        this.documentId = document_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public DocumentValues getDocumentValues() {
        return documentValues;
    }

    public void setDocumentValues(DocumentValues documentValues) {
        this.documentValues = documentValues;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getPreviousUser() {
        return previousUser;
    }

    public void setPreviousUser(User previousUser) {
        this.previousUser = previousUser;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "Document{" +
                "document_id=" + documentId +
                ", user=" + user +
                ", documentName='" + documentName + '\'' +
                ", active=" + active +
                ", title='" + title + '\'' +
                ", documentValues=" + documentValues +
                ", previousUser=" + previousUser +
                ", expireDate=" + expireDate +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
