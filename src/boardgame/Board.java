package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece [][] pieces;
	
	public Board(int rows, int columns) {
		super();
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];//matriz de peças sera instanciada na quantidade rows e columns informada
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public Piece piece(int row, int column) {
		return pieces[row][column]; //retorna a matriz pieces na linha row/ column
	}
	
	public Piece piece(Position position) {
		return pieces[position.getRow()][position.getColumn()];//sobrecarga retorna a matriz na posicão 
	}
	
	public void placePiece(Piece piece, Position position) {//metodo responsavel por colocar a peça nessa posição do tabuleiro
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;// posição de piece é acessivel diretamente, pois na classe Piece position esta protected(mesmo pacote)
		
	}
	
	
	

}
