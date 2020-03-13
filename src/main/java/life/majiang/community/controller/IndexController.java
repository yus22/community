package life.majiang.community.controller;

import life.majiang.community.dto.QuestionDto;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    /*
     * 此处实现逻辑 当用户登录过一次后.会把用户信息存到数据库中,生成一个token,
     * 将该token存到cookie上,再次登录直接从cookie上获取到该token和数据库中信息做对比,
     * 数据库中直接存在此user用户,再将user信息存到session中,html使用
     * */

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")//表示根目录 只要输入localhost:8887/就可以进入
    public String index(HttpServletRequest request, Model model) {
//        获取token
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        //带有question信息和用户信息 进入页面显示博客信息
        List<QuestionDto> questionList=questionService.list();
        model.addAttribute("questions",questionList);
        return "index";
    }

}