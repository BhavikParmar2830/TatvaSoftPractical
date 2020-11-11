package com.tatvasoftpractical.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BaseModel : Serializable {

    @SerializedName("status")
    @Expose
    var status : Boolean? = null

    @SerializedName("message")
    @Expose
    var message : String? = null

    @SerializedName("data")
    @Expose
    var data : UsersModel? = null

}