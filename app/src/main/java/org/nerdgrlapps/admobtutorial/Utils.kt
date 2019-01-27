package org.nerdgrlapps.admobtutorial

import com.google.android.gms.ads.AdRequest

class Utils {
    companion object {

        public fun getUserFriendlyError(errorCode: Int): String {
            return when(errorCode) {
                AdRequest.ERROR_CODE_INTERNAL_ERROR -> "internal error"
                AdRequest.ERROR_CODE_INVALID_REQUEST -> "invalid ad request"
                AdRequest.ERROR_CODE_NETWORK_ERROR -> "network error"
                AdRequest.ERROR_CODE_NO_FILL -> "no fill"
                else -> "unknown error"
            }
        }

    }
}