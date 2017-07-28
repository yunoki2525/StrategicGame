package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JApplet;


public class ShoppingPicture extends JApplet{

	Image girl;
	Image back;

	ShoppingMain sMain;

	public void init(){
		setSize(300,400);
	}

	public ShoppingPicture(ShoppingMain sMain){
		this.sMain = sMain;

		setPreferredSize(new Dimension(300,400));
		try{
			girl = ImageIO.read(new File("resource/picture/chara000.png"));
			back = ImageIO.read(new File("resource/picture/card.jpeg"));

		}
		catch(IOException er){
			throw new RuntimeException(er);
		}

		repaint();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(back, 0, 0, 300, 400, this);
		g.drawImage(girl, 0, 0, 300, 400, this);
	}
}
