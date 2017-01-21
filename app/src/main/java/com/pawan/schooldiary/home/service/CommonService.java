package com.pawan.schooldiary.home.service;

import com.pawan.schooldiary.home.model.Status;
import com.pawan.schooldiary.home.model.User;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by pawan on 21/1/17.
 */

public interface CommonService {

    @POST("/SchoolDiary_web/index.php/get-contacts")
    Observable<List<User>> getContacts(@Body Status status);
}
