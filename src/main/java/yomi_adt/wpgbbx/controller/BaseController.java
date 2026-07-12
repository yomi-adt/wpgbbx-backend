package yomi_adt.wpgbbx.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BaseController {

    @GetMapping("/ping")
    public String ping() {
        System.out.println("Got a ping request");
        return "Backend is reachable!";
    }
}