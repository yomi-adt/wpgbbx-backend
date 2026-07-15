package yomi_adt.wpgbbx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BaseController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/ping")
    public String ping() {
        System.out.println("Got a ping request");
        return "Backend is reachable!";
    }

    @GetMapping("/debug")
    public String debug() {
        return mongoTemplate.getDb().getName();
    }
}