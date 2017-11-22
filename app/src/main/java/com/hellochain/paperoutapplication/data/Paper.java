package com.hellochain.paperoutapplication.data;

import io.realm.RealmObject;

/**
 * Created by boxfox on 2017-11-22.
 */

public class Paper extends RealmObject {
    private String papaerName;
    private String paperUrl;

    public String getPapaerName() {
        return papaerName;
    }

    public void setPapaerName(String papaerName) {
        this.papaerName = papaerName;
    }

    public String getPaperUrl() {
        return paperUrl;
    }

    public void setPaperUrl(String paperUrl) {
        this.paperUrl = paperUrl;
    }
}
