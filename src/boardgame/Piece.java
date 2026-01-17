package boardgame;

public abstract class Piece {
	
	protected Position position;//posicão simples de matriz não pode ser visivel na camada Chess
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	protected Board getBoard() { //Board associado a uma peça só pode ser aceessado pelo pacote e subclasses de Piece
		return board;
	}

	public abstract boolean [][] possibleMoves();
	
	public boolean possibleMove(Position position) { // metodo concreto utilizando metodo abstrato(rook methods)
		return possibleMoves()[position.getRow()] [position.getColumn()];//metodo retorna true ou false se a peça pode mover para essa dada posição
	}
	
	public boolean isThereAnyPossibleMove () {
		boolean [][] mat = possibleMoves();
		for (int i=0; i<mat.length; i++) {
			for (int j=0; j<mat.length; j++) {
				if(mat[i][j]) {//se a matriz na linha i coluna j retornar verdadeiro, existe um movimento possivel
					return true;
				}
			}
		}
		return false;
	}

}
