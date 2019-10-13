
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
		
		//retorna posicao
		i = this.addVertice(origem);
		j = this.addVertice(destino);
		
		//adiciona aresta
		Aresta a = new Aresta(this.vertices.get(i), pesoDist, pesoTemp, this.vertices.get(j));	
	
		this.arestas.add(a);
		k = this.arestas.size();
		
		//adiciona aresta na lista de arestas incidentes em cada vertice
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

			//se ja existir na lista nao passara daqui
			if(this.posicaoVertice(vertices.get(i).getNome())==this.vertices.size()){
				//adicionando as arestas correspondentes a tais vertices
				for(int j=0; j<vertices.get(i).getIncidentes().size(); j++){

					//se o adicionado for a origem desse seu incidente, e o seu destino estiver na lista de vertices
					if ( (vertices.get(i).getNome().equals(vertices.get(i).getIncidentes().get(j).getOrigem().getNome())) &&
							(this.posicaoVertice(vertices.get(i).getIncidentes().get(j).getDestino().getNome())!=this.vertices.size()) ){

						this.addAresta(vertices.get(i).getIncidentes().get(j).getOrigem().getNome(), 
								       vertices.get(i).getIncidentes().get(j).getPesoDist(), 
							        vertices.get(i).getIncidentes().get(j).getPesoTemp(), 
										vertices.get(i).getIncidentes().get(j).getDestino().getNome());
					
					//se o adicionado for o destino desse seu incidente, e o sua origem estiver na lista de vertices	
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
		
		//se nao encontrar retorna o tamanho da lista vertices
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
		
		//metodo que retorna o caminho menos custoso entre dois vertices a partid do algoritmo de Dijkstra
	    public ArrayList<Vertice> encontrarMenorCaminhoDijkstraTemp(Vertice v1,Vertice v2) {

	    	// Atributos usados na funcao encontrarMenorCaminho

	        // Lista que guarda os vertices pertencentes ao menor caminho encontrado
	    	ArrayList<Vertice> menorCaminho = new ArrayList<Vertice>();

	        // Variavel que recebe os vertices pertencentes ao menor caminho
	        Vertice verticeCaminho;

	        // Variavel que guarda o vertice que esta sendo visitado
	        Vertice atual;

	        // Variavel que marca o vizinho do vertice atualmente visitado
	        Vertice vizinho;
	        
	        // Aresta que liga o atual ao seu vizinho;
	        Aresta ligacao;

	        // Lista dos vertices que ainda nao foram visitados
	        ArrayList<Vertice> naoVisitados = new ArrayList<Vertice>();

	        // Algoritmo de Dijkstra
	        
	    	// Adiciona a origem na lista do menor caminho
	        menorCaminho.add(v1);

	        // Colocando a distancias iniciais 
	        for (int i = 0; i < this.getVertices().size(); i++) {
	            // Vertice atual tem distancia zero, e todos os outros,
	            // 9999("infinita")
	            if (this.getVertices().get(i).getNome().equals(v1.getNome()))
	                this.getVertices().get(i).setDistancia(0);
	            else
	                this.getVertices().get(i).setDistancia(9999);
	            // Insere o vertice na lista de vertices nao visitados
	            naoVisitados.add(this.getVertices().get(i));
	        }

	        Collections.sort(naoVisitados);

	        // O algoritmo continua ate que todos os vertices sejam visitados
	        while (!naoVisitados.isEmpty()) {
	            // Toma-se sempre o vertice com menor distancia, que eh o primeiro
	            // da
	            // lista

	            atual = naoVisitados.get(0);
	            /*
	             * Para cada vizinho (cada aresta), calcula-se a sua possivel
	             * distancia, somando a distancia do vertice atual com a da aresta
	             * correspondente. Se essa distancia for menor que a distancia do
	             * vizinho, esta eh atualizada.
	             */
	            for (int i = 0; i < atual.getVizinhos().size(); i++) {
	            	
	                vizinho = atual.getVizinhos().get(i);
	                
	                if (!vizinho.isVisitado()) {
	                	
	                    // Comparando a distância do vizinho com a possível
	                    // distância
	                	ligacao = this.acharAresta(atual,vizinho);
	                    if (vizinho.getDistancia() > (atual.getDistancia() + ligacao.getPesoTemp())) {
	                        vizinho.setDistancia(atual.getDistancia()
	                                        + ligacao.getPesoTemp());
	                        vizinho.setPai(atual);

	                        /*
	                         * Se o vizinho eh o vertice procurado, e foi feita uma
	                         * mudanca na distancia, a lista com o menor caminho
	                         * anterior eh apagada, pois existe um caminho menor
	                         * vertices pais, ateh o vertice origem.
	                         */
	                        if (vizinho == v2) {
	                            menorCaminho.clear();
	                            verticeCaminho = vizinho;
	                            menorCaminho.add(vizinho);
	                            while (verticeCaminho.getPai() != null) {
	                                menorCaminho.add(verticeCaminho.getPai());
	                                verticeCaminho = verticeCaminho.getPai();

	                            }
	                            // Ordena a lista do menor caminho, para que ele
	                            // seja exibido da origem ao destino.
	                            Collections.sort(menorCaminho);

	                        }
	                    }
	                }

	            }
	            // Marca o vertice atual como visitado e o retira da lista de nao
	            // visitados
	            atual.setVisitado(true);
	            naoVisitados.remove(atual);
	            /*
	             * Ordena a lista, para que o vertice com menor distancia fique na
	             * primeira posicao
	             */

	            Collections.sort(naoVisitados);

	        }
	        this.limparVerticesPai();
	        return menorCaminho;
	    }

	
	

		//----------------------DIJKSTRA- Temp ------------------------------------------
			
			//metodo que retorna o caminho menos custoso entre dois vertices a partid do algoritmo de Dijkstra
		    public ArrayList<Vertice> encontrarMenorCaminhoDijkstraDist(Vertice v1,Vertice v2) {

		    	// Atributos usados na funcao encontrarMenorCaminho

		        // Lista que guarda os vertices pertencentes ao menor caminho encontrado
		    	ArrayList<Vertice> menorCaminho = new ArrayList<Vertice>();

		        // Variavel que recebe os vertices pertencentes ao menor caminho
		        Vertice verticeCaminho;

		        // Variavel que guarda o vertice que esta sendo visitado
		        Vertice atual;

		        // Variavel que marca o vizinho do vertice atualmente visitado
		        Vertice vizinho;
		        
		        // Aresta que liga o atual ao seu vizinho;
		        Aresta ligacao;

		        // Lista dos vertices que ainda nao foram visitados
		        ArrayList<Vertice> naoVisitados = new ArrayList<Vertice>();

		        // Algoritmo de Dijkstra
		        
		    	// Adiciona a origem na lista do menor caminho
		        menorCaminho.add(v1);

		        // Colocando a distancias iniciais 
		        for (int i = 0; i < this.getVertices().size(); i++) {
		            // Vertice atual tem distancia zero, e todos os outros,
		            // 9999("infinita")
		            if (this.getVertices().get(i).getNome().equals(v1.getNome()))
		                this.getVertices().get(i).setDistancia(0);
		            else
		                this.getVertices().get(i).setDistancia(9999);
		            // Insere o vertice na lista de vertices nao visitados
		            naoVisitados.add(this.getVertices().get(i));
		        }

		        Collections.sort(naoVisitados);

		        // O algoritmo continua ate que todos os vertices sejam visitados
		        while (!naoVisitados.isEmpty()) {
		            // Toma-se sempre o vertice com menor distancia, que eh o primeiro
		            // da
		            // lista

		            atual = naoVisitados.get(0);
		            /*
		             * Para cada vizinho (cada aresta), calcula-se a sua possivel
		             * distancia, somando a distancia do vertice atual com a da aresta
		             * correspondente. Se essa distancia for menor que a distancia do
		             * vizinho, esta eh atualizada.
		             */
		            for (int i = 0; i < atual.getVizinhos().size(); i++) {
		            	
		                vizinho = atual.getVizinhos().get(i);
		                
		                if (!vizinho.isVisitado()) {
		                	
		                    // Comparando a distância do vizinho com a possível
		                    // distância
		                	ligacao = this.acharAresta(atual,vizinho);
		                    if (vizinho.getDistancia() > (atual.getDistancia() + ligacao.getPesoDist())) {
		                        vizinho.setDistancia(atual.getDistancia()
		                                        + ligacao.getPesoDist());
		                        vizinho.setPai(atual);

		                        /*
		                         * Se o vizinho eh o vertice procurado, e foi feita uma
		                         * mudanca na distancia, a lista com o menor caminho
		                         * anterior eh apagada, pois existe um caminho menor
		                         * vertices pais, ateh o vertice origem.
		                         */
		                        if (vizinho == v2) {
		                            menorCaminho.clear();
		                            verticeCaminho = vizinho;
		                            menorCaminho.add(vizinho);
		                            while (verticeCaminho.getPai() != null) {
		                                menorCaminho.add(verticeCaminho.getPai());
		                                verticeCaminho = verticeCaminho.getPai();

		                            }
		                            // Ordena a lista do menor caminho, para que ele
		                            // seja exibido da origem ao destino.
		                            Collections.sort(menorCaminho);

		                        }
		                    }
		                }

		            }
		            // Marca o vertice atual como visitado e o retira da lista de nao
		            // visitados
		            atual.setVisitado(true);
		            naoVisitados.remove(atual);
		            /*
		             * Ordena a lista, para que o vertice com menor distancia fique na
		             * primeira posicao
		             */

		            Collections.sort(naoVisitados);

		        }
		        this.limparVerticesPai();
		        return menorCaminho;
		    }

	
}