
public class Teste {
	
	public static void main(String arg[]) {
	
	
	Grafo inicial = new Grafo();
	Grafo resultado = new Grafo();

	Vertice verticeAux1, verticeAux2;

	int  peso, temp;
	String origem; 
	String  destino;
	
	peso = 1;
	temp = 3;
	origem = "a";
	destino = "b";
	inicial.addAresta(origem, peso, temp,  destino);
	peso = 3;
	temp = 9;
	origem = "b";
	destino = "c";
	inicial.addAresta(origem, peso, temp,  destino);
	
	peso = 8;
	temp = 3;
	origem = "a";
	destino = "c";
	inicial.addAresta(origem, peso, temp,  destino);
	
	inicial.imprimeArvore();

	//Algoritmo de Dijkstra
	
	verticeAux1 = inicial.acharVertice("a");
	verticeAux2 = inicial.acharVertice("c");
	resultado.setVertices(inicial.encontrarMenorCaminhoDijkstraTemp(verticeAux1, verticeAux2));
	
	System.out.println(resultado.getVertices());
	
	
	inicial.limparArestaVisitada();
	inicial.limparVerticeVisitado();
	
	
	//Algoritmo de Dijkstra
	
	verticeAux1 = inicial.acharVertice("a");
	verticeAux2 = inicial.acharVertice("c");
	resultado.setVertices(inicial.encontrarMenorCaminhoDijkstraDist(verticeAux1, verticeAux2));
	
	System.out.println(resultado.getVertices());
	
	
	
	

	}
}
