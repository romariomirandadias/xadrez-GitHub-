package jogo_xadrez;

import jogo_tabuleiro.Tabuleiro;

public class PartidaXadrez {
	private Tabuleiro tabuleiro;

	public PartidaXadrez(Tabuleiro tabuleiro) {
		tabuleiro = new Tabuleiro(8, 8);
	}

	public PartidaXadrez() {
		// TODO Auto-generated constructor stub
	}

	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}

}
