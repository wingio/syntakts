package xyz.wingio.syntakts.android.spans

/**
 * Represents an annotation applied to some text
 *
 * @param tag Used to distinguish annotations
 * @param annotation Annotation to attach
 */
public class AnnotationSpan(
    public val tag: String,
    public val annotation: String
)