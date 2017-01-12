package com.chess.engine.piece;


import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class King extends Piece {

    private static final int[] CANDIDATE_MOVES_COORDINATES = {-9, -8, 7, -1, 1, 7, 8, 9};

    public King(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList();

        for(final int currentCandidateOffSet : CANDIDATE_MOVES_COORDINATES){
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffSet;

            if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffSet) || isEightColumnExclusion(this.piecePosition, currentCandidateOffSet)){
                continue;
            }

            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                //Destination Location is Not Occupied
                if (!candidateDestinationTile.isTileOccupied()) {
                    //Moved Piece "this", is the current piece we are on (Knight)
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if (pieceAlliance != pieceAlliance) {
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return null;
    }

    public static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffSet){
        return BoardUtils.FIRST_COLUMN[currentPosition] &&
                ((candidateOffSet == -9) || (candidateOffSet == -1) || (candidateOffSet == 7));
    }

    public static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHT_COLUMN[currentPosition] && ((candidateOffset == 9) || (candidateOffset == 1) || (candidateOffset == -7));
    }

    @Override
    public String toString(){
        return PieceType.KING.toString();
    }
}
