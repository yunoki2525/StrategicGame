package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

public class CSPanel extends JPanel{
	Unit unit;
	Weapon weapon;

	CSPanel(Unit unit){
		this.unit= unit;
		this.weapon = unit.weapon;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		super.paintComponent(g);
		// 武器ステータス表関連
		String[][] weapondata = new String[][] {  { "名前"        ,unit.name},
												  { "装備名"        ,weapon.name},
												  { "HP"            , String.valueOf(unit.HP)+"(+"+String.valueOf(weapon.HP)+")"}, //　体力　：Hit Point(HP)：ヒットポイント
												  { "ATK(攻撃力)"   , String.valueOf(unit.ATK)+"(+"+String.valueOf(weapon.ATK)+")"},//　攻撃　：Attack(ATK)：アタック
												  { "DEF(防御力)"   , String.valueOf(unit.DEF)+"(+"+String.valueOf(weapon.DEF)+")"},//　防御　：Defense(DEF)：ディフェンス
												  { "SP(魔力)"      , String.valueOf(unit.SP)+"(+"+String.valueOf(weapon.SP)+")" },//　魔力　：Magic Point(MP)：マジックポイント
												  { "INT(魔法攻撃)" , String.valueOf(unit.INT)+"(+"+String.valueOf(weapon.INT)+")"},//魔法攻撃：Magic Attack(MATK/MAT)：マジックアタック
												  { "MEN(魔法防御)" , String.valueOf(unit.MEN)+"(+"+String.valueOf(weapon.MEN)+")"},//魔法防御：Magic Defense(MDEF/MDE)：マジックディフェンス
												  { "AGI(回避)"     , String.valueOf(unit.AGI)+"(+"+String.valueOf(weapon.AGI)+")"},//　回避率：Evasion（EVA）：エヴァージョン
												  { "DEX(命中)"     , String.valueOf(unit.DEX)+"(+"+String.valueOf(weapon.DEX)+")"},//　命中率：Dexterity（DEX）：デクステリティー
												  { "LUCK(運)"      , String.valueOf(unit.LUCK)+"(+"+String.valueOf(weapon.LUCK)+")"},//　運　　：Luck（LUK）:ラック
		};

        g.setFont( new Font("ＭＳ Ｐゴシック", Font.BOLD + Font.ITALIC, 15) );
        FontMetrics fm = g.getFontMetrics();

        Dimension d = getSize();
        for(int i = 0;i<weapondata.length;i++){
        	int x = 0;
        	int y = (d.height*(i+1)) / (weapondata.length+1) - fm.getDescent();

        	g.drawString(weapondata[i][0]+"：　"+weapondata[i][1], x, y);
        }

	}

	public void setWeapon(Weapon weapon){
		this.weapon=weapon;
		repaint();
	}

}
