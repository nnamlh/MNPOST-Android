package com.mnpost.app.data.source.remote;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HAI on 9/15/2017.
 */

public class NotificationInfo {

    @SerializedName("id")
    private String id ;
    @SerializedName("time")
    private String time ;
    @SerializedName("title")
    private String title ;
    @SerializedName("messenger")
    private String messenger;
    @SerializedName("isRead")
    private boolean isRead;
    @SerializedName("content")
    private String content ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessenger() {
        return messenger;
    }

    public void setMessenger(String messenger) {
        this.messenger = messenger;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
