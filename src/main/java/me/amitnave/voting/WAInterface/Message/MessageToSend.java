package me.amitnave.voting.WAInterface.Message;

public class MessageToSend {
    protected String content;
    protected String chatID;

    public MessageToSend(String content, String destinationID) {
        this.content = content;
        this.chatID = destinationID;

    }

    public String getContent() {
        return content;
    }

    public String getChatID() {
        return chatID;
    }
}
