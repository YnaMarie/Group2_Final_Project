package com.cea.auf.model;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class Convertor {
    @TypeConverter
    public static List<JOBSModel.Result> fromString(String value) {
        Type listType = new TypeToken<List<JOBSModel.Result>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<JOBSModel.Result> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
