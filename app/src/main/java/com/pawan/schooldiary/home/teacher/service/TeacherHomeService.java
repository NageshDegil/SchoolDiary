package com.pawan.schooldiary.home.teacher.service;

import com.pawan.schooldiary.home.model.Chat;
import com.pawan.schooldiary.home.model.Group;
import com.pawan.schooldiary.home.model.Status;
import com.pawan.schooldiary.home.model.User;


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

    @POST("/SchoolDiary_web/index.php/add-group")
    Observable<ResponseBody> addGroup(@Body Group group);

    @POST("/SchoolDiary_web/index.php/get-groups")
    Observable<List<Group>> getGroups(@Body Status status);

    @POST("/SchoolDiary_web/index.php/change-group-name")
    Observable<Status> changeGroupName(@Body Group group);

    @POST("/SchoolDiary_web/index.php/remove-members")
    Observable<ResponseBody> removeMembers(@Body Group group);

    @POST("/SchoolDiary_web/index.php/add-members")
    Observable<ResponseBody> addMembers(@Body Group group);
}
