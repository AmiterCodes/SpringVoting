package me.amitnave.voting.WAInterface.Command.Parsers;

import dnl.utils.text.table.TextTable;
import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.Message;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.databaseObjects.DBHelper;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.ParseException;
import java.util.List;

public class AdminSelectParser implements CommandParser {
    private String response;
    private String query;
    private Message message;
    @Override
    public boolean isLegalCommand(Message message) throws SQLException, ParseException {
        if(!message.isPrivate()) return false;
        if(!message.getSender().equals("972544805278@c.us")) return false;
        if(!message.getContent().startsWith("#SQLS")) return false;
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
                    Connection con= DBHelper.getConnection();
                    String sql = query;
                    Statement st = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs = st.executeQuery(sql);
                    ResultSetMetaData rsmd = rs.getMetaData();
                        int columnCount = rsmd.getColumnCount();
                    String[] columnNames = new String[columnCount];
                    for (int i = 1; i <= columnCount; i++ ) {
                        String name = rsmd.getColumnName(i);
                        columnNames[i - 1] = name;
                    }
                    int size = 0;
                    rs.last();
                    size = rs.getRow();
                    rs.beforeFirst();

                    Object[][] objTable = new Object[size][columnCount];
                    int i = 0;
                    while(rs.next()) {
                        for(int j = 0; j < columnCount; j++) {
                            objTable[i][j] = rs.getObject(j + 1);
                        }
                        i++;
                    }
                    TextTable table = new TextTable(columnNames, objTable);
                    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    final String utf8 = StandardCharsets.UTF_8.name();
                    PrintStream ps = new PrintStream(baos, true, utf8);
                    table.printTable(ps, 0);
                    response = baos.toString(utf8);
                } catch (Exception e) {
                    response =  "קרתה בעיה\n" + e.getMessage();
                }
            }

            @Override
            public List<MessageToSend> message() throws SQLException, ParseException {
                return List.of(new MessageToSend("```" + response + "```", message.getChatID()));
            }
        };
    }
}
