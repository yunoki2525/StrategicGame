package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JApplet;

public class BattleView extends JApplet implements Runnable {
	MainPanel mp;

	BattlePanel panel;

	int[][] map;
	int[][] field;
	int[][] move_map;
	Piece[] enemy;
	Piece[] friend;

	Piece ptr = null;
	int ptr_idx;

	int skill_idx;
	int magic_idx;
	int item_id;
	int mode_pattern = 0;

	static final int default_mode = 0;
	static final int item_mode = 1;
	static final int target_mode = 2;
	static final int magic_mode = 3;
	static final int move_mode = 4;
	static final int enemy_mode = 5;

	boolean victory = false;
	boolean defeat = false;
	boolean win_or_lose =false; // true -> win/ false -> lose
	Clip win;
	Clip lose;

	int[] cursor_position = new int[2];

	int cells = 40;
	int shiftY = 35;

	int icon_w = 29;
	int icon_h = 32;
	int[] icon_posi = { icon_w, 0 };

	boolean PvP = false;
	boolean myTurn = true;
	// //////// BattleScreen へ送る変数群 /////////////////
	int effect_id = 0;
	boolean hit = false;
	boolean critical = true;
	int damage = 0;
	boolean drain_flag = false;
	int drain = 0;
	boolean counter = false;
	boolean c_hit = false;
	boolean c_critical = false;
	int c_damage = 0;
	boolean[] s_condition = new boolean[7];
	boolean[] a_buff = new boolean[2];
	boolean[] r_buff = new boolean[2];

	// ////////////////////////////////////////////////////
	Image[] map_image = new Image[7]; //

	static final int plain = 0;
	static final int sea = 1;
	static final int mountain = 2;
	static final int wood = 3;
	static final int fortress = 4;
	static final int road = 5;
	static final int noentry = 6;

	int width = 0;// ウィンドウの幅
	int height = 0;// ウィンドウの高さ
	Image backImage = null;// 背景画像バッファ
	Image workImage = null;// 作業画像バッファ

	Thread th = null;

	Clip mapMusic;

	int[] visible_start_posi = new int[2];
	int visible_width = 15;
	int visible_height = 10;




