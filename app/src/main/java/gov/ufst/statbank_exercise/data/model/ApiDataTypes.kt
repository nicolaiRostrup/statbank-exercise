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
data class TwinData(
    @SerializedName("dataset") val dataset: DataSet
)

data class DataSet(
    //@SerializedName("dimension") val dimension: Dimension,
    @SerializedName("value") val value: List<Int>
)

//data class Dimension(
//    @SerializedName("Tid") val tid: Tid
//)
//
//data class Tid(
//    @SerializedName("category") val category: Category
//)
//
//data class Category(
//    @SerializedName("index") val indexList: Map<String, Int>
//)









