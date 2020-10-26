package com.cstiweb.rcpt.service.impl;

import com.cstiweb.rcpt.mapper.QuestionRelationMapper;
import com.cstiweb.rcpt.model.QuestionRelation;
import com.cstiweb.rcpt.model.QuestionRelationExample;
import com.cstiweb.rcpt.service.QuestionRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionRelationServiceImpl implements QuestionRelationService {
    @Autowired
    QuestionRelationMapper questionRelationMapper;

    @Override
    public List<QuestionRelation> queryQuestionRelationListByFL(long family,long levels){
        QuestionRelationExample example = new QuestionRelationExample();
        example.createCriteria().andFamilyEqualTo((int)family)
                .andLevelsEqualTo((int)levels)
                .andIsDeleteEqualTo(0);
        return questionRelationMapper.selectByExample(example);
    }

    @Override
    public QuestionRelation queryQuestionRelationByPlayer(String questionName, long family, long levels) {
        QuestionRelationExample example = new QuestionRelationExample();
        example.createCriteria().andNameEqualTo(questionName)
                .andFamilyEqualTo((int) family)
                .andLevelsEqualTo((int) levels)
                .andIsDeleteEqualTo(0);
        return questionRelationMapper.selectByExample(example).get(0);
    }
}
