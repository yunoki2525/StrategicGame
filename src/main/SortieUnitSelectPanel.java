package main;

import java.applet.AppletContext;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JApplet;
import javax.swing.JButton;

public class SortieUnitSelectPanel extends JApplet implements KeyListener, ActionListener{
	AppletContext ac; // アプレットのコンテキスト
	Dimension size; // アプレットの大きさ
	MainPanel mp;

	JButton[] bt;
	int[] w;//ボタンの大きさ
	int[] h;

	//ここからSortieUnitSelect
	int[][] map;
	int[][] field;

	Piece[] enemy;

	Piece[] all_friend;
	Piece[] friend;

	int sortie_num =0;
	boolean[] selected ;
	int s_count = 0;
	int y = 0;

	int storyNum;
	String[] stage = {"kyuusyuu","tyugoku","sikoku","kinki","chubu","kantou","touhoku","saitan"};
////////////////////////////////
	Clip mapMusic;
////////////////////////////////

	SortieUnitSelectPanel(AppletContext ac1, Dimension size1, MainPanel mp1, int storyNum){
		//ここからPanel
		ac = ac1;
		size = size1;
		mp = mp1;

		String[] str1 ={"メニュー","ストーリー","マップ","ステータス","バトル","ゲームオーバー",
				"バトルスクリーン","出撃キャラ選択","出撃位置設定"};//ボタンの名前の配列
		bt = new JButton[str1.length];

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

		this.storyNum = storyNum;//どのステージを選んだかを判別する。

		//ここからSortieUnitSelect
		SkillList skill_list;
		MagicList magic_list;
		WeaponList weapon_list;


		try{

			/////// ここから味方情報////////////
			BufferedReader br = new BufferedReader(new FileReader("resource/Stage/MyData"));
			String str = br.readLine();
		//	System.out.println(str);
			int n0 = Integer.parseInt(str);
		//	System.out.println(n0);
			all_friend = new Piece[n0];

			for(int i=0; i<n0 ; i++){
				str = br.readLine();
				String[] tokens1 = str.split(",");
				System.out.println(" tokens1 : "+Arrays.toString(tokens1));
				int[] status = new int[tokens1.length-3];
				str = br.readLine();
				String[] tokens2 = str.split(",");
				System.out.println(" tokens2 : "+Arrays.toString(tokens2));
				int[] pass = new int[tokens2.length];

				for(int j=0 ; j<status.length ; j++) status[j] = Integer.parseInt(tokens1[j+3]);
				for(int j=0 ; j<pass.length ; j++) pass[j] = Integer.parseInt(tokens2[j]);
				//System.out.println("status : "+Arrays.toString(status));
				//int[] posi= {status[11],status[12]};

				//System.out.println("posi : "+Arrays.toString(posi));

				skill_list = new SkillList(status[0]);
				Skill[] skill = new Skill[skill_list.skills.length];
				for(int j=0 ; j<skill_list.skills.length ; j++) skill[j] = skill_list.skills[j];

				magic_list = new MagicList(status[0]);
				Magic[] magic = new Magic[magic_list.magics.length];
				for(int j=0 ; j<magic_list.magics.length ; j++) magic[j] = magic_list.magics[j];

				weapon_list = new WeaponList(Integer.parseInt(br.readLine()));
				Weapon weapon = weapon_list.weapon;

				int[] posi ={0, 0};

				System.out.println(Arrays.toString(tokens1));

				all_friend[i]=new Piece(new Unit(tokens1[0],tokens1[1],tokens1[2],status[0],status[1],status[2],status[3],status[4],
						status[5],status[6],status[7],status[8],status[9],status[10],pass,skill,magic,weapon),posi);


				all_friend[i].direction = Piece.left;
			}
			br.close();


			/////// ここから敵・Stage情報////////////
			br = new BufferedReader(new FileReader("resource/Stage/"+stage[storyNum]));
			str = br.readLine();
			friend = new Piece[Integer.parseInt(str)];
			sortie_num = Integer.parseInt(str);

//////////////////////////////////////////////////////////////////////////////////////////////////////
			str = br.readLine();
			try {
				mapMusic = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
				mapMusic.open(AudioSystem.getAudioInputStream(new File("resource/music/mapMusic/"+str+".wav")));
			} catch (LineUnavailableException | UnsupportedAudioFileException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
//////////////////////////////////////////////////////////////////////////////////////////////////////

			str = br.readLine();
			String[] n1 = str.split(",");
			map = new int[Integer.parseInt(n1[1])][Integer.parseInt(n1[0])];
			field =  new int[Integer.parseInt(n1[1])][Integer.parseInt(n1[0])];

			int k=0;
			for(int i=0 ; i<map.length ; i++){
				str = br.readLine();
				n1 = str.split(",");
				for(int j=0 ; j<map[0].length ; j++){
					map[i][j] = Integer.parseInt(n1[j]);
					if(map[i][j] == 1){
						all_friend[k].position[0] = j; all_friend[k].position[1] = i;
						k++;
					}
				}
			}
			br.readLine();
//////////////////////////////////////////////////////////////////////////////////
			k=0;
			for(int i=0 ; i<map.length ; i++){
				str = br.readLine();
				n1 = str.split(",");
				for(int j=0 ; j<map[0].length ; j++){
					field[i][j] = Integer.parseInt(n1[j]);

				}
			}
///////////////////////////////////////////////////////////////////////////////////////
			//for(int i=0 ; i<map.length ; i++) System.out.println(" field : "+Arrays.toString(field[i]));
			str = br.readLine();
			int n2 = Integer.parseInt(str);
			enemy = new Piece[n2];

			for(int i=0; i<n2 ; i++){
				str = br.readLine();
				String[] tokens1 = str.split(",");
				int[] status = new int[tokens1.length-3];
				for(int j=0 ; j<status.length ; j++) status[j] = Integer.parseInt(tokens1[j+3]);

				str = br.readLine();
				String[] tokens2 = str.split(",");
				System.out.println(" tokens2 : "+Arrays.toString(tokens2));
				int[] pass = new int[tokens2.length];
				for(int j=0 ; j<pass.length ; j++) pass[j] = Integer.parseInt(tokens2[j]);

				str = br.readLine();
				String[] tokens3 = str.split(",");
				System.out.println(" tokens3 : "+Arrays.toString(tokens2));
				int[] position = new int[tokens3.length];
				for(int j=0 ; j<position.length ; j++) position[j] = Integer.parseInt(tokens3[j]);


				skill_list = new SkillList(status[0]);
				Skill[] skill = new Skill[skill_list.skills.length];
				for(int j=0 ; j<skill_list.skills.length ; j++) skill[j] = skill_list.skills[j];

				magic_list = new MagicList(status[0]);
				Magic[] magic = new Magic[magic_list.magics.length];
				for(int j=0 ; j<magic_list.magics.length ; j++) magic[j] = magic_list.magics[j];

				weapon_list = new WeaponList(Integer.parseInt(br.readLine()));//status[0]);
				Weapon weapon = weapon_list.weapon;

				int[] posi ={0, 0};
				posi[0] = position[0]; posi[1] = position[1];

				System.out.println("enemyposi"+posi[0]+","+posi[1]);
				enemy[i]=new Piece(new Unit(tokens1[0],tokens1[1],tokens1[2],status[0],status[1],status[2],status[3],status[4],
						status[5],status[6],status[7],status[8],status[9],status[10],pass,skill,magic,weapon),posi);

				enemy[i].direction = Piece.right;
			}
			br.close();


		}catch(IOException er){
			throw new RuntimeException(er);
		}

		//System.out.println("sortie_num : "+sortie_num);
	    selected = new boolean[all_friend.length];

		setSize(size);

		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());

		addKeyListener(this);
		pane.setFocusable(true);
		pane.setBackground(Color.WHITE);

		pane.setLayout(null);
		//pane.add(bt[8]);
		bt[8].setBounds(200, 200, w[8], h[8]);

	}

