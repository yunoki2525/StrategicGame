package main;

import java.applet.AppletContext;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.JApplet;
import javax.swing.JButton;

public class SortiePositionSetPanel extends JApplet implements ActionListener, KeyListener{
	AppletContext ac; // アプレットのコンテキスト
	Dimension size; // アプレットの大きさ
	MainPanel mp;
	JButton[] bt;


	int cells = 40;
	int shiftY = 35;

	int[] c_posi = {0, 0};   // cursor position の略  カーソルの位置座標
	int u_num =0;            // unit number の略   ユニットの数
	boolean sw = false;      // switch の略  キャラ選択と位置設定の切り替えの変数

	int[][] map;
	int[][] field;

	Piece[] enemy;
	Piece[] friend;

	SkillList skill_list;
	WeaponList weapon_list;

	int icon_w = 29;
	int icon_h = 32;
	int[] icon_posi = {icon_w,0};

	int width = 0;// ウィンドウの幅
	int height = 0;// ウィンドウの高さ
	Image backImage = null;// 背景画像バッファ
	Image workImage = null;// 作業画像バッファ

	Image[] map_image = new Image[7]; //

	Clip mapMusic;


	int[] visible_start_posi = new int[2];
	int visible_width = 15;
	int visible_height = 10;




	// コンストラクタ
	public SortiePositionSetPanel(AppletContext ac1, Dimension size1, MainPanel mp1, int[][] map, int[][] field,Piece[] friend, Piece[] enemy,Clip mapMusic) {
		ac = ac1;
		size = size1;
		mp = mp1;
		String[] str1 ={"メニュー","ストーリー","マップ","ステータス","バトル","ゲームオーバー",
				"バトルスクリーン","出撃キャラ選択","出撃位置設定"};//ボタンの名前の配列
		bt = new JButton[str1.length];

		// ボタンの配置
		Font f = new Font("SansSerif", Font.BOLD, 20);
		FontMetrics fm = getFontMetrics(f);

		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());

		for(int i = 0 ; i < str1.length ; i++){
			int w = fm.stringWidth(str1[i]) + 40;
			int h = fm.getHeight() + 10;
			bt[i] = new JButton(str1[i]);
			bt[i].setFont(f);
			bt[i].addActionListener(this); // アクションリスナ
			bt[i].setSize(w, h);

			if(i==8)
				continue;

			//	pane.add(bt[i]);
		}


		this.map = map;
		this.field = field;
		this.friend = friend;
		this.enemy = enemy;

		this.mapMusic = mapMusic;

		c_posi = Arrays.copyOf(this.friend[0].position, 2);


		setSize(size);


