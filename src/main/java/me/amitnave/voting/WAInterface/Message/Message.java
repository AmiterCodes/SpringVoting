package me.amitnave.voting.WAInterface.Message;


public class Message extends MessageToSend {
    private String repliedMessage;
    private boolean repliedToBot;
    private String  sender;

    public String getSender() {
        return sender;
    }

    public boolean isPrivate() {
        return sender.equals(chatID);
    }

    public boolean isRepliedToBot() {
        return repliedToBot;
    }

    public Message(String content, String chatID, String repliedMessage, boolean repliedToBot,
                   String sender) {
        super(content, chatID);
        this.repliedMessage = repliedMessage;
        this.repliedToBot = repliedToBot;
        this.sender = sender;
    }

    public String getRepliedMessage() {
        return repliedMessage;
    }
}
