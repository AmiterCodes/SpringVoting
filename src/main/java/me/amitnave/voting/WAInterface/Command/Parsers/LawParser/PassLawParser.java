package me.amitnave.voting.WAInterface.Command.Parsers.LawParser;

import com.mysql.cj.util.StringUtils;
import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Command.VotingCommands.Law.PassLaw;
import me.amitnave.voting.WAInterface.Message.Message;
import me.amitnave.voting.databaseObjects.Law;
import me.amitnave.voting.databaseObjects.Member;

import java.sql.SQLException;

public class PassLawParser implements CommandParser {

    private final String LAW_SUGGESTION = "#הצעת חוק";
    private final String ANNONYMOUS_VOTE = "#הצבעה אנונימית";
    private final String ANNONYMOUS_SOURCE = "#מקור אנונימי";
    private String content;
    private int memberID;
    private boolean anonymousVoting;
    private boolean anonymousCretor;

    @Override
    public boolean isLegalCommand(Message message) throws SQLException {
        if (!message.getContent().startsWith(LAW_SUGGESTION) || !Member.isMember(message.getChatID()) ||
                !message.isPrivate()) {
            return false;
        }
        memberID=new Member(message.getChatID()).getId();
        String[] rows = message.getContent().split("\n");
        if(rows.length < 2) return false;
        if (rows[1].equals(ANNONYMOUS_VOTE)) {
            anonymousVoting = true;
            if (rows[2].equals(ANNONYMOUS_SOURCE)) {
                anonymousCretor = true;
                String s = "";
                for (int i = 3; i < rows.length; i++) {
                    s += rows[i];
                    s+=" ";
                }
                content=s;
            } else {
                anonymousCretor = false;
                String s = "";
                for (int i = 2; i < rows.length; i++) {
                    s += rows[i];
                    s+=" ";
                }
                content=s;
            }
        }
        else {
            if (rows[1].equals(ANNONYMOUS_SOURCE)){
                anonymousCretor=true;
                if (rows[2].equals(ANNONYMOUS_VOTE)){
                    anonymousVoting=true;
                    String s = "";
                    for (int i = 3; i < rows.length; i++) {
                        s += rows[i];
                        s+=" ";
                    }
                    content=s;
                }
                else {
                    anonymousVoting=false;
                    String s = "";
                    for (int i = 2; i < rows.length; i++) {
                        s += rows[i];
                        s+=" ";
                    }
                    content=s;
                }
            }
            else {
                anonymousCretor=false;
                anonymousVoting=false;
                String s = "";
                for (int i = 1; i < rows.length; i++) {
                    s += rows[i];
                    s+=" ";
                }
                content=s;
            }
        }
        return true;
    }

    @Override
    public VotingCommand getCommand() {
        return new PassLaw(new Law(content,memberID, Law.inProcess, Law.now(),anonymousVoting,anonymousCretor));
    }
}
