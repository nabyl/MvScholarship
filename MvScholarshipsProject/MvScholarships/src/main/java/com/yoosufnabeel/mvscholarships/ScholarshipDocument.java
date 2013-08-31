package com.yoosufnabeel.mvscholarships;

/**
 * Created by ynabyl on 8/30/13.
 */
public class ScholarshipDocument {
    public String name ;
    public String url ;

    public ScholarshipDocument(String url, String name) {
        super();
        this.name = name;
        this.url = url;
    }
    public ScholarshipDocument() {
        super();
        this.name = "";
        this.url ="";
    }
}