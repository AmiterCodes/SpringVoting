package me.amitnave.voting.databaseObjects;

public class PollAnswer {
    private int id;
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getPollId() {
        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }

    private int answer;
    private int pollId;

    public PollAnswer(int id, String phone, int answer, int pollId) {
        this.id = id;
        this.phone = phone;
        this.answer = answer;
        this.pollId = pollId;
    }
}
