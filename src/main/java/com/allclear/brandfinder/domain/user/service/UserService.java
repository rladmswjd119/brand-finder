package com.allclear.brandfinder.domain.user.service;

import com.allclear.brandfinder.domain.user.dto.JoinForm;
import com.allclear.brandfinder.domain.user.entity.User;

public interface UserService {


    User signUp(JoinForm form);

}
