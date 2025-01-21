package codes.matheus.util;

import codes.matheus.board.Move;
import codes.matheus.engine.tilemap.TileMap;
import codes.matheus.pieces.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public final class Promotion {
    private Promotion() {
        throw new UnsupportedOperationException();
    }

    public static void promote(@NotNull TileMap tileMap, @NotNull Pieces pieces, @NotNull Move move) {
        @NotNull String[] options = {"Queen", "Knight", "Bishop", "Knight"};
        int choice = JOptionPane.showOptionDialog(null, "Select promoted piece:", "Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,  options, options[0]);

        @Nullable Piece promotedPiece = null;
        switch (choice) {
            case 0 -> promotedPiece = new Queen(move.getPiece().getColor(), tileMap, pieces);
            case 1 -> promotedPiece = new Rook(move.getPiece().getColor(), tileMap, pieces);
            case 2 -> promotedPiece = new Bishop(move.getPiece().getColor(), tileMap, pieces);
            case 3 -> promotedPiece = new Knight(move.getPiece().getColor(), tileMap, pieces);
        };

        if (promotedPiece != null) {
            pieces.removePiece(tileMap, move.getTarget());
            pieces.put(tileMap, promotedPiece, move.getTarget());
        }
    }
}
