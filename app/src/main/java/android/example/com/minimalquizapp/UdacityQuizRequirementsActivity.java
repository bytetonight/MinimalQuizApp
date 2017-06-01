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

import android.content.Intent;
import android.example.com.minimalquizapp.interfaces.BaseQuestion;
import android.example.com.minimalquizapp.interfaces.ManyOfManyResponse;
import android.example.com.minimalquizapp.interfaces.Communicator;
import android.example.com.minimalquizapp.models.DualChoiceResponseQnA;
import android.example.com.minimalquizapp.models.FreeTextResponseQnA;
import android.example.com.minimalquizapp.models.MultipleChoiceQnA;
import android.example.com.minimalquizapp.models.MultipleResponseQnA;
import android.example.com.minimalquizapp.validation.MultipleResponseValidator;
import android.example.com.minimalquizapp.validation.SingleResponseValidator;
import android.example.com.minimalquizapp.validation.interfaces.HashMapValidator;
import android.example.com.minimalquizapp.validation.interfaces.StringValidator;
import android.example.com.minimalquizapp.views.FragmentCorrectAnswer;
import android.example.com.minimalquizapp.views.FragmentFreeTextResponse;
import android.example.com.minimalquizapp.views.FragmentMultipleChoice;
import android.example.com.minimalquizapp.views.FragmentMultipleResponse;
import android.example.com.minimalquizapp.views.FragmentTrueFalse;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;


public class UdacityQuizRequirementsActivity extends AppCompatActivity implements Communicator {

    public static final String QUESTION_FRAGMENT_TAG = "questionFragmentTag";
    public static final String CORRECT_ANSWER_DIALOG_TAG = "CADTag";
    public static final String KEY_QUESTION_INDEX = "currentQuestionIndex";
    public static final String KEY_PLAYER_SCORE = "playerScore";

    public static final String CLASS_MULTIPLE_CHOICE_CHECKBOX_QUESTION = "MultipleResponseQnA";
    public static final String CLASS_MULTIPLE_CHOICE_RADIO_QUESTION = "MultipleChoiceQnA";
    public static final String CLASS_FREE_TEXT_QUESTION = "FreeTextResponseQnA";
    public static final String CLASS_TRUE_FALSE_RADIO_QUESTION = "DualChoiceResponseQnA";


    private List<BaseQuestion> questions;
    private BaseQuestion currentQnA;
    private int currentQuestionIndex = 0;
    private int playerScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udacity_requirements);
        questions = QuestionHolder.getQuestions(this);

        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt(KEY_QUESTION_INDEX);
            playerScore = savedInstanceState.getInt(KEY_PLAYER_SCORE);
            currentQnA = questions.get(currentQuestionIndex);
        }
        else {
            currentQuestionIndex = 0;
            playerScore = 0;
            switchQuizFragment();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_QUESTION_INDEX, currentQuestionIndex);
        outState.putInt(KEY_PLAYER_SCORE, playerScore);
    }


    public void switchQuizFragment() {
        Fragment questionFragment = null;
        //Check if there are questions at all
        if (questions != null && !questions.isEmpty()) {
            if (currentQuestionIndex <= questions.size()) {
                currentQnA = questions.get(currentQuestionIndex);
                String qName = currentQnA.getClass().getSimpleName();
                switch (qName) {
                    case CLASS_MULTIPLE_CHOICE_CHECKBOX_QUESTION:
                        questionFragment = FragmentMultipleResponse.newInstance((MultipleResponseQnA) currentQnA);
                        break;

                    case CLASS_MULTIPLE_CHOICE_RADIO_QUESTION:
                        questionFragment = FragmentMultipleChoice.newInstance((MultipleChoiceQnA) currentQnA);
                        break;

                    case CLASS_FREE_TEXT_QUESTION:
                        questionFragment = FragmentFreeTextResponse.newInstance((FreeTextResponseQnA) currentQnA);
                        break;

                    case CLASS_TRUE_FALSE_RADIO_QUESTION:
                        questionFragment = FragmentTrueFalse.newInstance((DualChoiceResponseQnA) currentQnA);
                        break;
                }

                if (questionFragment != null) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                    ft.replace(R.id.fragment_container, questionFragment, QUESTION_FRAGMENT_TAG);
                    ft.commit();
                }
            }
        }
    }

    /**
     * Called in {@link FragmentMultipleResponse}
     *
     * @param answersMap HashMap of answers submitted by the Quiz Player
     */
    public void onFragmentSubmit(HashMap<String, String> answersMap) {

        ++currentQuestionIndex;

        HashMapValidator validator = new MultipleResponseValidator(this, (ManyOfManyResponse) currentQnA);
        boolean isCorrect = validator.validate(answersMap);

        if (isCorrect) {
            ++playerScore;
            if (currentQuestionIndex <= questions.size() - 1) {
                switchQuizFragment();
            } else {
                runResultsActivity();
            }
        } else {
            displayCorrectAnswerDialog(validator.getMessage());
        }

        Toast.makeText(UdacityQuizRequirementsActivity.this,
                getString(R.string.toast_score) +
                        playerScore + getString(R.string.toast_score_delimiter) +
                        questions.size(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Called in {@link FragmentFreeTextResponse}
     *
     * @param submitted String answer submitted by the Quiz Player
     */
    public void onFragmentSubmit(String submitted) {

        ++currentQuestionIndex;

        String qName = currentQnA.getClass().getSimpleName();
        StringValidator validator = null;
        boolean isCorrect = false;
        switch (qName) {

            case CLASS_MULTIPLE_CHOICE_RADIO_QUESTION:
                validator = new SingleResponseValidator(this, currentQnA);
                isCorrect = validator.validate(submitted);
                break;

            case CLASS_FREE_TEXT_QUESTION:
                validator = new SingleResponseValidator(this, currentQnA);
                isCorrect = validator.validate(submitted);
                break;

            case CLASS_TRUE_FALSE_RADIO_QUESTION:
                validator = new SingleResponseValidator(this, currentQnA);
                isCorrect = validator.validate(submitted);
                break;
        }


        if (isCorrect) {
            ++playerScore;
            if (currentQuestionIndex <= questions.size() - 1) {
                switchQuizFragment();
            } else {
                runResultsActivity();
            }
        } else {
            displayCorrectAnswerDialog(validator.getMessage());
        }

        Toast.makeText(UdacityQuizRequirementsActivity.this,
                getString(R.string.toast_score) +
                        playerScore + getString(R.string.toast_score_delimiter) +
                        questions.size(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Used to determine whether the CorrectAnswerDialogFragment was confirmed by clicking the Button
     *
     * @param msg
     */
    @Override
    public void onDialogMessage(String msg) {
        if (msg.equals(getString(R.string.ok))) {
            if (currentQuestionIndex <= questions.size() - 1)
                switchQuizFragment();
            else {
                runResultsActivity();
            }
        }
    }

    public void displayCorrectAnswerDialog(String msg) {
        DialogFragment fca = FragmentCorrectAnswer.newInstance(msg);
        fca.setCancelable(false);
        fca.show(getSupportFragmentManager(), CORRECT_ANSWER_DIALOG_TAG);
    }

    private void runResultsActivity() {
        Intent resultsIntent = new Intent(UdacityQuizRequirementsActivity.this, ResultsActivity.class);
        Bundle passData = new Bundle();
        passData.putInt("score", playerScore);
        passData.putInt("questions", questions.size());
        resultsIntent.putExtras(passData);
        startActivity(resultsIntent);
        finish();//Once we leave the Quiz, we don't want to come back by pressing back
    }
}
