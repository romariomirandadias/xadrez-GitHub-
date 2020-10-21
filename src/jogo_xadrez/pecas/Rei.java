package jogo_xadrez.pecas;

import jogo_tabuleiro.Posicao;
import jogo_tabuleiro.Tabuleiro;
import jogo_xadrez.Cor_da_peca;
import jogo_xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

	public Rei(Tabuleiro tabuleiro, Cor_da_peca cor) {
		super(tabuleiro, cor);
	}
//King->Rei->K
	@Override
	public String toString() {
		return "K";
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaXadrez p=(PecaXadrez)getTabuleiro().peca(posicao);
		return p==null || p.getCor()!=getCor();
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean [][] mat= new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p=new Posicao(0,0);
		
		//acima
		
		p.atualizarValores(posicao.getLinha() - 1, posicao.getColuna());
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//abaixo
		
		p.atualizarValores(posicao.getLinha() + 1, posicao.getColuna());
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//esquerda
		
		p.atualizarValores(posicao.getLinha(), posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//direita
		
		p.atualizarValores(posicao.getLinha(), posicao.getColuna() + 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//noroeste
		
		p.atualizarValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//nordeste
		
		p.atualizarValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//sudoeste
		
		p.atualizarValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//sudeste
		
		p.atualizarValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		return mat;
	}
}
