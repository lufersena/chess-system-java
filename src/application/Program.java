package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		ChessMatch chessMatch = new ChessMatch();
		Scanner sc = new Scanner(System.in);
		List<ChessPiece>captured = new ArrayList<>();

		while (!chessMatch.getCheckMate()) {//enquanto a partida nao estiver em check mate a pardida continua
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean [][] possibleMoves = chessMatch.possibleMoves(source);// matriz recebe a partida de xadrez com os possiveis movimentos dessa posição de origem
				UI.clearScreen();//limpa a tela
				UI.printBoard(chessMatch.getPieces(), possibleMoves);//sobrecarga do metodo de imprimir com os possiveis movimentos

				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);

				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				if(capturedPiece != null) {
					captured.add(capturedPiece);
				}
				if(chessMatch.getPromoted()!= null) {// promoted não for nulo usuario digita a nova peça por isso replacePromotedPiece recebe Q 
					System.out.print("Enter piece for promotion (B/N/R/Q)");
					String type = sc.nextLine().toUpperCase();
					if(!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
						System.out.print("Invalid value!! Enter piece for promotion (B/N/R/Q):");
						 type = sc.nextLine().toUpperCase();
					}
					chessMatch.replacePromotedPiece(type);// troca a Q para a nova peça
				}
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}

		}
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
	}

}
