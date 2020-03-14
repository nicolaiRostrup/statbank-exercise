package gov.ufst.statbank_exercise.ui.helpers

data class UserRequest(
    var deliveryType: DeliveryType,
    var fromYear: Int,
    var untilYear: Int )

enum class DeliveryType{
    SINGLE, TWIN, TRIPLET, QUADRUPLET
}