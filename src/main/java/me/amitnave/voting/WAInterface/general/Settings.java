package me.amitnave.voting.WAInterface.general;

public class Settings {
    private static String councilChatID = "972546461280-1571250832@g.us";
    private static String presidentChatID = "972584404076@c.us";
    public static final int TIME_TO_PASS = 86400000;
    public static String getCouncilChatID() {
        return councilChatID;
    }

    public static void setCouncilChatID(String councilChatID) {
        Settings.councilChatID = councilChatID;
    }

    public static String getPresidentChatID() {
        return presidentChatID;
    }

    public static void setPresidentChatID(String presidentChatID) {
        Settings.presidentChatID = presidentChatID;
    }
}
