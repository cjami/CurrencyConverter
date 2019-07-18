package che.codes.currencyconverter.core.ui.util

import android.widget.EditText

object EditTextUtils {

    fun setCursorToEnd(editText: EditText) {
        editText.setSelection(editText.text.length)
    }
}