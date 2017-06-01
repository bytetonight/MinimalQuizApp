/*
 * Open Trivia QuizApp is a Udacity EU-Scholarship Project
 * written by Thorsten Itter, Copyright (c) 2017.
 * This Software may be used solely for non-profit educational purposes
 * unless specified otherwise by the original author Thorsten Itter
 * Questions and answers provided by Open Trivia Database
 * through a free for commercial use API maintained by PIXELTAIL GAME
 * This source code including this header may not be modified
 *
 */

package android.example.com.minimalquizapp.validation;

import android.content.Context;
import android.example.com.minimalquizapp.R;
import android.example.com.minimalquizapp.interfaces.ManyOfManyResponse;
import android.example.com.minimalquizapp.validation.interfaces.HashMapValidator;


import java.util.HashMap;

/**
 * Created by ByteTonight on 30.05.2017.
 */

public class MultipleResponseValidator implements HashMapValidator {

    private Context context;
    private ManyOfManyResponse currentQnA;
    private StringBuilder message;

    public MultipleResponseValidator(Context context, ManyOfManyResponse currentQnA) {
        this.currentQnA = currentQnA;
        this.context = context;
    }

    @Override
    public boolean validate(HashMap<String, String> answersMap) {
        boolean hasError = false;
        message = new StringBuilder();
        String and = "";
        String aaand = context.getString(R.string.and);
        int hits = 0;
        for (String correctAnswer : currentQnA.getCorrectAnswers()) {
            if (!answersMap.containsValue(correctAnswer)) {
                hasError = true;
                message.append(and);
                message.append(correctAnswer);
                message.append("\n");
                and = aaand;
            } else {
                ++hits;
            }
        }
        if (hasError && hits > 0)
            message.append(context.getString(R.string.additionally));
        return !hasError;
    }

    @Override
    public String getMessage() {
        return message.toString();
    }
}
