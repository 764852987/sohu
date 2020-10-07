package com.example.demo.redis;

import com.example.demo.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;

import java.util.*;

@Service
public class RedisService {

    private static final String POPULAR_COMMENTS_KEY = RedisVariable.COMMENT_KEY;

    @Autowired
    RedisTemplate redisTemplate;

    public Boolean addPopularComments(List<Comment> list){
        Map<String,String> map = new HashMap<>();
        for(Comment c:list){
            map.put(c.getId()+"",beanToString(c));
        }
        redisTemplate.opsForHash().putAll(POPULAR_COMMENTS_KEY,map);
        return true;
    }

    public List<Comment> getPopularComments(){
        Map<String,String> entries = redisTemplate.opsForHash().entries(POPULAR_COMMENTS_KEY);
        List<Comment> list = new ArrayList<Comment>();
        for(String s:entries.values()){
            list.add(stringToBean(s,Comment.class));
        }
        return list;
    }

    public Comment getComment(Long id){
        Object obj = redisTemplate.opsForHash().get(POPULAR_COMMENTS_KEY, id+"");
        return stringToBean(obj.toString(),Comment.class);
    }

    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return String.valueOf(value);
        } else if (clazz == long.class || clazz == Long.class) {
            return String.valueOf(value);
        } else if (clazz == String.class) {
            return (String) value;
        } else {
            return JSON.toJSONString(value);
        }

    }

    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

}
