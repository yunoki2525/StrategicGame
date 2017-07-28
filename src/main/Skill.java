package main;

abstract public class Skill {
	String name;
	int effect_id;
	int cost;
	int power; // suppot = true の時 そのままスキル倍率、 support = false の時 回復量（固定値）
	int type;
	// int recovery;
	int[][] status_management = new int[8][3]; // バフの種類と倍率とターン数
	int[][] condition_add = new int[7][2]; // 状態異常付与の種類と確率とターン数
	boolean support;
	int drain; // ドレイン率（与えたダメージの何パーセント回復するか）

	static final int normal = 0;//無
	static final int slash = 1;//斬
	static final int spear = 2;//突
	static final int blow = 3;//打

	//魔法の属性
	static final int fire = 1;
	static final int aqua = 2;
	static final int sky = 3;
	static final int grand = 4;
	static final int ray = 5;
	static final int dark = 6;

	public Skill(String name, int effect_id, int type, int cost, int power,
			boolean support, int drain) {
		this.name = name;
		this.effect_id = effect_id;
		this.type = type;
		this.cost = cost;
		this.power = power;

		this.support = support;
		this.drain = drain;
	}

	abstract boolean inRange(int[] posi, int direction, int x, int y);

	abstract boolean attackRange(int direction, int[] target_posi, int x, int y);

}

class Defo extends Skill {
	Defo() {
		super("デフォルト", 10, 2, 0, 100, false, 0); // 名前、属性、消費SP、スキル倍率、支援技かどうか
		status_management[0][0] = 0;
		status_management[0][1] = 0;
		status_management[0][2] = 0; // ATK 0 -> 自バフ倍率,
		status_management[1][0] = 0;
		status_management[1][1] = 0;
		status_management[1][2] = 0; // DEF 1 -> 敵デバフ倍率,
		status_management[2][0] = 0;
		status_management[2][1] = 0;
		status_management[2][2] = 0; // INT 2 -> ターン数
		status_management[3][0] = 0;
		status_management[3][1] = 0;
		status_management[3][2] = 0; // MEN
		status_management[4][0] = 0;
		status_management[4][1] = 0;
		status_management[4][2] = 0; // AGI
		status_management[5][0] = 0;
		status_management[5][1] = 0;
		status_management[5][2] = 0; // DEX
		status_management[6][0] = 0;
		status_management[6][1] = 0;
		status_management[6][2] = 0; // LUCK
		status_management[7][0] = 0;
		status_management[7][1] = 0;
		status_management[7][2] = 0; // MOVE

		condition_add[0][0] = 0;
		condition_add[0][1] = 0; // 毒 0 -> 確率, 1 -> ターン数
		condition_add[1][0] = 0;
		condition_add[1][1] = 0; // 麻痺
		condition_add[2][0] = 0;
		condition_add[2][1] = 0; // 凍結
		condition_add[3][0] = 0;
		condition_add[3][1] = 0; // 気絶
		condition_add[4][0] = 0;
		condition_add[4][1] = 0; // 混乱
		condition_add[5][0] = 0;
		condition_add[5][1] = 0; // 燃焼
		condition_add[6][0] = 0;
		condition_add[6][1] = 0; // 暗闇
	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if (direction == Piece.up && y - posi[1] == -1 && x - posi[0] == 0)
			return true;
		if (direction == Piece.right && y - posi[1] == 0 && x - posi[0] == 1)
			return true;
		if (direction == Piece.down && y - posi[1] == 1 && x - posi[0] == 0)
			return true;
		if (direction == Piece.left && y - posi[1] == 0 && x - posi[0] == -1)
			return true;
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (target_posi[0] == x && target_posi[1] == y)
			return true;
		return false;
	}
}
class Snatch extends Skill {

