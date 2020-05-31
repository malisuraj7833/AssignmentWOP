package com.example.assignment.ui.main

import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Email validation pattern.
 */
val EMAIL_PATTERN = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)

class EmailAndPasswordValidator {

    companion object {
        fun isValidEmail(emailId : String?) : Boolean {
            return EMAIL_PATTERN.matcher(emailId.toString()).matches()
        }

        fun isValidPassword(password: String?): Boolean {
            val pattern: Pattern
            val matcher: Matcher
            val PASSWORD_PATTERN =
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$"
            pattern = Pattern.compile(PASSWORD_PATTERN)
            matcher = pattern.matcher(password.toString())
            return matcher.matches()
        }
    }

}