package main;

import java.applet.AppletContext;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JButton;

public class TitlePanel extends JApplet implements ActionListener, KeyListener
// public class TitlePanel extends JPanel implements ActionListener
{
	boolean in_game = true;
	AppletContext ac; // アプレットのコンテキスト
	Dimension size; // アプレットの大きさ
	MainPanel mp;
	JButton startbutton,continuebutton;
	Image backImage,TitleImage;
	ItemControl item;

	int width,height;
	
	boolean[] cleared;

	// コンストラクタ
	public TitlePanel(AppletContext ac1, Dimension size1, MainPanel mp1)
		{
			Container pane = getContentPane();

			ac   = ac1;
			size = size1;
			mp   = mp1;

			item= new ItemControl();
			setSize(size);

			pane.setSize(size);
						// レイアウトマネージャの停止
			pane.setLayout(null);
						// 背景色の設定
			pane.setBackground(Color.white);
						// ボタンの配置
			Font f = new Font("SansSerif", Font.BOLD, 20);
			FontMetrics fm = getFontMetrics(f);

			try{
				backImage = ImageIO.read(new File("resoruce/Image/title.jpg"));
				TitleImage = ImageIO.read(new File("resoruce/Image/地元愛1.png"));
			}catch(IOException e){
				throw new RuntimeException(e);
			}

			String str = "初めから";
			String str1= "続きから";
			int w = fm.stringWidth(str) + 40;
			int h = fm.getHeight() + 10;
			int w1 = fm.stringWidth(str1) + 40;
			int h1 = fm.getHeight() + 10;
			startbutton = new JButton(str);
			continuebutton = new JButton(str1);

			startbutton.setFont(f);
			startbutton.addActionListener(this);
			startbutton.setSize(w, h);
			startbutton.setLocation(size.width/2-w/2, 5);//文字列の幅と高さを元にボタンのサイズを決定し，中央に配置しています

			continuebutton.setFont(f);
			continuebutton.addActionListener(this);
			continuebutton.setSize(w1, h1);
			continuebutton.setLocation(size.width/2-w1/2, 5);//文字列の幅と高さを元にボタンのサイズを決定し，中央に配置しています

			pane.add(startbutton);
			pane.add(continuebutton);

			startbutton.setBounds(300-(w+15),500, w, h);
			continuebutton.setBounds(300+15,500, w1, h1);

			addKeyListener(this);

		}

	// 描画
	public void paint(Graphics g) {
		super.paint(g); // 親クラスの描画
		FontMetrics fm;
		Font f;
		String str1 = "Game Title";
		int w, h;

		startbutton.repaint();
		continuebutton.repaint();
//
//		f = new Font("Serif", Font.BOLD, 25);
//		fm = g.getFontMetrics(f);
//		str1 = str + " (Serif)";
//		w = fm.stringWidth(str1);
//		h = fm.getHeight() + 60;
//		g.setFont(f);
//		g.drawString(str1, size.width / 2 - w / 2, h);
//
//		f = new Font("SansSerif", Font.BOLD, 25);
//		fm = g.getFontMetrics(f);
//		str1 = str + " (SansSerif)";
//		w = fm.stringWidth(str1);
//		h = h + fm.getHeight() + 5;
//		g.setFont(f);
//		g.drawString(str1, size.width / 2 - w / 2, h);
//
//		f = new Font("Dialog", Font.BOLD, 25);
//		fm = g.getFontMetrics(f);
//		str1 = str + " (Dialog)";
//		w = fm.stringWidth(str1);
//		h = h + fm.getHeight() + 5;
//		g.setFont(f);
//		g.drawString(str1, size.width / 2 - w / 2, h);
//
//		f = new Font("DialogInput", Font.BOLD, 25);
//		fm = g.getFontMetrics(f);
//		str1 = str + " (DialogInput)";
//		w = fm.stringWidth(str1);
//		h = h + fm.getHeight() + 5;
//		g.setFont(f);
//		g.drawString(str1, size.width / 2 - w / 2, h);
//
		g.drawImage(backImage,0,0,this);
		width = getWidth();
		height = getHeight();
		w = (width-TitleImage.getWidth(this))/2;
		h = (height-TitleImage.getHeight(this))/2;
		g.drawImage(TitleImage,w,h,this);

		f = new Font("Serif", Font.PLAIN, 20);
		fm = g.getFontMetrics(f);
		str1 = "ゲーム開始：「 s 」キー";
		w = fm.stringWidth(str1);
		h = size.height - fm.getHeight() - 10;
		g.setFont(f);
		//g.drawString(str1, size.width / 2 - w / 2, h);

		g.setColor(Color.ORANGE);
		f = new Font("Dialog", Font.BOLD, 25);
		fm = g.getFontMetrics(f);
		str1 = "日本風戦略シミュレーションゲーム";
		w = fm.stringWidth(str1);
		h = h + fm.getHeight() + 5;
		g.setFont(f);
		g.drawString(str1, size.width / 2 - w / 2, h-200);

		// この Component が入力フォーカスを取得することを要求
		this.requestFocusInWindow();
	}

	// ボタンがクリックされたときの処理
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startbutton) {
			////////////////////////////////////通常ゲーム側初期化//////////////////////////////////////////////////////
			item.readItem(true);
			item.writeItem(true);
			try{
				BufferedReader br = new BufferedReader(new FileReader("../src/Money/Money"));
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("../src/Money/Money1")));
				String str = br.readLine();
				System.out.println(str);
				pw.println(str);
				br.close();
				pw.close();
			}catch(IOException er){
				throw new RuntimeException(er);
			}
			try{
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("../src/Weapon/OwnWeapon")));
				pw.println(0);
				pw.close();
			}catch(IOException er){
				throw new RuntimeException(er);
			}
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			//////////////////////////////////////////////PVP側初期化////////////////////////////////////////////////////////////////
			item.readItem(true);
			item.writeItem(MainPanel.everyone);
			try{
				BufferedReader br = new BufferedReader(new FileReader("../src/Money/Money"));
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("../src/Money/Money1")));
				String str = br.readLine();
				System.out.println(str);
				pw.println(str);
				br.close();
				pw.close();
			}catch(IOException er){
				throw new RuntimeException(er);
			}
			try{
				BufferedReader br = new BufferedReader(new FileReader("../src/Money/Money"));
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("../src/Money/Player1Money")));
				String str = br.readLine();
				System.out.println(str);
				pw.println(str);
				br.close();
				pw.close();
			}catch(IOException er){
				throw new RuntimeException(er);
			}
			try{
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("../src/Weapon/Player1Weapon")));
				pw.println(0);
				pw.close();
			}catch(IOException er){
				throw new RuntimeException(er);
			}
			try{
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("../src/Weapon/Player2Weapon")));
				pw.println(0);
				pw.close();
			}catch(IOException er){
				throw new RuntimeException(er);
			}
			
			for(int i = 0;i<8;i++){
				mp.cleared[i] = false;
			}
			try{
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("../src/Stage/Cleared")));
				for(int i= 0;i<mp.cleared.length;i++){
					pw.print(Boolean.toString(mp.cleared[i])+",");
				}
				pw.close();
			}catch(IOException er){
				throw new RuntimeException(er);
			}
			/////////////////////////////////////////////////////////////////////////////////////////////////////
			mp.state = 1;
		}else if (e.getSource() == continuebutton) {
			try{
				BufferedReader br = new BufferedReader(new FileReader("../src/Stage/Cleared"));
				String[] tokens = br.readLine().split(",");
				cleared=new boolean[tokens.length];
				for(int i = 0;i<tokens.length;i++)cleared[i]=Boolean.valueOf(tokens[i]);
				mp.cleared = Arrays.copyOf(cleared,8);
				br.close();
			}catch(IOException er){
				throw new RuntimeException(er);
			}
			mp.state = 1;
		}
	}

	// キーが押されたときの処理（イベントリスナ）
	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyCode() == 83) // 「s」キー
			mp.state = 1;
	}

	public void keyReleased(KeyEvent ke) {
	}

	public void keyTyped(KeyEvent ke) {
	}
	// キーが押されたときの処理（イベントアダプタ）
	/*
	 * class Key extends KeyAdapter { public void keyPressed(KeyEvent ke) { if
	 * (ke.getKeyCode() == 83) // 「s」キー mp.state = 1; } }
	 */
}