	Snatch() {
		super("スナッチ", 0, 2, 30, 100, false, 10); // 名前、属性、消費SP、スキル倍率、支援技かどうか
		status_management[0][0] = 150;
		status_management[0][1] = 50;
		status_management[0][2] = 2; // ATK 0 -> 味方バフ倍率,
		status_management[1][0] = 150;
		status_management[1][1] = 50;
		status_management[1][2] = 2; // DEF 1 -> 敵デバフ倍率,
		status_management[2][0] = 0;
		status_management[2][1] = 0;
		status_management[2][2] = 0; // INT 2 -> ターン数
		status_management[3][0] = 0;
		status_management[3][1] = 0;
		status_management[3][2] = 0; // MEN
		status_management[4][0] = 0;
		status_management[4][1] = 0;
		status_management[4][2] = 0; // AGI
		status_management[5][0] = 0;
		status_management[5][1] = 0;
		status_management[5][2] = 0; // DEX
		status_management[6][0] = 0;
		status_management[6][1] = 0;
		status_management[6][2] = 0; // LUCK
		status_management[7][0] = 0;
		status_management[7][1] = 0;
		status_management[7][2] = 0; // MOVE

		condition_add[0][0] = 0;
		condition_add[0][1] = 0; // 毒 0 -> 確率, 1 -> ターン数
		condition_add[1][0] = 0;
		condition_add[1][1] = 0; // 麻痺
		condition_add[2][0] = 0;
		condition_add[2][1] = 0; // 凍結
		condition_add[3][0] = 0;
		condition_add[3][1] = 0; // 気絶
		condition_add[4][0] = 0;
		condition_add[4][1] = 0; // 混乱
		condition_add[5][0] = 0;
		condition_add[5][1] = 0; // 燃焼
		condition_add[6][0] = 0;
		condition_add[6][1] = 0; // 暗闇
	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if (direction == Piece.up) {
			if (posi[0] == x && posi[1] >= y && Math.abs(posi[1] - y) <= 3)
				return true;
		} else if (direction == Piece.right) {
			if (posi[1] == y && posi[0] <= x && Math.abs(posi[0] - x) <= 3)
				return true;
		} else if (direction == Piece.down) {
			if (posi[0] == x && posi[1] <= y && Math.abs(posi[1] - y) <= 3)
				return true;
		} else if (direction == Piece.left) {
			if (posi[1] == y && posi[0] >= x && Math.abs(posi[0] - x) <= 3)
				return true;
		}
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {

		if (direction == Piece.up) {
			if ((target_posi[0] == x && target_posi[1] == y)
					|| target_posi[1] - 1 == y
					&& Math.abs(target_posi[0] - x) <= 1)
				return true;
		} else if (direction == Piece.right) {
			if ((target_posi[0] == x && target_posi[1] == y)
					|| target_posi[0] + 1 == x
					&& Math.abs(target_posi[1] - y) <= 1)
				return true;
		} else if (direction == Piece.down) {
			if ((target_posi[0] == x && target_posi[1] == y)
					|| target_posi[1] + 1 == y
					&& Math.abs(target_posi[0] - x) <= 1)
				return true;
		} else if (direction == Piece.left) {
			if ((target_posi[0] == x && target_posi[1] == y)
					|| target_posi[0] - 1 == x
					&& Math.abs(target_posi[1] - y) <= 1) {
				// System.out.println("attacker_posi  : ("+attacker_posi[0]+" , "+attacker_posi[1]+")");
				// System.out.println("target_posi  : ("+target_posi[0]+" , "+target_posi[1]+")");
				// System.out.println("( x , y )   = ("+x+" , "+y+")");

				return true;
			}
		}
		return false;
	}
}

// //////////////////////////////////////////////////////////////////////////////////////////////////

// ////////////// 九州 //////////////////////////////////


// /////////福岡
class Fukuoka1 extends Skill {
	Fukuoka1() {
		super("豚骨以外認めない!", 2, slash, 50, 120, false, 0); // 名前、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
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

	public String toString(){
		String str = "ラーメンは豚骨ラーメンが最強である。"
				+ "醤油も味噌も塩もまあ悪くはないが、"
				+ "豚骨の前では等しく劣等料理でしかない。麻痺3ターン付与50%";

		return str;
	}
}

class Fukuoka2 extends Skill {
	Fukuoka2() {
		super("バリカタ以外認めない!", 19, blow, 50, 140, false, 0); // 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
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
	public String toString(){
		String str = "福岡県民ならバリカタ一択。粉落とし？" +
				"なにそれ、おいしいの？3ターン防御1.5倍";

		return str;
	}
}

// /////////大分
class Oita1 extends Skill {
	Oita1() {
		super("地獄をみせてやろう", 39, blow, 67, 150, false, 0); // 名前、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
		status_management[0][0] = 150;
		status_management[0][1] = 0;
		status_management[0][2] = 3;
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
		String str = "脅威のワンダーランド「ザ・地獄」。" +
				"地獄めぐりに行きたい。3ターン攻撃1.5倍";

		return str;
	}
}

class Oita2 extends Skill {
	Oita2() {
		super("ゆけ！高崎！", 17, slash, 40, 110, false, 0); // 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
		condition_add[3][0] = 70;
		condition_add[3][1] = 3; // 気絶
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
		String str = "高崎山自然動物園は野生のニホンザルを餌付けています。気絶3ターン付与70%";

		return str;
	}
}

// /////////佐賀
class Saga1 extends Skill {
	Saga1() {
		super("バルーンフェスタ！", 72, spear, 50, 120, false, 0); // 名前、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
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
		String str = "佐賀である熱気球の大会です。秋ごろにあるのでぜひお越しください！";

		return str;
	}
}

class Saga2 extends Skill {
	Saga2() {
		super("飛龍窯", 23, blow, 50, 140, false, 0); // 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
		status_management[4][0] = 200;
		status_management[4][1] = 0;
		status_management[4][2] = 3; // AGI
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
		String str = "「ひりゅうがま」と呼びます。" +
				"一度に12万個の湯飲みを焼成することができる巨大な登り窯です。3ターン回避2倍";

		return str;
	}
}

// /////////長崎
class Nagasaki1 extends Skill {
	Nagasaki1() {
		super("カステラはいかがですか？", 30, slash, 50, 120, false, 0); // 名前、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
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
		String str = "長崎といえばカステラですね。" +
				"室町時代の末期、南蛮船により日本に来たらしいです。";

		return str;
	}
}

class Nagasaki2 extends Skill {
	Nagasaki2() {
		super("イギリスはいかがですか？", 23, spear, 50, 130, false, 0); // 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
		status_management[1][0] = 0;
		status_management[1][1] = 50;
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
	public String toString(){
		String str = "海草を使った料理です。これという特徴のある味はない。3ターン相手の防御0.5倍";

		return str;
	}
}

// /////////熊本
class Kumamoto1 extends Skill {
	Kumamoto1() {
		super("阿蘇の恵み", 13, blow, 40, 110, false, 50); // 名前、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
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
		String str = "世界最大級のカルデラ「阿蘇山」。" +
				"「火の国」熊本県のシンボル的な存在として親しまれています。ダメージの50%ドレイン";

		return str;
	}
}

class Kumamoto2 extends Skill {
	Kumamoto2() {
		super("馬刺しうまか～！	", 67, spear, 50, 140, false, 0); // 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
		condition_add[5][0] = 80;
		condition_add[5][1] = 3; // 燃焼
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
		String str = "馬刺しといえば熊本ですね。" +
				"熊本は馬肉生産量の4割を占めており日本一の産地です。燃焼3ターン付与80%";

		return str;
	}
}

// /////////宮崎
class Miyazaki1 extends Skill {
	Miyazaki1() {
		super("サボテン投擲", 70, slash, 40, 110, false, 0); // 名前、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
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
		String str = "投擲は「とうてき」と呼びます。サボテン公園もありますよ。";

		return str;
	}
}

class Miyazaki2 extends Skill {
	Miyazaki2() {
		super("こどものくに", 8, spear, 50, 140, false, 0); // 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
		status_management[0][0] = 0;
		status_management[0][1] = 50;
		status_management[0][2] = 3; // ATK 0 -> 味方バフ倍率,
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
		String str = "宮崎にある遊園地です。なぜ相手にデバフを…。3ターン相手の攻撃0.5倍";

		return str;
	}
}

// /////////鹿児島

//////////////////////関西////////////////////////////////////////
/////三重

/////滋賀
class siga1 extends Skill{
	siga1(){
		super("一級河川：琵琶湖",22, blow, 50, 120,false,0);// 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
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
		String str = "実は一級河川です。";

		return str;
	}
}
class siga2 extends Skill{
	siga2(){
		super("甲賀の血",36, slash, 40, 130,false,0);// 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
	}
	boolean inRange(int[] posi,int direction, int x, int y) {
		if(direction == Piece.up){
			if(posi[1] == y+1 && Math.abs(posi[0] - x) <= 2) return true;
		}else if(direction == Piece.right){
			if(posi[0] == x-1 && Math.abs(posi[1] - y) <= 2) return true;
		}else if(direction == Piece.down){
			if(posi[1] == y-1 && Math.abs(posi[0] - x) <= 2) return true;
		}else if(direction == Piece.left){
			if(posi[0] == x+1 && Math.abs(posi[1] - y) <= 2) return true;
		}
		return false;
	}
	boolean attackRange(int direction,int[] target_posi,int x, int y) {
		if(direction == Piece.up){
			if( (target_posi[0] == x && target_posi[1] == y) ||
					target_posi[1] -1 == y &&  Math.abs(target_posi[0] - x) <=1)
				return true;
		}else if(direction == Piece.right){
			if( (target_posi[0] == x && target_posi[1] == y) ||
					target_posi[0] +1 == x &&  Math.abs(target_posi[1] - y) <=1)
				return true;
		}else if(direction == Piece.down){
			if( (target_posi[0] == x && target_posi[1] == y) ||
					target_posi[1] +1 == y &&  Math.abs(target_posi[0] - x) <=1)
				return true;
		}else if(direction == Piece.left){
			if( (target_posi[0] == x && target_posi[1] == y) ||
					target_posi[0] -1 == x &&  Math.abs(target_posi[1] - y) <=1) {
				return true;
			}
		}
		return false;
	}
	public String toString(){
		String str = "忍者の国でした。";

		return str;
	}

}
/////京都
class kyouto1 extends Skill{
	kyouto1(){
		super("古都の誇り",25, blow, 50, 140,false,0);// 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
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
		String str = "都の誇りがあるんです。";

		return str;
	}
}
class kyouto2 extends Skill{
	kyouto2(){
		super("碁盤の目の陣",63, spear, 50, 130,false,0);// 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
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
		String str = "京都市内の道は碁盤目状です。";

		return str;
	}
}
/////大阪
class oosaka1 extends Skill{
	oosaka1(){
		super("新世界の中心：通天閣",3, slash, 50, 140,false,0);// 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
	}
	boolean inRange(int[] posi,int direction, int x, int y) {
		if(direction == Piece.up){
			if(Math.abs(posi[0] - x) <= 1 && posi[1] > y && Math.abs(posi[1] - y) <=3) return true;
		}else if(direction == Piece.right){
			if(Math.abs(posi[1] - y) <= 1 && posi[0] < x && Math.abs(posi[0] - x) <=3) return true;
		}else if(direction == Piece.down){
			if(Math.abs(posi[0] - x) <= 1 && posi[1] < y && Math.abs(posi[1] - y) <=3) return true;
		}else if(direction == Piece.left){
			if(Math.abs(posi[1] - y) <= 1 && posi[0] > x && Math.abs(posi[0] - x) <=3) return true;
		}
		return false;
	}
	boolean attackRange(int direction,int[] target_posi,int x, int y) {
		if(Math.abs(target_posi[0] - x) <= 1 && Math.abs(target_posi[1] - y) <= 1) return true;
		return false;
	}
	public String toString(){
		String str = "新世界は繁華街です。";

		return str;
	}
}
class oosaka2 extends Skill{
	oosaka2(){
		super("尋常ならざる漫才",8, spear, 50, 130,false,0);// 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
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
		String str = "大阪＝漫才という偏見。";

		return str;
	}
}
/////兵庫
class hyougo1 extends Skill{
	hyougo1(){
		super("原初の地：淡路島",72, blow, 50, 120,false,0);// 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
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
		String str = "イザナギ・イザナミが降り立った地。";

		return str;
	}
}
class hyougo2 extends Skill{
	hyougo2(){
		super("復興の祭典：ルミナリエ",5, slash, 50, 120,false,0);// 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
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
		String str = "鎮魂の意味を込めたイベントです。";

		return str;
	}
}
/////奈良
class nara1 extends Skill{
	nara1(){
		super("召喚術：鹿",18, blow, 40, 110,false,0);// 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
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
		String str = "私の愛鹿は凶暴です。";

		return str;
	}
}
class nara2 extends Skill{
	nara2(){
		super("六十華厳：盧舎那仏",33, slash, 50, 130,false,0);// 名前、エフェクトID、属性、消費SP、スキル倍率(%)、支援技かどうか、ドレイン率(%)
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
		String str = "ろくじゅうけごん：るしゃなぶつ";

		return str;
	}
}/////和歌山

// //////////////////////////////////////////////////////////////////////////////////////////////////////////////山下作成
// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////以下地理作

class simane1 extends Skill {
	simane1() {
		super("出雲大社の力", 21, blow, 50, 130, false, 0); // 名前、エフェクトid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
		status_management[0][0] = 130;
		status_management[0][1] = 0;
		status_management[0][2] = 2; // ATK 0 -> 自バフ倍率,
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
		String str = "祭神は「大国主大神」。2ターン攻撃1.3倍";

		return str;
	}
}

// ////
class simane2 extends Skill {
	simane2() {
		super("遊覧船アタック", 4, slash, 40, 110, false, 0); // 名前、エフェクトid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "○○アタックって名前を付けたかった。";

		return str;
	}
}

// //////
class yamaguti1 extends Skill {
	yamaguti1() {
		super("秋吉台", 69, spear, 40, 110, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "地理的にいいところです。";

		return str;
	}
}

// ////
class yamaguti2 extends Skill {
	yamaguti2() {
		super("錦川水の祭典", 53, spear, 50, 120, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
		condition_add[5][0] = 30;
		condition_add[5][1] = 3; // 燃焼
	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if (direction == Piece.up) {
			if (posi[1] == y + 1 && Math.abs(posi[0] - x) <= 2)
				return true;
		} else if (direction == Piece.right) {
			if (posi[0] == x - 1 && Math.abs(posi[1] - y) <= 2)
				return true;
		} else if (direction == Piece.down) {
			if (posi[1] == y - 1 && Math.abs(posi[0] - x) <= 2)
				return true;
		} else if (direction == Piece.left) {
			if (posi[0] == x + 1 && Math.abs(posi[1] - y) <= 2)
				return true;
		}
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (direction == Piece.up) {
			if ((target_posi[0] == x && target_posi[1] == y)
					|| target_posi[1] - 1 == y
					&& Math.abs(target_posi[0] - x) <= 1)
				return true;
		} else if (direction == Piece.right) {
			if ((target_posi[0] == x && target_posi[1] == y)
					|| target_posi[0] + 1 == x
					&& Math.abs(target_posi[1] - y) <= 1)
				return true;
		} else if (direction == Piece.down) {
			if ((target_posi[0] == x && target_posi[1] == y)
					|| target_posi[1] + 1 == y
					&& Math.abs(target_posi[0] - x) <= 1)
				return true;
		} else if (direction == Piece.left) {
			if ((target_posi[0] == x && target_posi[1] == y)
					|| target_posi[0] - 1 == x
					&& Math.abs(target_posi[1] - y) <= 1) {
				return true;
			}
		}
		return false;
	}
	public String toString(){
		String str = "花火大会らしいです。燃焼3ターン付与30%";

		return str;
	}
}

// ///////

// /////
class hirosima1 extends Skill {
	hirosima1() {
		super("大和の魂!!", 39, blow, 50, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "呉市の大和ミュージアムに行きたい。";

		return str;
	}
}

// //////
class hirosima2 extends Skill {
	hirosima2() {
		super("東洋カープ!!", 38, blow, 50, 120, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "私はホークス派です。";

		return str;
	}
}

// /////////
class okayama1 extends Skill {
	okayama1() {
		super("桃太郎伝説", 6, blow, 50, 120, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "桃太郎の町ですよ～";

		return str;
	}
}

// ///////
class okayama2 extends Skill {
	okayama2() {
		super("備前長船の刀", 20, slash, 50, 120, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if (direction == Piece.up) {
			if (posi[1] == y + 1 && Math.abs(posi[0] - x) <= 2)
				return true;
		} else if (direction == Piece.right) {
			if (posi[0] == x - 1 && Math.abs(posi[1] - y) <= 2)
				return true;
		} else if (direction == Piece.down) {
			if (posi[1] == y - 1 && Math.abs(posi[0] - x) <= 2)
				return true;
		} else if (direction == Piece.left) {
			if (posi[0] == x + 1 && Math.abs(posi[1] - y) <= 2)
				return true;
		}
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (direction == Piece.up) {
			if ((target_posi[0] == x && target_posi[1] == y)
					|| target_posi[1] - 1 == y
					&& Math.abs(target_posi[0] - x) <= 1)
				return true;
		} else if (direction == Piece.right) {
			if ((target_posi[0] == x && target_posi[1] == y)
					|| target_posi[0] + 1 == x
					&& Math.abs(target_posi[1] - y) <= 1)
				return true;
		} else if (direction == Piece.down) {
			if ((target_posi[0] == x && target_posi[1] == y)
					|| target_posi[1] + 1 == y
					&& Math.abs(target_posi[0] - x) <= 1)
				return true;
		} else if (direction == Piece.left) {
			if ((target_posi[0] == x && target_posi[1] == y)
					|| target_posi[0] - 1 == x
					&& Math.abs(target_posi[1] - y) <= 1) {
				return true;
			}
		}
		return false;
	}
	public String toString(){
		String str = "重要文化財の作刀があります。";

		return str;
	}
}

// ////////
class aomori1 extends Skill {
	aomori1() {
		super("津軽海峡だ!", 46, blow, 67, 150, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
		status_management[1][0] = 130;
		status_management[1][1] = 0;
		status_management[1][2] = 2; // DEF 1 -> 敵デバフ倍率,
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
		String str = "石川さゆりさんを思い出しますね。2ターン防御1.3倍";

		return str;
	}
}

// ////////
class aomori2 extends Skill {
	aomori2() {
		super("昭和大仏の力だ!", 1, slash, 50, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "青銅座像としては日本一大きな大仏です。";

		return str;
	}
}

// ////////

// ////////
class akita1 extends Skill {
	akita1() {
		super("なまはげさんの怒り", 27, spear, 50, 140, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
		status_management[0][0] = 130;
		status_management[0][1] = 0;
		status_management[0][2] = 2; // ATK 0 -> 自バフ倍率,
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
		String str = "泣く子はいねがー、親の言うこど聞がね子はいねがー。2ターン攻撃1.3倍";

		return str;
	}

}

// //////////
class akita2 extends Skill {
	akita2() {
		super("くまくま園のくまさん", 16, spear, 50, 120, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "阿仁（あに）熊牧場にあるそうです。";

		return str;
	}
}

// //////////
class miyagi1 extends Skill {
	miyagi1() {
		super("伊達政宗の真の力", 54, spear, 50, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "伊達政宗は本当に目を失明していたのか？";

		return str;
	}
}

// ///////////
class miyagi2 extends Skill {
	miyagi2() {
		super("一目千本桜", 45, blow, 50, 140, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "千本桜といえば…ボカロですかね。";

		return str;
	}
}

// ///////////
class yamagata1 extends Skill {
	yamagata1() {
		super("古竜湖の水", 66, blow, 50, 120, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "古竜湖でキャンプもいいいですね。";

		return str;
	}
}

// //////////
class yamagata2 extends Skill {
	yamagata2() {
		super("雪の降る町", 15, slash, 50, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "東北だったらどこでも雪ぐらい降りますよね…";

		return str;
	}
}

// ////////////
class hukusima1 extends Skill {
	hukusima1() {
		super("ずんだ餅でも食べませんか？", 34, blow, 50, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "すりつぶした枝豆を餡に用いる餅菓子です。";

		return str;
	}
}

// ////////////
class hukusima2 extends Skill {
	hukusima2() {
		super("これが、野口さんの力です!", 14, blow, 50, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "これが、野口さん（お金）の力です！";

		return str;
	}
}
// ///////////愛知
class aiti1 extends Skill {
	aiti1() {
		super("工業力の愛知", 3, slash, 50, 140, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
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
		String str = "トヨタ力はすごいですよね。";

		return str;
	}

}

class aiti2 extends Skill {
	aiti2() {
		super("倍返し神社", 14, blow, 50, 120, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
		status_management[0][0] = 150;
		status_management[0][1] = 0;
		status_management[0][2] = 2; // ATK
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
		String str = "金を洗うとなんと倍以上になって返ってくる。2ターン攻撃1.5倍";

		return str;
	}
}

// /////静岡

// /////岐阜
class gihu1 extends Skill {
	gihu1() {
		super("行け！鵜", 19, blow, 50, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
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
		String str = "アユとか魚を取ります。";

		return str;
	}
}

class gihu2 extends Skill {
	gihu2() {
		super("天下分け目の決戦", 32, slash, 50, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
		status_management[0][0] = 130;
		status_management[0][1] = 0;
		status_management[0][2] = 3; // ATK
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
		String str = "関ヶ原ですね。3ターン攻撃1.3倍";

		return str;
	}
}

// /////新潟
class niigata1 extends Skill {
	niigata1() {
		super("恋人の聖地:カリオンタワー", 51, spear, 50, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
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
		String str = "恋人の聖地ですか（遠い目）";

		return str;
	}
}

class niigata2 extends Skill {
	niigata2() {
		super("美人林", 13, spear, 50, 120, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
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
		String str = "美人（くらい美しい）林";

		return str;
	}
}

// /////福井
class hukui1 extends Skill {
	hukui1() {
		super("天空の城：越前大野城", 26, blow, 50, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if (direction == Piece.up) {
			if (posi[1] == y + 1 && Math.abs(posi[0] - x) <= 2)
				return true;
		} else if (direction == Piece.right) {
			if (posi[0] == x - 1 && Math.abs(posi[1] - y) <= 2)
				return true;
		} else if (direction == Piece.down) {
			if (posi[1] == y - 1 && Math.abs(posi[0] - x) <= 2)
				return true;
		} else if (direction == Piece.left) {
			if (posi[0] == x + 1 && Math.abs(posi[1] - y) <= 2)
				return true;
		}
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (direction == Piece.up) {
			if ((target_posi[0] == x && target_posi[1] == y)
					|| target_posi[1] - 1 == y
					&& Math.abs(target_posi[0] - x) <= 1)
				return true;
		} else if (direction == Piece.right) {
			if ((target_posi[0] == x && target_posi[1] == y)
					|| target_posi[0] + 1 == x
					&& Math.abs(target_posi[1] - y) <= 1)
				return true;
		} else if (direction == Piece.down) {
			if ((target_posi[0] == x && target_posi[1] == y)
					|| target_posi[1] + 1 == y
					&& Math.abs(target_posi[0] - x) <= 1)
				return true;
		} else if (direction == Piece.left) {
			if ((target_posi[0] == x && target_posi[1] == y)
					|| target_posi[0] - 1 == x
					&& Math.abs(target_posi[1] - y) <= 1) {
				return true;
			}
		}
		return false;
	}
	public String toString(){
		String str = "ラピュタじゃないよ。";

		return str;
	}
}

class hukui2 extends Skill {
	hukui2() {
		super("白川郷", 64, blow, 50, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
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
		String str = "合掌造りが素晴らしい。";

		return str;
	}

}

// /////長野
class nagano1 extends Skill {
	nagano1() {
		super("地獄の谷野猿", 17, slash, 50, 140, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
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
		String str = "温泉に浸かる猿が見れます。";

		return str;
	}


}

class nagano2 extends Skill {
	nagano2() {
		super("寝覚めの床", 71, slash, 50, 120, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
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
		String str = "浦島太郎が弁才天像を残したと言われています。";

		return str;
	}

}

// /////石川
class isikawa1 extends Skill {
	isikawa1() {
		super("加賀100万石の力", 36, slash, 70, 0, true, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
		status_management[0][0] = 150;
		status_management[0][1] = 0;
		status_management[0][2] = 2; // ATK 0 -> 味方バフ倍率,
		status_management[1][0] = 150;
		status_management[1][1] = 0;
		status_management[1][2] = 2; // DEF 1 -> 敵デバフ倍率,
		status_management[6][0] = 150;
		status_management[6][1] = 0;
		status_management[6][2] = 2; // LUCK
		status_management[7][0] = 150;
		status_management[7][1] = 0;
		status_management[7][2] = 2; // MOVE
	}

	boolean inRange(int[] posi, int direction, int x, int y) {
		if(posi[0] == x && posi[1] == y) return true;
		return false;
	}

	boolean attackRange(int direction, int[] target_posi, int x, int y) {
		if (target_posi[0] == x && target_posi[1] == y)return true;
		return false;
	}
	public String toString(){
		String str = "外様でありながら、御三家に準ずる待遇を受けたそうです。2ターン攻撃、防御、運、移動1.5倍";

		return str;
	}

}

class isikawa2 extends Skill {
	isikawa2() {
		super("スイミング・プール", 74, blow, 50, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
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
		String str = "ちょっと変わった芸術作品です。";

		return str;
	}
}

// /////山梨
class yamanasi1 extends Skill {
	yamanasi1() {
		super("ほったらかし回復", -1, normal, 70, 250, true, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
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
		String str = "ほったらかし温泉ということで。味方のHP250回復";

		return str;
	}
}

class yamanasi2 extends Skill {
	yamanasi2() {
		super("富士急ハイアタック", 62, slash, 50, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
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
		String str = "○○アタック第二弾。";

		return str;
	}
}

// /////富山

// ///////////
class tokusima1 extends Skill {
	tokusima1() {
		super("鳴門の渦潮", 55, slash, 50, 120, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "大潮の際には渦の直径は最大で30mに達するそうです。";

		return str;
	}
}

// ///////////
class tokusima2 extends Skill {
	tokusima2() {
		super("もみじ亭のおそば", -1, normal, 50, 200, true, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "徳島県の観光地の1つ。味方のHP200回復";

		return str;
	}
}

// ///////////
class kouti1 extends Skill {
	kouti1() {
		super("坂本龍馬よ、力を借せ！", 61, slash, 40, 110, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "大河ドラマでさらに有名に。";

		return str;
	}
}

// ///////////
class kouti2 extends Skill {
	kouti2() {
		super("伊尾木洞", 32, spear, 40, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "波の浸食することにより出来た天然の洞です。";

		return str;
	}
}

// ///////////
class kagawa1 extends Skill {
	kagawa1() {
		super("こんぴらさん", 31, spear, 40, 110, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
		status_management[0][0] = 150;
		status_management[0][2] = 3;
		status_management[1][0] = 150;
		status_management[1][2] = 3;
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
		String str = "こんぴらさんは「大物主神」を祀っています。3ターン攻撃、防御1.5倍";

		return str;
	}
}

// ///////////
class kagawa2 extends Skill {
	kagawa2() {
		super("天使の住む丘", 60, slash, 67, 150, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "町の夜景が素晴らしい。";

		return str;
	}
}

// ///////////


// ///////////
class tiba1 extends Skill {
	tiba1() {
		super("東京ディズニーランド、それは千葉のものだ！", 9, blow, 67, 180, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

		status_management[6][0] = 50;
		status_management[6][2] = 3; // LUCK
		status_management[7][0] = 200;
		status_management[7][2] = 4; // MOVE
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
		String str = "某ねずみの国はややこしい名前してますね。3ターン運0.5倍、4ターン移動2倍";

		return str;
	}

}

// ///////////
class tiba2 extends Skill {
	tiba2() {
		super("MAX缶こそ至高", 33, slash, 50, 150, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
		condition_add[0][0] = 40;
		condition_add[0][1] = 3;

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
		String str = "練乳入りのコーヒーで、甘くて最高です。毒3ターン付与40%";

		return str;
	}

}

// ///////////
class kanagawa1 extends Skill {

