package com.example.dialer.utils;

import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;

public class PhoneNumberHelper {
    private static final String TAG = "PhoneNumberUtil";

    /**
     * Determines if the specified number is actually a URI (i.e. a SIP address) rather than a regular
     * PSTN phone number, based on whether or not the number contains an "@" character.
     *
     * @param number Phone number
     * @return true if number contains @
     *     <p>TODO: Remove if PhoneNumberUtils.isUriNumber(String number) is made public.
     */
    public static boolean isUriNumber(String number) {
        // Note we allow either "@" or "%40" to indicate a URI, in case
        // the passed-in string is URI-escaped.  (Neither "@" nor "%40"
        // will ever be found in a legal PSTN number.)
        return number != null && (number.contains("@") || number.contains("%40"));
    }

    /** Returns true if the input phone number contains special characters. */
    public static boolean numberHasSpecialChars(String number) {
        return !TextUtils.isEmpty(number) && number.contains("#");
    }

    /** Returns true if the raw numbers of the two input phone numbers are the same. */
    public static boolean sameRawNumbers(String number1, String number2) {
        String rawNumber1 =
                PhoneNumberUtils.stripSeparators(PhoneNumberUtils.convertKeypadLettersToDigits(number1));
        String rawNumber2 =
                PhoneNumberUtils.stripSeparators(PhoneNumberUtils.convertKeypadLettersToDigits(number2));

        return rawNumber1.equals(rawNumber2);
    }
}
