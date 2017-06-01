package android.example.com.minimalquizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.R.attr.onClick;

public class StartActivity extends AppCompatActivity {

    private Button startQuiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startQuiz = (Button) findViewById(R.id.btnStartQuiz);
        startQuiz.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnStartQuiz) {
                startActivity(new Intent(StartActivity.this, UdacityQuizRequirementsActivity.class));
            }
        }
    };
}
