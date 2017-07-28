package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class CharacterDetailFrame extends JApplet {
	// フィールド
	Unit unit;
	Image gazou1;
	int weapon_num;//試験用変数

	Weapon weapon;

	//キャラクター
	String[] PictureFile = { "", "picture/hokkaidou.gif", "picture/aomori.gif", "picture/iwate.gif",
			"picture/miyagi.gif",
			"picture/akita.gif", "picture/yamagata.gif", "picture/hukusima.gif", "picture/ibaragi.gif",
			"picture/totigi.gif", "picture/gunma.gif", "picture/saitama.gif", "picture/tiba.gif",
			"picture/toukyou.gif", "picture/kanagawa.gif", "picture/niigata.gif", "picture/toyama.gif",
			"picture/isikawa.gif", "picture/hukui.gif", "picture/yamanasi.gif", "picture/nagano.gif",
			"picture/gihu.gif", "picture/sizuoka.gif", "picture/aiti.gif", "picture/mie.gif",
			"picture/siga.gif", "picture/kyouto.gif", "picture/oosaka.gif", "picture/hyougo.gif",
			"picture/nara.gif", "picture/wakayama.gif", "picture/tottori.gif", "picture/simane.gif",
			"picture/okayama.gif", "picture/hirosima.gif", "picture/yamaguti.gif", "picture/tokusima.gif",
			"picture/kagawa.gif", "picture/ehime.gif", "picture/kouti.gif", "picture/hukuoka.gif",
			"picture/saga.gif", "picture/nagasaki.gif", "picture/kumamoto.gif", "picture/ooita.gif",
			"picture/miyazaki.gif", "picture/kagosima.gif", "picture/okinawa.gif", "picture/zero.png",
	};

	JPanel p1,p2,p3,p4,p5,p6;
	JLabel lb,lb2,lb3;

	String[][]data3;

	Container pane ;

	// コンストラクタ
	public CharacterDetailFrame(Unit unit) {
		this.unit = unit;

		//試験用変数
		weapon_num = 0;

		pane = getContentPane();

		try {
			gazou1 = ImageIO.read(new File(PictureFile[unit.id]));
		} catch (IOException er) {
			throw new RuntimeException(er);
		}

		//装備関連
		int[] weapondata = new int[] {unit.weapon.HP,
									  unit.weapon.ATK,
									  unit.weapon.DEF,
									  unit.weapon.SP,
									  unit.weapon.INT,
									  unit.weapon.MEN,
									  unit.weapon.AGI,
									  unit.weapon.DEX,
									  unit.weapon.LUCK};

		// ステータス表関連
		String[][] data = new String[][] {  { "ステータス名", "数値(装備補正)" },
											{ "HP"            , String.valueOf(unit.HP)}, //　体力　：Hit Point(HP)：ヒットポイント
											{ "ATK(攻撃力)"   , String.valueOf(unit.ATK)},//　攻撃　：Attack(ATK)：アタック
											{ "DEF(防御力)"   , String.valueOf(unit.DEF)},//　防御　：Defense(DEF)：ディフェンス
											{ "SP(魔力)"      , String.valueOf(unit.SP) },//　魔力　：Magic Point(MP)：マジックポイント
											{ "INT(魔法攻撃)" , String.valueOf(unit.INT)},//魔法攻撃：Magic Attack(MATK/MAT)：マジックアタック
											{ "MEN(魔法防御)" , String.valueOf(unit.MEN)},//魔法防御：Magic Defense(MDEF/MDE)：マジックディフェンス
											{ "AGI(回避)"     , String.valueOf(unit.AGI)},//　回避率：Evasion（EVA）：エヴァージョン
											{ "DEX(命中)"     , String.valueOf(unit.DEX)},//　命中率：Dexterity（DEX）：デクステリティー
											{ "LUCK(運)"      , String.valueOf(unit.LUCK)},//　運　　：Luck（LUK）:ラック
		};

		String[][] data2 = new String[][] {  { "クリティカルクラス", "数値(装備補正)" },
											 { "斬", String.valueOf(unit.PASS[Unit.PASS_SLASH]) },
											 { "突", String.valueOf(unit.PASS[Unit.PASS_SPEAR]) },
											 { "打", String.valueOf(unit.PASS[Unit.PASS_BLOW]) },
											 { "火", String.valueOf(unit.PASS[Unit.PASS_FIRE]) },
											 { "水", String.valueOf(unit.PASS[Unit.PASS_AQUA]) },
											 { "天", String.valueOf(unit.PASS[Unit.PASS_SKY]) },
											 { "地", String.valueOf(unit.PASS[Unit.PASS_GRAND]) },
											 { "光", String.valueOf(unit.PASS[Unit.PASS_RAY]) },
											 { "闇", String.valueOf(unit.PASS[Unit.PASS_DARK]) },
		};

		weapon = this.unit.weapon;

		data3 = new String[][] {  {"使える技", "詳細"},
											 {"スキル(魔法)名", "効果"},
			                                 {"???"    , "???"},//unit.skill[1].name
			                                 {"スキル(魔法)名", "効果"},
			                                 {"???"     , "???"},
			                                 {"スキル(魔法)名", "効果"},
			                                 {"???"     , "???"},//unit.skill[2].name
			                                 {"装備"                 , weapon.name}

		};

		// ラベルのオブジェクトの生成と貼り付け
		p1 = new JPanel();
		p2 = new JPanel();
		//左
		p3 = new JPanel();
		p4 = new JPanel();
		//右
		p5 = new JPanel(){
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(gazou1, this.getWidth()/5, 0, this.getWidth()*4/5, this.getHeight(), 0, 0, gazou1.getWidth(this), gazou1.getHeight(this), this);
			}
		};
		p6 = new JPanel();

		//全体　左右に分ける
		pane.setLayout(new GridLayout(1, 2));
		pane.add(p1);
		pane.add(p2);

		//左パネルを3つに分ける
		JLabel nameLb = new JLabel("name: "+unit.name);
		nameLb.setHorizontalAlignment(SwingConstants.CENTER);
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		p1.add(nameLb);
		p1.add(p3);
		p1.add(p4);
		p1.setBackground(new Color(0xafeeee));//paleturquoise

		//右パネルを2つに分ける
		p2.setLayout(new GridLayout(2, 1));
		p2.add(p5);
		p2.add(p6);
		p2.setBackground(new Color(0xafeeee));//paleturquoise

		//左上のパネル(p3)
		p3.setBackground(new Color(0xafeeee));//paleturquoise
		p3.setLayout(new GridLayout(data.length, 2));
		for(int i = 0 ; i < data.length ; i++){
			for(int j = 0 ; j < 2 ; j++){
				lb = new JLabel();

				//赤い枠線
				if(i==0){
					LineBorder border = new LineBorder(Color.RED, 2, true);
					lb.setBorder(border);
				}

				lb.setOpaque(true);// 不透明の設定
				if(i%2==0)lb.setBackground(new Color(0x87cefa));//lightskyblue
				else lb.setBackground(new Color(0x00bfff));//deepskyblue
				lb.setFont(new java.awt.Font("Serif", 0, 10));
				lb.setForeground(Color.BLACK);
				lb.setHorizontalAlignment(SwingConstants.CENTER);

				//装備のステータス追加
				if( j == 0 || i == 0){
					lb.setText(data[i][j]);
				}else if(weapondata[i-1] == 0){
					lb.setText(data[i][j]+" (±0) ");
				}else if(weapondata[i-1] > 0){
					lb.setText(data[i][j]+" (+"+weapondata[i-1]+") ");
				}else{
					lb.setText(data[i][j]+" ("+weapondata[i-1]+") ");
				}

				p3.add(lb);
			}
		}

		//左下のパネル(p4)
		p4.setBackground(new Color(0xafeeee));//paleturquoise
		p4.setLayout(new GridLayout(data2.length, 2));
		for(int i = 0 ; i < data2.length ; i++){
			for(int j = 0 ; j < 2 ; j++){
				lb2 = new JLabel();

				if(i==0){
					LineBorder border = new LineBorder(Color.RED, 2, true);
					lb2.setBorder(border);
				}


				lb2.setOpaque(true);// 不透明の設定
				if(i%2==0) lb2.setBackground(new Color(0x87cefa));//lightskyblue
				else       lb2.setBackground(new Color(0x00bfff));//deepskyblue

				lb2.setFont(new java.awt.Font("Serif", 0, 10));
				lb2.setForeground(Color.BLACK);
				lb2.setHorizontalAlignment(SwingConstants.CENTER);

				//装備のステータス追加
				if( j == 0 || i == 0){
					lb2.setText(data2[i][j]);
				}else if(weapon_num == 0){
					lb2.setText(data2[i][j]);
				}else if(weapon_num > 0){
					lb2.setText(data2[i][j]);
				}else{
					lb2.setText(data2[i][j]);
				}

				p4.add(lb2);
			}
		}

		//右上のパネル(p5)
		p5.setBackground(new Color(0xafeeee));//paleturquoise


		//右下のパネル(p6)
		p6.setBackground(new Color(0xafeeee));//paleturquoise
		int num = 1;
		p6.setLayout(new GridLayout(data3.length, 2));
		for(int i = 1 ; i < unit.skill.length ; i++){
			data3[2*num][0] = unit.skill[i].name;
			data3[2*num][1] = unit.skill[i].toString();
			num++;
		}
		for(int i = 1 ; i < unit.magic.length ; i++){
			data3[2*num][0] = unit.magic[i].name;
			data3[2*num][1] = unit.magic[i].toString();
			num++;
		}
		for(int i = 0 ; i < data3.length ; i++){
			for(int j = 0 ; j < 2 ; j++){
				lb3 = new JLabel();

				if(i==0){
					LineBorder border = new LineBorder(Color.RED, 2, true);
					lb3.setBorder(border);
				}

				lb3.setOpaque(true);// 不透明の設定
				if(i%2==0) lb3.setBackground(new Color(0x87cefa));//lightskyblue
				else       lb3.setBackground(new Color(0x00bfff));//deepskyblue

				lb3.setFont(new java.awt.Font("Serif", 0, 10));
				lb3.setForeground(Color.BLACK);
				if( i==data3.length-1 && j==1 )lb3.setForeground(Color.YELLOW);
				lb3.setHorizontalAlignment(SwingConstants.CENTER);
				lb3.setText(data3[i][j]);
				p6.add(lb3);
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g); // 親クラスの描画
	}

	public void setWeaponCDF(Weapon weapon){
		this.weapon = weapon;
		data3[5][1]=weapon.name;
		p2.remove(p6);

		p6= new JPanel();
		//右下のパネル(p6)
		p6.setBackground(new Color(0xafeeee));//paleturquoise

		p6.setLayout(new GridLayout(data3.length, 2));
		for(int i = 0 ; i < data3.length ; i++){
			for(int j = 0 ; j < 2 ; j++){
				lb3 = new JLabel();

				if(i==0){
					LineBorder border = new LineBorder(Color.RED, 2, true);
					lb3.setBorder(border);
				}

				lb3.setOpaque(true);// 不透明の設定
				if(i%2==0) lb3.setBackground(new Color(0x87cefa));//lightskyblue
				else       lb3.setBackground(new Color(0x00bfff));//deepskyblue

				lb3.setFont(new java.awt.Font("Serif", 0, 10));
				lb3.setForeground(Color.BLACK);
				if( i==data3.length-1 && j==1 )lb3.setForeground(Color.YELLOW);
				lb3.setHorizontalAlignment(SwingConstants.CENTER);
				lb3.setText(data3[i][j]);
				p6.add(lb3);
			}
		}
		p2.add(p6);
	}

}
