package com.tatvasoftpractical.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UsersModel : Serializable {

    @SerializedName("users")
    @Expose
    var users : ArrayList<UserDetailsModel>? = null

    @SerializedName("has_more")
    @Expose
    var hasMode : Boolean? = null

}