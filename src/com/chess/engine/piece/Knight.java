package com.chess.engine.piece;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;


public class Knight extends Piece {

    private static final int[] CANDIDATE_MOVES_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(int piecePosition, Alliance pieceAlliance) {
        super(PieceType.KNIGHT, piecePosition, pieceAlliance);
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {

        int candidateDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();

        //Normal Comment - Pseudo code
        /**I dont know Comment*/
        //TODO Comment/ what we need to do


        for (final int currentCandidateOffset : CANDIDATE_MOVES_COORDINATES) {
            candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {//isValidCoordinate
                if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        (isSecondColumnExclusion(this.piecePosition, currentCandidateOffset)) ||
                        isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                        isEightColumnExclusion(this.piecePosition, currentCandidateOffset)){
                    continue;
                }
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                //Destination Location is Not Occupied
                if (!candidateDestinationTile.isTileOccupied()) {
                    //Moved Piece "this", is the current piece we are on (Knight)
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if (pieceAlliance != pieceAlliance) {
                        legalMoves.add(new Move.MajorMove.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    public static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffSet){
        return BoardUtils.FIRST_COLUMN[currentPosition] &&
                ((candidateOffSet == -17) || (candidateOffSet == -10) || (candidateOffSet == 6) || (candidateOffSet == 15));
    }

    public static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SECOND_COLUMN[currentPosition] && ((candidateOffset == 10) || (candidateOffset == 6));

    }

    public static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && ((candidateOffset == -6) || (candidateOffset == 10));
    }

    public static boolean isEightColumnExclusion(final int currentPositon, final int candidateOffset){
        return BoardUtils.EIGHT_COLUMN[currentPositon] &&
                ((candidateOffset == -15) || (candidateOffset == -6) || (candidateOffset == 10) || (candidateOffset == 17));
    }

    @Override
    public Knight movePiece(final Move move) {
        return new Knight(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
    }

    @Override
    public String toString(){
        return PieceType.KNIGHT.toString();
    }
}











