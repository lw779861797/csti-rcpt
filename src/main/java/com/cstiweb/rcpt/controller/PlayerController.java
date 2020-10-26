package com.cstiweb.rcpt.controller;

import com.cstiweb.rcpt.api.CommonResult;
import com.cstiweb.rcpt.model.Player;
import com.cstiweb.rcpt.model.QuestionRelation;
import com.cstiweb.rcpt.service.PlayerQuestionTypeRelationService;
import com.cstiweb.rcpt.service.PlayerService;
import com.cstiweb.rcpt.service.QuestionRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @Autowired
    QuestionRelationService questionRelationService;

    @Autowired
    PlayerQuestionTypeRelationService playerQuestionTypeRelationService;

    @RequestMapping("/transferInsert")
    public String transferInsert(){
        return "input.html";
    }

    @RequestMapping("/transferInserts")
    public String transferInserts(){
        return "inputs.html";
    }

    @ResponseBody
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public CommonResult insert(@RequestParam String playerID,
                               @RequestParam String name,
                               @RequestParam Double preContest,
                               @RequestParam Integer levels){
        Player player = new Player();
        player.setId(playerID);
        player.setLevels(levels);
        player.setPreContest(new BigDecimal(preContest));
        player.setName(name);
        player.setIsDelete(0);

        playerService.insert(player);
        List<QuestionRelation> questionRelations = questionRelationService.queryQuestionRelationListByFL(1, levels);
        for(QuestionRelation relation : questionRelations){
            playerQuestionTypeRelationService.insert(relation.getId(),playerID);
        }
        questionRelations = questionRelationService.queryQuestionRelationListByFL(2, levels);
        for(QuestionRelation relation : questionRelations){
            playerQuestionTypeRelationService.insert(relation.getId(),playerID);
        }
        return CommonResult.success("录入成功");
    }

    @ResponseBody
    @RequestMapping(value = "/inserts",method = RequestMethod.POST)
    public CommonResult inserts(@RequestParam String playerID,
                                @RequestParam Integer levels){
        List<QuestionRelation> questionRelations = questionRelationService.queryQuestionRelationListByFL(2, levels);
        for(QuestionRelation relation : questionRelations){
            playerQuestionTypeRelationService.insert(relation.getId(),playerID);
        }
        return CommonResult.success("录入成功");
    }
}
