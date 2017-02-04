package com.pawan.schooldiary.home.service;

import com.pawan.schooldiary.home.model.Chat;
import com.pawan.schooldiary.home.model.Status;
import com.pawan.schooldiary.home.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by pawan on 21/1/17.
 */

public interface CommonService {

    @POST("/SchoolDiary_web/index.php/get-contacts")
    Observable<List<User>> getContacts(@Body Status status);

    @POST("/SchoolDiary_web/index.php/add-recent-chat")
    Observable<ResponseBody> addRecentChat(@Body Chat chat);

    @POST("/SchoolDiary_web/index.php/get-recent-chats")
    Observable<List<User>> getRecentChats(@Body Chat chat);

    @POST("/SchoolDiary_web/index.php/send-notification")
    Observable<ResponseBody> sendPushNotification(@Body Status status);

    @POST("/SchoolDiary_web/index.php/update-token")
    Observable<ResponseBody> updateToken(@Body User user);

    @Multipart
    @POST("/SchoolDiary_web/index.php/upload-picture")
    Call<ResponseBody> uploadProfilePic(@Part("profile_pic") RequestBody description, @Part MultipartBody.Part file);
}
