package main;

import java.applet.AppletContext;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;

public class SortieUnitSetPanel extends JApplet implements ActionListener{
	AppletContext ac; // アプレットのコンテキスト
	Dimension size; // アプレットの大きさ
	MainPanel mp;
	JButton[] bt;

	// コンストラクタ
	public SortieUnitSetPanel(AppletContext ac1, Dimension size1, MainPanel mp1) {
		ac = ac1;
		size = size1;
		mp = mp1;
		String[] str ={"メニュー","ストーリー","マップ","ステータス","バトル","ゲームオーバー",
				"バトルスクリーン","出撃キャラ選択","出撃位置設定"};//ボタンの名前の配列
		bt = new JButton[str.length];

		// ボタンの配置
		Font f = new Font("SansSerif", Font.BOLD, 20);
		FontMetrics fm = getFontMetrics(f);

		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());

		for(int i = 0 ; i < str.length ; i++){
			int w = fm.stringWidth(str[i]) + 40;
			int h = fm.getHeight() + 10;
			bt[i] = new JButton(str[i]);
			bt[i].setFont(f);
			bt[i].addActionListener(this); // アクションリスナ
			bt[i].setSize(w, h);

			if(i==8)
				continue;

			pane.add(bt[i]);
		}

		/*int w = size.width / 2 - (w1 + w2 + 5) / 2;
		bt1.setLocation(w, size.height - h1 - 20);
		add(bt1);
		bt2.setLocation(w + w1 + 5, size.height - h1 - 20);
		add(bt2);*/

	}

	public void paint(Graphics g){
		super.paint(g); // 親クラスの描画
		FontMetrics fm;
		Font f;
		String str= "SortieUnitSetPanel";
		int w, h;

		f = new Font("Serif", Font.PLAIN, 20);
		fm = g.getFontMetrics(f);
		w = fm.stringWidth(str);
		h = size.height - fm.getHeight() - 10;
		g.setFont(f);
		g.drawString(str, size.width / 2 - w / 2, h);
	}

	// ボタンがクリックされたときの処理
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt[0]) //GameMenuPanelへ
			mp.state = 1;
		else if(e.getSource() == bt[1])//StoryPanelへ
			mp.state = 2;
		else if(e.getSource() == bt[2])//GameMapPanel
			mp.state = 3;
		else if(e.getSource() == bt[3])//StatePanel
			mp.state = 4;
		else if(e.getSource() == bt[4])//BattlePanel
			mp.state = 5;
		else if(e.getSource() == bt[5])//GameOverPanel
			mp.state = 6;
		else if(e.getSource() == bt[6])//BattleScreenPanel
			mp.state = 7;
		else if(e.getSource() == bt[7])//SortieUnitSelectPanel
			mp.state = 8;
		else if(e.getSource() == bt[8])//SortieUnitSetPanel
			mp.state = 9;
	}
}