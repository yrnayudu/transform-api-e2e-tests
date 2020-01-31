package com.racingpost.racing.api.e2e;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Belly {
    public void eat(int cukes) {

    }

    public static void main(String[] args) {
        String json = "{\n" +
                "    \"store\": {\n" +
                "        \"book\": [\n" +
                "            {\n" +
                "                \"category\": \"reference\",\n" +
                "                \"author\": \"Nigel Rees\",\n" +
                "                \"title\": \"Sayings of the Century\",\n" +
                "                \"price\": 8.95\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Evelyn Waugh\",\n" +
                "                \"title\": \"Sword of Honour\",\n" +
                "                \"price\": 12.99\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Herman Melville\",\n" +
                "                \"title\": \"Moby Dick\",\n" +
                "                \"isbn\": \"0-553-21311-3\",\n" +
                "                \"price\": 8.99\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"J. R. R. Tolkien\",\n" +
                "                \"title\": \"The Lord of the Rings\",\n" +
                "                \"isbn\": \"0-395-19395-8\",\n" +
                "                \"price\": 22.99\n" +
                "            }\n" +
                "        ],\n" +
                "        \"bicycle\": {\n" +
                "            \"color\": \"red\",\n" +
                "            \"price\": 19.95\n" +
                "        }\n" +
                "    },\n" +
                "    \"expensive\": 10\n" +
                "}";

        Configuration conf = Configuration.builder().jsonProvider(new GsonJsonProvider())
                .mappingProvider(new GsonMappingProvider()).build();

        JsonArray jsonArray = JsonPath.using(conf).parse(json).read("$.store.book[*]");
        Type listType = new TypeToken<List<Book>>() {}.getType();

        List<String> books = new Gson().fromJson(jsonArray, listType);
        System.out.println(books.size());

        Book book = JsonPath.using(conf).parse(json).read("$.store.book[0]", Book.class);
        System.out.println(book.getAuthor());
//        JsonObject jsonObject = JsonPath.using(conf).parse(json).read("$.store.book[0]");
//        Gson gson = new Gson();
//        Book book = gson.fromJson(jsonObject, Book.class);
//        System.out.println(book.getAuthor());
//        System.out.println(jsonObject);
//        System.out.println(jsonObject.get("category"));

        //jsonArray.forEach(element -> System.out.println(element.getAsString()));

        //System.out.println(book);


//        List<Map<String, Object>> authors = JsonPath.parse(json).read("$.store.book[?(@.author == 'Nigel Rees')]");
//
//        System.out.println(authors.get(0).get("author"));
        //System.out.println(authors.get(0).getAuthor());
    }
}

class Book {
    private String category;
    private String author;
    private String title;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
