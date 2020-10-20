package jogo_xadrez;

import jogo_tabuleiro.Peca;
import jogo_tabuleiro.Posicao;
import jogo_tabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {

	private Cor_da_peca cor;

	public PecaXadrez( Tabuleiro tabuleiro , Cor_da_peca cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor_da_peca getCor() {
		return cor;
	}

	protected boolean haPecaAdversaria(Posicao posicao) {
		PecaXadrez p=(PecaXadrez) getTabuleiro().peca(posicao);
		return p !=null && p.getCor() !=cor;
	}
}