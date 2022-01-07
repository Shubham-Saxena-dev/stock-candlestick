package com.republic.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonMapper {

    private Gson gson;

    private GsonMapper() {
        gson = new GsonBuilder().create();
    }

    public static Gson getInstance() {
        return IBuilder.INSTANCE.gson;
    }

    private static final class IBuilder {
        private static final GsonMapper INSTANCE = new GsonMapper();

    }
}
