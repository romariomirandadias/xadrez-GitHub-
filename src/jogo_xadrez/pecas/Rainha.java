package jogo_xadrez.pecas;

import jogo_tabuleiro.Posicao;
import jogo_tabuleiro.Tabuleiro;
import jogo_xadrez.Cor_da_peca;
import jogo_xadrez.PecaXadrez;

public class Rainha extends PecaXadrez {

	public Rainha(Tabuleiro tabuleiro, Cor_da_peca cor) {
		super(tabuleiro, cor);
	}

	
	@Override
	public String toString() {
		return "R";
	}
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean [][] mat= new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p=new Posicao(0,0);
		
		//acima
		
		p.atualizarValores(posicao.getLinha() - 1, posicao.getColuna());
		while(getTabuleiro().posicaoExiste( p) && !getTabuleiro().haPeca(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
			p.setLinha(p.getLinha() -1);
		}
		
		if(getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//esquerda
		p.atualizarValores(posicao.getLinha(), posicao.getColuna() - 1);
		while(getTabuleiro().posicaoExiste( p) && !getTabuleiro().haPeca(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
			p.setColuna(p.getColuna() -1);
		}
		
		if(getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
	
		//direita
		
		p.atualizarValores(posicao.getLinha(), posicao.getColuna() + 1);
		while(getTabuleiro().posicaoExiste( p) && !getTabuleiro().haPeca(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
			p.setColuna(p.getColuna() + 1);
		}
		
		if(getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		// abaixo
		
		p.atualizarValores(posicao.getLinha() + 1, posicao.getColuna());
		while(getTabuleiro().posicaoExiste( p) && !getTabuleiro().haPeca(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
			p.setLinha(p.getLinha() + 1);
		}
		
		if(getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//noroeste
		
				p.atualizarValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
				while(getTabuleiro().posicaoExiste( p) && !getTabuleiro().haPeca(p)) {
					mat[p.getLinha()][p.getColuna()]=true;
					p.atualizarValores(p.getLinha() -1,p.getColuna() - 1);
				}
				
				if(getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
					mat[p.getLinha()][p.getColuna()]=true;
				}
				
				//nordeste
				p.atualizarValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
				while(getTabuleiro().posicaoExiste( p) && !getTabuleiro().haPeca(p)) {
					mat[p.getLinha()][p.getColuna()]=true;
					p.atualizarValores(p.getColuna() -1,p.getColuna() + 1);
				}
				
				if(getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
					mat[p.getLinha()][p.getColuna()]=true;
				}
			
				//sudeste
				
				p.atualizarValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
				while(getTabuleiro().posicaoExiste( p) && !getTabuleiro().haPeca(p)) {
					mat[p.getLinha()][p.getColuna()]=true;
					p.atualizarValores(p.getLinha() + 1,p.getColuna() + 1);
				}
				
				if(getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
					mat[p.getLinha()][p.getColuna()]=true;
				}
				
				// sudoeste
				
				p.atualizarValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
				while(getTabuleiro().posicaoExiste( p) && !getTabuleiro().haPeca(p)) {
					mat[p.getLinha()][p.getColuna()]=true;
					p.atualizarValores(p.getLinha() + 1,p.getColuna() - 1);
				}
				
				if(getTabuleiro().posicaoExiste(p) && haPecaAdversaria(p)) {
					mat[p.getLinha()][p.getColuna()]=true;
				}
		
	return mat;
	}
}

