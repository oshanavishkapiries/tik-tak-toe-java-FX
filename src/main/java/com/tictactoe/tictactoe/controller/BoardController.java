package com.tictactoe.tictactoe.controller;

import com.tictactoe.tictactoe.service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class BoardController implements BoardUI {

    BoardImpl board;
    AiPlayer ai;
    HumanPlayer human;

    public BoardController() {
        board = new BoardImpl();
        ai = new AiPlayer(board);
        human = new HumanPlayer(board);
    }

    @FXML
    private GridPane MainGrid;

    @FXML
    void onclick_grid_btn(ActionEvent event) {
        Button button = (Button) event.getSource();
        int row = Integer.parseInt(button.getId().split("")[2]);
        int col = Integer.parseInt(button.getId().split("")[3]);

        human.move(row, col);
        ai.findBestMove();
        board.printBoard();
        updateUi();
        if (board.checkWinner() != null) {
            NotifyWinner(board.checkWinner().getWinningPiece());
        }
    }

    public void updateUi() {
        for (int i = 0; i < board.getPieces().length; i++) {
            for (int j = 0; j < board.getPieces()[i].length; j++) {
                update(i, j, board.getPieces()[i][j]);
            }
        }
    }

    @Override
    public void update(int row, int col, Piece piece) {
        String buttonId = "id" + row + col;
        for (Node node : MainGrid.getChildren()) {
            if (node instanceof Button button && buttonId.equals(node.getId())) {
                if (piece == Piece.X) {
                    button.setText("X");
                } else if (piece == Piece.O) {
                    button.setText("O");
                } else {
                    button.setText("");
                }
                break;
            }
        }
    }

    @Override
    public void NotifyWinner(Piece winner) {
        if (winner == Piece.X) {
            System.out.println("X wins");
            new Alert(Alert.AlertType.INFORMATION, "X wins").showAndWait();
        } else if (winner == Piece.O) {
            System.out.println("O wins");
            new Alert(Alert.AlertType.INFORMATION, "O wins").showAndWait();
        }
    }
}
