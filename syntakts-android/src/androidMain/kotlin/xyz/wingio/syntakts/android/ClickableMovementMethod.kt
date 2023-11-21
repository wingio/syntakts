package xyz.wingio.syntakts.android

import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.view.MotionEvent
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import xyz.wingio.syntakts.android.spans.ClickableSpan

public class ClickableMovementMethod : LinkMovementMethod() {

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate)
    private var downTime: Long = 0
    private var longPressJob: Job? = null

    override fun onTouchEvent(
        widget: TextView,
        buffer: Spannable,
        event: MotionEvent
    ): Boolean {
        val action = event.action

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
            var x = event.x.toInt()
            var y = event.y.toInt()

            x -= widget.totalPaddingLeft
            y -= widget.totalPaddingTop

            x += widget.scrollX
            y += widget.scrollY

            val layout = widget.layout
            val line = layout.getLineForVertical(y)
            val offset = layout.getOffsetForHorizontal(line, x.toFloat())

            val links = buffer.getSpans(
                /* start = */ offset,
                /* end = */ offset,
                /* type = */ ClickableSpan::class.java
            )

            if (links.isNotEmpty()) {
                val link = links[0]

                if (action == MotionEvent.ACTION_DOWN) {
                    downTime = System.currentTimeMillis()

                    longPressJob = coroutineScope.launch {
                        while (true) {
                            delay(1) // Only check every millisecond
                            val downDuration = System.currentTimeMillis() - downTime
                            if (downDuration >= LONG_TOUCH_DURATION) {
                                link.onLongClick(widget)
                                break // Only fire once
                            }
                        }
                    }
                }

                if (action == MotionEvent.ACTION_UP) {
                    longPressJob?.cancel()
                    longPressJob = null
                    val downDuration = System.currentTimeMillis() - downTime
                    if (downDuration < LONG_TOUCH_DURATION) {
                        link.onClick(widget)
                    }
                }

                return true
            }
        }

        return super.onTouchEvent(widget, buffer, event)
    }

    public companion object {

        public const val LONG_TOUCH_DURATION: Long = 500 // ms

    }

}