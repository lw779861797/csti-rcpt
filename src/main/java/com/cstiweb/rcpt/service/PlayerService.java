package com.cstiweb.rcpt.service;

import com.cstiweb.rcpt.model.Player;
import com.cstiweb.rcpt.vo.PlayerVo;

import java.util.List;

public interface PlayerService {
    Player queryPlayer(String playerID);

    List<Player> queryPlayers(long levels);

    void insert(Player player);

    void delete(PlayerVo playerVo);
}
