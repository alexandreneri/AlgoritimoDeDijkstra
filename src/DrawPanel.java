
import java.awt.*;
import java.awt.geom.*;
import java.util.List;
import javax.swing.*;
import org.jgraph.*;
import org.jgraph.graph.*;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;
import org.jgrapht.graph.DefaultEdge;

public class DrawPanel
    extends JPanel
{
    private static final long serialVersionUID = 3256444702936019250L;
    private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

    private JGraphModelAdapter<String, MyEdge> jgAdapter;

    public DrawPanel(){
        this.setBackground(Color.white);
        this.setSize(400,400);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }
     
    public void draw(List<Vertice> vertices){
    	 ListenableGraph<String, MyEdge> g =
            new ListenableDirectedMultigraph<String, MyEdge>(
                MyEdge.class);

     
        jgAdapter = new JGraphModelAdapter<String, MyEdge>(g);

        JGraph jgraph = new JGraph(jgAdapter);
        adjustDisplaySettings(jgraph);
        
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.add(jgraph,BorderLayout.CENTER);
                
        resize(DEFAULT_SIZE);
        
        for(Vertice v:vertices){
        	g.addVertex(v.getNome().trim());
        }
        
        for(Vertice v1:vertices){
          	for(Aresta a: v1.getIncidentes())
        		g.addEdge(v1.getNome().trim(), a.getDestino().getNome().trim(), new MyEdge("Temp "+a.getPesoTemp()+" Dist "+a.getPesoDist()+""));
        }
       
        int size = vertices.size();
        int i = 0;
        int x = 20;
        int y = 20;
        
        for(Vertice v: vertices){
            
            positionVertexAt(v.getNome().trim(),x,y);
            x+=150;
            if(i==size/2){
            	x = 20;
            	y+=200;
            }
            i++;
        }
    }

    private void adjustDisplaySettings(JGraph jg)
    {
        jg.setPreferredSize(DEFAULT_SIZE);
        Color c = Color.white;
        jg.setBackground(c);
    }

    @SuppressWarnings("unchecked") 
    private void positionVertexAt(Object vertex, int x, int y)
    {
        DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();
        Rectangle2D bounds = GraphConstants.getBounds(attr);

        Rectangle2D newBounds =
            new Rectangle2D.Double(
                x,
                y,
                bounds.getWidth(),
                bounds.getHeight());

        GraphConstants.setBounds(attr, newBounds);

        AttributeMap cellAttr = new AttributeMap();
        cellAttr.put(cell, attr);
        jgAdapter.edit(cellAttr, null, null, null);
    }

    private static class ListenableDirectedMultigraph<V, E>
        extends DefaultListenableGraph<V, E>
        implements DirectedGraph<V, E>
    {
        private static final long serialVersionUID = 1L;
        ListenableDirectedMultigraph(Class<E> edgeClass)
        {
            super(new DirectedMultigraph<V, E>(edgeClass));
        }
    }
    
    class MyEdge extends DefaultEdge{
    	
    	private String label;    	
    	public MyEdge(String label) {
			this.label = label;
		}
    	
    	@Override
    	public String toString() {
    		return label;
    	}
    }
}

