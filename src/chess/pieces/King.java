package chess.pieces;

import boardgame.Board;
import boardgame.Position;
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
	
	private boolean canMove(Position position) {// metodo auxiliar para saber se é possivel se mover
		ChessPiece p = (ChessPiece)getBoard().piece(position);//downcast de piece para chesspiece
		return p == null || p.getColor() != getColor();// se p for igual a nulo ou de cor diferente 
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];//cria uma matriz de boolean da mesma dimensão do tabuleiro
		
		Position p = new  Position(0, 0);
		
		// above
		p.setValues(position.getRow() - 1, position.getColumn());//define as posiçoes acima do rei 
		if(getBoard().positionExists(p) && canMove(p)) {//posição p exsite e pode se mover
			mat[p.getRow()][p.getColumn()] = true;//matriz na linha p e coluna p recebe true 
		}
		
		// below
		p.setValues(position.getRow() + 1, position.getColumn());//define as posiçoes abaixo do rei 
		if(getBoard().positionExists(p) && canMove(p)) {//posição p exsite e pode se mover
		mat[p.getRow()][p.getColumn()] = true;//matriz na linha p e coluna p recebe true 
		}
		
		// left
		p.setValues(position.getRow() , position.getColumn() - 1);//define as posiçoes à esquerda do rei 
		if(getBoard().positionExists(p) && canMove(p)) {//posição p exsite e pode se mover
		mat[p.getRow()][p.getColumn()] = true;//matriz na linha p e coluna p recebe true 
		}
		
		// right
		p.setValues(position.getRow() , position.getColumn() + 1);//define as posiçoes à direita do rei 
		if(getBoard().positionExists(p) && canMove(p)) {//posição p exsite e pode se mover
		mat[p.getRow()][p.getColumn()] = true;//matriz na linha p e coluna p recebe true 
		}
		
		//northwest
		p.setValues(position.getRow() - 1, position.getColumn() -1);//define as posiçoes à noroeste do rei 
		if(getBoard().positionExists(p) && canMove(p)) {//posição p exsite e pode se mover
		mat[p.getRow()][p.getColumn()] = true;//matriz na linha p e coluna p recebe true 
		}
		
		//northeast
		p.setValues(position.getRow() -1, position.getColumn() + 1);//define as posiçoes à nordeste do rei 
		if(getBoard().positionExists(p) && canMove(p)) {//posição p exsite e pode se mover
		mat[p.getRow()][p.getColumn()] = true;//matriz na linha p e coluna p recebe true 
		}
		
		//southwest
		p.setValues(position.getRow() +1, position.getColumn() - 1);//define as posiçoes à suldoeste do rei 
		if(getBoard().positionExists(p) && canMove(p)) {//posição p exsite e pode se mover
		mat[p.getRow()][p.getColumn()] = true;//matriz na linha p e coluna p recebe true 
		}
		
		//southeast
		p.setValues(position.getRow() +1, position.getColumn() + 1);//define as posiçoes à sudeste do rei 
		if(getBoard().positionExists(p) && canMove(p)) {//posição p exsite e pode se mover
		mat[p.getRow()][p.getColumn()] = true;//matriz na linha p e coluna p recebe true 
		}
		
		return mat;
		
	}
	
	

}
