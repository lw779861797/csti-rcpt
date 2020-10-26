package com.cstiweb.rcpt.service;

import com.cstiweb.rcpt.model.QuestionRelation;
import org.springframework.stereotype.Service;

import java.util.List;

public interface QuestionRelationService {
    List<QuestionRelation> queryQuestionRelationListByFL(long family, long levels);

    QuestionRelation queryQuestionRelationByPlayer(String questionName, long family, long levels);
}
