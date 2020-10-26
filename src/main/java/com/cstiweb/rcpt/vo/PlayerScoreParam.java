package com.cstiweb.rcpt.vo;

import java.io.Serializable;
import java.util.List;

public class PlayerScoreParam implements Serializable {
    private Long family;

    private Long levels;

    private List<Double> scores;

    private String playerID;

    private String questionName;

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public Long getFamily() {
        return family;
    }

    public void setFamily(Long family) {
        this.family = family;
    }

    public Long getLevels() {
        return levels;
    }

    public void setLevels(Long levels) {
        this.levels = levels;
    }

    public List<Double> getScores() {
        return scores;
    }

    public void setScores(List<Double> scores) {
        this.scores = scores;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }
}
