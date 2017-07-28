package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Player2ShoppingItemList extends JPanel implements ActionListener{
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	int itemLength = Item.kind+Weapon.kind;//アイテムの数
	////////////////////////////////////////////////////////////////////////////////////////////////////////

	Player2ArmamentsPanel sMain;

	JButton[] UP = new JButton[itemLength];
	JButton[] DOWN = new JButton[itemLength];
	Image backGraund;

	public Player2ShoppingItemList(Player2ArmamentsPanel sMain){
		//System.out.println("shopping item");
		this.sMain = sMain;

		try{
			backGraund = ImageIO.read(new File("resource/picture/shoppingBackGraund.jpeg"));
		}
		catch(IOException er){
			throw new RuntimeException(er);
		}
		setPreferredSize(new Dimension(600,1200*itemLength/23));

		setLayout(null);
		for(int i=0;i<itemLength;i++){
			UP[i] = new JButton("＋");
			UP[i].addActionListener(this);
			DOWN[i] = new JButton("－");
			DOWN[i].addActionListener(this);
			UP[i].setBounds(150,4+51*i,50,40);
			DOWN[i].setBounds(200,4+51*i,50,40);
			add(UP[i]);
			add(DOWN[i]);
		}
		//repaint();

	}
	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(backGraund, 0, 0, 600,1200*itemLength/23 , this);

		for(int i=0;i<sMain.up.itemLength;i++){
			UP[i].repaint();
			DOWN[i].repaint();
		}

		g.setFont(new Font("Serif", Font.PLAIN, 15));
		g.setColor(Color.BLACK);
		for(int i=0;i<itemLength;i++){
			if(sMain.up.itemName[i] == null) continue;
			g.setFont(new Font("Serif", Font.PLAIN, 15));
			if(sMain.up.itemName[i] != null)g.drawString(sMain.up.itemName[i], 0, 22+51*i);
			g.setFont(new Font("Serif", Font.PLAIN, 15));
			g.drawString("所持数"+String.valueOf(sMain.up.havingItem[i])+"個"+" ¥"+sMain.up.money[i]+"円", 0, 42+51*i);
			g.setFont(new Font("Serif", Font.PLAIN, 20));
			g.drawString(String.valueOf(sMain.up.buyItem[i]), 255, 30+51*i);
		}
	}
	public void set(){
		for(int i=0;i<itemLength;i++){
			sMain.up.buyItem[i]=0;
		}
	}


	public void actionPerformed(ActionEvent e){
		Object src = e.getSource();
		for(int i=0;i<itemLength;i++){
			if(UP[i]==src){
				System.out.println("UP"+i);
				sMain.up.buyItem[i]++;
			}
			else if(DOWN[i]==src){
				System.out.println("DOWN"+i);
				sMain.up.buyItem[i]--;
				if(sMain.up.buyItem[i]<0){
					sMain.up.buyItem[i]=0;
				}
			}

		}
		repaint();
		sMain.cal(sMain.up.buyItem);
		sMain.down.commentNum = 0;
		sMain.Payrepaint();
	}
}
