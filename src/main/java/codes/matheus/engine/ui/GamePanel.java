package codes.matheus.engine.ui;

import codes.matheus.board.Position;
import codes.matheus.engine.core.Dimension;
import codes.matheus.engine.core.Vector2D;
import codes.matheus.engine.graphics.Elements;
import codes.matheus.engine.graphics.Sprite;
import codes.matheus.engine.tilemap.Tile;
import codes.matheus.engine.tilemap.TileMap;
import codes.matheus.pieces.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

public final class GamePanel extends ResponsivePanel {
    private final @NotNull MouseInput mouseInput = new MouseInput();
    private final @NotNull TileMap tileMap = new TileMap();
    private final @NotNull Pieces pieces = new Pieces();
    private final @NotNull UI UI;
    private @Nullable Piece selectedPiece;
    private @Nullable Position originPosition;
    private @Nullable Position targetPosition;

    public GamePanel() {
        this.UI = new UI();
        setBackground(Color.darkGray);
    }

    public @NotNull Pieces getPieces() {
        return pieces;
    }

    public @NotNull TileMap getTileMap() {
        return tileMap;
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
//                UI.resize(new Dimension(getWidth(), getHeight()));
                repaint();
                revalidate();
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
                    @NotNull Sprite sprite = ((i + j) % 2 == 0) ? new Sprite(new File("src/main/resources/background/brown-dark.png")) : new Sprite(new File("src/main/resources/background/brown-light.png"));
                    @NotNull Position position = new Position(i, j);
                    @NotNull Tile tile = new Tile(position.toVector2D(64), new Dimension(64, 64), sprite, null);
                    tileMap.setTile(position, tile);
                }
            }
            putPieces();
        }

        private void putPieces() {
            pieces.put(tileMap, new Rook(codes.matheus.Color.WHITE, tileMap), new Position('a', 1));
            pieces.put(tileMap, new Knight(codes.matheus.Color.WHITE, tileMap), new Position('b', 1));
            pieces.put(tileMap, new Bishop(codes.matheus.Color.WHITE, tileMap), new Position('c', 1));
            pieces.put(tileMap, new Queen(codes.matheus.Color.WHITE, tileMap), new Position('d', 1));
            pieces.put(tileMap, new King(codes.matheus.Color.WHITE, tileMap), new Position('e', 1));
            pieces.put(tileMap, new Bishop(codes.matheus.Color.WHITE, tileMap), new Position('f', 1));
            pieces.put(tileMap, new Knight(codes.matheus.Color.WHITE, tileMap), new Position('g', 1));
            pieces.put(tileMap, new Rook(codes.matheus.Color.WHITE, tileMap), new Position('h', 1));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.WHITE, tileMap), new Position('a', 2));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.WHITE, tileMap), new Position('b', 2));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.WHITE, tileMap), new Position('c', 2));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.WHITE, tileMap), new Position('d', 2));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.WHITE, tileMap), new Position('e', 2));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.WHITE, tileMap), new Position('f', 2));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.WHITE, tileMap), new Position('g', 2));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.WHITE, tileMap), new Position('h', 2));

            pieces.put(tileMap, new Rook(codes.matheus.Color.BLACK, tileMap), new Position('a', 8));
            pieces.put(tileMap, new Knight(codes.matheus.Color.BLACK, tileMap), new Position('b', 8));
            pieces.put(tileMap, new Bishop(codes.matheus.Color.BLACK, tileMap), new Position('c', 8));
            pieces.put(tileMap, new Queen(codes.matheus.Color.BLACK, tileMap), new Position('d', 8));
            pieces.put(tileMap, new King(codes.matheus.Color.BLACK, tileMap), new Position('e', 8));
            pieces.put(tileMap, new Bishop(codes.matheus.Color.BLACK, tileMap), new Position('f', 8));
            pieces.put(tileMap, new Knight(codes.matheus.Color.BLACK, tileMap), new Position('g', 8));
            pieces.put(tileMap, new Rook(codes.matheus.Color.BLACK, tileMap), new Position('h', 8));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.BLACK, tileMap), new Position('a', 7));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.BLACK, tileMap), new Position('b', 7));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.BLACK, tileMap), new Position('c', 7));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.BLACK, tileMap), new Position('d', 7));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.BLACK, tileMap), new Position('e', 7));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.BLACK, tileMap), new Position('f', 7));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.BLACK, tileMap), new Position('g', 7));
            pieces.put(tileMap, new Pawn(codes.matheus.Color.BLACK, tileMap), new Position('h', 7));
        }

        public void resize(@NotNull Dimension dimension) {
            int squareSize = Math.min(dimension.getWidth(), dimension.getHeight()) / 8;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    @NotNull Vector2D newPosition = new Vector2D(j * squareSize, i * squareSize);
                    @NotNull Dimension newDimension = new Dimension(squareSize, squareSize);
                    @NotNull Tile tile = tileMap.getTile(new Position(i, j));
                    tile.setTargetVector2D(newPosition);
                    tile.setDimension(newDimension);
                }
            }
        }

        public void draw(@NotNull Graphics2D graphics2D) {
            tileMap.draw(graphics2D, null);
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
                } else {
                    System.out.println("Origem definida");
                    System.out.println(selectedPiece);
                }
            } else if (originPosition.equals(clickedPosition)) {
                originPosition = null;
                selectedPiece = null;
                System.out.println("Origem null");
            } else {
                @Nullable Piece piece = pieces.getPiece(clickedPosition);
                if (piece != null && selectedPiece.getPosition() != null && !piece.isThereOpponentPiece(selectedPiece.getPosition())) {
                    originPosition = clickedPosition;
                    selectedPiece = piece;
                    System.out.println("Origem e peça selecionada alteradas");
                } else if (selectedPiece != null) {
                    targetPosition = clickedPosition;
                    System.out.println("Target definido");
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