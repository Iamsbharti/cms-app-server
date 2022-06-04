package com.cms.deutsche.service;

import com.cms.deutsche.common.ApiResponse;
import com.cms.deutsche.model.Article;
import com.cms.deutsche.model.MultifactorAuthentication;
import com.cms.deutsche.model.User;
import com.cms.deutsche.model.UserResponse;
import com.cms.deutsche.repository.ArticleRepository;
import com.cms.deutsche.repository.UserRepository;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    Logger logger = LogManager.getLogger(UserService.class);
    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    public UserResponse saveUser(User user){
        logger.info("Save user Service::");
        User newUser = new User();

        if(user!=null){
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setSecurityQuestion(user.getSecurityQuestion());
            newUser.setSecurityAnswer(user.getSecurityAnswer());
            newUser.setPassword(user.getPassword());
            newUser.setType(user.getType());
            logger.info("New User Object:"+newUser);
            newUser = userRepository.save(newUser);
        }
        // compose user response
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(newUser.getUserId());
        userResponse.setName(newUser.getName());
        userResponse.setEmail(newUser.getEmail());
        userResponse.setType(newUser.getType());
        userResponse.setSecurityQuestion(newUser.getSecurityQuestion());
        return userResponse;
    }

    public ApiResponse userPasswordAuthentication(String emailId,String password){
        logger.info("User Password Authentication::"+emailId+"--"+password);
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(emailId));
        if(user.isPresent()){
            User userInfo = user.get();
            if(userInfo.getPassword().equalsIgnoreCase(password)) {
                UserResponse userResponse = new UserResponse();
                userResponse.setUserId(userInfo.getUserId());
                userResponse.setName(userInfo.getName());
                userResponse.setEmail(userInfo.getEmail());
                userResponse.setType(userInfo.getType());
                userResponse.setSecurityQuestion(userInfo.getSecurityQuestion());
                logger.info("Login User Object Response::" + userInfo);
                return new ApiResponse("200", "User Auth With Password Success", userInfo);
            }else{
                return new ApiResponse("401","User Auth With Password failed",emailId);
            }
        }else{
            return new ApiResponse("404","User Not Found",emailId);
        }
    }

    public ApiResponse userMultifactorAuthentication(MultifactorAuthentication authentication){
        logger.info("User Multi-factor Authentication::");
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(authentication.getEmail()));
        if(user.isPresent()){
            User userInfo = user.get();

            if(userInfo.getSecurityAnswer().equalsIgnoreCase(authentication.getSecurityAnswer())){
                // get all articles based on user[Type]
                List<Article> articles = null;
                switch (userInfo.getType()) {
                    case "SUPER":
                        articles = articleRepository.findAll();
                        break;
                    case "ADMIN":
                        articles = articleRepository.findAllByContributor(authentication.getEmail());
                        break;
                    case "READER":
                        articles = articleRepository.findAllByStatus("approved");
                        break;
                }
                UserResponse userResponse = new UserResponse();
                userResponse.setUserId(userInfo.getUserId());
                userResponse.setName(userInfo.getName());
                userResponse.setEmail(userInfo.getEmail());
                userResponse.setType(userInfo.getType());
                userResponse.setSecurityQuestion(userInfo.getSecurityQuestion());
                userResponse.setArticleList(articles);
                logger.info("Multi-factor Login User Object Response::"+userResponse);
                return new  ApiResponse("200","User Login Success",userResponse);
            }else{
                return new ApiResponse("401","Multi Factor Authentication Failed",authentication.getEmail());
            }
        }else{
            return new ApiResponse("404","User Not Found",authentication.getEmail());
        }
    }
}
