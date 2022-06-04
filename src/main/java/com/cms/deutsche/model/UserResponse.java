package com.cms.deutsche.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Integer userId;
    private String name;
    private String email;
    private String securityQuestion;
    private String type;
    private List<Article> articleList;
}
