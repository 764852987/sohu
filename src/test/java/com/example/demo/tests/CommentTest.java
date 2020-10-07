package com.example.demo.tests;

import com.example.demo.DemoApplication;
import com.example.demo.controller.CommentController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class CommentTest{

    @Autowired
    private CommentController commentController;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }
    @Test
    public void addComment() throws Exception{
        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("content","unitTest");
        MockHttpServletRequestBuilder params1 = MockMvcRequestBuilders.get("/comment/add").params(params);
        MvcResult mvcResult = mockMvc.perform(params1)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        HttpServletResponse r = mvcResult.getResponse();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        System.out.println(contentAsString);
    }



    @Test
    public void getComments() throws Exception{
        MockHttpServletRequestBuilder params1 = MockMvcRequestBuilders.get("/comment");
        MvcResult mvcResult = mockMvc.perform(params1)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        System.out.println(contentAsString);
    }


    @Test
    public void getComment() throws Exception{
        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("id","1");
        MockHttpServletRequestBuilder params1 = MockMvcRequestBuilders.get("/comment/detail").params(params);
        MvcResult mvcResult = mockMvc.perform(params1)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        System.out.println(contentAsString);
    }
}
