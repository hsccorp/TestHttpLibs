package com.aggarwalankur.testhttplibs;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ankur on 7/4/2016.
 */
public class QuestionPOJO {
    private String title;

    private Owner owner;

    public Owner getOwner() {
        return owner;
    }

    private class Owner{
        @SerializedName("profile_image")
        private String avatarUrl;

        public String getAvatarUrl() {
            return avatarUrl;
        }
    }

    public String getTitle() {
        return title;
    }

    public String getAvatarUrl() {
        return owner.getAvatarUrl();
    }


}
