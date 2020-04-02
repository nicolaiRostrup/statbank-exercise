package gov.ufst.statbank_exercise.ui.helpers

/**
 * With this class one can make handling the events explicit.
 * All the observers get the data and they may get it more than once (since activity/fragment may get destroyed and upon reaction get the data again),
 * but the event will be handled only once by the first observer who decides to handle it.
 * Thanks to Alireza A. Ahmadi for this implementation:
 * See also, https://android.jlelse.eu/sending-events-from-viewmodel-to-activities-fragments-the-right-way-26bb68502b24
 */

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandledOrReturnNull(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}