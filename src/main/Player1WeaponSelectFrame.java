package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Player1WeaponSelectFrame extends JApplet implements ActionListener {
	// フィールド
	Player1CharacterStatusPanel p1csp;
	Player1StatePanel p1sp;
	int CSPnum;
	Unit unit;

	//装備関連
	Weapon weapon;
	Weapon weapon_bt;//ボタン用weapon
	Image buki;
	int weapon_num;
	JButton[] weaponBt;
	int select_weapon_num;
	int[] having_weapon ;//それぞれの武器所持数
	int[] using_weapon;

	Clip ok;
	Clip no;

	int unit_num;

	WeaponList weapon_list;

	String[] all_weapon_name={"約束された勝利の剣"  ,"全て遠き理想郷"          ,"天地乖離す開闢の星",
			"セラフ・プレステージ","ジェットブレード・スカイ","ロスヴァイセ",
			"天鹿児弓"            ,"ハートオブエイン"        ,"サーモンオブナレッジ",
			"破邪神の指輪"        ,"マグネットコーティング"  ,"松岡修造の写真",
			"柚木の助言"          ,"眠眠打破"                ,"ラーウェイ",
			"閃光弾"              ,"一蘭のラーメン"          ,"高崎",
			"栗おこわ"            ,"薩摩剣士隼人"};

	int[] all_weapon_id;

	JButton bt1;
	JButton bt2;

	//ここから制作
	JPanel pl1,pl2,pl1_1,pl1_2,pl2_1,pl2_2,pl2_3;
	JScrollPane pl1_scr;

	// コンストラクタ
	public Player1WeaponSelectFrame(Player1CharacterStatusPanel csp , Player1StatePanel sp) throws LineUnavailableException, UnsupportedAudioFileException{
		this.p1csp = csp;
		this.p1sp = sp;
		this.CSPnum = this.p1csp.CSPnum;

		Container pane = getContentPane();

		try{
			ok=(Clip)AudioSystem.getLine(new Line.Info(Clip.class));
			ok.open(AudioSystem.getAudioInputStream(new File("music/okok.wav")));
			no=(Clip)AudioSystem.getLine(new Line.Info(Clip.class));
			no.open(AudioSystem.getAudioInputStream(new File("music/nono.wav")));
		}catch(IOException er){

		}

		// ラベルのオブジェクトの生成と貼り付け
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();

		p1.setBackground(Color.WHITE);
		p2.setBackground(Color.WHITE);

		pane.setLayout(new BorderLayout());
		pane.add(p1, BorderLayout.CENTER);
		pane.add(p2, BorderLayout.SOUTH);
		pane.setBackground(Color.WHITE);

		//装備関係
		using_weapon = new int[Weapon.kind];

		//P1unitの読み込み
		checkWeapon();

		System.out.println(" having_weapon : "+Arrays.toString(having_weapon));
		System.out.println(" using_weapon : "+Arrays.toString(using_weapon));


		int back_h,back_w,decide_h,decide_w;
		Font f = new Font("SansSerif", Font.BOLD, 10);
		FontMetrics fm = getFontMetrics(f);
		back_w = fm.stringWidth("戻る")+40;
		back_h = fm.getHeight()+10;
		//	System.out.println("back_w = "+back_w+" back_h = "+back_h);
		bt1 = new JButton("戻る");
		bt1.setFont(f);
		decide_w = fm.stringWidth("決定")+40;
		decide_h = fm.getHeight()+10;
		//	System.out.println("decide_w = "+decide_w+" decide_h = "+decide_h);
		bt2 = new JButton("決定");
		bt2.setFont(f);

		bt1.addActionListener(this);
		bt2.addActionListener(this);


		//ここから作成
		//pl1用
		pl1 = new JPanel();//武器表示用Panel
		pl1_1 = new JPanel();//「武器名」
		pl1_2 = new JPanel();//スクロール用Panel
		pl1_scr = new JScrollPane(pl1_2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		pl1.setBackground(Color.WHITE);
		pl1_1.setBackground(Color.WHITE);
		pl1_2.setBackground(Color.WHITE);

		//pl2用
		pl2 = new JPanel();//武器ステータス用Panel
		pl2_1 = new JPanel(){//name
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				setBackground(Color.WHITE);
				String str = weapon.name;

				g.setFont( new Font("ＭＳ Ｐゴシック", Font.BOLD + Font.ITALIC, 26) );
				FontMetrics fm = g.getFontMetrics();

				Dimension d = getSize();
				int x = d.width / 2 - fm.stringWidth(str) / 2;
				int y = d.height / 2 - fm.getDescent();

				g.drawString(str, x, y);

			}
		};
		pl2_2 = new JPanel(){//picture(武器)
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					//					System.out.println("CSPnum = "+CSPnum);
					//					System.out.println("weapon.id = "+weapon.id);
					buki = ImageIO.read(new File("weapon/buki"+weapon.id+".jpg"));
				} catch (IOException er) {
					throw new RuntimeException(er);
				}

				g.drawImage(buki,0,0,getWidth(),getHeight(),this);
			}
		};
		pl2_3 = new JPanel(){//武器ステータス
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				setBackground(Color.WHITE);
				// 武器ステータス表関連
				String[][] weapondata = new String[][] {
					{ "HP"            , String.valueOf(weapon.HP)}, //　体力　：Hit Point(HP)：ヒットポイント
					{ "ATK(攻撃力)"   , String.valueOf(weapon.ATK)},//　攻撃　：Attack(ATK)：アタック
					{ "DEF(防御力)"   , String.valueOf(weapon.DEF)},//　防御　：Defense(DEF)：ディフェンス
					{ "SP(魔力)"      , String.valueOf(weapon.SP) },//　魔力　：Magic Point(MP)：マジックポイント
					{ "INT(魔法攻撃)" , String.valueOf(weapon.INT)},//魔法攻撃：Magic Attack(MATK/MAT)：マジックアタック
					{ "MEN(魔法防御)" , String.valueOf(weapon.MEN)},//魔法防御：Magic Defense(MDEF/MDE)：マジックディフェンス
					{ "AGI(回避)"     , String.valueOf(weapon.AGI)},//　回避率：Evasion（EVA）：エヴァージョン
					{ "DEX(命中)"     , String.valueOf(weapon.DEX)},//　命中率：Dexterity（DEX）：デクステリティー
					{ "LUCK(運)"      , String.valueOf(weapon.LUCK)},//　運　　：Luck（LUK）:ラック
				};
				//				String[][] weapondata2 = new String[][] {
				//														   { "斬", String.valueOf(unit.PASS[Unit.PASS_SLASH]) },
				//														   { "突", String.valueOf(unit.PASS[Unit.PASS_SPEAR]) },
				//														   { "打", String.valueOf(unit.PASS[Unit.PASS_BLOW]) },
				//														   { "火", String.valueOf(unit.PASS[Unit.PASS_FIRE]) },
				//														   { "水", String.valueOf(unit.PASS[Unit.PASS_AQUA]) },
				//														   { "天", String.valueOf(unit.PASS[Unit.PASS_SKY]) },
				//														   { "地", String.valueOf(unit.PASS[Unit.PASS_GRAND]) },
				//														   { "光", String.valueOf(unit.PASS[Unit.PASS_RAY]) },
				//														   { "闇", String.valueOf(unit.PASS[Unit.PASS_DARK]) },
				//				};


				g.setFont( new Font("ＭＳ Ｐゴシック", Font.BOLD + Font.ITALIC, 15) );
				FontMetrics fm = g.getFontMetrics();

				Dimension d = getSize();
				for(int i = 0;i<weapondata.length;i++){
					int x = 0;
					int y = (d.height*(i+1)) / (weapondata.length+1) - fm.getDescent();

					g.drawString(weapondata[i][0]+"：　"+weapondata[i][1], x, y);
				}

				//		        for(int i = 0;i<weapondata.length;i++){
				//		        	int x = 0;
				//		        	int y = (d.height*(i+1)) / 11 - fm.getDescent();
				//
				//		        	g.drawString(weapondata[i][0]+"：　"+weapondata[i][1], x, y);
				//		        }
			}
		};

		//パネルのサイズ変更
		pl1.setPreferredSize(new Dimension(200, 600));
		pl2.setPreferredSize(new Dimension(400, 600));
		pl1_1.setPreferredSize(new Dimension(200, 50));
		pl1_2.setPreferredSize(new Dimension(200, 550));
		pl2_1.setPreferredSize(new Dimension(400, 50));
		pl2_2.setPreferredSize(new Dimension(400, 250));
		pl2_3.setPreferredSize(new Dimension(400, 300));

		//pl1の内容
		pl1.setLayout(new BoxLayout(pl1, BoxLayout.Y_AXIS));
		pl1_1.add(new JLabel("武器名"));
		pl1.add(pl1_1);
		pl1.add(pl1_scr);
		pl1_2.setLayout(new GridLayout(10, 1));
		if((Weapon.kind+1)>10){
			pl1_2.setPreferredSize(new Dimension(200, 550+55*((Weapon.kind+1)-10)));
			pl1_2.setLayout(new GridLayout(10+(Weapon.kind+1), 1));
		}
		//pl1でボタンの貼り付け
		weaponBt = new JButton[Weapon.kind+1];
		weaponBt[0] = new JButton("武器無し");
		weaponBt[0].addActionListener(this);
		if(weapon.id==0)weaponBt[0].setBackground(Color.PINK);
		pl1_2.add(weaponBt[0]);
		for(int i = 1 ; i < (Weapon.kind+1); i++){
			weapon_bt =new WeaponList(i).weapon;
			weaponBt[i] = new JButton(weapon_bt.name);
			weaponBt[i].addActionListener(this);
			if(weapon.id==i)weaponBt[i].setBackground(Color.PINK);
			pl1_2.add(weaponBt[i]);
		}

		//pl2の内容
		pl2.setLayout(new BoxLayout(pl2, BoxLayout.Y_AXIS));
		pl2.add(pl2_1);
		pl2.add(pl2_2);
		pl2.add(pl2_3);
		pl2_3.setLayout(null);
		pl2_3.add(bt1);
		pl2_3.add(bt2);
		bt1.setBounds(400-back_w-25,300-back_h-20,back_w,back_h);//setBounds(x座標,y座標,コンポーネントの幅,コンポーネントの高さ);
		bt2.setBounds(400-back_w-25-decide_w,300-back_h-20,decide_w,decide_h);//setBounds(x座標,y座標,コンポーネントの幅,コンポーネントの高さ);
		//台紙に貼り付け
		pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
		pane.add(pl1);
		pane.add(pl2);
	}

	public void paint(Graphics g) {
		super.paint(g); // 親クラスの描画
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for(int i = 0 ; i < (Weapon.kind+1) ; i++){
			if(e.getSource() == weaponBt[i]){
				System.out.println(i+"番目が押されたよ～");
				weaponBt[i].setBackground(Color.PINK);
				for(int j = 0 ; j < (Weapon.kind+1) ; j++){
					if(j == i)continue;
					weaponBt[j].setBackground(null);
				}
				select_weapon_num = i;
				weapon_list = new WeaponList(select_weapon_num);
				weapon = weapon_list.weapon;
				pl2_1.repaint();
				pl2_2.repaint();
				pl2_3.repaint();
			}
		}

		if (e.getSource() == bt1) {
			p1csp.weaponSelectFrameHide();
			p1sp.show_WSFnum = -1;
		} else if (e.getSource() == bt2) {
			if(select_weapon_num >= 0){
				System.out.println("select_weapon_num ="+select_weapon_num);
				System.out.println("所次数 ="+having_weapon[select_weapon_num]);
				System.out.println("装備している数 ="+ using_weapon[select_weapon_num]);
				if(having_weapon[select_weapon_num] > using_weapon[select_weapon_num]){
					System.out.println("決定");
					try {
						//P1unit2→	P1unit書き込み
						BufferedReader br = new BufferedReader(new FileReader("../src/PvP/P1unit2"));
						PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("../src/PvP/P1unit")));
						pw.println(br.readLine());
						for (int i = 0; i < unit_num; i++) {
							pw.println(br.readLine());
							pw.println(br.readLine());
							if(CSPnum == i){
								//	System.out.println(String.valueOf(combo.getSelectedIndex()+1));
								pw.println(String.valueOf(select_weapon_num));
								br.readLine();
							}else{
								pw.println(br.readLine());
							}
						}
						pw.close();
						br.close();
					} catch (IOException er) {

					}
					ok.setMicrosecondPosition(0);
					ok.start();
					p1csp.setWeaponCDF(weapon);
					p1sp.setWeaponP1SP(weapon);
				}else{
					no.setMicrosecondPosition(0);
					no.start();
					System.out.println("所持数を超えています。");
				}
			}else{
				no.setMicrosecondPosition(0);
				no.start();
				System.out.println("何も選ばれていません。");
			}

			p1csp.weaponSelectFrameHide();
			p1sp.show_WSFnum = -1;
		}

		//System.out.println("START:" + start);

	}

	public void checkWeapon(){
		using_weapon = new int[Weapon.kind+1];

		try{
			//みんなで使っている装備の数を知る
			BufferedReader br4 = new BufferedReader(new FileReader("../src/PvP/P1unit"));
			String str4 = br4.readLine();
			unit_num = Integer.parseInt(str4);

			for (int i = 0; i < unit_num; i++) {
				br4.readLine();
				br4.readLine();
				weapon_num =Integer.parseInt(br4.readLine());
				weapon_list = new WeaponList(weapon_num);
				weapon = weapon_list.weapon;
				using_weapon[weapon.id]++;
			}
			br4.close();

			BufferedReader br = new BufferedReader(new FileReader("../src/PvP/P1unit"));
			String str = br.readLine();
			unit_num = Integer.parseInt(str);
			//	all_weapon_name = new String[unit_num];

			for (int i = 0; i < (CSPnum+1); i++) {
				br.readLine();
				br.readLine();
				weapon_num =Integer.parseInt(br.readLine());
				weapon_list = new WeaponList(weapon_num);
				weapon = weapon_list.weapon;
				//System.out.println("weapon.id = "+weapon.id);

				//all_weapon_name[i] = weapon.name;

			}
			br.close();

			//P1unit→P1unit2書き込み
			BufferedReader br2 = new BufferedReader(new FileReader("../src/PvP/P1unit"));
			PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter("../src/PvP/P1unit2")));
			while(true){
				str = br2.readLine();
				if(str == null) break;
				else pw2.println(str);
			}
			br2.close();
			pw2.close();

			/////////////////////item///////////////////////////
			having_weapon = new int[Weapon.kind+1];
			having_weapon[0] = 100;

			BufferedReader br3 = new BufferedReader(new FileReader("../src/Weapon/Player1Weapon"));
			String itemstr = br3.readLine();
			if(itemstr != null){
				String[] tokens1 = itemstr.split(",");
				System.out.println(" tokens1 : "+Arrays.toString(tokens1));

				for(int i = 0; i < tokens1.length; i++){
					having_weapon[Integer.parseInt(tokens1[i])]++;
				}
			}

			br3.close();

			/////////////////////item///////////////////////////

		}catch(IOException er){
			throw new RuntimeException(er);
		}
	}

}
