package com.shanchuang.ssf.net.rxjava;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.shanchuang.ssf.net.entity.BaseBean;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Charles on 2016/3/17.
 */
class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;
    private Context context;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
//        String s = String.valueOf(response.charAt(0));
        //httpResult 只解析result字段
//        if ("[".equals(s)) {
//            response = "{\"data\":" + response + "}";
//        }
        Log.d("===服务器返回数据===", "response>>" + response);
        BaseBean httpResult = gson.fromJson(response, BaseBean.class);
        Log.i("=====Msg=======", httpResult.getMsg());
        if (httpResult.getCode() != 1 ) {
            Log.i("======错误代码======", httpResult.getCode() + "");
            throw new ApiException(httpResult);
        }
        return gson.fromJson(response, type);


    }
}
