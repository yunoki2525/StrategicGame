package main;


import java.applet.AppletContext;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JApplet;

public class GameMain extends JApplet {
	// 初期設定
	public void init() {
		setSize(600,600);
		Dimension size = getSize(); // アプレットの大きさの取得
		System.out.println("size.width="+size.width+" size.height="+size.height);
		Container CP = getContentPane(); // contentPane を取得
		CP.setLayout(null); // レイアウトマネージャを停止
		CP.setBackground(new Color(220, 255, 220)); // 背景色
		AppletContext AC = getAppletContext(); // アプレットのコンテキストを取得

		MainPanel pn = new MainPanel(AC, size); // MainPanel オブジェクトの生成
		CP.add(pn); // MainPanel オブジェクトを contentPane に追加
		pn.setSize(size.width, size.height);
		pn.setLocation(5, 5);
	}

}