package main;


public class SkillList {
		Skill[] skills;
		SkillList( int id){
			int skill_length = 0;
//			if(id==0){
//				skill_length = 2;
//				skills = new Skill[skill_length];
//				skills[0] = new Defo();
//				skills[1] = new Ouka();
//			}else if(id==1){
//				skill_length = 2;
//				skills = new Skill[skill_length];
//				skills[0] = new Defo();
//				skills[1] = new Ouka();
//			}else if(id==2){
//				skill_length = 5;
//				skills = new Skill[skill_length];
//				skills[0] = new Defo();
//				skills[1] = new Ouka();
//				skills[2] = new Kaiten();
//				skills[3] = new Takamakuri();
//				skills[4] = new Snatch();
//			}else if(id==3){
//				skill_length = 6;
//				skills = new Skill[skill_length];
//				skills[0] = new Defo();
//				skills[1] = new Range1();
//				skills[2] = new Range2();
//				skills[3] = new Range4();
//				skills[4] = new Range5();
//				skills[5] = new Range6();
//			}

			if (id == 1) {
				skill_length = 4;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new hokkaidou1();
				skills[2] = new hokkaidou2();
				skills[3] = new hokkaidou3();
			}
			else if(id==2){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new aomori1();
				skills[2] = new aomori2();
			}
			else if(id==3){
				skill_length = 2;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new waza1();
				//skills[1] = new iwate1();
				//skills[2] = new iwate2();
			}
			else if(id==4){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new miyagi1();
				skills[2] = new miyagi2();
			}
			else if(id==5){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new akita1();
				skills[2] = new akita2();
			}
			else if(id==6){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new yamagata1();
				skills[2] = new yamagata2();
			}
			else if(id==7){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new hukusima1();
				skills[2] = new hukusima2();
			}
			else if(id==8){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new ibaragi1();
				skills[2] = new ibaragi2();
			}
			else if(id==9){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new totigi1();
				skills[2] = new totigi2();
			}
			else if(id==10){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new gunma1();
				skills[2] = new gunma2();
			}
			else if(id==11){
				skill_length = 2;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new waza1();
				//skills[1] = new saitama1();
				//skills[2] = new saitama2();
			}
			else if(id==12){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new tiba1();
				skills[2] = new tiba2();
			}
			else if(id==13){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new toukyou1();
				skills[2] = new toukyou2();
			}
			else if(id==14){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new kanagawa1();
				skills[2] = new kanagawa2();
			}
			else if(id==15){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new niigata1();
				skills[2] = new niigata2();
			}
			else if(id==16){
				skill_length = 2;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new waza1();
				//skills[1] = new toyama1();
				//skills[2] = new toyama2();
			}
			else if(id==17){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new isikawa1();
				skills[2] = new isikawa2();
			}
			else if(id==18){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new hukui1();
				skills[2] = new hukui2();
			}
			else if(id==19){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new yamanasi1();
				skills[2] = new yamanasi2();
			}
			else if(id==20){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new nagano1();
				skills[2] = new nagano2();
			}else if(id==21){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new gihu1();
				skills[2] = new gihu2();
			}else if(id==22){
				skill_length = 2;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new waza1();
				//skills[1] = new sizuoka1();
				//skills[2] = new sizuoka2();
			}else if(id==23){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new aiti1();
				skills[2] = new aiti2();
			}
			else if(id==24){
				skill_length = 2;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new waza1();
				//skills[1] = new mie1();
				//skills[2] = new mie2();
			}
			else if(id==25){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new siga1();
				skills[2] = new siga2();
			}
			else if(id==26){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new kyouto1();
				skills[2] = new kyouto2();
			}
			else if(id==27){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new oosaka1();
				skills[2] = new oosaka2();
			}
			else if(id==28){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new hyougo1();
				skills[2] = new hyougo2();
			}
			else if(id==29){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new nara1();
				skills[2] = new nara2();
			}
			else if(id==30){
				skill_length = 2;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new waza1();
				//skills[1] = new wakayama1();
				//skills[2] = new wakayama2();
			}
			else if(id==31){
				skill_length = 2;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new waza1();
				//skills[1] = new tottori1();
				//skills[2] = new tottori2();
			}
			else if(id==32){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new simane1();
				skills[2] = new simane2();
			}
			else if(id==33){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new okayama1();
				skills[2] = new okayama2();
			}
			else if(id==34){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new hirosima1();
				skills[2] = new hirosima2();
			}
			else if(id==35){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new yamaguti1();
				skills[2] = new yamaguti2();
			}
			else if(id==36){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new tokusima1();
				skills[2] = new tokusima2();
			}
			else if(id==37){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new kagawa1();
				skills[2] = new kagawa2();
			}
			else if(id==38){
				skill_length = 2;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new waza1();
				//skills[1] = new ehime1();
				//skills[2] = new ehime2();
			}
			else if(id==39){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new kouti1();
				skills[2] = new kouti2();
			}
			else if(id==40){   //福岡
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new Fukuoka1();
				skills[2] = new Fukuoka2();
			}else if(id==41){   //佐賀
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new Saga1();
				skills[2] = new Saga2();
			}else if(id==42){   //長崎
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new Nagasaki1();
				skills[2] = new Nagasaki2();
			}else if(id==43){   //熊本
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new Kumamoto1();
				skills[2] = new Kumamoto2();
			}else if(id==44){   //大分
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new Oita1();
				skills[2] = new Oita2();
			}else if(id==45){   //宮崎
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new Miyazaki1();
				skills[2] = new Miyazaki2();
			}else if(id==46){   //鹿児島
				skill_length = 2;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new waza1();
				//skills[1] = new Kagoshima1();
				//skills[2] = new Kagoshima2();
				//skills[3] = new Fukuoka1();
			}
			else if (id == 47) {
				skill_length = 4;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new okinawa1();
				skills[2] = new okinawa2();
				skills[3] = new okinawa3();
			}


		}
}

