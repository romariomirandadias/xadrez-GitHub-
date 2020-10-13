package jogo_xadrez.pecas;

import jogo_tabuleiro.Tabuleiro;
import jogo_xadrez.Cor_da_peca;
import jogo_xadrez.PecaXadrez;

public class Torre extends PecaXadrez {

	public Torre(Tabuleiro tabuleiro, Cor_da_peca cor) {
		super(tabuleiro, cor);
	}

	//Rook-> Torre -> T
	@Override
	public String toString() {
		return "R";
	}
}
