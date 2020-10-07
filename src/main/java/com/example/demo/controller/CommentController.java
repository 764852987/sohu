package com.example.demo.controller;

import com.example.demo.annotation.Authority;
import com.example.demo.exception.ParameterException;
import com.example.demo.kafka.KafkaProducer;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.pojo.Comment;
import com.example.demo.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {


    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    RedisService redisService;



    @Authority("user")
    @RequestMapping("")
    public String getPopularComments(HttpServletResponse response){
        List<Comment> list = redisService.getPopularComments();
        if(list.size() == 0){
            list = commentMapper.getAll();
            redisService.addPopularComments(list);
        }
        log.info("获得全部热评:"+list);
        StringBuffer buffer = new StringBuffer();
        for(Comment c:list){
            buffer.append(" "+c.getContent());
        }
        response.addHeader("comments",buffer.toString());
        return buffer.toString();

    }
    @Authority("user")
    @RequestMapping("/detail")
    public String getCommentDetail(HttpServletResponse response,Long id) throws ParameterException{
        if(id == null){
            throw new ParameterException(111);
        }
        Comment comment = redisService.getComment(id);
        if(comment == null){
            List<Comment> list = commentMapper.getAll();
            redisService.addPopularComments(list);
        }
        log.info("获得单个评论:"+comment.getContent());
        response.addHeader("comment",comment.getContent());
        return comment.getContent();
    }

    @Authority("user")
    @RequestMapping("/add")
    public String addComment(HttpServletResponse response, String content) throws ParameterException{
        if(content == null){
            throw new ParameterException(222);
        }
        if(commentMapper.addOne(content)>0){
            kafkaProducer.send("check",content);
            log.info("添加单个评论:"+content);
            return true+"";
        }else{
            return false+"";
        }



    }

}
