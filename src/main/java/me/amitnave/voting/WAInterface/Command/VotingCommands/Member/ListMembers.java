package me.amitnave.voting.WAInterface.Command.VotingCommands.Member;

import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.MessageStructure;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.databaseObjects.Member;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class ListMembers implements VotingCommand {
    public ListMembers(String askerId, boolean activeOnly) {
        this.askerId = askerId;
        this.activeOnly = activeOnly;
    }

    @Override
    public void DatabaseAction() throws SQLException, ParseException {

    }
    private String askerId;
    private boolean activeOnly;

    public String padLeftSpaces(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();


        while (sb.length() < length - inputString.length()) {
            sb.append(' ');
        }

        return inputString +  sb.toString();
    }
    @Override
    public List<MessageToSend> message() throws SQLException, ParseException {
        List<Member> members = Member.getMembers();
        final MessageStructure structure = new MessageStructure();
        structure.addToLastRow(activeOnly ? "*חברי מועצה*" : "*חברי רייך במאגר*");
        structure.addRow("חבר במועצה או הנשיא - " + "\uD83D\uDFE2");
        structure.addRow("חבר רייך רגיל - " + "\uD83D\uDD34");
        structure.addRow("");
        members.stream().filter(member -> (!activeOnly) || member.isActive()).forEach(member -> {
            structure.addRow((!activeOnly ? (member.isActive() ? "\uD83D\uDFE2" : "\uD83D\uDD34"): "") + member.getName());
        });
        return List.of(new MessageToSend(structure.getString(), askerId));
    }
}
