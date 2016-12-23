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

public class Queen extends Piece {


    public Queen(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    public static final int[] CANDIDATE_MOVE_COORDINATES = {-9,-8,-7,-1,1,7,8,9};


    //Initialize list of legal moves
    //Loop through each possible move candidate, check if tile is not occupied, add new move coordinate
    //Check if the new TileCoordinate is Valid
    //Check if empty Tile or Occupied, add Major move to list, Otherwise Add attack move
    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        List<Move> legalMoves = new ArrayList<>();


        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES){
            int candidateDestinationCoordianate = this.piecePosition;

            while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordianate)){
                if(firstColumnException(candidateDestinationCoordianate, currentCandidateOffset) ||
                        eightColumnException(candidateDestinationCoordianate, currentCandidateOffset)) {
                    break;
                }

                candidateDestinationCoordianate += candidateDestinationCoordianate;

                if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordianate)){
                    final Tile tileDestinationCoordinate = board.getTile(candidateDestinationCoordianate);{

                        if(!tileDestinationCoordinate.isTileOccupied()) {
                            legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordianate));
                        }else {
                            final Piece pieceAtDestination = tileDestinationCoordinate.getPiece();
                            final Alliance pieceAlliance = getPieceAlliance();

                            if(pieceAlliance != pieceAlliance){
                                legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordianate, pieceAtDestination));

                            }
                            break;
                        }
                    }
                }
            }
        } return ImmutableList.copyOf(legalMoves);
    }

    //Todo FINISH EXCEPTIONS AND PSEUDO CODE !!!! 
    public boolean firstColumnException(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1 || candidateOffset == -9 || candidateOffset == 7);
    }

    public boolean eightColumnException(final int currentPostion, final int candidateOffset){
        return BoardUtils.EIGHT_COLUMN[currentPostion] && (candidateOffset == 1 || candidateOffset == 9 || candidateOffset == -7);
    }
}
