package chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece getEnPassantVulnerable(){
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
	}
	
	public ChessPiece[][]getPieces(){
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];//variavel auxiliar recebendo a quantidade de linhas e colunas do board
		for(int i=0; i<board.getRows(); i++) {
			for(int j=0; j<board.getColumns(); j++) {
				mat[i][j]= (ChessPiece)board.piece(i, j);//percorre o board e a matriz i,j recebe o piece usando DownCast(interpretando-a como ChessPiece agora)
			}
		}
		return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){// metodo que retorna uma matriz com os possiveis movimentos(imprime no program)
		Position position = sourcePosition.toPosition();//posição de origem convertida para posiçao de matriz
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();// retorna o tabuleiro com a peça da posiçao e seu movimentos
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();//posição de origem 
		Position target = targetPosition.toPosition();//posiçao de destino
		validateSourcePosition(source);//valida a posicao de origem 
		validateTargetPosition(source, target);//valida o destino da peça 
		Piece capturedPiece = makeMove(source, target);// recebe a operaçao que realiza o movimento da peça
		
		if(testCheck(currentPlayer)) {// testa se o jogador atual se colocou em check e desfaz o movimento
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in chek!");	
		}
		
		ChessPiece movedPiece = (ChessPiece)board.piece(target);//pega a peça que se moveu para o destino e isola
		
		//movimento especial promoção
		promoted = null;// garante que é uma nova promoção
		if(movedPiece instanceof Pawn) {
			if((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) ||movedPiece.getColor() == Color.BLACK && target.getRow() == 7){// se algum peão chegar na linha final do tabuleiro
				promoted = (ChessPiece)board.piece(target);// promoted recebe o peão na posição de destino
				promoted = replacePromotedPiece ("Q"); 
			}
		}
		
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;//atributo check recebe true se o testCheck do oponente do jogador atual for true  se não false
		
		if(testCheckMate(opponent(currentPlayer))) {//se o oponente da peça que mexeu ficou em check mate
			checkMate = true;
		}else {
			nextTurn();			
		}
		
		//movimento especial en passant
		if(movedPiece instanceof Pawn && (target.getRow() == source.getRow()-2) || (target.getRow() == source.getRow()+2)) {//se for um peão e ele se moveu 2 casas 
			enPassantVulnerable = movedPiece;//a peça esta vulneravel ao en passant
		}
		else {
			enPassantVulnerable = null;
		}
		
		return (ChessPiece)capturedPiece;//peça capturada recebendo um downcast para ChessPiece
	}
	
	//movimento especial promoção
	public ChessPiece replacePromotedPiece(String type) {//metodo que recebe uma letra (type) e promove o peão
		if(promoted == null) {//verifica se existe uma peça para promoção
			throw new IllegalStateException("There is no piece to be promoted");
		}
		if(!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {//peças validas para promoçao do peão
			throw new InvalidParameterException("Invalid type for promotion");
		}
		
		Position pos = promoted.getChessPosition().toPosition();// pega a posiçao da peça
		Piece p = board.removePiece(pos);//remove a peça e guarda na variavel
		piecesOnTheBoard.remove(p);//remove da lista de peças no tabuleiro
		
		ChessPiece newPiece = newPiece(type, promoted.getColor());//nova peça recebe a letra e a cor da peça promovida
		board.placePiece(newPiece, pos);//coloca a peça no tabuleiro
		piecesOnTheBoard.add(newPiece);//adiciona na lista de peças do tabuleiro a nova peça
		
		return newPiece;
	}
	
	private ChessPiece newPiece(String type, Color color) {//metodo auxiliar para trocar(instanciar) a peça promovida
		if(type.equals("B")) return new Bishop(board, color);
		if(type.equals("N")) return new Knight(board, color);
		if(type.equals("Q")) return new Queen(board, color);
		return new Rook(board, color);
	}
	
	private Piece makeMove(Position source, Position target) {// metodo que faz o movimento da peça 
		ChessPiece p = (ChessPiece)board.removePiece(source);//p recebe a peça na posiçao de origem 
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);// capturedPiece recebe a possivel peça de destino
		board.placePiece(p, target);//coloca a peça de origem na posiçao de destino
		
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);//remove a peça capturada da lista de peças no tabuleiro 
			capturedPieces.add(capturedPiece);//e adiciona na lista de peças capturadas
		}
		//movimeto especial roque lado do rei
		if(p instanceof King && target.getColumn() == source.getColumn()+2){//se for um rei e o destino foi duas colunas da posiçao de origem(direita)
			Position sourceT = new Position(source.getRow(), source.getColumn()+3);//pega a posiçao de origem da torre
			Position targetT = new Position(source.getRow(), source.getColumn()+1);//pega a posiçao de destino da torre
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);//remove a torre
			board.placePiece(rook, targetT);//e coloca no destino
			rook.increaseMoveCount();//icrementa a contagem de movimentos	
		}

		//movimeto especial roque lado da rainha
		if(p instanceof King && target.getColumn() == source.getColumn()-2){//se for um rei e o destino foi duas colunas da posiçao de origem(esquerda)
			Position sourceT = new Position(source.getRow(), source.getColumn()-4);//pega a posiçao de origem da torre
			Position targetT = new Position(source.getRow(), source.getColumn()-1);//pega a posiçao de destino da torre
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);//remove a torre
			board.placePiece(rook, targetT);//e coloca no destino
			rook.increaseMoveCount();//icrementa a contagem de movimentos
					
		}
		
		//movimento especial en passant
		if(p instanceof Pawn) {
			if(source.getColumn() != target.getColumn() && capturedPiece == null) {//se a coluna de origem for diferente da de destino e não capturar peça foi um en passant
				Position pawnPosition;
				if(p.getColor() == Color.WHITE) {//se o peão for branco
					pawnPosition = new Position(target.getRow()+1,target.getColumn());//a peça a ser capturada esta abaixo da linha de destino ou seja +1(brancas)pretas
				}else {
					pawnPosition = new Position(target.getRow()-1,target.getColumn());//a peça a ser capturada esta acima da linha de destino ou seja -1(pretas)brancas
				}
				capturedPiece = board.removePiece(pawnPosition);//captura esse peão
				capturedPieces.add(capturedPiece);//adiciona esse peão a lista de peças capturas
				piecesOnTheBoard.remove(capturedPiece);//remove a peça do tabuleiro
				
			}
		}
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {//metodo que desfaz o movimento
		ChessPiece p = (ChessPiece)board.removePiece(target);//remove a peça do destino
		p.decreaseMoveCount();
		board.placePiece(p, source);//coloca a peça na posição de origem
		
		if(capturedPiece != null) {//se capturar um peça ***
			board.placePiece(capturedPiece, target);//volta a peça capturada no destino
			capturedPieces.remove(capturedPiece);//tira a peça da lista de capturadas
			piecesOnTheBoard.add(capturedPiece);//adciona a peça na lista de peças do tabuleiro
		}
		
		//desfazer movimeto especial roque lado do rei
		if(p instanceof King && target.getColumn() == source.getColumn()+2){//se for um rei e o destino foi duas colunas da posiçao de origem(direita)
		Position sourceT = new Position(source.getRow(), source.getColumn()+3);//pega a posiçao de origem da torre
		Position targetT = new Position(source.getRow(), source.getColumn()+3);//pega a posiçao de destino da torre
		ChessPiece rook = (ChessPiece)board.removePiece(targetT);//remove a torre do destino 
		board.placePiece(rook, sourceT);//e coloca  na origem
		rook.decreaseMoveCount();//decrementa a contagem de movimentos	
		}

		//desfazer movimeto especial roque lado da rainha
		if(p instanceof King && target.getColumn() == source.getColumn()-2){//se for um rei e o destino foi duas colunas da posiçao de origem(esquerda)
		Position sourceT = new Position(source.getRow(), source.getColumn()-4);//pega a posiçao de origem da torre
		Position targetT = new Position(source.getRow(), source.getColumn()-1);//pega a posiçao de destino da torre
		ChessPiece rook = (ChessPiece)board.removePiece(targetT);//remove a torre do destio
		board.placePiece(rook, sourceT);//e coloca na origem
		rook.decreaseMoveCount();//decrementa a contagem de movimentos
							
		}
		
		//desfazer movimento especial en passant
		if(p instanceof Pawn) {
			if(source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {//se a coluna de origem for diferente da de destino e capturar uma peça vulneravel ao en passant
				ChessPiece pawn = (ChessPiece)board.removePiece(target);//remove o peão do destino
				Position pawnPosition;
				if(p.getColor() == Color.WHITE) {//se o peão for branco
					pawnPosition = new Position(3,target.getColumn());//a peça a ser devolvida esta na linha 3 da matriz(pretas)
				}else {//se o peão for black
					pawnPosition = new Position(target.getRow()-1,target.getColumn());//a peça a ser devolvida esta na linha 4 da matriz(bracas)
				}
				board.placePiece(pawn, pawnPosition);//devolve o peão para posiçao 3 ou 4 da matriz
				
				capturedPiece = board.removePiece(pawnPosition);//captura esse peão
				//capturedPieces.add(capturedPiece); *** a troca de lista não é necessaria já que houve uma peça capturada
				//piecesOnTheBoard.remove(capturedPiece); ***
				
			}
		}
		
		
	}
	
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) {//testa se existe uma peça na posição
			throw new ChessException("There is no piece on source position");
		}
		if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {//testa se o jogador esta tentando jogar com a peça do adversario 
			throw new ChessException("The chosen piece is not yours");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) {//testa se existe algum movimento possivel da peça
			throw new ChessException("There is no moves for the chosen piece");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {//testa se o destino da peça é possivel
		if(!board.piece(source).possibleMove(target)) {//testa se para peça de origem a posição de destino não é um movimento possivel
			throw new ChessException("The chosen piece can't move to target position");
		}
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; // se o jogador for igual a branco então agora ele vai ser o preto caso contrario vai ser o branco
	}
	
	private Color opponent(Color color) {// metodo que retorna a cor do oponente
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); //filtra a lista
		for (Piece p : list) {
			if(p instanceof King) {
				return(ChessPiece)p;
			}
		}
		throw new IllegalStateException("Ther is no "+ color + "king on the board!");
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();// posição do rei em matriz
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());//filtra as peças do oponente que podem atacar o rei 
		for (Piece p : opponentPieces) {//para cada peça p na minha lista de peças do oponente
			boolean[][] mat = p.possibleMoves();// matriz mat recebe todos os movimentos possiveis da peça adversaria p
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {//se o rei nessa posiçao da matirz for true o rei esta em check
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if(!testCheck(color)) {//se não estiver em check não esta em checkMate
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor()== color).collect(Collectors.toList());//filtra as peças do jogador
		for(Piece p : list) {// para cada peça dessa lista
			boolean[][] mat = p.possibleMoves();//matriz auxiliar com os movimentos possiveis da peça
			for(int i=0;i<board.getRows();i++) {//percorre cada linha 
				for(int j=0; j<board.getColumns();j++) {//percorre cada coluna
					if(mat[i][j]) {//se a peça nessa posiçao tirar do check retorna falso ou seja não esta em check mate
						Position source = ((ChessPiece)p).getChessPosition().toPosition();//pega a posiçao de origem da peça usando downcast (Position é protected)
						Position target = new Position(i, j);//intancia uma position de destino com (i,j) que é um movimento possivel
						Piece capturedPiece = makeMove(source, target); //faz o movimento da origem para o destino
						boolean testCheck = testCheck(color);//variavel auxiliar testa se o rei da cor ainda  esta em check
						undoMove(source, target, capturedPiece);//desfaz todo esse movimeto de peça
						if(!testCheck) {//se não esta em check o movimento tirou o rei do check
							return false;//retorna falso porque não esta em check mate
						}
					}
					
				}
			}
			
		}
		return true;// se retornar true esta em check mate
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { // operção de colocar peça usando as cordenadas do xadrez 
		board.placePiece(piece, new ChessPosition(column, row).toPosition());// converte para posição da matriz
		piecesOnTheBoard.add(piece);// coloca as peças do tabuleiro na lista 
	}
	
	private void initialSetup() {//metodo responsavel por iniciar a partida colocando as peças no tabuleiro
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE,this));//auto referencia 
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE,this));
        
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK,this));//auto referencia  
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK,this));
        
	}
}
