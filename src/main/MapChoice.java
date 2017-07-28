package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JApplet;


public class MapChoice extends JApplet{
	
	int map_max = 8;
	int map_num;
	int[][] initial_stage_position = {{70,500},{140,420},{160,470},{240,440},{300,400},{370,410},{430,250},{500,100}};
	int x;
	int width;
	int height;
	Image backImage = null;
	Image workImage = null;
	BufferedImage tizu;
	MapStage[] stage = new MapStage[map_max];
	MapKeyManegement key;
	public void init(){
		
		map_num = 0;
		tizu = new BufferedImage(100,100,BufferedImage.TYPE_INT_ARGB);
		
		try{
			tizu = ImageIO.read(new File("../src/Image/map.gif"));
		}catch(Exception e){
			e.printStackTrace();
			tizu = null;
			System.out.println("画像読み込み失敗");
		}
		
		for(int i = 0; i < map_max; i++){
			stage[i] = new MapStage(initial_stage_position[i]);
		}
		setSize(600,600);
		Container pane = getContentPane();
		pane.setBackground(Color.white);
		
		
		pane.setLayout(null);
		
	}
	
	public void paint(Graphics g){
		//super.paint(g);
		/*g.drawImage(tizu, 0,0,600,600,this);
		g.setColor(Color.BLACK);
		for(int i = 0; i < map_max; i++){
		if(i == map_num)g.setColor(Color.RED);
		g.drawOval(stage[i].position[0] ,stage[i].position[1] , 20,20);
		
		if(i == map_num)g.setColor(Color.BLACK);
		*/
		drawFigure(g);
		}
	
	public void drawFigure(Graphics g){
		if(backImage == null | width != getWidth() || height != getHeight()){
			width = getWidth();
			height = getHeight();
			createBackImage();
		}
		createWorkImage();
		g.drawImage(workImage,0,0,this);
	}
	public void createBackImage(){
		backImage = createImage(width,height);
		Graphics g = backImage.getGraphics();
		
		g.drawImage(tizu, 0,0,width,height,this);
	}
	public void createWorkImage(){
		workImage = createImage(width,height);
		Graphics g = workImage.getGraphics();
		
		g.drawImage(backImage, 0,0,this);
		
		g.setColor(Color.BLACK);
		for(int i = 0; i < map_max; i++){
		if(i == map_num)g.setColor(Color.cyan);
		g.fillOval(stage[i].position[0] ,stage[i].position[1] , 20,20);
		
		if(i == map_num)g.setColor(Color.BLACK);
		}
	}
	
	
	public void updateMap_Num(int map_num){
		this.map_num = map_num;
		repaint();
	}
	
}
