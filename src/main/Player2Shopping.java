package main;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JApplet;
import javax.swing.JScrollPane;

public class Player2Shopping extends JApplet{
	Player2ArmamentsPanel sMain ;
	Player2ShoppingItemList wl;
	public void init(){
		setSize(300,400);
	}

	public Player2Shopping(Player2ArmamentsPanel sMain){
		setPreferredSize(new Dimension(300,400));
		this.sMain = sMain;
		wl = new Player2ShoppingItemList(this.sMain);
		Container pane = getContentPane();
		JScrollPane scrollPane = new JScrollPane(wl,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.add(scrollPane);
	}
}
