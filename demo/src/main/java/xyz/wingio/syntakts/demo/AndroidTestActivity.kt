package xyz.wingio.syntakts.demo

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.res.ResourcesCompat
import xyz.wingio.syntakts.android.render
import xyz.wingio.syntakts.android.style.DefaultFontResolver

class AndroidTestActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_test)

        DefaultFontResolver.register(
            "jetbrains mono" to ResourcesCompat.getFont(this, R.font.jetbrains_mono)!!
        )

        val testString = """
            # Header
            **Bold** *Italic* __Underline__ ~~Strikethrough~~ <@1234> :heart:
            `println("hi")`
        """.trimIndent()

        findViewById<TextView>(R.id.test_text).render(
            text = testString,
            syntakts = TestSyntakts,
            context = Context(this),
            enableClickable = true
        )
    }

}