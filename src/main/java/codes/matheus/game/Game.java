package codes.matheus.game;

import codes.matheus.Color;
import codes.matheus.board.Board;
import codes.matheus.board.ChessPosition;
import codes.matheus.board.Move;
import codes.matheus.pieces.Pawn;
import codes.matheus.pieces.Piece;
import codes.matheus.player.Player;
import codes.matheus.player.Username;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Scanner;

public final class Game implements Runnable {
    private final @NotNull Board board = new Board();
    private final @NotNull Scanner scanner = new Scanner(System.in);

    public Game() {
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void run() {
        System.out.print("Insira um nome do player 1: ");
        @NotNull Username username1 = new Username(scanner.nextLine());
        System.out.print("Insira um nome do player 2: ");
        @NotNull Username username2 = new Username(scanner.nextLine());

        boolean player1IsWhite = Math.random() < 0.5;
        @NotNull Color player1Color = player1IsWhite ? Color.WHITE : Color.BLACK;
        @NotNull Color player2Color = (player1Color == Color.WHITE) ? Color.BLACK : Color.WHITE;

        @NotNull Player player1 = new Player(username1, player1Color);
        @NotNull Player player2 = new Player(username2, player2Color);

        @NotNull GameManager gameManager = new GameManager(board, player1, player2);
        board.setMoveExecutor(gameManager.getMoveExecutor());
        @NotNull UI ui = new UI(player1, player2);
        while (!(gameManager.getGameState().equals(GameManager.GameState.CHECKMATE) || gameManager.getGameState().equals(GameManager.GameState.DRAW))) {
            ui.printBoard(board.getBoard(), null);
            if (gameManager.getGameState().equals(GameManager.GameState.CHECK)) {
                System.out.println("Check!");
            }
            ui.printCapturedPieces(gameManager.getCapturedPieces());
            System.out.println(gameManager.getCurrentPlayer().getUsername() + " play!");
            System.out.print("Enter piece origin position: ");
            @NotNull ChessPosition origin = Move.readChessPosition();
            @NotNull Piece piece = board.getPiece(origin.toPosition());

            while (!board.thereIsAPiece(origin.toPosition()) || !piece.hasPossibleMove() || piece.getColor() != gameManager.getCurrentPlayer().getColor()) {
                System.out.print("There is no piece in this position or it has no possible moves or it doesn't belong to your color. Choose another position: ");
                origin = Move.readChessPosition();
                piece = board.getPiece(origin.toPosition());
            }

            System.out.println("Piece selected: " + piece.getClass().getSimpleName());
            ui.printBoard(board.getBoard(), piece.possibleMoves());
            ui.printCapturedPieces(gameManager.getCapturedPieces());

            System.out.print("Enter piece target position: ");
            @NotNull ChessPosition target = Move.readChessPosition();

            while (!piece.possibleMoves()[target.toPosition().getRow()][target.toPosition().getColumn()]) {
                System.out.print("Move invalid! Enter a new position valid: ");
                target = Move.readChessPosition();
            }

            @NotNull Move move = new Move(origin, target, Objects.requireNonNull(board.getPiece(origin.toPosition())));
            gameManager.executeMove(move);

            if (piece instanceof Pawn && (target.toPosition().getRow() == 0 || target.toPosition().getRow() == 7)) {
                System.out.print("Choice a new piece (Q/R/B/N): ");
                char choice = scanner.next().toUpperCase().charAt(0);
                gameManager.promotion(piece, target.toPosition(), choice);
            }

            gameManager.checkDraw();
        }

        ui.printBoard(board.getBoard(), null);
        if (gameManager.getGameState().equals(GameManager.GameState.CHECKMATE)) {
            System.out.println("Checkmate");
            System.out.println(gameManager.getOpponent(gameManager.getCurrentPlayer().getColor()).getUsername() + " win!");
        } else {
            System.out.println("The game is a draw (stalemate).");
        }
    }
}
