package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece{
	
	public Rook (Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "R";
	}
	
	@Override
	public boolean[][] possibleMoves() {//provisorio todas posiçoes retornam false
		boolean [][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];//cria uma matriz de boolean da mesma dimensão do tabuleiro
		
		Position p = new Position(0, 0);
		
		//above - valida se existe movimento para cima 
		p.setValues(position.getRow() - 1, position.getColumn());// acessa a posiçao da peça na mesma coluna e acima da posiçao na linha -1 dela
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posição p existir e não tiver uma peça nela (a posiçao estara vaga)
			mat[p.getRow()][p.getColumn()] = true;// ou seja recebe um true indicando que a peça pode mover para la
			p.setRow(p.getRow() - 1);//faz a peça andar uma posição para cima enquanto tiver vazia
		}
		if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {// se tiver uma posiçao p e uma peça adversaria tambem recebe true
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//left - valida se existe movimento para esquerda 
				p.setValues(position.getRow(), position.getColumn() - 1);// acessa a posiçao da peça na mesma linha e à esquerda da posiçao na coluna -1 dela
				while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posição p existir e não tiver uma peça nela (a posiçao estara vaga)
					mat[p.getRow()][p.getColumn()] = true;// ou seja recebe um true indicando que a peça pode mover para la
					p.setColumn(p.getColumn() - 1);//faz a peça andar uma posição para esquerda enquanto tiver vazia
				}
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {// se tiver uma posiçao p e uma peça adversaria tambem recebe true
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				//right - valida se existe movimento para direita 
				p.setValues(position.getRow(), position.getColumn() + 1);// acessa a posiçao da peça na mesma linha e à direita da posiçao na coluna +1 dela
				while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posição p existir e não tiver uma peça nela (a posiçao estara vaga)
					mat[p.getRow()][p.getColumn()] = true;// ou seja recebe um true indicando que a peça pode mover para la
					p.setColumn(p.getColumn() + 1);//faz a peça andar uma posição para direita enquanto tiver vazia
				}
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {// se tiver uma posiçao p e uma peça adversaria tambem recebe true
					mat[p.getRow()][p.getColumn()] = true;
				}
				
				//below - valida se existe movimento para baixo 
				p.setValues(position.getRow() + 1, position.getColumn());// acessa a posiçao da peça na mesma coluna e abaixo da posiçao na linha +1 dela
				while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // enquanto a posição p existir e não tiver uma peça nela (a posiçao estara vaga)
					mat[p.getRow()][p.getColumn()] = true;// ou seja recebe um true indicando que a peça pode mover para la
					p.setRow(p.getRow() + 1);//faz a peça andar uma posição para baixo enquanto tiver vazia
				}
				if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {// se tiver uma posiçao p e uma peça adversaria tambem recebe true
					mat[p.getRow()][p.getColumn()] = true;
				}
		
		return mat;
	}

}