	kanagawa1() {
		super("要塞：猿島", 76, spear, 67, 150, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "ショッカーの基地があるらしいです。";

		return str;
	}

}

// ///////////
class kanagawa2 extends Skill {

	kanagawa2() {
		super("横浜中華街の肉まん", -1, normal, 50, 300, true, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "安くてウマい！横浜中華街。味方のHP300回復";

		return str;
	}

}

// ///////////

// ///////////
class gunma1 extends Skill {

	gunma1() {
		super("榛名湖の美しさ", 66, blow, 50, 120, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "イルミネーションが季節によってはありますよ。";

		return str;
	}

}

// ///////////
class gunma2 extends Skill {

	gunma2() {
		super("鬼押出し", 57, blow, 50, 140, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "浅間山の噴火の際に流れ出た溶岩です。";

		return str;
	}

}

// ///////////
class totigi1 extends Skill {

	totigi1() {
		super("巨大迷宮：パラディアム", 47, blow, 50, 140, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
		condition_add[0][0] = 60;
		condition_add[0][1] = 3;

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
		String str = "なんと迷路面積約3,500m2。毒3ターン付与60%";

		return str;
	}

}

// ///////////
class totigi2 extends Skill {

	totigi2() {
		super("竜化", 68, spear, 50, 120, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "竜化の滝の話で有名ですね。";

		return str;
	}

}

// ///////////
class ibaragi1 extends Skill {

