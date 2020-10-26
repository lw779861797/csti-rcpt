package com.cstiweb.rcpt.mapper;

import com.cstiweb.rcpt.model.PlayerQuestionTypeRelation;
import com.cstiweb.rcpt.model.PlayerQuestionTypeRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlayerQuestionTypeRelationMapper {
    long countByExample(PlayerQuestionTypeRelationExample example);

    int deleteByExample(PlayerQuestionTypeRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PlayerQuestionTypeRelation record);

    int insertSelective(PlayerQuestionTypeRelation record);

    List<PlayerQuestionTypeRelation> selectByExample(PlayerQuestionTypeRelationExample example);

    PlayerQuestionTypeRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PlayerQuestionTypeRelation record, @Param("example") PlayerQuestionTypeRelationExample example);

    int updateByExample(@Param("record") PlayerQuestionTypeRelation record, @Param("example") PlayerQuestionTypeRelationExample example);

    int updateByPrimaryKeySelective(PlayerQuestionTypeRelation record);

    int updateByPrimaryKey(PlayerQuestionTypeRelation record);
}