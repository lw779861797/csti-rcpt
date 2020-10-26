package com.cstiweb.rcpt.service;

import com.cstiweb.rcpt.model.Achievement;

import java.math.BigDecimal;

public interface AchievementService {
    void insert(Integer id, BigDecimal bigDecimal);

    void delete(Achievement achievement);
}
