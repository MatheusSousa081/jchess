package codes.matheus.board;

import codes.matheus.exception.BoardException;
import codes.matheus.pieces.Piece;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Board {
    public static final int ROWS = 8;
    public static final int COLUMNS = 8;
    private final @Nullable Piece[][] board = new Piece[ROWS][COLUMNS];
    private @NotNull MoveExecutor moveExecutor;

    public Board() {
    }

    public @NotNull MoveExecutor getMoveExecutor() {
        return moveExecutor;
    }

    public void setMoveExecutor(@NotNull MoveExecutor moveExecutor) {
        this.moveExecutor = moveExecutor;
    }

    public Piece @Nullable [][] getBoard() {
        return board;
    }

    public @Nullable Piece getPiece(@NotNull Position position) {
        return board[position.getRow()][position.getColumn()];
    }

    public void putPiece(@NotNull Piece piece, @NotNull Position position) {
        if (thereIsAPiece(position)) {
            throw new BoardException("There is already a piece on position");
        }

        board[position.getRow()][position.getColumn()] = piece;
        piece.setPosition(position);
    }

    public @Nullable Piece removePiece(@NotNull Position position) {
        if (getPiece(position) == null) {
            return null;
        }

        @Nullable Piece piece = getPiece(position);
        piece.setPosition(null);
        board[position.getRow()][position.getColumn()] = null;
        return piece;
    }

    @SuppressWarnings("ConstantValue")
    public boolean positionExists(@NotNull Position position) {
        return position.getRow() >= 0 && position.getRow() < ROWS && position.getColumn() >= 0 && position.getColumn() < COLUMNS;

    }

    public boolean thereIsAPiece(@NotNull Position position) {
        return getPiece(position) != null;
    }
}
