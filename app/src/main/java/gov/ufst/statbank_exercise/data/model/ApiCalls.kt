package gov.ufst.statbank_exercise.data.model


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiCalls {


    @POST("v1/data/")
    fun getTwinData(@Body body: DataRequest): Call<TwinData>

//    @POST("v1/data/")
//    fun getTwinData(): Call<TwinData>

//    @GET("member")
//    fun getMemberData(): Call<ApiResponse<MemberData>>

//    @GET("/todos/{id}")
//    fun getTwinData(@Path(value = "id") todoId: Int): Call<TwinData>

    //@GET("v2/member")
//fun getV2MemberData(): Call<ApiResponse<V2MemberData>>
//
//@GET("stillinger")
//fun getAllPositions(): Call<ApiResponse<List<PositionInformation>>>
//
//@GET("arbejdspladser")
//fun getEmployersByZip(@Header("Zipcode") zip: String): Call<ApiResponse<List<EmployerInformation>>>
//
//@PATCH("member")
//fun updateMemberData(@Body memberData: UpdateMemberData): Call<ApiResponse<String?>>
//
//@PATCH("v2/member")
//fun updateV2MemberData(@Body memberData: UpdateV2MemberData): Call<ApiResponse<String?>>
//
//@POST("SubmitJob")
//fun sendJob(@Body job: Job): Call<ApiResponse<Unit>>

}