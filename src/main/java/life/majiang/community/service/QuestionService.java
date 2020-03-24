package life.majiang.community.service;

import life.majiang.community.dto.PageinationDTO;
import life.majiang.community.dto.QuestionDto;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.QuestionExtMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.QuestionExample;
import life.majiang.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
    @Autowired
    private QuestionExtMapper questionExtMapper;

    //相当于关联查question表和user表,将两个表信息bean存到questionDto中
    public PageinationDTO list(Integer page, Integer size) {
        Integer totalCout = (int) questionMapper.countByExample(new QuestionExample());
        PageinationDTO pageinationDTO = new PageinationDTO();
        Integer totalPage;
        if (totalCout % size == 0) {
            totalPage = totalCout / size;
        } else {
            totalPage = totalCout / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        pageinationDTO.setPagination(totalPage, page);
        //        5*(i-1)  size*(page-1)分页
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
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


        QuestionExample example = new QuestionExample();
        example.createCriteria().andIdEqualTo(userId);
        //由于此处需要userId查询,所以就给example传一个userId
        Integer totalCout = (int) questionMapper.countByExample(example);
        PageinationDTO pageinationDTO = new PageinationDTO();
        Integer totalPage;
        if (totalCout % size == 0) {
            totalPage = totalCout / size;
        } else {
            totalPage = totalCout / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        pageinationDTO.setPagination(totalPage, page);

        //        5*(i-1)  size*(page-1)分页
        Integer offset = size * (page - 1);

        QuestionExample example1 = new QuestionExample();
        example1.createCriteria().andIdEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example1, new RowBounds(offset, size));
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
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
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto = new QuestionDto();
        //把question放到questionDto中
        BeanUtils.copyProperties(question, questionDto);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }
    //更新或修改问题
    public void createOrUpdate(Question question) {

        if (question.getId()==null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);

        } else{
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setDescription(question.getDescription());
            QuestionExample example = new QuestionExample();
//           给example传id
            example.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if(updated!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    //增加阅读数
    public void inView(Integer id) {
        //获取该问题
        Question question = new Question();
        question.setId(id);
        //设置步长为1
        question.setViewCount(1);
        questionExtMapper.incView(question);


    }
}
