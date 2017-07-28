package main;

abstract public class Weapon {
	String name;
	int id;
	int HP;
	int SP;
	int ATK;
	int DEF;
	int INT;
	int MEN;
	int AGI;
	int DEX;
	int LUCK;
	int MOVE;

	/*
	 * HP ~ MEN までは固定値でステータスが上がる
	 * AGI~LUCK までは割合でステータスが上がる
	 *
	 * 例： HP = 20 -> Piece のHP が 20上がる
	 *      AGI = 50 -> 命中率が 50% 上がる
	 *      LUCK = 20 -> Piece のLUCK が 20% 上がる
	 *
	 */
	int money;
	boolean[] condition_resist = new boolean[7];
	public static final int kind =24;

	Weapon(String name, int id,int money,int HP, int ATK, int DEF, int INT,int MEN, int AGI, int DEX, int LUCK, int MOVE){
		this.name=name;
		this.id=id;
		this.money = money;
		this.HP=HP;
		this.ATK = ATK;
		this.DEF=DEF;
		this.INT = INT;
		this.MEN=MEN;
		this.AGI=AGI;
		this.DEX=DEX;
		this.LUCK=LUCK;
		this.MOVE = MOVE;
	}
	Weapon(){
		name = "";
		id = 0;
		money =0;
		this.HP=0;
		this.ATK = 0;
		this.DEF = 0;
		this.INT = 0;
		this.MEN=0;
		this.AGI=0;
		this.DEX=0;
		this.LUCK=0;
		this.MOVE = 0;
	}

	abstract void specialEffect(Unit unit);
}

class NoWeapon extends Weapon{
	NoWeapon(){
		super();
	}
	void specialEffect(Unit unit) { }
}

