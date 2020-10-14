package jogo_xadrez;

import jogo_tabuleiro.Tabuleiro;
import jogo_xadrez.pecas.Rei;
import jogo_xadrez.pecas.Torre;;

public class PartidaXadrez {

	private Tabuleiro tabuleiro;

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		iniciarPartida();
	}

	public PartidaXadrez(Tabuleiro tabuleiro) {
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
private void colocarNovaPeca(char coluna ,int linha ,PecaXadrez peca) {
	tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna,linha).posicionar());
}
	private void iniciarPartida() {
		colocarNovaPeca('b',6,new Torre(tabuleiro, Cor_da_peca.Branca));
		colocarNovaPeca('e',8,new Rei(tabuleiro, Cor_da_peca.Preta));
		colocarNovaPeca('e',1,new Rei(tabuleiro, Cor_da_peca.Preta));
	}
}