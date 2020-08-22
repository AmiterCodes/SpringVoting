package me.amitnave.voting.WAInterface.Command.Parsers.LawParser;

import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Command.VotingCommands.Law.PassLaw;
import me.amitnave.voting.WAInterface.Message.Message;
import me.amitnave.voting.WAInterface.general.Settings;
import me.amitnave.voting.databaseObjects.Law;
import me.amitnave.voting.databaseObjects.Member;

import java.sql.SQLException;

public class PassLawParser implements CommandParser {

    private static final String LAW_SUGGESTION = "#הצעת חוק";
    private static final String ANONYMOUS_VOTE = "#הצבעה אנונימית";
    private static final String ANONYMOUS_SOURCE = "#מקור אנונימי";
    private static final String FAKE_NAME = "#שם בדוי ";

    private String content;
    private int memberID;
    private boolean anonymousVoting;
    private boolean anonymousCreator;
    private boolean hasFakeName;
    private String fakeName = "";

    @Override
    public boolean isLegalCommand(Message message) throws SQLException {
        if (!message.getContent().startsWith(LAW_SUGGESTION) ||
                (!Member.isMember(message.getChatID()) && !message.getChatID().equals(Settings.getPresidentChatID())) ||
                !message.isPrivate()) {
            return false;
        }
        memberID = new Member(message.getChatID()).getId();
        content = message.getContent();
        String[] rows = message.getContent().split("\n");
        if (rows.length < 2) return false;
        content = content.replaceFirst(LAW_SUGGESTION + "\n", "");
        if(content.contains(ANONYMOUS_SOURCE + "\n")) {
            anonymousCreator = true;
            content = content.replaceFirst(ANONYMOUS_SOURCE +"\n", "");
        }
        if(content.contains(ANONYMOUS_VOTE +"\n")) {
            anonymousVoting = true;
            content = content.replaceFirst(ANONYMOUS_VOTE+"\n", "");
        }


        if(content.startsWith(FAKE_NAME)) {
            content = content.replaceFirst(FAKE_NAME, "");
            rows = content.split("\n");
            fakeName = rows[0];
            hasFakeName = true;
            content = content.replaceFirst(fakeName, "");
        }

        if(content.startsWith("\n")) {
            content = content.replaceFirst("\n", "");
        }

        return true;
    }

    @Override
    public VotingCommand getCommand() {
        return new PassLaw(new Law(content, memberID, Law.inProcess, Law.now(), anonymousVoting, anonymousCreator, hasFakeName, fakeName));
    }
}
