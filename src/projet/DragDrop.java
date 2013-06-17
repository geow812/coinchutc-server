package projet;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.TransferHandler;

public class DragDrop extends JPanel{
	 
	  private ArrayList<Carte> main;
	  private JLabel c0;
	  private JLabel c1;
	  private JLabel c2;
	  private JLabel c3;
	  private JLabel c4;
	  private JLabel c5;
	  private JLabel c6;
	  private JLabel c7;
	  
	  public DragDrop(){
		  
		  c0 = new JLabel();
		  c1 = new JLabel();
		  c2 = new JLabel();
		  c3 = new JLabel();
		  c4 = new JLabel();
		  c5 = new JLabel();
		  c6 = new JLabel();
		  c7 = new JLabel();
		  
		main = new ArrayList();
		
	    setName("Drag'n Drop avec un JLabel !");
	    setSize(200, 200);
	    
	       
	    
	    this.setLayout(new GridLayout(2,2));
	    this.setBackground(Color.white);
	    
	   /* this.add(c0);
	    this.add(c1);
	    this.add(c2);
	    this.add(c3);
	    this.add(c4);
	    this.add(c5);
	    this.add(c6);
	    this.add(c7);*/
	    
	    JLabel srcLib = new JLabel("Source de drag : ", JLabel.RIGHT);
	    JLabel src = new JLabel("Texte à déplacer !");
	       
	    ImageIcon source = new ImageIcon("res\\drawable\\14Pique.png");
		ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		c0.setIcon(resultat);
		
		
		 
	    //-------------------------------------------------------------------
	    //On crée le nouvel objet pour activer le drag'n drop 
	    c0.setTransferHandler(new TransferHandler("text"));
	       
	    //On spécifie au composant qu'il doit envoyer ses données via son objet TransferHandler
	    c0.addMouseListener(new MouseAdapter(){
	      //On utilise cet événement pour que les actions soient visibles dès le clic de souris…
	      //Nous aurions pu utiliser mouseReleased, mais, niveau IHM, nous n'aurions rien vu
	      public void mousePressed(MouseEvent e){
	        //On récupère le JComponent            
	        JComponent lab = (JComponent)e.getSource();
	        //Du composant, on récupère l'objet de transfert : le nôtre
	        TransferHandler handle =  lab.getTransferHandler();
	        //On lui ordonne d'amorcer la procédure de drag'n drop
	        handle.exportAsDrag(lab, e, TransferHandler.COPY);
	      }
	    });
	    //-------------------------------------------------------------------
	       
	    JLabel destLib = new JLabel("Destination de drag : ", JLabel.RIGHT);
	    JTextField dest = new JTextField();
	    //On active le comportement par défaut de ce composant
	    dest.setDragEnabled(true);
	       
	    this.add(c0);
	    this.add(srcLib);
	    //this.add(src);
	    this.add(destLib);
	    this.add(dest);
	       
	  
	    setVisible(true);
	  }
	 
	 
	  public void setCarte(Carte card)
	  {
		  main.add(card);
	  }
	}