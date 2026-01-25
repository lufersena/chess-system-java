package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

	public Knight(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString(){
		return "N";
	}
	
	private boolean canMove(Position position) {// metodo auxiliar para saber se é possivel se mover
		ChessPiece p = (ChessPiece)getBoard().piece(position);//downcast de piece para chesspiece
		return p == null || p.getColor() != getColor();// se p for igual a nulo ou de cor diferente 
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];//cria uma matriz de boolean da mesma dimensão do tabuleiro
		
		Position p = new  Position(0, 0);
		
		
		p.setValues(position.getRow() - 1, position.getColumn()-2);
		if(getBoard().positionExists(p) && canMove(p)) {//posição p exsite e pode se mover
			mat[p.getRow()][p.getColumn()] = true;//matriz na linha p e coluna p recebe true 
		}
		
		p.setValues(position.getRow()-2, position.getColumn()-1);
		if(getBoard().positionExists(p) && canMove(p)) {//posição p exsite e pode se mover
		mat[p.getRow()][p.getColumn()] = true;//matriz na linha p e coluna p recebe true 
		}
		
		p.setValues(position.getRow()-2, position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {//posição p exsite e pode se mover
		mat[p.getRow()][p.getColumn()] = true;//matriz na linha p e coluna p recebe true 
		}
		
		p.setValues(position.getRow() -1 , position.getColumn() + 2);
		if(getBoard().positionExists(p) && canMove(p)) {//posição p exsite e pode se mover
		mat[p.getRow()][p.getColumn()] = true;//matriz na linha p e coluna p recebe true 
		}
		
		p.setValues(position.getRow() + 1, position.getColumn() +2);
		if(getBoard().positionExists(p) && canMove(p)) {//posição p exsite e pode se mover
		mat[p.getRow()][p.getColumn()] = true;//matriz na linha p e coluna p recebe true 
		}
		
		p.setValues(position.getRow() +2, position.getColumn() + 1); 
		if(getBoard().positionExists(p) && canMove(p)) {//posição p exsite e pode se mover
		mat[p.getRow()][p.getColumn()] = true;//matriz na linha p e coluna p recebe true 
		}
		
		p.setValues(position.getRow() +2, position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {//posição p exsite e pode se mover
		mat[p.getRow()][p.getColumn()] = true;//matriz na linha p e coluna p recebe true 
		}
		
		p.setValues(position.getRow() +1, position.getColumn() -2 );
		if(getBoard().positionExists(p) && canMove(p)) {//posição p exsite e pode se mover
		mat[p.getRow()][p.getColumn()] = true;//matriz na linha p e coluna p recebe true 
		}
		
		return mat;
		
	}
	
	

}
