package xyz.wingio.syntakts.style

import androidx.compose.runtime.Stable

/**
 * Used to build styled text across various UI frameworks
 *
 * @param S The type that this builder uses when building, usually framework dependant
 */
@Stable
public interface StyledTextBuilder<S> {

    /**
     * Length of the text
     */
    public val length: Int

    /**
     * Append some [text] with an optional [style]
     *
     * @param text The text to append
     * @param style Information used to style the [text]
     * @return [StyledTextBuilder] To allow for builder method chaining
     */
    public fun append(text: CharSequence, style: Style? = null): StyledTextBuilder<S>

    /**
     * Append some [text] with a DSL for styling
     *
     * @param text The text to append
     * @param style Lambda used to style the [text]
     * @return [StyledTextBuilder] To allow for builder method chaining
     */
    public fun append(text: CharSequence, style: Style.() -> Unit): StyledTextBuilder<S>

    /**
     * Appends some [text] that can be clicked
     *
     * @param text The text to append
     * @param style How to style the [text]
     * @param onLongClick Callback for when the child nodes are long clicked
     * @param onClick What to do when the [text] is clicked
     */
    public fun appendClickable(text: CharSequence, style: Style? = null, onLongClick: (() -> Unit)? = null, onClick: () -> Unit): StyledTextBuilder<S>

    /**
     * Appends some [text] with an annotation
     *
     * @param text The text to append
     * @param tag Used to distinguish annotations
     * @param annotation The annotation to attach
     */
    public fun appendAnnotated(
        text: CharSequence,
        tag: String,
        annotation: String
    ): StyledTextBuilder<S>

    /**
     * Will call [onClick] when anything within the given [range] is clicked
     *
     * @param range (inclusive start, exclusive end) Any clicked here will call [onClick]
     * @param onLongClick Callback for when the child nodes are long clicked
     * @param onClick Callback invoked when the text is clicked
     * @return [StyledTextBuilder] To allow for builder method chaining
     */
    public fun addClickable(range: IntRange, onLongClick: (() -> Unit)? = null, onClick: () -> Unit): StyledTextBuilder<S> = addClickable(range.first, range.last + 1, onLongClick, onClick)

    /**
     * Will call [onClick] when anything between the [startIndex] and [endIndex] is clicked
     *
     * @param startIndex (inclusive) Start of the clickable area
     * @param endIndex (exclusive) End of the clickable area
     * @param onLongClick Callback for when the child nodes are long clicked
     * @param onClick Callback invoked when the text is clicked
     * @return [StyledTextBuilder] To allow for builder method chaining
     */
    public fun addClickable(startIndex: Int, endIndex: Int, onLongClick: (() -> Unit)? = null, onClick: () -> Unit): StyledTextBuilder<S>

    /**
     * Apply a [Style] to a given [range]
     *
     * @param style The style to apply
     * @param range (inclusive start, exclusive end) Where the style should apply
     * @return [StyledTextBuilder] To allow for builder method chaining
     */
    public fun addStyle(style: Style, range: IntRange): StyledTextBuilder<S> = addStyle(style, range.first, range.last + 1)

    /**
     * Apply a [Style] from [start][startIndex] to [end][endIndex]
     *
     * @param style The style to apply
     * @param startIndex (inclusive) Where to start applying the style
     * @param endIndex (exclusive) Where to finish applying the style
     * @return [StyledTextBuilder] To allow for builder method chaining
     */
    public fun addStyle(style: Style, startIndex: Int, endIndex: Int): StyledTextBuilder<S>

    /**
     * Apply a [Style] to a given [range]
     *
     * @param range (inclusive start, exclusive end) Where the style should apply
     * @param style The style to apply
     * @return [StyledTextBuilder] To allow for builder method chaining
     */
    public fun addStyle(range: IntRange, style: Style.() -> Unit): StyledTextBuilder<S> = addStyle(range.first, range.last + 1, style)

    /**
     * Apply a [Style] from [start][startIndex] to [end][endIndex]
     *
     * @param startIndex (inclusive) Where to start applying the style
     * @param endIndex (exclusive) Where to finish applying the style
     * @param style The style to apply
     * @return [StyledTextBuilder] To allow for builder method chaining
     */
    public fun addStyle(startIndex: Int, endIndex: Int, style: Style.() -> Unit): StyledTextBuilder<S>

    /**
     * Apply an annotation to the text, this does not usually effect the appearance of the text
     *
     * @param tag Used to distinguish annotations
     * @param annotation Annotation to attach
     * @param range (inclusive start, exclusive end) Where the annotation should apply
     */
    public fun addAnnotation(
        tag: String,
        annotation: String,
        range: IntRange
    ): StyledTextBuilder<S> = addAnnotation(tag, annotation, range.first, range.last + 1)

    /**
     * Apply an annotation to the text, this does not usually effect the appearance of the text
     *
     * @param tag Used to distinguish annotations
     * @param annotation Annotation to attach
     * @param startIndex (inclusive) Where to start applying the annotation
     * @param endIndex (exclusive) Where to finish applying the annotation
     */
    public fun addAnnotation(
        tag: String,
        annotation: String,
        startIndex: Int,
        endIndex: Int
    ): StyledTextBuilder<S>

    /**
     * Clears all text and styles
     */
    public fun clear()

    /**
     * Builds the framework specific representation of rich text
     */
    public fun build(): S

}