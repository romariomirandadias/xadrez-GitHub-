package jogo_xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jogo_tabuleiro.Peca;
import jogo_tabuleiro.Posicao;
import jogo_tabuleiro.Tabuleiro;
import jogo_xadrez.pecas.Bispo;
import jogo_xadrez.pecas.Cavalo;
import jogo_xadrez.pecas.Peao;
import jogo_xadrez.pecas.Rainha;
import jogo_xadrez.pecas.Rei;
import jogo_xadrez.pecas.Torre;;

public class PartidaXadrez {
	private int turno;
	private Cor_da_peca jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean xeque;
	private boolean xequeMate;
	private PecaXadrez vulneravelEnPassant;
	private PecaXadrez promocao;

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

	public boolean getXequeMate() {
		return xequeMate;
	}

	public PecaXadrez getVulneravelEnPassant() {
		return vulneravelEnPassant;
	}

	public PecaXadrez getPromocao() {
		return promocao;
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

		PecaXadrez pecaMovida = (PecaXadrez) tabuleiro.peca(destino);

		// Jogada especial promoção

		promocao = null;
		if (pecaMovida instanceof Peao) {
			if ((pecaMovida.getCor() == Cor_da_peca.Branca && destino.getLinha() == 0)
					|| (pecaMovida.getCor() == Cor_da_peca.Preta && destino.getLinha() == 7)) {
				promocao = (PecaXadrez) tabuleiro.peca(destino);
				promocao = trocarPromocaoPeca("R");
			}
		}

		xeque = (testarXeque(oponente(jogadorAtual))) ? true : false;

		if (testarXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		} else {
			proximoTurno();
		}

		// movimento especial en passant

		if (pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() - 2)
				|| destino.getLinha() == origem.getLinha() + 2) {
			vulneravelEnPassant = pecaMovida;
		} else {
			vulneravelEnPassant = null;
		}

		proximoTurno();
		return (PecaXadrez) capturarPeca;
	}

	public PecaXadrez trocarPromocaoPeca(String tipo) {
		if (promocao == null) {
			throw new IllegalStateException("Não há peça a ser promovida");
		}
		if (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("R") && !tipo.equals("T")) {
        throw new InvalidParameterException("Valor inválido para promoção"); 
		}
	Posicao pos=promocao.mostrarPosicaoXadrez().posicionar();
	Peca p=tabuleiro.removerPeca(pos);
	pecasNoTabuleiro.remove(p);
	
	PecaXadrez novaPeca=novaPeca(tipo,promocao.getCor());
	tabuleiro.colocarPeca(novaPeca, pos);
	pecasNoTabuleiro.add(novaPeca);
	
	return novaPeca;
	}

	private PecaXadrez novaPeca (String tipo,Cor_da_peca cor ) {
		if (tipo.equals("B")) return new Bispo (tabuleiro,cor);
		if (tipo.equals("C")) return new Cavalo (tabuleiro,cor);
		if (tipo.equals("R")) return new Rainha (tabuleiro,cor);
		return new Torre  (tabuleiro,cor);
	}
	
	private Peca moverPeca(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(origem);
		p.incrementarContagemMovimentos();
		Peca capturarPeca = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		proximoTurno();
		if (capturarPeca != null) {
			pecasNoTabuleiro.remove(capturarPeca);
			pecasCapturadas.add(capturarPeca);
		}

		// Jogada especial de roque ao lado do Rei

		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(origemT);
			tabuleiro.colocarPeca(torre, destinoT);
			torre.incrementarContagemMovimentos();
		}

		// Jogada especial de roque ao lado da Rainha

		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(origemT);
			tabuleiro.colocarPeca(torre, destinoT);
			torre.incrementarContagemMovimentos();
		}

		// movimento especial en passant

		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && capturarPeca == null) {
				Posicao posicaoPeao;
				if (p.getCor() == Cor_da_peca.Branca) {
					posicaoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				} else {
					posicaoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				capturarPeca = tabuleiro.removerPeca(posicaoPeao);
				pecasCapturadas.add(capturarPeca);
				pecasNoTabuleiro.remove(capturarPeca);
			}

		}

		return capturarPeca;
	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca capturarPeca) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(destino);
		p.decrementarContagemMovimentos();
		tabuleiro.colocarPeca(p, origem);
		if (capturarPeca != null) {
			tabuleiro.colocarPeca(capturarPeca, destino);
			pecasCapturadas.remove(capturarPeca);
			pecasNoTabuleiro.add(capturarPeca);
		}

		// Jogada especial de roque ao lado do Rei

		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.decrementarContagemMovimentos();
		}

		// Jogada especial de roque ao lado da Rainha

		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.decrementarContagemMovimentos();
		}

		// Movimento especial en Passant

		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && capturarPeca == vulneravelEnPassant) {
				PecaXadrez peao = (PecaXadrez) tabuleiro.removerPeca(destino);
				Posicao posicaoPeao;
				if (p.getCor() == Cor_da_peca.Branca) {
					posicaoPeao = new Posicao(3, destino.getColuna());
				} else {
					posicaoPeao = new Posicao(4, destino.getColuna());
				}
				tabuleiro.colocarPeca(peao, posicaoPeao);
			}
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
		Posicao posicaoDoRei = rei(cor).mostrarPosicaoXadrez().posicionar();
		List<Peca> pecasOponentes = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasOponentes) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[posicaoDoRei.getLinha()][posicaoDoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testarXequeMate(Cor_da_peca cor) {
		if (!testarXeque(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaXadrez) p).mostrarPosicaoXadrez().posicionar();
						Posicao destino = new Posicao(i, j);
						Peca capturarPeca = moverPeca(origem, destino);
						boolean testarXeque = testarXeque(cor);
						desfazerMovimento(origem, destino, capturarPeca);
						if (!testarXeque) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).posicionar());
		pecasNoTabuleiro.add(peca);
	}

	private void iniciarPartida() {
		colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor_da_peca.Branca));
		colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor_da_peca.Branca));
		colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor_da_peca.Branca));
		colocarNovaPeca('d', 1, new Rainha(tabuleiro, Cor_da_peca.Branca));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor_da_peca.Branca, this));
		colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor_da_peca.Branca));
		colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor_da_peca.Branca));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor_da_peca.Branca));
		colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor_da_peca.Branca, this));
		colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor_da_peca.Branca, this));
		colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor_da_peca.Branca, this));
		colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor_da_peca.Branca, this));
		colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor_da_peca.Branca, this));
		colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor_da_peca.Branca, this));
		colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor_da_peca.Branca, this));
		colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor_da_peca.Branca, this));
		colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor_da_peca.Preta));
		colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor_da_peca.Preta));
		colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor_da_peca.Preta));
		colocarNovaPeca('d', 8, new Rainha(tabuleiro, Cor_da_peca.Preta));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor_da_peca.Preta, this));
		colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor_da_peca.Preta));
		colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor_da_peca.Preta));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor_da_peca.Preta));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor_da_peca.Preta, this));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor_da_peca.Preta, this));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor_da_peca.Preta, this));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor_da_peca.Preta, this));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor_da_peca.Preta, this));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor_da_peca.Preta, this));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor_da_peca.Preta, this));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor_da_peca.Preta, this));
	}
}
