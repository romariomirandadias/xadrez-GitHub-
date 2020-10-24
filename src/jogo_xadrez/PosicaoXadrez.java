package jogo_xadrez;

import jogo_tabuleiro.Posicao;

public class PosicaoXadrez {
	private char coluna;
	private int linha;

	public PosicaoXadrez(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new XadrezExcecao("Erro instanciando XadrezExcecao.Valores v�lidos s�o de a1 at� h8");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}

	protected Posicao posicionar() {
		return new Posicao(8 - linha, coluna - 'a');
	}

	protected static PosicaoXadrez daPosicao(Posicao posicao) {
		return new PosicaoXadrez((char) ('a' + posicao.getColuna()), 8 - posicao.getLinha());
	}

	@Override
	public String toString() {
		return "" + coluna + linha;
	}
}
