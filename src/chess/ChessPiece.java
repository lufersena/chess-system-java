package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color;
	private int moveCount;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public void increaseMoveCount() {
		moveCount++;
	}
	
	public void decreaseMoveCount() {
		moveCount--;
	}
	
	public ChessPosition getChessPosition() {//metodo para achar a posiçao de xadrez dentro da aplicação (Position é protected em Piece)
		return ChessPosition.fromPosition(position);//converte posicão de xadrez em posição de matriz
	}
	
	protected boolean isThereOpponentPiece(Position position) {// metodo que valida se existe uma peça adversaria
		ChessPiece p = (ChessPiece)getBoard().piece(position);//pega a peça na posição
		return p != null && p.getColor() != color;//se for diferente de nulo e diferente da cor da minha peça
	}



}
