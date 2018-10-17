package com.mnpost.app.data.source.remote;

import java.util.List;

public class UpdateTakeMailerSend {

    private String documentId;
    private List<String> mailers;


    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public List<String> getMailers() {
        return mailers;
    }

    public void setMailers(List<String> mailers) {
        this.mailers = mailers;
    }
}
