package me.amitnave.voting.WAInterface.Command.Parsers.AppealParser;

import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Command.VotingCommands.Appeal.AnswerAppeal;
import me.amitnave.voting.WAInterface.Message.Message;
import me.amitnave.voting.WAInterface.general.Settings;
import me.amitnave.voting.databaseObjects.Appeal;

import java.sql.SQLException;

public class AnswerAppealParser implements CommandParser {
    private static final String approved="התקבל";
    private static final String rejected="נדחה";
    private Appeal appeal;
    private boolean appealApproved;
    private String reason;
    @Override
    public boolean isLegalCommand(Message message) throws SQLException {
        if (!message.getContent().contains("\n")||!message.getChatID().equals(Settings.getPresidentChatID())) return false;
        String header=message.getContent().split("\n")[0];
        if (header.equals(approved)) appealApproved=true;
        else if (header.equals(rejected)) appealApproved=false;
        else return false;
        if (!message.getRepliedMessage().contains("\n")) return false;
        String repliedAppealHeader=message.getRepliedMessage().substring(0,message.getRepliedMessage().indexOf('\n'));
        int appealID =Integer.parseInt(repliedAppealHeader.substring(repliedAppealHeader.indexOf('#') + 1));
        appeal=new Appeal(appealID);
        if (appeal.getReason()==null) return false;
        reason=message.getContent().substring(message.getContent().indexOf('\n'));
        return true;
    }

    @Override
    public VotingCommand getCommand() throws SQLException {
        return new AnswerAppeal(appeal,appealApproved,reason);
    }

}