	public void paint(Graphics g) {
		super.paint(g); // 親クラスの描画
		g.drawString("出撃するユニットを選択してください("+sortie_num+")", 50, 20);

		for(int i=0 ; i<all_friend.length ; i++){
			if(selected[i])g.setColor(Color.RED);
			else           g.setColor(Color.BLACK);
			g.drawString(all_friend[i].unit.name,50,60+20*i);
		}

		g.setColor(Color.RED);
		g.fillOval(40, 55+20*y, 5, 5);

		g.setColor(Color.RED);
		g.drawString("初期位置設定に遷移 : n", 400, 50);

		 g.setColor(Color.BLACK);
		int x0=30; int y0=200;
		int x1= x0 + 200 + 30; int y1= y0 + 50;
		g.drawImage(all_friend[y].unit.original_image,x0 ,y0,200,320,this);
		g.setFont( new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		g.setColor(Color.BLUE);
		g.drawString(" HP :"+all_friend[y].HP + " (+ "+all_friend[y].unit.weapon.HP+" )", 0 + x1, 0 + y1);
		g.drawString(" SP:"+all_friend[y].SP + " (+ "+all_friend[y].unit.weapon.SP+" )",140 + x1, 0 + y1);
		g.drawString(" ATK :"+all_friend[y].ATK + " (+ "+all_friend[y].unit.weapon.ATK+" )", 0 + x1, 40 + y1);
		g.drawString(" DEF:"+all_friend[y].DEF + " (+ "+all_friend[y].unit.weapon.DEF+" )", 140 + x1, 40 + y1);
		g.drawString(" INT :"+all_friend[y].INT + " (+ "+all_friend[y].unit.weapon.INT+" )", 0 + x1, 80 + y1);
		g.drawString(" MEN :"+all_friend[y].MEN + " (+ "+all_friend[y].unit.weapon.MEN+" )", 140 + x1, 80 + y1);
		g.drawString(" DEX :"+all_friend[y].DEX + " (+ "+all_friend[y].unit.weapon.DEX+" )", 0 + x1, 120 + y1);
		g.drawString(" AGI :"+all_friend[y].AGI + " (+ "+all_friend[y].unit.weapon.AGI+" )", 140 + x1, 120 + y1);
		g.drawString(" LUCK :"+all_friend[y].LUCK + " (+ "+all_friend[y].unit.weapon.LUCK+" )", 0 + x1, 160 + y1);
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyChar = e.getKeyCode();

		if (keyChar == KeyEvent.VK_DOWN) {
			if(y < all_friend.length-1)
				y++;
			//System.out.println("y : "+y);
		}

		if (keyChar == KeyEvent.VK_UP) {
			if(y > 0) y--;
			//System.out.println("y : "+y);
		}

		/*if (keyChar == KeyEvent.VK_LEFT){}

		if (keyChar == KeyEvent.VK_RIGHT){}
		*/

		if (keyChar == KeyEvent.VK_SPACE){
			if(selected[y]) {
				selected[y] = false;
				s_count--;
			}else{
				if(s_count < sortie_num) {
					selected[y] = true;
					s_count++;
				}else{
					System.out.println("you cannot sotie more!");
				}
			}
			//System.out.println(" s_cont ="+s_count);
		}

		if (keyChar == KeyEvent.VK_N){
			int n =0;
			for(int i=0; i<all_friend.length ; i++){
				if(selected[i]) friend[n++] = all_friend[i];
			}
			for(int i=0 ; i<friend.length ; i++){
				if(friend[i] == null){
					System.out.println("friend["+i+"] is null");
					for(int j=0; j<all_friend.length ; j++){
						if(!selected[j]){
							System.out.println("insert all_friend["+j+"]");
							friend[i] = all_friend[j];
							selected[j] = true;
							break;
						}
					}
				}
			}
			for(int i=0; i<friend.length ; i++)
				for(int j=0 ; j<map.length ; j++){
					for(int k=0 ; k<map[0].length ; k++){
						if(map[j][k] == 1){
							friend[i].position[0] = k; friend[i].position[1] = j;
							i++;
						}
					}
				}
			for(int j=0 ; j<friend.length ; j++)
				System.out.println(friend[j].unit.name);
//			for(int j=0; j<map.length ; j++)
//				System.out.println(Arrays.toString(map[j]));
				/*
				 *
				 *   ここで、戦闘画面"SortieUnitPositionsetPanel"に遷移
				 *   送るデータは map, friend, enemy , mapMusic
				 *
				 */
			mp.jumpPositionSetPanel(map,field,friend,enemy,mapMusic);
			mp.state = 9;

		}

		repaint();

	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	// ボタンがクリックされたときの処理
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt[0])     //GameMenuPanelへ
			mp.state = 1;
		else if(e.getSource() == bt[1]) //StoryPanelへ
			mp.state = 2;
		else if(e.getSource() == bt[2]) //MapPanel
			mp.state = 3;
		else if(e.getSource() == bt[3]) //StatePanel
			mp.state = 4;
		else if(e.getSource() == bt[4]) //BattlePanel
			mp.state = 5;
		else if(e.getSource() == bt[5]) //GameOverPanel
			mp.state = 6;
		else if(e.getSource() == bt[6]) //BattleScreenPanel
			mp.state = 7;
		else if(e.getSource() == bt[7]) //SortieUnitSelectPanel
			mp.state = 8;
		else if(e.getSource() == bt[8]) //SortiePositionSetPanel
			mp.state = 9;
	}

}
