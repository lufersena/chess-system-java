package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
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
	
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { // operção de colocar peça usando as cordenadas do xadrez 
		board.placePiece(piece, new ChessPosition(column, row).toPosition());// converte para posição da matriz
	}
	
	private void initialSetup() {//metodo responsavel por iniciar a partida colocando as peças no tabuleiro
		placeNewPiece('b', 6, new Rook (board, Color.WHITE));
		placeNewPiece('e', 8, new King(board, Color.BLACK));
		placeNewPiece('e', 1, new King(board, Color.BLACK));
	}
}
