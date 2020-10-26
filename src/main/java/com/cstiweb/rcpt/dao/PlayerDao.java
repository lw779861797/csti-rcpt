package com.cstiweb.rcpt.dao;

import com.cstiweb.rcpt.model.Player;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerDao {

    @Insert("insert into player (id,name,levels,pre_contest,is_delete) values(#{id},#{name},#{levels},#{preContest},#{isDelete})")
    void insert(Player player);
}
