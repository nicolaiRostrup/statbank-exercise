package gov.ufst.statbank_exercise.data.model

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

@GET("member")
fun getMemberData(): Call<ApiResponse<MemberData>> {

}

data class MemberData(
    @SerializedName("Id") val id: String,
    @SerializedName("FullName") val fullName: String,
)



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