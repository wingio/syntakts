package xyz.wingio.syntakts.demo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import xyz.wingio.syntakts.demo.ui.theme.SyntaktsTheme

import xyz.wingio.syntakts.compose.material3.clickable.ClickableText
import xyz.wingio.syntakts.compose.rememberRendered
import xyz.wingio.syntakts.compose.style.DefaultFontResolver

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val atIntent = Intent(this, AndroidTestActivity::class.java)

        DefaultFontResolver.register(
            "jetbrains mono" to FontFamily(Font(R.font.jetbrains_mono))
        )

        setContent {
            SyntaktsTheme {
                var text by remember { mutableStateOf("**bold** *italic* __underline__ ~~strikethrough~~ `code`") }

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
                            text = TestSyntakts.rememberRendered(text, Context(LocalContext.current)),
                            inlineContent = mapOf(
                                "heart" to InlineTextContent(
                                    Placeholder(LocalTextStyle.current.fontSize, LocalTextStyle.current.fontSize, PlaceholderVerticalAlign.Center)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Favorite,
                                        contentDescription = it
                                    )
                                }
                            ),
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
                        Button(
                            onClick = { startActivity(atIntent) }
                        ) {
                            Text("Launch android test")
                        }
                    }
                }
            }
        }
    }

}

