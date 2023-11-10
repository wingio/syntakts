package xyz.wingio.syntakts.compose.style

import androidx.compose.ui.text.font.FontWeight as ComposeFontWeight
import xyz.wingio.syntakts.style.FontWeight

/**
 * Converts a [FontWeight] into its compose representation
 */
public fun FontWeight.toComposeFontWeight(): ComposeFontWeight = ComposeFontWeight(weight)

/**
 * Converts a [ComposeFontWeight] into its Syntakts representation
 */
public fun ComposeFontWeight.toSyntaktsFontWeight(): FontWeight = FontWeight(weight)