package com.Week9.learn_spring_ai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class RAGServiceTests {

    @Autowired
    private RAGService ragService;

        @Test
    public void testaskAI(){
        var response=ragService.askAI("which company i work in?");
        System.out.println(response);
    }

    @Test
    public void testingest(){
       ragService.ingestPdfToVectorStore();
    }
}
