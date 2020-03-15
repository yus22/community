package life.majiang.community.service;

import life.majiang.community.dto.PageinationDTO;
import life.majiang.community.dto.QuestionDto;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    //相当于关联查question表和user表,将两个表信息bean存到questionDto中
    public PageinationDTO list(Integer page, Integer size) {
        Integer totalCout=questionMapper.count();
        PageinationDTO pageinationDTO = new PageinationDTO();
        pageinationDTO.setPagination(totalCout,page,size);
        if (page<1){
            page=1;
        }
        if(page>pageinationDTO.getTotalPage()){
            page=pageinationDTO.getTotalPage();
        }
        //        5*(i-1)  size*(page-1)分页
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            //把question这个bean复制到questionDto当中
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageinationDTO.setQuestions(questionDtoList);
        return pageinationDTO;
    }
}
