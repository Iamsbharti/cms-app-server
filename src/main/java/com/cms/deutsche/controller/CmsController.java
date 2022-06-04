package com.cms.deutsche.controller;

import com.cms.deutsche.common.ApiResponse;
import com.cms.deutsche.model.*;
import com.cms.deutsche.service.ArticleService;
import com.cms.deutsche.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController()
@RequestMapping("/api/v1/cms")
public class CmsController {
    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    @GetMapping("/health")
    public ApiResponse serverHealth(){
        ApiResponse apiResponse = new ApiResponse("200","CMS-Server-UP",null);
        return apiResponse;
    }

    @PostMapping("/user/register")
    public ApiResponse registerUser(@RequestBody User user){
        UserResponse savedUser = userService.saveUser(user);
        return new ApiResponse("200","User Registered Success",savedUser);
    }

    @GetMapping("/user/login/password")
    public ApiResponse userLoginWithPassword(@RequestBody UserPasswordAuthentication userPasswordAuthentication){
        return userService.userPasswordAuthentication(userPasswordAuthentication.getEmail(), userPasswordAuthentication.getPassword());
    }

    @GetMapping("/user/login/multi-factor")
    public ApiResponse userMultiFactorAuthentication(@RequestBody MultifactorAuthentication multifactorAuthentication){
        return userService.userMultifactorAuthentication(multifactorAuthentication);
    }

    @PostMapping("/article/post")
    public ApiResponse postArticle(@RequestBody Article article){
        return articleService.saveArticle(article);
    }

    @GetMapping("/article/approved")
    public  ApiResponse getAllApprovedArticles(){
        return articleService.getAllApprovedArticles();
    }

    @GetMapping("/article/draft")
    public  ApiResponse getAllDraftArticles(){
        return articleService.getAllDraftArticles();
    }

    @GetMapping("/article/all")
    public  ApiResponse getAllArticles(){
        return articleService.getAllArticles();
    }

    @PutMapping("/article/update")
    public  ApiResponse getAllArticles(@RequestBody Article article){
        return articleService.updateArticle(article);
    }

    @GetMapping("/article/search")
    public ApiResponse searchArticle(@RequestParam(value = "search") String search){
        return articleService.searchArticles(search);
    }
}
