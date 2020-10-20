package jogo_xadrez;

import jogo_tabuleiro.Tabuleiro;
import jogo_tabuleiro.Peca;

public abstract class PecaXadrez extends Peca {

	private Cor_da_peca cor;

	public PecaXadrez( Tabuleiro tabuleiro , Cor_da_peca cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor_da_peca getCor() {
		return cor;
	}

	
}