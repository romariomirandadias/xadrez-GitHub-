package jogo_xadrez.pecas;

import jogo_tabuleiro.Posicao;
import jogo_tabuleiro.Tabuleiro;
import jogo_xadrez.Cor_da_peca;
import jogo_xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	public Peao(Tabuleiro tabuleiro, Cor_da_peca cor) {
		super(tabuleiro, cor);

	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);
		if (getCor() == Cor_da_peca.Branca) {
			p.atualizarValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.atualizarValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haPeca(p) && getTabuleiro().posicaoExiste(p2)
					&& !getTabuleiro().haPeca(p2) && getContagemMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.atualizarValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.atualizarValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		} else {
			p.atualizarValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.atualizarValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haPeca(p) && getTabuleiro().posicaoExiste(p2)
					&& !getTabuleiro().haPeca(p2) && getContagemMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.atualizarValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.atualizarValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}
		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}
}