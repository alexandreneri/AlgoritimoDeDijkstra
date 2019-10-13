
public class Aresta {
	
	private int pesoDist;
	private int pesoTemp;
	private Vertice origem;
	private Vertice destino;
	private boolean visitado = false;
		
	public Aresta(Vertice origem, int pesoDist, int pesoTemp, Vertice destino) {
		this.setPesoDist(pesoDist);
		this.setPesoTemp(pesoTemp);
		this.setOrigem(origem);
		this.setDestino(destino);
	}
	
	public boolean isVisitado() {
		return visitado;
	}

	public void setVisitado(boolean visitado) {
		this.visitado = visitado;
	}
	
	public int getPesoDist() {
		return pesoDist;
	}

	public void setPesoDist(int pesoDist) {
		this.pesoDist = pesoDist;
	}

	public int getPesoTemp() {
		return pesoTemp;
	}

	public void setPesoTemp(int pesoTemp) {
		this.pesoTemp = pesoTemp;
	}

	public Vertice getOrigem() {
		return origem;
	}

	public void setOrigem(Vertice origem) {		
		this.origem = origem;
	}

	public Vertice getDestino() {
		return destino;
	}

	public void setDestino(Vertice destino) {		
		this.destino = destino;
	}
	
	@Override
	public String toString() {
		String s = " ";
		s+= this.getOrigem().getNome() + this.getDestino().getNome();
		return s;
	}

}