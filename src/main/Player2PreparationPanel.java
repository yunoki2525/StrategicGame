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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;

public class Player2PreparationPanel extends JApplet implements ActionListener{
	AppletContext ac; // アプレットのコンテキスト
	Dimension size; // アプレットの大きさ
	MainPanel mp;

	JButton backButton, battleButton, statusButton, shopButton, weaponButton;

	Image button1,backIcon,battleIcon,statusIcon,shopIcon,weaponIcon;
	ImageIcon gostButtonIcon;

	//buttonの配置をずらすための変数
	int dx=0;
	int dy=0;

	String field_name;

	// コンストラクタ
	public Player2PreparationPanel(AppletContext ac1, Dimension size1, MainPanel mp1, String file_name) {
		ac = ac1;
		size = size1;
		mp = mp1;
		this.field_name = file_name;
		setSize(size);

		Container pane = getContentPane();
		pane.setLayout(null);

		try{
			button1 = ImageIO.read(new File("ButtonImage/button1.png"));

			backIcon = ImageIO.read(new File("Image/teitoku.png"));

			battleIcon = ImageIO.read(new File("ButtonImage/出撃アイコン3.png"));
			statusIcon = ImageIO.read(new File("ButtonImage/ステータスアイコン1.png"));
			shopIcon = ImageIO.read(new File("ButtonImage/ショップアイコン1.png"));
			weaponIcon = ImageIO.read(new File("ButtonImage/出撃アイコン1.png"));

			gostButtonIcon = new ImageIcon("ButtonImage/透明.png");

		}catch(IOException e){
			throw new RuntimeException(e);
		}


		//		backButton = new JButton(){
		//			public void paintComponent(Graphics g) {
		//				super.paintComponent(g);
		//				//全部座標
		//				g.drawImage(backIcon, 50, 50, 148, 148, 0, 0, (148-50)*480/600, (148-50)*480/600, this);
		//
		//				g.drawImage(button1, 0, 0, 148, 148,this);
		//
		//				g.setColor(Color.WHITE);
		//				g.setFont(new Font("Serif", Font.BOLD, 40));
		//				g.drawString("休",  3+50, 35+50);
		//				g.drawString("憩", 25+50, 60+50);
		//
		//				//ボタンの線を消す
		//				setBorderPainted(false);
		//			}
		//		};

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

		weaponButton = new JButton(){
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				//全部座標
				g.drawImage(backIcon, 0, 0, 80, 80, (250+dx)*480/600, (400+dy)*480/600, ((250+dx)+80)*480/600, ((400+dy)+80)*480/600, this);

				g.drawImage(weaponIcon, 0, 0, 80, 80,this);

				g.setColor(Color.WHITE);
				g.setFont(new Font("Serif", Font.BOLD, 35));
				g.drawString("装", 255-250, 475-400);
				g.drawString("備", 285-250, 485-400);

				//ボタンの線を消す
				setBorderPainted(false);
			}
		};

		//	backButton.addActionListener(this);
		battleButton.addActionListener(this);
		statusButton.addActionListener(this);
		shopButton.addActionListener(this);
		weaponButton.addActionListener(this);

		//pane.add(backButton);
		pane.add(battleButton);
		pane.add(statusButton);
		pane.add(shopButton);
		pane.add(weaponButton);

		//	backButton.setBounds(-50,-50, 148, 148);
		battleButton.setBounds(125+dx, 300+dy, 125, 125);
		statusButton.setBounds(145+dx, 200+dy,  80,  80);
		shopButton.setBounds(   50+dx, 400+dy,  80,  80);
		weaponButton.setBounds(250+dx, 400+dy,  80,  80);


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
		//		g.drawImage(button1,-50,-50, 148, 148,this);
		//
		//		g.setColor(Color.WHITE);
		//		g.setFont(new Font("Serif", Font.BOLD, 40));
		//		g.drawString("休", 3, 35);
		//		g.drawString("憩", 25, 60);
		///////////////////////////////////////////

