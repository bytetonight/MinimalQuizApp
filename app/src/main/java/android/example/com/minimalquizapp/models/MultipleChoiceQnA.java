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

package android.example.com.minimalquizapp.models;


import android.example.com.minimalquizapp.IntStringPair;
import android.example.com.minimalquizapp.interfaces.OneOfManyResponse;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ByteTonight on 22.05.2017.
 */

public class MultipleChoiceQnA implements OneOfManyResponse, Parcelable {
    public static final Creator<MultipleChoiceQnA> CREATOR =
            new Creator<MultipleChoiceQnA>() {

                @Override
                public MultipleChoiceQnA createFromParcel(Parcel source) {
                    return new MultipleChoiceQnA(source);
                }

                @Override
                public MultipleChoiceQnA[] newArray(int size) {
                    return new MultipleChoiceQnA[size];
                }
            };
    private String question;
    private String answer;
    private List<String> incorrectAnswers = new ArrayList<>();

    public MultipleChoiceQnA(Parcel parcel) {
        question = parcel.readString();
        answer = parcel.readString();
        parcel.readStringList(incorrectAnswers);
    }

    public MultipleChoiceQnA(String question, List<IntStringPair> answers) {
        this.question = question;
        for (IntStringPair item : answers) {
            if (item.getKey() == 1)
                answer = item.getValue();
            else
                incorrectAnswers.add(item.getValue());
        }
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     * @see #CONTENTS_FILE_DESCRIPTOR
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeList(incorrectAnswers);
    }
}
