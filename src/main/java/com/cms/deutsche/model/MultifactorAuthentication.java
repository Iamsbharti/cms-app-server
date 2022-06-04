package com.cms.deutsche.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultifactorAuthentication {
    private String email;
    private String securityQuestion;
    private String securityAnswer;
}
