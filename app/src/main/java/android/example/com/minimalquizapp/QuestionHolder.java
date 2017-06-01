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

package android.example.com.minimalquizapp;



import android.content.Context;
import android.content.res.Resources;
import android.example.com.minimalquizapp.interfaces.BaseQuestion;
import android.example.com.minimalquizapp.models.DualChoiceResponseQnA;
import android.example.com.minimalquizapp.models.FreeTextResponseQnA;
import android.example.com.minimalquizapp.models.MultipleChoiceQnA;
import android.example.com.minimalquizapp.models.MultipleResponseQnA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ByteTonight on 22.05.2017.
 */

public class QuestionHolder {
    private static List<BaseQuestion> questions = new ArrayList<>();
    private static Context context;

    public static void setContext(Context c) {
        context = c;
    }
    public static List<BaseQuestion> getQuestions() {
        if (context == null)
            return null;
        String question;
        String answer;
        List<IntStringPair> answers;

        //Checkboxes
        question = context.getString(R.string.q1);
        answers = new ArrayList<>();
        answers.add(new IntStringPair(1, context.getString(R.string.q1o1)));
        answers.add(new IntStringPair(0, context.getString(R.string.q1o2)));
        answers.add(new IntStringPair(1, context.getString(R.string.q1o3)));
        answers.add(new IntStringPair(0, context.getString(R.string.q1o4)));
        questions.add(new MultipleResponseQnA(question, answers));

        //Free Text
        question = context.getString(R.string.q2);
        answer = context.getString(R.string.q2o1);
        questions.add(new FreeTextResponseQnA(question, answer));

        //RadioButtons
        question = context.getString(R.string.q3);
        answers = new ArrayList<>();
        answers.add(new IntStringPair(0, context.getString(R.string.q3o1)));
        answers.add(new IntStringPair(0, context.getString(R.string.q3o2)));
        answers.add(new IntStringPair(0, context.getString(R.string.q3o3)));
        answers.add(new IntStringPair(1, context.getString(R.string.q3o4)));
        questions.add(new MultipleChoiceQnA(question, answers));

        //RadioButtons
        question = context.getString(R.string.q4);
        answers = new ArrayList<>();
        answers.add(new IntStringPair(0, context.getString(R.string.q4o1)));
        answers.add(new IntStringPair(1, context.getString(R.string.q4o2)));
        questions.add(new DualChoiceResponseQnA(question, answers));
        return questions;
    }
}
