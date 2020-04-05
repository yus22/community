package life.majiang.community.controller;

import life.majiang.community.dto.CommentCreateDto;
import life.majiang.community.dto.CommentDTO;
import life.majiang.community.dto.ResultDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.model.Comment;
import life.majiang.community.model.User;
import life.majiang.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommenController {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDto commentCreateDto,
                       HttpServletRequest request
    ) {
//        登录状态验证
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            //此处是为了返回json 如果抛异常返回的则是error页面
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentCreateDto == null || StringUtils.isBlank(commentCreateDto.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        //传进来问题id
        comment.setParentId(commentCreateDto.getParentId());
        comment.setContent(commentCreateDto.getContent());
        comment.setType(commentCreateDto.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        //评论用户设置
        comment.setCommentator(user.getId());
        commentService.insert(comment);
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO comments(@PathVariable(name = "id") Long id){
        List<CommentDTO> commentDTOS=commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}
