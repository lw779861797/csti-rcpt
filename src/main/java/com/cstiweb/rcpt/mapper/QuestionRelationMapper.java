package com.cstiweb.rcpt.mapper;

import com.cstiweb.rcpt.model.QuestionRelation;
import com.cstiweb.rcpt.model.QuestionRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuestionRelationMapper {
    long countByExample(QuestionRelationExample example);

    int deleteByExample(QuestionRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QuestionRelation record);

    int insertSelective(QuestionRelation record);

    List<QuestionRelation> selectByExample(QuestionRelationExample example);

    QuestionRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") QuestionRelation record, @Param("example") QuestionRelationExample example);

    int updateByExample(@Param("record") QuestionRelation record, @Param("example") QuestionRelationExample example);

    int updateByPrimaryKeySelective(QuestionRelation record);

    int updateByPrimaryKey(QuestionRelation record);
}