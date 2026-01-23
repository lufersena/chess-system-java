package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{

	public Pawn(Board board, Color color) {
		super(board, color);
	
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean mat [][] = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		if(getColor()== Color.WHITE) {
			p.setValues(position.getRow()-1, position.getColumn());// ve a casa acima do peão
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//se a posição existir e não tiver uma peça
				mat[p.getRow()][p.getColumn()] = true; // a posiçao na matriz recebe true
			}
			p.setValues(position.getRow()-2,position.getColumn());//vê a segunda casa acima do peão
			Position p2 = new Position(position.getRow()-1, position.getColumn());//variavel auxiliar para verificar a casa 1 antes da 2
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)&&getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0){
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow()-1, position.getColumn()-1);// ve a casa noroeste do peão
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {//se a posição existir e tiver uma peça adversaria
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow()-1, position.getColumn()+1);// ve a casa nordeste do peão
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {//se a posição existir e tiver uma peça adversaria
				mat[p.getRow()][p.getColumn()] = true;
			}
		}else {
			p.setValues(position.getRow()+1, position.getColumn());// ve a casa abaixo do peão
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//se a posição existir e não tiver uma peça
				mat[p.getRow()][p.getColumn()] = true; // a posiçao na matriz recebe true
			}
			p.setValues(position.getRow()+2,position.getColumn());//vê a segunda casa abaixo do peão
			Position p2 = new Position(position.getRow()+1, position.getColumn());//variavel auxiliar para verificar a casa 1 antes da 2
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)&&getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0){
				mat[p.getRow()][p.getColumn()] = true;
		  	}
			p.setValues(position.getRow()+1, position.getColumn()+1);// ve a casa suldoeste do peão
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {//se a posição existir e tiver uma peça adversaria
				mat[p.getRow()][p.getColumn()] = true;
			}
			p.setValues(position.getRow()+1, position.getColumn()+1);// ve a casa suldeste do peão
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {//se a posição existir e tiver uma peça adversaria
				mat[p.getRow()][p.getColumn()] = true;
			}	
		}
		
		return mat;
	}	
	
	@Override
	public String toString() {
		return "P";
	}
	
	

}
