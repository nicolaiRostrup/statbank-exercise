package gov.ufst.statbank_exercise.data.model

import com.google.gson.annotations.SerializedName


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
data class DeliveryData(
    @SerializedName("dataset") val dataset: DataSet
)

data class DataSet(
    @SerializedName("value") val value: List<Int>
)









