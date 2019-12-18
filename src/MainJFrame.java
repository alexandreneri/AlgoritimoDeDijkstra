import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.Caret;

 

public class MainJFrame extends JFrame{
	
	
	private DrawPanel drawPanel = new DrawPanel();
	
	public MainJFrame() {
		Grafo inicial = new Grafo();
		setTitle("Teoria dos Grafos");
	    setSize(750,500);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    Container c = this.getContentPane();
	    c.setLayout(new BorderLayout());
	    
	    //south
	    JPanel panelSouth = new JPanel();
	    panelSouth.setLayout(new BorderLayout());
	    JPanel panelPont = new JPanel();
	    panelPont.setLayout(new BorderLayout());
	    final JTextField dist = new JTextField(20);
	    dist.setText("Distancia");
	    final JTextField origem = new JTextField(20);
	    origem.setText("Origem");
	    final JTextField destino = new JTextField(20);
	    destino.setText("Destino");
	    final JTextField temp = new JTextField(20);
	    temp.setText("tempo");
	    final JTextField pontA = new JTextField(10);
	    pontA.setText("Ponto A");
	    final JTextField pontB = new JTextField(10);
	    pontB.setText("Ponto B");
	    final JTextField res = new JTextField(20); 		
	   
	    JButton okButtonDist = new JButton("Melhor Caminho Distancia");
	    JButton okButton = new JButton("Melhor Caminho Tempo");
	    JButton AddButton = new JButton("ADD Grafo");

	    
	    panelSouth.add(res,BorderLayout.NORTH);		
	    panelSouth.add(dist,BorderLayout.WEST);
	    panelSouth.add(origem,BorderLayout.CENTER);
	    panelSouth.add(destino,BorderLayout.EAST);
	    panelSouth.add(temp,BorderLayout.SOUTH);
	    
	    panelPont.add(okButtonDist, BorderLayout.NORTH);
	    panelPont.add(okButton, BorderLayout.SOUTH);
	    panelPont.add(pontA, BorderLayout.WEST);
	    panelPont.add(pontB, BorderLayout.EAST);
	      
	    c.add(panelSouth,BorderLayout.SOUTH);
	    c.add(drawPanel,BorderLayout.CENTER);
	    c.add(AddButton,BorderLayout.WEST);
	    c.add(panelPont,BorderLayout.EAST);
	 
	    AddButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {				
			
				int distancia =  Integer.parseInt(dist.getText());
				int tempo  =  Integer.parseInt(temp.getText());
				
				inicial.addAresta(origem.getText(),distancia, tempo, destino.getText());
				
				drawPanel.draw(inicial.getVertices());
				drawPanel.updateUI();
				
			}
	    });

	    okButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
	    		Grafo resultado = new Grafo();

	    		Vertice verticeAux1, verticeAux2;

	    		
	    		inicial.imprimeArvore();

	    		//Algoritmo de Dijkstra	    		
	    		verticeAux1 = inicial.acharVertice(pontA.getText());
	    		verticeAux2 = inicial.acharVertice(pontB.getText());
	    		resultado.setVertices(inicial.encontrarMenorCaminhoDijkstraTemp(verticeAux1, verticeAux2));
	    		
	    		System.out.println(resultado.getVertices());
	 	    		
	    		
				System.out.println("Esse é o menor caminho:"
						+ resultado.getVertices());
				
				res.setText("Menor caminho é: "
						+  resultado.getVertices());
				inicial.limparArestaVisitada();
				inicial.limparVerticeVisitado();
				
			}
		});
	 
	    okButtonDist.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
	    		Grafo resultado = new Grafo();

	    		Vertice verticeAux1, verticeAux2;

	    		
	    		inicial.imprimeArvore();

	    		//Algoritmo de Dijkstra	    		
	    		verticeAux1 = inicial.acharVertice(pontA.getText());
	    		verticeAux2 = inicial.acharVertice(pontB.getText());
	    		resultado.setVertices(inicial.encontrarMenorCaminhoDijkstraDist(verticeAux1, verticeAux2));

				System.out.println("Esse é o menor caminho:"
						+ resultado.getVertices());
				
				res.setText("Menor caminho é: "
						+  resultado.getVertices());
				
				inicial.limparArestaVisitada();
				inicial.limparVerticeVisitado();
		
				
			}
		});
	    
	    this.setVisible(true);
	    this.setResizable(true);
	}
	
	public static void main(String[] args) {
		new MainJFrame();
	}

}