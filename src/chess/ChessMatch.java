package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	
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
		nextTurn();
		return (ChessPiece)capturedPiece;//peça capturada recebendo um downcast para ChessPiece
	}
	
	private Piece makeMove(Position source, Position target) {// metodo que faz o movimento da peça 
		Piece p = board.removePiece(source);//p recebe a peça na posiçao de origem 
		Piece capturedPiece = board.removePiece(target);// capturedPiece recebe a possivel peça de destino
		board.placePiece(p, target);//coloca a peça de origem na posiçao de destino
		return capturedPiece;
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
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { // operção de colocar peça usando as cordenadas do xadrez 
		board.placePiece(piece, new ChessPosition(column, row).toPosition());// converte para posição da matriz
	}
	
	private void initialSetup() {//metodo responsavel por iniciar a partida colocando as peças no tabuleiro
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
