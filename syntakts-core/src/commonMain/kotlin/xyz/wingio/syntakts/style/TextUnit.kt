package xyz.wingio.syntakts.style


/**
 * Framework independent representation of a text unit (sp and em)
 * @see sp
 * @see em
 */
public open class TextUnit(public open val value: Float, public val unit: String) {
    /**
     * Represents an unspecified text unit, typically used to pass through to a fallback
     */
    public object Unspecified : TextUnit(0f, "")
}

public class Sp(value: Float): TextUnit(value, "sp")

public class Em(value: Float): TextUnit(value, "em")

public val Int.sp: Sp
    get() = Sp(this.toFloat())

public val Int.em: Em
    get() = Em(this.toFloat())

public val Float.sp: Sp
    get() = Sp(this)

public val Float.em: Em
    get() = Em(this)

public val Double.sp: Sp
    get() = Sp(this.toFloat())

public val Double.em: Em
    get() = Em(this.toFloat())