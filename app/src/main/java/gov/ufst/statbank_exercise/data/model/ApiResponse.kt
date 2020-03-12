package gov.ufst.statbank_exercise.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CancellationException
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.await
import java.net.HttpURLConnection
import java.net.UnknownHostException

data class ApiResponse<T>(
    @SerializedName("Data") val data: T?,
    @SerializedName("Error") val errorMessage: ErrorMessage?,
    @SerializedName("Success") val success: Boolean,
    @SerializedName("Status") val statusCode: Int
)

data class ErrorMessage(
    @SerializedName("UserMessage") val userMessage: String
)

/**
 * Network call result base class.
 */
sealed class CallResult<out T : Any?> {

    /**
     * Success class with contains a [data] field with the network call result.
     */
    data class Success<out T : Any?>(val data: T?) : CallResult<T>()

    /**
     * Base Error class that can be extended with individual errors.
     */
    sealed class Error(val message: String) : CallResult<Nothing>() {

        /**
         * Base class for all network-related errors, either server thrown exceptions or from API domain layer.
         */
        sealed class NetworkError(
            message: String,
            val code: Int
        ) : Error(message) {

            /**
             * Error specific to API domain layer.
             */
            data class ApiError(
                val errorMessage: String,
                val statusCode: Int
            ) : NetworkError(errorMessage, statusCode)

            /**
             * Standard server thrown HTTP exceptions.
             */
            data class HttpError(
                val errorMessage: String,
                val statusCode: Int
            ) : NetworkError(errorMessage, statusCode)

            /**
             * Error thrown when the network is offline.
             */
            data class OfflineError(
                val errorMessage: String,
                val statusCode: Int = HttpURLConnection.HTTP_BAD_GATEWAY
            ) : NetworkError(errorMessage, statusCode)
        }

        /**
         * Error used to denote a suspending function cancellation request.
         */
        data class CancellationError(
            val errorMessage: String
        ) : Error(errorMessage)

        /**
         * General purpose error that can be thrown under any circumstance.
         */
        data class GeneralError(
            val errorMessage: String
        ) : Error(errorMessage)
    }
}

/**
 * Perform the [ApiResponse] API call and parse the result into a [CallResult] in accordance to success/failure status.
 */
suspend fun <T : Any?> Call<ApiResponse<T>>.fetch(): CallResult<T> {
    return try {
        val apiResponse = await()
        if (apiResponse.success) {
            Success(apiResponse.data)
        } else {
            CallResult.Error.NetworkError.ApiError(apiResponse.errorMessage?.userMessage ?: "", apiResponse.statusCode)
        }
    } catch (e: HttpException) {
        CallResult.Error.NetworkError.HttpError(e.message(), e.code())
    } catch (e: UnknownHostException) {
        CallResult.Error.NetworkError.OfflineError(e.message ?: "")
    } catch (e: CancellationException) {
        CancellationError(e.message ?: "")
    } catch (e: Exception) {
        GeneralError(e.message ?: "")
    }
}

/**
 * Perform a standard API call and parse the result into a [CallResult] in accordance to success/failure status.
 */
suspend fun <T : Any> Call<T>.fetchCall(): CallResult<T> {
    return try {
        val apiResponse = await()
        Success(apiResponse)
    } catch (e: HttpException) {
        CallResult.Error.NetworkError.HttpError(e.message(), e.code())
    } catch (e: UnknownHostException) {
        CallResult.Error.NetworkError.OfflineError(e.message ?: "")
    } catch (e: CancellationException) {
        CancellationError(e.message ?: "")
    } catch (e: Exception) {
        GeneralError(e.message ?: "")
    }
}