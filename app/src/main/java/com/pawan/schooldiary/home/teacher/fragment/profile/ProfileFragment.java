package com.pawan.schooldiary.home.teacher.fragment.profile;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pawan.schooldiary.R;
import com.pawan.schooldiary.app.SchoolDiaryApplication;
import com.pawan.schooldiary.home.service.CommonService;
import com.pawan.schooldiary.home.utils.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EFragment(R.layout.fragment_profile)
public class ProfileFragment extends Fragment {

    @App
    SchoolDiaryApplication schoolDiaryApplication;

    @ViewById(R.id.button_upload_pic)
    Button buttonUploadPic;

    private String photoFileName;
    private CommonService commonService;

    @AfterViews
    public void init() {
        commonService = schoolDiaryApplication.retrofit.create(CommonService.class);
        photoFileName = Utils.getLoggedInEmail(getContext()) + ".png";
    }

    @Click(R.id.button_upload_pic)
    public void changeProfilePicture() {
        // TODO add alert dialog for choose profile pic from camera | Gallery
    }

    private void chooseFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            intent = getCropIntent(intent);
            startActivityForResult(intent, 2);
        }
    }

    private void chooseFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(getCropIntent(galleryIntent), 1);
    }

    private Intent getCropIntent(Intent intent) {
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        return intent;
    }

    private void uploadProfilePic(final File file) {
        // 4194304 = 4 MB
        if (file.length() > 4194304) {
            // TODO show error about image have more size
            //Snackbar snackbar = Snackbar.make(getView(), context.getString(R.string.pic_error), Snackbar.LENGTH_LONG);
            //snackbar.show();
            return;
        }

        Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, bos);


        InputStream in = new ByteArrayInputStream(bos.toByteArray());
        Log.e("File size user", String.valueOf(file.length()));
        Utils.copyInputStreamToFile(in, file);
        Log.e("File size compress", String.valueOf(file.length()));

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = "profile_pic";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);

        Utils.clearGlideCache(getActivity());

        // finally, execute the request
        Call<ResponseBody> call = commonService.uploadProfilePic(description, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public Uri getPhotoFileUri(String fileName) {
        if (isExternalStorageAvailable()) {
            File mediaStorageDir = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "PROFILE_FRAGMENT");
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            }
            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
        }
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 2) {
                Uri selectedImage = getPhotoFileUri(photoFileName);
                File file = new File(selectedImage.getPath());
                uploadProfilePic(file);
            } else if (requestCode == 1 && null != data) {
                Uri selectedImage = data.getData();
                File file = new File(getPath(selectedImage));
                uploadProfilePic(file);
            }
        } catch (Exception e) {
        }
    }

    public String getPath(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        return cursor.getString(columnIndex);

    }
}
