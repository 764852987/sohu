package com.example.demo.kafka;

import com.example.demo.mapper.CommentMapper;
import com.example.demo.pojo.Comment;
import com.example.demo.redis.RedisService;
import com.example.demo.redis.RedisVariable;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.List;

@Component
@Slf4j
public class KafkaConsumer {


    @KafkaListener(topics= "check")
    public void getComments(String content){
        log.info("kafka消费: 新增评论检验通过");
    }

}
