package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece [][] pieces;
	
	public Board(int rows, int columns) {
		if(rows < 1 || columns <1) {// programação defensiva
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];//matriz de peças sera instanciada na quantidade rows e columns informada
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece piece(int row, int column) {
		if (!positionExists(row, column)) { //programação defensiva se a posição não existir
			throw new BoardException("Position not on the board");
		}
		return pieces[row][column]; //retorna a matriz pieces na linha row/ column
	}
	
	public Piece piece(Position position) {
		if (!positionExists(position)) { //programação defensiva se a posição não existir
			throw new BoardException("Position not on the board");
		}
		return pieces[position.getRow()][position.getColumn()];//sobrecarga retorna a matriz na posicão 
	}
	
	public void placePiece(Piece piece, Position position) {//metodo responsavel por colocar a peça nessa posição do tabuleiro
		if(thereIsAPiece(position)) {// programação defensiva para se caso ja tiver uma peça nessa posiçao 
			throw new BoardException("There is already a piece on position "+ position);
		}
		
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;// posição de piece é acessivel diretamente, pois na classe Piece position esta protected(mesmo pacote)
		
	}
	
	private boolean positionExists(int row, int column) { //metodo auxiliar para saber se uma posição existe com base na linha e coluna
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());//metodo para testar se uma posiçao existe
	}
	
	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) { //programação defensiva se a posição não existir
			throw new BoardException("Position not on the board");
		}
		return piece(position) != null;// metodo piece busca a position na matriz e se  for diferente de null tem uma peça na posição
	}

}
