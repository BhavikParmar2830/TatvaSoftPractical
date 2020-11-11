package com.tatvasoftpractical.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserDetailsModel : Serializable {

    @SerializedName("name")
    @Expose
    var name : String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("items")
    @Expose
    var items: ArrayList<String>? = null

}