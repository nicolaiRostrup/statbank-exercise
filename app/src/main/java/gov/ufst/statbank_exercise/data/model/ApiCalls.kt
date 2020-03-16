package gov.ufst.statbank_exercise.data.model


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiCalls {

    @POST("v1/data/")
    fun getBirthDeliveryData(@Body body: DataRequest): Call<DeliveryData>



}