package gov.ufst.statbank_exercise.data.model

import android.util.Log
import dk.danskmetal.mitmetal.core.managers.ErrorManager
import dk.danskmetal.mitmetal.core.managers.NetworkAvailabilityManager

import gov.ufst.statbank_exercise.data.model.CallResult
import dk.danskmetal.mitmetal.core.webapi.fetch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

class StatBankRepository {

    private val memberDataChannel = ConflatedBroadcastChannel<CallResult<MemberData>>()

//    private val v2MemberDataChannel = ConflatedBroadcastChannel<CallResult<V2MemberData>>()
//
//    private val positionDataChannel = ConflatedBroadcastChannel<CallResult<List<PositionInformation>>>()
//
//    private val employerDataChannel = ConflatedBroadcastChannel<CallResult<List<EmployerInformation>>>()


    init {
        GlobalScope.launch(newSingleThreadContext("repo")) {
            networkAvailabilityManager.subscribeNetworkAvailability().consumeEach {
                if (it && memberDataChannel.valueOrNull != null) {
                    fetchMemberData()
                }
            }
        }
    }

    fun subscribeMemberData() = memberDataChannel.openSubscription()

//    fun subscribeV2MemberData() = v2MemberDataChannel.openSubscription()
//
//    fun subscribePositionData() = positionDataChannel.openSubscription()
//
//    fun subscribeEmployerData() = employerDataChannel.openSubscription()

    fun clearData() {
        memberDataChannel.offer(CallResult.Success(null))
        //v2MemberDataChannel.offer(CallResult.Success(null))
    }

    suspend fun fetchMemberData() {
        //when the app is installed / updated this will produce a unauthorized response

            getMemberData()
                .fetch()
                .also {
                    if (it is CallResult.Error) {
                        Log.e("", "error...")
                    }
                    memberDataChannel.offer(it)
                }

    }

//    suspend fun fetchV2MemberData() {
//        //when the app is installed / updated this will produce a unauthorized response
//        if (sessionManager.isSignedIn()) {
//            api.getV2MemberData()
//                .fetch()
//                .also {
//                    if (it is CallResult.Error) {
//                        errorManager.submitError(it)
//                    }
//                    v2MemberDataChannel.offer(it)
//                }
//        }
//    }
//
//    suspend fun updateMemberData(memberData: UpdateMemberData): CallResult<String?> {
//        return api.updateMemberData(memberData)
//            .fetch()
//            .also { callResult ->
//                if (callResult is CallResult.Error) {
//                    errorManager.submitError(callResult)
//                } else {
//                    val newData: CallResult<MemberData>
//                    val oldData = memberDataChannel.value
//                    if (oldData is CallResult.Success) {
//                        when {
//                            memberData.mobilePhone != null -> newData =
//                                oldData.copy(data = oldData.data?.copy(mobilePhone = memberData.mobilePhone))
//                            memberData.email != null -> newData =
//                                oldData.copy(data = oldData.data?.copy(email = memberData.email))
//                            else -> newData = oldData
//                        }
//                    } else
//                        newData = oldData
//                    memberDataChannel.offer(newData)
//                }
//            }
//    }
//
//    suspend fun updateV2MemberData(memberData: UpdateV2MemberData): CallResult<String?> {
//        return api.updateV2MemberData(memberData)
//            .fetch()
//            .also { callResult ->
//                if (callResult is CallResult.Error) {
//                    errorManager.submitError(callResult)
//                } else {
//                    val newData: CallResult<V2MemberData>
//                    val oldData = v2MemberDataChannel.value
//                    if (oldData is CallResult.Success) {
//                        when {
//                            memberData.employerInformation != null -> newData =
//                                oldData.copy(data = oldData.data?.copy(employerInformation = memberData.employerInformation))
//                            memberData.positionInformation != null -> newData =
//                                oldData.copy(data = oldData.data?.copy(positionInformation = memberData.positionInformation))
//                            else -> newData = oldData
//                        }
//                    } else
//                        newData = oldData
//                    v2MemberDataChannel.offer(newData)
//                }
//            }
//    }
//
//    suspend fun sendJob(job: Job): CallResult<Unit> {
//        return api.sendJob(job)
//            .fetch()
//            .also {
//                if (it is CallResult.Error) {
//                    errorManager.submitError(it)
//                }
//            }
//    }
//
//    suspend fun fetchAllJobPositions() {
//        if (sessionManager.isSignedIn()) {
//            api.getAllPositions()
//                .fetch()
//                .also {
//                    if (it is CallResult.Error) {
//                        errorManager.submitError(it)
//                    }
//                    positionDataChannel.offer(it)
//                }
//        }
//    }
//
//    suspend fun fetchEmployersByZip(zipCode: String) {
//        if (sessionManager.isSignedIn()) {
//            api.getEmployersByZip(zipCode)
//                .fetch()
//                .also {
//                    if (it is CallResult.Error) {
//                        errorManager.submitError(it)
//                    }
//                    employerDataChannel.offer(it)
//                }
//        }
//    }
}