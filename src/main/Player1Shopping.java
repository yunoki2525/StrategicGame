package main;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JScrollPane;


public class Player1Shopping extends JApplet{
	Player1ArmamentsPanel sMain ;
	Player1ShoppingItemList wl;
	public void init(){
		setSize(300,400);
	}

	public Player1Shopping(Player1ArmamentsPanel sMain){
		setPreferredSize(new Dimension(300,400));
		this.sMain = sMain;
		wl = new Player1ShoppingItemList(this.sMain);
		Container pane = getContentPane();
		JScrollPane scrollPane = new JScrollPane(wl,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.add(scrollPane);
	}
}
