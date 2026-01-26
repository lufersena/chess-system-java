package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	private ChessMatch chessMatch;//associção do rei com a partida
	
	public King(Board board, Color color,ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}
	
	@Override
	public String toString(){
		return "K";
	}
	
	private boolean canMove(Position position) {// metodo auxiliar para saber se é possivel se mover
		ChessPiece p = (ChessPiece)getBoard().piece(position);//downcast de piece para chesspiece
		return p == null || p.getColor() != getColor();// se p for igual a nulo ou de cor diferente 
	}
	
	private boolean testRookCastling(Position position) {//testa se a torre esta apta para o roque(quantidade de movimentos igual a 0) 
		ChessPiece p = (ChessPiece)getBoard().piece(position);//pega a peça na posição
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
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
		
		//movimeto especial roque
		if(getMoveCount() == 0 && !chessMatch.getCheck()) {// quantidade de movimento for 0 e não estiver em cheque
			//movimento especial roque do lado do rei
			Position posT1 = new Position(position.getRow(),position.getColumn()+3);// pega a posição da torre do rei
			if(testRookCastling(posT1)) {
				Position p1 = new Position(position.getRow(),position.getColumn()+1);
				Position p2 = new Position(position.getRow(),position.getColumn()+2);
				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null) {//se as posiçao p1 e p2 estiver vazia
					mat[position.getRow()][position.getColumn()+ 2] = true;//minha matriz na linha do rei e coluna mais 2 recebe true
				}
			}
			//movimento especial roque do lado da rainha
			Position posT2 = new Position(position.getRow(),position.getColumn()-4);// pega a posição da torre da rainha
			if(testRookCastling(posT2)) {
				Position p1 = new Position(position.getRow(),position.getColumn()-1);
				Position p2 = new Position(position.getRow(),position.getColumn()-2);
				Position p3 = new Position(position.getRow(),position.getColumn()-3);
				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {//se as posiçao p1,p2 e p3 estiver vazia
					mat[position.getRow()][position.getColumn()- 2] = true;//minha matriz na linha do rei e coluna menos 2 recebe true
				}
			}
		}
		
		
		return mat;
		
	}
	
	

}
