package xyz.wingio.syntakts.markdown

import xyz.wingio.syntakts.Syntakts
import xyz.wingio.syntakts.node.Node
import xyz.wingio.syntakts.node.styleNode
import xyz.wingio.syntakts.node.textNode
import xyz.wingio.syntakts.parser.ParseSpec
import xyz.wingio.syntakts.style.FontStyle
import xyz.wingio.syntakts.style.FontWeight
import xyz.wingio.syntakts.style.Style
import xyz.wingio.syntakts.style.StyledTextBuilder
import xyz.wingio.syntakts.style.TextDecoration
import xyz.wingio.syntakts.syntakts
import xyz.wingio.syntakts.util.hashCountToStyle

private val ITALICS_REGEX = (
            // only match _s surrounding words.
            "^\\b_" + "((?:__|\\\\[\\s\\S]|[^\\\\_])+?)_" + "\\b" +
            "|" +
            // Or match *s that are followed by a non-space:
            "^\\*(?=\\S)(" +
            // Match any of:
            //  - `**`: so that bolds inside italics don't close the italics
            //  - whitespace
            //  - non-whitespace, non-* characters
            "(?:\\*\\*|\\s+(?:[^*\\s]|\\*\\*)|[^\\s*])+?" +
            // followed by a non-space, non-* then *
            ")\\*(?!\\*)"
).toRegex()

private val BOLD_REGEX = "\\*\\*([\\s\\S]+?)\\*\\*(?!\\*)".toRegex()

private val UNDERLINE_REGEX = "__([\\s\\S]+?)__(?!_)".toRegex()

private val STRIKETHROUGH_REGEX = "~~([\\s\\S]+?)~~(?!~)".toRegex()

private val HEADER_REGEX = "\\s*(#+)[ \\t](.+) *(?=\\n|\$)".toRegex()


/**
 * Adds a simple bold rule
 *
 * Example: `**Some Text**` -> **Some Text**
 *
 * @return [Syntakts.Builder] To allow for builder method chaining
 */
public fun <C> Syntakts.Builder<C>.addBoldRule(): Syntakts.Builder<C> = addRule(BOLD_REGEX, name = "Bold") {
    styleNode(Style(fontWeight = FontWeight.Bold), it.groups[1]!!.range)
}

/**
 * Adds a simple italics rule
 *
 * Example: `*Some Text*` or `_Some Text_` -> *Some Text*
 *
 * @return [Syntakts.Builder] To allow for builder method chaining
 */
public fun <C> Syntakts.Builder<C>.addItalicsRule(): Syntakts.Builder<C> = addRule(ITALICS_REGEX, name = "Italics") {
    val asteriskMatch = it.groups[2]
    val range = if (asteriskMatch != null && asteriskMatch.value.isNotEmpty()) {
        asteriskMatch.range
    } else {
        it.groups[1]!!.range
    }

    styleNode(Style(fontStyle = FontStyle.Italic), range)
}

/**
 * Adds a simple underline rule
 *
 * Example: `__Some Text__`
 *
 * @return [Syntakts.Builder] To allow for builder method chaining
 */
public fun <C> Syntakts.Builder<C>.addUnderlineRule(): Syntakts.Builder<C> = addRule(UNDERLINE_REGEX, name = "Underline") {
    styleNode(Style(textDecoration = TextDecoration.Underline), it.groups[1]!!.range)
}

/**
 * Adds a simple strikethrough rule
 *
 * Example: `~~Some Text~~`
 *
 * @return [Syntakts.Builder] To allow for builder method chaining
 */
public fun <C> Syntakts.Builder<C>.addStrikethroughRule(): Syntakts.Builder<C> = addRule(STRIKETHROUGH_REGEX, name = "Strikethrough") {
    styleNode(Style(textDecoration = TextDecoration.LineThrough), it.groups[1]!!.range)
}

/**
 * Adds a simple header rule
 *
 * Example: `# Some Text`
 *
 * @return [Syntakts.Builder] To allow for builder method chaining
 */
public fun <C> Syntakts.Builder<C>.addHeaderRule(): Syntakts.Builder<C> = addRule(HEADER_REGEX, name = "Header") { result ->
    val hashCount = result.groups[1]!!.value.length
    val content = result.groups[2]!!
    ParseSpec.createNonterminal(
        object : Node.Parent<C>() {
            override fun render(builder: StyledTextBuilder<*>, context: C) {
                val i = builder.length
                super.render(builder, context)
                builder.addStyle(hashCountToStyle(hashCount), i, builder.length)
            }
        },
        content.range.first,
        content.range.last + 1
    )
}

/**
 * Adds some basic markdown rules
 *
 * **Bold**: `**Some Text**` -> **Some Text**
 *
 * **Italics**: `*Some Text*` or `_Some Text_` -> *Some Text*
 *
 * **Underline**: `__Some Text__`
 *
 * **Strikethrough**: `~~Some Text~~`
 *
 * @see addMarkdownRules
 * @see addExtendedMarkdownRules
 */
public fun <C> Syntakts.Builder<C>.addBasicMarkdownRules(): Syntakts.Builder<C> {
    addStrikethroughRule()
    addUnderlineRule()
    addItalicsRule()
    addBoldRule()
    return this
}

/**
 * Adds more advanced markdown rules
 *
 * **Headers**: `# Header`
 *
 * @see addMarkdownRules
 * @see addBasicMarkdownRules
 */
public fun <C> Syntakts.Builder<C>.addExtendedMarkdownRules(): Syntakts.Builder<C> {
    addHeaderRule()
    return this
}

/**
 * Adds all markdown rules
 *
 * **Bold**: `**Some Text**` -> **Some Text**
 *
 * **Italics**: `*Some Text*` or `_Some Text_` -> *Some Text*
 *
 * **Underline**: `__Some Text__`
 *
 * **Strikethrough**: `~~Some Text~~`
 *
 * **Headers**: `# Header`
 *
 * @see addBasicMarkdownRules
 * @see addExtendedMarkdownRules
 */
public fun <C> Syntakts.Builder<C>.addMarkdownRules(): Syntakts.Builder<C> {
    addExtendedMarkdownRules()
    addBasicMarkdownRules()
    return this
}

/**
 * Instance of [Syntakts] with some basic markdown rules
 *
 * **Bold**: `**Some Text**` -> **Some Text**
 *
 * **Italics**: `*Some Text*` or `_Some Text_` -> *Some Text*
 *
 * **Underline**: `__Some Text__`
 *
 * **Strikethrough**: `~~Some Text~~`
 */
public val BasicMarkdownSyntakts: Syntakts<Unit> = syntakts {
    addBasicMarkdownRules()
}

/**
 * Instance of [Syntakts] with some markdown rules
 *
 * **Bold**: `**Some Text**` -> **Some Text**
 *
 * **Italics**: `*Some Text*` or `_Some Text_` -> *Some Text*
 *
 * **Underline**: `__Some Text__`
 *
 * **Strikethrough**: `~~Some Text~~`
 *
 * **Headers**: `# Header`
 */
public val MarkdownSyntakts: Syntakts<Unit> = syntakts {
    addMarkdownRules()
}