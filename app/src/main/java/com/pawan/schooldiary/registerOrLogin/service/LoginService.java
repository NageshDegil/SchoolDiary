package com.pawan.schooldiary.registerOrLogin.service;

import com.pawan.schooldiary.home.model.Chat;
import com.pawan.schooldiary.home.model.User;
import com.pawan.schooldiary.registerOrLogin.model.Login;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by pawan on 20/1/17.
 */

public interface LoginService {

    @POST("/SchoolDiary_web/index.php/login")
    Observable<User> login(@Body Login login);
}
