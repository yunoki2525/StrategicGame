package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

public class Player2ShoppingMyMoney extends JPanel{

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	int itemLength = Item.kind+Weapon.kind;//アイテムの数

	String[] itemName = new String[itemLength];//アイテム名

	String[] comment = {"どれにしますか？","ありがとうございます","お金が足りません！"};//キャラクターのコメント
	int[] money = new int[itemLength];

	int myMoney;
	int[] havingItem =new int[itemLength];//アイテム所持数
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	int[] buyItem = new int[itemLength];//アイテムの購入数
	String[] itemExplain = new String[itemLength];

	int payMoney = 0;

	Image back;
	Image back2;
	Clip kane;
	Clip dame;

	Player2ArmamentsPanel sMain;
	public Player2ShoppingMyMoney(Player2ArmamentsPanel sMain) throws LineUnavailableException, UnsupportedAudioFileException{
		this.sMain = sMain;

		setSize(600,100);

		try{
			back = ImageIO.read(new File("picture/shoppingMyMoney1.jpg"));
			back2 = ImageIO.read(new File("picture/shoppingMyMoney2.jpg"));
			kane = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			kane.open(AudioSystem.getAudioInputStream(new File("music/kane.wav")));
			dame = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			dame.open(AudioSystem.getAudioInputStream(new File("music/dame.wav")));
			BufferedReader br = new BufferedReader(new FileReader("../src/Money/Player2Money"));
			myMoney = Integer.parseInt(br.readLine());
			br.close();
		}
		catch(IOException er){
			throw new RuntimeException(er);
		}
		setPreferredSize(new Dimension(600,100));
		repaint();
	}

	public void cal(int payMoney1){
		this.myMoney -= payMoney1;
		repaint();
	}
	public void kane(){
		kane.setMicrosecondPosition(0);
		kane.start();
	}
	public void dame(){
		dame.setMicrosecondPosition(0);
		dame.start();
	}

	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(back, 0, 0, 300,100 , this);
		g.drawImage(back2, 300, 0, 300,100 , this);
		g.setFont(new Font("Serif", Font.PLAIN, 20));
		g.setColor(Color.BLACK);
		g.drawString("↓アイテム一覧↓", 50,50 );
		g.drawString("資金"+this.myMoney+"円", 350,50 );

	}
	public void passItem(Item[] items){
		for(int i = 0; i < items.length; i++){
			itemName[i] = items[i].name;
			money[i] = items[i].money;
			havingItem[i]  = items[i].num;
			itemExplain[i] = items[i].toString();
		}
	}
	public void passWeapon(WeaponList[] weapon_list, int[] having_weapon) {
		for(int i = Item.kind; i < itemLength; i++){
			//System.out.print(i+",");
			int idx = i - Item.kind;
			//System.out.print(idx+",");
			itemName[i] = weapon_list[idx].weapon.name;
			money[i] = weapon_list[idx].weapon.money;
			havingItem[i] = having_weapon[idx];
			itemExplain[i] = weapon_list[idx].toString();
		}

	}
}
