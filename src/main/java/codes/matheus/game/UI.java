package codes.matheus.game;

import codes.matheus.Color;
import codes.matheus.pieces.Piece;
import codes.matheus.player.Player;
import codes.matheus.player.Username;
import codes.matheus.util.Colors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class UI {
    private final @NotNull Player player1;
    private final @NotNull Player player2;

    public UI(@NotNull Player player1, @NotNull Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void printCapturedPieces(@NotNull List<@NotNull Piece> capturedPieces) {
        @NotNull List<@NotNull Piece> whitePieces = capturedPieces.stream()
                .filter(piece -> piece.getColor() == Color.WHITE)
                .toList();
        @NotNull List<@NotNull Piece> blackPieces = capturedPieces.stream()
                .filter(piece -> piece.getColor() == Color.BLACK)
                .toList();

        System.out.println("Captured Pieces:");
        System.out.println("White: " + Colors.ANSI_WHITE + whitePieces + Colors.ANSI_RESET);
        System.out.println("Black: " + Colors.ANSI_BLACK + blackPieces + Colors.ANSI_RESET);
        System.out.println();
    }

    public void printBoard(@Nullable Piece[][] board, boolean[][] possibleMoves) {
        @NotNull Username black = (player1.getColor() == Color.BLACK) ? player1.getUsername() : player2.getUsername();
        System.out.println("\n" + black);
        for (int i = 0; i < board.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < board[0].length; j++) {
                if (possibleMoves != null) {
                    printPiece(board[i][j], possibleMoves[i][j]);
                } else {
                    printPiece(board[i][j], false);
                }
            }
            System.out.println();
        }
        System.out.println("   a   b   c   d   e   f   g   h");
        @NotNull Username white = (black.equals(player1.getUsername())) ? player2.getUsername() : player1.getUsername();
        System.out.println(white + "\n");
    }

    private void printPiece(@Nullable Piece piece, boolean background) {
        if (background) {
            System.out.print(Colors.ANSI_BLUE_BACKGROUND);
        }

        if (piece == null) {
            System.out.print("[ ]");
        } else {
            if (piece.getColor().equals(Color.WHITE)) {
                if (background) {
                    System.out.print(Colors.ANSI_BLUE_BACKGROUND + "[" + Colors.ANSI_WHITE + piece + Colors.ANSI_RESET + Colors.ANSI_BLUE_BACKGROUND + "]" + Colors.ANSI_RESET);
                } else {
                    System.out.print("[" + Colors.ANSI_WHITE + piece + Colors.ANSI_RESET + "]");
                }
            } else {
                if (background) {
                    System.out.print(Colors.ANSI_BLUE_BACKGROUND + "[" + Colors.ANSI_BLACK + piece + Colors.ANSI_RESET + Colors.ANSI_BLUE_BACKGROUND + "]" + Colors.ANSI_RESET);
                } else {
                    System.out.print("[" + Colors.ANSI_BLACK + piece + Colors.ANSI_RESET + "]");
                }
            }
        }
        System.out.print(Colors.ANSI_RESET + " ");
    }
}
