package com.Week9.learn_spring_ai.service;

import com.Week9.learn_spring_ai.dto.Joke;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    public float[] getEmbedding(String text){
        return embeddingModel.embed(text);
    }

    public void ingestDataToVectorStore(){
        List<Document>movies=List.of(
                new Document("This is a movie about most famous cricketer in India",Map.of("title","msd","genre","biopic","year","2016")),
                new Document("This is a movie about most famous runner in India",Map.of("title","bmb","genre","biopic","year","2012")),
                new Document("This is a movie about most famous india pakistan incidents",Map.of("title","dhurandhar","genre","fiction","year","2025"))
        );
        vectorStore.add(movies);

    }
    public List<Document> similaritySearch(String text){
        return vectorStore.similaritySearch(text);
    }

    public String getJoke(String topic){

        String systemPrompt= """
            You are a sarcastic joker, give a poem in 4 lines
            Don't involve politics
            The topic on which you have to give is: {topic}
            """;
        PromptTemplate promptTemplate=new PromptTemplate(systemPrompt);
        String renderedText=promptTemplate.render(Map.of("topic",topic));
        var response= chatClient.prompt()
                .user(renderedText)
                .call()
                //.content();
                .entity(Joke.class);
        return response.text();
    }
}
