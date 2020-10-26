package com.cstiweb.rcpt.controller;

import com.cstiweb.rcpt.api.CommonResult;
import com.cstiweb.rcpt.api.ResultCode;
import com.cstiweb.rcpt.mapper.AdminMapper;
import com.cstiweb.rcpt.model.Admin;
import com.cstiweb.rcpt.model.AdminExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
    AdminMapper adminMapper;

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public CommonResult<String > login(@RequestParam String username, @RequestParam String password,
                                       HttpServletRequest request){
        if(username == null || username.isEmpty()){
            return CommonResult.failed(ResultCode.FAILED,"用户名为空");
        }
        if(password == null || password.isEmpty()){
            return CommonResult.failed(ResultCode.FAILED,"登入密码为空");
        }
        AdminExample example = new AdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<Admin> admins = adminMapper.selectByExample(example);
        if(admins.isEmpty()){
            return CommonResult.failed(ResultCode.FAILED,"没有此用户");
        }
        Admin admin = admins.get(0);
        if(!admin.getPassword().equals(password)) {
            return CommonResult.failed(ResultCode.FAILED, "密码错误");
        }
        HttpSession session = request.getSession();
        session.setAttribute("user",username);
        session.setMaxInactiveInterval(60 * 60 * 6);
        return CommonResult.success("登入成功");
    }
}
