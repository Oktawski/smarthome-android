package com.example.smarthome.ui.user

import android.widget.EditText

interface BlankFieldsChecker {

    fun showErrorIfTextFieldEmpty(vararg text: EditText): Boolean {
        var isEmpty = false
        for (t in text) {
            if(t.text.isNullOrEmpty()){
                t.error = "Cannot be empty"
                isEmpty = true
            }
        }
        return isEmpty
    }

}