package main;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JApplet;
import javax.swing.JScrollPane;


public class Shopping extends JApplet{
	ShoppingMain sMain;
	ShoppingItemList wl;
	public void init(){
		setSize(300,400);
	}

	public Shopping(ShoppingMain sMain){
		setPreferredSize(new Dimension(300,400));
		this.sMain = sMain;
		wl = new ShoppingItemList(this.sMain);
		Container pane = getContentPane();
		JScrollPane scrollPane = new JScrollPane(wl,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.add(scrollPane);
	}
	
}

