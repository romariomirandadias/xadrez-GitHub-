package aplicattion;

import java.util.InputMismatchException;
import java.util.Scanner;

import jogo_xadrez.PartidaXadrez;
import jogo_xadrez.PecaXadrez;
import jogo_xadrez.PosicaoXadrez;
import jogo_xadrez.XadrezExcecao;

public class ProgramXadrez {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
	
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		UI.imprimirTabuleiro(partidaXadrez.getPecas());

		while (true) {
			try {
				UI.limparTela();
			UI.imprimirPartida(partidaXadrez);
			System.out.println();
			System.out.print("Origem: ");
			PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);

			boolean [][] movimentosPossiveis=partidaXadrez.movimentosPossiveis(origem);
			UI.limparTela();
			UI.imprimirTabuleiro(partidaXadrez.getPecas(),movimentosPossiveis);
			
			System.out.println();
			System.out.print("Destino: ");
			PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);

			PecaXadrez capturarPeca = partidaXadrez.realizarMovimentoXadrez(origem, destino);
			System.out.println(capturarPeca);
			}
			catch (XadrezExcecao e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
	}
		
}
}