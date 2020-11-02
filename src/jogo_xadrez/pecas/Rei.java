package jogo_xadrez.pecas;

import jogo_tabuleiro.Posicao;
import jogo_tabuleiro.Tabuleiro;
import jogo_xadrez.Cor_da_peca;
import jogo_xadrez.PartidaXadrez;
import jogo_xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

	private PartidaXadrez partidaXadrez;

	public Rei(Tabuleiro tabuleiro, Cor_da_peca cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

//King->Rei->K
	@Override
	public String toString() {
		return "K";
	}

	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}

	private boolean testarRoqueTorre(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && getContagemMovimentos() == 0;
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
		
		//Jogada especial de Roque
		
		if(getContagemMovimentos()==0 && !partidaXadrez.getXeque()) {
			//jogada especial de Roque ao lado do Rei
			Posicao posTorre1=new Posicao(posicao.getLinha(),posicao.getColuna()+ 3);
			if(testarRoqueTorre(posTorre1)) {
				Posicao p1=new Posicao(posicao.getLinha(),posicao.getColuna() +1);
				Posicao p2=new Posicao(posicao.getLinha(),posicao.getColuna() +2);
				if(getTabuleiro().peca(p1)==null && getTabuleiro().peca(p2)==null) {
					mat[posicao.getLinha()][posicao.getColuna() + 2]= true;
				}
			}
		     
			//jogada especial de Roque ao lado da Rainha
			Posicao posTorre2=new Posicao(posicao.getLinha(),posicao.getColuna() - 4);
			if(testarRoqueTorre(posTorre2)) {
			Posicao p1=new Posicao(posicao.getLinha(),posicao.getColuna() - 1);
			Posicao p2=new Posicao(posicao.getLinha(),posicao.getColuna() - 2);
			Posicao p3=new Posicao(posicao.getLinha(),posicao.getColuna() - 3);
			if(getTabuleiro().peca(p1)==null && getTabuleiro().peca(p2)==null && getTabuleiro().peca(p3)==null) {
				mat[posicao.getLinha()][posicao.getColuna() - 2]= true;
			}
		   }
		}
		
		return mat;
	}
}
