package com.cstiweb.rcpt.service.impl;

import com.cstiweb.rcpt.dao.PlayerDao;
import com.cstiweb.rcpt.mapper.PlayerMapper;
import com.cstiweb.rcpt.model.Player;
import com.cstiweb.rcpt.model.PlayerExample;
import com.cstiweb.rcpt.service.PlayerService;
import com.cstiweb.rcpt.vo.PlayerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayServiceImpl implements PlayerService {
    @Autowired
    PlayerMapper playerMapper;

    @Autowired
    PlayerDao playerDao;

    public Player queryPlayer(String playerID){
        return playerMapper.selectByPrimaryKey(playerID);
    }

    @Override
    public List<Player> queryPlayers(long levels) {
        PlayerExample example = new PlayerExample();
        example.createCriteria().andLevelsEqualTo((int) levels)
                .andIsDeleteEqualTo(0);
        return playerMapper.selectByExample(example);
    }

    @Override
    public void insert(Player player) {
        playerDao.insert(player);
    }

    @Override
    public void delete(PlayerVo playerVo) {
        Player player = playerVo;
        player.setIsDelete(1);
        playerMapper.updateByPrimaryKeySelective(player);
    }
}
