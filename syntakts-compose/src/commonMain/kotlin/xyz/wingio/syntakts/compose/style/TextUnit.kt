package xyz.wingio.syntakts.compose.style

import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import xyz.wingio.syntakts.style.Em
import xyz.wingio.syntakts.style.Sp
import androidx.compose.ui.unit.TextUnit as ComposeTextUnit
import xyz.wingio.syntakts.style.TextUnit

/**
 * Converts a [TextUnit] into its compose representation
 */
public fun TextUnit.toComposeTextUnit(): ComposeTextUnit = when(this) {
    is Sp -> value.sp
    is Em -> value.em
    else -> ComposeTextUnit.Unspecified
}

/**
 * Converts a [ComposeTextUnit] into its Syntakts representation
 */
public fun ComposeTextUnit.toSyntaktsTextUnit(): TextUnit = when(type) {
    TextUnitType.Companion.Sp -> Sp(value)
    TextUnitType.Companion.Em -> Em(value)
    else -> TextUnit.Unspecified
}