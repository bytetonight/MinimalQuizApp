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
import android.example.com.minimalquizapp.UdacityQuizRequirementsActivity;
import android.example.com.minimalquizapp.interfaces.Questionable;
import android.example.com.minimalquizapp.itternet.utils.Utils;
import android.example.com.minimalquizapp.models.DualChoiceResponseQnA;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTrueFalse extends Fragment implements View.OnClickListener, Questionable {

    private View rootView;
    private int checkedRadioID = -1;
    private String realIdString;
    private String question;
    private ArrayList<String> choices;
    private Button submitButton;
    private DualChoiceResponseQnA qNa;

    public static FragmentTrueFalse newInstance(DualChoiceResponseQnA questionAndAnswers) {
        FragmentTrueFalse ftf = new FragmentTrueFalse();
        Bundle args = new Bundle();
        args.putParcelable(KEY_PAYLOAD, questionAndAnswers);
        ftf.setArguments(args);
        return ftf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            realIdString = savedInstanceState.getString(KEY_REAL_ID_STRING, null);
        }
        // Get back arguments
        Bundle b = this.getArguments();
        if (b != null) {
            qNa = b.getParcelable(KEY_PAYLOAD);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_REAL_ID_STRING, realIdString);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v("Fragment", "onCreateView");
        inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.fragment_fragment_true_false, container, false);
        submitButton = (Button) rootView.findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(this);
        TextView questionBox = (TextView) rootView.findViewById(R.id.questionBox);
        questionBox.setText(Utils.fromHtml(qNa.getQuestion()));

        String packageName = getActivity().getPackageName();

        List<String> tempList = new ArrayList<>();
        tempList.add(qNa.getAnswer());
        tempList.addAll(qNa.getIncorrectAnswers());

        int counter = 1;
        for (String item : tempList) {
            RadioButton rb = (RadioButton) rootView.findViewById(getResources()
                    .getIdentifier("radioBtn" + counter, "id", packageName));

            if (realIdString != null)
                setRadioSelectionByRealID(realIdString);

            rb.setText(Utils.fromHtml(item));
            rb.setOnClickListener(this);
            ++counter;
        }

        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view instanceof RadioButton) {
            if (checkedRadioID != -1 && checkedRadioID != view.getId()) {
                RadioButton rb = (RadioButton) rootView.findViewById(checkedRadioID);
                if (rb != null)
                    rb.setChecked(false);
            }
            checkedRadioID = view.getId();
            realIdString = getResources().getResourceName(checkedRadioID);
            return;
        }

        //A click on the Non-RadioButton submitButton lead us here
        //Check if a RadioButton was previously selected
        if (checkedRadioID != -1) {
            //Disable button after a successful submission to prevent multiple
            // submissions from click-happy-users
            submitButton.setEnabled(false);
            String radioValue = getCheckedText();
            //Call onFragmentSubmit method in MainActivity
            ((UdacityQuizRequirementsActivity) getActivity()).onFragmentSubmit(radioValue);
        } else {
            Toast.makeText(getActivity(), getResources().getText(R.string.no_selection).toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setRadioSelectionByRealID(String idString) {
        RadioButton rb = (RadioButton) rootView.findViewById(getResources()
                .getIdentifier(idString, "id", getActivity().getPackageName()));
        rb.setSelected(true);
        checkedRadioID = rb.getId();
    }

    private String getCheckedText() {
        RadioButton rb = (RadioButton) rootView.findViewById(checkedRadioID);
        if (rb != null)
            return rb.getText().toString();
        return "";
    }

}
