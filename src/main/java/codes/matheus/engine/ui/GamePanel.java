package codes.matheus.engine.ui;

import codes.matheus.board.Position;
import codes.matheus.engine.core.Dimension;
import codes.matheus.engine.core.Vector2D;
import codes.matheus.engine.graphics.Elements;
import codes.matheus.engine.graphics.Sprite;
import codes.matheus.engine.tilemap.Tile;
import codes.matheus.engine.tilemap.TileMap;
import codes.matheus.game.Game;
import codes.matheus.pieces.*;
import codes.matheus.player.Player;
import codes.matheus.player.Username;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.event.*;

public final class GamePanel extends ResponsivePanel {
    private final @NotNull MouseInput mouseInput = new MouseInput();
    private final @NotNull TileMap tileMap = new TileMap();
    private final @NotNull Pieces pieces = new Pieces();
    private final @NotNull UI UI;
    private @Nullable Piece selectedPiece;
    private @Nullable Position originPosition;
    private @Nullable Position targetPosition;
    private @Nullable Player player;
    private @NotNull Player player2;

    public GamePanel() {
        this.UI = new UI();
        this.player2 = new Player(new Username("Player2"));
        setBackground(Color.darkGray);
    }

    public @NotNull Player getPlayer2() {
        return player2;
    }

    public @NotNull Pieces getPieces() {
        return pieces;
    }

    public @NotNull TileMap getTileMap() {
        return tileMap;
    }

    public void setPlayer(@Nullable Player player) {
        this.player = player;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        setFocusable(true);
        setDoubleBuffered(true);
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        getResponsiveLayout().addAllReferences(Elements.getElements());
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                UI.resize();
                revalidate();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        @NotNull Graphics2D graphics2D = (Graphics2D) g;
        UI.draw(graphics2D);
        graphics2D.dispose();
    }

    public final class UI {

