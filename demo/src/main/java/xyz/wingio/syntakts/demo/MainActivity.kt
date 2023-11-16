package xyz.wingio.syntakts.demo

import android.graphics.Typeface
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import xyz.wingio.syntakts.demo.ui.theme.SyntaktsTheme

import xyz.wingio.syntakts.compose.material3.clickable.ClickableText
import xyz.wingio.syntakts.compose.rememberRendered
import xyz.wingio.syntakts.compose.rememberSyntakts
import xyz.wingio.syntakts.compose.style.toSyntaktsColor
import xyz.wingio.syntakts.markdown.MarkdownSyntakts
import xyz.wingio.syntakts.markdown.addBasicMarkdownRules
import xyz.wingio.syntakts.markdown.addBoldRule
import xyz.wingio.syntakts.markdown.addHeaderRule
import xyz.wingio.syntakts.markdown.addItalicsRule
import xyz.wingio.syntakts.markdown.addMarkdownRules
import xyz.wingio.syntakts.markdown.addStrikethroughRule
import xyz.wingio.syntakts.markdown.addUnderlineRule
import xyz.wingio.syntakts.node.clickableNode
import xyz.wingio.syntakts.node.styleNode
import xyz.wingio.syntakts.style.Style
import java.io.File
import xyz.wingio.syntakts.style.Color as SyntaktsColor

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SyntaktsTheme {
                val syntakts = rememberSyntakts<Context> {
                    debugOptions {
                        enableLogging = true
                    }

                    rule("<@([0-9]+)>") { result, ctx ->
                        val username = ctx.userMap[result.groupValues[1]] ?: "Unknown"
                        appendClickable(
                            "@$username",
                            Style(
                                color = SyntaktsColor.YELLOW,
                                background = SyntaktsColor.YELLOW withOpacity 0.2f
                            ),
                            onLongClick = {
                                Toast.makeText(this@MainActivity, "Mention long clicked", Toast.LENGTH_SHORT).show()
                            },
                            onClick = {
                                Toast.makeText(this@MainActivity, "Mention clicked", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }

                    addMarkdownRules()
                }

                var text by remember { mutableStateOf("Test") }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                    ) {
                        ClickableText(
                            text = syntakts.rememberRendered(text, Context(MaterialTheme.colorScheme.primary)),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                        )

                        OutlinedTextField(
                            value = text,
                            onValueChange = {
                                text = it
                            },
                            modifier = Modifier.weight(1f).fillMaxWidth()
                        )
                    }
                }
            }
        }
    }

}

data class Context(
    val primaryColor: Color,
    val userMap: Map<String, String> = mapOf("1234" to "Wing")
)