package gov.ufst.statbank_exercise.data.model


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiCalls {


//    @GET("member")
//    fun getMemberData(): Call<ApiResponse<MemberData>>

//    @GET("/todos/{id}")
//    fun getTwinData(@Path(value = "id") todoId: Int): Call<TwinData>

    @POST("v1/data/")
    fun getTwinData(@Body body: DataRequest): Call<TwinData>

//    @POST("v1/data/")
//    fun getTwinData(): Call<TwinData>



}