package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JPanel;

public class P2CharaSele extends JPanel implements MouseListener, ActionListener{
	 MainPanel mp;
	JButton decide;

	Unit[][] unit = new Unit[8][];

	boolean[][] selected = new boolean[8][];

	int[][] img_posix = new int[8][];
	int[][] img_posiy = new int[8][];

	int img_width = 100;
	int img_height = 170;

	int sortie =0;
	int count = 0;
	int[][] idx;

	int x0 = 20;
	int y0 = 100;

	int space = 50;

	public P2CharaSele( MainPanel mp ,String field_name){
		this.mp = mp;

		setPreferredSize(new Dimension(950,1850));
		setBackground(Color.GRAY);

		addMouseListener(this);

		decide = new JButton("決定");
		decide.setBackground(new Color(205,85,85));
		decide.setForeground(new Color(250,250,250));
		decide.setFont(new Font("SansSerif", Font.ITALIC, 20));
		decide.addActionListener(this);
		setLayout(null);
		decide.setBounds(400,1760,80,45);
		add(decide);


		try{
			SkillList skill_list;
			MagicList magic_list;
			WeaponList weapon_list;

			BufferedReader br = new BufferedReader(new FileReader("resource/PvP/UnitData"));
			for(int i=0 ; i<unit.length ;i++){
				String str = br.readLine();  //から打ち
				str = br.readLine();         //地域ごとのユニット数
				int n0 = Integer.parseInt(str);
				unit[i] = new Unit[n0];
				selected[i] = new boolean[n0];
				img_posix[i] = new int[n0];
				img_posiy[i] = new int[n0];

				for(int j=0; j<n0 ; j++){
					str = br.readLine();
					String[] tokens1 = str.split(",");
					System.out.println(" tokens1 : "+Arrays.toString(tokens1));
					int[] status = new int[tokens1.length-3];
					str = br.readLine();
					String[] tokens2 = str.split(",");
					System.out.println(" tokens2 : "+Arrays.toString(tokens2));
					int[] pass = new int[tokens2.length];

					for(int k=0 ; k<status.length ; k++) status[k] = Integer.parseInt(tokens1[k+3]);
					for(int k=0 ; k<pass.length ; k++) pass[k] = Integer.parseInt(tokens2[k]);
					//System.out.println("status : "+Arrays.toString(status));
					//int[] posi= {status[11],status[12]};

					//System.out.println("posi : "+Arrays.toString(posi));

					skill_list = new SkillList(status[0]);
					Skill[] skill = new Skill[skill_list.skills.length];
					for(int k=0 ; k<skill_list.skills.length ; k++) skill[k] = skill_list.skills[k];

					magic_list = new MagicList(status[0]);
					Magic[] magic = new Magic[magic_list.magics.length];
					for(int k=0 ; k<magic_list.magics.length ; k++) magic[k] = magic_list.magics[k];

					weapon_list = new WeaponList(0);//Integer.parseInt(br.readLine()));
					Weapon weapon = weapon_list.weapon;

					System.out.println(Arrays.toString(tokens1));

					unit[i][j]= new Unit(tokens1[0],tokens1[1],tokens1[2],status[0],status[1],status[2],status[3],status[4],
							status[5],status[6],status[7],status[8],status[9],status[10],pass,skill,magic,weapon);

					img_posix[i][j] = x0 + j*img_width;
					img_posiy[i][j] = y0 + i*img_height + i*space;
				}

			}
			br.close();

			br = new BufferedReader(new FileReader("resource/Field/"+field_name));
			sortie = Integer.parseInt(br.readLine());
			idx = new int[sortie][2];
			br.close();

		}catch(IOException er){
			throw new RuntimeException(er);
		}
	}

