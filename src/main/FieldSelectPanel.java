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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;


public class FieldSelectPanel  extends JApplet  implements ActionListener{
	AppletContext ac; // アプレットのコンテキスト
	Dimension size; // アプレットの大きさ
	MainPanel mp;
	JButton decide,right,left,back;

	int[][] field;
	int field_width = 20;
	int field_height = 10;
	int cells = 0;

	int image_w = 48;
	int image_h = 48;

	String[] field_name = {"無限回廊","セルムブルグ","ニブルヘイム","アオイの島"};
	int select = 0;

	Image[] map_image = new Image[7];
	public FieldSelectPanel(AppletContext ac1, Dimension size1, MainPanel mp1){
		Container pane = getContentPane();
		ac   = ac1;
		size = size1;
		mp   = mp1;
		setSize(size);
		pane.setSize(size);
		pane.setBackground(new Color(150,150,255));
					// レイアウトマネージャの停止
		pane.setLayout(null);
					// 背景色の設定
		//pane.setBackground(Color.white);
					// ボタンの配置

		readField();

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



		right = new JButton(new ImageIcon("../bin/ButtonImage/arrow/arrow_right.png"));
		left = new JButton(new ImageIcon("../bin/ButtonImage/arrow/arrow_left.png"));
		back = new JButton(new ImageIcon("../bin/ButtonImage/cross_mark/back.png"));

		right.setBackground(new Color(150,150,255));
		left.setBackground(new Color(150,150,255));
		back.setBackground(Color.WHITE);

		decide = new JButton("ここで戦う");
		decide.setBackground(Color.cyan);
		decide.setForeground(Color.magenta);
		decide.setFont(new Font("SansSerif", Font.ITALIC, 20));

		decide.addActionListener(this);
		right.addActionListener(this);
		left.addActionListener(this);
		back.addActionListener(this);

		setLayout(null);
		decide.setBounds(190,540,220,50);
		pane.add(decide);

		left.setBounds(100,30,30,20);
		pane.add(left);

		right.setBounds(470,30,30,20);
		pane.add(right);



		back.setBounds(570,20,20,20);
		pane.add(back);


	}
	public void paint(Graphics g) {
		super.paint(g); // 親クラスの描画
		// この Component が入力フォーカスを取得することを要求
		this.requestFocusInWindow();

		Font f = new Font("Serif", Font.BOLD |  Font.ITALIC, 30);
		FontMetrics fm = getFontMetrics(f);
		int w = fm.stringWidth(field_name[select]);
		int h = fm.getHeight();

		g.setFont(f);
		g.setColor(Color.RED);
		g.drawString(field_name[select],size.width/2 - w/2, 30 + h/2);


		g.setColor(Color.BLACK);
		int x0 =10; int y0 =100;
		for (int i = 0; i < field[0].length; i++) {
			for (int j = 0; j < field.length; j++) {
				switch (field[j][i]) {
				case BattleView.plain:
					g.drawImage(map_image[BattleView.plain], i * cells +x0, j * cells +y0,
							(i + 1) * cells +x0, (j + 1) * cells +y0, 0, 0, image_w, image_h, this);
					break;
				case BattleView.sea:
					g.drawImage(map_image[BattleView.sea], i * cells +x0, j * cells +y0,
							(i + 1) * cells +x0, (j + 1) * cells +y0, 0, 0, image_w, image_h, this);
					break;
				case BattleView.mountain:
					g.drawImage(map_image[BattleView.mountain], i * cells +x0, j * cells +y0,
							(i + 1) * cells +x0, (j + 1) * cells +y0, 0, 0, image_w,
							image_h, this);
					break;
				case BattleView.wood:
					g.drawImage(map_image[BattleView.wood], i * cells +x0, j * cells +y0,
							(i + 1) * cells +x0, (j + 1) * cells +y0, 0, 0, image_w, image_h, this);
					break;
				case BattleView.fortress:
					g.drawImage(map_image[BattleView.fortress], i * cells +x0, j * cells +y0,
							(i + 1) * cells +x0, (j + 1) * cells +y0, 0, 0, image_w,
							image_h, this);
					break;
				case BattleView.road:
					g.drawImage(map_image[BattleView.road], i * cells +x0, j * cells +y0,
							(i + 1) * cells +x0, (j + 1) * cells +y0, 0, 0, image_w, image_h, this);
					break;
				case BattleView.noentry:
					g.drawImage(map_image[BattleView.noentry], i * cells +x0, j * cells +y0,
							(i + 1) * cells +x0, (j + 1) * cells +y0, 0, 0, image_w,
							image_h, this);
					break;

				}
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == right ){
			if(select++ == field_name.length-1) select = 0;
			readField();
			repaint();
		}
		if(src == left ){
			if(select-- == 0) select = field_name.length-1;
			readField();
			repaint();
		}
		if(src == back ){
			mp.state = 1;
		}
		if(src == decide ){
			mp.preperatePvP( field_name[select] );
			mp.state = 10;
		}

	}

	public void readField(){
		try {
			BufferedReader br = new BufferedReader(new FileReader("../src/Field/"+field_name[select]));
//			br.readLine();br.readLine();
			String str = br.readLine();
			System.out.println(str);
			br.readLine();
			str = br.readLine();
			System.out.println(str);
			String[] map_size = str.split(",");
			field = new int[Integer.parseInt(map_size[1])][Integer.parseInt(map_size[0])];
			cells = 600 / (field[0].length +1);
			System.out.println("cells : "+cells);
			for(int i=0 ; i<field.length ; i++)
				br.readLine();
			br.readLine();
			for(int i=0 ; i<field.length ; i++){
				str = br.readLine();
				System.out.println(str);
				String[] field_data = str.split(",");
				for(int j=0 ; j<field[0].length ; j++){
					field[i][j] = Integer.parseInt(field_data[j]);
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
