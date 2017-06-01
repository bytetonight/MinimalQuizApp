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

    public static List<BaseQuestion> getQuestions() {
        String question;
        String answer;
        List<IntStringPair> answers;

        //Checkboxes
        question = "What's 1+1 ?";
        answers = new ArrayList<>();
        answers.add(new IntStringPair(1, "2"));
        answers.add(new IntStringPair(0, "11"));
        answers.add(new IntStringPair(1, "0010"));
        answers.add(new IntStringPair(0, "eleven"));
        questions.add(new MultipleResponseQnA(question, answers));

        //Free Text
        question = "What is the Answer to the Ultimate Question of Life, The Universe, and Everything ?";
        answer = "42";
        questions.add(new FreeTextResponseQnA(question, answer));

        //RadioButtons
        question = "The company that produces the iPad uses which Logo ?";
        answers = new ArrayList<>();
        answers.add(new IntStringPair(0, "Banana"));
        answers.add(new IntStringPair(0, "Strawberry"));
        answers.add(new IntStringPair(0, "Pine Apple"));
        answers.add(new IntStringPair(1, "Apple"));
        questions.add(new MultipleChoiceQnA(question, answers));

        //RadioButtons
        question = "Is the product of 1 and 1 equal to 2 ?";
        answers = new ArrayList<>();
        answers.add(new IntStringPair(0, "true"));
        answers.add(new IntStringPair(1, "false"));
        questions.add(new DualChoiceResponseQnA(question, answers));
        return questions;
    }
}
