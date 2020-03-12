package gov.ufst.statbank_exercise.data.model

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST



//data class MemberData(
//    @SerializedName("Id") val id: String,
//    @SerializedName("FullName") val fullName: String
//)

//Outgoing
data class DataRequest(
    @SerializedName("lang") val lang: String,
    @SerializedName("table") val table: String,
    @SerializedName("format") val format: String,
    @SerializedName("variables") val variables: List<Variable>
)

data class Variable(
    @SerializedName("code") val code: String,
    @SerializedName("placement") val placement: String,
    @SerializedName("values") val values: List<String>

)

//Incoming
data class TwinData(
    @SerializedName("dataset") val dataset: DataSet
)

data class DataSet(
    @SerializedName("value") val value: List<Int>
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