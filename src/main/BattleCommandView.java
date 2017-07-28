package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JApplet;

public class BattleCommandView extends JApplet  {
	BattlePanel panel;
	Piece[] friend;
	Piece[] enemy;

	Piece ptr;
	int bar_length = 150;

	int select_num = 0;

	boolean focus = true;
	static final int waiting = 0;
	static final int showing = 1;
	static final int motion_select = 2;
	static final int attack_select = 3;
	static final int item_select = 4;
	static final int skill_select = 5;
	static final int magic_select = 6;
	static final int target_select = 7;
	static final int aim_select = 8;
	static final int using_select = 9;
	static final int move_select = 10;
	static final int enemy_phase = 11;

	static final int default_selections = 6;

	int selection_pattern = waiting;

	int selections = default_selections;

	boolean PvP = false;
	boolean myTurn = true;

	boolean[] peek_enemy;
	boolean[] peek_friend;

	int cursor_posi1 = 0;
	int cursor_posi2 = 0;

	public BattleCommandView(BattlePanel panel, Piece[] ene, Piece[] fri) {
		this.panel = panel;
		this.enemy = ene;
		this.friend = fri;
		peek_enemy = new boolean[enemy.length];
		peek_friend = new boolean[friend.length];

		Container pane = getContentPane();
		pane.setBackground(Color.BLACK);
		// pane.setLayout(null);

	}

	public void paint(Graphics g) {
		super.paint(g);
		//System.out.println("at command/ paint");

		//	System.out.println("at paint :selection_pattern = "+selection_pattern);
		if(selection_pattern == showing || selection_pattern == motion_select ||
				selection_pattern == skill_select || selection_pattern == item_select ||
				selection_pattern == magic_select ){
			drawShowing(g);
		}
		g.setColor(Color.WHITE);
		//		g.setFont(new Font("Serif", Font.PLAIN, 18));
		if (selection_pattern == motion_select ){
			cursor_posi1 = select_num;
			drawMotionSelect(g);
		}
		if(selection_pattern == attack_select){
			cursor_posi1 = select_num;
			//			drawShowing(g);
			//			drawMotionSelect(g);
			drawAttackSelect(g);
		}else if(selection_pattern == item_select ){
			cursor_posi2 = select_num;
			drawMotionSelect(g);
			drawItemSelect(g);
		} else if (selection_pattern == skill_select) {
			cursor_posi2 = select_num;
			drawMotionSelect(g);
			drawSkillSelect(g);
		}  else if (selection_pattern == magic_select) {
			cursor_posi2 = select_num;
			drawMotionSelect(g);
			drawMagicSelect(g);
		}
		else if (selection_pattern == target_select) {
			drawTargetSelect(g);
		} else if (selection_pattern == aim_select) {
			drawAimSelect(g);
		} else if (selection_pattern == using_select) {
			drawUsingSelect(g);

		}
		//
		else if (selection_pattern == move_select) {
			drawShowing(g);
			drawMotionSelect(g);
		}

		//		else if (selection_pattern == enemy_phase) {
		//
		//			g.drawString("enemy phase ...", 20, 200);
		//		}
	}
	private void drawAttackSelect(Graphics g) {
		g.setFont(new Font("Serif", Font.PLAIN, 17));
		g.setColor(Color.WHITE);
		g.drawString(ptr.unit.skill[0].name, 20, 20);

		g.setFont(new Font("Serif", Font.PLAIN, 15));
		for(int i=0 ; i<3 ; i++){
			switch(ptr.unit.skill[0].type){
			case Skill.normal :
				g.drawString("属性 : 無", 20, 40);
				break;
			case Skill.slash :
				g.drawString("属性 : 斬", 20, 40);
				break;
			case Skill.spear :
				g.drawString("属性 : 突", 20, 40);
				break;
			case Skill.blow :
				g.drawString("属性 : 打", 20, 40);
				break;
			}
		}
		g.drawString("威力 :"+ ptr.unit.skill[0].power, 80, 40);
		g.drawString("消費SP :" + ptr.unit.skill[0].cost, 80, 55);

		String[] skill_detail = makeSkillDetail(ptr.unit.skill[0].toString());

		for(int i= 0 ; i<skill_detail.length ; i++)
			g.drawString(skill_detail[i],20,85 + i*15);

		peekStatus(g);
	}

