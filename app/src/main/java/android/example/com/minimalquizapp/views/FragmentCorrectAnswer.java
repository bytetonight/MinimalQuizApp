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

package android.example.com.minimalquizapp.views;


import android.example.com.minimalquizapp.R;
import android.example.com.minimalquizapp.interfaces.Communicator;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;




public class FragmentCorrectAnswer extends DialogFragment implements View.OnClickListener {

    private static final String CORRECT_ANSWER = "param1";

    Communicator communicator;
    Button okButton;
    private String correctAnswer;


    public static FragmentCorrectAnswer newInstance(String param1) {
        FragmentCorrectAnswer fragment = new FragmentCorrectAnswer();
        Bundle args = new Bundle();
        args.putString(CORRECT_ANSWER, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        communicator = (Communicator) getActivity();
        if (getArguments() != null) {
            correctAnswer = getArguments().getString(CORRECT_ANSWER);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View thisView = inflater.inflate(R.layout.fragment_fragment_correct_answer, container, false);

        okButton = (Button) thisView.findViewById(R.id.btnConfirmCorrectAnswer);
        TextView tvCorrectAnswer = (TextView) thisView.findViewById(R.id.tvDialogCorrectAnswer);
        tvCorrectAnswer.setText(correctAnswer);
        setCancelable(false);

        okButton.setOnClickListener(this);
        return thisView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnConfirmCorrectAnswer) {
            //onDialogMessage will fire in Activity that implements Communicator
            //upon which the next question is displayed
            communicator.onDialogMessage("OK");
            dismiss();
        }
    }
}

