package chess;

import boardgame.Board;

public class ChessMatch {

	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8);
	}
	
	public ChessPiece[][]getPieces(){
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];//variavel auxiliar recebendo a quantidade de linhas e colunas do board
		for(int i=0; i<board.getRows(); i++) {
			for(int j=0; j<board.getColumns(); j++) {
				mat[i][j]= (ChessPiece)board.piece(i, j);//percorre o board e a matriz i,j recebe o piece usando DownCast(interpretando-a como ChessPiece agora)
			}
		}
		return mat;
	}
}
