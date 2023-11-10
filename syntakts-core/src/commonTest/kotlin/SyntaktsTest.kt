import org.junit.Test
import xyz.wingio.syntakts.markdown.MarkdownSyntakts
import xyz.wingio.syntakts.node.StyleNode
import xyz.wingio.syntakts.style.Color
import xyz.wingio.syntakts.style.Style
import xyz.wingio.syntakts.syntakts

const val sampleText = "Just **some** *sample* @text"

class SyntaktsTest {

    @Test
    fun `Parse plain text`() {
        val syntakts = syntakts<Unit> {  }
        val parsedNodes = syntakts.parse(sampleText)
        assert(parsedNodes.isNotEmpty())
    }

    @Test
    fun `Parse text with markdown`() {
        val parsedNodes = MarkdownSyntakts.parse(sampleText)
        assert(parsedNodes.isNotEmpty())
        assert(parsedNodes.any { it is StyleNode })
    }

    @Test
    fun `Parse text with custom rules`() {
        val syntakts = syntakts<Unit> {
            rule("@([A-z]+)") { result, _ ->
                append(result.value, Style(color = Color.GRAY))
            }
        }
        val parsedNodes = syntakts.parse(sampleText)
        assert(parsedNodes.isNotEmpty())
    }

}