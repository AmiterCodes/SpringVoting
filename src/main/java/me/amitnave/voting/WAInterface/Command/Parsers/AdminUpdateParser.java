package me.amitnave.voting.WAInterface.Command.Parsers;

import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.Message;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.databaseObjects.DBHelper;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class AdminUpdateParser implements CommandParser {
    private String response;
    private String query;
    private Message message;
    @Override
    public boolean isLegalCommand(Message message) throws SQLException, ParseException {
        if(!message.isPrivate()) return false;
        if(!message.getSender().equals("972544805278@c.us")) return false;
        if(!message.getContent().startsWith("#SQLU") && !message.getContent().startsWith("#SQLI")) return false;
        if(message.getContent().length() < 6) return false;
        query = message.getContent().substring(6);
        this.message = message;
        return true;
    }

    @Override
    public VotingCommand getCommand() throws SQLException, ParseException {
        return new VotingCommand() {
            @Override
            public void DatabaseAction() throws SQLException, ParseException {
                try {
                    int changes;
                    if (message.getContent().charAt(4) == 'U') {
                        changes = DBHelper.update(query);
                    } else if (message.getContent().charAt(4) == 'I') {
                        changes = DBHelper.insert(query);
                    } else {
                        return;
                    }
                    response = changes + " rows have been changed";
                } catch (SQLException e) {
                    response =  "קרתה בעיה\n" + e.getMessage();
                }
            }

            @Override
            public List<MessageToSend> message() throws SQLException, ParseException {
                return List.of(new MessageToSend(response, message.getChatID()));
            }
        };
    }
}
