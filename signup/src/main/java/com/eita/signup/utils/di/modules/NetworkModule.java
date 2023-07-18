package com.eita.signup.utils.di.modules;

import androidx.lifecycle.MutableLiveData;


import com.airbnb.lottie.BuildConfig;
import com.eita.signup.data.responses.BaseResponse;
import com.eita.signup.data.sources.ApiInterface;
import com.eita.signup.utils.di.qualifires.HeadersInterceptor;
import com.eita.signup.utils.di.qualifires.Loading;
import com.eita.signup.utils.di.qualifires.ResponseInterceptor;
import com.eita.signup.utils.di.qualifires.Signup;
import com.eita.signup.utils.helpers.Constants;
import com.eita.signup.utils.helpers.UserPreferences;
import com.eita.signup.utils.helpers.Validation;
import com.google.gson.Gson;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {


    @Provides
    @Singleton
    public ApiInterface retrofitInterface(@Signup OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface.class);
    }

    @Provides
    @Singleton
    @Signup

    public OkHttpClient provideOkHttpClient(
            @Signup HttpLoggingInterceptor loggingInterceptor,
            @HeadersInterceptor Interceptor headerInterceptor,
            @ResponseInterceptor Interceptor bodyInterceptor
    ) {
        OkHttpClient.Builder builder = new OkHttpClient
                .Builder()
                .addInterceptor(bodyInterceptor)
                .addInterceptor(headerInterceptor);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor);
        }
        return builder.build();
    }

    @Provides
    @Singleton
    @Signup
    public HttpLoggingInterceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor(Timber::d).setLevel(HttpLoggingInterceptor.Level.BODY);
    }


    @HeadersInterceptor
    @Provides
    @Singleton
    public Interceptor provideHeaderInterceptor(UserPreferences userPreferences) {
        return chain -> {
            int timeoutSeconds = 10;
            Request request = chain.request();
            Request.Builder requestBuilder = request.newBuilder();
            requestBuilder
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Timeout", String.valueOf(timeoutSeconds));
            if (!userPreferences.getAuthToken().isEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer " + userPreferences.getAuthToken());
            }
            return chain.proceed(requestBuilder.build());
        };
    }

    @Provides
    @Signup
    public CompositeDisposable compositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    public MutableLiveData<BaseResponse> baseResponseMutableLiveData() {
        return new MutableLiveData<>();
    }


    @Provides
    @Loading
    public MutableLiveData<Boolean> loadingMutableLiveData() {
        return new MutableLiveData<>();
    }




    @Provides
    @ResponseInterceptor
    public Interceptor responseInterceptor(Validation validation) {
        return chain -> {

            Response response = chain.proceed(chain.request());
            Response.Builder newResponse = response.newBuilder();
            ResponseBody body = response.body();
            Gson gson = new Gson();
            String json = body.string();
            if (response.code() > 200 && validation.isValidJSON(json)) {

                BaseResponse baseResponse = gson.fromJson(json, BaseResponse.class);
                baseResponse.setStatusCode(response.code());
                newResponse.body(ResponseBody.create(gson.toJson(baseResponse), body.contentType())).code(200);
                return newResponse.build();
            }
            newResponse.body(ResponseBody.create(json, body.contentType()));
            return newResponse.build();

        };
    }


}
