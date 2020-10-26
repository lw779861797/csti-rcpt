package com.cstiweb.rcpt.dao;

import com.cstiweb.rcpt.model.Achievement;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AchievementDao {
    Achievement query(@Param("playerID") String playerID,@Param("questionName") String questionName,
                      @Param("family") Integer family,@Param("levels")Integer levels);
}
