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
        Integer totalPage;
        if (totalCout % size == 0) {
            totalPage = totalCout / size;
        } else {
            totalPage = totalCout / size + 1;
        }
        if (page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        pageinationDTO.setPagination(totalPage,page);
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

    public PageinationDTO listByUserId(Integer userId, Integer page, Integer size) {
        Integer totalCout=questionMapper.countByUserId(userId);
        PageinationDTO pageinationDTO = new PageinationDTO();
        Integer totalPage;
        if (totalCout % size == 0) {
            totalPage = totalCout / size;
        } else {
            totalPage = totalCout / size + 1;
        }
        if (page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        pageinationDTO.setPagination(totalPage,page);

        //        5*(i-1)  size*(page-1)分页
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
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

    public QuestionDto getById(Integer id) {
        Question question=questionMapper.getById(id);
        QuestionDto questionDto = new QuestionDto();
        //把question放到questionDto中
        BeanUtils.copyProperties(question,questionDto);
        User user = userMapper.findById(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }
}
