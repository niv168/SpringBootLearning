package com.Week9.learn_spring_ai.service;


import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RAGService {

    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    @Value("classpath:bio1.pdf")
    Resource pdfFile;

    public String askAI(String prompt){

        String template= """
        You are an AI assistant helping a developer.

        Rules:
        - Use ONLY the information provided in the context.
        - You MAY rephrase, summarize, and explain in natural language.
        - Do NOT introduce new concepts or facts.
        - If multiple context sections are relevant, combine them into a single explanation.
        - If the answer is not present, say "I don't know".

        Context:
        {context}

        Answer in a friendly, conversational tone.
        """;


        List<Document> documents=vectorStore.similaritySearch(SearchRequest.builder()
                .query(prompt)
                .topK(2)
                .filterExpression("file_name=='bio1.pdf'")
                .build());
        String context= documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        PromptTemplate promptTemplate=new PromptTemplate(template);
        String systemPrompt=promptTemplate.render(Map.of("context",context));
        return chatClient.prompt()
                .system(systemPrompt)
                .user(prompt)
                .advisors(new SimpleLoggerAdvisor())
                .call()
                .content();
    }

    public void ingestPdfToVectorStore(){

        PagePdfDocumentReader pagePdfDocumentReader=new PagePdfDocumentReader(pdfFile);
        List<Document>pages=pagePdfDocumentReader.get();
        TokenTextSplitter tokenTextSplitter= TokenTextSplitter.builder()
                .withChunkSize(200)
                .build();
        List<Document> chunks=tokenTextSplitter.apply(pages);
        vectorStore.add(chunks);


    }
    public static List<Document> springAiDocks() {
        return List.of(
                new Document(
                        "Spring AI provides abstractions like ChatClient, ChatModel, and EmbeddingModel to interact with LLMs.",
                        Map.of("topic", "ai")
                ),
                new Document(
                        "A VectorStore is used to persist embeddings and perform similarity search for retrieval augmented generation.",
                        Map.of("topic", "vectorstores")
                ),
                new Document(
                        "Retrieval Augmented Generation combines vector similarity search with LLM prompting to reduce hallucinations.",
                        Map.of("topic", "rag")
                ),
                new Document(
                        "PgVectorStore stores embeddings inside PostgreSQL using the pgvector extension.",
                        Map.of("topic", "vectorstores")
                ),
                new Document(
                        "ChatClient provides a fluent API to send prompts to language models like OpenAI.",
                        Map.of("topic", "ai")
                )
        );
    }
}
