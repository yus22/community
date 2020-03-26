package life.majiang.community.controller;

import life.majiang.community.dto.CommentDto;
import life.majiang.community.dto.ResultDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.exception.ICustomizeErrorCode;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.model.Comment;
import life.majiang.community.model.User;
import life.majiang.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommenController {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDto commentDto,
                       HttpServletRequest request
    ){
//        登录状态验证
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            //此处是为了返回json 如果抛异常返回的则是error页面
           return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        Comment comment = new Comment();
        //传进来问题id
        comment.setParentId(commentDto.getParentId());
        comment.setContent(commentDto.getContent());
        comment.setType(commentDto.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        //评论用户设置
        comment.setCommentator(user.getId());
        commentService.insert(comment);


        return ResultDTO.okOf();
    }
}
