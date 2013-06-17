package projet;

import java.awt.datatransfer.DataFlavor;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

public class CardTransferHandler extends TransferHandler{

	public CardTransferHandler(){
		super();
	}
	public int getSourceActions(JComponent c)
	{
		return MOVE;
	}

	/*public NodeTransferable createTransferable(JComponent c)
	{
		if (c.getClass()==JTextArea.class)
		{
			JTextArea jta = (JTextArea)c;
			return new NodeTransferable((String)jta.getText());
		}
		else
		{
			JTree tree = (JTree)c;
			return new NodeTransferable((DefaultMutableTreeNode) tree.getLastSelectedPathComponent());
		}
		
	}*/

	public boolean canImport(TransferHandler.TransferSupport support)
	{
		//System.out.println("je suis dans le can impor");
		if (!support.isDrop())
		{
			return false;
		}

		return true;
	}

	/*public boolean importData(TransferSupport support) {
		//System.out.println("je suis dans le import data");
		if (canImport(support)) {
			try {
				DataFlavor[] result = support.getDataFlavors();
				for (int i=0;i<result.length;i++)
				{
					if (result[i] == NodeTransferable.nodeFlavor)
					{
						Transferable t =  support.getTransferable();
						DefaultMutableTreeNode dmt = (DefaultMutableTreeNode)
								t.getTransferData(NodeTransferable.nodeFlavor);
						JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
						TreePath tp = dl.getPath();
						if (tp == null) {return false;}
						DefaultMutableTreeNode parent = (DefaultMutableTreeNode) 
								tp.getLastPathComponent();
						if (parent.getUserObject() instanceof Contact) {return false;}
						JTree tree = (JTree)support.getComponent(); 
						DefaultTreeModel tm = (DefaultTreeModel) tree.getModel();
						parent.add(dmt);
						tm.reload();
						tree.expandPath(tp);
						
					}
					else if (result[i] == DataFlavor.stringFlavor)
					{
						System.out.println("on y arrive");
						
					}
						
				}
				return true;
			}
			catch(Exception ex) {ex.printStackTrace();}
		}
		return false;
	}*/
}