		//////////////////遷移選択/////////////////
		g.drawImage(battleIcon, 125+dx, 300+dy, 125, 125, this);//真ん中　出撃
		g.drawImage(statusIcon, 145+dx, 200+dy,  80,  80, this);//上　　　ステータス
		g.drawImage(shopIcon,    50+dx, 400+dy,  80,  80, this);//左下　　ショップ
		g.drawImage(weaponIcon, 250+dx, 400+dy,  80,  80, this);//右下　　装備選択

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
		g.drawString("装", 255+dx, 475+dy);
		g.drawString("備", 285+dx, 485+dy);
		///////////////////////////////////////////

	}

	// ボタンがクリックされたときの処理
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backButton){   //休憩
			System.out.println("backButton");
			mp.state = 0;
		}
		else if(e.getSource() == battleButton){ //出撃

			SkillList skill_list;
			MagicList magic_list;
			WeaponList weapon_list;
			Piece[] player1;
			Piece[] player2;
			int[][] map;
			int[][] field;
			Clip mapMusic = null ;
			try{
				/////// ここからPlayer1情報////////////
				BufferedReader br = new BufferedReader(new FileReader("../src/PvP/P1unit"));
				String str = br.readLine();
				//	System.out.println(str);
				int n0 = Integer.parseInt(str);
				//	System.out.println(n0);
				player1 = new Piece[n0];

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

					player1[i]=new Piece(new Unit(tokens1[0],tokens1[1],tokens1[2],status[0],status[1],status[2],status[3],status[4],
							status[5],status[6],status[7],status[8],status[9],status[10],pass,skill,magic,weapon),posi);


					player1[i].direction = Piece.left;
				}
				br.close();


				/////// ここからPlayer2情報////////////
				br = new BufferedReader(new FileReader("../src/PvP/P2unit"));
				str = br.readLine();
				int n2 = Integer.parseInt(str);
				player2 = new Piece[n2];

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

					skill_list = new SkillList(status[0]);
					Skill[] skill = new Skill[skill_list.skills.length];
					for(int j=0 ; j<skill_list.skills.length ; j++) skill[j] = skill_list.skills[j];

					magic_list = new MagicList(status[0]);
					Magic[] magic = new Magic[magic_list.magics.length];
					for(int j=0 ; j<magic_list.magics.length ; j++) magic[j] = magic_list.magics[j];

					weapon_list = new WeaponList(Integer.parseInt(br.readLine()));//status[0]);
					Weapon weapon = weapon_list.weapon;

					int[] posi ={0, 0};

					System.out.println("enemyposi"+posi[0]+","+posi[1]);
					player2[i]=new Piece(new Unit(tokens1[0],tokens1[1],tokens1[2],status[0],status[1],status[2],status[3],status[4],
							status[5],status[6],status[7],status[8],status[9],status[10],pass,skill,magic,weapon),posi);

					player2[i].direction = Piece.right;
				}
				br.close();


				br = new BufferedReader(new FileReader("../src/Field/"+field_name));
				str = br.readLine();
				str = br.readLine();
				try {
					mapMusic = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
					mapMusic.open(AudioSystem.getAudioInputStream(new File("../bin/music/mapMusic/"+str+".wav")));
				} catch (LineUnavailableException | UnsupportedAudioFileException e1) {
					// TODO 自動生成された catch ブロック
					e1.printStackTrace();
				}


				str = br.readLine();
				String[] n1 = str.split(",");
				map = new int[Integer.parseInt(n1[1])][Integer.parseInt(n1[0])];
				field =  new int[Integer.parseInt(n1[1])][Integer.parseInt(n1[0])];

				int k=0; int l=0;
				for(int i=0 ; i<map.length ; i++){
					str = br.readLine();
					n1 = str.split(",");
					for(int j=0 ; j<map[0].length ; j++){
						map[i][j] = Integer.parseInt(n1[j]);
						if(map[i][j] == 4){
							map[i][j] = -1;
							player2[k].position[0] = j; player2[k].position[1] = i;
							k++;
						} else if(map[i][j] == 7){
							map[i][j] = 1;
							player1[l].position[0] = j; player1[l].position[1] = i;
							l++;
						}else map[i][j] = 0;
					}
				}
				br.readLine();

				for(int i=0 ; i<map.length ; i++){
					str = br.readLine();
					n1 = str.split(",");
					for(int j=0 ; j<map[0].length ; j++){
						field[i][j] = Integer.parseInt(n1[j]);
					}
				}
			}catch(IOException er){
				throw new RuntimeException(er);
			}

			mp.jumpBattlePanel(map, field, player1, player2, mapMusic);
			mp.state = 5;
			System.out.println("battleButton");
		}
		else if(e.getSource() == statusButton){ //編成
			mp.state = 13;
			System.out.println("statusButton");
		}
		else if(e.getSource() == shopButton){   //買い物
			mp.state = 15;
			System.out.println("shopButton");
		}
		else if(e.getSource() == weaponButton){ //装備
			mp.state = 17;
			System.out.println("weaponButton");
		}
	}
}
