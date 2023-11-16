package xyz.wingio.syntakts.util

public interface Logger {

    /**
     * Prints a message to the console
     *
     * @param message Message to be printed
     */
    public fun info(message: String)

    /**
     * Prints a message to the console at a higher log level
     *
     * @param message Message to be printed
     */
    public fun debug(message: String)

    /**
     * Prints a warning to the console, optionally with an error
     *
     * @param message Message to be printed
     * @param throwable Error to show alongside warning
     */
    public fun warn(message: String, throwable: Throwable? = null)

    /**
     * Prints an error to the console
     *
     * @param message Message to be printed
     * @param throwable Error to show alongside [message]
     */
    public fun error(message: String, throwable: Throwable? = null)

}

/**
 * Uses [println] on jvm and Log on Android
 *
 * @param tag Printed alongside each message for better locating
 */
internal expect class LoggerImpl internal constructor(tag: String): Logger