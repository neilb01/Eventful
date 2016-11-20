package com.example.greenteam.eventfulevents.Retrofit.utility;

import android.util.Log;

import com.example.greenteam.eventfulevents.Retrofit.Interface.APIListService;
import com.example.greenteam.eventfulevents.Retrofit.application.APIError;
import com.example.greenteam.eventfulevents.Retrofit.application.ErrorUtils;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public  class RetrofitUtil {


    public static retrofit2.Retrofit retrofit;
    private static APIListService service;
    private Object cls;

    private Call<String> call;

    public RetrofitUtil() {

        if (retrofit == null) {

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
            client.dispatcher().setMaxRequests(3);

            retrofit = new retrofit2.Retrofit.Builder()
//            http://api.eventful.com/docs/events/search
                    .baseUrl(" http://api.eventful.com/")
                    .client(client)
                    .addConverterFactory(new ToStringConverterFactory())
                    .build();
            service = retrofit.create(APIListService.class);
        }
    }

    public APIListService getAPIListService() {
        service = retrofit.create(APIListService.class);
        return service;
    }

    public void callAPI(Call<String> objectCall, Object clz, final OnLoadCallback onLoadCallback) {
        if (service != null) {

            call = objectCall;
            cls = clz;
            call.enqueue(new Callback<String>() {

                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    Log.v("Tag", "Response=>" + response.body());
                    if (response.isSuccessful()) {

                        cls = new Gson().fromJson(response.body(), cls.getClass());
                        onLoadCallback.onResponseSuccess(cls);

                    } else {
                        APIError error = ErrorUtils.parseError(response);
                        onLoadCallback.onResponseFailure(error.message());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    if (!call.isCanceled()) {
                        if (throwable.getMessage() != null && throwable.getMessage().length() != 0)
                            onLoadCallback.onResponseFailure(throwable.getMessage());
                        else
                            onLoadCallback.onResponseFailure("Server error. Please try again");
                    }
                }
            });
        }
    }

    public void cancelRequest() {

        if (call != null)
            call.cancel();
    }

    public interface OnLoadCallback {

        void onResponseSuccess(Object response);

        void onResponseFailure(Object error);
    }
}
