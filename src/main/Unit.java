package main;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class Unit {
	Image original_image;
	Image face_image;
	Image icon;
	String name;

	int id;
	int HP;
	int ATK;
	int DEF;
	int SP;
	int INT;
	int MEN;
	int AGI;
	int DEX;
	int LUCK;
	int MOVE;
	int[] PASS;

	static final int PASS_SLASH = 0;
	static final int PASS_SPEAR = 1;
	static final int PASS_BLOW = 2;
	static final int PASS_FIRE = 3;
	static final int PASS_AQUA = 4;
	static final int PASS_SKY = 5;
	static final int PASS_GRAND = 6;
	static final int PASS_RAY = 7;
	static final int PASS_DARK = 8;

	Skill[] skill;
	Magic[] magic;
	Weapon weapon;

	String image_name;
	String icon_name;


	Unit(String image_name, String icon_name,String name, int id, int HP,int ATK, int DEF,int SP, int INT,int MEN, int AGI, int DEX, int LUCK, int MOVE,
			int[] PASS ,Skill[] skill,Magic[] magic,Weapon weapon){
		try {
			original_image = ImageIO.read(new File("../bin/Image/都道府県擬人化/"+image_name+".gif"));
			face_image = ImageIO.read(new File("../bin/Image/都道府県擬人化/"+image_name+"2.gif"));
			icon = ImageIO.read(new File("../bin/Image/charachip/"+icon_name+".png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.name=name;
		this.id=id;
		this.HP=HP;
		this.ATK = ATK;
		this.DEF=DEF;
		this.SP=SP;
		this.INT = INT;
		this.MEN=MEN;
		this.AGI=AGI;
		this.DEX=DEX;
		this.LUCK=LUCK;
		this.MOVE = MOVE;
		this.PASS = Arrays.copyOf(PASS, PASS.length);
		this.skill=skill;
		this.magic = magic;
		this.weapon = weapon;

		this.image_name = image_name;
		this.icon_name = icon_name;
	}

	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append(image_name);buff.append(",");
		buff.append(icon_name);buff.append(",");
		buff.append(name);buff.append(",");
		//System.out.println(buff.toString());
		buff.append(id);buff.append(",");
		buff.append(HP);buff.append(",");
		buff.append(ATK);buff.append(",");
		buff.append(DEF);buff.append(",");
		buff.append(SP);buff.append(",");
		buff.append(INT);buff.append(",");
		buff.append(MEN);buff.append(",");
		buff.append(AGI);buff.append(",");
		buff.append(DEX);buff.append(",");
		buff.append(LUCK);buff.append(",");
		buff.append(MOVE);buff.append("/");
		//System.out.println(buff.toString());
		buff.append(PASS[0]);buff.append(",");
		buff.append(PASS[1]);buff.append(",");
		buff.append(PASS[2]);buff.append(",");
		buff.append(PASS[3]);buff.append(",");
		buff.append(PASS[4]);buff.append(",");
		buff.append(PASS[5]);buff.append(",");
		buff.append(PASS[6]);buff.append(",");
		buff.append(PASS[7]);buff.append(",");
		buff.append(PASS[8]);buff.append(",");
		//System.out.println(buff.toString());

		return buff.toString();
	}
}
