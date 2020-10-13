package jogo_xadrez.pecas;

import jogo_tabuleiro.Tabuleiro;
import jogo_xadrez.Cor_da_peca;
import jogo_xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

	public Rei(Tabuleiro tabuleiro, Cor_da_peca cor) {
		super(tabuleiro, cor);
	}
//King->Rei->K
	@Override
	public String toString() {
		return "K";
	}
}
