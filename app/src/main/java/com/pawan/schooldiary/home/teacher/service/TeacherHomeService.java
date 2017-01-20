package com.pawan.schooldiary.home.teacher.service;

import com.pawan.schooldiary.home.model.Chat;


import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by pawan on 19/1/17.
 */

public interface TeacherHomeService {

    @POST("/SchoolDiary_web/index.php/fetch-chat")
    Observable<List<Chat>> fetchChats(@Body Chat chat);

    @POST("/SchoolDiary_web/index.php/insert-chat")
    Observable<List<Chat>> insertChat(@Body Chat chat);
}
