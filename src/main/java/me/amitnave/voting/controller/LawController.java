package me.amitnave.voting.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.Message;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.WAInterface.general.Settings;
import me.amitnave.voting.WAInterface.general.WhatsappVotingSystem;
import me.amitnave.voting.databaseObjects.Law;
import org.springframework.scheduling.concurrent.ScheduledExecutorTask;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import static org.apache.logging.log4j.status.StatusLogger.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class LawController {
    @CrossOrigin
    @GetMapping("/listlaws")
    @ResponseBody
    public List<Law> ListPendingLaws() throws SQLException, ParseException {
        return Law.getLawsByStatus(Law.inProcess);
    }
    @ResponseBody
    @CrossOrigin
    @PutMapping("/update")
    public List<MessageToSend> update() throws SQLException, ParseException {
        return new WhatsappVotingSystem().updateLaws();
    }

    public static String blacklist = "972584319945@c.us 972587782724@c.us 972584404076@c.us";

    @ResponseBody
    @CrossOrigin
    @RequestMapping(value = "/message", method = POST)
    public List<MessageToSend> message(@RequestBody ObjectNode node) throws SQLException, ParseException {

        String sender=  node.get("sender").asText();
        if(blacklist.contains(sender)) {
            return null;
        }
        String content=  node.get("content").asText();
        String chatId=  node.get("chatId").asText();
        String repliedMessage=  node.get("repliedMessage").asText();
        boolean repliedToBot=  node.get("repliedToBot").asBoolean();
        Message m = new Message(content,chatId,repliedMessage,repliedToBot,sender);
        VotingCommand c = new WhatsappVotingSystem().parseMessage(m);
        if(c == null) return null;
        c.DatabaseAction();
        return c.message();
    }

    @ResponseBody
    @CrossOrigin
    @PutMapping("/setcouncil")
    public void updateCouncil(@RequestBody String id) {
        Settings.setCouncilChatID(id);
    }

    @ResponseBody
    @CrossOrigin
    @PutMapping("/setpresident")
    public void updatePresident(@RequestBody String id) {
        Settings.setPresidentChatID(id);
    }
}
