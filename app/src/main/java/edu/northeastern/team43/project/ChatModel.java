package edu.northeastern.team43.project;

import java.io.Serializable;

public class ChatModel implements Serializable{
    public String receiverEmail;
    public String senderEmail;

    public String message;
    public String date;
    public String chatId;

    public ChatModel(String chatId, String senderEmail, String receiverEmail, String message, String date){
        this.chatId=chatId;
        this.senderEmail=senderEmail;
        this.receiverEmail=receiverEmail;
        this.message=message;
        this.date=date;
    }

    public ChatModel(){}

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public static class Builder implements Serializable{
        public String receiverEmail;
        public String senderEmail;
        public String message;
        public String date;
        public String chatId;

    public Builder receiverEmail(String receiverEmail){
        this.receiverEmail=receiverEmail;
        return this;
    }
    public Builder senderEmail(String senderEmail){
        this.senderEmail=senderEmail;
        return this;
    }

    public Builder date(String date){
        this.date=date;
        return this;
    }
    public Builder message(String message){
        this.message=message;
        return this;
    }

    public Builder chatId(String chatId){
        this.chatId=chatId;
        return this;
    }

    public ChatModel build(){
        return new ChatModel(chatId,senderEmail,receiverEmail,message,date);
    }
    }




}
