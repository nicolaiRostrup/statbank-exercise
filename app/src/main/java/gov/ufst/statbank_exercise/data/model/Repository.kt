package gov.ufst.statbank_exercise.data.model

import gov.ufst.statbank_exercise.ui.helpers.DeliveryType
import gov.ufst.statbank_exercise.ui.helpers.toZuluTime
import java.util.*


class Repository{

    lateinit var allDeliveryData: List<ServerDataObject>
    var lastUpdate: String? = null

    fun setUpdateTime(){
        val localNow = Calendar.getInstance()
        lastUpdate = localNow.toZuluTime()
    }

}

data class ServerDataObject(
    val deliveryType: DeliveryType,
    val year: String,
    val count: Int
)