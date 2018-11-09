package com.example.gebruiker.tictactoe;

import java.io.Serializable;

public class Game implements Serializable {
    final private int BOARD_SIZE = 3;
    private TileState[][] board;
    private Boolean playerOneTurn;  // true if player 1's turn, false if player 2's turn
    private int movesPlayed;
    private Boolean gameOver;

    // Constructor
    public Game() {
        board = new TileState[BOARD_SIZE][BOARD_SIZE];
        for(int i=0; i<BOARD_SIZE; i++)
            for(int j=0; j<BOARD_SIZE; j++)
                board[i][j] = TileState.BLANK;

        playerOneTurn = true;
        gameOver = false;

    }

    // Method
    public TileState choose(int row, int column) {
        if (board[row][column] == TileState.BLANK) {
            if (playerOneTurn) {
                board[row][column] = TileState.CROSS;
                playerOneTurn = false;
            }
            else {
                board[row][column] = TileState.CIRCLE;
                playerOneTurn = true;
            }
            return board[row][column];
        }
        else {
            return TileState.INVALID;
        }
    }

    // Method win
    public GameState won() {
        int row_p1 = 0, row_p2 = 0, column_p1 = 0, column_p2 = 0, diagonal_p1 = 0, diagonal_p2 = 0;
        int blank = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            row_p1 = 0;
            row_p2 = 0;
            column_p1 = 0;
            column_p2 = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {

                // checks for a winning line in the rows
                if (board[i][j] == TileState.CROSS) {
                    row_p1++;
                }
                else if (board[i][j] == TileState.CIRCLE) {
                    row_p2++;
                }

                // checks for a winning line in the columns
                if (board[j][i] == TileState.CROSS) {
                    column_p1++;
                }
                else if (board[j][i] == TileState.CIRCLE) {
                    column_p2++;
                }

                // checks if there are empty tiles
                if (board[i][j] == TileState.BLANK) {
                    blank++;
                }
            }

            // checks for a winning line in the top left right down diagonal
            if (board[i][i] == TileState.CROSS) {
                diagonal_p1++;
            }
            else if (board[i][i] == TileState.CIRCLE) {
                diagonal_p2++;
            }

            // returns the current GameState if won in a row or column
            if (row_p1 == BOARD_SIZE || column_p1 == BOARD_SIZE) {
                return GameState.PLAYER_ONE;
            }
            else if (row_p2 == BOARD_SIZE || column_p2 == BOARD_SIZE) {
                return GameState.PLAYER_TWO;
            }

        }

        // checks for a winning line in the top right left down diagonal
        if (board[2][0] == TileState.CROSS && board[1][1] == TileState.CROSS && board[0][2] == TileState.CROSS){
            diagonal_p1 = 3;
        }
        else if (board[2][0] == TileState.CIRCLE && board[1][1] == TileState.CIRCLE && board[0][2] == TileState.CIRCLE) {
            diagonal_p2 = 3;
        }

        // returns the current GameState if won in a diagonal
        if (diagonal_p1 == BOARD_SIZE) {
            return GameState.PLAYER_ONE;
        }
        else if (diagonal_p2 == BOARD_SIZE) {
            return GameState.PLAYER_TWO;
        }
        else if (blank != 0) {
            return GameState.IN_PROGRESS;
        }
        else {
            return GameState.DRAW;
        }
    }
}