//////////////////////////////////////////////////////////////////////////////////

class Range1 extends Skill{
	Range1(){
		super("レンジのデフォルト１",0, 0,0, 0,false,0);
		status_management[0][0] = 0; status_management[0][1] = 0; status_management[0][2] = 0;  // ATK   0 -> 味方バフ倍率,
		status_management[1][0] = 0; status_management[1][1] = 0; status_management[1][2] = 0;  // DEF    1 -> 敵デバフ倍率,
		status_management[2][0] = 0; status_management[2][1] = 0; status_management[2][2] = 0;  // INT      2 -> ターン数
		status_management[3][0] = 0; status_management[3][1] = 0; status_management[3][2] = 0;  // MEN
		status_management[4][0] = 0; status_management[4][1] = 0; status_management[4][2] = 0;  // AGI
		status_management[5][0] = 0; status_management[5][1] = 0; status_management[5][2] = 0;  // DEX
		status_management[6][0] = 0; status_management[6][1] = 0; status_management[6][2] = 0;  // LUCK
		status_management[7][0] = 0; status_management[7][1] = 0; status_management[7][2] = 0;  // MOVE

		condition_add[0][0] = 0; condition_add[0][1] = 0; // 毒   0 -> 確率, 1 -> ターン数
		condition_add[1][0] = 0; condition_add[1][1] = 0; // 麻痺
		condition_add[2][0] = 0; condition_add[2][1] = 0; // 凍結
		condition_add[3][0] = 0; condition_add[3][1] = 0; // 気絶
		condition_add[4][0] = 0; condition_add[4][1] = 0; // 混乱
		condition_add[5][0] = 0; condition_add[5][1] = 0; // 燃焼
		condition_add[6][0] = 0; condition_add[6][1] = 0; // 暗闇
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

}
class Range2 extends Skill{
	Range2(){
		super("レンジのデフォルト2",0, 0,0, 0,false,0);
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

}
class Range4 extends Skill{
	Range4(){
		super("レンジのデフォルト4",0, 0,0, 0,false,0);
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

}

class Range5 extends Skill{
	Range5(){
		super("レンジのデフォルト5",0, 0,0, 0,false,0);
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

}

class Range6 extends Skill{
	Range6(){
		super("レンジのデフォルト6",0, 0,0, 0,false,0);
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
}