//////////////////////以下地理作
class buki1 extends Weapon{
	buki1(){
		super("約束された勝利の剣",1,8000,0,10,0,0,0,0,0,0,0);
	}
	void specialEffect(Unit unit) {

	}
}
/////////////////
class buki2 extends Weapon{
	buki2(){
		super("全て遠き理想郷",2,8000,0,0,10,0,0,0,0,0,0);
	}
	void specialEffect(Unit unit) {

	}
}
//////////////////
class buki3 extends Weapon{
	buki3(){
		super("天地乖離す開闢の星",3,8000,0,0,0,10,0,0,0,0,0);
	}
	void specialEffect(Unit unit) {

	}
}
//////////////////
class buki4 extends Weapon{
	buki4(){
		super("セラフ・プレステージ",4,8000,0,0,0,0,10,0,0,0,0);
	}
	void specialEffect(Unit unit) {

	}
}
/////////////////
class buki5 extends Weapon{
	buki5(){
		super("ジェットブレード・スカイ",5,11000,10,5,0,0,0,0,0,0,0);
	}
	void specialEffect(Unit unit) {

	}
}
/////////////////////
class buki6 extends Weapon{
	buki6(){
		super("ロスヴァイセ",6,11000,0,0,0,0,0,10,0,0,0);
	}
	void specialEffect(Unit unit) {
		AGI= unit.AGI * this.AGI /100;
	}
}
///////////////////
class buki7 extends Weapon{
	buki7(){
		super("天鹿児弓",7,11000,0,0,0,0,0,0,10,0,0);
	}
	void specialEffect(Unit unit) {
		DEX= unit.DEX * this.DEX /100;
	}
}
//////////////////////
class buki8 extends Weapon{
	buki8(){
		super("ハートオブエイン",8,11000,0,0,0,0,0,0,0,10,0);
	}
	void specialEffect(Unit unit) {
		LUCK= unit.LUCK * this.LUCK /100;
	}
}
///////////////////////
class buki9 extends Weapon{
	buki9(){
		super("サーモンオブナレッジ",9,11000,0,0,0,0,0,0,0,0,1);
	}
	void specialEffect(Unit unit) {
	}
}
/////////////////////////
class buki10 extends Weapon{
	buki10(){
		super("破邪神の指輪",10,14000,0,0,0,0,0,0,0,0,0);
		condition_resist[Piece.poison] = true;
	}
	void specialEffect(Unit unit) {

	}
}
/////////////////////////
class buki11 extends Weapon{
	buki11(){
		super("マグネットコーティング",11,14000,0,0,0,0,0,0,0,0,0);
		condition_resist[Piece.paralysis] = true;
	}
	void specialEffect(Unit unit) {

	}
}
/////////////////////////
class buki12 extends Weapon{
	buki12(){
		super("松岡修造の写真",12,14000,0,0,0,0,0,0,0,0,0);
		condition_resist[Piece.freeze] = true;
	}
	void specialEffect(Unit unit) {

	}
}
///////////////////////
class buki13 extends Weapon{
	buki13(){
		super("柚木の助言",13,14000,0,0,0,0,0,0,0,0,0);
		condition_resist[Piece.piyopiyo] = true;
	}
	void specialEffect(Unit unit) {

	}
}
/////////////////////////
class buki14 extends Weapon{
	buki14(){
		super("眠眠打破",14,14000,0,0,0,0,0,0,0,0,0);
		condition_resist[Piece.sleep] = true;
	}
	void specialEffect(Unit unit) {

	}
}
///////////////////////////////
class buki15 extends Weapon{
	buki15(){
		super("ラーウェイ",15,14000,0,0,0,0,0,0,0,0,0);
		condition_resist[Piece.burn] = true;
	}
	void specialEffect(Unit unit) {

	}
}
////////////////////////////
class buki16 extends Weapon{
	buki16(){
		super("閃光弾",16,14000,0,0,0,0,0,0,0,0,0);
		condition_resist[Piece.ToLOVEru] = true;
	}
	void specialEffect(Unit unit) {

	}
}
///////////////////////////////
class buki17 extends Weapon{
	buki17(){
		super("一蘭のラーメン",17,30000,0,0,0,0,0,0,0,0,0);

	}
	void specialEffect(Unit unit) {
		if(unit.name.equalsIgnoreCase("福岡")){

			ATK = 10;
			DEF = 10;
			HP = 20;
		}
	}
}
/////////////////////////////////
class buki18 extends Weapon{
	buki18(){
		super("高崎",18,30000,0,0,0,0,0,0,0,0,0);

	}
	void specialEffect(Unit unit) {
		if(unit.name.equalsIgnoreCase("大分")){
			ATK = 5;
			DEF = 15;
			HP = 10;
		}
	}
}
/////////////////////////////////
class buki19 extends Weapon{
	buki19(){
		super("栗おこわ",19,30000,0,0,0,0,0,0,0,0,0);

	}
	void specialEffect(Unit unit) {
		if(unit.name.equalsIgnoreCase("佐賀")){
			DEF = 10;
			HP = 30;
		}
	}
}
/////////////////////////////////
class buki20 extends Weapon{
	buki20(){
		super("薩摩剣士隼人",20,30000,0,0,0,0,0,0,0,0,0);

	}
	void specialEffect(Unit unit) {
		if(unit.name.equalsIgnoreCase("鹿児島")){
			INT = 10;
			DEF = 30;
		}
	}
}
/////////////////////////////////
class buki21 extends Weapon{
	buki21(){
		super("カステラ",21,30000,0,0,0,0,0,0,0,0,0);

	}
	void specialEffect(Unit unit) {
		if(unit.name.equalsIgnoreCase("長崎")){
			ATK = 10;
			DEF = 10;
			HP = 20;
		}
	}
}
/////////////////////////////////
class buki22 extends Weapon{
	buki22(){
		super("マンゴー",22,30000,0,0,0,0,0,0,0,0,0);

	}
	void specialEffect(Unit unit) {
		if(unit.name.equalsIgnoreCase("宮崎")){
			ATK = 10;
			DEF = 10;
			HP = 20;
		}
	}
}
/////////////////////////////////
class buki23 extends Weapon{
	buki23(){
		super("くまもん",23,30000,0,0,0,0,0,0,0,0,0);

	}
	void specialEffect(Unit unit) {
		if(unit.name.equalsIgnoreCase("熊本")){
			ATK = 15;
			DEF = 10;
			HP = 15;
		}
	}
}
/////////////////////////////////
class buki24 extends Weapon{
	buki24(){
		super("幸運のたらい",24,100000,50,10,10,10,10,50,50,100,-1);
	}
	void specialEffect(Unit unit) {
		System.out.println("幸運のたらい");
		LUCK= unit.LUCK * this.LUCK /100;
	}
}