	private void drawUsingSelect(Graphics g) {
		g.setFont(new Font("Serif", Font.PLAIN, 17));
		g.setColor(Color.WHITE);
		String[] item_detail;
		if(myTurn){
			g.drawString(panel.item.items[0][panel.havingItemList(myTurn)[select_num]].name, 20, 20);

			g.setFont(new Font("Serif", Font.PLAIN, 15));

			g.drawString("所持数 :"+panel.item.items[0][panel.havingItemList(myTurn)[select_num]].num, 80, 40);

			item_detail = makeSkillDetail(panel.item.items[0][panel.havingItemList(myTurn)[select_num]].toString());

		}else{
			g.drawString(panel.item.items[1][panel.havingItemList(myTurn)[select_num]].name, 20, 20);

			g.setFont(new Font("Serif", Font.PLAIN, 15));

			g.drawString("所持数 :"+panel.item.items[1][panel.havingItemList(myTurn)[select_num]].num, 80, 40);

			item_detail = makeSkillDetail(panel.item.items[1][panel.havingItemList(myTurn)[select_num]].toString());

		}

		for(int i= 0 ; i<item_detail.length ; i++)
			g.drawString(item_detail[i],20,85 + i*15);
		peekStatus(g);
	}

	private void drawAimSelect(Graphics g) {
		// 			g.setFont(new Font("Serif", Font.PLAIN, 17));
		g.setColor(Color.WHITE);
		g.drawString(ptr.unit.magic[select_num ].name, 20, 20);

		g.setFont(new Font("Serif", Font.PLAIN, 15));
		for(int i=0 ; i<6 ; i++){
			switch(ptr.unit.magic[select_num ].type){
			case Skill.normal :
				g.drawString("属性 : 無", 20, 40);
				break;
			case Magic.fire :
				g.drawString("属性 : 火", 20, 40);
				break;
			case Magic.aqua :
				g.drawString("属性 : 水", 20, 40);
				break;
			case Magic.sky :
				g.drawString("属性 : 天", 20, 40);
				break;
			case Magic.grand :
				g.drawString("属性 : 地", 20, 40);
				break;
			case Magic.ray :
				g.drawString("属性 : 光", 20, 40);
				break;
			case Magic.dark :
				g.drawString("属性 : 闇", 20, 40);
				break;
			}
		}
		g.drawString("威力 :"+ ptr.unit.magic[select_num ].power, 80, 40);
		g.drawString("消費SP :" + ptr.unit.magic[select_num ].cost, 80, 55);

		String[] magic_detail = makeSkillDetail(ptr.unit.magic[select_num ].toString());

		for(int i= 0 ; i<magic_detail.length ; i++)
			g.drawString(magic_detail[i],20,85 + i*15);

		peekStatus(g);

	}

	private void drawTargetSelect(Graphics g) {
		g.setFont(new Font("Serif", Font.PLAIN, 17));
		g.setColor(Color.WHITE);
		g.drawString(ptr.unit.skill[select_num + 1].name, 20, 20);

		g.setFont(new Font("Serif", Font.PLAIN, 15));
		for(int i=0 ; i<3 ; i++){
			switch(ptr.unit.skill[select_num + 1].type){
			case Skill.normal :
				g.drawString("属性 : 無", 20, 40);
				break;
			case Skill.slash :
				g.drawString("属性 : 斬", 20, 40);
				break;
			case Skill.spear :
				g.drawString("属性 : 突", 20, 40);
				break;
			case Skill.blow :
				g.drawString("属性 : 打", 20, 40);
				break;
			}
		}
		g.drawString("威力 :"+ ptr.unit.skill[select_num + 1].power, 80, 40);
		g.drawString("消費SP :" + ptr.unit.skill[select_num + 1].cost, 80, 55);

		String[] skill_detail = makeSkillDetail(ptr.unit.skill[select_num + 1].toString());

		for(int i= 0 ; i<skill_detail.length ; i++)
			g.drawString(skill_detail[i],20,85 + i*15);

		peekStatus(g);
	}

	private void drawMagicSelect(Graphics g) {
		g.setColor(Color.WHITE);
		for (int i = 0; i < ptr.unit.magic.length; i++) {
			if(cursor_posi2 == i)g.setColor(Color.RED);
			g.drawString(ptr.unit.magic[i].name, 320, 45 + 20 * i);
			if(cursor_posi2 == i)g.setColor(Color.WHITE);
		}
		//System.out.println(cursor_posi2);
		if(cursor_posi2 == ptr.unit.magic.length)g.setColor(Color.RED);
		g.drawString("戻る", 320, 45 + 20 * ptr.unit.magic.length);
		g.setColor(Color.RED);
		g.fillOval(310, 45-8+cursor_posi2*20, 8, 8);
		g.setColor(Color.WHITE);
	}

	private void drawSkillSelect(Graphics g) {
		g.setColor(Color.WHITE);
		for (int i = 1; i < ptr.unit.skill.length; i++) {
			if(cursor_posi2 == i-1)g.setColor(Color.RED);
			g.drawString(ptr.unit.skill[i].name, 320, 45 + 20 * (i-1));
			if(cursor_posi2 == i-1)g.setColor(Color.WHITE);
		}
		if(cursor_posi2 == ptr.unit.skill.length-1)g.setColor(Color.RED);
		g.drawString("戻る", 320, 45 + 20 * (ptr.unit.skill.length-1));
		g.setColor(Color.RED);
		g.fillOval(310, 45-8+cursor_posi2*20, 8, 8);
		g.setColor(Color.WHITE);
	}

