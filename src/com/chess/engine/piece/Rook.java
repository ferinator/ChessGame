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


public class Rook extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-8, -1, 1, 8};

    public Rook(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    /**First I will create List with possible/legal moves for the piece
     * Second I will loop through my "CALCULATE_LEGAL_MOVES" which are representing the possible move for this piece
     * Third Im giving the candidateDestinationCoordinate the value of "this.piecePosition"
     * Fourth check if the DestinationCoordinate is still on Board
     * Fifth add another legal move
     * Sixth check again if the Piece is on Board
     * Seventh give the Tile on which is the current Piece
     * Eight check if Destination Tile is not Occupied anymore
     * Ninth add MajorLegalMove to the list
     * Tenth
     * @param board
     * @return
     */
    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();

        for(final int candidateCoordinateOffset : CANDIDATE_MOVE_COORDINATES){
            int candidateDestinationCoordinate = this.piecePosition;

            while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){

                if(isEightColumnException(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                        isFirstColumnExcepiton(candidateDestinationCoordinate, candidateCoordinateOffset)){
                    break;
                }
                candidateDestinationCoordinate += candidateDestinationCoordinate;

                if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                    //Why is my movedPiece defined as This ?
                    if(!candidateDestinationTile.isTileOccupied()){
                        legalMoves.add(new Move.MajorMove(board, this ,candidateDestinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                        if(pieceAlliance != pieceAlliance){
                            legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    public static boolean isFirstColumnExcepiton(final int currentPosition, final int candidateOffSet){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffSet != -1);
    }

    public static boolean isEightColumnException(final int currentPosition, final int candidateOffSet){
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffSet != 1);
    }

    @Override
    public String toString(){
        return PieceType.ROOK.toString();
    }
}
