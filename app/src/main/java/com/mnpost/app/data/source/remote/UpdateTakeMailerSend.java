package com.mnpost.app.data.source.remote;

import java.util.List;

public class UpdateTakeMailerSend {

    private String documentId;
    private String mailers;
    private float weight;


    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }


    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getMailers() {
        return mailers;
    }

    public void setMailers(String mailers) {
        this.mailers = mailers;
    }
}
