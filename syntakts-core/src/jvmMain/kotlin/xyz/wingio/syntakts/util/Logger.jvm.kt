package xyz.wingio.syntakts.util

internal actual class LoggerImpl actual constructor(
    private val tag: String
): Logger {

    override fun info(message: String) {
        println("I: [$tag] $message")
    }

    override fun debug(message: String) {
        println("D: [$tag] $message")
    }

    override fun warn(message: String, throwable: Throwable?) {
        println("""
            W: [$tag] $message ${
            throwable?.let {
                "\n${it.stackTraceToString()}"
            }
        }
        """.trimIndent())
    }

    override fun error(message: String, throwable: Throwable?) {
        System.err.println("""
            E: [$tag] $message ${
                throwable?.let { 
                    "\n${it.stackTraceToString()}"
                }
            }
        """.trimIndent())
    }

}