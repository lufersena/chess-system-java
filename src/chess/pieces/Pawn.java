 package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{

	private ChessMatch chessMatch;
	
	public Pawn(Board board, Color color,ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	
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
			
			//movimento especia en passant esquerda branca 
			if(position.getRow() == 3) {//posiçao da peça estiver linha 3 da matriz
				Position left = new Position(position.getRow(),position.getColumn()-1);//posiçao à esquerda dessa peça (-1 coluna)
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {//se a posiçao existir e tiver uma peça do oponente e a peça estiver vulneravel ao en passant
					mat[left.getRow()-1][left.getColumn()] = true;//a posiçao acima sera true para meu peão se mover (diagonal esquerda)
				}
			}
			//movimento especia en passant direita branca 
			if(position.getRow() == 3) {//posiçao da peça estiver linha 3 da matriz
				Position right = new Position(position.getRow(),position.getColumn()+1);//posiçao à direita dessa peça (+1 coluna)
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {//se a posiçao existir e tiver uma peça do oponente e a peça estiver vulneravel ao en passant
					mat[right.getRow()-1][right.getColumn()] = true;//a posiçao acima sera true para meu peão se mover (diagonal direita)
				}
				
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
			p.setValues(position.getRow()+1, position.getColumn()-1);// ve a casa suldeste do peão
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {//se a posição existir e tiver uma peça adversaria
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			if(position.getRow() == 4) {//posiçao da peça estiver linha 4 da matriz
				Position left = new Position(position.getRow(),position.getColumn()-1);//posiçao à esquerda dessa peça (-1 coluna)
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {//se a posiçao existir e tiver uma peça do oponente e a peça estiver vulneravel ao en passant
					mat[left.getRow()+1][left.getColumn()] = true;//a posiçao abaixo sera true para meu peão se mover (diagonal esquerda)
				}
			}
			//movimento especia en passant direita branca 
			if(position.getRow() == 4) {//posiçao da peça estiver linha 4 da matriz
				Position right = new Position(position.getRow(),position.getColumn()+1);//posiçao à direita dessa peça (+1 coluna)
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {//se a posiçao existir e tiver uma peça do oponente e a peça estiver vulneravel ao en passant
					mat[right.getRow()+1][right.getColumn()] = true;//a posiçao abaixo sera true para meu peão se mover (diagonal direita)
				}
				
			}
			
		}
		
		return mat;
	}	
	
	@Override
	public String toString() {
		return "P";
	}
	
	

}
