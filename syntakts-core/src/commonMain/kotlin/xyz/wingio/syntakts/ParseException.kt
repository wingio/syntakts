package xyz.wingio.syntakts

public class ParseException internal constructor (message: String, source: CharSequence?, cause: Throwable? = null)
    : RuntimeException("Error while parsing: $message \n Source: $source", cause)