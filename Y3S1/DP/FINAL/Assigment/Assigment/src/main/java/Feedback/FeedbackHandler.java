package Feedback;

import singleton.Logger;

public class FeedbackHandler {

    public static void displayFeedback(String message, boolean isSuccess) {
        String prefix = isSuccess ? "[SUCCESS]" : "[ERROR]";
        System.out.println(prefix + " " + message);
        Logger.getInstance().log(prefix + " " + message);
    }

    public static void displayErrors(FormValidator validator) {
        for (String error : validator.getErrorMessages()) {
            displayFeedback(error, false);
        }
    }
}
