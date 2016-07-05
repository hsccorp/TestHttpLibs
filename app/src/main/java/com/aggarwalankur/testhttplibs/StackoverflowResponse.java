package com.aggarwalankur.testhttplibs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ankur on 7/5/2016.
 */
public class StackoverflowResponse {

    @SerializedName("items")
    private List<QuestionPOJO> items;


    public List<QuestionPOJO> getQuestions() {
        return items;
    }
}
