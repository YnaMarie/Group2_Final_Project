package com.cea.auf.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CategoryCovertor {
    @TypeConverter
    public static List<JOBSModel.Result.Category> fromString(String value) {
        Type listType = new TypeToken<List<JOBSModel.Result.Category>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<JOBSModel.Result.Category> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
