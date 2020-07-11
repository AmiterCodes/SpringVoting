package me.amitnave.voting;

import me.amitnave.voting.databaseObjects.DBHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class VotingApplication {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        SpringApplication.run(VotingApplication.class, args);

    }

}
