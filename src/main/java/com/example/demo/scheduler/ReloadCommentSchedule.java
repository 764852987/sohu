package com.example.demo.scheduler;

import com.example.demo.mapper.CommentMapper;
import com.example.demo.pojo.Comment;
import com.example.demo.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ReloadCommentSchedule extends QuartzJobBean {

    @Autowired
    RedisService redisService;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
        List<Comment> list = commentMapper.getAll();
        redisService.addPopularComments(list);
        log.info("定时加载热评:"+list);
    }


}
