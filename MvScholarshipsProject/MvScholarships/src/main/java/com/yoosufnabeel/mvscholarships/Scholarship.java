package com.yoosufnabeel.mvscholarships;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ynabyl on 8/29/13.
 */
public class Scholarship {
    public String name;
    public int icon;
    public String link;
    public String date;
    public ArrayList<ScholarshipDocument> documents;

    public Scholarship(int icon, String name) {
        super();
        this.icon = icon;
        this.name = name;
        documents = new ArrayList<ScholarshipDocument>();
    }

    public Scholarship(int icon, String name, ArrayList<ScholarshipDocument> documents) {
        super();
        this.icon = icon;
        this.name = name;
        this.documents = documents;
    }

    public Scholarship() {
        super();
        this.icon = R.drawable.scho_logo_new;
        this.name = "";
    }
}

