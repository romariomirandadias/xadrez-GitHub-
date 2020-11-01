package jogo_xadrez.pecas;

import jogo_tabuleiro.Posicao;
import jogo_tabuleiro.Tabuleiro;
import jogo_xadrez.Cor_da_peca;
import jogo_xadrez.PecaXadrez;

public class Cavalo extends PecaXadrez {

	public Cavalo(Tabuleiro tabuleiro, Cor_da_peca cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "C";
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaXadrez p=(PecaXadrez)getTabuleiro().peca(posicao);
		return p==null || p.getCor()!=getCor();
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean [][] mat= new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p=new Posicao(0,0);
		
		
		
		p.atualizarValores(posicao.getLinha() - 1, posicao.getColuna() - 2);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		
		
		p.atualizarValores(posicao.getLinha() - 2, posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		
		
		p.atualizarValores(posicao.getLinha() - 2, posicao.getColuna() + 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		
		
		p.atualizarValores(posicao.getLinha() - 1, posicao.getColuna() + 2);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		
		
		p.atualizarValores(posicao.getLinha() + 1, posicao.getColuna() + 2);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		
		
		p.atualizarValores(posicao.getLinha() + 2, posicao.getColuna() + 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		
		
		p.atualizarValores(posicao.getLinha() + 2, posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		
		
		p.atualizarValores(posicao.getLinha() + 1, posicao.getColuna() - 2);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		return mat;
	}
}
