package io.aiico.currency.presentation

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class FrozenCursorEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        val expectedCursorPosition = text?.length ?: 0
        if (selStart == selEnd && selStart != expectedCursorPosition) {
            setSelection(expectedCursorPosition)
        }
    }
}
