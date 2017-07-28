package main;

public class Piece {
	Unit unit;

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

	boolean alive = true;
	boolean acted = false;
	boolean moved = false;
	boolean waited = false;
	int[] position;
	int direction =3;

/////////////////////////////////////////
int[] condition = {0, 0, 0, 0, 0, 0, 0,};       // 状態異常を管理する変数  状態異常のターン数を表す  左から、毒、麻痺、凍結、気絶、睡眠、燃焼、暗闇
static final int poison = 0;
static final int paralysis = 1;
static final int freeze = 2;
static final int piyopiyo = 3;
static final int sleep = 4;
static final int burn = 5;
static final int ToLOVEru = 6;


int[] status_shift = {0, 0, 0, 0 ,0 ,0 ,0 ,0,};
                // 能力変化を管理する変数 能力変化のターン数を表す 左から、攻撃、防御、特功、特防、敏捷性、正確性、運、移動距離
/////////////////////////////////////////
	static final int up = 3;
	static final int right = 2;
	static final int  down= 0;
	static final int left = 1;



	Piece( Unit u,int[] p){
		u.weapon.specialEffect(u);

		this.unit = u;
		HP = u.HP+ u.weapon.HP;
		ATK = u.ATK+ u.weapon.ATK;
		DEF = u.DEF+ u.weapon.DEF;
		SP = u.SP;
		INT = u.INT+ u.weapon.INT;
		MEN = u.MEN+ u.weapon.MEN;
		AGI = u.AGI+ u.weapon.AGI;
		DEX = u.DEX+ u.weapon.DEX;
		LUCK = u.LUCK+ u.weapon.LUCK;
		MOVE = u.MOVE+ u.weapon.MOVE;


		for(int i=0 ; i< condition.length; i++) if(u.weapon.condition_resist[i]) condition[i] = -1;

		this.position = p;
	}

	public void getTurn(){
		acted = false;
		moved = false;
		waited = false;
	}

	public void acted(){
		//System.out.println(unit.name+"動いたお");
		acted = true;
	}
	public void moved(){
		moved = true;
	}
	public void waited(){
		acted = true;
		moved = true;
		waited = true;
	}
}
