package com.mnpost.app.data.source.remote;

import java.util.List;

public class UpdateDeliverySend {

    private String MailerID ;

    private String DocumentID ;

    private int StatusID ;

    private String Reciever ;

    private String DeliveryDate ; // dd/MM/yyyy HH:mm

    private String ReturnReasonID ;

    private List<String> images ;

    private String Note;


    public String getMailerID() {
        return MailerID;
    }

    public void setMailerID(String mailerID) {
        MailerID = mailerID;
    }

    public String getDocumentID() {
        return DocumentID;
    }

    public void setDocumentID(String documentID) {
        DocumentID = documentID;
    }

    public int getStatusID() {
        return StatusID;
    }

    public void setStatusID(int statusID) {
        StatusID = statusID;
    }

    public String getReciever() {
        return Reciever;
    }

    public void setReciever(String reciever) {
        Reciever = reciever;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }



    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getReturnReasonID() {
        return ReturnReasonID;
    }

    public void setReturnReasonID(String returnReasonID) {
        ReturnReasonID = returnReasonID;
    }
}
