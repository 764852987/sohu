package com.example.demo.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

@Component
@Slf4j
public class KafkaProducer {
    @Autowired
    KafkaTemplate kafkaTemplate;

    public void send(String topic, String content){
        ListenableFuture listenableFuture = kafkaTemplate.send(topic,content);
        //发送成功后回调
        SuccessCallback<String> successCallback = new SuccessCallback() {
            @Override
            public void onSuccess(Object result) {
                log.info("----新增comment放入topic：{}，发送成功:{}",topic, content);
            }
        };
        //发送失败回调
        FailureCallback failureCallback = new FailureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("----新增comment放入topic：{}，发送失败{}",topic,content,ex);
            }
        };
        listenableFuture.addCallback(successCallback,failureCallback);

    }
}
