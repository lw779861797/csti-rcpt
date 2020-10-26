package com.cstiweb.rcpt.service.impl;

import com.cstiweb.rcpt.mapper.AchievementMapper;
import com.cstiweb.rcpt.mapper.PlayerQuestionTypeRelationMapper;
import com.cstiweb.rcpt.model.Achievement;
import com.cstiweb.rcpt.model.AchievementExample;
import com.cstiweb.rcpt.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AchievementServiceImpl implements AchievementService {
    @Autowired
    AchievementMapper achievementMapper;

    @Autowired
    PlayerQuestionTypeRelationMapper playerQuestionTypeRelationMapper;

    @Override
    public void insert(Integer id, BigDecimal bigDecimal) {
        Achievement record = new Achievement();
        record.setGrade(bigDecimal);
        record.setPlayerRelationId(id);
        record.setIsDelete(0);
        achievementMapper.insert(record);
    }

    @Override
    public void delete(Achievement achievement) {
        achievement.setIsDelete(1);
        AchievementExample example = new AchievementExample();
        example.createCriteria().andIdEqualTo(achievement.getId());
        achievementMapper.updateByExample(achievement,example);
    }
}