		addKeyListener(this);
		pane.setFocusable(true);
		pane.setBackground(Color.WHITE);
		//	pane.setLayout(null);

	}


	public void paint(Graphics g) {

		width = getWidth();// ウィンドウの幅を得る
		height = getHeight();// ウィンドウの高さを得る
		if (backImage == null) {
			createBackImage();// 背景画像(backImage)の作成
		}
		createWorkImage();// 作業画像(workImage)の作成
		g.drawImage(workImage, 0, 0, this);// 作業画像をアプレットに描画
	}
	private void createWorkImage() {
		workImage = createImage(map[0].length*cells, map.length*cells+170);// 作業バッファの生成
		Graphics g = workImage.getGraphics();// 作業バッファのグラフィックス
		//		g.drawImage(backImage, 0, 170, this);// 背景画像を作業バッファにコピー
		g.drawImage(backImage, 0,170,0+visible_width*cells,170+visible_height*cells,
				visible_start_posi[0] *cells , visible_start_posi[1] *cells ,
				(visible_start_posi[0]+visible_width) *cells ,(visible_start_posi[1]+visible_height) * cells, this);// 背景画像を作業バッファにコピー

		g.drawString("出撃するユニットの初期位置を決定してください", 50, 20);

		for(int i = 0; i < friend.length; i++)
			g.drawString(friend[i].unit.name, 35 , i*15 + 50);


		int x0=350; int y0=50;
		g.drawImage(friend[u_num].unit.face_image,x0 - 110,y0 -20,100,100,this);
		g.drawString(" HP :"+friend[u_num].HP, 0 + x0, 0 + y0);
		g.drawString(" SP:"+friend[u_num].SP,80 + x0, 0 + y0);
		g.drawString(" ATK :"+friend[u_num].ATK, 0 + x0, 20 + y0);
		g.drawString(" DEF:"+friend[u_num].DEF, 80 + x0, 20 + y0);
		g.drawString(" INT :"+friend[u_num].INT, 0 + x0, 40 + y0);
		g.drawString(" MEN :"+friend[u_num].MEN, 80 + x0, 40 + y0);
		g.drawString(" DEX :"+friend[u_num].DEX, 0 + x0, 60 + y0);
		g.drawString(" AGI :"+friend[u_num].AGI, 80 + x0, 60 + y0);
		g.drawString(" LUCK :"+friend[u_num].LUCK, 0 + x0, 80 + y0);


		g.fillOval( 25 ,u_num*15 +45, 4 , 4 );
		x0 = -3; y0 = 170;
		for (int i = 0; i < friend.length; i++) {
			icon_posi[1] = icon_h * friend[i].direction;
			int x = (friend[i].position[0] -visible_start_posi[0])* cells;
			int y = (friend[i].position[1] -visible_start_posi[1])* cells;

			if(y > 0){
				g.drawImage(friend[i].unit.icon, x + x0,  y + y0,
						icon_w +x + x0 ,icon_h +y + y0 , icon_posi[0], icon_posi[1], icon_posi[0]+icon_w, icon_posi[1]+icon_h, this);
				// System.out.println("map["+friend[i].position[1]+"]["+friend[i].position[0]+"]に味方描画");
			}
		}

		g.setColor(new Color(255 , 0 , 0));
		g.drawRect((c_posi[0] - visible_start_posi[0])* cells + x0, (c_posi[1] - visible_start_posi[1]) * cells + y0, cells, cells);


		g.drawString("バトルに遷移 : n", 450, 155);
		g.drawString("向き変更 : d", 450, 165);
	}


	public void createBackImage() {// 背景画像を作成する
		//		Image[] map_image = new Image[7]; //
		//	System.out.println("create back image !");
		try {
			map_image[0] = ImageIO.read(new File(
					"resource/Image/mapchip/Field_plain.png"));
			map_image[1] = ImageIO.read(new File(
					"resource/Image/mapchip/Field_sea.png"));
			map_image[2] = ImageIO.read(new File(
					"resource/Image/mapchip/Field_mountain.png"));
			map_image[3] = ImageIO.read(new File(
					"resource/Image/mapchip/Field_wood.png"));
			map_image[4] = ImageIO.read(new File(
					"resource/Image/mapchip/Field_fortress.png"));
			map_image[5] = ImageIO.read(new File(
					"resource/Image/mapchip/Field_road.png"));
			map_image[6] = ImageIO.read(new File(
					"resource/Image/mapchip/Field_noentry.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		backImage = createImage(map[0].length*cells, map.length*cells);// 背景バッファの生成
		Graphics g = backImage.getGraphics();// 背景バッファのグラフィックス
		// 背景画像を描く
		g.setColor(Color.BLACK);
		//int x0=-3; int y0 = 170;
		int x0 =-3; int y0 =0;
		for (int i = 0; i < field[0].length; i++) {
			for (int j = 0; j < field.length; j++) {
				switch (field[j][i]) {
				case BattleView.plain:
					g.drawImage(map_image[BattleView.plain], i * cells +x0, j * cells +y0,
							(i + 1) * cells +x0, (j + 1) * cells +y0, 0, 0, cells, cells, this);
					break;
				case BattleView.sea:
					g.drawImage(map_image[BattleView.sea], i * cells +x0, j * cells +y0,
							(i + 1) * cells +x0, (j + 1) * cells +y0, 0, 0, cells, cells, this);
					break;
				case BattleView.mountain:
					g.drawImage(map_image[BattleView.mountain], i * cells +x0, j * cells +y0,
							(i + 1) * cells +x0, (j + 1) * cells +y0, 0, 0, cells,
							cells, this);
					break;
				case BattleView.wood:
					g.drawImage(map_image[BattleView.wood], i * cells +x0, j * cells +y0,
							(i + 1) * cells +x0, (j + 1) * cells +y0, 0, 0, cells, cells, this);
					break;
				case BattleView.fortress:
					g.drawImage(map_image[BattleView.fortress], i * cells +x0, j * cells +y0,
							(i + 1) * cells +x0, (j + 1) * cells +y0, 0, 0, cells,
							cells, this);
					break;
				case BattleView.road:
					g.drawImage(map_image[BattleView.road], i * cells +x0, j * cells +y0,
							(i + 1) * cells +x0, (j + 1) * cells +y0, 0, 0, cells, cells, this);
					break;
				case BattleView.noentry:
					g.drawImage(map_image[BattleView.noentry], i * cells +x0, j * cells +y0,
							(i + 1) * cells +x0, (j + 1) * cells +y0, 0, 0, cells,
							cells, this);
					break;

				}
				System.out.print(field[j][i]+" ");
			}
			System.out.println();
		}
		g.setColor(new Color(0 , 0 , 255, 100));
		for(int i=0 ; i<map[0].length ; i++){
			for(int j=0 ; j<map.length ; j++ ){
				if(map[j][i] == 9 || map[j][i] == 1) g.fillRect(i * cells + x0 , j * cells + y0 , cells , cells);
			}
		}

	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public void keyPressed(KeyEvent e) {
		int keyChar = e.getKeyCode();
		if(sw){

			if (keyChar == KeyEvent.VK_UP) {
				if(cursorMovable(c_posi[0],c_posi[1]-1) )c_posi[1]--;
			}

			if (keyChar == KeyEvent.VK_DOWN) {
				if(cursorMovable(c_posi[0],c_posi[1]+1) )c_posi[1]++;
			}

			if (keyChar == KeyEvent.VK_LEFT){
				if(cursorMovable(c_posi[0]-1,c_posi[1]))c_posi[0]--;
			}

			if (keyChar == KeyEvent.VK_RIGHT){
				if(cursorMovable(c_posi[0]+1,c_posi[1]) )c_posi[0]++;
			}


			if (keyChar == KeyEvent.VK_SPACE){
				if(map[c_posi[1]][c_posi[0]] == 9 || (friend[u_num].position[0] == c_posi[0] && friend[u_num].position[1] == c_posi[1])){
					map[friend[u_num].position[1]][friend[u_num].position[0]] = 9;
					friend[u_num].position[0] = c_posi[0] ; friend[u_num].position[1] = c_posi[1] ;
					map[friend[u_num].position[1]][friend[u_num].position[0]] = 1;

					sw = false;
					//for(int i=0 ; i<map.length ; i++) System.out.println(" map : "+Arrays.toString(map[i]));


				}

			}
		}else{
			if (keyChar == KeyEvent.VK_UP) {
				if(u_num > 0 ){
					u_num--;
					c_posi[0] = friend[u_num].position[0] ; c_posi[1] = friend[u_num].position[1] ;
				}
			}

			if (keyChar == KeyEvent.VK_DOWN) {
				if(u_num < friend.length-1 ){
					u_num++;
					c_posi[0] = friend[u_num].position[0] ; c_posi[1] = friend[u_num].position[1] ;
				}
			}

			if (keyChar == KeyEvent.VK_SPACE){
				sw = true;
			}

			if (keyChar == KeyEvent.VK_D){
				changeDirection(u_num);
			}
		}
		if (keyChar == KeyEvent.VK_N){
			for(int i=0 ; i<map.length ; i++){
				for(int j=0 ; j<map[0].length ; j++){
					if(map[i][j] == 9) map[i][j] = 0;
				}
			}

			for(int i=0 ; i<map.length ; i++) System.out.println(" map : "+Arrays.toString(map[i]));
			/*
			 * ここでBattlMainに遷移
			 *
			 * 渡すデータは map, friend , enemy
			 *
			 *
			 *
			 */
			mp.showBP();
			mp.showP();
			mp.jumpBattlePanel(map,field,friend,enemy,mapMusic);
			mp.state = 5;

		}
		repaint();

	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	private void changeDirection(int u_num2) {
		if(friend[u_num].direction == Piece.up)           friend[u_num].direction = Piece.right;
		else if(friend[u_num].direction == Piece.right)   friend[u_num].direction = Piece.down;
		else if(friend[u_num].direction == Piece.down)    friend[u_num].direction = Piece.left;
		else                                              friend[u_num].direction = Piece.up;
		repaint();
	}



	// ボタンがクリックされたときの処理
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt[0]) //GameMenuPanelへ
			mp.state = 1;
		else if(e.getSource() == bt[1])//StoryPanelへ
			mp.state = 2;
		else if(e.getSource() == bt[2])//GameMapPanel
			mp.state = 3;
		else if(e.getSource() == bt[3])//StatePanel
			mp.state = 4;
		else if(e.getSource() == bt[4])//BattlePanel
			mp.state = 5;
		else if(e.getSource() == bt[5])//GameOverPanel
			mp.state = 6;
		else if(e.getSource() == bt[6])//BattleScreenPanel
			mp.state = 7;
		else if(e.getSource() == bt[7])//SortieUnitSelectPanel
			mp.state = 8;
		else if(e.getSource() == bt[8])//SortieUnitSetPanel
			mp.state = 9;
	}

	public boolean cursorMovable(int x, int y){
	//	System.out.println("check ("+x+" , "+y+")");
		boolean re = (x >= 0 && x < map[0].length && y >= 0 && y < map.length) ? true : false;
		if(re){
			if( x-visible_start_posi[0] >= visible_width) visible_start_posi[0]++;
			else if( x  < visible_start_posi[0])visible_start_posi[0]--;
			else if( y-visible_start_posi[1] >= visible_height) visible_start_posi[1]++;
			else if( y  <  visible_start_posi[1] )visible_start_posi[1]--;
		}
		return re;
	}

}