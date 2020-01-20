package com.majiang.springboot.service;

import com.majiang.springboot.dto.QuestionDTO;
import com.majiang.springboot.mapper.QuestionMapper;
import com.majiang.springboot.mapper.UserMapper;
import com.majiang.springboot.model.Question;
import com.majiang.springboot.model.User;
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

    public List<QuestionDTO> list() {
        List<Question> questionList = questionMapper.select();
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        for (Question question: questionList){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
