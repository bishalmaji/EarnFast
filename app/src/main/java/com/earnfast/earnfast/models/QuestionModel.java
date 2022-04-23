package com.earnfast.earnfast.models;

public class QuestionModel {
    public QuestionModel(){

    }
    public QuestionModel(String a, String question, String b, String c, String d, Long type) {
        this.a = a;
        this.question = question;
        this.b = b;
        this.c = c;
        this.d = d;
        this.type = type;
    }

    private String a;
    private String question;
    private String b;
    private String c;
    private String d;
    private Long type;


    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
