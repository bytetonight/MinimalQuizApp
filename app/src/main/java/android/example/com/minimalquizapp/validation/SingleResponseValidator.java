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
import android.example.com.minimalquizapp.interfaces.BaseQuestion;
import android.example.com.minimalquizapp.interfaces.OneOfManyResponse;
import android.example.com.minimalquizapp.interfaces.OneResponse;
import android.example.com.minimalquizapp.validation.interfaces.StringValidator;


/**
 * Created by ByteTonight on 30.05.2017.
 */

public class SingleResponseValidator implements StringValidator {

    private Context context;
    private BaseQuestion currentQnA;

    public SingleResponseValidator(Context context, BaseQuestion currentQnA) {
        this.currentQnA = currentQnA;
        this.context = context;
    }

    @Override
    public boolean validate(String userAnswer) {
        if (currentQnA instanceof OneOfManyResponse)
            return userAnswer.equalsIgnoreCase( ((OneOfManyResponse) currentQnA).getAnswer() ) ;
        return userAnswer.equalsIgnoreCase( ((OneResponse) currentQnA).getAnswer() ) ;
    }

    @Override
    public String getMessage() {
        if (currentQnA instanceof OneOfManyResponse)
            return ((OneOfManyResponse) currentQnA).getAnswer();
        return ((OneResponse) currentQnA).getAnswer();
    }
}