	ibaragi1() {
		super("これがJAXA力!!!", 52, blow, 50, 140, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "筑波宇宙センターより。";

		return str;
	}

}

// ///////////
class ibaragi2 extends Skill {

	ibaragi2() {
		super("納豆餅喰え！", 35, blow, 50, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

		condition_add[0][0] = 80;
		condition_add[0][1] = 3;

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
		String str = "納豆といえば水戸納豆！毒3ターン付与80%";

		return str;
	}

}

// ///////////
class toukyou1 extends Skill {

	toukyou1() {
		super("首都の誇り", 73, slash, 50, 150, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

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
		String str = "便利な町ですよねぇ～";

		return str;
	}
}

// ///////////
class toukyou2 extends Skill {

	toukyou2() {
		super("スカイツリーの避雷針", 27, spear, 50, 140, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率

		status_management[0][0] = 150;
		status_management[0][2] = 3;
		status_management[1][0] = 150;
		status_management[1][2] = 3;
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
		String str = "3ターン攻撃、防御1.5倍";

		return str;
	}

}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////tiri
class hokkaidou1 extends Skill {
	hokkaidou1() {
		super("十勝魂", 3, slash, 50, 200, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
		status_management[0][0] = 150;
		status_management[0][1] = 0;
		status_management[0][2] = 3; // ATK   0 -> 自バフ倍率,
	}

	boolean inRange(int[] posi,int direction, int x, int y) {
		if(direction == Piece.up){
			if(Math.abs(posi[0] - x) <= 1 && posi[1] > y && Math.abs(posi[1] - y) <=3) return true;
		}else if(direction == Piece.right){
			if(Math.abs(posi[1] - y) <= 1 && posi[0] < x && Math.abs(posi[0] - x) <=3) return true;
		}else if(direction == Piece.down){
			if(Math.abs(posi[0] - x) <= 1 && posi[1] < y && Math.abs(posi[1] - y) <=3) return true;
		}else if(direction == Piece.left){
			if(Math.abs(posi[1] - y) <= 1 && posi[0] > x && Math.abs(posi[0] - x) <=3) return true;
		}
		return false;
	}
	boolean attackRange(int direction,int[] target_posi,int x, int y) {
		if(Math.abs(target_posi[0] - x) <= 1 && Math.abs(target_posi[1] - y) <= 1) return true;
		return false;
	}
	public String toString(){
		String str = "十勝って、なんだかおいしそうなイメージがあります。3ターン攻撃1.5倍";

		return str;
	}
}
//////////////
class hokkaidou2 extends Skill {
	hokkaidou2() {
		super("白い好敵手", 41, spear, 50, 150, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
		condition_add[2][0] = 50;
		condition_add[2][1] = 3; // 凍結
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
		String str = "白い○○より。凍結3ターン付与50%";

		return str;
	}
}
//////////////////
class hokkaidou3 extends Skill {
	hokkaidou3() {
		super("オホーツク海の流氷", 58, blow, 50, 130, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
		status_management[4][0] = 0; status_management[4][1] = 75; status_management[4][2] = 3;  // AGI
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
		String str = "美しい景色です。3ターン相手の回避0.75倍";

		return str;
	}
}
//////////////////
class okinawa1 extends Skill {
	okinawa1() {
		super("琉球王国の意地", 43, blow, 50, 150, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
		status_management[1][0] = 150;
		status_management[1][1] = 0;
		status_management[1][2] = 3; // DEF    1 -> 敵デバフ倍率,
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
		String str = "首里城などで有名です。3ターン防御1.5倍";

		return str;
	}
}
//////////////////
class okinawa2 extends Skill {
	okinawa2() {
		super("ナゴ☆パイナップル", 20, slash, 50, 150, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
		condition_add[1][0] = 50;
		condition_add[1][1] = 3; // 麻痺
	}

	boolean inRange(int[] posi,int direction, int x, int y) {
		if(direction == Piece.up){
			if(Math.abs(posi[0] - x) <= 1 && posi[1] > y && Math.abs(posi[1] - y) <=3) return true;
		}else if(direction == Piece.right){
			if(Math.abs(posi[1] - y) <= 1 && posi[0] < x && Math.abs(posi[0] - x) <=3) return true;
		}else if(direction == Piece.down){
			if(Math.abs(posi[0] - x) <= 1 && posi[1] < y && Math.abs(posi[1] - y) <=3) return true;
		}else if(direction == Piece.left){
			if(Math.abs(posi[1] - y) <= 1 && posi[0] > x && Math.abs(posi[0] - x) <=3) return true;
		}
		return false;
	}
	boolean attackRange(int direction,int[] target_posi,int x, int y) {
		if(Math.abs(target_posi[0] - x) <= 1 && Math.abs(target_posi[1] - y) <= 1) return true;
		return false;
	}
	public String toString(){
		String str = "パイナップルのテーマパークもあるそうです。麻痺3ターン付与50%";

		return str;
	}
}
//////////////////
class okinawa3 extends Skill {
	okinawa3() {
		super("ビオスの牛", 5, spear, 50, 0, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
		status_management[7][0] = 0;
		status_management[7][1] = 50;
		status_management[7][2] = 3; // MOVE
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
		String str = "湖水鑑賞がお勧めです。3ターン相手の移動0.5倍";

		return str;
	}

}
//////////////////
//////////////////デフォルト用のスキル
class waza1 extends Skill {
	waza1() {
		super("デフォ的な技", 42, spear, 40, 100, false, 0); // 名前、スキルid、属性、消費SP、スキル倍率、支援技かどうか、ドレイン倍率
	}

	boolean inRange(int[] posi,int direction, int x, int y) {
		if(direction == Piece.up){
			if(Math.abs(posi[0] - x) <= 1 && posi[1] > y && Math.abs(posi[1] - y) <=3) return true;
		}else if(direction == Piece.right){
			if(Math.abs(posi[1] - y) <= 1 && posi[0] < x && Math.abs(posi[0] - x) <=3) return true;
		}else if(direction == Piece.down){
			if(Math.abs(posi[0] - x) <= 1 && posi[1] < y && Math.abs(posi[1] - y) <=3) return true;
		}else if(direction == Piece.left){
			if(Math.abs(posi[1] - y) <= 1 && posi[0] > x && Math.abs(posi[0] - x) <=3) return true;
		}
		return false;
	}
	boolean attackRange(int direction,int[] target_posi,int x, int y) {
		if(Math.abs(target_posi[0] - x) <= 1 && Math.abs(target_posi[1] - y) <= 1) return true;
		return false;
	}
	public String toString(){
		String str = "デフォです。";
		return str;
	}

}
