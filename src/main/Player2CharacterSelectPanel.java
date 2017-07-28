package main;

import java.applet.AppletContext;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JScrollPane;


public class Player2CharacterSelectPanel extends JApplet{
	AppletContext ac; // アプレットのコンテキスト
	Dimension size; // アプレットの大きさ
	MainPanel mp;

	P2CharaSele p2cs;
	JScrollPane scrollPane;


	// コンストラクタ
	public Player2CharacterSelectPanel(AppletContext ac1, Dimension size1, MainPanel mp1, String field_name)
	{
		Container pane = getContentPane();

		ac   = ac1;
		size = size1;
		mp   = mp1;

		setSize(size);

		pane.setSize(size);
		// レイアウトマネージャの停止
		//pane.setLayout(null);
		// 背景色の設定
		pane.setBackground(Color.white);
		// ボタンの配置
		p2cs = new P2CharaSele( mp,field_name);
		//p1cs.repaint();

		scrollPane = new JScrollPane(p2cs);
		pane.add(scrollPane);

	}
}
