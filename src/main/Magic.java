package main;

public abstract class Magic {
	String name;
	int effect_id;
	int type;
	int cost;
	int power;
	boolean support;

	int[][] status_management = new int[8][3]; // バフの種類と倍率とターン数
	int[][] condition_add = new int[7][2]; // 状態異常付与の種類と確率とターン数
	int drain; // ドレイン率（与えたダメージの何パーセント回復するか）

	static final int normal = 0;
	static final int  fire= 1;
	static final int  aqua= 2;
	static final int  sky= 3;
	static final int  grand = 4;
	static final int  ray = 5;
	static final int  dark= 6;

	public Magic(String name,int effect_id,int type ,int cost, int power, boolean support,int drain){
		this.effect_id = effect_id;
		this.name=name;
		this.type = type;
		this.cost=cost;
		this.power=power;
		this.support = support;
		this.drain = drain;
	}


	abstract boolean inRange(int[] posi,int direction,int x,int y);
	abstract boolean attackRange(int direction, int[] target_posi,int x, int y);


}


class DefoMagic extends Magic{

	DefoMagic(){
		super("デフォ的な魔法",-1, normal,0, 100, false,0);
	}
	boolean inRange(int[] posi,int direction, int x, int y) {
		if(direction == Piece.up &&  y - posi[1] == -1 && x - posi[0] == 0) return true;
		if(direction == Piece.right &&  y - posi[1] == 0 && x - posi[0] == 1) return true;
		if(direction == Piece.down &&  y - posi[1] == 1 && x - posi[0] == 0) return true;
		if(direction == Piece.left &&  y - posi[1] == 0 && x - posi[0] == -1) return true;
		return false;
	}
	boolean attackRange(int direction, int[] target_posi,int x, int y) {
		if(target_posi[0] == x && target_posi[1] == y)return true;
		return false;
	}
	public String toString(){
		String str = "デフォです。";
		return str;
	}
}
class kuoka1 extends Magic{
	kuoka1() {
		super("豚骨以外認めない!", 2, fire, 50, 120, false, 0); // 名前、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
		condition_add[1][0] = 50;
		condition_add[1][1] = 3; // 麻痺
	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if (direction == Piece.up) {
			if (Math.abs(posi[0] - x) <= 2 && posi[1] > y
					&& Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.right) {
			if (Math.abs(posi[1] - y) <= 2 && posi[0] < x
					&& Math.abs(posi[0] - x) <= 4)
				return true;
		} else if (direction == Piece.down) {
			if (Math.abs(posi[0] - x) <= 2 && posi[1] < y
					&& Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.left) {
			if (Math.abs(posi[1] - y) <= 2 && posi[0] > x
					&& Math.abs(posi[0] - x) <= 4)
				return true;
		}
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (target_posi[0] == x && target_posi[1] == y)
			return true;
		return false;
	}
}

class kuoka2 extends Magic {
	kuoka2() {
		super("バリカタ以外認めない!", 19, aqua, 50, 140, false, 0); // 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
		status_management[1][0] = 150;
		status_management[1][1] = 0;
		status_management[1][2] = 3; // DEF
	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if (direction == Piece.up) {
			if (posi[0] == x && posi[1] > y && Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.right) {
			if (posi[1] == y && posi[0] < x && Math.abs(posi[0] - x) <= 4)
				return true;
		} else if (direction == Piece.down) {
			if (posi[0] == x && posi[1] < y && Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.left) {
			if (posi[1] == y && posi[0] > x && Math.abs(posi[0] - x) <= 4)
				return true;
		}
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (direction == Piece.up || direction == Piece.down) {
			if (target_posi[1] == y && Math.abs(target_posi[0] - x) <= 1)
				return true;
		} else {
			if (target_posi[0] == x && Math.abs(target_posi[1] - y) <= 1)
				return true;

		}
		return false;
	}
}
//////////////////////////////////////////以下地理作
//////////////鹿児島
class Kagoshima1 extends Magic {
	Kagoshima1() {
		super("桜島大爆発", 48, fire, 50, 140, false, 0); // 名前、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
		condition_add[5][0] = 100;
		condition_add[5][1] = 5; // 燃焼
	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if (direction == Piece.up) {
			if (Math.abs(posi[0] - x) <= 1 && posi[1] > y
					&& Math.abs(posi[1] - y) <= 3)
				return true;
		} else if (direction == Piece.right) {
			if (Math.abs(posi[1] - y) <= 1 && posi[0] < x
					&& Math.abs(posi[0] - x) <= 3)
				return true;
		} else if (direction == Piece.down) {
			if (Math.abs(posi[0] - x) <= 1 && posi[1] < y
					&& Math.abs(posi[1] - y) <= 3)
				return true;
		} else if (direction == Piece.left) {
			if (Math.abs(posi[1] - y) <= 1 && posi[0] > x
					&& Math.abs(posi[0] - x) <= 3)
				return true;
		}
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (Math.abs(target_posi[0] - x) <= 1
				&& Math.abs(target_posi[1] - y) <= 1)
			return true;
		return false;
	}
	public String toString(){
		String str = "鹿児島のシンボルです。桜島の火山灰はなかなかメンドウだったりします。燃焼5ターン付与100%";

		return str;
	}

}

class Kagoshima2 extends Magic {
	Kagoshima2() {
		super("好きです、かすたどん", -1, normal, 50, 300, true, 0); // 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
		status_management[0][0] = 150;
		status_management[0][1] = 0;
		status_management[0][2] = 2; // ATK 0 -> 味方バフ倍率,
	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if(posi[0] == x  && posi[1] == y) return true;
		if (direction == Piece.up) {
			if (Math.abs(posi[0] - x) <= 2 && posi[1] > y
					&& Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.right) {
			if (Math.abs(posi[1] - y) <= 2 && posi[0] < x
					&& Math.abs(posi[0] - x) <= 4)
				return true;
		} else if (direction == Piece.down) {
			if (Math.abs(posi[0] - x) <= 2 && posi[1] < y
					&& Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.left) {
			if (Math.abs(posi[1] - y) <= 2 && posi[0] > x
					&& Math.abs(posi[0] - x) <= 4)
				return true;
		}
		
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (target_posi[0] == x && target_posi[1] == y)
			return true;
		return false;
	}
	public String toString(){
		String str = "薩摩蒸氣屋のおしゃれな銘菓。カスタードクリームをふんわり生地で包みました。" +
				"味方のHP300回復&2ターン味方の攻撃1.5倍";

		return str;
	}

}
///////////////////////////
///////////////////////愛媛
class ehime1 extends Magic {
	ehime1() {
		super("こどもの城", 9, ray, 40, 110, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if (direction == Piece.up) {
			if (Math.abs(posi[0] - x) <= 1 && posi[1] > y
					&& Math.abs(posi[1] - y) <= 3)
				return true;
		} else if (direction == Piece.right) {
			if (Math.abs(posi[1] - y) <= 1 && posi[0] < x
					&& Math.abs(posi[0] - x) <= 3)
				return true;
		} else if (direction == Piece.down) {
			if (Math.abs(posi[0] - x) <= 1 && posi[1] < y
					&& Math.abs(posi[1] - y) <= 3)
				return true;
		} else if (direction == Piece.left) {
			if (Math.abs(posi[1] - y) <= 1 && posi[0] > x
					&& Math.abs(posi[0] - x) <= 3)
				return true;
		}
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (Math.abs(target_posi[0] - x) <= 1
				&& Math.abs(target_posi[1] - y) <= 1)
			return true;
		return false;
	}
	public String toString(){
		String str = "ちゃんとwikiにも載ってます。";

		return str;
	}

}

// ///////////
class ehime2 extends Magic {
	ehime2() {
		super("四国カルスト", 40, grand, 50, 120, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
		condition_add[3][0] = 80;
		condition_add[3][1] = 3; // 気絶
	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if (direction == Piece.up) {
			if (posi[0] == x && posi[1] > y && Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.right) {
			if (posi[1] == y && posi[0] < x && Math.abs(posi[0] - x) <= 4)
				return true;
		} else if (direction == Piece.down) {
			if (posi[0] == x && posi[1] < y && Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.left) {
			if (posi[1] == y && posi[0] > x && Math.abs(posi[0] - x) <= 4)
				return true;
		}
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (direction == Piece.up || direction == Piece.down) {
			if (target_posi[1] == y && Math.abs(target_posi[0] - x) <= 1)
				return true;
		} else {
			if (target_posi[0] == x && Math.abs(target_posi[1] - y) <= 1)
				return true;

		}
		return false;
	}
	public String toString(){
		String str = "秋吉台に似た場所です。気絶3ターン付与80%";

		return str;
	}

}
///////////////////////////////
/////////////////////////////鳥取
class tottori1 extends Magic {
	tottori1() {
		super("雨滝の癒し", -1, normal, 50, 200, true, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if(posi[0] == x  && posi[1] == y) return true;
		if (direction == Piece.up) {
			if (Math.abs(posi[0] - x) <= 2 && posi[1] > y
					&& Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.right) {
			if (Math.abs(posi[1] - y) <= 2 && posi[0] < x
					&& Math.abs(posi[0] - x) <= 4)
				return true;
		} else if (direction == Piece.down) {
			if (Math.abs(posi[0] - x) <= 2 && posi[1] < y
					&& Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.left) {
			if (Math.abs(posi[1] - y) <= 2 && posi[0] > x
					&& Math.abs(posi[0] - x) <= 4)
				return true;
		}
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (target_posi[0] == x && target_posi[1] == y)
			return true;
		return false;
	}
	public String toString(){
		String str = "鳥取県一の大飛瀑の雨滝。";

		return str;
	}
}

// ///////
class tottori2 extends Magic {
	tottori2() {
		super("三朝温泉", 65, aqua, 40, 110, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if (direction == Piece.up) {
			if (posi[0] == x && posi[1] > y && Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.right) {
			if (posi[1] == y && posi[0] < x && Math.abs(posi[0] - x) <= 4)
				return true;
		} else if (direction == Piece.down) {
			if (posi[0] == x && posi[1] < y && Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.left) {
			if (posi[1] == y && posi[0] > x && Math.abs(posi[0] - x) <= 4)
				return true;
		}
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (direction == Piece.up || direction == Piece.down) {
			if (target_posi[1] == y && Math.abs(target_posi[0] - x) <= 1)
				return true;
		} else {
			if (target_posi[0] == x && Math.abs(target_posi[1] - y) <= 1)
				return true;

		}
		return false;
	}
	public String toString(){
		String str = "鳥取は水系の技ばかりですね…。";

		return str;
	}
}
/////////////////////////////////
////////////////////////////////三重
class mie1 extends Magic{
	mie1(){
		super("内宮：天照大御神",22, fire, 50, 130,false,0);// 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
	}
	boolean inRange(int[] posi,int direction, int x, int y) {
		if(direction == Piece.up){
			if(posi[0] == x && posi[1] > y && Math.abs(posi[1] - y) <=4) return true;
		}else if(direction == Piece.right){
			if(posi[1] == y && posi[0] < x && Math.abs(posi[0] - x) <=4) return true;
		}else if(direction == Piece.down){
			if(posi[0] == x && posi[1] < y && Math.abs(posi[1] - y) <=4) return true;
		}else if(direction == Piece.left){
			if(posi[1] == y && posi[0] > x && Math.abs(posi[0] - x) <=4) return true;
		}
		return false;
	}
	boolean attackRange(int direction,int[] target_posi,int x, int y) {
		if(Math.abs(target_posi[0] - x) <= 1 && target_posi[1] == y ||
				Math.abs(target_posi[1] - y) <= 1 && target_posi[0] == x)
			return true;
		return false;
	}
	public String toString(){
		String str = "太陽を司る神です。";

		return str;
	}
}
class mie2 extends Magic{
	mie2(){
		super("外宮：豊受大御神",36, grand, 40, 110,false,0);// 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
	}
	boolean inRange(int[] posi,int direction, int x, int y) {
		if(direction == Piece.up){
			if(posi[0] == x && posi[1] > y && Math.abs(posi[1] - y) <=4) return true;
		}else if(direction == Piece.right){
			if(posi[1] == y && posi[0] < x && Math.abs(posi[0] - x) <=4) return true;
		}else if(direction == Piece.down){
			if(posi[0] == x && posi[1] < y && Math.abs(posi[1] - y) <=4) return true;
		}else if(direction == Piece.left){
			if(posi[1] == y && posi[0] > x && Math.abs(posi[0] - x) <=4) return true;
		}
		return false;
	}

	boolean attackRange(int direction,int[] target_posi,int x, int y) {
		if(direction == Piece.up || direction == Piece.down){
			if(target_posi[1] == y && Math.abs(target_posi[0] - x) <= 1)
				return true;
		}else{
			if(target_posi[0] == x && Math.abs(target_posi[1] - y) <= 1)
				return true;

		}
		return false;
	}
	public String toString(){
		String str = "食べ物を司る神です。";

		return str;
	}
}
//////////////////////////
//////////////////////////和歌山
class wakayama1 extends Magic{
	wakayama1(){
		super("わ～い、みかんとったよ～",12, grand, 50, 120,false,0);// 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
	}
	boolean inRange(int[] posi,int direction, int x, int y) {
		if(direction == Piece.up){
			if(Math.abs(posi[0] - x) <= 2 && posi[1] > y && Math.abs(posi[1] - y) <=4) return true;
		}else if(direction == Piece.right){
			if(Math.abs(posi[1] - y) <= 2 && posi[0] < x && Math.abs(posi[0] - x) <=4) return true;
		}else if(direction == Piece.down){
			if(Math.abs(posi[0] - x) <= 2 && posi[1] < y && Math.abs(posi[1] - y) <=4) return true;
		}else if(direction == Piece.left){
			if(Math.abs(posi[1] - y) <= 2 && posi[0] > x && Math.abs(posi[0] - x) <=4) return true;
		}
		return false;
	}
	boolean attackRange(int direction,int[] target_posi,int x, int y) {
		if(target_posi[0] == x && target_posi[1] == y) return true;
		return false;
	}
	public String toString(){
		String str = "みかんで有名な県なら、こんな技使うんじゃないでしょうか？。";

		return str;
	}
}
class wakayama2 extends Magic{
	wakayama2(){
		super("きみ、かわいいね～",51, sky, 50, 130,false,0);// 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
	}
	boolean inRange(int[] posi,int direction, int x, int y) {
		if(direction == Piece.up){
			if(posi[0] == x && posi[1] > y && Math.abs(posi[1] - y) <=4) return true;
		}else if(direction == Piece.right){
			if(posi[1] == y && posi[0] < x && Math.abs(posi[0] - x) <=4) return true;
		}else if(direction == Piece.down){
			if(posi[0] == x && posi[1] < y && Math.abs(posi[1] - y) <=4) return true;
		}else if(direction == Piece.left){
			if(posi[1] == y && posi[0] > x && Math.abs(posi[0] - x) <=4) return true;
		}
		return false;
	}

	boolean attackRange(int direction,int[] target_posi,int x, int y) {
		if(direction == Piece.up || direction == Piece.down){
			if(target_posi[1] == y && Math.abs(target_posi[0] - x) <= 1)
				return true;
		}else{
			if(target_posi[0] == x && Math.abs(target_posi[1] - y) <= 1)
				return true;

		}
		return false;
	}
	public String toString(){
		String str = "県は全く関係ないですね。";

		return str;
	}
}
///////////////////////////
//////////////////////////静岡
class sizuoka1 extends Magic {
	sizuoka1() {
		super("エアーパラダイス", 49, sky, 50, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if (direction == Piece.up) {
			if (posi[0] == x && posi[1] > y && Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.right) {
			if (posi[1] == y && posi[0] < x && Math.abs(posi[0] - x) <= 4)
				return true;
		} else if (direction == Piece.down) {
			if (posi[0] == x && posi[1] < y && Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.left) {
			if (posi[1] == y && posi[0] > x && Math.abs(posi[0] - x) <= 4)
				return true;
		}
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (direction == Piece.up || direction == Piece.down) {
			if (target_posi[1] == y && Math.abs(target_posi[0] - x) <= 1)
				return true;
		} else {
			if (target_posi[0] == x && Math.abs(target_posi[1] - y) <= 1)
				return true;

		}
		return false;
	}
	public String toString(){
		String str = "日本で唯一の航空自衛隊のテーマパークです。";

		return str;
	}

}

class sizuoka2 extends Magic {
	sizuoka2() {
		super("フラワーパラダイス", 45, grand, 50, 120, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if (direction == Piece.up) {
			if (Math.abs(posi[0] - x) <= 2 && posi[1] > y
					&& Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.right) {
			if (Math.abs(posi[1] - y) <= 2 && posi[0] < x
					&& Math.abs(posi[0] - x) <= 4)
				return true;
		} else if (direction == Piece.down) {
			if (Math.abs(posi[0] - x) <= 2 && posi[1] < y
					&& Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.left) {
			if (Math.abs(posi[1] - y) <= 2 && posi[0] > x
					&& Math.abs(posi[0] - x) <= 4)
				return true;
		}
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (target_posi[0] == x && target_posi[1] == y)
			return true;
		return false;
	}
	public String toString(){
		String str = "はままつフラワーパークより。";

		return str;
	}
}
//////////////////////////
/////////////////////////富山
class toyama1 extends Magic {
	toyama1() {
		super("黒部ダムの放水", 65, aqua, 50, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if (direction == Piece.up) {
			if (posi[0] == x && posi[1] > y && Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.right) {
			if (posi[1] == y && posi[0] < x && Math.abs(posi[0] - x) <= 4)
				return true;
		} else if (direction == Piece.down) {
			if (posi[0] == x && posi[1] < y && Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.left) {
			if (posi[1] == y && posi[0] > x && Math.abs(posi[0] - x) <= 4)
				return true;
		}
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (direction == Piece.up || direction == Piece.down) {
			if (target_posi[1] == y && Math.abs(target_posi[0] - x) <= 1)
				return true;
		} else {
			if (target_posi[0] == x && Math.abs(target_posi[1] - y) <= 1)
				return true;

		}
		return false;
	}
	public String toString(){
		String str = "ダムの放水って迫力ありますよね。";

		return str;
	}
}

class toyama2 extends Magic {
	toyama2() {
		super("ホタルイカの発光", 50, ray, 50, 140, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if (direction == Piece.up) {
			if (Math.abs(posi[0] - x) <= 1 && posi[1] > y
					&& Math.abs(posi[1] - y) <= 3)
				return true;
		} else if (direction == Piece.right) {
			if (Math.abs(posi[1] - y) <= 1 && posi[0] < x
					&& Math.abs(posi[0] - x) <= 3)
				return true;
		} else if (direction == Piece.down) {
			if (Math.abs(posi[0] - x) <= 1 && posi[1] < y
					&& Math.abs(posi[1] - y) <= 3)
				return true;
		} else if (direction == Piece.left) {
			if (Math.abs(posi[1] - y) <= 1 && posi[0] > x
					&& Math.abs(posi[0] - x) <= 3)
				return true;
		}
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (Math.abs(target_posi[0] - x) <= 1
				&& Math.abs(target_posi[1] - y) <= 1)
			return true;
		return false;
	}
	public String toString(){
		String str = "富山ではホタルイカが取れます。";

		return str;
	}
}
///////////////////////////
//////////////////////////岩手
class iwate1 extends Magic {
	iwate1() {
		super("浄土庭園の緑", 37, grand, 50, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if (direction == Piece.up) {
			if (posi[0] == x && posi[1] > y && Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.right) {
			if (posi[1] == y && posi[0] < x && Math.abs(posi[0] - x) <= 4)
				return true;
		} else if (direction == Piece.down) {
			if (posi[0] == x && posi[1] < y && Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.left) {
			if (posi[1] == y && posi[0] > x && Math.abs(posi[0] - x) <= 4)
				return true;
		}
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (direction == Piece.up || direction == Piece.down) {
			if (target_posi[1] == y && Math.abs(target_posi[0] - x) <= 1)
				return true;
		} else {
			if (target_posi[0] == x && Math.abs(target_posi[1] - y) <= 1)
				return true;

		}
		return false;
	}
	public String toString(){
		String str = "仏教における極楽浄土を模した庭園です。";

		return str;
	}
}

// ////////
class iwate2 extends Magic {
	iwate2() {
		super("釜石大観音の力", -1, normal, 50, 250, true, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if(posi[0] == x  && posi[1] == y) return true;
		if (direction == Piece.up) {
			if (Math.abs(posi[0] - x) <= 2 && posi[1] > y
					&& Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.right) {
			if (Math.abs(posi[1] - y) <= 2 && posi[0] < x
					&& Math.abs(posi[0] - x) <= 4)
				return true;
		} else if (direction == Piece.down) {
			if (Math.abs(posi[0] - x) <= 2 && posi[1] < y
					&& Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.left) {
			if (Math.abs(posi[1] - y) <= 2 && posi[0] > x
					&& Math.abs(posi[0] - x) <= 4)
				return true;
		}
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (target_posi[0] == x && target_posi[1] == y)
			return true;
		return false;
	}
	public String toString(){
		String str = "海抜120mの絶景。";

		return str;
	}
}
//////////////////////
//////////////////////埼玉
class saitama1 extends Magic {

	saitama1() {
		super("トトロの森", 37, grand, 50, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if (direction == Piece.up) {
			if (posi[0] == x && posi[1] > y && Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.right) {
			if (posi[1] == y && posi[0] < x && Math.abs(posi[0] - x) <= 4)
				return true;
		} else if (direction == Piece.down) {
			if (posi[0] == x && posi[1] < y && Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.left) {
			if (posi[1] == y && posi[0] > x && Math.abs(posi[0] - x) <= 4)
				return true;
		}
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (Math.abs(target_posi[0] - x) <= 1 && target_posi[1] == y
				|| Math.abs(target_posi[1] - y) <= 1 && target_posi[0] == x)
			return true;
		return false;
	}
	public String toString(){
		String str = "トトロの森のモデルがあります。";

		return str;
	}

}

// ///////////
class saitama2 extends Magic {

	saitama2() {
		super("五百羅漢・怒", 7, dark, 50, 140, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
		condition_add[0][0] = 80;
		condition_add[0][1] = 5;

	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if (direction == Piece.up) {
			if (posi[0] == x && posi[1] > y && Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.right) {
			if (posi[1] == y && posi[0] < x && Math.abs(posi[0] - x) <= 4)
				return true;
		} else if (direction == Piece.down) {
			if (posi[0] == x && posi[1] < y && Math.abs(posi[1] - y) <= 4)
				return true;
		} else if (direction == Piece.left) {
			if (posi[1] == y && posi[0] > x && Math.abs(posi[0] - x) <= 4)
				return true;
		}
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (direction == Piece.up || direction == Piece.down) {
			if (target_posi[1] == y && Math.abs(target_posi[0] - x) <= 1)
				return true;
		} else {
			if (target_posi[0] == x && Math.abs(target_posi[1] - y) <= 1)
				return true;

		}
		return false;
	}
	public String toString(){
		String str = "とても闇闇してます。毒3ターン付与80%";

		return str;
	}
}
//////////////////////////
