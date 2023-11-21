package xyz.wingio.syntakts.android.spans

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

public class ClickableSpan(
    private val onClickListener: (() -> Unit)?,
    private val onLongClickListener: (() -> Unit)?
) : ClickableSpan() {

    override fun onClick(view: View) {
        onClickListener?.invoke()
    }

    public fun onLongClick(view: View) {
        onLongClickListener?.invoke()
    }

    override fun updateDrawState(ds: TextPaint) {
        // NO-OP
    }

}