package xyz.wingio.syntakts.demo

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import xyz.wingio.syntakts.android.render

class AndroidTestActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_test)

        val testString = """
            # Header
            **Bold** *Italic* __Underline__ ~~Strikethrough~~ <@1234> :heart:
        """.trimIndent()

        findViewById<TextView>(R.id.test_text).render(
            text = testString,
            syntakts = TestSyntakts,
            context = Context(this),
            enableClickable = true
        )
    }

}