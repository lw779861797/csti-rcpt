package com.cstiweb.rcpt.service.impl;

import com.cstiweb.rcpt.mapper.PlayerQuestionTypeRelationMapper;
import com.cstiweb.rcpt.model.PlayerQuestionTypeRelation;
import com.cstiweb.rcpt.model.PlayerQuestionTypeRelationExample;
import com.cstiweb.rcpt.service.PlayerQuestionTypeRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerQuestionTypeRelationServiceImpl implements PlayerQuestionTypeRelationService {
    @Autowired
    PlayerQuestionTypeRelationMapper playerQuestionTypeRelationMapper;

    @Override
    public Integer queryPlayerQuestionRe(String playerID,Integer questionRelationID){
        PlayerQuestionTypeRelationExample example = new PlayerQuestionTypeRelationExample();
        example.createCriteria().andPlayerIdEqualTo(playerID)
                .andQuestionTypeIdEqualTo(questionRelationID)
                .andIsDeleteEqualTo(0);
        return playerQuestionTypeRelationMapper.selectByExample(example).get(0).getId();
    }

    @Override
    public void insert(Integer id, String playerID) {
        PlayerQuestionTypeRelation playerQuestionTypeRelation = new PlayerQuestionTypeRelation();
        playerQuestionTypeRelation.setQuestionTypeId(id);
        playerQuestionTypeRelation.setIsDelete(0);
        playerQuestionTypeRelation.setPlayerId(playerID);
        playerQuestionTypeRelationMapper.insert(playerQuestionTypeRelation);
    }
}
