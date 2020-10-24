package jogo_xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jogo_tabuleiro.Peca;
import jogo_tabuleiro.Posicao;
import jogo_tabuleiro.Tabuleiro;
import jogo_xadrez.pecas.Rei;
import jogo_xadrez.pecas.Torre;;

public class PartidaXadrez {
	private int turno;
	private Cor_da_peca jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean xeque;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor_da_peca.Branca;
		iniciarPartida();
	}

	public int getTurno() {
		return turno;
	}

	public Cor_da_peca getJogadorAtual() {
		return jogadorAtual;
	}

	public boolean getXeque() {
		return xeque;
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

	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.posicionar();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}

	public PecaXadrez realizarMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.posicionar();
		Posicao destino = posicaoDestino.posicionar();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca capturarPeca = moverPeca(origem, destino);

		if (testarXeque(jogadorAtual)) {
			desfazerMovimento(origem, destino, capturarPeca);
			throw new XadrezExcecao("Você não pode se colocar em cheque");
		}

		xeque = (testarXeque(oponente(jogadorAtual))) ? true : false;
		proximoTurno();
		return (PecaXadrez) capturarPeca;
	}

	private Peca moverPeca(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca capturarPeca = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		proximoTurno();
		if (capturarPeca != null) {
			pecasNoTabuleiro.remove(capturarPeca);
			pecasCapturadas.add(capturarPeca);
		}
		return capturarPeca;
	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca capturarPeca) {
		Peca p = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, origem);
		if (capturarPeca != null) {
			tabuleiro.colocarPeca(capturarPeca, destino);
			pecasCapturadas.remove(capturarPeca);
			pecasNoTabuleiro.add(capturarPeca);
		}

	}

	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.haPeca(posicao)) {
			throw new XadrezExcecao("Não há uma peça na posição de origem");
		}

		if (jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezExcecao("A peça não é sua");
		}

		if (!tabuleiro.peca(posicao).haMovimentoPossivel()) {
			throw new XadrezExcecao("Não há movimentos possíveis para a peça escolhida");
		}
	}

	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new XadrezExcecao("A peça escolhida não pode se mover para a posição de destino ");
		}
	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor_da_peca.Branca) ? Cor_da_peca.Preta : Cor_da_peca.Branca;
	}

	private Cor_da_peca oponente(Cor_da_peca cor) {
		return (cor == Cor_da_peca.Branca) ? Cor_da_peca.Preta : Cor_da_peca.Branca;
	}

	private PecaXadrez rei(Cor_da_peca cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalStateException("Este não é " + cor + "rei do tabuleiro");
	}

	private boolean testarXeque(Cor_da_peca cor) {
		Posicao posicaoDoRei=rei(cor).mostrarPosicaoXadrez().posicionar();
		List<Peca> pecasOponentes=pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == oponente(cor)).collect(Collectors.toList());
		for(Peca p:pecasOponentes) {
			boolean [][] mat=p.movimentosPossiveis();
			if(mat[posicaoDoRei.getLinha()][posicaoDoRei.getColuna()]) {
			return true;	
			}
		}
	return false;
	}
	
	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).posicionar());
		pecasNoTabuleiro.add(peca);
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
