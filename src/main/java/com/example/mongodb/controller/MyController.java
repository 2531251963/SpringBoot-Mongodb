package com.example.mongodb.controller;

import com.example.mongodb.bean.User;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MyController
 * @Description 业务增删改查
 * @Author Liyihe
 * @Date 19-8-11 下午1:19
 * @Version 1.0
 */
@RestController
public class MyController {
    private final MongoDatabase db;

    @Autowired
    public MyController(MongoDatabase db) {
        this.db = db;
    }

    @RequestMapping(value = "/search/all", method = RequestMethod.POST)
    public String test(){
        MongoCollection<Document> collection=db.getCollection("user");
        FindIterable<Document> findIterable=  collection.find();
        List<String> list=new ArrayList<>();
        for (Document document:findIterable) {
            System.out.println( document.toJson());
            list.add(document.toJson());
        }
        return list.toString();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "id",defaultValue = "all") String id){
        if (id.equals("all")) {
            return test();
        }else if (id.length()!=24){
            return "[]";
        }
        System.out.println(id);
        MongoCollection<Document> collection=db.getCollection("user");
        Bson bson=Filters.eq("_id",new ObjectId(id));
        FindIterable<Document> findIterable=collection.find(bson);
        List<String> list=new ArrayList<>();
        for (Document document:findIterable) {
            System.out.println( document.toJson());
            list.add(document.toJson());
        }
        return list.toString();
    }

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public String insert(@RequestBody User user) throws IllegalAccessException {
        MongoCollection<Document> collection=db.getCollection("user");
        Field[] fields=user.getClass().getDeclaredFields();
        Document document=new Document();
        /**    Document document=new Document(map);
         *   或者
         *     Document document=new Document("key","value");
         *     document.append("key","value")
         *     这里用的反射
         */
        for(int i = 0 ; i < fields.length ;i++) {
            fields[i].setAccessible(true);
            Object o=fields[i].get(user);
            if (fields[i].getName().equals("id")) continue;
            if (o!=null) {
                document.append(fields[i].getName(),o);
            }else {
                return "bad";
            }
        }
        collection.insertOne(document);
        return document.toJson();
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public String delete(@RequestBody String id){
        if (id==null||id.length()!=24) {
            return "bad_id";
        }
        System.out.println(id);
        MongoCollection<Document> collection=db.getCollection("user");
        Bson bson=Filters.eq("_id",new ObjectId(id));
        DeleteResult result=collection.deleteOne(bson);
        return result.getDeletedCount()+"";
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(@RequestBody User user){
        MongoCollection<Document> collection=db.getCollection("user");
        if (user.getId()==null||user.getId().length()!=24) return "bad_id";
        Bson filter = Filters.eq("_id", new ObjectId(user.getId()));
        Document document1=new Document("name",user.getName()).append("age",user.getAge()).append("phonenumber",user.getPhonenumber());
        Document document=new Document("$set",document1);
        collection.updateOne(filter,document);
        return document1.toJson();
    }
}
