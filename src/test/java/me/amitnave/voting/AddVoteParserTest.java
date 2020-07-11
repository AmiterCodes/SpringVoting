package me.amitnave.voting;

import me.amitnave.voting.WAInterface.Command.Parsers.AddVoteParser.AddAnonymousVoteParser;
import me.amitnave.voting.WAInterface.Command.Parsers.AddVoteParser.AddVoteParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.Message;
import me.amitnave.voting.WAInterface.general.Settings;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddVoteParserTest {

    @Test
    void isLegalCommand() throws SQLException {
        Message message=new Message("בעד כענ", Settings.getCouncilChatID(),"הצעת חוק #765476", true, "058");
        AddAnonymousVoteParser addAnonymousVoteParser=new AddAnonymousVoteParser();
        assertTrue(!addAnonymousVoteParser.isLegalCommand(message));
    }

    @Test
    void getCommand() throws SQLException, ParseException {
        Message message=new Message("בעד כענ", Settings.getCouncilChatID(),"הצעת חוק #378", true, "058");
        AddVoteParser addVoteParser=new AddVoteParser();
        addVoteParser.isLegalCommand(message);
        VotingCommand command=addVoteParser.getCommand();
        command.DatabaseAction();
        System.out.println(command.message().getContent());
    }
}