	public BattleView(BattlePanel panel, MainPanel mp, int[][] map,
			int[][] field, Piece[] enemy, Piece[] friend, Clip mapMusic) {
		this.mp = mp;
		this.panel = panel;
		this.map = new int[map.length][map[0].length];
		this.field = new int[field.length][field[0].length];
		this.move_map = new int[map.length][map[0].length];
		for (int i = 0; i < this.map.length; i++) {
			this.map[i] = Arrays.copyOf(map[i], map[i].length);
			this.field[i] = Arrays.copyOf(field[i], field[i].length);
			this.move_map[i] = Arrays.copyOf(map[i], map[i].length);
		}
		// this.move_map = map;
		this.friend = Arrays.copyOf(friend, friend.length);
		this.enemy = Arrays.copyOf(enemy, enemy.length);
////////////////////////////////////////////////////////////////////////////////
		this.mapMusic = mapMusic;
		FloatControl control = (FloatControl)mapMusic.getControl(FloatControl.Type.MASTER_GAIN);
		controlByLinearScalar(control, 0.7);

		mapMusic.loop(999);
///////////////////////////////////////////////////////////////////////////////
		try {
			map_image[0] = ImageIO.read(new File(
					"../bin/Image/mapchip/Field_plain.png"));
			map_image[1] = ImageIO.read(new File(
					"../bin/Image/mapchip/Field_sea.png"));
			map_image[2] = ImageIO.read(new File(
					"../bin/Image/mapchip/Field_mountain.png"));
			map_image[3] = ImageIO.read(new File(
					"../bin/Image/mapchip/Field_wood.png"));
			map_image[4] = ImageIO.read(new File(
					"../bin/Image/mapchip/Field_fortress.png"));
			map_image[5] = ImageIO.read(new File(
					"../bin/Image/mapchip/Field_road.png"));
			map_image[6] = ImageIO.read(new File(
					"../bin/Image/mapchip/Field_noentry.png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Container pane = getContentPane();
		pane.setBackground(Color.WHITE);
	}

	public void createBackImage() {// 背景画像を作成する
		System.out.println("create Back Image !! ");
		//backImage = createImage(width, height);// 背景バッファの生成
		backImage = createImage(map[0].length*cells, map.length*cells);
		Graphics g = backImage.getGraphics();// 背景バッファのグラフィックス
		// 背景画像を描く
		g.setColor(Color.BLACK);
		//int x0 = visible_start_posi[0]; int y0 = visible_start_posi[1];
		int x0 =0; int y0 = 0;
		for (int i = 0; i < map[0].length; i++) {
			for (int j = 0; j < map.length; j++) {
				switch (field[j +y0][i +x0]) {
				case plain:
					g.drawImage(map_image[plain], (i +x0) * cells, (j +y0) * cells ,
							(i + 1 +x0) * cells , (j + 1 +y0) * cells, 0, 0, cells, cells, this);
					break;
				case sea:
					g.drawImage(map_image[sea], (i +x0) * cells, (j +y0) * cells,
							(i + 1 +x0) * cells, (j + 1 +y0) * cells, 0, 0, cells, cells, this);
					break;
				case mountain:
					g.drawImage(map_image[mountain], (i +x0) * cells, (j +y0) * cells,
							(i + 1 +x0) * cells, (j + 1 +y0) * cells, 0, 0, cells,
							cells, this);
					break;
				case wood:
					g.drawImage(map_image[wood], (i +x0) * cells, (j +y0) * cells,
							(i + 1 +x0) * cells, (j + 1 +y0) * cells, 0, 0, cells, cells, this);
					break;
				case fortress:
					g.drawImage(map_image[fortress], (i +x0) * cells, (j +y0) * cells,
							(i + 1 +x0) * cells, (j + 1 +y0) * cells, 0, 0, cells,
							cells, this);
					break;
				case road:
					g.drawImage(map_image[road], (i +x0) * cells, (j +y0) * cells,
							(i + 1 +x0) * cells,(j + 1 +y0) * cells, 0, 0, cells, cells, this);
					break;
				case noentry:
					g.drawImage(map_image[noentry], (i +x0) * cells, (j +y0) * cells,
							(i + 1 +x0) * cells, (j + 1 +y0) * cells, 0, 0, cells,
							cells, this);
					break;
				}
				System.out.print(field[j +y0][i +x0]+" ");
			}
			System.out.println();
		}
	}

	public void createWorkImage() {// 作業画像を作成する
		//workImage = createImage(width, height);// 作業バッファの生成
		workImage = createImage(map[0].length*cells, map.length*cells);
		Graphics g = workImage.getGraphics();// 作業バッファのグラフィックス
		g.drawImage(backImage, 0,0,width,height,visible_start_posi[0] *cells , visible_start_posi[1] *cells ,
				(visible_start_posi[0]+visible_width) *cells ,(visible_start_posi[1]+visible_height) * cells, this);// 背景画像を作業バッファにコピー
//		g.drawImage(backImage, 0,0, this);// 背景画像を作業バッファにコピー

		Graphics2D g2 = (Graphics2D)g;

		for (int i = 0; i < enemy.length; i++) {
			icon_posi[1] = icon_h * enemy[i].direction;
			int x = (enemy[i].position[0] -visible_start_posi[0])* cells;
			int y = (enemy[i].position[1] -visible_start_posi[1])* cells;

			if(!enemy[i].alive){
				g2.rotate(Math.PI/2,x+cells/2 -4,y+cells/2 +4);
				icon_posi[0] = 0; icon_posi[1] = icon_h*2;
			}
			g2.drawImage(enemy[i].unit.icon, x, y, icon_w + x, icon_h + y,
					icon_posi[0], icon_posi[1], icon_posi[0] + icon_w,
					icon_posi[1] + icon_h, this);
			// System.out.println("map["+enemy[i].position[1]+"]["+enemy[i].position[0]+"]に敵描画");
			if(!enemy[i].alive){
				g2.rotate(-Math.PI/2,x+cells/2 -4,y+cells/2 +4);
				icon_posi[0] = icon_w;// icon_posi[1] = icon_h;
			}
		}
		for (int i = 0; i < friend.length; i++) {
			icon_posi[1] = icon_h * friend[i].direction;
			int x = (friend[i].position[0] -visible_start_posi[0])* cells;
			int y = (friend[i].position[1] -visible_start_posi[1])* cells;

			if(!friend[i].alive){
				g2.rotate(-Math.PI/2,x+cells/2 ,y+cells/2 -2);
				icon_posi[0] = 0; icon_posi[1] = icon_h;
			}
			g2.drawImage(friend[i].unit.icon, x, y, icon_w + x, icon_h + y,
					icon_posi[0], icon_posi[1], icon_posi[0] + icon_w,
					icon_posi[1] + icon_h, this);
			// System.out.println("map["+friend[i].position[1]+"]["+friend[i].position[0]+"]に味方描画");
			if(!friend[i].alive){
				g2.rotate(Math.PI/2,x+cells/2 ,y+cells/2 -2);
				icon_posi[0] = icon_w;// icon_posi[1] = icon_h;
			}
		}
		if(victory){
			g.setFont( new Font("SansSerif", Font.BOLD, 90));
			g.setColor(new Color(255, 0, 0));
			g.drawString("VICTORY", 120, 200);
		}else if(defeat){
			g.setFont( new Font("SansSerif", Font.BOLD, 90));
			g.setColor(new Color(0, 0, 255));
			g.drawString("DEFEAT", 120, 200);
		}else{
			System.out.println("at panit(switch前) /カーソルのポジション(x,y) =("+cursor_position[0]+","+cursor_position[1]+")");
			switch (mode_pattern) {
			case default_mode:
				 System.out.println("at panit(default) /カーソルのポジション(x,y) =("+cursor_position[0]+","+cursor_position[1]+")");
				g.setColor(new Color(255, 0, 0));
				g.drawRect((cursor_position[0]-visible_start_posi[0]) * cells, (cursor_position[1]-visible_start_posi[1]) * cells,
						cells, cells);

				break;

			case target_mode:
				System.out.println("at panit(target) /カーソルのポジション(x,y) =("+cursor_position[0]+","+cursor_position[1]+")");
				// System.out.println("発動するスキル = "+ptr.unit.skill[skill_idx].name);
				g.setColor(new Color(0, 0, 255, 100));
				for (int i = 0; i < map.length; i++) {
					for (int j = 0; j < map[i].length; j++) {
						if (ptr.unit.skill[skill_idx].inRange(ptr.position,
								ptr.direction, j, i))
							g.fillRect((j-visible_start_posi[0]) * cells, (i-visible_start_posi[1]) * cells, cells, cells);

					}
				}

				// System.out.println("ターゲットのポジション(x,y) =("+target_position[0]+","+target_position[1]+")");
				g.setColor(new Color(255, 0, 0, 100));
				panel.missTargetStatus();
				for (int i = 0; i < map.length; i++) {
					for (int j = 0; j < map[i].length; j++) {

						if (ptr.unit.skill[skill_idx].attackRange(ptr.direction, cursor_position, j, i)){
							g.fillRect((j-visible_start_posi[0]) * cells, (i-visible_start_posi[1]) * cells, cells, cells);
							if(!ptr.unit.skill[skill_idx].support && myTurn ||
									ptr.unit.skill[skill_idx].support && !myTurn){
								for(int k=0 ; k<enemy.length ; k++){
									if(enemy[k].position[0] == j && enemy[k].position[1] == i){
										panel.peekEnemyStatus(k);
									}
								}
							}else{
								for(int k=0 ; k<friend.length ; k++){
									if(friend[k].position[0] == j && friend[k].position[1] == i){
										panel.peekFriendStatus(k);
									}
								}
							}
						}

					}
				}
				break;

			case magic_mode:
				System.out.println("at panit(magic) /カーソルのポジション(x,y) =("+cursor_position[0]+","+cursor_position[1]+")");
				// System.out.println("発動するスキル = "+ptr.unit.magic[magic_idx].name);
				g.setColor(new Color(0, 0, 255, 100));
				g.setColor(new Color(0, 0, 255, 100));
				for (int i = 0; i < map.length; i++) {
					for (int j = 0; j < map[i].length; j++) {
						if (ptr.unit.magic[magic_idx].inRange(ptr.position,
								ptr.direction, j, i))
							g.fillRect((j-visible_start_posi[0]) * cells, (i-visible_start_posi[1]) * cells, cells, cells);

					}
				}

				// System.out.println("ターゲットのポジション(x,y) =("+target_position[0]+","+target_position[1]+")");
				g.setColor(new Color(255, 0, 0, 100));
				panel.missTargetStatus();
				for (int i = 0; i < map.length; i++) {
					for (int j = 0; j < map[i].length; j++) {
						if (ptr.unit.magic[magic_idx].attackRange(ptr.direction, cursor_position, j, i)){
							g.fillRect((j-visible_start_posi[0]) * cells, (i-visible_start_posi[1]) * cells, cells, cells);
							if(!ptr.unit.magic[magic_idx].support&& myTurn ||
									ptr.unit.magic[magic_idx].support && !myTurn){
								for(int k=0 ; k<enemy.length ; k++){
									if(enemy[k].position[0] == j && enemy[k].position[1] == i){
										panel.peekEnemyStatus(k);
									}
								}
							}else{
								for(int k=0 ; k<friend.length ; k++){
									if(friend[k].position[0] == j && friend[k].position[1] == i){
										panel.peekFriendStatus(k);
									}
								}
							}

						}
					}
				}
				break;
			case item_mode:
				System.out.println("at panit(item) /カーソルのポジション(x,y) =("+cursor_position[0]+","+cursor_position[1]+")");
				g.setColor(new Color(0, 0, 255, 100));
				for (int i = 0; i < map.length; i++) {
					for (int j = 0; j < map[i].length; j++) {
						if (ptr.unit.magic[0].inRange(ptr.position,ptr.direction, j, i)  ||
								(ptr.position[0] == j && ptr.position[1] == i ))
							g.fillRect((j-visible_start_posi[0]) * cells, (i-visible_start_posi[1]) * cells, cells, cells);
					}
				}
				g.setColor(new Color(255, 0, 0, 100));
				panel.missTargetStatus();
				for (int i = 0; i < map.length; i++) {
					for (int j = 0; j < map[i].length; j++) {
						if (cursor_position[0] == j && cursor_position[1] == i ){
							g.fillRect((j-visible_start_posi[0]) * cells, (i-visible_start_posi[1]) * cells, cells, cells);
							if(!myTurn){
								for(int k=0 ; k<enemy.length ; k++){
									if(enemy[k].position[0] == j && enemy[k].position[1] == i){
										panel.peekEnemyStatus(k);
									}
								}
							}else{
								for(int k=0 ; k<friend.length ; k++){
									if(friend[k].position[0] == j && friend[k].position[1] == i){
										panel.peekFriendStatus(k);
									}
								}
							}

						}
					}
				}

				break;
			case move_mode:
				System.out.println("at panit(move) /カーソルのポジション(x,y) =("+cursor_position[0]+","+cursor_position[1]+")");
				g.setColor(new Color(0, 0, 255, 100));
				// System.out.println("前");
				// for(int i=0 ; i<map.length ;
				// i++)System.out.println(Arrays.toString(move_map[i]));
				// System.out.println();
				panel.movable(ptr.position, ptr.position[0], ptr.position[1],
						ptr.MOVE);
				// for(int i=0 ; i<map.length ;
				// i++)System.out.println(Arrays.toString(move_map[i]));
				// System.out.println();
				for (int i = 0; i < map.length; i++) {
					for (int j = 0; j < map[i].length; j++) {
						if (move_map[i][j] == 5)
							g.fillRect((j-visible_start_posi[0]) * cells, (i-visible_start_posi[1]) * cells, cells, cells);

					}
				}
				// System.out.println("ターゲットのポジション(x,y) =("+target_position[0]+","+target_position[1]+")");
				g.setColor(new Color(255, 0, 0, 100));
				g.fillRect((cursor_position[0]-visible_start_posi[0]) * cells, (cursor_position[1]-visible_start_posi[1]) * cells,
						cells, cells);
				// panel.initMoveMap();

				break;

			case enemy_mode:
				g.setColor(new Color(255, 0, 0));
				g.drawRect((cursor_position[0]-visible_start_posi[0]) * cells, (cursor_position[1]-visible_start_posi[1]) * cells,
						cells, cells);
				break;
			}
		}
	}

	public void paint(Graphics g) {
		System.out.print("at view/ paint  /  ");
		System.out.println("corsor_position : "+Arrays.toString(cursor_position));
//		System.out.println("visible_start_posi : "+Arrays.toString(visible_start_posi));

		width = getWidth();// ウィンドウの幅を得る
		height = getHeight();// ウィンドウの高さを得る
		if (backImage == null) {
			createBackImage();// 背景画像(backImage)の作成
		}
		createWorkImage();// 作業画像(workImage)の作成
		g.drawImage(workImage, 0, 0, this);// 作業画像をアプレットに描画
	}

	public void updateMap(int[][] map) {
		for (int i = 0; i < this.map.length; i++)
			this.map[i] = Arrays.copyOf(map[i], map[i].length);
		// System.out.println("view map");
		// for(int i=0 ; i<map.length ;
		// i++)System.out.println(Arrays.toString(this.map[i]));
		/*
		 * for(int i=0 ; i<map.length ; i++){ for(int j=0; j<map[i].length ;
		 * j++) System.out.print(map[i][j]+"  ,  "); System.out.println(); }
		 */
	}

	public void targetSelect(Piece ptr, int skill_idx) {
		this.ptr = ptr;
		this.skill_idx = skill_idx;
		cursor_position[0] = ptr.position[0];
		cursor_position[1] = ptr.position[1];

		///System.out.println("cursor_position (x,y) = ( "+cursor_position[0]+" , "+cursor_position[1]+" )");
		while(!ptr.unit.skill[skill_idx].inRange(ptr.position, ptr.direction, cursor_position[0], cursor_position[1])){
		//	System.out.println("crusor_position (x,y) = ( "+cursor_position[0]+" , "+cursor_position[1]+" )");
			switch (ptr.direction) {
			case Piece.up:
				cursor_position[1]--;
				break;
			case Piece.right:
				cursor_position[0]++;
				break;
			case Piece.down:
				cursor_position[1]++;
				break;
			case Piece.left:
				cursor_position[0]--;
				break;
			}
		}
		panel.setCursorPosi(cursor_position);
		//System.out.println("cursor_position (x,y) = ( "+cursor_position[0]+" , "+cursor_position[1]+" )");

		mode_pattern = target_mode;
		repaint();
	}
	public void magicSelect(Piece ptr, int magic_idx) {
		this.ptr = ptr;
		this.magic_idx = magic_idx;
		cursor_position[0] = ptr.position[0];
		cursor_position[1] = ptr.position[1];

		while(!ptr.unit.magic[magic_idx].inRange(ptr.position, ptr.direction, cursor_position[0], cursor_position[1])){
			System.out.println("target_position (x,y) = ( "+cursor_position[0]+" , "+cursor_position[1]+" )");
			switch (ptr.direction) {
			case Piece.up:
				cursor_position[1]--;
				break;
			case Piece.right:
				cursor_position[0]++;
				break;
			case Piece.down:
				cursor_position[1]++;
				break;
			case Piece.left:
				cursor_position[0]--;
				break;
			}
		}
		panel.setCursorPosi(cursor_position);
		mode_pattern = magic_mode;
		//System.out.println("magic_mode");
		repaint();
	}
	public void itemSelect(Piece ptr, int item_id){
		this.ptr = ptr;
		this.item_id = item_id;
		cursor_position[0] = ptr.position[0];
		cursor_position[1] = ptr.position[1];
		panel.setCursorPosi(cursor_position);
		mode_pattern = item_mode;
		//System.out.println("item_mode");
		repaint();
	}

	public void moveSelect(Piece ptr) {
		this.ptr = ptr;
		cursor_position[0] = ptr.position[0];
		cursor_position[1] = ptr.position[1];
		panel.setCursorPosi(cursor_position);
		mode_pattern = move_mode;
		repaint();
	}

	public void updateView(int[] cursor_position) {
//		System.out.println("now /corsor_position : "+Arrays.toString(this.cursor_position));
//		System.out.println("com /corsor_position : "+Arrays.toString(cursor_position));
		this.cursor_position = Arrays.copyOf(cursor_position, 2);
		//System.out.println("updateView");

		if (mode_pattern == default_mode ||mode_pattern == enemy_mode) { /////////////ここ変更
			if (checkCursor() != null) {
				ptr = checkCursor();
				panel.showState(ptr);
			} else {
				ptr = null;
				panel.ptrLost();
			}

		}
		repaint();

	}

	public Piece checkCursor() {
		for (int i = 0; i < enemy.length; i++)
			if (enemy[i].position[0] == cursor_position[0]
					&& enemy[i].position[1] == cursor_position[1]) {
				// panel.showState(enemy[i]);
				ptr_idx = i;
				return enemy[i];
			}
		for (int i = 0; i < friend.length; i++)
			if (friend[i].position[0] == cursor_position[0]
					&& friend[i].position[1] == cursor_position[1]) {
				// panel.showState(friend[i]);
				ptr_idx = i;
				return friend[i];
			}
		return null;
	}

	public void changeMode() {
		//System.out.println("changeMode/  mode_pattern : "+ mode_pattern +" ->");

		if (mode_pattern == default_mode) {
			// System.out.println("changemode");
			if (ptr != null && map[ptr.position[1]][ptr.position[0]] == 1
					&& myTurn) {
				// /////////////////////////////////// 状態異常効果
				// //////////////////////////////////////////////
				if (ptr.alive && ptr.condition[Piece.freeze] <= 0
						&& ptr.condition[Piece.piyopiyo] <= 0
						&& ptr.condition[Piece.sleep] <= 0
						&& ptr.condition[Piece.paralysis] <= 0) {
					panel.changeSelect();
					panel.setState(0);
				}
			}
			if (PvP && ptr != null
					&& map[ptr.position[1]][ptr.position[0]] == -1 && !myTurn) {
				if (ptr.alive && ptr.condition[Piece.freeze] <= 0
						&& ptr.condition[Piece.piyopiyo] <= 0
						&& ptr.condition[Piece.sleep] <= 0
						&& ptr.condition[Piece.paralysis] <= 0) {
					panel.changeSelect();
					panel.setState(0);
				}
			}
		}
/////////////////////////////////////////////////////////////////////////ここ変更
		if(mode_pattern == enemy_mode){
			if (ptr != null
					&& map[ptr.position[1]][ptr.position[0]] == -1 && !myTurn) {
				if (ptr.alive && ptr.condition[Piece.freeze] <= 0
						&& ptr.condition[Piece.piyopiyo] <= 0
						&& ptr.condition[Piece.sleep] <= 0
						&& ptr.condition[Piece.paralysis] <= 0) {
					panel.changeSelect();
					panel.setState(0);
				}
			}
		}
////////////////////////////////////////////////////////////////////////
		if (mode_pattern == target_mode) {

			if (myTurn) {
				if (!ptr.unit.skill[skill_idx].support) {
					for (int i = 0; i < enemy.length; i++) {
						if (enemy[i].alive &&ptr.unit.skill[skill_idx].attackRange(
								ptr.direction, cursor_position,
								enemy[i].position[0], enemy[i].position[1])) {
							effect_id = ptr.unit.skill[skill_idx].effect_id;
							if (!ptr.acted) {
								panel.payCost(ptr_idx, skill_idx);
								mode_pattern = default_mode;
							}
							panel.attack(ptr_idx, i, skill_idx);

							if (enemy[i].alive) {
								panel.turnAround(ptr_idx, i);
								if (enemy[i].unit.skill[0].inRange(
										enemy[i].position, enemy[i].direction,
										ptr.position[0], ptr.position[1])
										&& enemy[i].condition[Piece.sleep] <= 0
										&& enemy[i].condition[Piece.paralysis] <= 0
										&& enemy[i].condition[Piece.piyopiyo] <= 0
										&& enemy[i].condition[Piece.freeze] <= 0) {
									counter = true;
									panel.counterAttack(i, ptr_idx);
								}
							}
							// /////////////////////////////////////////////////////////////////////////////////
							// ここで遷移
							System.out.println("BattleScreenへ");
							 showScreenParameta();
							// System.out.println("damage  "+damage);
							 musicStop();
							mp.jumpBattleScreenPanel(effect_id, hit, critical,
									damage, drain_flag, drain, counter, c_hit,
									c_critical, c_damage, ptr.unit.id,
									enemy[i].unit.id, ptr.HP, enemy[i].HP,
									ptr.SP, enemy[i].SP, ptr.unit.HP
									+ ptr.unit.weapon.HP,
									enemy[i].unit.HP + enemy[i].unit.weapon.HP,
									ptr.unit.SP, enemy[i].unit.SP,
									s_condition,a_buff,r_buff);
							mp.state = 7;
							initScreenParameta();
							// /////////////////////////////////////////////////////////////////////////////////
						}
					}

				} else {
					for (int i = 0; i < friend.length; i++) {
						if (friend[i].alive && ptr.unit.skill[skill_idx].attackRange(
								ptr.direction, friend[i].position,
								cursor_position[0], cursor_position[1])) {
							panel.support(ptr_idx, i, skill_idx);
						}
					}
					if (ptr.acted) {
						panel.payCost(ptr_idx, skill_idx);
						mode_pattern = default_mode;
					}
				}
			} else {
				if (!ptr.unit.skill[skill_idx].support) {
					for (int i = 0; i < friend.length; i++) {
						if (friend[i].alive &&ptr.unit.skill[skill_idx].attackRange(
								ptr.direction, cursor_position,
								friend[i].position[0], friend[i].position[1])) {
							effect_id = ptr.unit.skill[skill_idx].effect_id;
							if (!ptr.acted) {
								panel.payCost(ptr_idx, skill_idx);
								mode_pattern = default_mode;
								if(!PvP) mode_pattern = enemy_mode;/////////////////////////////////ここ変更
							}
							panel.attack(ptr_idx, i, skill_idx);

							if (friend[i].alive) {
								panel.turnAround(ptr_idx, i);
								if (friend[i].unit.skill[0].inRange(
										friend[i].position,
										friend[i].direction, ptr.position[0],
										ptr.position[1])
										&& friend[i].condition[Piece.sleep] <= 0
										&& friend[i].condition[Piece.paralysis] <= 0
										&& friend[i].condition[Piece.piyopiyo] <= 0
										&& friend[i].condition[Piece.freeze] <= 0) {
									counter = true;
									panel.counterAttack(i, ptr_idx);
								}
							}
							// /////////////////////////////////////////////////////////////////////////////////
							// ここで遷移
							musicStop();
							mp.jumpBattleScreenPanel(effect_id, hit, critical,
									damage, drain_flag, drain, counter, c_hit,
									c_critical, c_damage, ptr.unit.id,
									friend[i].unit.id, ptr.HP, friend[i].HP,
									ptr.SP, friend[i].SP, ptr.unit.HP+ptr.unit.weapon.HP,
									friend[i].unit.HP+friend[i].unit.weapon.HP, ptr.unit.SP,
									friend[i].unit.SP,
									s_condition,a_buff,r_buff);
							mp.state = 7;
							initScreenParameta();
							// /////////////////////////////////////////////////////////////////////////////////
						}
					}

				} else {
					for (int i = 0; i < friend.length; i++) {
						if ( friend[i].alive && ptr.unit.skill[skill_idx].attackRange(
								ptr.direction, friend[i].position,
								cursor_position[0], cursor_position[1])) {
							panel.support(ptr_idx, i, skill_idx);
						}
					}
					if (ptr.acted) {
						panel.payCost(ptr_idx, skill_idx);
						mode_pattern = default_mode;
						if(!PvP) mode_pattern = enemy_mode;/////////////////////////////////ここ変更
					}
				}
			}
			panel.ptrLost();
			panel.showState(ptr);
			try{
				Thread.sleep(500);
			}catch (InterruptedException e){

			}
		}
		if (mode_pattern == magic_mode) {

			if (myTurn) {
				if (!ptr.unit.magic[magic_idx].support) {
					for (int i = 0; i < enemy.length; i++) {
						if ( enemy[i].alive && ptr.unit.magic[magic_idx].attackRange(
								ptr.direction, cursor_position,
								enemy[i].position[0], enemy[i].position[1])) {
							effect_id = ptr.unit.magic[magic_idx].effect_id;
							if (!ptr.acted) {
								panel.MagicpayCost(ptr_idx, magic_idx);
								mode_pattern = default_mode;
							}

							panel.magicAttack(ptr_idx, i, magic_idx);

							if (enemy[i].alive) {
								panel.turnAround(ptr_idx, i);
								if (enemy[i].unit.magic[0].inRange(
										enemy[i].position, enemy[i].direction,
										ptr.position[0], ptr.position[1])
										&& enemy[i].condition[Piece.sleep] <= 0
										&& enemy[i].condition[Piece.paralysis] <= 0
										&& enemy[i].condition[Piece.piyopiyo] <= 0
										&& enemy[i].condition[Piece.freeze] <= 0) {
									counter = true;
									panel.counterAttack(i, ptr_idx);
								}
							}
							// /////////////////////////////////////////////////////////////////////////////////
							// ここで遷移
							System.out.println("BattleScreenへ");
							// showScreenParameta();
							// System.out.println("damage  "+damage);
							musicStop();
							mp.jumpBattleScreenPanel(effect_id, hit, critical,
									damage, drain_flag, drain, counter, c_hit,
									c_critical, c_damage, ptr.unit.id,
									enemy[i].unit.id, ptr.HP, enemy[i].HP,
									ptr.SP, enemy[i].SP, ptr.unit.HP
									+ ptr.unit.weapon.HP,
									enemy[i].unit.HP + enemy[i].unit.weapon.HP,
									ptr.unit.SP, enemy[i].unit.SP,
									s_condition,a_buff,r_buff);
							mp.state = 7;
							initScreenParameta();
							// /////////////////////////////////////////////////////////////////////////////////
						}
					}

				} else {
					for (int i = 0; i < friend.length; i++) {
						if ( friend[i].alive && ptr.unit.magic[magic_idx].attackRange(
								ptr.direction, friend[i].position,
								cursor_position[0], cursor_position[1])) {
							panel.magicSupport(ptr_idx, i, magic_idx);
						}
					}
					if (ptr.acted) {
						panel.MagicpayCost(ptr_idx, magic_idx);
						mode_pattern = default_mode;
					}
				}
			} else {
				if (!ptr.unit.magic[magic_idx].support) {
					for (int i = 0; i < friend.length; i++) {
						if ( friend[i].alive && ptr.unit.magic[magic_idx].attackRange(
								ptr.direction, cursor_position,
								friend[i].position[0], friend[i].position[1])) {
							effect_id = ptr.unit.magic[magic_idx].effect_id;
							if (!ptr.acted) {
								panel.MagicpayCost(ptr_idx, magic_idx);
								mode_pattern = default_mode;
								if(!PvP) mode_pattern = enemy_mode;/////////////////////////////////ここ変更
							}
							panel.magicAttack(ptr_idx, i, magic_idx);

							if (friend[i].alive) {
								panel.turnAround(ptr_idx, i);
								if (friend[i].unit.magic[0].inRange(
										friend[i].position,
										friend[i].direction, ptr.position[0],
										ptr.position[1])
										&& friend[i].condition[Piece.sleep] <= 0
										&& friend[i].condition[Piece.paralysis] <= 0
										&& friend[i].condition[Piece.piyopiyo] <= 0
										&& friend[i].condition[Piece.freeze] <= 0) {
									counter = true;
									panel.counterAttack(i, ptr_idx);
								}
							}
							// /////////////////////////////////////////////////////////////////////////////////
							// ここで遷移
							musicStop();
							mp.jumpBattleScreenPanel(effect_id, hit, critical,
									damage, drain_flag, drain, counter, c_hit,
									c_critical, c_damage, ptr.unit.id,
									friend[i].unit.id, ptr.HP, friend[i].HP,
									ptr.SP, friend[i].SP, ptr.unit.HP,
									friend[i].unit.HP, ptr.unit.SP,
									friend[i].unit.SP,
									s_condition,a_buff,r_buff);
							mp.state = 7;
							initScreenParameta();
							// /////////////////////////////////////////////////////////////////////////////////
						}
					}

				} else {
					for (int i = 0; i < enemy.length; i++) {
						if ( enemy[i].alive && ptr.unit.magic[magic_idx].attackRange(
								ptr.direction, enemy[i].position,
								cursor_position[0], cursor_position[1])) {
							panel.magicSupport(ptr_idx, i, magic_idx);
						}
					}
					if (ptr.acted) {
						panel.MagicpayCost(ptr_idx, magic_idx);
						mode_pattern = default_mode;
						if(!PvP) mode_pattern = enemy_mode;/////////////////////////////////ここ変更
					}
				}
			}
			panel.ptrLost();
			panel.showState(ptr);
			try{
				Thread.sleep(500);
			}catch (InterruptedException e){

			}
		}

		if (mode_pattern == move_mode) {
			if (map[cursor_position[1]][cursor_position[0]] == BattleSystem.nobody) {
				panel.movePosition(ptr_idx, cursor_position[0],cursor_position[1]);
				mode_pattern = default_mode;
				panel.ptrLost();
				panel.showState(ptr);
				if(!PvP && !myTurn) mode_pattern = enemy_mode;///////////////////////ここ変更
			}
			repaint();
		}
		if(mode_pattern == item_mode){
			if (myTurn) {
				for (int i = 0; i < friend.length; i++) {
					if (friend[i].position[0] == cursor_position[0] && friend[i].position[1] == cursor_position[1]  ) {
						System.out.println(friend[i].unit.name + friend[ptr_idx].unit.name);
						panel.useItem(item_id , myTurn , i ,ptr_idx);
						mode_pattern = default_mode;
					}
				}
				if(panel.mp.vsAI)panel.item.writeItem(false);
				else panel.item.writeItem(MainPanel.player1);
			}else{
				for (int i = 0; i < enemy.length; i++) {
					if (enemy[i].position[0] == cursor_position[0] && enemy[i].position[1] == cursor_position[1] ) {
						System.out.println("item used");
						panel.useItem(item_id , myTurn,i , ptr_idx);
						mode_pattern = default_mode;
						if(!PvP) mode_pattern = enemy_mode;////////////////////////ここ変更
					}
				}
				if(panel.mp.vsAI)panel.item.writeItem(false);
				else panel.item.writeItem(MainPanel.player2);
			}
			panel.ptrLost();
			panel.showState(ptr);
		}
/////////////////////////////////////////////////////////////////////////////////////ここ変更


//		System.out.println(" -> changeMode/  mode_pattern : "+ mode_pattern );
//		System.out.println("");

/////////////////////////////////////////////////////////////////////////////////////////ここ変更
		// System.out.println("changeMode : mode_pattern = "+mode_pattern);
	}

	public boolean cursorMovable(int x, int y) {
		if (mode_pattern == default_mode) {
			boolean re = (x >= 0 && x < map[0].length && y >= 0 && y < map.length) ? true : false;
			if(re){
				if( x-visible_start_posi[0] >= visible_width) visible_start_posi[0]++;
				else if( x  < visible_start_posi[0])visible_start_posi[0]--;
				else if( y-visible_start_posi[1] >= visible_height) visible_start_posi[1]++;
				else if( y  <  visible_start_posi[1] )visible_start_posi[1]--;
			}
			return re;
		} else if (mode_pattern == target_mode) {
			boolean re = (ptr.unit.skill[skill_idx].inRange(ptr.position,	ptr.direction, x, y)
					&& x >= 0
					&& x < map[0].length
					&& y >= 0 && y < map.length) ? true : false;
			if(re){
				if( x-visible_start_posi[0] >= visible_width) visible_start_posi[0]++;
				else if( x  < visible_start_posi[0])visible_start_posi[0]--;
				else if( y-visible_start_posi[1] >= visible_height) visible_start_posi[1]++;
				else if( y  <  visible_start_posi[1] )visible_start_posi[1]--;
			}
			return re;
		} else if (mode_pattern == item_mode) {
			boolean re = ((ptr.unit.skill[0].inRange(ptr.position,ptr.direction, x, y) ||
					ptr.position[0] == x && ptr.position[1] == y)
					&& x >= 0
					&& x < map[0].length
					&& y >= 0 && y < map.length) ? true : false;
			if(re){
				if( x-visible_start_posi[0] >= visible_width) visible_start_posi[0]++;
				else if( x  < visible_start_posi[0])visible_start_posi[0]--;
				else if( y-visible_start_posi[1] >= visible_height) visible_start_posi[1]++;
				else if( y  <  visible_start_posi[1] )visible_start_posi[1]--;
			}
			return re;
		}else if (mode_pattern == magic_mode) {
			boolean re =  (ptr.unit.magic[magic_idx].inRange(ptr.position,
					ptr.direction, x, y)
					&& x >= 0
					&& x < map[0].length
					&& y >= 0 && y < map.length) ? true : false;
			if(re){
				if( x-visible_start_posi[0] >= visible_width) visible_start_posi[0]++;
				else if( x  < visible_start_posi[0])visible_start_posi[0]--;
				else if( y-visible_start_posi[1] >= visible_height) visible_start_posi[1]++;
				else if( y  <  visible_start_posi[1] )visible_start_posi[1]--;
			}
			return re;
		} else if (mode_pattern == move_mode) {
			// System.out.println("x = " + x + " y = "+ y);
			panel.movable(ptr.position, ptr.position[0], ptr.position[1],ptr.MOVE);
			boolean re =  (x >= 0 && x < map[0].length && y >= 0 && y < map.length && move_map[y][x] == 5) ? true : false;
			if(re){
				if( x-visible_start_posi[0] >= visible_width) visible_start_posi[0]++;
				else if( x  < visible_start_posi[0])visible_start_posi[0]--;
				else if( y-visible_start_posi[1] >= visible_height) visible_start_posi[1]++;
				else if( y  <  visible_start_posi[1] )visible_start_posi[1]--;
			}
			return re;
		}
		return false;
	}

	public void backMode() {
		if (mode_pattern == target_mode || mode_pattern == move_mode
				|| mode_pattern == magic_mode || mode_pattern == item_mode) {
			if( mode_pattern == move_mode) panel.initMoveMap();

			mode_pattern = default_mode;
			cursor_position[0] = ptr.position[0];
			cursor_position[1] = ptr.position[1];
			panel.setCursorPosi(cursor_position);
			System.out.println("c_posi : "+Arrays.toString(cursor_position)+" / ptr.posi : "+Arrays.toString(ptr.position));
			panel.backSelect();
		}
		repaint();
	}

	public void changeDirection() {
		if (mode_pattern == default_mode) {
			if (myTurn) {
				if (map[cursor_position[1]][cursor_position[0]] == 1) {
					if (ptr.direction == Piece.up)
						ptr.direction = Piece.right;
					else if (ptr.direction == Piece.right)
						ptr.direction = Piece.down;
					else if (ptr.direction == Piece.down)
						ptr.direction = Piece.left;
					else
						ptr.direction = Piece.up;
					if(ptr != null) panel.setDirection(ptr_idx, ptr.direction);
				}
			} else {
				if (PvP) {
					if (map[cursor_position[1]][cursor_position[0]] == -1) {
						if (ptr.direction == Piece.up)
							ptr.direction = Piece.right;
						else if (ptr.direction == Piece.right)
							ptr.direction = Piece.down;
						else if (ptr.direction == Piece.down)
							ptr.direction = Piece.left;
						else
							ptr.direction = Piece.up;
						if(ptr != null) panel.setDirection(ptr_idx, ptr.direction);
					}
				}
			}
		}


	}

	public void updatePiece(Piece[] friend, Piece[] enemy) {
		this.friend = Arrays.copyOf(friend, friend.length);
		this.enemy = Arrays.copyOf(enemy, enemy.length);
		repaint();
	}

	public void getPhase() {
		mode_pattern = default_mode;
		repaint();
	}

	public void endPhase() {
		if (myTurn) {
			if (!PvP)
				mode_pattern = enemy_mode;
			else
				mode_pattern = default_mode;
		} else {
			mode_pattern = default_mode;
		}
		myTurn = (myTurn) ? false : true;
		repaint();
	}

	public void updateMoveMap(int[][] move_map) {
		for (int i = 0; i < this.move_map.length; i++)
			this.move_map[i] = Arrays.copyOf(move_map[i], move_map[i].length);

	}

	public void PvPmode(boolean PvP) {
		this.PvP = PvP;

	}

	public void setScreenParameta_hit(boolean b) {
		hit = b;
		if (!hit)
			critical = false;
	}

	public void setScreenParameta_critical(boolean b) {
		critical = b;
	}

	public void setScreenParameta_damage(int damage) {
		this.damage = damage;
	}

	public void setScreenParameta_drain(int i) {
		drain = i;
		if (i == 0)
			drain_flag = false;
		else
			drain_flag = true;
	}

	public void setScreenParameta_c_hit(boolean b) {
		c_hit = b;
	}

	public void setScreenParameta_c_critical(boolean b) {
		c_critical = b;
	}

	public void setScreenParameta_c_damage(int damage) {
		c_damage = damage;
	}

	public void setScreenParameta_s_condition(int kind) {
		s_condition[kind] = true;
	}
	public void setScreenParameta_a_buff(boolean buff) {
		if(buff) a_buff[0] = true;
		else     a_buff[1] = true;
	}
	public void setScreenParameta_r_buff(boolean buff) {
		if(buff) r_buff[0] = true;
		else     r_buff[1] = true;
	}
	public void showScreenParameta(){
		System.out.println("effect_id = "+effect_id );
		System.out.println("hit       = "+hit );
		System.out.println("critical  = "+critical );
		System.out.println("damage    = "+damage );
		System.out.println("drain_flag= "+drain_flag );
		System.out.println("drain     = "+drain );
		System.out.println("counter   = "+counter );
		System.out.println("c_hit     = "+c_hit );
		System.out.println("c_critical= "+c_critical );
		System.out.println("c_damage  = "+c_damage );
		System.out.println("s_condition :"+Arrays.toString(s_condition));
		System.out.println("a_buff :"+Arrays.toString(a_buff));
		System.out.println("r_buff :"+Arrays.toString(r_buff));

	}

	public void initScreenParameta(){
		effect_id = 0;
		hit = false;
		critical = true;
		damage = 0;
		drain_flag = false;
		drain = 0;
		counter = false;
		c_hit = false;
		c_critical = false;
		c_damage = 0;
		for(int i=0 ; i<s_condition.length ; i++) s_condition[i] = false;
		a_buff[0] = false; a_buff[1] = false;
		r_buff[0] = false; r_buff[1] = false;
	}

	public void victory(){
		panel.aiStop();
		repaint();
		start();
		System.out.println("VICTORY");
		win_or_lose = true;
		musicStop();
		mp.cleared[mp.storyNum] = true;
		try{
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("../src/Stage/Cleared")));
			for(int i= 0;i<mp.cleared.length;i++){
				pw.print(Boolean.toString(mp.cleared[i])+",");
			}
			pw.close();
		}catch(IOException er){
			throw new RuntimeException(er);
		}
		//repaint();
		/////////////////////////////////////////////////////////////////////////
		////    ここで遷移
		//////////////////////////////////////////////////////////////////////////
	}
	public void defeat(){
		panel.aiStop();
		repaint();
		start();
		System.out.println("DEFEAT");
		win_or_lose = false;
		musicStop();
		//repaint();
		/////////////////////////////////////////////////////////////////////////
		////    ここで遷移
		//////////////////////////////////////////////////////////////////////////
	}
	public void start() {
		if (th == null) {
			th = new Thread(this);
			th.start();
		}
		//running = true;
	}

	public void stop() {
		if (th != null) {
			//th.stop();
			th = null;
		}
		//running = false;
	}

	@Override
	public void run() {
		System.out.println("thread 開始");
		musicStop();
		try{
			Thread.sleep(2000);
		}catch (InterruptedException e){

		}
		if(win_or_lose)victory = true;
		else           defeat = true;
		repaint();
		try{
			win = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
			win.open(AudioSystem.getAudioInputStream(new File("../bin/Sound/効果音/win.wav")));
			FloatControl control1 = (FloatControl)win.getControl(FloatControl.Type.MASTER_GAIN);
			controlByLinearScalar(control1, 1);
			lose = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
			lose.open(AudioSystem.getAudioInputStream(new File("../bin/Sound/効果音/lose.wav")));
			FloatControl control2 = (FloatControl)lose.getControl(FloatControl.Type.MASTER_GAIN);
			controlByLinearScalar(control2, 1);
		}
		catch(IOException | LineUnavailableException | UnsupportedAudioFileException er){
			throw new RuntimeException(er);
		}

		if(victory) win.start();
		else if(defeat)  lose.start();
		panel.nextPanel();
		stop();

	}

	public void musicRestart(){
		mapMusic.setMicrosecondPosition(0);
		mapMusic.loop(999);
	}
	public void musicStop(){
		System.out.println("Music ...... stooop");
		mapMusic.stop();
	}
	private void controlByLinearScalar(FloatControl control, double linearScalar) {
		control.setValue((float)Math.log10(linearScalar) * 20);
	}
}