	private void drawItemSelect(Graphics g) {
		g.setColor(Color.WHITE);
		int[] item_list = new int[panel.havingItemList(myTurn).length] ;

		item_list = Arrays.copyOf(panel.havingItemList(myTurn),panel.havingItemList(myTurn).length);
		for(int i = 0; i < item_list.length;i++){
			if(cursor_posi2 == i)  g.setColor(Color.RED);
			g.drawString(panel.getItemName(item_list[i], myTurn), 320, 45 + 20 * i);
			g.setColor(Color.WHITE);
		}

		if(cursor_posi2 == item_list.length)g.setColor(Color.RED);
		g.drawString("戻る", 320, 45 + 20 * panel.havingItemList(myTurn).length);
		g.setColor(Color.RED);
		g.fillOval(310, 45-8+cursor_posi2*20, 8, 8);

	}

	private void drawMotionSelect(Graphics g) {
		//
		g.setFont(new Font("Serif", Font.PLAIN, 13));
		if(ptr.acted)g.setColor(Color.GRAY);
		else if(cursor_posi1 == 0)g.setColor(Color.RED);
		g.drawString("攻撃", 230, 45);
		g.setColor(Color.WHITE);
		if(ptr.acted)g.setColor(Color.GRAY);
		else if(cursor_posi1 == 1)g.setColor(Color.RED);
		g.drawString("アイテム", 230, 65);
		g.setColor(Color.WHITE);
		if(ptr.acted)g.setColor(Color.GRAY);
		else if(cursor_posi1 == 2)g.setColor(Color.RED);
		g.drawString("スキル", 230, 85);
		g.setColor(Color.WHITE);
		if(ptr.acted)g.setColor(Color.GRAY);
		else if(cursor_posi1 == 3)g.setColor(Color.RED);
		g.drawString("魔法", 230, 105);
		g.setColor(Color.WHITE);
		if(ptr.moved)g.setColor(Color.GRAY);
		else if(cursor_posi1 == 4)g.setColor(Color.RED);
		g.drawString("移動", 230, 125);
		g.setColor(Color.WHITE);
		if(ptr.waited)g.setColor(Color.GRAY);
		else if(cursor_posi1 == 5)g.setColor(Color.RED);
		g.drawString("待機", 230, 145);
		g.setColor(Color.WHITE);
		if(cursor_posi1 == 6)g.setColor(Color.RED);
		g.drawString("戻る", 230, 165);

		g.setColor(new Color(255,0,0));
		g.fillOval(220, 45-8+cursor_posi1*20, 8, 8);
		g.setColor(Color.WHITE);

	}

