package main;

import java.applet.AppletContext;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JApplet;
import javax.swing.JButton;


public class Player1ArmamentsPanel extends JApplet implements ActionListener{
	Clip music; 
	
	AppletContext ac; // アプレットのコンテキスト
	Dimension size; // アプレットの大きさ
	MainPanel mp;
	JButton bt;
	// コンストラクタ
	
	Player1ShoppingMyMoney up;
	Player1Shopping left;
	Player1ShoppingPay down;
	Player1ShoppingPicture right;

	ItemControl item_con;
	WeaponList[] weapon_list;//全武器のリスト
	int[] having_weapon ;//それぞれの武器所持数
	
	public Player1ArmamentsPanel(AppletContext ac1, Dimension size1, MainPanel mp1) throws LineUnavailableException, UnsupportedAudioFileException
	{
		Container pane = getContentPane();
		pane.setBackground(Color.WHITE);

		ac=ac1;
		size=size1;
		mp=mp1;

		setSize(size);

//		pane.setSize(size);
//					// レイアウトマネージャの停止
//		pane.setLayout(null);
//					// 背景色の設定
//		pane.setBackground(Color.white);
//					// ボタンの配置
//		Font f = new Font("SansSerif", Font.BOLD, 20);
//		FontMetrics fm = getFontMetrics(f);
//
//		String str = "戻る";
//		int w = fm.stringWidth(str) + 40;
//		int h = fm.getHeight() + 10;
//		bt = new JButton(str);
//		bt.setFont(f);
//		bt.addActionListener(this);
//		bt.setSize(w, h);
//		bt.setLocation(size.width/2-w/2, 5);//文字列の幅と高さを元にボタンのサイズを決定し，中央に配置しています
//		add(bt);

		
		up= new Player1ShoppingMyMoney(this);
		left= new Player1Shopping(this);
		down= new Player1ShoppingPay(this, mp);
		right= new Player1ShoppingPicture(this);

		item_con = new ItemControl();
		having_weapon = new int[Weapon.kind];
		weapon_list = new WeaponList[Weapon.kind];

		for(int i = 0; i < Weapon.kind; i++){
			weapon_list[i] = new WeaponList(i+1);
		}

		pane.setLayout(new BorderLayout());
		pane.add(up,BorderLayout.NORTH);
		pane.add(left,BorderLayout.WEST);
		pane.add(right,BorderLayout.EAST);
		pane.add(down,BorderLayout.SOUTH);

		/*
		pane.setLayout(new FlowLayout());
		pane.add(up);
		pane.add(left);
		pane.add(right);
		pane.add(down);
	*/
		wlrepaint();
		//System.out.println("wlrepaint");
		MMrepaint();
		Payrepaint();
		Picturerepaint();

		repaint();

		try{
		music = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
		music.open(AudioSystem.getAudioInputStream(new File("resource/music/shopping.wav")));
		}
		catch (IOException e) {
			throw new RuntimeException(e);

		}
		music.setMicrosecondPosition(0);
		music.loop(999);

		//////////////////////////////ここからitem//////////////////////////////////////////////////
		item_con.readItem(mp.pvp_num);
		up.passItem(item_con.items[0]);

		try{
			BufferedReader br = new BufferedReader(new FileReader("resource/Weapon/Player1Weapon"));
			String read = br.readLine();
			if(read != null){
			String[] tokens1 = read.split(",");
			//System.out.println(" tokens1 : "+Arrays.toString(tokens1));
			
			for(int i = 0; i < tokens1.length; i++){
				if(Integer.parseInt(tokens1[i]) !=  0)having_weapon[Integer.parseInt(tokens1[i])-1]++;
			}
			}
			up.passWeapon(weapon_list, having_weapon);

			br.close();
		}catch(IOException er){
			throw new RuntimeException(er);
		}

		//////////////////////////////////////////////////////////////////////////////////////////
	}


	//Shopping-ShoppingItemList
	public void set(){
		left.wl.set();
	}
	public void wlrepaint(){
		left.wl.repaint();
	}

	//ShoppingMyMoney
	public void cal(int payMoney){
		up.cal(payMoney);
	}
	public void kane(){
		up.kane();
	}
	public void dame(){
		up.dame();
	}
	public void MMrepaint(){
		up.repaint();
	}

	//ShoppingPay
	public void cal(int[] buyItem){
		down.cal(buyItem);
	}
	public void Payrepaint(){
		down.repaint();
	}

	//ShoppingPcture
	public void Picturerepaint(){
		right.repaint();
	}

	public void paint(Graphics g){
		super.paint(g);
		g.setFont(new Font("Serif", Font.BOLD, 40));
		g.drawString("fdhsajkl",0,0);
	}

	// ボタンがクリックされたときの処理
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt) {
			mp.state = 10;
		}
	}
}
