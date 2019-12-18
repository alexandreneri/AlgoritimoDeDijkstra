
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Grafo {
	
	private ArrayList<Aresta> arestas = new ArrayList<Aresta>();
	private ArrayList<Vertice> vertices = new ArrayList<Vertice>();
	private boolean hasCycle = false;
	
	public void clearLists(){
		this.arestas.clear();
		this.vertices.clear();
		this.setHasCycle(false);
	}

	public boolean isHasCycle() {
		return hasCycle;
	}

	public void setHasCycle(boolean hasCycle) {
		this.hasCycle = hasCycle;
	}

	public void addAresta(String origem, int pesoDist, int pesoTemp, String destino){
		int i,j,k;
		
		i = this.addVertice(origem);
		j = this.addVertice(destino);
		
		Aresta a = new Aresta(this.vertices.get(i), pesoDist, pesoTemp, this.vertices.get(j));	
	
		this.arestas.add(a);
		k = this.arestas.size();
		
		this.vertices.get(i).addIncidentes(this.arestas.get(k-1));
		this.vertices.get(j).addIncidentes(this.arestas.get(k-1));
	}
			
	public void setArestas(ArrayList<Aresta> arestas) {
		this.clearLists();
		
		for (int i=0; i<arestas.size() ; i++)
			this.addAresta(arestas.get(i).getOrigem().getNome(), 
					       arestas.get(i).getPesoDist(), 
				           arestas.get(i).getPesoTemp(), 
						   arestas.get(i).getDestino().getNome() );
	}

	public void setVertices(ArrayList<Vertice> vertices) {
		this.clearLists();
		
		for (int i=0; i<vertices.size() ; i++){

			if(this.posicaoVertice(vertices.get(i).getNome())==this.vertices.size()){
				
				for(int j=0; j<vertices.get(i).getIncidentes().size(); j++){

					if ( (vertices.get(i).getNome().equals(vertices.get(i).getIncidentes().get(j).getOrigem().getNome())) &&
							(this.posicaoVertice(vertices.get(i).getIncidentes().get(j).getDestino().getNome())!=this.vertices.size()) ){

						this.addAresta(vertices.get(i).getIncidentes().get(j).getOrigem().getNome(), 
								       vertices.get(i).getIncidentes().get(j).getPesoDist(), 
							        vertices.get(i).getIncidentes().get(j).getPesoTemp(), 
										vertices.get(i).getIncidentes().get(j).getDestino().getNome());
					
					}else if ( (vertices.get(i).getNome().equals(vertices.get(i).getIncidentes().get(j).getDestino().getNome())) &&
							(this.posicaoVertice(vertices.get(i).getIncidentes().get(j).getOrigem().getNome())!=this.vertices.size()) ){

						this.addAresta(vertices.get(i).getIncidentes().get(j).getOrigem().getNome(), 
							       vertices.get(i).getIncidentes().get(j).getPesoDist(), 
						        vertices.get(i).getIncidentes().get(j).getPesoTemp(), 
									vertices.get(i).getIncidentes().get(j).getDestino().getNome());
					}
				}
				this.addVertice(vertices.get(i).getNome());
			}
		}
	}

	public int addVertice(String nome){
		int i= this.posicaoVertice(nome); 
		
		if(i==this.vertices.size()){
			this.vertices.add(new Vertice(nome));
			return (this.vertices.size() - 1);
		}
		
		return i;
	}
	
	public void limparVerticesPai(){
		for(int i=0; i<this.getVertices().size() ;i++)
			this.getVertices().get(i).setPai(null);
	}
	
	public void limparVerticeVisitado(){
		for(int i=0; i<this.getVertices().size() ;i++)
			this.getVertices().get(i).setVisitado(false);
	}
	
	public void limparArestaVisitada(){
		for(int i=0; i<this.getArestas().size() ;i++)
			this.getArestas().get(i).setVisitado(false);
	}
	
	public void imprimeArvore(){
		for (int i=0; i<arestas.size();i++)
			System.out.print(this.arestas.get(i).getOrigem().getNome() + this.arestas.get(i).getDestino().getNome() + " - Peso Dist " + this.arestas.get(i).getPesoDist() + " - Peso Temp " + this.arestas.get(i).getPesoTemp() +  " | ");
		System.out.println();
	}

	public ArrayList<Vertice> getVertices() {
		return vertices;
	}
	
	public int posicaoVertice(String nome){
		int i;
		
		for (i=0; i<this.vertices.size() ; i++)
			if (this.vertices.get(i).getNome().equals(nome))
				return i;
		
		return this.vertices.size();
	
	}
	
	public Vertice acharVertice(String nome){
		return this.vertices.get(this.posicaoVertice(nome));
	}
	
	public Aresta acharAresta(Vertice vet1, Vertice vet2){
		for(int i=0; i<this.arestas.size();i++){
			if( ((this.arestas.get(i).getOrigem().getNome().equals(vet1.getNome())) &&
				(this.arestas.get(i).getDestino().getNome().equals(vet2.getNome()))) ||
				((this.arestas.get(i).getOrigem().getNome().equals(vet2.getNome())) &&
				(this.arestas.get(i).getDestino().getNome().equals(vet1.getNome()))) ){
				return this.arestas.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<Aresta> getArestas() {
		return arestas;
	}

	
	//----------------------DIJKSTRA- Temp ------------------------------------------
		
	    public ArrayList<Vertice> encontrarMenorCaminhoDijkstraTemp(Vertice v1,Vertice v2) {

	     	ArrayList<Vertice> menorCaminho = new ArrayList<Vertice>();

	        Vertice verticeCaminho;
	        Vertice atual;
	        Vertice vizinho;
	        Aresta ligacao;

	        ArrayList<Vertice> naoVisitados = new ArrayList<Vertice>();

	        menorCaminho.add(v1);

	        for (int i = 0; i < this.getVertices().size(); i++) {
	            if (this.getVertices().get(i).getNome().equals(v1.getNome()))
	                this.getVertices().get(i).setDistancia(0);
	            else
	                this.getVertices().get(i).setDistancia(9999);
	            naoVisitados.add(this.getVertices().get(i));
	        }

	        Collections.sort(naoVisitados);

	       while (!naoVisitados.isEmpty()) {
	          
	            atual = naoVisitados.get(0);
	          
	            for (int i = 0; i < atual.getVizinhos().size(); i++) {
	            	
	                vizinho = atual.getVizinhos().get(i);
	                
	                if (!vizinho.isVisitado()) {
	                	
	                	ligacao = this.acharAresta(atual,vizinho);
	                    if (vizinho.getDistancia() > (atual.getDistancia() + ligacao.getPesoTemp())) {
	                        vizinho.setDistancia(atual.getDistancia()
	                                        + ligacao.getPesoTemp());
	                        vizinho.setPai(atual);

	                        if (vizinho == v2) {
	                            menorCaminho.clear();
	                            verticeCaminho = vizinho;
	                            menorCaminho.add(vizinho);
	                            while (verticeCaminho.getPai() != null) {
	                                menorCaminho.add(verticeCaminho.getPai());
	                                verticeCaminho = verticeCaminho.getPai();

	                            }
	                          
	                            Collections.sort(menorCaminho);
	                        }
	                    }
	                }
	            }
	        
	            atual.setVisitado(true);
	            naoVisitados.remove(atual);

	            Collections.sort(naoVisitados);
	        }
	        this.limparVerticesPai();
	        return menorCaminho;
	    }


		//----------------------DIJKSTRA- Dist ------------------------------------------
			
		    public ArrayList<Vertice> encontrarMenorCaminhoDijkstraDist(Vertice v1,Vertice v2) {

		    	ArrayList<Vertice> menorCaminho = new ArrayList<Vertice>();
		        Vertice verticeCaminho;
		        Vertice atual;
     	        Vertice vizinho;
		        Aresta ligacao;
		        ArrayList<Vertice> naoVisitados = new ArrayList<Vertice>();
		        menorCaminho.add(v1);

		        for (int i = 0; i < this.getVertices().size(); i++) {
		            
		            if (this.getVertices().get(i).getNome().equals(v1.getNome()))
		                this.getVertices().get(i).setDistancia(0);
		            else
		                this.getVertices().get(i).setDistancia(9999);
		                naoVisitados.add(this.getVertices().get(i));
		        }

		        Collections.sort(naoVisitados);

		        while (!naoVisitados.isEmpty()) {
		            
		            atual = naoVisitados.get(0);
		            for (int i = 0; i < atual.getVizinhos().size(); i++) {
		   	
		                vizinho = atual.getVizinhos().get(i);
		                
		                if (!vizinho.isVisitado()) {
		                	
		                	ligacao = this.acharAresta(atual,vizinho);
		                    if (vizinho.getDistancia() > (atual.getDistancia() + ligacao.getPesoDist())) {
		                        vizinho.setDistancia(atual.getDistancia()
		                                        + ligacao.getPesoDist());
		                        vizinho.setPai(atual);

		                        if (vizinho == v2) {
		                            menorCaminho.clear();
		                            verticeCaminho = vizinho;
		                            menorCaminho.add(vizinho);
		                            while (verticeCaminho.getPai() != null) {
		                                menorCaminho.add(verticeCaminho.getPai());
		                                verticeCaminho = verticeCaminho.getPai();
		                            }
		                            Collections.sort(menorCaminho);
		                        }
		                    }
		                }

		            }
		            atual.setVisitado(true);
		            naoVisitados.remove(atual);
		          
		            Collections.sort(naoVisitados);
		        }
		        this.limparVerticesPai();
		        return menorCaminho;
		    }
}