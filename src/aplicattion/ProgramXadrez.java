package aplicattion;

import java.util.Scanner;

import jogo_xadrez.PartidaXadrez;
import jogo_xadrez.PecaXadrez;
import jogo_xadrez.PosicaoXadrez;

public class ProgramXadrez {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
	
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		UI.imprimirTabuleiro(partidaXadrez.getPecas());

		while (true) {
			UI.imprimirTabuleiro(partidaXadrez.getPecas());
			System.out.println();
			System.out.print("Origem: ");
			PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);

			System.out.println();
			System.out.print("Destino: ");
			PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);

			PecaXadrez capturarPeca = partidaXadrez.realizarMovimentoXadrez(origem, destino);
			System.out.println(capturarPeca);
	}
		
}
}