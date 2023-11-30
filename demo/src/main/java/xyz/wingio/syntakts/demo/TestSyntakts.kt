package xyz.wingio.syntakts.demo

import android.content.Context as AndroidContext
import android.widget.Toast
import xyz.wingio.syntakts.compose.style.appendInlineContent
import xyz.wingio.syntakts.markdown.addMarkdownRules
import xyz.wingio.syntakts.style.Color
import xyz.wingio.syntakts.style.Style
import xyz.wingio.syntakts.syntakts

val TestSyntakts = syntakts<Context> {
    rule("<@([0-9]+)>") { result, ctx ->
        val username = ctx.userMap[result.groupValues[1]] ?: "Unknown"
        appendClickable(
            "@$username",
            Style(
                color = Color.MAGENTA,
                background = Color.MAGENTA withOpacity 0.2f
            ),
            onLongClick = {
                Toast.makeText(ctx.androidContext, "Mention long clicked", Toast.LENGTH_SHORT).show()
            },
            onClick = {
                Toast.makeText(ctx.androidContext, "Mention clicked", Toast.LENGTH_SHORT).show()
            }
        )
    }

    rule(":heart:") { result, _ ->
        appendInlineContent(
            id = "heart",
            alternateText = result.value
        )
    }

    addMarkdownRules()
}

data class Context(
    val androidContext: AndroidContext,
    val userMap: Map<String, String> = mapOf("1234" to "Wing")
)