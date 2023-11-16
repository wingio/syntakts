package xyz.wingio.syntakts.util

import android.util.Log

internal actual class LoggerImpl actual constructor(
    private val tag: String
): Logger {

    override fun info(message: String) {
        Log.i(tag, message)
    }

    override fun debug(message: String) {
        Log.d(tag, message)
    }

    override fun warn(message: String, throwable: Throwable?) {
        Log.w(tag, message, throwable)
    }

    override fun error(message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }

}