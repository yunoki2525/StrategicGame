package main;

import java.applet.AppletContext;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;

public class GameMenuPanel extends JApplet implements ActionListener{
	AppletContext ac; // アプレットのコンテキスト
	Dimension size; // アプレットの大きさ
	MainPanel mp;

	JButton backButton, battleButton, statusButton, shopButton, PvPButton;

	Image button1,backIcon,battleIcon,statusIcon,shopIcon,PvPIcon;
	ImageIcon gostButtonIcon;

	//buttonの配置をずらすための変数
	int dx=0;
	int dy=0;

	
	// コンストラクタ
	public GameMenuPanel(AppletContext ac1, Dimension size1, MainPanel mp1) {
		ac = ac1;
		size = size1;
		mp = mp1;

		setSize(size);

		Container pane = getContentPane();
		pane.setLayout(null);

		try{
			button1 = ImageIO.read(new File("ButtonImage/button1.png"));

			backIcon = ImageIO.read(new File("Image/teitoku.png"));

			battleIcon = ImageIO.read(new File("ButtonImage/出撃アイコン3.png"));
			statusIcon = ImageIO.read(new File("ButtonImage/ステータスアイコン1.png"));
			shopIcon = ImageIO.read(new File("ButtonImage/ショップアイコン1.png"));
			PvPIcon = ImageIO.read(new File("ButtonImage/taisenbuttonIconRed.png"));

			gostButtonIcon = new ImageIcon("ButtonImage/透明.png");

		}catch(IOException e){
			throw new RuntimeException(e);
		}


		backButton = new JButton(){
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				//全部座標
				g.drawImage(backIcon, 50, 50, 148, 148, 0, 0, (148-50)*480/600, (148-50)*480/600, this);

				g.drawImage(button1, 0, 0, 148, 148,this);

				g.setColor(Color.WHITE);
				g.setFont(new Font("Serif", Font.BOLD, 40));
				g.drawString("休",  3+50, 35+50);
				g.drawString("憩", 25+50, 60+50);

				//ボタンの線を消す
				setBorderPainted(false);
			}
		};

