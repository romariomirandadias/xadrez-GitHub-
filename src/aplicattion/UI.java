package aplicattion;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import jogo_xadrez.Cor_da_peca;
import jogo_xadrez.PartidaXadrez;
import jogo_xadrez.PecaXadrez;
import jogo_xadrez.PosicaoXadrez;

public class UI {
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void limparTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static PosicaoXadrez lerPosicaoXadrez(Scanner sc) {
		try {
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosicaoXadrez(coluna, linha);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro ao ler posi��o do xadrez. Valores v�lidos s�o de a1 at� h8.");
		}
	}

	public static void imprimirPartida(PartidaXadrez partidaXadrez, List<PecaXadrez> captura) {
		imprimirTabuleiro(partidaXadrez.getPecas());
		System.out.println();
		imprimirPecasCapturadas(captura);
		System.out.println();
		System.out.println("Turno : " + partidaXadrez.getTurno());
		if (!partidaXadrez.getXequeMate()) {
			System.out.println("Esperando jogador :" + partidaXadrez.getJogadorAtual());
			if (partidaXadrez.getXeque()) {
				System.out.println("XEQUE!!!");
			}
		}
		else {
			System.out.println("XEQUE MATE !!!");
			System.out.println("Vencedor : " + partidaXadrez.getJogadorAtual());
		}
	}

	public static void imprimirTabuleiro(PecaXadrez[][] pecas) {

		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");

			for (int j = 0; j < pecas.length; j++) {
				imprimirPeca(pecas[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h ");
	}

	static void imprimirTabuleiro(PecaXadrez[][] pecas, boolean[][] movimentosPossiveis) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");

			for (int j = 0; j < pecas.length; j++) {
				imprimirPeca(pecas[i][j], movimentosPossiveis[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h ");
	}

	private static void imprimirPeca(PecaXadrez peca, boolean blackground) {
		if (blackground) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}

		if (peca == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			if (peca.getCor() == Cor_da_peca.Branca) {
				System.out.print(ANSI_WHITE + peca + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}

	private static void imprimirPecasCapturadas(List<PecaXadrez> captura) {
		List<PecaXadrez> branca = captura.stream().filter(x -> x.getCor() == Cor_da_peca.Branca)
				.collect(Collectors.toList());
		List<PecaXadrez> preta = captura.stream().filter(x -> x.getCor() == Cor_da_peca.Preta)
				.collect(Collectors.toList());
		System.out.println("Pe�as capturadas :");
		System.out.print("Branca :");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(branca.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print(" Preta:");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(preta.toArray()));
		System.out.print(ANSI_RESET);

	}

}