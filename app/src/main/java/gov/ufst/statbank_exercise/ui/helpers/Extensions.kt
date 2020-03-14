package gov.ufst.statbank_exercise.ui.helpers

import java.lang.Exception

fun Int.toDeliveryType(): DeliveryType {

    return when(this){
        0 -> DeliveryType.SINGLE
        1 -> DeliveryType.TWIN
        2 -> DeliveryType.TRIPLET
        3 -> DeliveryType.QUADRUPLET

        else -> throw Exception("Delivery type unknown")
    }

}

fun DeliveryType.toServerFormat(): String {

    return when(this){
        DeliveryType.SINGLE -> "09"
        DeliveryType.TWIN -> "10"
        DeliveryType.TRIPLET -> "11"
        DeliveryType.QUADRUPLET -> "12"

    }

}

fun DeliveryType.toInt(): Int {

    return when(this){
        DeliveryType.SINGLE -> 0
        DeliveryType.TWIN -> 1
        DeliveryType.TRIPLET -> 2
        DeliveryType.QUADRUPLET -> 3

    }

}