		battleButton = new JButton(){
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				//全部座標
				g.drawImage(backIcon, 0, 0, 125, 125, (125+dx)*480/600, (300+dy)*480/600, ((125+dx)+125)*480/600, ((300+dy)+125)*480/600, this);

				g.drawImage(battleIcon, 0, 0, 125, 125,this);

				g.setColor(Color.YELLOW);
				g.setFont(new Font("Serif", Font.BOLD, 60));
				g.drawString("出", 130-125, 410-300);
				g.drawString("撃", 180-125, 430-300);

				//ボタンの線を消す
				setBorderPainted(false);
			}
		};

		statusButton = new JButton(){
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				//全部座標
				g.drawImage(backIcon, 0, 0, 80, 80, (145+dx)*480/600, (200+dy)*480/600, ((145+dx)+80)*480/600, ((200+dy)+80)*480/600, this);

				g.drawImage(statusIcon, 0, 0, 80, 80,this);

				g.setColor(Color.WHITE);
				g.setFont(new Font("Serif", Font.BOLD, 35));
				g.drawString("編", 150-145, 275-200);
				g.drawString("成", 180-145, 285-200);

				//ボタンの線を消す
				setBorderPainted(false);
			}
		};

		shopButton = new JButton(){
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				//全部座標
				g.drawImage(backIcon, 0, 0, 80, 80, (50+dx)*480/600, (400+dy)*480/600, ((50+dx)+80)*480/600, ((400+dy)+80)*480/600, this);

				g.drawImage(shopIcon, 0, 0, 80, 80,this);

				g.setColor(Color.WHITE);
				g.setFont(new Font("Serif", Font.BOLD, 35));
				g.drawString("買", 53-50, 475-400);
				g.drawString("物", 85-50, 485-400);

				//ボタンの線を消す
				setBorderPainted(false);
			}
		};

		PvPButton = new JButton(){
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				//全部座標
				g.drawImage(backIcon, 0, 0, 80, 80, (250+dx)*480/600, (400+dy)*480/600, ((250+dx)+80)*480/600, ((400+dy)+80)*480/600, this);

				g.drawImage(PvPIcon, 0, 0, 80, 80,this);

				g.setColor(Color.WHITE);
				g.setFont(new Font("Serif", Font.BOLD, 35));
				g.drawString("対", 255-250, 475-400);
				g.drawString("決", 285-250, 485-400);

				//ボタンの線を消す
				setBorderPainted(false);
			}
		};

		backButton.addActionListener(this);
		battleButton.addActionListener(this);
		statusButton.addActionListener(this);
		shopButton.addActionListener(this);
		PvPButton.addActionListener(this);

		pane.add(backButton);
		pane.add(battleButton);
		pane.add(statusButton);
		pane.add(shopButton);
		pane.add(PvPButton);

		backButton.setBounds(-50,-50, 148, 148);
		battleButton.setBounds(125+dx, 300+dy, 125, 125);
		statusButton.setBounds(145+dx, 200+dy,  80,  80);
		shopButton.setBounds(   50+dx, 400+dy,  80,  80);
		PvPButton.setBounds(250+dx, 400+dy,  80,  80);

		/*int w = size.width / 2 - (w1 + w2 + 5) / 2;
		bt1.setLocation(w, size.height - h1 - 20);
		add(bt1);
		bt2.setLocation(w + w1 + 5, size.height - h1 - 20);
		add(bt2);*/

	}

	public void paint(Graphics g){
		super.paint(g); // 親クラスの描画
		setBackground(Color.WHITE);

		g.drawImage(backIcon, 0, 0, 600, 600, 0, 0, 480, 480,this);//背景

		///////////////////左上●//////////////////
		g.drawImage(button1,-50,-50, 148, 148,this);

		g.setColor(Color.WHITE);
		g.setFont(new Font("Serif", Font.BOLD, 40));
		g.drawString("休", 3, 35);
		g.drawString("憩", 25, 60);
		///////////////////////////////////////////

		//////////////////遷移選択/////////////////
		g.drawImage(battleIcon, 125+dx, 300+dy, 125, 125, this);//真ん中　出撃
		g.drawImage(statusIcon, 145+dx, 200+dy,  80,  80, this);//上　　　ステータス
		g.drawImage(shopIcon,    50+dx, 400+dy,  80,  80, this);//左下　　ショップ
		g.drawImage(PvPIcon, 250+dx, 400+dy,  80,  80, this);//右下　　装備選択

		//真ん中　出撃
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Serif", Font.BOLD, 60));
		g.drawString("出", 130+dx, 410+dy);
		g.drawString("撃", 180+dx, 430+dy);

		//上　　　ステータス
		g.setColor(Color.WHITE);
		g.setFont(new Font("Serif", Font.BOLD, 35));
		g.drawString("編", 150+dx, 275+dy);
		g.drawString("成", 180+dx, 285+dy);

		//左下　　ショップ
		g.setColor(Color.WHITE);
		g.setFont(new Font("Serif", Font.BOLD, 35));
		g.drawString("買", 53+dx, 475+dy);
		g.drawString("物", 85+dx, 485+dy);

		//右下　　装備選択
		g.setColor(Color.WHITE);
		g.setFont(new Font("Serif", Font.BOLD, 35));
		g.drawString("対", 255+dx, 475+dy);
		g.drawString("決", 285+dx, 485+dy);
		///////////////////////////////////////////

	}

	// ボタンがクリックされたときの処理
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backButton){   //休憩
			System.out.println("backButton");
			mp.state = 0;
		}
		else if(e.getSource() == battleButton){ //出撃
			mp.state = 3;
			System.out.println("battleButton");
		}
		else if(e.getSource() == statusButton){ //編成
			mp.state = 4;
			System.out.println("statusButton");
		}
		else if(e.getSource() == shopButton){   //買い物
			mp.state = 18;
			System.out.println("shopButton");
		}
		else if(e.getSource() == PvPButton){ //装備
			System.out.println("weaponButton");
			mp.state = 19;
		}
	}
}