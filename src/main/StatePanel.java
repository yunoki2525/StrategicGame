package main;

import java.applet.AppletContext;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class StatePanel extends JApplet implements ActionListener {
	AppletContext ac; // アプレットのコンテキスト
	Dimension size; // アプレットの大きさ
	MainPanel mp;
	JButton[] bt;
	JPanel p1, p2, p3;
	JButton BackBt;
	Image backImage;

	CharacterStatusPanel[] csp;
	JScrollPane scr;
	int show_CDFnum;//どのキャラクターの詳細画面を表示しているか調べる変数
	int show_WSFnum;//どのキャラクターの装備変更画面を表示しているか調べる変数

	Unit[] my_unit;

	//変更
	Piece[] all_friend;
	Piece[] friend;
	SkillList skill_list;
	MagicList magic_list;
	WeaponList weapon_list;

	// コンストラクタ
	public StatePanel(AppletContext ac1, Dimension size1, MainPanel mp1) {
		ac = ac1;
		size = size1;
		mp = mp1;
		String[] str1 = { "メニュー", "ストーリー", "マップ", "ステータス", "バトル", "ゲームオーバー", "バトルスクリーン" };// ボタンの名前の配列
		bt = new JButton[str1.length];
		BackBt = new JButton("戻る");

		setSize(size);

		//ステータスの詳細画面の表示関連
		show_CDFnum = -1;

		// ボタンの配置
		for (int i = 0; i < str1.length; i++) {
			bt[i] = new JButton(str1[i]);
			bt[i].setPreferredSize(new Dimension(200, 150));
			bt[i].addActionListener(this); // アクションリスナ
		}

		//画像取り込み
		try{
			backImage = ImageIO.read(new File("Image/teitoku.png"));
		}catch(IOException e){
			throw new RuntimeException(e);
		}

		try{
			/////// ここから味方情報////////////
//			SkillList skill_list;
//			WeaponList weapon_list;
//			BufferedReader br = new BufferedReader(new FileReader("../src/Stage/friend_test"));
//			String str1 = br.readLine();
//		//	System.out.println(n0);
//			my_unit = new Unit[Integer.parseInt(str1)];
//
//			for(int i=0; i<my_unit.length ; i++){
//				str1 = br.readLine();
//				String[] tokens1 = str1.split(",");
//				//System.out.println(" tokens1 : "+Arrays.toString(tokens1));
//				int[] status = new int[tokens1.length-1];
//
//				for(int j=0 ; j<status.length ; j++) status[j] = Integer.parseInt(tokens1[j+1]);
//
//				skill_list = new SkillList(status[0]);
//				Skill[] skill = new Skill[skill_list.skills.length];
//				for(int j=0 ; j<skill_list.skills.length ; j++) skill[j] = skill_list.skills[j];
//
//				weapon_list = new WeaponList(0);//status[0]);
//				Weapon weapon = weapon_list.weapon;
//
//				my_unit[i]=new Unit(tokens1[0],status[0],status[1],status[2],status[3],status[4],
//						status[5],status[6],status[7],status[8],status[9],status[10],skill,weapon);
			BufferedReader br = new BufferedReader(new FileReader("../src/Stage/MyData"));
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

		csp = new CharacterStatusPanel[my_unit.length];
		for (int i = 0; i < my_unit.length; i++) {
			csp[i] = new CharacterStatusPanel(ac, size, mp, this, my_unit[i],i);
		}

		Container pane = getContentPane();

		pane.setLayout(null);
		pane.setBackground(Color.GREEN);
		for (int i = 0; i < my_unit.length; i++) {
			pane.add(csp[i]);
			if(i<4)csp[i].setBounds(150*i,0,150,300);
			else csp[i].setBounds((150*(i-4))+75,300,150,300);
		}
		BackBt.addActionListener(this);
		pane.add(BackBt);
		BackBt.setBounds((150*3)+75,570,73,28);

	}

	public void paint(Graphics g) {
		super.paint(g); // 親クラスの描画
	}

	public void panelResize(int n){
		if(n > 8){
			p2.setPreferredSize(new Dimension(400, 600+200*(int)((n-8)/2+1/2)));
		}
	}

	// ボタンがクリックされたときの処理
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt[0] || e.getSource() == BackBt){ // GameMenuPanelへ
			if(show_CDFnum != -1)detailFrameHide(show_CDFnum);
			mp.state = 1;
		}
		else if (e.getSource() == bt[1]) // StoryPanelへ
			mp.state = 2;
		else if (e.getSource() == bt[2]) // GameMapPanel
			mp.state = 3;
		else if (e.getSource() == bt[3]) // StatePanel
			mp.state = 4;
		else if (e.getSource() == bt[4]) // BattlePanel
			mp.state = 5;
		else if (e.getSource() == bt[5]) // GameOverPanel
			mp.state = 6;
		else if (e.getSource() == bt[6]) // BattleScreenPanel
			mp.state = 7;
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
		csp[n].detailFrameHide();
	}

	public void weaponSelectFrameHide(int n){
		csp[n].weaponSelectFrameHide();
	}
}