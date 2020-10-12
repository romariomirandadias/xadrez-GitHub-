package aplicattion;

import jogo_xadrez.PartidaXadrez;

public class Xadrez {

	public static void main(String[] args) {
		PartidaXadrez partidaXadrez = new PartidaXadrez();
UI.imprimirTabuleiro(partidaXadrez.getPecas());
	}

}
