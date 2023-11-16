package xyz.wingio.syntakts.parser

import androidx.compose.runtime.Stable
import xyz.wingio.syntakts.Syntakts
import xyz.wingio.syntakts.node.node
import xyz.wingio.syntakts.node.textNode

@Stable
public open class Rule<C>(
    public val regex: Regex,
    public val name: String,
    public val parse: ParseRule<C>
) {

    internal val _regex = "^${regex.pattern}".toRegex()

    public open fun match(inspectionSource: CharSequence, lastCapture: String?): MatchResult? {
        return _regex.find(inspectionSource)
    }

}

/**
 * Uses the result of the match to generate a [ParseSpec], usually done using [node]
 */
@Stable
public fun interface ParseRule<C> {

    /**
     * Uses the result of the match to generate a [ParseSpec], usually done using [node]
     *
     * @param result The result of the regex match
     * @return [ParseSpec] representing the node to be rendered
     */
    public operator fun invoke(result: MatchResult): ParseSpec<C>

}

private val PLAINTEXT_REGEX = """^[\s\S]+?(?=\b|[^0-9A-Za-z\s\u00c0-\uffff]|\n| {2,}\n|\w+:\S|$)""".toRegex()

/**
 * Adds a plain text rule, used as a fallback in case no user defined rules catch anything
 *
 * @return [Syntakts.Builder] To allow for builder method chaining
 */
public fun <C> Syntakts.Builder<C>.addTextRule(): Syntakts.Builder<C> = addRule(PLAINTEXT_REGEX, name = "Plain Text") {
    textNode(it.value)
}