        public UI() {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    @NotNull Sprite sprite = ((i + j) % 2 == 0) ? Game.getTheme().getDark() : Game.getTheme().getLight();
                    @NotNull Position position = new Position(i, j);
                    @NotNull Tile tile = new Tile(position.toVector2D(64), new Dimension(64, 64), sprite, null);
                    tileMap.setTile(position, tile);
                }
            }
            putPieces();
        }

        private void putPieces() {
            pieces.put(tileMap, new Rook(codes.matheus.Color.WHITE, tileMap, pieces), new Position('a', 1));
            pieces.put(tileMap, new Knight(codes.matheus.Color.WHITE, tileMap, pieces), new Position('b', 1));
            pieces.put(tileMap, new Bishop(codes.matheus.Color.WHITE, tileMap, pieces), new Position('c', 1));
            pieces.put(tileMap, new Queen(codes.matheus.Color.WHITE, tileMap, pieces), new Position('d', 1));
            pieces.put(tileMap, new King(codes.matheus.Color.WHITE, tileMap, pieces), new Position('e', 1));
            pieces.put(tileMap, new Bishop(codes.matheus.Color.WHITE, tileMap, pieces), new Position('f', 1));
            pieces.put(tileMap, new Knight(codes.matheus.Color.WHITE, tileMap, pieces), new Position('g', 1));
            pieces.put(tileMap, new Rook(codes.matheus.Color.WHITE, tileMap, pieces), new Position('h', 1));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.WHITE, tileMap, pieces), new Position('a', 2));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.WHITE, tileMap, pieces), new Position('b', 2));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.WHITE, tileMap, pieces), new Position('c', 2));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.WHITE, tileMap, pieces), new Position('d', 2));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.WHITE, tileMap, pieces), new Position('e', 2));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.WHITE, tileMap, pieces), new Position('f', 2));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.WHITE, tileMap, pieces), new Position('g', 2));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.WHITE, tileMap, pieces), new Position('h', 2));

            pieces.put(tileMap, new Rook(codes.matheus.Color.BLACK, tileMap, pieces), new Position('a', 8));
            pieces.put(tileMap, new Knight(codes.matheus.Color.BLACK, tileMap, pieces), new Position('b', 8));
            pieces.put(tileMap, new Bishop(codes.matheus.Color.BLACK, tileMap, pieces), new Position('c', 8));
            pieces.put(tileMap, new Queen(codes.matheus.Color.BLACK, tileMap, pieces), new Position('d', 8));
            pieces.put(tileMap, new King(codes.matheus.Color.BLACK, tileMap, pieces), new Position('e', 8));
            pieces.put(tileMap, new Bishop(codes.matheus.Color.BLACK, tileMap, pieces), new Position('f', 8));
            pieces.put(tileMap, new Knight(codes.matheus.Color.BLACK, tileMap, pieces), new Position('g', 8));
            pieces.put(tileMap, new Rook(codes.matheus.Color.BLACK, tileMap, pieces), new Position('h', 8));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.BLACK, tileMap, pieces), new Position('a', 7));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.BLACK, tileMap, pieces), new Position('b', 7));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.BLACK, tileMap, pieces), new Position('c', 7));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.BLACK, tileMap, pieces), new Position('d', 7));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.BLACK, tileMap, pieces), new Position('e', 7));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.BLACK, tileMap, pieces), new Position('f', 7));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.BLACK, tileMap, pieces), new Position('g', 7));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.BLACK, tileMap, pieces), new Position('h', 7));
        }

        public void resize() {
            int squareSize = Math.min(getWidth(), getHeight()) / 8;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    @NotNull Vector2D newPosition = new Vector2D(j * squareSize, i * squareSize);
                    @NotNull Dimension newDimension = new Dimension(squareSize, squareSize);
                    tileMap.getTile(new Position(i, j)).setTargetVector2D(newPosition);
                    tileMap.getTile(new Position(i, j)).setDimension(newDimension);
                }
            }
        }

        private void draw(@NotNull Graphics2D graphics2D) {
            boolean[][] possibleMoves = new boolean[8][8];
            if (selectedPiece != null) {
                for (int i = 0; i < selectedPiece.possibleMoves().length; i++) {
                    for (int j = 0; j < selectedPiece.possibleMoves()[0].length; j++) {
                        if (selectedPiece.possibleMoves()[i][j] && tileMap.isValidPosition(new Position(i, j))) {
                            possibleMoves[i][j] = true;
                        }
                    }
                }
            }

            tileMap.draw(graphics2D, possibleMoves);
            if (player != null && player.getColor() != null) {
                graphics2D.setColor(Color.WHITE);
                int playerHeight = (player.getColor().equals(codes.matheus.Color.WHITE)) ?  tileMap.getSize().getHeight() - 10 : 10;
                graphics2D.drawString(player.getUsername().toString(), tileMap.getSize().getWidth() + 5, playerHeight);
                int player2Height = (player2.getColor().equals(codes.matheus.Color.WHITE)) ?  tileMap.getSize().getHeight() - 10 : 10;
                graphics2D.drawString(player2.getUsername().toString(), tileMap.getSize().getWidth() + 5, player2Height);
            }
        }
    }

    private final class MouseInput implements MouseListener, MouseMotionListener {
        private @NotNull Vector2D mouseVector2D;

        @Override
        public void mouseClicked(MouseEvent e) {
            @NotNull Position clickedPosition = mouseVector2D.toPosition(new Dimension(tileMap.getSize()));

            if (originPosition == null) {
                originPosition = clickedPosition;
                selectedPiece = pieces.getPiece(originPosition);
                if (selectedPiece == null) {
                    originPosition = null;
                }
            } else if (originPosition.equals(clickedPosition)) {
                originPosition = null;
                selectedPiece = null;
            } else {
                @Nullable Piece piece = pieces.getPiece(clickedPosition);
                if (piece != null && selectedPiece.getPosition() != null && !piece.isThereOpponentPiece(selectedPiece.getPosition())) {
                    originPosition = clickedPosition;
                    selectedPiece = piece;
                } else if (selectedPiece != null) {
                    targetPosition = clickedPosition;
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mouseVector2D = new Vector2D(e.getX(), e.getY());
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseVector2D = new Vector2D(e.getX(), e.getY());
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseVector2D = new Vector2D(e.getX(), e.getY());
        }
    }
}