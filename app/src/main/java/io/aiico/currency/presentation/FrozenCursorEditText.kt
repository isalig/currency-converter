package io.aiico.currency.presentation

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class FrozenCursorEditText(context: Context?, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        val expectedCursorPosition = text?.length ?: 0
        if (selStart == selEnd && selStart != expectedCursorPosition) {
            setSelection(expectedCursorPosition)
        }
    }
}