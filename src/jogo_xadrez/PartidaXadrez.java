package jogo_xadrez;

import jogo_tabuleiro.Peca;
import jogo_tabuleiro.Posicao;
import jogo_tabuleiro.Tabuleiro;
import jogo_xadrez.pecas.Rei;
import jogo_xadrez.pecas.Torre;;

public class PartidaXadrez {

private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		iniciarPartida();
	}
	
	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}

	public PecaXadrez realizarMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.posicionar();
		Posicao destino = posicaoDestino.posicionar();
		validarPosicaoOrigem(origem);
		Peca capturarPeca = moverPeca(origem, destino);
		return (PecaXadrez)capturarPeca;
	}

	private Peca moverPeca(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca  capturarPeca= tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		return capturarPeca;
	}

	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.haPeca(posicao)) {
			throw new XadrezExcecao("N�o h� uma pe�a na posi��o de origem");
		}
	}

	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).posicionar());
	}
	
	private void iniciarPartida() {
		colocarNovaPeca('c', 1, new Torre(tabuleiro, Cor_da_peca.Branca));
		colocarNovaPeca('c', 2, new Torre(tabuleiro, Cor_da_peca.Branca));
		colocarNovaPeca('d', 2, new Torre(tabuleiro, Cor_da_peca.Branca));
		colocarNovaPeca('e', 2, new Torre(tabuleiro, Cor_da_peca.Branca));
		colocarNovaPeca('e', 1, new Torre(tabuleiro, Cor_da_peca.Branca));
		colocarNovaPeca('d', 1, new Rei(tabuleiro, Cor_da_peca.Branca));
		colocarNovaPeca('c', 7, new Torre(tabuleiro, Cor_da_peca.Preta));
		colocarNovaPeca('c', 8, new Torre(tabuleiro, Cor_da_peca.Preta));
		colocarNovaPeca('d', 7, new Torre(tabuleiro, Cor_da_peca.Preta));
		colocarNovaPeca('e', 7, new Torre(tabuleiro, Cor_da_peca.Preta));
		colocarNovaPeca('e', 8, new Torre(tabuleiro, Cor_da_peca.Preta));
		colocarNovaPeca('d', 8, new Rei(tabuleiro, Cor_da_peca.Preta));
	}
	}
