package com.example.guessinggame;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText txtGuess;
    private Button guessBtn;
    private TextView resText;
    private int theNumber, count;
    private TextView countTry;
    private int range = 100;
    private TextView lblRange;

    public void newGame(){
        count = 0;
        theNumber = (int) (Math.random() * range + 1);
        lblRange.setText("Введите число от 1 до " + range + ":");
        countTry=findViewById(R.id.countTry);
        countTry.setText(Integer.toString(count));
        resText=findViewById(R.id.resText);
        resText.setText("Начнём новую игру!");
        txtGuess.setText("" + range/2);
        txtGuess.requestFocus();
        txtGuess.selectAll();
    }

    public void checkGuess(){
        String guessText = txtGuess.getText().toString();
        String message = "";
        try{
            int guess = Integer.parseInt(guessText);
            if (guess < theNumber && guess <= range){
                count = count + 1;
                countTry=findViewById(R.id.countTry);
                countTry.setText(Integer.toString(count));
                message = guess + " меньше загаданного";
            }
            else if (guess > theNumber && guess <= range){
                count = count + 1;
                countTry=findViewById(R.id.countTry);
                countTry.setText(Integer.toString(count));
                message = guess + " больше загаданного";
            }
            else if (guess <= range){
                count = count + 1;
                countTry=findViewById(R.id.countTry);
                countTry.setText(Integer.toString(count));
                message = "Вы угадали за " + count + " попыток! Давайте сыграем ещё!";
                newGame();
            }
            else {
                throw new Exception("Введите число от 1 до " + range + "!");
            }
        } catch (Exception e){
            message = e.getMessage();
        } finally {
            countTry=findViewById(R.id.countTry);
            countTry.setText(Integer.toString(count));

            resText.setText(message);
            txtGuess.requestFocus();
            txtGuess.selectAll();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtGuess = (EditText) findViewById(R.id.txtGuess);
        guessBtn = (Button) findViewById(R.id.guessBtn);
        resText = (TextView) findViewById(R.id.resText);
        lblRange =(TextView) findViewById(R.id.textProps);
        newGame();
        resText.setText("Веведите число, а за тем нажмите на кнопку 'Проверить'");
        guessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess();
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_newgame:
                newGame();
                return true;
            case R.id.action_gamestats:
                return true;
            case R.id.action_settings:
                final CharSequence[] items = {"1 to 10", "1 to 100", "1 to 1000"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Выбор диапазона");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item){
                            case 0:
                                range = 10;
                                newGame();
                                break;
                            case 1:
                                range = 100;
                                newGame();
                                break;
                            case 2:
                                range = 1000;
                                newGame();
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                AlertDialog alert =builder.create();
                alert.show();
                return true;
            case R.id.action_about:
                AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).create();
                aboutDialog.setTitle("О Guessing Game");
                aboutDialog.setMessage("(c)2020 Dudakov A.I.");
                aboutDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                aboutDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
