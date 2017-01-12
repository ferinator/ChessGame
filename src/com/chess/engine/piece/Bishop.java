package com.chess.engine.piece;


import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    /**
     * Define Vectors in which a bishop can move
     * Add Exceptions where they cannot be applied
     * Loop through the 64 Fields and Add Legal Moves for the Bishop
     */

    //Vectors
    private static final int CANDIDATE_MOVE_VECTOR_COORDINATES[] = {-9, -7, 7, 9};

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        //Todo add pseudo code for explaining loop
        for (final int currentCandidateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {

            int candidateDestinationCoordinate = this.piecePosition;

            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnException(candidateDestinationCoordinate, currentCandidateOffset) ||
                        isEightColumnException(candidateDestinationCoordinate, currentCandidateOffset)) {
                    break;
                }

                candidateDestinationCoordinate += currentCandidateOffset;

                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    //Destination Location is Not Occupied
                    if (!candidateDestinationTile.isTileOccupied()) {
                        //Moved Piece "this", is the current piece we are on (Knight)
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if (pieceAlliance != pieceAlliance) {
                            legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    public static boolean isFirstColumnException(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset != 7 || candidateOffset != -9);
    }

    public static boolean isEightColumnException(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset != -7 || candidateOffset != 9);
    }

    @Override
    public String toString(){
        return PieceType.BISHOP.toString();
    }
}
