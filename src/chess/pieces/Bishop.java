package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece{
	
	public Bishop (Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "B";
	}
	
	@Override
	public boolean[][] possibleMoves() {//provisorio todas posiçoes retornam false
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];//cria uma matriz de boolean da mesma dimensão do tabuleiro
		
		Position p = new Position(0, 0);
		
		//noroeste 
		p.setValues(position.getRow() - 1, position.getColumn()-1);
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posição p existir e não tiver uma peça nela (a posiçao estara vaga)
			mat[p.getRow()][p.getColumn()] = true;// ou seja recebe um true indicando que a peça pode mover para la
			p.setValues(p.getRow()-1, p.getColumn()-1);
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {// se tiver uma posiçao p e uma peça adversaria tambem recebe true
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//nordeste 
				p.setValues(position.getRow()-1, position.getColumn()+1);
				while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posição p existir e não tiver uma peça nela (a posiçao estara vaga)
					mat[p.getRow()][p.getColumn()] = true;// ou seja recebe um true indicando que a peça pode mover para la
					p.setValues(p.getRow()-1,p.getColumn()+1);
				}
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {// se tiver uma posiçao p e uma peça adversaria tambem recebe true
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				//suldoeste 
				p.setValues(position.getRow()+1, position.getColumn() + 1);
				while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posição p existir e não tiver uma peça nela (a posiçao estara vaga)
					mat[p.getRow()][p.getColumn()] = true;// ou seja recebe um true indicando que a peça pode mover para la
					p.setValues(p.getRow()+1, p.getColumn()+1);;
				}
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {// se tiver uma posiçao p e uma peça adversaria tambem recebe true
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				//suldeste 
				p.setValues(position.getRow() + 1, position.getColumn()-1);
				while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posição p existir e não tiver uma peça nela (a posiçao estara vaga)
					mat[p.getRow()][p.getColumn()] = true;// ou seja recebe um true indicando que a peça pode mover para la
					p.setValues(p.getRow()+1, p.getColumn()-1);
				}
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {// se tiver uma posiçao p e uma peça adversaria tambem recebe true
					mat[p.getRow()][p.getColumn()] = true;
				}
		
		return mat;
	}

}
