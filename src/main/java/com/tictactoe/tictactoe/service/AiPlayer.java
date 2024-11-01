package com.tictactoe.tictactoe.service;

public class AiPlayer extends Player {

    public AiPlayer(BoardImpl board) {
        super(board);
    }

    @Override
    public void move(int bestRow, int bestCol) {

        int bestValue = Integer.MIN_VALUE;
        Piece[][] pieces = board.getPieces();

        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[i][j] == Piece.EMPTY) {
                    pieces[i][j] = Piece.O;
                    int moveValue = minimax(pieces, 0, false);
                    pieces[i][j] = Piece.EMPTY;

                    if (moveValue > bestValue) {
                        bestRow = i;
                        bestCol = j;
                        bestValue = moveValue;
                    }
                }
            }
        }


        if (bestRow != -1 && bestCol != -1) {
            if (board.isLegalMove(bestRow, bestCol)) {
                board.updateMove(bestRow, bestCol, Piece.O);
            }
        }
    }


    private int minimax(Piece[][] pieces, int depth, boolean isMaximizing) {
        Winner winner = board.checkWinner();
        if (winner != null) {
            if (winner.getWinningPiece() == Piece.O) {
                return 10 - depth;
            } else if (winner.getWinningPiece() == Piece.X) {
                return depth - 10;
            }
        }

        if (board.isBoardFull()) {
            return 0;
        }

        if (isMaximizing) {
            int bestValue = Integer.MIN_VALUE;
            for (int i = 0; i < pieces.length; i++) {
                for (int j = 0; j < pieces[i].length; j++) {
                    if (pieces[i][j] == Piece.EMPTY) {
                        pieces[i][j] = Piece.O;
                        bestValue = Math.max(bestValue, minimax(pieces, depth + 1, false));
                        pieces[i][j] = Piece.EMPTY;
                    }
                }
            }
            return bestValue;
        } else {
            int bestValue = Integer.MAX_VALUE;
            for (int i = 0; i < pieces.length; i++) {
                for (int j = 0; j < pieces[i].length; j++) {
                    if (pieces[i][j] == Piece.EMPTY) {
                        pieces[i][j] = Piece.X;
                        bestValue = Math.min(bestValue, minimax(pieces, depth + 1, true));
                        pieces[i][j] = Piece.EMPTY;
                    }
                }
            }
            return bestValue;
        }
    }


}
