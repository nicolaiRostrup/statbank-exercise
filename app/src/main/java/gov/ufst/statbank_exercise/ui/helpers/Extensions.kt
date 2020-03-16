package gov.ufst.statbank_exercise.ui.helpers

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun Int.toDeliveryType(): DeliveryType {

    return when (this) {
        0 -> DeliveryType.SINGLE
        1 -> DeliveryType.TWIN
        2 -> DeliveryType.TRIPLET
        3 -> DeliveryType.QUADRUPLET

        else -> throw Exception("Delivery type unknown: $this")
    }

}

fun DeliveryType.toServerFormat(): String {

    return when (this) {
        DeliveryType.SINGLE -> "09"
        DeliveryType.TWIN -> "10"
        DeliveryType.TRIPLET -> "11"
        DeliveryType.QUADRUPLET -> "12"

    }

}

fun DeliveryType.toInt(): Int {

    return when (this) {
        DeliveryType.SINGLE -> 0
        DeliveryType.TWIN -> 1
        DeliveryType.TRIPLET -> 2
        DeliveryType.QUADRUPLET -> 3

    }

}

fun Calendar.toZuluTime(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    return sdf.format(this.time)
}

