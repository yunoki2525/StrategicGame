package main;

import java.applet.AppletContext;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;


public class Player2StatePanel extends JApplet implements ActionListener{
	AppletContext ac; // アプレットのコンテキスト
	Dimension size; // アプレットの大きさ
	MainPanel mp;
	JButton bt;

	Player2CharacterStatusPanel[] p2csp;
	Unit[] my_unit;
	Unit unit;
	Piece[] all_friend;
	Piece[] friend;
	SkillList skill_list;
	MagicList magic_list;
	WeaponList weapon_list;
	Weapon weapon;

	int show_CDFnum;//どのキャラクターの詳細画面を表示しているか調べる変数
	int show_WSFnum;//どのキャラクターの装備変更画面を表示しているか調べる変数

	JPanel[] panel = new JPanel[3];
	CSPanel[] pstatus = new CSPanel[3];

	Container pane;

	// コンストラクタ
	public Player2StatePanel(AppletContext ac1, Dimension size1, MainPanel mp1)
	{
		pane = getContentPane();

		ac   = ac1;
		size = size1;
		mp   = mp1;

		setSize(size);

		pane.setSize(size);
		// レイアウトマネージャの停止
		pane.setLayout(null);
		// 背景色の設定
		pane.setBackground(Color.white);

		try{
			/////// ここから味方情報////////////
			BufferedReader br = new BufferedReader(new FileReader("../src/PvP/P1unit"));
			String str = br.readLine();
		//	System.out.println(str);
			int n0 = Integer.parseInt(str);
		//	System.out.println(n0);
			my_unit = new Unit[n0];

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
				System.out.println("status : "+Arrays.toString(status));
				System.out.println("pass : "+Arrays.toString(pass));

				skill_list = new SkillList(status[0]);
				Skill[] skill = new Skill[skill_list.skills.length];
				for(int j=0 ; j<skill_list.skills.length ; j++) skill[j] = skill_list.skills[j];

				magic_list = new MagicList(status[0]);
				Magic[] magic = new Magic[magic_list.magics.length];
				for(int j=0 ; j<magic_list.magics.length ; j++) magic[j] = magic_list.magics[j];

				weapon_list = new WeaponList(Integer.parseInt(br.readLine()));
				Weapon weapon = weapon_list.weapon;



				System.out.println("skill[0] :" + skill[0].name);
				System.out.println("magic[0] :" + magic[0].name);
				System.out.println("weapon :" + weapon.name);


				my_unit[i] =new Unit(tokens1[0],tokens1[1],tokens1[2],status[0],status[1],status[2],status[3],status[4],
						status[5],status[6],status[7],status[8],status[9],status[10],pass,skill,magic,weapon);

			}
			br.close();

		}catch(IOException er){
			throw new RuntimeException(er);
		}

		//キャラクターパネル初期化
		p2csp = new Player2CharacterStatusPanel[my_unit.length];
		for (int i = 0; i < my_unit.length; i++) {
			p2csp[i] = new Player2CharacterStatusPanel(ac, size, mp, this, my_unit[i],i);
		}

		BevelBorder border = new BevelBorder(BevelBorder.RAISED);
		//パネル初期化
		for(int i = 0; i < panel.length; i++){
			panel[i] = new JPanel();
			pstatus[i] = new CSPanel(my_unit[i]);
			pstatus[i].setBorder(border);
			pstatus[i].setBackground(Color.WHITE);
		}

		panel[0].setBackground(Color.YELLOW);
		panel[1].setBackground(Color.GREEN);
		panel[2].setBackground(Color.BLUE);


		//キャラクターパネル貼り付け
		for(int i = 0; i < panel.length; i++){
			panel[i].setLayout(null);
			panel[i].add(p2csp[i]);
			p2csp[i].setBounds(25, 0, 150, 300);
		}

		//paneに貼り付け
		for(int i = 0; i < panel.length; i++){
			pane.setLayout(null);
			pane.add(panel[i]);
			pane.add(pstatus[i]);
			panel[i].setBounds(200*i, 0, 200, 300);
			pstatus[i].setBounds(200*i, 300, 200, 300);
		}

	}

	// 描画
	public void paint(Graphics g) {
		super.paint(g); // 親クラスの描画
		// この Component が入力フォーカスを取得することを要求
		this.requestFocusInWindow();
	}

	// ボタンがクリックされたときの処理
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt) {
			mp.state = 10;
		}
	}

	public int getshow_CDFnum(){
		return this.show_CDFnum;
	}

	public void setshow_CDFnum(int n){
		this.show_CDFnum = n;
	}

	public int getshow_WSFnum(){
		return this.show_WSFnum;
	}

	public void setshow_WSFnum(int n){
		this.show_WSFnum = n;
	}

	public void detailFrameHide(int n){
		p2csp[n].detailFrameHide();
	}

	public void weaponSelectFrameHide(int n){
		p2csp[n].weaponSelectFrameHide();
	}

	public void setWeaponP1SP(Weapon weapon){
		this.weapon=weapon;
		pstatus[show_WSFnum].setWeapon(weapon);
	}
}

