package xyz.wingio.syntakts

import androidx.compose.runtime.Stable
import xyz.wingio.syntakts.node.Node
import xyz.wingio.syntakts.node.node
import xyz.wingio.syntakts.parser.ParseRule
import xyz.wingio.syntakts.parser.ParseSpec
import xyz.wingio.syntakts.parser.Rule
import xyz.wingio.syntakts.parser.addTextRule
import xyz.wingio.syntakts.style.StyledTextBuilder
import xyz.wingio.syntakts.util.Logger
import xyz.wingio.syntakts.util.LoggerImpl
import xyz.wingio.syntakts.util.SynchronizedCache
import xyz.wingio.syntakts.util.Stack
import xyz.wingio.syntakts.util.firstMapOrNull

/**
 * The base class used to parse any input string into AST [Node]s from as set of [Rule]s
 *
 * @param C The context passed to a [Node] when its getting rendered, can be any class
 */
@Stable
public class Syntakts<C> internal constructor(
    private val rules: List<Rule<C>>,
    @Deprecated("Use debugOptions instead")
    private val debug: Boolean = false,
    private val debugOptions: DebugOptions
){

    /**
     * Configures options for debugging
     *
     * @param enableLogging Whether or not logging is enabled
     * @param logger Used to log rule matches and parsing time
     * @param storeMetadata Whether to store some metadata in each node
     */
    @Stable
    public data class DebugOptions(
        var enableLogging: Boolean = false,
        var logger: Logger = LoggerImpl(tag = "Syntakts"),
        var storeMetadata: Boolean = false
    )

    /**
     * The class used to build an instance of [Syntakts], used within the [syntakts] DSL
     *
     * @param C The context passed to a [Node] when its getting rendered, can be any class
     */
    @Stable
    public class Builder<C> {

        /**
         * The parsing rules that will be passed into a [Syntakts] when built
         */
        private val rules = mutableListOf<Rule<C>>()

        /**
         * When enabled will log any rule misses and matches
         */
        @Deprecated("Use debugOptions instead")
        public var debug: Boolean = false

        /**
         * Options for debugging
         */
        public var debugOptions: DebugOptions = DebugOptions()

        /**
         * Create an instance of [Syntakts] with the currently defined rules
         */
        public fun build(): Syntakts<C> {
            return Syntakts(rules, debug, debugOptions)
        }

        /**
         * Adds multiple rules
         *
         * @param rules The rules to add
         */
        public fun addRules(rules: Collection<Rule<C>>) {
            this.rules.addAll(rules)
        }

        /**
         * Adds multiple rules
         *
         * @param rules The rules to add
         */
        public fun addRules(vararg rules: Rule<C>) {
            this.rules.addAll(rules)
        }

        /**
         * Manually add an instance of a [Rule]
         *
         * @param rule The rule to add
         * @return [Builder] Used to chain methods together
         * @return [Builder] To allow for builder method chaining
         */
        public fun addRule(rule: Rule<C>): Builder<C> {
            rules.add(rule)
            return this
        }

        /**
         * Add a rule based on the specified [regex]
         *
         * ```
         * addRule("@([A-z]+)") { result ->
         *      node {
         *          append(result.value, style = Style(color = Color.CYAN))
         *      }
         * }
         * ```
         *
         * @param regex The regex pattern used to define this rule
         * @param name (optional) The name of this rule
         * @param parse The callback to run when the pattern is found
         * @return [Builder] To allow for builder method chaining
         */
        public fun addRule(regex: String, name: String = "Unnamed Rule", parse: ParseRule<C>): Builder<C> = addRule(regex.toRegex(), name, parse)

        /**
         * Add a rule based on the specified [regex]
         *
         * ```
         * addRule("@([A-z]+)".toRegex()) { result ->
         *      node {
         *          append(result.value, style = Style(color = Color.CYAN))
         *      }
         * }
         * ```
         *
         * @param regex The regex pattern used to define this rule
         * @param name (optional) The name of this rule
         * @param parse The callback to run when the pattern is found
         * @return [Builder] To allow for builder method chaining
         */
        public fun addRule(regex: Regex, name: String = "Unnamed Rule", parse: ParseRule<C>): Builder<C> {
            rules.add(Rule(regex, name, parse))
            return this
        }

        /**
         * Simplest way to add a rule based on the specified [regex], doesn't render any children or use any predefined nodes
         *
         * ```
         * rule("@([A-z]+)") { result, context ->
         *      append(result.value, style = Style(color = Color.CYAN))
         * }
         * ```
         *
         * @param regex The regex pattern used to define this rule
         * @param name (optional) The name of this rule
         * @param render How to render the resulting node, see: [StyledTextBuilder]
         * @return [Builder] To allow for builder method chaining
         */
        public fun rule(regex: String, name: String = "Unnamed Rule", render: StyledTextBuilder<*>.(result: MatchResult, context: C) -> Unit): Builder<C> = rule(regex.toRegex(), name, render)

        /**
         * Simplest way to add a rule based on the specified [regex], doesn't render any children or use any predefined nodes
         *
         * ```
         * rule("@([A-z]+)".toRegex()) { result, context ->
         *      append(result.value, style = Style(color = Color.CYAN))
         * }
         * ```
         *
         * @param regex The regex pattern used to define this rule
         * @param name (optional) The name of this rule
         * @param render How to render the resulting node, see: [StyledTextBuilder]
         * @return [Builder] To allow for builder method chaining
         */
        public fun rule(regex: Regex, name: String = "Unnamed Rule", render: StyledTextBuilder<*>.(result: MatchResult, context: C) -> Unit): Builder<C> {
            addRule(regex, name) { result -> node { context: C -> render(result, context) } }
            return this
        }

        /**
         * When enabled will log any rule misses and matches
         *
         * @param debug Whether debug mode is enabled
         * @return [Builder] To allow for builder method chaining
         */
        @Deprecated("Use debugOptions instead")
        public fun debug(debug: Boolean): Builder<C> {
            debugOptions(enableLogging = debug)
            return this
        }

        /**
         * Configures options for debugging
         *
         * @param options Lambda for configuring options
         */
        public fun debugOptions(options: DebugOptions.() -> Unit): Builder<C> {
            debugOptions.apply(options)
            return this
        }

        /**
         * Configures options for debugging
         *
         * @param enableLogging Whether or not logging is enabled
         * @param logger Used to log rule matches and parsing time
         * @param storeMetadata Whether to store some metadata in each node
         */
        // Make sure parameters here match the DebugOptions data class
        public fun debugOptions(
            enableLogging: Boolean = false,
            logger: Logger = LoggerImpl(tag = "Syntakts"),
            storeMetadata: Boolean = false
        ): Builder<C> {
            debugOptions = DebugOptions(
                enableLogging = enableLogging,
                logger = logger,
                storeMetadata = storeMetadata
            )
            return this
        }

        /**
         * Set options for debugging
         *
         * @param options Options for debugging
         */
        public fun debugOptions(options: DebugOptions): Builder<C> {
            debugOptions = options
            return this
        }

    }

    /**
     * Log a message to stdout with a defined prefix
     */
    private fun log(message: String) {
        if(debugOptions.enableLogging) debugOptions.logger.debug(message)
    }

    private val cache: SynchronizedCache<String, MatchResult> = SynchronizedCache()

    /**
     * Parse an input using the specified [rules]
     *
     * @param text The text to parse
     * @return A list of [Node]s, used to render the final text
     * @throws ParseException when no matching rules could be found or when a rule fails to match
     */
    public fun parse(
        text: CharSequence
    ): List<Node<C>> {
        val start = System.currentTimeMillis()
        val remainingParses = Stack<ParseSpec<C>>()
        val rootNode = Node<C>()

        var lastCapture: String? = null

        if(text.isNotEmpty()) {
            remainingParses.add(ParseSpec(rootNode, 0, text.length))
        }

        while (!remainingParses.isEmpty()) {
                val builder = remainingParses.pop()

                if (builder.startIndex >= builder.endIndex) {
                    break
                }

                val inspectionSource = text.subSequence(builder.startIndex, builder.endIndex)
                val offset = builder.startIndex

                val (rule, matchResult) =
                    rules.firstMapOrNull { rule ->
                        val key = "${rule.regex}-$inspectionSource-$lastCapture"

                        val matchResult = if(cache.hasKey(key))
                            cache[key]
                        else
                            rule.match(inspectionSource, lastCapture).apply {
                                if(cache.size > 10_000) cache.removeFirst()
                                cache[key] = this
                            }

                        if (matchResult == null) {
                            log("MISSED: ${rule._regex.pattern} in $inspectionSource")
                            null
                        } else {
                            log("MATCHED: ${rule._regex.pattern} in $inspectionSource")
                            rule to matchResult
                        }
                    }
                        ?: throw ParseException("failed to find rule to match source", text)

                val matcherSourceEnd = matchResult.range.last + offset + 1
                val newBuilder = rule.parse(matchResult)

                if(debugOptions.storeMetadata) {
                    newBuilder.root.setMetadata(rule.name, rule.regex, matchResult)
                }

                val parent = builder.root
                parent.addChild(newBuilder.root)

                // In case the last match didn't consume the rest of the source for this subtree,
                // make sure the rest of the source is consumed.
                if (matcherSourceEnd != builder.endIndex) {
                    remainingParses.push(ParseSpec.createNonterminal(parent, matcherSourceEnd, builder.endIndex))
                }

                // We want to speak in terms of indices within the source string,
                // but the Rules only see the matchers in the context of the substring
                // being examined. Adding this offset addresses that issue.
                if (!newBuilder.isTerminal) {
                    newBuilder.applyOffset(offset)
                    remainingParses.push(newBuilder)
                }

                try {
                    lastCapture = matchResult.groups[0]!!.value
                } catch (throwable: Throwable) {
                    throw ParseException(message = "matcher found no matches", source = text, cause = throwable)
                }
            }

        val ast = rootNode.children?.toMutableList()
        log("Finished in ${System.currentTimeMillis() - start}ms")
        return ast ?: arrayListOf()
    }

}

/**
 * DSL for [Syntakts.Builder], the preferred way to set up a [Syntakts] instance
 *
 * Simple example:
 * ```
 * val syntakts = syntakts {
 *      rule("@([A-z0-9_]+)") { result, context ->
 *          append(result.value)
 *      }
 * }
 * ```
 *
 * @param builder A lambda given an instance of [Syntakts.Builder], this is where you declare rules
 * @return [Syntakts]
 */
public fun <C> syntakts(builder: Syntakts.Builder<C>.() -> Unit): Syntakts<C> {
    val _builder = Syntakts.Builder<C>().also(builder)
    _builder.addTextRule() // Add a plain text rule to match anything not caught by another rule
    return _builder.build()
}