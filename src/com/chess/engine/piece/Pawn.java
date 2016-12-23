package com.chess.engine.piece;


import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece {


    private static final int[] CANDIDATE_MOVE_COORDINATES = {7, 8, 9, 16};

    public Pawn(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }


    /** blab albd
     *
     * @param board
     * @return
     */
    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        List<Move> legalMoves = new ArrayList<>();

        for (final int currentCanidateOffset : CANDIDATE_MOVE_COORDINATES) {

            final int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * currentCanidateOffset);

            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            if (currentCanidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));

            } else if (currentCanidateOffset == 16 && this.isFirstMove() &&
                        (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite())) {

                    final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);

                    if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                    }

                } else if(currentCanidateOffset == 7 &&
                !((BoardUtils.EIGHT_COLUMN[this.piecePosition]) && this.pieceAlliance.isWhite() ||
                        (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))){

                    if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                        final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();

                        if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                            //TODO more to do here
                            legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                        }
                    }

                } else if(currentCanidateOffset == 9 &&
                        !((BoardUtils.FIRST_COLUMN[this.piecePosition]) && this.pieceAlliance.isWhite() ||
                                (BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))){

                    if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                        final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();

                        if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                            //TODO more to do here
                            legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                        }
                    }
                }
            }
            return ImmutableList.copyOf(legalMoves);
        }
    }

