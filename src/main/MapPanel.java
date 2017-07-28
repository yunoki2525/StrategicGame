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
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JButton;

public class MapPanel extends JApplet implements ActionListener{
	AppletContext ac; // アプレットのコンテキスト
	Dimension size; // アプレットの大きさ
	MainPanel mp;

	//ボタン関係
	JButton[] bt;
	int[] w;
	int[] h;

	//ここからMapChoice
	int map_max = 8;
	int map_num;
	private int[][] initial_stage_position = {{70,500},{140,420},{160,470},{240,440},{300,400},{370,410},{430,250},{500,100}};

	int width;
	int height;
	Image backImage = null;
	Image workImage = null;
	BufferedImage tizu;

	MapStage[] stage = new MapStage[map_max];

	MapKeyManegement key;


	// コンストラクタ
	public MapPanel(AppletContext ac1, Dimension size1, MainPanel mp1) {
		ac = ac1;
		size = size1;
		mp = mp1;
		String[] str1 ={"メニュー","ストーリー","マップ","ステータス","バトル","ゲームオーバー","バトルスクリーン"};//ボタンの名前の配列
		bt = new JButton[str1.length];

		setSize(size);

		// ボタンの配置
		Font f = new Font("SansSerif", Font.BOLD, 20);
		FontMetrics fm = getFontMetrics(f);
		w = new int[str1.length];
		h = new int[str1.length];

		for(int i = 0 ; i < str1.length ; i++){
			w[i] = fm.stringWidth(str1[i]) + 40;
			h[i] = fm.getHeight() + 10;
			bt[i] = new JButton(str1[i]);
			bt[i].setFont(f);
			bt[i].addActionListener(this); // アクションリスナ
			bt[i].setSize(w[i], h[i]);
		}

		//ここからMapChoice
		key = new MapKeyManegement(this,mp);
		tizu = new BufferedImage(100,100,BufferedImage.TYPE_INT_ARGB);

		try{
			tizu = ImageIO.read(new File("resource/Image/map.gif"));
		}catch(Exception e){
			e.printStackTrace();
			tizu = null;
			System.out.println("画像読み込み失敗");
		}

		for(int i = 0; i < map_max; i++){
			stage[i] = new MapStage(initial_stage_position[i]);
		}

		Container pane = getContentPane();
		pane.setSize(600,600);
		pane.setBackground(Color.white);

		pane.setLayout(null);

//		pane.add(bt[0]);
//		bt[0].setBounds(0, 0, w[0], h[0]);
//		pane.add(bt[1]);
//		bt[1].setBounds(0, 40, w[1], h[1]);

		/*int w = size.width / 2 - (w1 + w2 + 5) / 2;
		bt1.setLocation(w, size.height - h1 - 20);
		add(bt1);
		bt2.setLocation(w + w1 + 5, size.height - h1 - 20);
		add(bt2);*/
	}

	public void paint(Graphics g){
		super.paint(g);

		//ここからMapChoice
		drawFigure(g);
	}

	// ボタンがクリックされたときの処理
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt[0]) //GameMenuPanelへ
			mp.state = 1;
		else if(e.getSource() == bt[1]){//StoryPanelへ
			mp.setNextPanel(0);
			mp.setStoryNum(map_num);//mapnumで代用？
			mp.state = 2;
		}
		else if(e.getSource() == bt[2])//GameMapPanel
			mp.state = 3;
		else if(e.getSource() == bt[3])//StatePanel
			mp.state = 4;
		else if(e.getSource() == bt[4])//BattlePanel
			mp.state = 5;
		else if(e.getSource() == bt[5])//GameOverPanel
			mp.state = 6;
		else if(e.getSource() == bt[6])//GameOverPanel
			mp.state = 7;
	}

	//ここからMapChoice
	public void drawFigure(Graphics g){
		if(backImage == null | width != getWidth() || height != getHeight()){
			width = getWidth();
			height = getHeight();
			createBackImage();
		}
		createWorkImage();
		g.drawImage(workImage,0,0,this);
	}

	public void createBackImage(){
		backImage = createImage(width,height);
		Graphics g = backImage.getGraphics();

		g.drawImage(tizu, 0,0,width,height,this);
	}

	public void createWorkImage(){
		workImage = createImage(width,height);
		Graphics g = workImage.getGraphics();

		g.drawImage(backImage, 0,0,this);

		g.setColor(Color.BLACK);
		for(int i = 0; i < map_max; i++){
		if(i == map_num)g.setColor(Color.cyan);
		g.fillOval(stage[i].position[0] ,stage[i].position[1] , 20,20);

		if(i == map_num)g.setColor(Color.BLACK);
		}
		System.out.println(map_num);
	}

	public void updateMap_Num(int map_num){
		this.map_num = map_num;
		repaint();
	}


}