	public void paint(Graphics g) {
		super.paint(g); // 親クラスの描画
		// この Component が入力フォーカスを取得することを要求
		this.requestFocusInWindow();

		//System.out.println("道を描くよ～");

		for(int i=0 ; i<unit.length ; i++){
			for(int j=0 ; j<unit[i].length ; j++){
				g.drawImage(unit[i][j].original_image, img_posix[i][j], img_posiy[i][j], img_width, img_height,this);
			}
		}
		int x1 = 200; int y1 = -10;
		g.setFont( new Font("Serif", Font.BOLD | Font.ITALIC, 25));
		g.setColor(Color.YELLOW);
		g.drawString("九州", x1, y0 + y1 );
		g.drawString("四国", x1, y0 + y1 + img_height*1 + space *1);
		g.drawString("中国", x1, y0 + y1 + img_height*2 + space *2);
		g.drawString("関西", x1, y0 + y1 + img_height*3 + space *3);
		g.drawString("中部", x1, y0 + y1 + img_height*4 + space *4);
		g.drawString("東北", x1, y0 + y1 + img_height*5 + space *5);
		g.drawString("関東", x1, y0 + y1 + img_height*6 + space *6);
		g.drawString("最端", x1, y0 + y1 + img_height*7 + space *7);

		g.setFont( new Font("Monspaced", Font.BOLD |  Font.ITALIC, 20));
		g.setColor(Color.WHITE);
		g.drawString("誰を選出する？("+sortie+"人)",200,40);




		BasicStroke stroke;
		Graphics2D g2 = (Graphics2D)g;
		stroke = new BasicStroke(3.0f); //線の太さを変更
		g2.setStroke(stroke); //線の種類を設定

		g2.setColor(Color.RED);
		for(int i=0 ; i<selected.length ; i++){
			for(int j=0 ; j<selected[i].length ; j++){
				if(selected[i][j])g2.drawRect(img_posix[i][j], img_posiy[i][j], img_width, img_height);
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		int x = e.getX();
		int y = e.getY();
		System.out.println("( x , y ) = ( "+x+" , "+y+" )");
		for(int i=0 ; i<unit.length ;i++){
			for(int j=0; j<unit[i].length ; j++){
				if(img_posix[i][j] <= x && x <= img_posix[i][j]+img_width &&
						img_posiy[i][j] <= y && y <= img_posiy[i][j]+img_width ){

					if(selected[i][j]){
						selected[i][j]  = false ;
						for(int k=0 ; k<sortie ; k++){
							if(unit[i][j].name.equals(unit[idx[k][0]][idx[k][1]].name)){
								for(int l=k ; l<sortie-1 ; l++){
									idx[l][0] = idx[l+1][0]; idx[l][1] = idx[l+1][1];
								}
							}
						}
						count--;
						System.out.println(unit[i][j].name+"？なにそれおいしいの？");
					}else{
						if(count < sortie){
							selected[i][j] = true ;
							idx[count][0] = i; idx[count][1] = j;
							count++;

							System.out.println(unit[i][j].name+"が選ばれたよ？");
						}else{
							System.out.println("出撃ユニット制限にひっかかりました");
						}
					}
				}
			}
		}
		repaint();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == decide){
			System.out.println("count : "+ count);
			System.out.println("sortie : "+ sortie);

			if(count == sortie){
				System.out.println("書き込み開始");
				Unit[] myUnit = new Unit[sortie];
				PrintWriter pw;
				try {
					pw = new PrintWriter(new BufferedWriter(new FileWriter("resource/PvP/P2unit")));
					pw.println(sortie);
					for(int i=0 ; i<myUnit.length ; i++){
						myUnit[i] = unit[idx[i][0]][idx[i][1]];
						String str = myUnit[i].toString();
						String[] str2 = str.split("/");
						//System.out.println(str);
						System.out.println(Arrays.toString(str2));
						pw.println(str2[0]);
						pw.println(str2[1]);
						pw.println(0);
					}
					pw.close();
				} catch (IOException e1) {
					// TODO 自動生成された catch ブロック
					e1.printStackTrace();
				}
			}
		}
		mp.state = 11;

	}
}

