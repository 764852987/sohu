package com.example.demo.mapper;

import com.example.demo.pojo.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CommentMapper {
    @Select("SELECT * FROM comment")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "content", column = "content"),
            @Result(property = "count", column = "count")
    })
    List<Comment> getAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "content", column = "content"),
            @Result(property = "count", column = "count")
    })
    Comment getOne(Long id);

    @Insert("INSERT INTO comment (content, count) VALUES (#{content},0)")
    Integer addOne(String content);
}
