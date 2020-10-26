package com.cstiweb.rcpt.service;

public interface PlayerQuestionTypeRelationService {
    Integer queryPlayerQuestionRe(String playerID,Integer questionRelationID);

    void insert(Integer id, String playerID);
}
