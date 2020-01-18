package com.majiang.springboot.mapper;

import com.majiang.springboot.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question " +
            "(title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag) values " +
            "(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void create(Question question);
}