	public void drawShowing(Graphics g){
		// 顔画像
		g.drawImage(ptr.unit.face_image,10,10,100,100,this);
		if(!ptr.alive){
			g.setColor(new Color(100,100,100,200));
			g.fillRect(10, 10, 100, 100);
			g.setColor(Color.WHITE);
		}

		g.setColor(Color.WHITE);
		g.setFont(new Font("Serif", Font.PLAIN, 15));
		g.drawString(ptr.unit.name,20, 123);
		if(ptr.alive){
			//HP
			g.drawString("HP : "+ptr.HP+"/"+(ptr.unit.HP+ptr.unit.weapon.HP),20, 140);
			//SP
			g.setColor(Color.WHITE);
			g.drawString("SP : "+ptr.SP+"/"+ptr.unit.SP,20, 170);

			//HPbar
			g.setColor(Color.CYAN);
			g.drawRect(20 -1, 145 -1, bar_length +2, 10 +2);
			g.setColor(Color.GRAY);
			g.fillRect(20, 145, bar_length, 10);
			g.setColor(Color.GREEN);
			g.fillRect(20, 145, bar_length*ptr.HP/(ptr.unit.HP+ptr.unit.weapon.HP) , 10);
			//SPbar
			g.setColor(Color.CYAN);
			g.drawRect(20 -1, 175 -1, bar_length +2, 10 +2);
			g.setColor(Color.GRAY);
			g.fillRect(20, 175, bar_length, 10);
			g.setColor(Color.GREEN);
			g.fillRect(20, 175, bar_length*ptr.SP/ptr.unit.SP , 10);
		}else{
			g.setColor(Color.RED);
			g.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 20));
			g.drawString("DEAD",20, 155);
		}
		//状態異常
		for (int j = 0; j < ptr.condition.length; j++) {
			if (ptr.condition[j] > 0){
				switch(j){
				case 0:
					g.setFont(new Font("Serif", Font.PLAIN, 13));
					g.setColor(new Color(255, 0 , 255));
					g.drawString("毒",115,20);
					g.setFont(new Font("Serif", Font.PLAIN, 9));
					g.drawString("("+ptr.condition[j]+")",130,20);
					break;
				case 1:
					g.setFont(new Font("Serif", Font.PLAIN, 13));
					g.setColor(Color.YELLOW);
					g.drawString("麻痺",115,34);
					g.setFont(new Font("Serif", Font.PLAIN, 9));
					g.drawString("("+ptr.condition[j]+")",142,34);
					break;
				case 2:
					g.setFont(new Font("Serif", Font.PLAIN, 13));
					g.setColor(new Color(80,80,255));
					g.drawString("凍結",115,48);
					g.setFont(new Font("Serif", Font.PLAIN, 9));
					g.drawString("("+ptr.condition[j]+")",142,48);
					break;
				case 3:
					g.setFont(new Font("Serif", Font.PLAIN, 13));
					g.setColor(Color.ORANGE);
					g.drawString("気絶",115,62);
					g.setFont(new Font("Serif", Font.PLAIN, 9));
					g.drawString("("+ptr.condition[j]+")",142,62);
					break;
				case 4:
					g.setFont(new Font("Serif", Font.PLAIN, 13));
					g.setColor(Color.WHITE);
					g.drawString("睡眠",115,76);
					g.setFont(new Font("Serif", Font.PLAIN, 9));
					g.drawString("("+ptr.condition[j]+")",142,76);
					break;
				case 5:
					g.setFont(new Font("Serif", Font.PLAIN, 13));
					g.setColor(Color.RED);
					g.drawString("燃焼",115,90);
					g.setFont(new Font("Serif", Font.PLAIN, 9));
					g.drawString("("+ptr.condition[j]+")",142,90);
					break;
				case 6:
					g.setFont(new Font("Serif", Font.PLAIN, 13));
					g.setColor(Color.GRAY);
					g.drawString("暗闇",115,104);
					g.setFont(new Font("Serif", Font.PLAIN, 9));
					g.drawString("("+ptr.condition[j]+")",142,104);
					break;

				}
			}
		}
		//能力変化
		for (int j = 0; j < ptr.status_shift.length; j++) {
			if (ptr.status_shift[j] > 0){
				switch(j){
				case 0:
					g.setFont(new Font("Serif", Font.PLAIN, 11));
					if(ptr.ATK > ptr.unit.ATK +ptr.unit.weapon.ATK)g.setColor(new Color( 255, 0 , 0));
					else                      g.setColor(new Color( 0, 0 , 255));
					g.drawString("ATK",160,20);
					g.setFont(new Font("Serif", Font.PLAIN, 9));
					g.drawString("("+ptr.status_shift[j]+")",187,20);
					break;
				case 1:
					g.setFont(new Font("Serif", Font.PLAIN, 11));
					if(ptr.DEF > ptr.unit.DEF +ptr.unit.weapon.DEF)g.setColor(new Color( 255, 0 , 0));
					else                      g.setColor(new Color( 0, 0 , 255));
					g.drawString("DEF",160,32);
					g.setFont(new Font("Serif", Font.PLAIN, 9));
					g.drawString("("+ptr.status_shift[j]+")",187,32);
					break;
				case 2:
					g.setFont(new Font("Serif", Font.PLAIN, 11));
					if(ptr.INT > ptr.unit.INT +ptr.unit.weapon.INT)g.setColor(new Color( 255, 0 , 0));
					else                      g.setColor(new Color( 0, 0 , 255));
					g.drawString("INT",160,44);
					g.setFont(new Font("Serif", Font.PLAIN, 9));
					g.drawString("("+ptr.status_shift[j]+")",187,44);
					break;
				case 3:
					g.setFont(new Font("Serif", Font.PLAIN, 11));
					if(ptr.MEN > ptr.unit.MEN +ptr.unit.weapon.MEN)g.setColor(new Color( 255, 0 , 0));
					else                      g.setColor(new Color( 0, 0 , 255));
					g.drawString("MEN",160,56);
					g.setFont(new Font("Serif", Font.PLAIN, 9));
					g.drawString("("+ptr.status_shift[j]+")",187,56);
					break;
				case 4:
					g.setFont(new Font("Serif", Font.PLAIN, 11));
					if(ptr.AGI > ptr.unit.AGI +ptr.unit.weapon.AGI)g.setColor(new Color( 255, 0 , 0));
					else                      g.setColor(new Color( 0, 0 , 255));
					g.drawString("AGI",160,68);
					g.setFont(new Font("Serif", Font.PLAIN, 9));
					g.drawString("("+ptr.status_shift[j]+")",187,68);
					break;

				case 5:
					g.setFont(new Font("Serif", Font.PLAIN, 11));
					if(ptr.DEX > ptr.unit.DEX +ptr.unit.weapon.DEX)g.setColor(new Color( 255, 0 , 0));
					else                      g.setColor(new Color( 0, 0 , 255));
					g.drawString("DEX",160,80);
					g.setFont(new Font("Serif", Font.PLAIN, 9));
					g.drawString("("+ptr.status_shift[j]+")",187,80);
					break;
				case 6:
					g.setFont(new Font("Serif", Font.PLAIN, 11));
					if(ptr.LUCK > ptr.unit.LUCK +ptr.unit.weapon.LUCK)g.setColor(new Color( 255, 0 , 0));
					else                      g.setColor(new Color( 0, 0 , 255));
					g.drawString("LUCK",160,92);
					g.setFont(new Font("Serif", Font.PLAIN, 9));
					g.drawString("("+ptr.status_shift[j]+")",192,92);
					break;
				case 7:
					g.setFont(new Font("Serif", Font.PLAIN, 11));
					if(ptr.MOVE > ptr.unit.MOVE +ptr.unit.weapon.MOVE)g.setColor(new Color( 255, 0 , 0));
					else                      g.setColor(new Color( 0, 0 , 255));
					g.drawString("MOVE",160,104);
					g.setFont(new Font("Serif", Font.PLAIN, 9));
					g.drawString("("+ptr.status_shift[j]+")",192,104);
					break;
				}
			}
		}
		g.setFont(new Font("Serif", Font.PLAIN, 14));
		g.setColor(new Color(0, 255 , 255));
		g.drawString("@ "+ptr.unit.weapon.name,70,123);
		g.setColor(Color.WHITE);
	}

	public void peekStatus(Graphics g){
		int shift = 0;
		for(int i=0 ; i<peek_enemy.length ; i++){
			if(enemy[i].alive){
				g.setFont(new Font("Serif", Font.PLAIN, 12));
				if(peek_enemy[i]){
					// 顔画像
					g.drawImage(enemy[i].unit.original_image, 240+shift,2,90,150,this);
					//名前
					g.setColor(Color.WHITE);
					g.drawString(enemy[i].unit.name,240+shift, 165);
					//HP
					g.drawString("HP : "+enemy[i].HP+"/"+(enemy[i].unit.HP+enemy[i].unit.weapon.HP),240+shift, 177);
					g.setColor(Color.CYAN);
					g.drawRect(240 -1+shift, 183 -1, (bar_length +2 )/2, 5 +2);
					g.setColor(Color.GRAY);
					g.fillRect(240   +shift, 183   , bar_length      /2, 5);
					g.setColor(Color.GREEN);
					g.fillRect(240   +shift, 183   , bar_length/2*enemy[i].HP/(enemy[i].unit.HP+enemy[i].unit.weapon.HP) , 5);
					//状態異常
					for (int j = 0; j < enemy[i].condition.length; j++) {
						if (enemy[i].condition[j] > 0){
							g.setFont(new Font("Serif", Font.PLAIN, 11));
							switch(j){
							case 0:
								g.setColor(new Color(255, 0 , 255));
								g.drawString("毒",333+shift,12);
								break;
							case 1:
								g.setColor(Color.YELLOW);
								g.drawString("麻痺",333+shift,22);
								break;
							case 2:
								g.setColor(Color.BLUE);
								g.drawString("凍結",333+shift,32);
								break;
							case 3:
								g.setColor(Color.ORANGE);
								g.drawString("気絶",333+shift,42);
								break;
							case 4:
								g.setColor(Color.WHITE);
								g.drawString("睡眠",333+shift,52);
								break;
							case 5:
								g.setColor(Color.RED);
								g.drawString("燃焼",333+shift,62);
								break;
							case 6:
								g.setColor(Color.GRAY);
								g.drawString("暗闇",333+shift,72);
								break;

							}
						}
					}
					//能力変化
					for (int j = 0; j < enemy[i].status_shift.length; j++) {
						if (enemy[i].status_shift[j] > 0){
							switch(j){
							case 0:
								if(enemy[i].ATK > enemy[i].unit.ATK +enemy[i].unit.weapon.ATK)g.setColor(new Color( 255, 0 , 0));
								else                      g.setColor(new Color( 0, 0 , 255));
								g.drawString("ATK",333+shift,82);
								break;
							case 1:
								if(enemy[i].DEF > enemy[i].unit.DEF +enemy[i].unit.weapon.DEF)g.setColor(new Color( 255, 0 , 0));
								else                      g.setColor(new Color( 0, 0 , 255));
								g.drawString("DEF",333+shift,92);
								break;
							case 2:
								if(enemy[i].INT > enemy[i].unit.INT +enemy[i].unit.weapon.INT)g.setColor(new Color( 255, 0 , 0));
								else                      g.setColor(new Color( 0, 0 , 255));
								g.drawString("INT",333+shift,102);
								break;
							case 3:
								if(enemy[i].MEN > enemy[i].unit.MEN +enemy[i].unit.weapon.MEN)g.setColor(new Color( 255, 0 , 0));
								else                      g.setColor(new Color( 0, 0 , 255));
								g.drawString("MEN",333+shift,112);
								break;
							case 4:
								if(enemy[i].AGI > enemy[i].unit.AGI +enemy[i].unit.weapon.AGI)g.setColor(new Color( 255, 0 , 0));
								else                      g.setColor(new Color( 0, 0 , 255));
								g.drawString("AGI",333+shift,122);
								break;
							case 5:
								if(enemy[i].DEX > enemy[i].unit.DEX +enemy[i].unit.weapon.DEX)g.setColor(new Color( 255, 0 , 0));
								else                      g.setColor(new Color( 0, 0 , 255));
								g.drawString("DEX",333+shift,132);
								break;
							case 6:
								if(enemy[i].LUCK > enemy[i].unit.LUCK +enemy[i].unit.weapon.LUCK)g.setColor(new Color( 255, 0 , 0));
								else                      g.setColor(new Color( 0, 0 , 255));
								g.drawString("LUCK",333+shift,142);
								break;
							case 7:
								if(enemy[i].MOVE > enemy[i].unit.MOVE +enemy[i].unit.weapon.MOVE)g.setColor(new Color( 255, 0 , 0));
								else                      g.setColor(new Color( 0, 0 , 255));
								g.drawString("MOVE",333+shift,152);
								break;
							}
						}
					}
					shift += 120;
				}
			}
		}
		for(int i=0 ; i<peek_friend.length ; i++){
			if(friend[i].alive){
				g.setFont(new Font("Serif", Font.PLAIN, 12));
				if(peek_friend[i]){
					// 顔画像
					g.drawImage(friend[i].unit.original_image, 240+shift,2,90,150,this);
					//名前
					g.setColor(Color.WHITE);
					g.drawString(friend[i].unit.name,240+shift, 165);
					//HP
					g.drawString("HP : "+friend[i].HP+"/"+(friend[i].unit.HP+friend[i].unit.weapon.HP),240+shift, 177);
					g.setColor(Color.CYAN);
					g.drawRect(240 -1+shift, 183 -1, (bar_length +2 )/2, 5 +2);
					g.setColor(Color.GRAY);
					g.fillRect(240   +shift, 183   , bar_length      /2, 5);
					g.setColor(Color.GREEN);
					g.fillRect(240   +shift, 183   , bar_length/2*friend[i].HP/(friend[i].unit.HP+friend[i].unit.weapon.HP) , 5);
					//状態異常
					for (int j = 0; j < friend[i].condition.length; j++) {
						if (friend[i].condition[j] > 0){
							g.setFont(new Font("Serif", Font.PLAIN, 11));
							switch(j){
							case 0:
								g.setColor(new Color(255, 0 , 255));
								g.drawString("毒",333+shift,12);
								break;
							case 1:
								g.setColor(Color.YELLOW);
								g.drawString("麻痺",333+shift,22);
								break;
							case 2:
								g.setColor(Color.BLUE);
								g.drawString("凍結",333+shift,32);
								break;
							case 3:
								g.setColor(Color.ORANGE);
								g.drawString("気絶",333+shift,42);
								break;
							case 4:
								g.setColor(Color.WHITE);
								g.drawString("睡眠",333+shift,52);
								break;
							case 5:
								g.setColor(Color.RED);
								g.drawString("燃焼",333+shift,62);
								break;
							case 6:
								g.setColor(Color.GRAY);
								g.drawString("暗闇",333+shift,72);
								break;

							}
						}
					}
					//能力変化
					for (int j = 0; j < friend[i].status_shift.length; j++) {
						if (friend[i].status_shift[j] > 0){
							switch(j){
							case 0:
								if(friend[i].ATK > friend[i].unit.ATK +friend[i].unit.weapon.ATK)g.setColor(new Color( 255, 0 , 0));
								else                      g.setColor(new Color( 0, 0 , 255));
								g.drawString("ATK",333+shift,82);
								break;
							case 1:
								if(friend[i].DEF > friend[i].unit.DEF +friend[i].unit.weapon.DEF)g.setColor(new Color( 255, 0 , 0));
								else                      g.setColor(new Color( 0, 0 , 255));
								g.drawString("DEF",333+shift,92);
								break;
							case 2:
								if(friend[i].INT > friend[i].unit.INT +friend[i].unit.weapon.INT)g.setColor(new Color( 255, 0 , 0));
								else                      g.setColor(new Color( 0, 0 , 255));
								g.drawString("INT",333+shift,102);
								break;
							case 3:
								if(friend[i].MEN > friend[i].unit.MEN +friend[i].unit.weapon.MEN)g.setColor(new Color( 255, 0 , 0));
								else                      g.setColor(new Color( 0, 0 , 255));
								g.drawString("MEN",333+shift,112);
								break;
							case 4:
								if(friend[i].AGI > friend[i].unit.AGI +friend[i].unit.weapon.AGI)g.setColor(new Color( 255, 0 , 0));
								else                      g.setColor(new Color( 0, 0 , 255));
								g.drawString("AGI",333+shift,122);
								break;
							case 5:
								if(friend[i].DEX > friend[i].unit.DEX +friend[i].unit.weapon.DEX)g.setColor(new Color( 255, 0 , 0));
								else                      g.setColor(new Color( 0, 0 , 255));
								g.drawString("DEX",333+shift,132);
								break;
							case 6:
								if(friend[i].LUCK > friend[i].unit.LUCK +friend[i].unit.weapon.LUCK)g.setColor(new Color( 255, 0 , 0));
								else                      g.setColor(new Color( 0, 0 , 255));
								g.drawString("LUCK",333+shift,142);
								break;
							case 7:
								if(friend[i].MOVE > friend[i].unit.MOVE +friend[i].unit.weapon.MOVE)g.setColor(new Color( 255, 0 , 0));
								else                      g.setColor(new Color( 0, 0 , 255));
								g.drawString("MOVE",333+shift,152);
								break;
							}
						}
					}
					shift += 120;
				}
			}
		}
	}



	public void showState(Piece piece) {
		this.ptr = piece;
		if(selection_pattern == waiting)
			selection_pattern = showing;
		//System.out.println("at showState : selection_pattern = "+selection_pattern);
		repaint();
	}


	public void updateCommand(int select_num) {
		//System.out.println("updateCommand");
		this.select_num = select_num;
		repaint();

	}

	public void changeSelect() {
		//System.out.println("changeSelect / selection_pattern :"+selection_pattern+" ->");

		//System.out.println("before/ selectioin_pattern : "+ selection_pattern);
		if(selection_pattern == waiting){
			selection_pattern = showing;
		}
		else if(selection_pattern == showing){
			selection_pattern = motion_select;
			//	System.out.println("selection_pattern = "+selection_pattern);
			repaint();
		}
		else if (selection_pattern == motion_select) {
			if (select_num == 0) {
				if(!ptr.acted){
					selection_pattern = attack_select;
					panel.targetSelect(ptr, 0);
					panel.setState(1);
				}
			}else if (select_num == 1) {
				if(!ptr.acted){
					selection_pattern = item_select;
					panel.reselect();
					selections = panel.havingItemList(myTurn).length;
					select_num = 0;
					repaint();
				}
			}else if (select_num == 2) {
				if(!ptr.acted){
					selection_pattern = skill_select;
					panel.reselect();
					selections = ptr.unit.skill.length - 1;
					select_num = 0;
					repaint();
				}
			} else if (select_num == 3) {
				if(!ptr.acted){
					selection_pattern = magic_select;
					panel.reselect();
					selections = ptr.unit.magic.length ;
					select_num = 0;
					repaint();
				}
			}else if (select_num == 4) {
				if(!ptr.moved){
					//System.out.println(ptr.position[0]+", "+ptr.position[1]);
					//panel.movable(ptr.position[0] , ptr.position[1] ,ptr.move);
					//for(int i = 0;  i < panel.system.move_map.length;i++)System.out.println(Arrays.toString(panel.system.move_map[i]));
					selection_pattern = move_select;
					panel.reselect();
					panel.moveSelect(ptr);
					panel.setState(1);
					select_num = 0;
					repaint();
				}
			}else if (select_num == 5) {
				//待機
				ptr.moved = true;
				ptr.acted = true;
				ptr.waited = true;
				selection_pattern = showing;
				panel.reselect();
				panel.setState(1);
				select_num = 0;
			}else if (select_num == 6) {
				backSelect();
				repaint();
			}
		}
		else if (selection_pattern == skill_select) {
			if(select_num == selections){
				backSelect();
				//System.out.println("backSelect with 戻る");
			}
			else{
				if( ptr.unit.skill[select_num+1].cost <= ptr.SP){
					selection_pattern = target_select;
					panel.targetSelect(ptr, select_num+1);
					panel.setState(1);
					selections = default_selections;
				}else{
					System.out.println("you don't have 'sp' to use this skill !");
				}
			}
			repaint();
		}
		else if (selection_pattern == magic_select) {
			if(select_num == selections){
				backSelect();
			}
			else{
				if( ptr.unit.magic[select_num].cost <= ptr.SP){
					selection_pattern = aim_select;
					panel.magicSelect(ptr, select_num );
					panel.setState(1);
					selections = default_selections;
				}else{
					System.out.println("you don't have 'sp' to use this skill !");
				}
			}
			repaint();
		}
		else if(selection_pattern == item_select){
			if(select_num == selections){
				backSelect();
			}
			else{
				selection_pattern = using_select;
				panel.itemSelect(ptr, panel.item.havingItemList(myTurn)[select_num] );
				panel.setState(1);
				selections = default_selections;
			}
		}
		//		System.out.println(" -> changeSelect / selection_pattern :"+selection_pattern);
		//		System.out.println("");
	}
	//	System.out.println("changeSelect : selection_pattern = "+selection_pattern);


	public boolean selectable(int i) {
		//System.out.println("selections = "+selections);
		//System.out.println(" i = "+i);

		return (i >= 0 && i <= selections) ?true : false;
	}

	public void  backSelect() {
		//System.out.println("before/ selectioin_pattern : "+ selection_pattern);
		if(selection_pattern == target_select){
			selection_pattern = skill_select;
			panel.setSelect_num(cursor_posi2);
			selections = ptr.unit.skill.length - 1;
			select_num = cursor_posi2;
			panel.setState(0);
		}
		else if(selection_pattern == aim_select){
			selection_pattern = magic_select;
			panel.setSelect_num(cursor_posi2);
			selections = ptr.unit.magic.length ;
			select_num = cursor_posi2;
			panel.setState(0);
		}
		else if (selection_pattern == using_select) {
			selection_pattern = item_select;
			panel.setSelect_num(cursor_posi2);
			selections = panel.havingItemList(myTurn).length ;
			select_num = cursor_posi2;
			panel.setState(0);
		}
		else if(selection_pattern == item_select){
			//System.out.println("backSelect in skill_select");
			selection_pattern = motion_select;
			panel.setSelect_num(cursor_posi1);
			selections = default_selections;
			select_num = cursor_posi1;
		}

		else if(selection_pattern == skill_select){
			//System.out.println("backSelect in skill_select");
			selection_pattern = motion_select;
			panel.setSelect_num(cursor_posi1);
			selections = default_selections;
			select_num = cursor_posi1;
		}
		else if(selection_pattern == magic_select){
			//System.out.println("backSelect in skill_select");
			selection_pattern = motion_select;
			panel.setSelect_num(cursor_posi1);
			selections = default_selections;
			select_num = cursor_posi1;
		}
		else if(selection_pattern == move_select){
			selection_pattern = motion_select;
			panel.reselect();
			selections = default_selections;
			select_num = cursor_posi1;
			panel.setSelect_num(cursor_posi1);
			panel.setState(0);

		}else if(selection_pattern == attack_select){
			selection_pattern = motion_select;
			panel.reselect();
			selections = default_selections;
			select_num = 0;
			panel.setState(0);
		}

		else if (selection_pattern == motion_select) {
			selection_pattern = showing;
			panel.reselect();
			select_num = 0;
			panel.setState(1);
		}
		else if (selection_pattern == showing) {
			selection_pattern = waiting;
		}
		repaint();
		//System.out.println("after/ selectioin_pattern : "+ selection_pattern);
	}

	public void ptrLost() {
		selection_pattern = waiting;
		repaint();
	}
	public void endPhase() {
		if(myTurn){
			if(!PvP) selection_pattern = waiting;//enemy_select
			else selection_pattern = waiting;
		}else{
			selection_pattern = waiting;
		}
		myTurn = (myTurn) ? false:true;
		repaint();
	}
	public void getPhase() {
		selection_pattern = waiting;
		repaint();
	}
	public void PvPMode(boolean PvP) {
		this.PvP =PvP;
	}
	public void updatePiece(Piece[] friend, Piece[] enemy) {
		this.friend = Arrays.copyOf(friend, friend.length);
		this.enemy = Arrays.copyOf(enemy, enemy.length);
		repaint();
	}
	public void peekEnemyStatus(int i) {
		peek_enemy[i] = true;
		System.out.println("peek : "+ enemy[i].unit.name);
		repaint();
	}
	public void peekFriendStatus(int i) {
		peek_friend[i] = true;
		System.out.println("peek : "+ friend[i].unit.name);
		repaint();
	}
	public void missTargetStatus() {
		for(int i=0 ; i< peek_enemy.length ; i++) peek_enemy[i] = false;
		for(int i=0 ; i< peek_friend.length ; i++) peek_friend[i] = false;
		repaint();
	}
	private String[] makeSkillDetail(String string) {
		int w = 14;
		String[] str = string.split("");
		String[] ans;
		if(str.length <= w){
			ans = new String[2];
			ans[0] = string;
			ans[1] = "";
		}
		else{
			ans = new String[str.length/w+1];
			for(int i=0 ; i<ans.length ; i++){
				StringBuffer buff = new StringBuffer();
				int j;
				for( j = i*w +1 ; j< (i+1)*w +1 ; j++){
					if(j== str.length){
						break;
					}
					buff.append(str[j]);
				}
				ans[i] = buff.toString();
			}
		}
		return ans;
	}
}


