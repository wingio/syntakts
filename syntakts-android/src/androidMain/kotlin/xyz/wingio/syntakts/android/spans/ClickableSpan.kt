package xyz.wingio.syntakts.android.spans

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

/**
 * [ClickableSpan] with the added ability to receive long clicks
 *
 * @param onClickListener Called when this span is clicked
 * @param onLongClickListener Called when this span is long clicked
 */
public open class ClickableSpan(
    private val onClickListener: (() -> Unit)?,
    private val onLongClickListener: (() -> Unit)?
) : ClickableSpan() {

    override fun onClick(view: View) {
        onClickListener?.invoke()
    }

    /**
     * Performs the long click action associated with this span
     *
     * @param view A reference to the view that was clicked
     */
    public fun onLongClick(view: View) {
        onLongClickListener?.invoke()
    }

    override fun updateDrawState(ds: TextPaint) {
        // NO-OP
    }

}