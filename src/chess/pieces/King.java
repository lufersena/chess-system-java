package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString(){
		return "K";
	}

	@Override
	public boolean[][] possibleMoves() {//provisorio todas posiçoes retornam false
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];//cria uma matriz de boolean da mesma dimensão do tabuleiro
		return mat;
	}
	
	

}
