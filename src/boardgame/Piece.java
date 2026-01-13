package boardgame;

public class Piece {
	
	protected Position position;//posicão simples de matriz não pode ser visivel na camada Chess
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	protected Board getBoard() { //Board associado a uma peça só pode ser aceessado pelo pacote e subclasses de Piece
		return board;
	}

	
	

}
