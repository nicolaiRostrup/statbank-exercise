package gov.ufst.statbank_exercise.ui.helpers


data class UserRequest(
    var deliveryType: DeliveryType = DefaultSettings.deliveryType,
    var fromYear: Int = DefaultSettings.minimumYear,
    var untilYear: Int = DefaultSettings.maximumYear )


object DefaultSettings {
    val deliveryType = DeliveryType.SINGLE
    const val minimumYear = 1850
    const val maximumYear = 2018

}