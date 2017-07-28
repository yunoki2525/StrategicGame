package main;

import java.applet.AppletContext;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class Player1CharacterStatusPanel extends JPanel implements ActionListener, MouseListener, WindowListener{// キャラクターのステータスを表示するためのパネル
	AppletContext ac; // アプレットのコンテキスト
	Dimension size; // アプレットの大きさ

	MainPanel mp;
	Player1StatePanel p1sp;
	Unit my_unit;

	JPanel p1,p2;
	JLabel lb1,lb2;

	// クリックされたときに表示関連
	JFrame detailFrame,weaponSelectFrame;
	CharacterDetailFrame cdf;
	int CSPnum;//どのキャラクターを表示しているか調べる変数

	// 装備変更の画面表示関連
	JButton weaponBt;
	Player1WeaponSelectFrame p1wsf;

	// キャラクター
	String[] PictureFile = { "", "resource/picture/hokkaidou.gif", "resource/picture/aomori.gif", "resource/picture/iwate.gif",
			"resource/picture/miyagi.gif",
			"resource/picture/akita.gif", "resource/picture/yamagata.gif", "resource/picture/hukusima.gif", "resource/picture/ibaragi.gif",
			"resource/picture/totigi.gif", "resource/picture/gunma.gif", "resource/picture/saitama.gif", "resource/picture/tiba.gif",
			"resource/picture/toukyou.gif", "resource/picture/kanagawa.gif", "resource/picture/niigata.gif", "resource/picture/toyama.gif",
			"resource/picture/isikawa.gif", "resource/picture/hukui.gif", "resource/picture/yamanasi.gif", "resource/picture/nagano.gif",
			"resource/picture/gihu.gif", "resource/picture/sizuoka.gif", "resource/picture/aiti.gif", "resource/picture/mie.gif",
			"resource/picture/siga.gif", "resource/picture/kyouto.gif", "resource/picture/oosaka.gif", "resource/picture/hyougo.gif",
			"resource/picture/nara.gif", "resource/picture/wakayama.gif", "resource/picture/tottori.gif", "resource/picture/simane.gif",
			"resource/picture/okayama.gif", "resource/picture/hirosima.gif", "resource/picture/yamaguti.gif", "resource/picture/tokusima.gif",
			"resource/picture/kagawa.gif", "resource/picture/ehime.gif", "resource/picture/kouti.gif", "resource/picture/hukuoka.gif",
			"resource/picture/saga.gif", "resource/picture/nagasaki.gif", "resource/picture/kumamoto.gif", "resource/picture/ooita.gif",
			"resource/picture/miyazaki.gif", "resource/picture/kagosima.gif", "resource/picture/okinawa.gif", "resource/picture/zero.png",
	};

	Image gazou1;
	String name;

	public Player1CharacterStatusPanel(AppletContext ac, Dimension size, MainPanel mp, Player1StatePanel p1sp, Unit unit, int CSPnum) {
		this.ac = ac;
		this.size = this.getSize();// size.width=600/3=200 size.height=600/4=150
		this.mp = mp;
		this.p1sp = p1sp;
		this.my_unit = unit;
		this.CSPnum = CSPnum;

		// ボタンの配置
		int h,w;
		Font f = new Font("SansSerif", Font.BOLD, 10);
		FontMetrics fm = getFontMetrics(f);
		w = fm.stringWidth("装備") + 40;
		h = fm.getHeight() + 10;
		weaponBt = new JButton("装備");
		weaponBt.setFont(f);
		weaponBt.addActionListener(this); // アクションリスナ
		weaponBt.setBackground(Color.CYAN);

		try {
			gazou1 = ImageIO.read(new File(PictureFile[my_unit.id]));
		} catch (IOException er) {
			throw new RuntimeException(er);
		}

		setSize(200, 150);

		setBackground(Color.WHITE);
		BevelBorder border = new BevelBorder(BevelBorder.RAISED);
		setBorder(border);

		this.addMouseListener(this);

		p1 = new JPanel(){
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				//全部座標
				g.drawImage(gazou1,0,0,getWidth(),getHeight(),this);
			}
		};
		p2 = new JPanel();
		lb1 = new JLabel(my_unit.name,SwingConstants.CENTER);
		p2.setBackground(Color.WHITE);

		p2.setLayout(new GridLayout(1, 2));
		p2.add(lb1);
		p2.add(weaponBt);

		setLayout(new BorderLayout());
		add(p1,BorderLayout.CENTER);
		add(p2,BorderLayout.SOUTH);

		//キャラクター詳細画面
		detailFrame = new JFrame();
		  //フレームのサイズを指定
	      detailFrame.setSize(500, 500);
		  cdf = new CharacterDetailFrame(my_unit);
		  detailFrame.add(cdf);
		  // リスナ登録
          detailFrame.addWindowListener(this);


          //装備変更画面
          weaponSelectFrame = new JFrame();
            //フレームのサイズを指定
            weaponSelectFrame.setSize(600, 600);
 		   try {
 			p1wsf = new Player1WeaponSelectFrame(this,p1sp);
 		} catch (LineUnavailableException e) {
 			// TODO 自動生成された catch ブロック
 			e.printStackTrace();
 		} catch (UnsupportedAudioFileException e) {
 			// TODO 自動生成された catch ブロック
 			e.printStackTrace();
 		}
 		   weaponSelectFrame.add(p1wsf);
 		   // リスナ登録
 		   weaponSelectFrame.addWindowListener(this);



		// setLayout(null);
		//
		// add(bt);

		// bt.setBounds(0,0,100,50);//setBounds(x座標,y座標,コンポーネントの幅,コンポーネントの高さ);
		// laLv.setBounds(size.width*(1/2),size.height*(1/3),size.width*(1/3),size.height*(1/3));

	}

	public void paint(Graphics g) {
		super.paint(g); // 親クラスの描画
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == weaponBt){ // 装備変更画面を表示
			if (p1sp.getshow_WSFnum() == this.CSPnum) {
				weaponSelectFrameHide();
				p1sp.show_WSFnum = -1;
			} else {
				if (p1sp.getshow_WSFnum() != -1)
					p1sp.weaponSelectFrameHide(p1sp.getshow_WSFnum());
				p1wsf.checkWeapon();
				weaponSelectFrame.show();
				p1sp.setshow_WSFnum(this.CSPnum);
			}
		}
	}


	// MouseListener インタフェース

	@SuppressWarnings("deprecation")
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		detailFrame.setTitle(name);

		// System.out.println("Clicked
		// CSPnum="+CSPnum+"sp.getshow_CDFnum()="+sp.getshow_CDFnum());

		if (p1sp.getshow_CDFnum() == this.CSPnum) {
			detailFrameHide();
			p1sp.show_CDFnum = -1;
		} else {
			if (p1sp.getshow_CDFnum() != -1)
				p1sp.detailFrameHide(p1sp.getshow_CDFnum());
			detailFrame.show();
			p1sp.setshow_CDFnum(this.CSPnum);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		// System.out.println("Pressed");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		// System.out.println("Released");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		// System.out.println("Entered");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		// System.out.println("Exited");
	}

	@SuppressWarnings("deprecation")
	public void detailFrameHide() {
		detailFrame.hide();
	}



	@SuppressWarnings("deprecation")
	public void weaponSelectFrameHide() {
		weaponSelectFrame.hide();
	}

	//WindowListener インタフェース

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if (e.getSource() == detailFrame){
			detailFrameHide();
			p1sp.show_CDFnum = -1;
		}else if(e.getSource() == weaponSelectFrame){
			weaponSelectFrameHide();
			p1sp.show_WSFnum = -1;
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void setWeaponCDF(Weapon weapon){
		cdf.setWeaponCDF(weapon);
	}

}

