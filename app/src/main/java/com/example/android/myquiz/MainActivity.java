package com.example.android.myquiz;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int questionCounter;
    int score = 0;
    Toast toastMessage;
    int numberOfRadioQuestions;
    String nameOfPlayer;

    // Define which button holds the correct answer




    public void startQuiz(View view){
    //void for saving the users name and bringing user to the first layout with questions

        // Define value which follows the number of the question
        questionCounter = 0;

        // Retrieve name of player
        TextView playerName = (TextView) findViewById(R.id.namePlayer);
        nameOfPlayer = playerName.getText().toString();

        // Switch layout to next screen with first questions
        setContentView(R.layout.activity_questions1);

        //Fill in the first question and answer
        checkNextQuestion();

    }

    public void submitRadioAnswer (View view) {

        //Cancel any ongoing toast messages to prevent queue of multiple toasts
        if (toastMessage!= null) {
            toastMessage.cancel();
        }

        // Retrieve ID-value of checked radiobutton. If ID = -1, then no button is selected and user has to try again
        // If value is not equal to -1 then a button is selected and results are checked.
        RadioGroup answerGroup = (RadioGroup) findViewById(R.id.answerRadio);
        int buttonID = answerGroup.getCheckedRadioButtonId();
        if (buttonID == -1) {

            toastMessage = Toast.makeText(MainActivity.this, "No answer selected", Toast.LENGTH_SHORT);
            toastMessage.show();

        } else

            //Retrieve textvalue of selected Radiobutton and retrieve value withing Strings of correct answer.
            {
            RadioButton selectedButton = (RadioButton) findViewById(buttonID);
            String givenAnswer = selectedButton.getText().toString();
            String correctAnswer = getResources().getStringArray(R.array.correctAnswer)[questionCounter];

            //Check if selected answer is equal to correct answer
            Boolean isCorrectAnswer = givenAnswer.equals(correctAnswer);


                if (isCorrectAnswer) {
                    //add 1 to score and send user message
                    score += 1;

                    toastMessage = Toast.makeText(MainActivity.this, "Good! score: " + score, Toast.LENGTH_SHORT);
                    toastMessage.show();

                } else {

                    toastMessage = Toast.makeText(MainActivity.this, "False! score: " + score, Toast.LENGTH_SHORT);
                    toastMessage.show();
                }

                //increase question number, clear selected radiobuttons and go to next question
                questionCounter += 1;
                answerGroup.clearCheck();
                checkNextQuestion();
        }
    }

    public void submitCheckAnswer(View view){
        //void for checking answer in last question which uses CheckBoxes


        //Cancel any ongoing toast messages to prevent building up queue of toasts
        if (toastMessage!= null) {
            toastMessage.cancel();
        }

        //retrieve correct answers for each checkbox
        Boolean checkBoxAnswerA1 = getResources().getBoolean(R.bool.checkBoxAnswerA);
        Boolean checkBoxAnswerB1 = getResources().getBoolean(R.bool.checkBoxAnswerB);
        Boolean checkBoxAnswerC1 = getResources().getBoolean(R.bool.checkBoxAnswerC);

        //Declare all views and check given answers of checkboxes
        CheckBox checkBoxA = (CheckBox) findViewById(R.id.checkboxA);
        CheckBox checkBoxB = (CheckBox) findViewById(R.id.checkboxB);
        CheckBox checkBoxC = (CheckBox) findViewById(R.id.checkboxC);
        Boolean checkBox1State = checkBoxA.isChecked();
        Boolean checkBox2State = checkBoxB.isChecked();
        Boolean checkBox3State = checkBoxC.isChecked();

        //Determine if the correct boxes are checked, update score and send message
        if (checkBox1State == checkBoxAnswerA1 && checkBox2State == checkBoxAnswerB1 && checkBox3State == checkBoxAnswerC1) {

            score += 1;
            toastMessage = Toast.makeText(MainActivity.this, "Good answer! Score: " + score, Toast.LENGTH_SHORT);
            toastMessage.show();
        } else {

            toastMessage = Toast.makeText(MainActivity.this, "False answer! " + score, Toast.LENGTH_SHORT);
            toastMessage.show();
        }

       endResults();

    }

    public void checkNextQuestion() {
    //void for checking the correct answer and determine the next step (new radioquestion or last (checkbox)question.

        //Lookup the number of questions. If all Radio-questions have been answered then open the next layout
        numberOfRadioQuestions = getResources().getStringArray(R.array.Questions).length;

        // numberOfRadioQuestions = questions.length;
        if (questionCounter < (numberOfRadioQuestions)){
            radiobuttonQuestion();
        }
        else
        {

        setContentView(R.layout.activity_question5);

        //Change the textView to show the right questionNumber
        TextView questionNumberTextView2 = findViewById(R.id.questionNumberTextview2);
        String numberOfQuestion = "Question " + (numberOfRadioQuestions +1) ;
        questionNumberTextView2.setText(numberOfQuestion);

        //create Checkbox & textview variables
        TextView checkQuestion = findViewById(R.id.checkQuestion);
        CheckBox checkBoxA = findViewById(R.id.checkboxA);
        CheckBox checkBoxB = findViewById(R.id.checkboxB);
        CheckBox checkBoxC = findViewById(R.id.checkboxC);

        //Set the text of the views with the information from string.xml
        checkQuestion.setText(getResources().getString(R.string.checkQuestion));
        checkBoxA.setText(getResources().getString(R.string.checkAnswer1));
        checkBoxB.setText(getResources().getString(R.string.checkAnswer2));
        checkBoxC.setText(getResources().getString(R.string.checkAnswer3));

        }
    }

    public void radiobuttonQuestion() {
    //void for updating the view with new questions and answers

        //Create variables for question views
        TextView questionsView = findViewById(R.id.question);
        TextView questionNumberTextview = (TextView) findViewById(R.id.questionNumberTextview);
        Button buttonA = findViewById(R.id.buttonA);
        Button buttonB = findViewById(R.id.buttonB);
        Button buttonC = findViewById(R.id.buttonC);

        //Fill the views with the questions/answers for the next question
        String numberOfQuestion = "Question " + (questionCounter +1) ;

        questionNumberTextview.setText(numberOfQuestion);
        questionsView.setText(getResources().getStringArray(R.array.Questions)[questionCounter]);
        buttonA.setText(getResources().getStringArray(R.array.AnswerA)[questionCounter]);
        buttonB.setText(getResources().getStringArray(R.array.AnswerB)[questionCounter]);
        buttonC.setText(getResources().getStringArray(R.array.AnswerC)[questionCounter]);

    }

    public void endResults(){
    //void which takes the user to the last page of the quiz with the final results.


        //Switch to results layout
        setContentView(R.layout.activity_results);

        //Display the score and the maximum score
        TextView finalScoreView2 = findViewById(R.id.finalScoreView);
        String scoreResults = score + "/" + (numberOfRadioQuestions + 1);
        finalScoreView2.setText(scoreResults);

        //Display  message with name
        String resultsMessage;
        if (score == (numberOfRadioQuestions + 1)){
            resultsMessage = "Hi " + nameOfPlayer + ", You're the best! All question were correct!";
        } else
        {
            resultsMessage = "Hi " + nameOfPlayer + ", Great try! Not the maximum score, but you can always try again";
        }

        TextView resultsMessageView = findViewById(R.id.resultsMessageView);
        resultsMessageView.setText(resultsMessage);

    }

    public void tryAgain(View view){
    //Resets the score and brings the user back to the first screen

        score = 0;
        setContentView(R.layout.activity_main);

    }


}
