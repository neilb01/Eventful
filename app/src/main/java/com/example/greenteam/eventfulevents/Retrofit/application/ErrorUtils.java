package com.example.greenteam.eventfulevents.Retrofit.application;


import com.example.greenteam.eventfulevents.Retrofit.utility.RetrofitUtil;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;


public class ErrorUtils {

    public static APIError parseError(Response<?> response) {
        APIError error = null;
        try {
            Converter<ResponseBody, APIError> converter = RetrofitUtil.retrofit
                    .responseBodyConverter(APIError.class, new Annotation[0]);
            try {
                error = converter.convert(response.errorBody());
            } catch (IOException e) {
                error = new APIError();
            }
        } catch (Exception e) {
            Converter<ResponseBody, String> converter = RetrofitUtil.retrofit.stringConverter(APIError.class, new Annotation[0]);
            try {
                if (error == null)
                    error = new APIError();

                if (response.errorBody().toString().contains("okhttp"))
                    error.setMessage("Server error.Please Try again after some time!");
                else
                    error.setMessage(converter.convert(response.errorBody()));
            } catch (IOException e1) {
                error = new APIError();
            }
        }

        return error;
    }
}