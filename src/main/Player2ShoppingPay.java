package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Player2ShoppingPay extends JPanel implements ActionListener{
	JButton button;
	JButton back;
	int commentNum = 0;
	Image backGraund;
	Image waku;

	Player2ArmamentsPanel sMain;

	MainPanel mp;
	
	public void init(){
		setSize(600,100);
	}

	public Player2ShoppingPay(Player2ArmamentsPanel sMain , MainPanel mp){
		this.sMain = sMain;
		this.mp = mp;

		try{
			backGraund = ImageIO.read(new File("resource/picture/ShoppingPay.jpg"));
			waku = ImageIO.read(new File("resource/picture/waku002.png"));
		}
		catch(IOException er){
			throw new RuntimeException(er);
		}

		setPreferredSize(new Dimension(600,100));
		setLayout(null);
		button = new JButton("購入");
		button.setBounds(190,20,100,50);
		button.addActionListener(this);
		add(button);

		back = new JButton("戻る");
		back.setBounds(520,70,80,30);
		back.addActionListener(this);
		add(back);



	}
	
	

	public void cal(int[] buyItem){
		sMain.up.payMoney = 0;
		for(int i=0;i<sMain.up.money.length;i++){
			sMain.up.payMoney += sMain.up.money[i] * buyItem[i];
		}
		repaint();
	}

	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(backGraund, 0, 0, 300,100 , this);
		g.drawImage(waku, 300, 0, 300,100 , this);
		button.repaint();
		back.repaint();
		g.setFont(new Font("Serif", Font.PLAIN, 20));
		g.setColor(Color.BLACK);
		g.drawString(sMain.up.comment[commentNum], 350,50 );
		g.setFont(new Font("Serif", Font.PLAIN, 30));
		g.drawString("総額"+sMain.up.payMoney+"円", 0,55 );
	}

	public void actionPerformed(ActionEvent e){
		Object src = e.getSource();
		if(src==button){
			if(sMain.up.myMoney-sMain.up.payMoney>=0){
				sMain.kane();
				sMain.cal(sMain.up.payMoney);
				System.out.println("アイテム購入");
				for(int i=0;i<sMain.up.itemLength;i++){
					System.out.println(i+"番目:"+sMain.up.itemName[i]+":"+sMain.up.buyItem[i]);
					sMain.up.havingItem[i]+=sMain.up.buyItem[i];
				}
				/////////////////////////////item記録////////////////////////////////////////
				System.out.println("買ったお");
				sMain.item_con.buyItem(sMain.up.buyItem , mp.pvp_num);
				////////////////////////////////////////////////////////////////////////////

				/////////////////////////////weapon記録///////////////////////////////////////////////
				String str = "0,";
				for(int i = Item.kind ; i < sMain.up.itemLength; i++){
						for(int j = 0; j < sMain.up.havingItem[i]; j++){
							str += i-Item.kind+1+",";
					}
				}
				try{
					if(str != null){
					PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("resource/Weapon/Player2Weapon")));
					pw.println(str);
					pw.close();
					}
				}catch (IOException er) {
				}
				////////////////////////////money記録////////////////////////////////////////////////////////
				try{
					PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("resource/Money/Player2Money")));
					pw.println(sMain.up.myMoney);
					pw.close();
				}catch (IOException er) {
				}
				//////////////////////////////////////////////////////////////////////////////////////////////
				commentNum =1;
				sMain.up.payMoney = 0;
				sMain.up.buyItem = new int[sMain.up.itemLength];
				sMain.wlrepaint();
				repaint();
			}
			else{
				commentNum = 2;
				sMain.dame();
				repaint();
			}

		}
		if(src==back){
			sMain.music.stop();
			mp.state = 11;
		}
	}
}
