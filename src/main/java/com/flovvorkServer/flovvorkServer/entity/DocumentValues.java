package com.flovvorkServer.flovvorkServer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "document_values")
@SequenceGenerator(name = "document_values_seq", sequenceName = "document_values_seq", allocationSize = 1)
public class DocumentValues
{
    @Id
    @Column(name = "document_values_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int documentValuesId = 0;

    @Column(name = "text1")
    private String text1;

    @Column(name = "text2")
    private String text2;

    @Column(name = "text3")
    private String text3;

    @Column(name = "text4")
    private String text4;

    @Column(name = "text5")
    private String text5;

    @Column(name = "text6")
    private String text6;

    @Column(name = "text9")
    private String text9;

    @Column(name = "text7")
    private String text7;

    @Column(name = "text8")
    private String text8;

    @Column(name = "text10")
    private String text10;

    @Column(name = "number1")
    private Integer number1;
    @Column(name = "number2")
    private Integer number2;
    @Column(name = "number3")
    private Integer number3;
    @Column(name = "number4")
    private Integer number4;
    @Column(name = "number5")
    private Integer number5;
    @Column(name = "number6")
    private Integer number6;
    @Column(name = "number7")
    private Integer number7;

    public DocumentValues()
    {
        documentValuesId = 0;
        number1 = 0;
        number2 = 0;
        number3 = 0;
        number4 = 0;
        number5 = 0;
        number6 = 0;
        number7 = 0;
    }

    public int getDocumentValuesId() {
        return documentValuesId;
    }

    public void setDocumentValuesId(int documentValuesId) {
        this.documentValuesId = documentValuesId;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String getText4() {
        return text4;
    }

    public void setText4(String text4) {
        this.text4 = text4;
    }

    public String getText5() {
        return text5;
    }

    public void setText5(String text5) {
        this.text5 = text5;
    }

    public String getText6() {
        return text6;
    }

    public void setText6(String text6) {
        this.text6 = text6;
    }

    public String getText9() {
        return text9;
    }

    public void setText9(String text9) {
        this.text9 = text9;
    }

    public String getText7() {
        return text7;
    }

    public void setText7(String text7) {
        this.text7 = text7;
    }

    public String getText8() {
        return text8;
    }

    public void setText8(String text8) {
        this.text8 = text8;
    }

    public String getText10() {
        return text10;
    }

    public void setText10(String text10) {
        this.text10 = text10;
    }

    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public int getNumber2() {
        return number2;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }

    public int getNumber3() {
        return number3;
    }

    public void setNumber3(int number3) {
        this.number3 = number3;
    }

    public int getNumber4() {
        return number4;
    }

    public void setNumber4(int number4) {
        this.number4 = number4;
    }

    public int getNumber5() {
        return number5;
    }

    public void setNumber5(int number5) {
        this.number5 = number5;
    }

    public int getNumber6() {
        return number6;
    }

    public void setNumber6(int number6) {
        this.number6 = number6;
    }

    public int getNumber7() {
        return number7;
    }

    public void setNumber7(int number7) {
        this.number7 = number7;
    }

    @Override
    public String toString() {
        return "DocumentValues{" +
                "documentValuesId=" + documentValuesId +
                ", text1='" + text1 + '\'' +
                ", text2='" + text2 + '\'' +
                ", text3='" + text3 + '\'' +
                ", text4='" + text4 + '\'' +
                ", text5='" + text5 + '\'' +
                ", text6='" + text6 + '\'' +
                ", text9='" + text9 + '\'' +
                ", text7='" + text7 + '\'' +
                ", text8='" + text8 + '\'' +
                ", text10='" + text10 + '\'' +
                ", number1=" + number1 +
                ", number2=" + number2 +
                ", number3=" + number3 +
                ", number4=" + number4 +
                ", number5=" + number5 +
                ", number6=" + number6 +
                ", number7=" + number7 +
                '}';
    }

    public DocumentValues(String text1, String text2, String text3, String text4, String text5, String text6, String text9, String text7, String text8, String text10, int number1, int number2, int number3, int number4, int number5, int number6, int number7) {
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.text4 = text4;
        this.text5 = text5;
        this.text6 = text6;
        this.text9 = text9;
        this.text7 = text7;
        this.text8 = text8;
        this.text10 = text10;
        this.number1 = number1;
        this.number2 = number2;
        this.number3 = number3;
        this.number4 = number4;
        this.number5 = number5;
        this.number6 = number6;
        this.number7 = number7;
    }
}
