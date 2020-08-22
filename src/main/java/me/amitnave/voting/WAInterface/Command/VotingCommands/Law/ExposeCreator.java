package me.amitnave.voting.WAInterface.Command.VotingCommands.Law;

import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.MessageStructure;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.databaseObjects.DBHelper;
import me.amitnave.voting.databaseObjects.Law;
import me.amitnave.voting.databaseObjects.Member;
import me.amitnave.voting.databaseObjects.Vote;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ExposeCreator implements VotingCommand {
    private Law law;
    private String askerNum;

    @Override
    public void DatabaseAction() throws SQLException, ParseException {

    }


    @Override
    public List<MessageToSend> message() throws SQLException, ParseException {
        Member member = new Member(law.getCreator());
        if(law.isAnonymousCreator())
            return List.of(new MessageToSend(askerNum.substring(0,12) + " ניסה לחשוף את שמך על חוק מספר #" + law.getId() + " אך החוק ממקור אנונימי", member.getPhone()),
                    new MessageToSend("החוק הזה הוצע ממקור אנונימי ולכן אי אפשר לחשוף את שם היוצר", askerNum));
        return List.of(new MessageToSend( "שם היוצר של החוק: " + member.getName(), askerNum),
                new MessageToSend(askerNum.substring(0,12) + " חשף את שמך על חוק #" + law.getId(), member.getPhone()));
    }


    public ExposeCreator(Law law, String askerNum) {
        this.law = law;
        this.askerNum = askerNum;
    }


}
