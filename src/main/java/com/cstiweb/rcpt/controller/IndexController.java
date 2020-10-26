package com.cstiweb.rcpt.controller;

import com.cstiweb.rcpt.api.CommonResult;
import com.cstiweb.rcpt.api.ResultCode;
import com.cstiweb.rcpt.dao.AchievementDao;
import com.cstiweb.rcpt.dao.PlayerQuestionTypeRelationDao;
import com.cstiweb.rcpt.model.Achievement;
import com.cstiweb.rcpt.model.Player;
import com.cstiweb.rcpt.model.QuestionRelation;
import com.cstiweb.rcpt.service.AchievementService;
import com.cstiweb.rcpt.service.PlayerQuestionTypeRelationService;
import com.cstiweb.rcpt.service.PlayerService;
import com.cstiweb.rcpt.service.QuestionRelationService;
import com.cstiweb.rcpt.util.NumberUtil;
import com.cstiweb.rcpt.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    PlayerService playerService;

    @Autowired
    private QuestionRelationService questionRelationService;
    
    @Autowired
    private PlayerQuestionTypeRelationService playerQuestionTypeRelationService;

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private AchievementDao achievementDao;

    @Autowired
    private PlayerQuestionTypeRelationDao playerQuestionTypeRelationDao;

    @RequestMapping("/index")
    public String index(){
        return "index.html";
    }

    @RequestMapping(value = "/transferScore",method = RequestMethod.GET)
    public ModelAndView transferScore(@RequestParam Long family,@RequestParam Long levels){
        ModelAndView modelAndView = new ModelAndView();
        List<QuestionRelation> questionRelations = questionRelationService.
                queryQuestionRelationListByFL(family, levels);
        modelAndView.addObject("questionRelations",questionRelations);
        modelAndView.addObject("family",family);
        modelAndView.addObject("levels",levels);
        modelAndView.setViewName("score.html");
        return modelAndView;
    }

    @RequestMapping(value = "/transferFinal",method = RequestMethod.GET)
    public String transferFinal(){
        return "semifinal.html";
    }

    @RequestMapping(value = "/transferSemiFinal",method = RequestMethod.GET)
    public String transferSemiFinal(){
        return "finals.html";
    }

    @RequestMapping(value = "/transferHead",method = RequestMethod.GET)
    public String  transferHead(){
        return "head.html";
    }

    @RequestMapping(value = "/transferFoot",method = RequestMethod.GET)
    public String  transferFoot(){
        return "foot.html";
    }

    @RequestMapping(value = "/transferHead2",method = RequestMethod.GET)
    public String transferHead2(){
        return "head2.html";
    }

    @RequestMapping(value = "/transferFoot2",method = RequestMethod.GET)
    public String  transferFoot2(){
        return "foot2.html";
    }

    @ResponseBody
    @RequestMapping(value = "/submitScore",method = RequestMethod.POST)
    public CommonResult submitScore(@RequestBody PlayerScoreParam playerScoreParam){
        Player player = playerService.queryPlayer(playerScoreParam.getPlayerID());
        if(player == null){
            return CommonResult.failed(ResultCode.FAILED,"没有此选手");
        }
        QuestionRelation questionRelation = questionRelationService.queryQuestionRelationByPlayer(playerScoreParam.getQuestionName(),
                playerScoreParam.getFamily(), playerScoreParam.getLevels());
        int questionRelationID = questionRelation.getId();
        Integer ID = null;
        try{
            ID = playerQuestionTypeRelationService.queryPlayerQuestionRe(playerScoreParam.getPlayerID(), questionRelationID);
        }catch (Exception e){
            return CommonResult.failed(ResultCode.FAILED,"本选手不属于该组");
        }
        List<Double> scores = playerScoreParam.getScores();
        double max = scores.get(0);
        double min = scores.get(0);
        BigDecimal sum = new BigDecimal("0");
        for(int i = 0;i < scores.size();i++){
            if(max < scores.get(i)){
                max =  scores.get(i);
            }
            if(min > scores.get(i)){
                min =  scores.get(i);
            }
            sum = sum.add(new BigDecimal(scores.get(i)));
        }
        sum = sum.subtract(new BigDecimal(max));
        sum = sum.subtract(new BigDecimal(min));
        BigDecimal avg = sum.divide(new BigDecimal("5"),2,BigDecimal.ROUND_HALF_UP);
        achievementService.insert(ID,avg);
        PlayerScoreVo playerScoreVo = new PlayerScoreVo();
        playerScoreVo.setScore(NumberUtil.formatToNumber(avg));
        playerScoreVo.setPlayerName(player.getName());
        return CommonResult.success(playerScoreVo);
    }

    @RequestMapping(value = "/transferShowchoice",method = RequestMethod.GET)
    public String transferShowchoice(@RequestParam Integer family, Model model){
        model.addAttribute("family",family);
        return "showchoice.html";
    }

    @RequestMapping("/showList1/{family}")
    public String showList1(@PathVariable("family") Integer family,Model model){
        if(family == 1){
            List<Player> players = playerService.queryPlayers(1);
            List<PlayerVo> playerVos = new ArrayList<>();
            for(int i = 0;i < players.size();i++){
                PlayerVo playerVo = new PlayerVo();
                BeanUtils.copyProperties(players.get(i),playerVo);
                BigDecimal prContest = playerVo.getPreContest().multiply(new BigDecimal("0.25"));
                playerVo.setPreContest(prContest.setScale(2,BigDecimal.ROUND_HALF_UP));
                Achievement speech = achievementDao.query(playerVo.getId(), "命题演讲", family, 1);
                BigDecimal speechs = null;
                if(speech == null){
                    speechs = new BigDecimal(0);
                }else {
                    speechs = speech.getGrade().multiply(new BigDecimal("0.2"));
                }
                playerVo.setSpeech(speechs.setScale(2,BigDecimal.ROUND_HALF_UP));
                Achievement spoken = achievementDao.query(playerVo.getId(), "口语表述", family, 1);
                BigDecimal spokens = null;
                if(spoken == null){
                    spokens = new BigDecimal(0);
                }else {
                    spokens = spoken.getGrade().multiply(new BigDecimal("0.15"));
                }
                playerVo.setSpoken(spokens.setScale(2,BigDecimal.ROUND_HALF_UP));
                playerVo.setSum(prContest.add(speechs).add(spokens).setScale(2,BigDecimal.ROUND_HALF_UP));
                playerVos.add(playerVo);
            }
            Collections.sort(playerVos, new Comparator<PlayerVo>() {
                @Override
                public int compare(PlayerVo o2, PlayerVo o1) {
                    int flag1 = o1.getSum().compareTo(o2.getSum());
                    if(flag1 == 0){
                        int flag2 = o1.getSpeech().compareTo(o2.getSpeech());
                        if(flag2 == 0){
                            int flag3 = o1.getSpoken().compareTo(o2.getSpoken());
                            if(flag3 == 0){
                                int flag4 = o1.getPreContest().compareTo(o2.getPreContest());
                                return flag4;
                            }else {
                                return flag3;
                            }
                        }else {
                            return flag2;
                        }
                    }else {
                        return flag1;
                    }
                }
            });
            for(int i = 0;i < playerVos.size();i++){
                playerVos.get(i).setRanking(i + 1);
            }
            model.addAttribute("playerVos",playerVos);
            return "show1.html";
        }else {
            List<Player> players = playerService.queryPlayers(1);
            List<PlayerJuniorVo> playerJuniorVos = new ArrayList<>();
            for(int i = 0;i < players.size();i++){
                PlayerJuniorVo playerJuniorVo = new PlayerJuniorVo();
                BeanUtils.copyProperties(players.get(i),playerJuniorVo);
                BigDecimal prContest = playerJuniorVo.getPreContest().multiply(new BigDecimal("0.25"));
                playerJuniorVo.setPreContest(prContest.setScale(2,BigDecimal.ROUND_HALF_UP));
                Achievement speech = achievementDao.query(playerJuniorVo.getId(), "命题演讲", 1, 1);
                BigDecimal speechs = null;
                if(speech == null){
                    speechs = new BigDecimal(0);
                }else {
                    speechs = speech.getGrade().multiply(new BigDecimal("0.2"));
                }
                playerJuniorVo.setSpeech(speechs.setScale(2,BigDecimal.ROUND_HALF_UP));
                Achievement spoken = achievementDao.query(playerJuniorVo.getId(), "口语表述", 1, 1);
                BigDecimal spokens = null;
                if(spoken == null){
                    spokens = new BigDecimal(0);
                }else {
                    spokens = spoken.getGrade().multiply(new BigDecimal("0.15"));
                }
                playerJuniorVo.setSpoken(spokens.setScale(2,BigDecimal.ROUND_HALF_UP));

                Achievement translation = achievementDao.query(playerJuniorVo.getId(), "俄汉双向翻译", 2, 1);
                BigDecimal translations = null;
                if(translation == null){
                    translations = new BigDecimal(0);
                }else {
                    translations = translation.getGrade().multiply(new BigDecimal("0.2"));
                }
                playerJuniorVo.setTranslation(translations.setScale(2,BigDecimal.ROUND_HALF_UP));

                Achievement constant = achievementDao.query(playerJuniorVo.getId(), "回答连贯问题", 2, 1);
                BigDecimal constants = null;
                if(constant == null){
                    constants = new BigDecimal(0);
                }else {
                    constants = constant.getGrade().multiply(new BigDecimal("0.2"));
                }
                playerJuniorVo.setConstant(constants.setScale(2,BigDecimal.ROUND_HALF_UP));

                playerJuniorVo.setSum(prContest.add(speechs).add(spokens).add(translations).add(constants).setScale(2,BigDecimal.ROUND_HALF_UP));
                playerJuniorVos.add(playerJuniorVo);
            }

            Collections.sort(playerJuniorVos, new Comparator<PlayerJuniorVo>() {
                @Override
                public int compare(PlayerJuniorVo o2, PlayerJuniorVo o1) {
                    int flag1 = o1.getSum().compareTo(o2.getSum());
                    if(flag1 == 0){
                        int flag2 = o1.getSpeech().compareTo(o2.getSpeech());
                        if(flag2 == 0){
                            int flag3 = o1.getSpoken().compareTo(o2.getSpoken());
                            if(flag3 == 0){
                                int flag4 = o1.getTranslation().compareTo(o2.getTranslation());
                                if(flag4 == 0){
                                    int flag5 = o1.getConstant().compareTo(o2.getConstant());
                                    if(flag5 == 0){
                                        int flag6 = o1.getPreContest().compareTo(o2.getPreContest());
                                        return flag6;
                                    }else {
                                        return flag5;
                                    }
                                }else {
                                    return flag4;
                                }
                            }else {
                                return flag3;
                            }
                        }else {
                            return flag2;
                        }
                    }else {
                        return flag1;
                    }
                }
            });

            for(int i = 0;i < playerJuniorVos.size();i++){
                playerJuniorVos.get(i).setRanking(i + 1);
            }

            model.addAttribute("playerJuniorVos",playerJuniorVos);
            return "show2.html";
        }
    }

    @RequestMapping("/showList3/{family}")
    public String showList3(@PathVariable("family") Integer family,Model model){
        if(family == 1){
            List<Player> players = playerService.queryPlayers(3);
            List<PlayerVo> playerVos = new ArrayList<>();
            for(int i = 0;i < players.size();i++){
                PlayerVo playerVo = new PlayerVo();
                BeanUtils.copyProperties(players.get(i),playerVo);
                BigDecimal prContest = playerVo.getPreContest().multiply(new BigDecimal("0.25"));
                playerVo.setPreContest(prContest.setScale(2,BigDecimal.ROUND_HALF_UP));
                Achievement speech = achievementDao.query(playerVo.getId(), "命题演讲", family, 3);
                BigDecimal speechs = null;
                if(speech == null){
                    speechs = new BigDecimal(0);
                }else {
                    speechs = speech.getGrade().multiply(new BigDecimal("0.2"));
                }
                playerVo.setSpeech(speechs.setScale(2,BigDecimal.ROUND_HALF_UP));
                Achievement spoken = achievementDao.query(playerVo.getId(), "口语表述", family, 3);
                BigDecimal spokens = null;
                if(spoken == null){
                    spokens = new BigDecimal(0);
                }else {
                    spokens = spoken.getGrade().multiply(new BigDecimal("0.15"));
                }
                playerVo.setSpoken(spokens.setScale(2,BigDecimal.ROUND_HALF_UP));
                playerVo.setSum(prContest.add(speechs).add(spokens).setScale(2,BigDecimal.ROUND_HALF_UP));
                playerVos.add(playerVo);
            }
            Collections.sort(playerVos, new Comparator<PlayerVo>() {
                @Override
                public int compare(PlayerVo o2, PlayerVo o1) {
                    int flag1 = o1.getSum().compareTo(o2.getSum());
                    if(flag1 == 0){
                        int flag2 = o1.getSpeech().compareTo(o2.getSpeech());
                        if(flag2 == 0){
                            int flag3 = o1.getSpoken().compareTo(o2.getSpoken());
                            if(flag3 == 0){
                                int flag4 = o1.getPreContest().compareTo(o2.getPreContest());
                                return flag4;
                            }else {
                                return flag3;
                            }
                        }else {
                            return flag2;
                        }
                    }else {
                        return flag1;
                    }
                }
            });
            for(int i = 0;i < playerVos.size();i++){
                playerVos.get(i).setRanking(i + 1);
            }
            model.addAttribute("playerVos",playerVos);
            return "show1.html";
        }else {
            List<Player> players = playerService.queryPlayers(3);
            List<PlayerGraduateVo> playerGraduateVos = new ArrayList<>();
            for(int i = 0;i < players.size();i++){
                PlayerGraduateVo playerGraduateVo = new PlayerGraduateVo();
                BeanUtils.copyProperties(players.get(i),playerGraduateVo);
                BigDecimal prContest = playerGraduateVo.getPreContest().multiply(new BigDecimal("0.25"));
                playerGraduateVo.setPreContest(prContest.setScale(2,BigDecimal.ROUND_HALF_UP));
                Achievement speech = achievementDao.query(playerGraduateVo.getId(), "命题演讲", 1, 3);
                BigDecimal speechs = null;
                if(speech == null){
                    speechs = new BigDecimal(0);
                }else {
                    speechs = speech.getGrade().multiply(new BigDecimal("0.2"));
                }
                playerGraduateVo.setSpeech(speechs.setScale(2,BigDecimal.ROUND_HALF_UP));
                Achievement spoken = achievementDao.query(playerGraduateVo.getId(), "口语表述", 1, 3);
                BigDecimal spokens = null;
                if(spoken == null){
                    spokens = new BigDecimal(0);
                }else {
                    spokens = spoken.getGrade().multiply(new BigDecimal("0.15"));
                }
                playerGraduateVo.setSpoken(spokens.setScale(2,BigDecimal.ROUND_HALF_UP));

                Achievement interpret = achievementDao.query(playerGraduateVo.getId(), "对话口译", 2, 3);
                BigDecimal interprets = null;
                if(interpret == null){
                    interprets = new BigDecimal(0);
                }else {
                    interprets = interpret.getGrade().multiply(new BigDecimal("0.2"));
                }
                playerGraduateVo.setInterpret(interprets.setScale(2,BigDecimal.ROUND_HALF_UP));

                Achievement recount = achievementDao.query(playerGraduateVo.getId(), "看视频用俄语讲述内容", 2, 3);
                BigDecimal recounts = null;
                if(recount == null){
                    recounts = new BigDecimal(0);
                }else {
                    recounts = recount.getGrade().multiply(new BigDecimal("0.2"));
                }
                playerGraduateVo.setRecount(recounts.setScale(2,BigDecimal.ROUND_HALF_UP));

                playerGraduateVo.setSum(prContest.add(speechs).add(spokens).add(interprets).add(recounts).setScale(2,BigDecimal.ROUND_HALF_UP));
                playerGraduateVos.add(playerGraduateVo);
            }

            Collections.sort(playerGraduateVos, new Comparator<PlayerGraduateVo>() {
                @Override
                public int compare(PlayerGraduateVo o2, PlayerGraduateVo o1) {
                    int flag1 = o1.getSum().compareTo(o2.getSum());
                    if(flag1 == 0){
                        int flag2 = o1.getSpeech().compareTo(o2.getSpeech());
                        if(flag2 == 0){
                            int flag3 = o1.getSpoken().compareTo(o2.getSpoken());
                            if(flag3 == 0){
                                int flag4 = o1.getInterpret().compareTo(o2.getInterpret());
                                if(flag4 == 0){
                                    int flag5 = o1.getRecount().compareTo(o2.getRecount());
                                    if(flag5 == 0){
                                        int flag6 = o1.getPreContest().compareTo(o2.getPreContest());
                                        return flag6;
                                    }else {
                                        return flag5;
                                    }
                                }else {
                                    return flag4;
                                }
                            }else {
                                return flag3;
                            }
                        }else {
                            return flag2;
                        }
                    }else {
                        return flag1;
                    }
                }
            });

            for(int i = 0;i < playerGraduateVos.size();i++){
                playerGraduateVos.get(i).setRanking(i + 1);
            }

            model.addAttribute("playerGraduateVos",playerGraduateVos);
            return "show4.html";
        }

    }

    @RequestMapping("/showList2/{family}")
    public String showList2(@PathVariable("family") Integer family,Model model){
        if(family == 1){
            List<Player> players = playerService.queryPlayers(2);
            List<PlayerVo> playerVos = new ArrayList<>();
            for(int i = 0;i < players.size();i++){
                PlayerVo playerVo = new PlayerVo();
                BeanUtils.copyProperties(players.get(i),playerVo);
                BigDecimal prContest = playerVo.getPreContest().multiply(new BigDecimal("0.25"));
                playerVo.setPreContest(prContest.setScale(2,BigDecimal.ROUND_HALF_UP));
                Achievement speech = achievementDao.query(playerVo.getId(), "命题演讲", family, 2);
                BigDecimal speechs = null;
                if(speech == null){
                    speechs = new BigDecimal(0);
                }else {
                    speechs = speech.getGrade().multiply(new BigDecimal("0.2"));
                }
                playerVo.setSpeech(speechs.setScale(2,BigDecimal.ROUND_HALF_UP));
                Achievement spoken = achievementDao.query(playerVo.getId(), "口语表述", family, 2);
                BigDecimal spokens = null;
                if(spoken == null){
                    spokens = new BigDecimal(0);
                }else {
                    spokens = spoken.getGrade().multiply(new BigDecimal("0.15"));
                }
                playerVo.setSpoken(spokens.setScale(2,BigDecimal.ROUND_HALF_UP));
                playerVo.setSum(prContest.add(speechs).add(spokens).setScale(2,BigDecimal.ROUND_HALF_UP));
                playerVos.add(playerVo);
            }
            Collections.sort(playerVos, new Comparator<PlayerVo>() {
                @Override
                public int compare(PlayerVo o2, PlayerVo o1) {
                    int flag1 = o1.getSum().compareTo(o2.getSum());
                    if(flag1 == 0){
                        int flag2 = o1.getSpeech().compareTo(o2.getSpeech());
                        if(flag2 == 0){
                            int flag3 = o1.getSpoken().compareTo(o2.getSpoken());
                            if(flag3 == 0){
                                int flag4 = o1.getPreContest().compareTo(o2.getPreContest());
                                return flag4;
                            }else {
                                return flag3;
                            }
                        }else {
                            return flag2;
                        }
                    }else {
                        return flag1;
                    }
                }
            });
            for(int i = 0;i < playerVos.size();i++){
                playerVos.get(i).setRanking(i + 1);
            }
            model.addAttribute("playerVos",playerVos);
            return "show1.html";
        }else {
            List<Player> players = playerService.queryPlayers(2);
            List<PlayerSeniorVo> playerSeniorVos = new ArrayList<>();
            for(int i = 0;i < players.size();i++){
                PlayerSeniorVo playerSeniorVo = new PlayerSeniorVo();
                BeanUtils.copyProperties(players.get(i),playerSeniorVo);
                BigDecimal prContest = playerSeniorVo.getPreContest().multiply(new BigDecimal("0.25"));
                playerSeniorVo.setPreContest(prContest.setScale(2,BigDecimal.ROUND_HALF_UP));
                Achievement speech = achievementDao.query(playerSeniorVo.getId(), "命题演讲", 1, 2);
                BigDecimal speechs = null;
                if(speech == null){
                    speechs = new BigDecimal(0);
                }else {
                    speechs = speech.getGrade().multiply(new BigDecimal("0.2"));
                }
                playerSeniorVo.setSpeech(speechs.setScale(2,BigDecimal.ROUND_HALF_UP));
                Achievement spoken = achievementDao.query(playerSeniorVo.getId(), "口语表述", 1, 2);
                BigDecimal spokens = null;
                if(spoken == null){
                    spokens = new BigDecimal(0);
                }else {
                    spokens = spoken.getGrade().multiply(new BigDecimal("0.15"));
                }
                playerSeniorVo.setSpoken(spokens.setScale(2,BigDecimal.ROUND_HALF_UP));

                Achievement translation = achievementDao.query(playerSeniorVo.getId(), "俄汉双向翻译", 2, 2);
                BigDecimal translations = null;
                if(translation == null){
                    translations = new BigDecimal(0);
                }else {
                    translations = translation.getGrade().multiply(new BigDecimal("0.2"));
                }
                playerSeniorVo.setTranslation(translations.setScale(2,BigDecimal.ROUND_HALF_UP));

                Achievement constant = achievementDao.query(playerSeniorVo.getId(), "阅读俄语篇章并用汉语阐释观点", 2, 2);
                BigDecimal constants = null;
                if(constant == null){
                    constants = new BigDecimal(0);
                }else {
                    constants = constant.getGrade().multiply(new BigDecimal("0.2"));
                }
                playerSeniorVo.setConstant(constants.setScale(2,BigDecimal.ROUND_HALF_UP));

                playerSeniorVo.setSum(prContest.add(speechs).add(spokens).add(translations).add(constants).setScale(2,BigDecimal.ROUND_HALF_UP));
                playerSeniorVos.add(playerSeniorVo);
            }

            Collections.sort(playerSeniorVos, new Comparator<PlayerSeniorVo>() {
                @Override
                public int compare(PlayerSeniorVo o2, PlayerSeniorVo o1) {
                    int flag1 = o1.getSum().compareTo(o2.getSum());
                    if(flag1 == 0){
                        int flag2 = o1.getSpeech().compareTo(o2.getSpeech());
                        if(flag2 == 0){
                            int flag3 = o1.getSpoken().compareTo(o2.getSpoken());
                            if(flag3 == 0){
                                int flag4 = o1.getTranslation().compareTo(o2.getTranslation());
                                if(flag4 == 0){
                                    int flag5 = o1.getConstant().compareTo(o2.getConstant());
                                    if(flag5 == 0){
                                        int flag6 = o1.getPreContest().compareTo(o2.getPreContest());
                                        return flag6;
                                    }else {
                                        return flag5;
                                    }
                                }else {
                                    return flag4;
                                }
                            }else {
                                return flag3;
                            }
                        }else {
                            return flag2;
                        }
                    }else {
                        return flag1;
                    }
                }
            });
            for(int i = 0;i < playerSeniorVos.size();i++){
                playerSeniorVos.get(i).setRanking(i + 1);
            }
            model.addAttribute("playerSeniorVos",playerSeniorVos);
            return "show3.html";
        }
    }

    @RequestMapping("/transferDelete")
    public String transferDelete(){
        return "delete.html";
    }

    @RequestMapping("/delete")
    public CommonResult delete(@RequestParam Integer levels){
        List<Player> players = playerService.queryPlayers(levels);
        List<PlayerVo> playerVos = new ArrayList<>();
        for(int i = 0;i < players.size();i++){
            PlayerVo playerVo = new PlayerVo();
            BeanUtils.copyProperties(players.get(i),playerVo);
            BigDecimal prContest = playerVo.getPreContest().multiply(new BigDecimal("0.25"));
            playerVo.setPreContest(prContest.setScale(2,BigDecimal.ROUND_HALF_UP));
            Achievement speech = achievementDao.query(playerVo.getId(), "命题演讲", 1, levels);
            BigDecimal speechs = null;
            if(speech == null){
                speechs = new BigDecimal(0);
            }else {
                speechs = speech.getGrade().multiply(new BigDecimal("0.2"));
            }
            playerVo.setSpeech(speechs.setScale(2,BigDecimal.ROUND_HALF_UP));
            Achievement spoken = achievementDao.query(playerVo.getId(), "口语表述", 1, levels);
            BigDecimal spokens = null;
            if(spoken == null){
                spokens = new BigDecimal(0);
            }else {
                spokens = spoken.getGrade().multiply(new BigDecimal("0.15"));
            }
            playerVo.setSpoken(spokens.setScale(2,BigDecimal.ROUND_HALF_UP));
            playerVo.setSum(prContest.add(speechs).add(spokens).setScale(2,BigDecimal.ROUND_HALF_UP));
            playerVos.add(playerVo);
        }
        Collections.sort(playerVos, new Comparator<PlayerVo>() {
            @Override
            public int compare(PlayerVo o2, PlayerVo o1) {
                int flag1 = o1.getSum().compareTo(o2.getSum());
                if(flag1 == 0){
                    int flag2 = o1.getSpeech().compareTo(o2.getSpeech());
                    if(flag2 == 0){
                        int flag3 = o1.getSpoken().compareTo(o2.getSpoken());
                        if(flag3 == 0){
                            int flag4 = o1.getPreContest().compareTo(o2.getPreContest());
                            return flag4;
                        }else {
                            return flag3;
                        }
                    }else {
                        return flag2;
                    }
                }else {
                    return flag1;
                }
            }
        });
        for(int i = 0;i < playerVos.size();i++){
            playerVos.get(i).setRanking(i + 1);
        }
        if(levels == 1){
            for(int i = 9;i < playerVos.size();i++){
                PlayerVo playerVo = playerVos.get(i);
                Achievement speech = achievementDao.query(playerVo.getId(), "命题演讲", 1, levels);
                if(speech != null){
                    achievementService.delete(speech);
                    playerQuestionTypeRelationDao.delete(speech.getPlayerRelationId());
                }
                Achievement spoken = achievementDao.query(playerVo.getId(), "口语表述", 1, levels);
                if(spoken != null){
                    achievementService.delete(spoken);
                    playerQuestionTypeRelationDao.delete(spoken.getPlayerRelationId());
                }
                playerService.delete(playerVo);
            }
        }else if(levels == 2){
            for(int i = 8;i < playerVos.size();i++){
                PlayerVo playerVo = playerVos.get(i);
                Achievement speech = achievementDao.query(playerVo.getId(), "命题演讲", 1, levels);
                if(speech != null){
                    achievementService.delete(speech);
                    playerQuestionTypeRelationDao.delete(speech.getPlayerRelationId());
                }
                Achievement spoken = achievementDao.query(playerVo.getId(), "口语表述", 1, levels);
                if(spoken != null){
                    achievementService.delete(spoken);
                    playerQuestionTypeRelationDao.delete(spoken.getPlayerRelationId());
                }
                playerService.delete(playerVo);
            }
        }else {
            for(int i = 5;i < playerVos.size();i++){
                PlayerVo playerVo = playerVos.get(i);
                Achievement speech = achievementDao.query(playerVo.getId(), "命题演讲", 1, levels);
                if(speech != null){
                    achievementService.delete(speech);
                    playerQuestionTypeRelationDao.delete(speech.getPlayerRelationId());
                }
                Achievement spoken = achievementDao.query(playerVo.getId(), "口语表述", 1, levels);
                if(spoken != null){
                    achievementService.delete(spoken);
                    playerQuestionTypeRelationDao.delete(spoken.getPlayerRelationId());
                }
                playerService.delete(playerVo);
            }
        }

        return CommonResult.success("操作成功");
    }
}
