package com.example.gebruiker.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Game game;
    int buttons[] = {R.id.c_11, R.id.c_12, R.id.c_13, R.id.c_21, R.id.c_22, R.id.c_23, R.id.c_31, R.id.c_32, R.id.c_33};
    String buttons_id[] = {"c_11", "c_12", "c_13", "c_21", "c_22", "c_23", "c_31", "c_32", "c_33"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

            // initialize the game
            game = new Game();
        }
        else {

            // retrieves the game state
            game = (Game) savedInstanceState.getSerializable("game_state");

            // retrieves the won message and disables the buttons if necessary
            ((TextView) findViewById(R.id.end_game)).setText(savedInstanceState.getCharSequence("end_game"));
            if (savedInstanceState.getCharSequence("end_game") == "IT'S A DRAW!"
                || savedInstanceState.getCharSequence("end_game") == "CONGRATULATIONS PLAYER ONE!"
                || savedInstanceState.getCharSequence("end_game") == "CONGRATULATIONS PLAYER TWO!"){
                buttonsOff();
            }
//            // TODO dit is mooier maar werkt niet --> later nog naar kijken!
//            if (savedInstanceState.getCharSequence("end_game") != "") {
//                buttonsOff();
//            }

            // retrieves all the button figures
            for (int i = 0; i < buttons.length; i++) {
                ((TextView) findViewById(buttons[i])).setText(savedInstanceState.getCharSequence(buttons_id[i]));
            }
        }
    }

    public void tileClicked(View view) {

        // gets the state of the clicked button
        int id = view.getId();
        TileState state = TileState.BLANK;
        switch (id) {
            case R.id.c_11:
                state = game.choose(0, 0);
                break;
            case R.id.c_12:
                state = game.choose(0, 1);
                break;
            case R.id.c_13:
                state = game.choose(0, 2);
                break;
            case R.id.c_21:
                state = game.choose(1, 0);
                break;
            case R.id.c_22:
                state = game.choose(1, 1);
                break;
            case R.id.c_23:
                state = game.choose(1, 2);
                break;
            case R.id.c_31:
                state = game.choose(2, 0);
                break;
            case R.id.c_32:
                state = game.choose(2, 1);
                break;
            case R.id.c_33:
                state = game.choose(2, 2);
                break;
        }

        // puts the correct figure on the button
        switch (state) {
            case CROSS:
                ((TextView) findViewById(id)).setText("X");
                break;
            case CIRCLE:
                ((TextView) findViewById(id)).setText("O");
                break;
            case INVALID:
                break;
        }

        // checks if the game is won, returns an appropriate win message and disables the buttons if won
        GameState won = game.won();
        switch (won) {
            case PLAYER_ONE:
                ((TextView) findViewById(R.id.end_game)).setText("CONGRATULATIONS PLAYER ONE!");
                buttonsOff();
                break;
            case PLAYER_TWO:
                ((TextView) findViewById(R.id.end_game)).setText("CONGRATULATIONS PLAYER TWO!");
                buttonsOff();
                break;
            case DRAW:
                ((TextView) findViewById(R.id.end_game)).setText("IT'S A DRAW!");
                buttonsOff();
                break;
            case IN_PROGRESS:
                break;
        }
    }

    // function that disables the buttons
    public void buttonsOff() {
        for (int i = 0; i < buttons.length; i++) {
            findViewById(buttons[i]).setEnabled(false);
        }
    }

    // function that creates a new game when new game button is clicked
    public void resetClicked(View view) {
        game = new Game();
        for (int i = 0; i < buttons.length; i++) {
            ((TextView) findViewById(buttons[i])).setText(" ");
            findViewById(buttons[i]).setEnabled(true);
        }
        ((TextView) findViewById(R.id.end_game)).setText("");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (int i = 0; i < buttons.length; i++) {
            outState.putCharSequence(buttons_id[i], ((TextView) findViewById(buttons[i])).getText());
        }
        outState.putCharSequence("end_game", ((TextView) findViewById(R.id.end_game)).getText());
        outState.putSerializable("game_state", game);
    }

}


