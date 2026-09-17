package com.Week9.learn_spring_ai.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AIServiceTests {

    @Autowired
    private  AIService aiService;

    @Test
    public void getJoke(){
        var joke=aiService.getJoke("Dog");
        System.out.println(joke);
    }

    @Test
    public void testgetEmbedding(){
        var embed=aiService.getEmbedding("This is a big text here");
        System.out.println(embed.length);
        for(float e :embed){
            System.out.println(e+" ");
        }
    }

    @Test
    public void getStore(){
       aiService.ingestDataToVectorStore();
    }
    @Test
    public void getSimilaritySearch(){
        var res=aiService.similaritySearch("bat and ball");
        System.out.println(res);
    }
}
