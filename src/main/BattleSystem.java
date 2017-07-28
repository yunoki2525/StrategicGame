package main;

import java.util.Arrays;
import java.util.Random;

public class BattleSystem {

	BattlePanel panel;

	int[][] map;
	int[][] field;
	int[][] move_map;
	Piece[] enemy;
	Piece[] friend;

	static final int nobody = 0;

	boolean PvP = false;
	boolean myTurn = true;

	public BattleSystem(BattlePanel panel, int[][] map, int[][] field,
			Piece[] enemy, Piece[] friend) {
		this.panel = panel;
		this.friend = Arrays.copyOf(friend, friend.length);
		this.enemy = Arrays.copyOf(enemy, enemy.length);
		this.map = new int[map.length][map[0].length];
		this.field = new int[field.length][field[0].length];
		this.move_map = new int[map.length][map[0].length];
		for (int i = 0; i < this.map.length; i++) {
			this.map[i] = Arrays.copyOf(map[i], map[i].length);
			this.field[i] = Arrays.copyOf(field[i], field[i].length);
			this.move_map[i] = Arrays.copyOf(map[i], map[i].length);
		}
		updateMap();
		initMoveMap();
	}

	public void updateMap() {

		for (int i = 0; i < enemy.length; i++) {
			map[enemy[i].position[1]][enemy[i].position[0]] = -1;
			// System.out.println("map["+enemy[i].position[1]+"]["+enemy[i].position[0]+"]に敵配置");
		}
		for (int i = 0; i < friend.length; i++) {
			map[friend[i].position[1]][friend[i].position[0]] = 1;
			// System.out.println("map["+friend[i].position[1]+"]["+friend[i].position[0]+"]に味方配置");
		}
		// System.out.println("system map");
		// for(int i=0 ; i<map.length ;
		// i++)System.out.println(Arrays.toString(this.map[i]));

		panel.updateMap(map);
	}


	public void attack(int attacker_idx, int receiver_idx, int skill_idx) {
		if (myTurn) {
			System.out.println(friend[attacker_idx].unit.name+"の攻撃");
			////////////////   SP消費     //////////////////////////////////////////////////////////////////////////////////
			//friend[attacker_idx].SP -= friend[attacker_idx].unit.skill[skill_idx].cost;
			//////////////////////////////////////////////////////////////////////////////////////////////////////
			////////  バフ  ////////
			for(int i=0 ; i<friend[attacker_idx].unit.skill[skill_idx].status_management.length ; i++){
				if(friend[attacker_idx].unit.skill[skill_idx].status_management[i][0] != 0)
					buff(attacker_idx, i, friend[attacker_idx].unit.skill[skill_idx].status_management[i][0],
							friend[attacker_idx].unit.skill[skill_idx].status_management[i][2]);
			}

			/////////  状態異常効果    ///////
			boolean a_darkness = (friend[attacker_idx].condition[Piece.ToLOVEru] > 0);
			boolean r_darkness = (enemy[receiver_idx].condition[Piece.ToLOVEru] > 0);
			boolean r_piyoORsleep =  (enemy[receiver_idx].condition[Piece.piyopiyo] > 0 || enemy[receiver_idx].condition[Piece.sleep] > 0 );

			if (hitJudge(friend[attacker_idx].DEX,friend[attacker_idx].unit.weapon.DEX, a_darkness,field[friend[attacker_idx].position[1]][friend[attacker_idx].position[0]],
					enemy[receiver_idx].AGI,enemy[receiver_idx].unit.weapon.AGI, r_darkness,field[enemy[receiver_idx].position[1]][enemy[receiver_idx].position[0]],
					friend[attacker_idx].LUCK  )  || r_piyoORsleep) {
				////////////////////////////////////////////////////////////////////////////////////
				panel.setScreenParameta_hit(true);
				////////////////////////////////////////////////////////////////////////////////////
				Random random = new Random();
				int r = 0;
				r = random.nextInt(31);
				int attack_point = (int) (friend[attacker_idx].ATK
						* friend[attacker_idx].unit.skill[skill_idx].power / 100);
				int DEFence_point = enemy[receiver_idx].DEF;
				System.out.println("attack_point :"+attack_point+"  DEFence_point  : "+DEFence_point);

				int basic_point = attack_point
						- (int) (DEFence_point * 0.5);
				int random_point = (int) (basic_point * (r - 15) / 100);
				System.out.println("basic_point :"+basic_point+"  random_point  : "+random_point);
				int basic_damage = basic_point + random_point;
				System.out.println("basic_damege :"+basic_damage);

				int damage = basic_damage;

				//////////  状態異常効果    //////////////
				if(enemy[receiver_idx].condition[Piece.piyopiyo] > 0 ){
					damage = damage * 3 / 2;
					enemy[receiver_idx].condition[Piece.piyopiyo] = 0;
					System.out.println("気絶時確定クリティカル。気絶回復。");
				}

				else if(enemy[receiver_idx].condition[Piece.sleep] > 0 ){
					damage = damage * 3 / 2;
					enemy[receiver_idx].condition[Piece.sleep] = 0;
					System.out.println("睡眠時確定クリティカル。睡眠回復");
				}

				////////  critical 判定 /////////////
				else if (criticalJudge(friend[attacker_idx].LUCK ,friend[attacker_idx] , enemy[receiver_idx] , skill_idx)) {
					damage = damage * 3 / 2;
					System.out.println("攻撃がクリティカル。");
				}else{
					////////////////////////////////////////////////////////////////////////////////////
					panel.setScreenParameta_critical(false);
					////////////////////////////////////////////////////////////////////////////////////
				}

				damage = damageLimit(damage,enemy[receiver_idx].HP);
				System.out.println(" damege : "+damage);
				////////////////////////////////////////////////////////////////////////////////////
				panel.setScreenParameta_damage(damage);
				////////////////////////////////////////////////////////////////////////////////////

				enemy[receiver_idx].HP -= damage;

				///////  ドレイン ///////////
				if(friend[attacker_idx].unit.skill[skill_idx].drain > 0){
					recovery(attacker_idx,damage * friend[attacker_idx].unit.skill[skill_idx].drain / 100);
					////////////////////////////////////////////////////////////////////////////////////
					panel.setScreenParameta_drain(damage * friend[attacker_idx].unit.skill[skill_idx].drain / 100);
					////////////////////////////////////////////////////////////////////////////////////

				}

				enemy[receiver_idx] = deadCheck(enemy[receiver_idx]);

				if(enemy[receiver_idx].alive){
					//////  状態異常付与 /////////
					for(int i=0 ; i<friend[attacker_idx].unit.skill[skill_idx].condition_add.length ; i++){
						////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						if(enemy[receiver_idx].condition[i] >= 0){
							////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							if(friend[attacker_idx].unit.skill[skill_idx].condition_add[i][0] != 0)
								addBadCondition(receiver_idx, i, friend[attacker_idx].unit.skill[skill_idx].condition_add[i][0],
										friend[attacker_idx].unit.skill[skill_idx].condition_add[i][1]);
						}
					}
					//////  デバフ  //////////////
					for(int i=0 ; i<friend[attacker_idx].unit.skill[skill_idx].status_management.length ; i++){
						if(friend[attacker_idx].unit.skill[skill_idx].status_management[i][1] != 0)
							debuff(receiver_idx, i, friend[attacker_idx].unit.skill[skill_idx].status_management[i][1],
									friend[attacker_idx].unit.skill[skill_idx].status_management[i][2]);
					}
				}

			} else {
				System.out.println("攻撃が外れた。");
				// 攻撃はずした場合
			}
			friend[attacker_idx].acted();
			System.out.println(enemy[receiver_idx].unit.name + "のHP : "+ enemy[receiver_idx].HP);
			panel.updatePiece(friend, enemy);
		} else {
			System.out.println(enemy[attacker_idx].unit.name+"の攻撃");
			////////////////SP消費     //////////////////////////////////////////////////////////////////////////////////
			//enemy[attacker_idx].SP -= enemy[attacker_idx].unit.skill[skill_idx].cost;
			//////////////////////////////////////////////////////////////////////////////////////////////////////

			///////  バフ  //////////
			for(int i=0 ; i<enemy[attacker_idx].unit.skill[skill_idx].status_management.length ; i++){
				if(enemy[attacker_idx].unit.skill[skill_idx].status_management[i][0] != 0)
					buff(attacker_idx, i, enemy[attacker_idx].unit.skill[skill_idx].status_management[i][0],
							enemy[attacker_idx].unit.skill[skill_idx].status_management[i][2]);
			}

			//////  状態異常効果    ////////////
			boolean a_darkness = (enemy[attacker_idx].condition[Piece.ToLOVEru] > 0);
			boolean r_darkness = (friend[receiver_idx].condition[Piece.ToLOVEru] > 0);
			boolean r_piyoORsleep =  (friend[receiver_idx].condition[Piece.piyopiyo] > 0 || friend[receiver_idx].condition[Piece.sleep] > 0 );

			if (hitJudge(enemy[attacker_idx].DEX, enemy[attacker_idx].unit.weapon.DEX,a_darkness,field[enemy[attacker_idx].position[1]][enemy[attacker_idx].position[0]],
					friend[receiver_idx].AGI, friend[receiver_idx].unit.weapon.AGI,r_darkness,field[friend[receiver_idx].position[1]][friend[receiver_idx].position[0]],
					enemy[attacker_idx].LUCK)  || r_piyoORsleep) {
				////////////////////////////////////////////////////////////////////////////////////
				panel.setScreenParameta_hit(true);
				////////////////////////////////////////////////////////////////////////////////////
				Random random = new Random();
				int r = 0;
				r = random.nextInt(31);
				int attack_point = (int) (enemy[attacker_idx].ATK * enemy[attacker_idx].unit.skill[skill_idx].power / 100);
				int DEFence_point = friend[receiver_idx].DEF;
				int basic_point = attack_point - (int) (DEFence_point * 0.5);
				int random_point = basic_point * (r - 15) / 100;
				System.out.println("basic_point :"+basic_point+"  random_point  : "+random_point);

				int basic_damage = basic_point + random_point;
				System.out.println("basic_damage :"+basic_damage);

				int damage = basic_damage;
				/////  状態異常効果    /////////////
				if(friend[receiver_idx].condition[Piece.piyopiyo] > 0 ){
					damage = damage * 3 / 2;
					friend[receiver_idx].condition[Piece.piyopiyo] = 0;
					System.out.println("気絶時確定クリティカル。気絶回復。");
				}
				else if(friend[receiver_idx].condition[Piece.sleep] > 0 ){
					damage = damage * 3 / 2;
					friend[receiver_idx].condition[Piece.sleep] = 0;
					System.out.println("睡眠時確定クリティカル。睡眠回復");
				}
				////////////   critical 判定         ///////////////////////////
				else if (criticalJudge(enemy[attacker_idx].LUCK, enemy[attacker_idx] , friend[receiver_idx], skill_idx)) {
					damage = damage * 3 / 2;
					System.out.println("攻撃がクリティカル。");
				}else{
					////////////////////////////////////////////////////////////////////////////////////
					panel.setScreenParameta_critical(false);
					////////////////////////////////////////////////////////////////////////////////////
				}
				damage = damageLimit(damage ,friend[receiver_idx].HP);
				System.out.println("damage : "+damage);
				////////////////////////////////////////////////////////////////////////////////////
				panel.setScreenParameta_damage(damage);
				////////////////////////////////////////////////////////////////////////////////////


				friend[receiver_idx].HP -= damage;

				///////////  ドレイン //////////////////
				if(enemy[attacker_idx].unit.skill[skill_idx].drain > 0){
					recovery(attacker_idx,damage * friend[attacker_idx].unit.skill[skill_idx].drain / 100);
					////////////////////////////////////////////////////////////////////////////////////
					panel.setScreenParameta_drain(damage * friend[attacker_idx].unit.skill[skill_idx].drain / 100);
					////////////////////////////////////////////////////////////////////////////////////

				}

				friend[receiver_idx] = deadCheck(friend[receiver_idx]);

				if(friend[receiver_idx].alive){
					///////  状態異常付与 ///////////////
					for(int i=0 ; i<enemy[attacker_idx].unit.skill[skill_idx].condition_add.length ; i++){
						////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						if(friend[receiver_idx].condition[i] >= 0){
							////////////////////////////////////////////////////////////////////////////////////////////////////////////////
							if(enemy[attacker_idx].unit.skill[skill_idx].condition_add[i][0] != 0)
								addBadCondition(receiver_idx, i, enemy[attacker_idx].unit.skill[skill_idx].condition_add[i][0],
										enemy[attacker_idx].unit.skill[skill_idx].condition_add[i][1]);
						}
					}
					////////  デバフ  ////////////////////
					for(int i=0 ; i<enemy[attacker_idx].unit.skill[skill_idx].status_management.length ; i++){
						if(enemy[attacker_idx].unit.skill[skill_idx].status_management[i][1] != 0)
							debuff(receiver_idx, i, enemy[attacker_idx].unit.skill[skill_idx].status_management[i][1],
									enemy[attacker_idx].unit.skill[skill_idx].status_management[i][2]);
					}
				}

			} else {
				System.out.println("攻撃が外れた。");
				// 攻撃はずした場合
			}
			enemy[attacker_idx].acted();
			System.out.println(friend[receiver_idx].unit.name + "のHP : "
					+ friend[receiver_idx].HP);
			panel.updatePiece(friend, enemy);
		}
	}


	public void magicAttack(int attacker_idx, int receiver_idx, int magic_idx) {
		if (myTurn) {
			System.out.println(friend[attacker_idx].unit.name+"の攻撃");
			////////////////////////////////////
			panel.setScreenParameta_hit(true);
			////////////////////////////////////

			///////////////////////////////////  バフ  ////////////////////////////////////////
			for(int i=0 ; i<friend[attacker_idx].unit.magic[magic_idx].status_management.length ; i++){
				if(friend[attacker_idx].unit.magic[magic_idx].status_management[i][0] != 0)
					buff(attacker_idx, i, friend[attacker_idx].unit.magic[magic_idx].status_management[i][0],
							friend[attacker_idx].unit.magic[magic_idx].status_management[i][2]);
			}
			//////////////////////////////////////////////////////////////////////////////////

			Random random = new Random();
			int r = 0;
			r = random.nextInt(31);
			int attack_point = (int) (friend[attacker_idx].INT* friend[attacker_idx].unit.magic[magic_idx].power / 100);
			int DEFence_point = enemy[receiver_idx].MEN;
			// System.out.println("attack_point :"+attack_point+"  DEFence_point  : "+DEFence_point);

			int basic_point = attack_point
					- (int) (DEFence_point * 0.5);

			int random_point = (int) (basic_point * (r - 15) / 100);

			int basic_damage = basic_point + random_point;
			// System.out.println("basic_point :"+basic_point+"  random_point  : "+random_point);

			int damage = basic_damage;

			damage = magic_typeJudge(friend[attacker_idx], enemy[receiver_idx],magic_idx, damage);

			/////////////////////////////////////  状態異常効果    //////////////////////////////////////////////
			if(enemy[receiver_idx].condition[Piece.piyopiyo] > 0 ){
				damage = damage * 3 / 2;
				enemy[receiver_idx].condition[Piece.piyopiyo] = 0;
				System.out.println("気絶時確定クリティカル。気絶回復。");
			}
			else if(enemy[receiver_idx].condition[Piece.sleep] > 0 ){
				damage = damage * 3 / 2;
				enemy[receiver_idx].condition[Piece.sleep] = 0;
				System.out.println("睡眠時確定クリティカル。睡眠回復");
			}
			else{
				System.out.println("クリティカルじゃないよ");
				panel.setScreenParameta_critical(false);
			}
			////////////////////////////////////////////////////////////////////////////////////////////////////

			damage = damageLimit(damage,enemy[receiver_idx].HP);

			////////////////////////////////////////////////////////////////////////////////////
			panel.setScreenParameta_damage(damage);
			///////////////////////////////////////////////////////////////////////////////////

			enemy[receiver_idx].HP -= damage;

			///////////  ドレイン //////////////////
			if(friend[attacker_idx].unit.magic[magic_idx].drain > 0){
				recovery(attacker_idx, damage * friend[attacker_idx].unit.magic[magic_idx].drain / 100);

				////////////////////////////////////////////////////////////////////////////////////
				panel.setScreenParameta_drain(damage * friend[attacker_idx].unit.magic[magic_idx].drain / 100);
				////////////////////////////////////////////////////////////////////////////////////

			}

			enemy[receiver_idx] = deadCheck(enemy[receiver_idx]);

			if(enemy[receiver_idx].alive){
				//////  状態異常付与 /////////
				for(int i=0 ; i<friend[attacker_idx].unit.magic[magic_idx].condition_add.length ; i++){
					////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					if(enemy[receiver_idx].condition[i] >= 0){
						////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						if(friend[attacker_idx].unit.magic[magic_idx].condition_add[i][0] != 0)
							addBadCondition(receiver_idx, i, friend[attacker_idx].unit.magic[magic_idx].condition_add[i][0],
									friend[attacker_idx].unit.magic[magic_idx].condition_add[i][1]);
					}
				}
				//////  デバフ  //////////////
				for(int i=0 ; i<friend[attacker_idx].unit.magic[magic_idx].status_management.length ; i++){
					if(friend[attacker_idx].unit.magic[magic_idx].status_management[i][1] != 0)
						debuff(receiver_idx, i, friend[attacker_idx].unit.magic[magic_idx].status_management[i][1],
								friend[attacker_idx].unit.magic[magic_idx].status_management[i][2]);
				}
			}

			friend[attacker_idx].acted();
			System.out.println(enemy[receiver_idx].unit.name + "のHP : "+ enemy[receiver_idx].HP);
			panel.updatePiece(friend, enemy);

		} else {
			////////////////////////////////////
			panel.setScreenParameta_hit(true);
			////////////////////////////////////

			///////  バフ  //////////
			for(int i=0 ; i<enemy[attacker_idx].unit.magic[magic_idx].status_management.length ; i++){
				if(enemy[attacker_idx].unit.magic[magic_idx].status_management[i][0] != 0)
					buff(attacker_idx, i, enemy[attacker_idx].unit.magic[magic_idx].status_management[i][0],
							enemy[attacker_idx].unit.magic[magic_idx].status_management[i][2]);
			}
			/////////////////////


			Random random = new Random();
			int r = 0;
			r = random.nextInt(31);
			int attack_point = (int) (enemy[attacker_idx].INT* enemy[attacker_idx].unit.magic[magic_idx].power / 100);
			int DEFence_point = friend[receiver_idx].MEN;

			int basic_point = attack_point- (int) (DEFence_point * 0.5);

			int random_point = basic_point * (r - 15) / 100;

			int basic_damage = basic_point + random_point;

			int damage = basic_damage;

			damage = magic_typeJudge(enemy[attacker_idx],
					friend[receiver_idx], magic_idx, damage);

			/////////////////////////////////////  状態異常効果    //////////////////////////////////////////////
			if(friend[receiver_idx].condition[Piece.piyopiyo] > 0 ){
				damage = damage * 3 / 2;
				friend[receiver_idx].condition[Piece.piyopiyo] = 0;
				System.out.println("気絶時確定クリティカル。気絶回復。");
			}
			else if(friend[receiver_idx].condition[Piece.sleep] > 0 ){
				damage = damage * 3 / 2;
				friend[receiver_idx].condition[Piece.sleep] = 0;
				System.out.println("睡眠時確定クリティカル。睡眠回復");
			}
			else{
				System.out.println("クリティカルじゃないよ");
				panel.setScreenParameta_critical(false);
			}
			////////////////////////////////////////////////////////////////////////////////////////////////////

			damage = damageLimit(damage , friend[receiver_idx].HP);

			////////////////////////////////////////////////////////////////////////////////////
			panel.setScreenParameta_damage(damage);
			///////////////////////////////////////////////////////////////////////////////////

			System.out.println(damage);
			friend[receiver_idx].HP -= damage;

			friend[receiver_idx] = deadCheck(friend[receiver_idx]);

			if(friend[receiver_idx].alive){
				///////  状態異常付与 ///////////////
				for(int i=0 ; i<enemy[attacker_idx].unit.magic[magic_idx].condition_add.length ; i++){
					////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					if(friend[receiver_idx].condition[i] >= 0){
						////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						if(enemy[attacker_idx].unit.magic[magic_idx].condition_add[i][0] != 0)
							addBadCondition(receiver_idx, i, enemy[attacker_idx].unit.magic[magic_idx].condition_add[i][0],
									enemy[attacker_idx].unit.magic[magic_idx].condition_add[i][1]);
					}
				}
				////////  デバフ  ////////////////////
				for(int i=0 ; i<enemy[attacker_idx].unit.magic[magic_idx].status_management.length ; i++){
					if(enemy[attacker_idx].unit.magic[magic_idx].status_management[i][1] != 0)
						debuff(receiver_idx, i, enemy[attacker_idx].unit.magic[magic_idx].status_management[i][1],
								enemy[attacker_idx].unit.magic[magic_idx].status_management[i][2]);
				}
			}
			enemy[attacker_idx].acted();

			System.out.println(friend[receiver_idx].unit.name + "のHP : "+ friend[receiver_idx].HP);
			panel.updatePiece(friend, enemy);
		}
	}

	public int typeJudge(Piece attacker, Piece DEFencer, int skill_idx) {
		int pass = 100;
		switch (attacker.unit.skill[skill_idx].type) {
		case Skill.slash:
			pass =DEFencer.unit.PASS[Unit.PASS_SPEAR];
			break;
		case Skill.spear:
			pass =  DEFencer.unit.PASS[Unit.PASS_SPEAR];
			break;
		case Skill.blow:
			pass = DEFencer.unit.PASS[Unit.PASS_BLOW] ;
			break;
		}
		return pass;
	}

	public int magic_typeJudge(Piece attacker, Piece receiver, int magic_idx,
			int damage) {
		switch (attacker.unit.magic[magic_idx].type) {
		case Magic.fire:
			damage = damage * receiver. unit.PASS[Unit.PASS_FIRE]/ 100;
			break;
		case Magic.aqua:
			damage = damage * receiver.unit.PASS[Unit.PASS_AQUA]/100;
			break;
		case Magic.sky:
			damage = damage * receiver.unit.PASS[Unit.PASS_SKY] / 100;
			break;
		case Magic.grand:
			damage = damage * receiver. unit.PASS[Unit.PASS_GRAND]/ 100;
			break;
		case Magic.ray:
			damage = damage * receiver.unit.PASS[Unit.PASS_RAY]/ 100;
			break;
		case Magic.dark:
			damage = damage * receiver.unit.PASS[Unit.PASS_DARK]/ 100;
			break;
		}
		return damage;
	}

	public void counterAttack(int attacker_idx, int receiver_idx) {
		if (myTurn) {
			System.out.println(enemy[attacker_idx].unit.name+"の反撃");
			///////////  状態異常効果    //////////////////
			boolean a_darkness = (enemy[attacker_idx].condition[Piece.ToLOVEru] > 0);
			boolean r_darkness = (friend[receiver_idx].condition[Piece.ToLOVEru] > 0);
			//			boolean a_piyoORsleepORparalysisORfreeze =  (enemy[attacker_idx].condition[Piece.piyopiyo] > 0 ||
			//					enemy[attacker_idx].condition[Piece.sleep] > 0 ||
			//					enemy[attacker_idx].condition[Piece.paralysis] > 0 ||
			//					enemy[attacker_idx].condition[Piece.freeze] > 0);
			//			if(!a_piyoORsleepORparalysisORfreeze){
			if (hitJudge(enemy[attacker_idx].DEX,enemy[attacker_idx].unit.weapon.DEX, a_darkness,field[enemy[receiver_idx].position[1]][enemy[receiver_idx].position[0]],
					friend[receiver_idx].AGI, friend[receiver_idx].unit.weapon.AGI,r_darkness,field[friend[receiver_idx].position[1]][friend[receiver_idx].position[0]],
					enemy[attacker_idx].LUCK) ) {
				////////////////////////////////////////////////////////////////////////////////////
				panel.setScreenParameta_c_hit(true);
				////////////////////////////////////////////////////////////////////////////////////

				Random random = new Random();
				int r = 0;
				r = random.nextInt(31);
				int attack_point = (int) (enemy[attacker_idx].ATK * enemy[attacker_idx].unit.skill[0].power / 100);
				int DEFence_point = friend[receiver_idx].DEF;
				System.out.println("attack_point :"+attack_point+"  DEFence_point  : "+DEFence_point);

				int basic_point = attack_point - (int) (DEFence_point * 0.5);
				int random_point = (int) (basic_point * (r - 15) / 100);
				System.out.println("basic_point :"+basic_point+"  random_point  : "+random_point);
				int basic_damage = basic_point + random_point;
				System.out.println("basic_damage :"+basic_damage);

				int damage = basic_damage;
				if (criticalJudge(enemy[attacker_idx].LUCK , enemy[attacker_idx],friend[receiver_idx],0)) {
					damage = damage * 3 / 2;
					System.out.println("攻撃がクリティカル。");
					////////////////////////////////////////////////////////////////////////////////////
					panel.setScreenParameta_c_critical(true);
					////////////////////////////////////////////////////////////////////////////////////
				}
				damage = damageLimit(damage, friend[receiver_idx].HP);
				System.out.println("ダメージ："+damage);
				////////////////////////////////////////////////////////////////////////////////////
				panel.setScreenParameta_c_damage(damage);
				////////////////////////////////////////////////////////////////////////////////////



				friend[receiver_idx].HP -= damage;
				friend[receiver_idx] = deadCheck(friend[receiver_idx]);
			} else {
				System.out.println("攻撃が外れた。");
				// 攻撃はずした場合
			}
			System.out.println(friend[receiver_idx].unit.name + "のHP : "+ friend[receiver_idx].HP);
			panel.updatePiece(friend, enemy);

		} else {
			System.out.println(friend[attacker_idx].unit.name+"の反撃");
			/////  状態異常効果    /////////
			boolean a_darkness = (friend[attacker_idx].condition[Piece.ToLOVEru] > 0);
			boolean r_darkness = (enemy[receiver_idx].condition[Piece.ToLOVEru] > 0);
			//			boolean a_piyoORsleepORparalysisORfreeze =  (friend[attacker_idx].condition[Piece.piyopiyo] > 0 ||
			//					friend[attacker_idx].condition[Piece.sleep] > 0 ||
			//					friend[attacker_idx].condition[Piece.paralysis] > 0 ||
			//					friend[attacker_idx].condition[Piece.freeze] > 0);
			//			if(!a_piyoORsleepORparalysisORfreeze){
			if (hitJudge(friend[attacker_idx].DEX,friend[attacker_idx].unit.weapon.DEX,  a_darkness,field[friend[attacker_idx].position[1]][friend[attacker_idx].position[0]],
					enemy[receiver_idx].AGI,enemy[receiver_idx].unit.weapon.AGI,  r_darkness,field[enemy[receiver_idx].position[1]][enemy[receiver_idx].position[0]],
					friend[attacker_idx].LUCK)) {
				////////////////////////////////////////////////////////////////////////////////////
				panel.setScreenParameta_c_hit(true);
				////////////////////////////////////////////////////////////////////////////////////
				Random random = new Random();
				int r = 0;
				r = random.nextInt(31);
				int attack_point = (int) (friend[attacker_idx].ATK * friend[attacker_idx].unit.skill[0].power / 100);
				int DEFence_point = enemy[receiver_idx].DEF;
				System.out.println("attack_point :"+attack_point+"  DEFence_point  : "+DEFence_point);

				int basic_point = attack_point - (DEFence_point /2);
				int random_point = (int) (basic_point * (r - 15) / 100);
				System.out.println("basic_point :"+basic_point+"  random_point  : "+random_point);
				int basic_damage = basic_point + random_point;
				System.out.println("basic_damage :"+basic_damage);
				int damage = basic_damage;

				if (criticalJudge(friend[attacker_idx].LUCK , friend[attacker_idx], enemy[receiver_idx],0)) {
					damage = damage * 3 / 2;
					System.out.println("攻撃がクリティカル。");
					////////////////////////////////////////////////////////////////////////////////////
					panel.setScreenParameta_c_critical(true);
					////////////////////////////////////////////////////////////////////////////////////
				}
				damage = damageLimit(damage , enemy[receiver_idx].HP);
				System.out.println("ダメージ："+damage);
				////////////////////////////////////////////////////////////////////////////////////
				panel.setScreenParameta_c_damage(damage);
				////////////////////////////////////////////////////////////////////////////////////

				enemy[receiver_idx].HP -= damage;
				enemy[receiver_idx] = deadCheck(enemy[receiver_idx]);
			} else {
				System.out.println("攻撃が外れた。");
				// 攻撃はずした場合
			}
			System.out.println(enemy[receiver_idx].unit.name + "のHP : "+ enemy[receiver_idx].HP);
			panel.updatePiece(friend, enemy);
			//			}else{
			//				System.out.println("と、思ったが気絶か麻痺か凍結か睡眠していて反撃できない");
			//			}
		}
	}

	public Piece deadCheck(Piece piece) {
		// System.out.println("deadcheck"+piece.HP);
		if (piece.HP <= 0) {
			piece.HP = 0;
			piece.alive = false;
			for(int i=0 ; i<piece.condition.length ; i++ ) if(piece.condition[i] >= 0) piece.condition[i] =0;
			for(int i=0 ; i<piece.status_shift.length ; i++ ) if(piece.status_shift[i] >= 0) piece.status_shift[i] =0;
		}
		return piece;
	}

	public int damageLimit(int damage , int HP){
		if(damage <= 0) damage = 1;
		if(damage >= HP) damage = HP;
		return damage;
	}

	public void support(int supporter_idx, int receiver_idx, int skill_idx) {
		if (myTurn) {
			//  回復
			if(friend[supporter_idx].unit.skill[skill_idx].power != 0){
				recovery(receiver_idx, friend[supporter_idx].unit.skill[skill_idx].power);
			}

			//  バフ
			for(int i=0 ; i<friend[supporter_idx].unit.skill[skill_idx].status_management.length ; i++){
				if(friend[supporter_idx].unit.skill[skill_idx].status_management[i][0] != 0)
					buff(receiver_idx, i, friend[supporter_idx].unit.skill[skill_idx].status_management[i][0],
							friend[supporter_idx].unit.skill[skill_idx].status_management[i][2]);
			}
			friend[supporter_idx].acted();
			panel.updatePiece(friend, enemy);
		} else {
			//  回復
			if(enemy[supporter_idx].unit.skill[skill_idx].power != 0){
				recovery(receiver_idx, enemy[supporter_idx].unit.skill[skill_idx].power);
			}

			//  バフ
			for(int i=0 ; i<enemy[supporter_idx].unit.skill[skill_idx].status_management.length ; i++){
				if(enemy[supporter_idx].unit.skill[skill_idx].status_management[i][0] != 0)
					buff(receiver_idx, i, enemy[supporter_idx].unit.skill[skill_idx].status_management[i][0],
							enemy[supporter_idx].unit.skill[skill_idx].status_management[i][2]);
			}

			enemy[supporter_idx].acted();
			panel.updatePiece(friend, enemy);
		}

	}
	public void magicSupport(int supporter_idx, int receiver_idx, int skill_idx) {
		if (myTurn) {
			//  回復
			if(friend[supporter_idx].unit.magic[skill_idx].power != 0){
				recovery(receiver_idx, friend[supporter_idx].unit.magic[skill_idx].power);
			}

			//  バフ
			for(int i=0 ; i<friend[supporter_idx].unit.magic[skill_idx].status_management.length ; i++){
				if(friend[supporter_idx].unit.magic[skill_idx].status_management[i][0] != 0)
					buff(receiver_idx, i, friend[supporter_idx].unit.magic[skill_idx].status_management[i][0],
							friend[supporter_idx].unit.magic[skill_idx].status_management[i][2]);
			}
			friend[supporter_idx].acted();
			panel.updatePiece(friend, enemy);
		} else {
			//  回復
			if(enemy[supporter_idx].unit.magic[skill_idx].power != 0){
				recovery(receiver_idx, enemy[supporter_idx].unit.magic[skill_idx].power);
			}

			//  バフ
			for(int i=0 ; i<enemy[supporter_idx].unit.magic[skill_idx].status_management.length ; i++){
				if(enemy[supporter_idx].unit.magic[skill_idx].status_management[i][0] != 0)
					buff(receiver_idx, i, enemy[supporter_idx].unit.magic[skill_idx].status_management[i][0],
							enemy[supporter_idx].unit.magic[skill_idx].status_management[i][2]);
			}

			enemy[supporter_idx].acted();
			panel.updatePiece(friend, enemy);
		}

	}



	public void getPhase() {
		for (int i = 0; i < friend.length; i++) {
			friend[i].getTurn();
		}
	}

	public void enemyPhase() {
		conditionPass(false);
		statusPass(false);
	}

	public void movePosition(int ptr_idx, int x, int y) {
		if (myTurn) {
			if (map[y][x] != 1) {
				map[friend[ptr_idx].position[1]][friend[ptr_idx].position[0]] = 0;
				map[y][x] = 1;
				friend[ptr_idx].position[0] = x;
				friend[ptr_idx].position[1] = y;
			}
			panel.updateMap(map);
			initMoveMap();
			friend[ptr_idx].moved();
			panel.updatePiece(friend, enemy);
		} else {
			if (map[y][x] != -1) {
				map[enemy[ptr_idx].position[1]][enemy[ptr_idx].position[0]] = 0;
				map[y][x] = -1;
				enemy[ptr_idx].position[0] = x;
				enemy[ptr_idx].position[1] = y;
			}
			panel.updateMap(map);
			initMoveMap();
			enemy[ptr_idx].moved();
			//System.out.println(enemy[ptr_idx].unit.name+"移動したお");
			panel.updatePiece(friend, enemy);
		}

	}


	public void turnAround(int attacker_idx, int receiver_idx) {

		if (myTurn){
			if (friend[attacker_idx].position[1] > enemy[receiver_idx].position[1] ) {
		 
				enemy[receiver_idx].direction = Piece.down;
			}
		
			else if (friend[attacker_idx].position[0] < enemy[receiver_idx].position[0] ) {
				enemy[receiver_idx].direction = Piece.left;
			}
			
			else if (friend[attacker_idx].position[1] < enemy[receiver_idx].position[1] ) {
				enemy[receiver_idx].direction = Piece.up;
			}
			else if(friend[attacker_idx].position[0] > enemy[receiver_idx].position[0]){
				enemy[receiver_idx].direction = Piece.right;
			}
			panel.updatePiece(friend, enemy);
		}else{
			if (enemy[attacker_idx].position[1] > friend[receiver_idx].position[1] ) {
				 
				friend[receiver_idx].direction = Piece.down;
			}
		
			else if (enemy[attacker_idx].position[0] < friend[receiver_idx].position[0] ) {
				friend[receiver_idx].direction = Piece.left;
			}
			
			else if (enemy[attacker_idx].position[1] < friend[receiver_idx].position[1] ) {
				friend[receiver_idx].direction = Piece.up;
			}
			else if(enemy[attacker_idx].position[0] > friend[receiver_idx].position[0]){
				friend[receiver_idx].direction = Piece.right;
			}
			panel.updatePiece(friend, enemy);
		}
	}

	public void movable(int[] start_posi, int ptr_x, int ptr_y, int move) {
		if(field[start_posi[1]][start_posi[0]] == BattleView.mountain) movable_(start_posi ,ptr_x,ptr_y,move-1);
		else  movable_(start_posi ,ptr_x,ptr_y,move);
	}
	
	public void movable_(int[] start_posi,int ptr_x,int ptr_y, int move){
		move_map[start_posi[1]][start_posi[0]] = 5;
		if(myTurn){
			if (move >= 0) {
				if (move_map[ptr_y][ptr_x] == 0 && field[ptr_y][ptr_x] != BattleView.sea && field[ptr_y][ptr_x] != BattleView.noentry)
					move_map[ptr_y][ptr_x] = 5;
				if (ptr_y - 1 >= 0 && map[ptr_y-1][ptr_x] >= BattleSystem.nobody && field[ptr_y-1][ptr_x] != BattleView.sea  && field[ptr_y-1][ptr_x] != BattleView.noentry ) {
					movable_(start_posi, ptr_x, ptr_y - 1, move - 1);
					// System.out.println("上"+ptr_x+","+ptr_y);
					// for(int i=0 ; i<map.length ;
					// i++)System.out.println(Arrays.toString(move_map[i]));

				}
				if (ptr_x - 1 >= 0 && map[ptr_y][ptr_x-1] >= BattleSystem.nobody&& field[ptr_y][ptr_x-1] != BattleView.sea  && field[ptr_y][ptr_x-1] != BattleView.noentry) {
					movable_(start_posi, ptr_x - 1, ptr_y, move - 1);
					// System.out.println("左"+move);
					// for(int i=0 ; i<map.length ;
					// i++)System.out.println(Arrays.toString(move_map[i]));
				}
				if (ptr_y + 1 < move_map.length && map[ptr_y+1][ptr_x] >= BattleSystem.nobody&& field[ptr_y+1][ptr_x] != BattleView.sea  && field[ptr_y+1][ptr_x] != BattleView.noentry) {
					movable_(start_posi, ptr_x, ptr_y + 1, move - 1);
					// System.out.println("下");
					// for(int i=0 ; i<map.length ;
					// i++)System.out.println(Arrays.toString(move_map[i]));
				}
				if (ptr_x + 1 < move_map[0].length && map[ptr_y][ptr_x+1] >= BattleSystem.nobody&& field[ptr_y][ptr_x+1] != BattleView.sea  && field[ptr_y][ptr_x+1] != BattleView.noentry) {
					movable_(start_posi, ptr_x + 1, ptr_y, move - 1);
					// System.out.println("右");
					// for(int i=0 ; i<map.length ;
					// i++)System.out.println(Arrays.toString(move_map[i]));
				}
			} else {
				// System.out.println("movable");
				updateMoveMap(move_map);
			}
		}else{
			if (move >= 0) {
				if (move_map[ptr_y][ptr_x] == 0 && field[ptr_y][ptr_x] != BattleView.sea && field[ptr_y][ptr_x] != BattleView.noentry)
					move_map[ptr_y][ptr_x] = 5;
				if (ptr_y - 1 >= 0 && map[ptr_y-1][ptr_x] <= BattleSystem.nobody && field[ptr_y-1][ptr_x] != BattleView.sea  && field[ptr_y-1][ptr_x] != BattleView.noentry) {
					movable_(start_posi, ptr_x, ptr_y - 1, move - 1);
					// System.out.println("上"+ptr_x+","+ptr_y);
					// for(int i=0 ; i<map.length ;
					// i++)System.out.println(Arrays.toString(move_map[i]));

				}
				if (ptr_x - 1 >= 0 && map[ptr_y][ptr_x-1] <= BattleSystem.nobody&& field[ptr_y][ptr_x-1] != BattleView.sea  && field[ptr_y][ptr_x-1] != BattleView.noentry) {
					movable_(start_posi, ptr_x - 1, ptr_y, move - 1);
					// System.out.println("左"+move);
					// for(int i=0 ; i<map.length ;
					// i++)System.out.println(Arrays.toString(move_map[i]));
				}
				if (ptr_y + 1 < move_map.length && map[ptr_y+1][ptr_x] <= BattleSystem.nobody&& field[ptr_y+1][ptr_x] != BattleView.sea  && field[ptr_y+1][ptr_x] != BattleView.noentry) {
					movable_(start_posi, ptr_x, ptr_y + 1, move - 1);
					// System.out.println("下");
					// for(int i=0 ; i<map.length ;
					// i++)System.out.println(Arrays.toString(move_map[i]));
				}
				if (ptr_x + 1 < move_map[0].length && map[ptr_y][ptr_x+1] <= BattleSystem.nobody&& field[ptr_y][ptr_x+1] != BattleView.sea  && field[ptr_y][ptr_x+1] != BattleView.noentry) {
					movable_(start_posi, ptr_x + 1, ptr_y, move - 1);
					// System.out.println("右");
					// for(int i=0 ; i<map.length ;
					// i++)System.out.println(Arrays.toString(move_map[i]));
				}
			} else {
				// System.out.println("movable");
				updateMoveMap(move_map);
			}
		}

	}
	public void initMoveMap() {// initializeMoveMap
		//System.out.println("MoveMap initialized!");
		for (int i = 0; i < move_map.length; i++){
			for(int j = 0; j < move_map[i].length; j++){
				move_map[i][j] = BattleSystem.nobody;
			}
		}
		panel.updateMoveMap(move_map);
	}

	public void updateMoveMap(int[][] move_map) {
		panel.updateMoveMap(move_map);
	}

	public void conditionPass(boolean myTurn) {
		if (myTurn) {
			for (int i = 0; i < friend.length; i++) {
				for (int j = 0; j < friend[i].condition.length; j++) {
					if (friend[i].condition[j] > 0){
						friend[i].condition[j]--;
						if(j == Piece.poison){
							friend[i].HP -= friend[i].unit.HP * 10 / 100;
							System.out.println(friend[i].unit.name+" : 毒によるダメージ  -"+(friend[i].unit.HP * 10 / 100));
						}
						if(j == Piece.freeze){
							friend[i].HP -= friend[i].unit.HP * 5 / 100;
							System.out.println(friend[i].unit.name+" : 凍結によるダメージ  -"+(friend[i].unit.HP * 5 / 100));
						}
						if(j == Piece.burn){
							friend[i].HP -= 60;
							System.out.println(friend[i].unit.name+" : 燃焼によるダメージ  -60");
						}

						deadCheck(friend[i]);
						switch(j){
						case Piece.poison:
							System.out.println(friend[i].unit.name+" 毒 ：残り "+friend[i].condition[j]+" ターン");
							break;
						case Piece.paralysis:
							System.out.println(friend[i].unit.name+" 麻痺 ：残り "+friend[i].condition[j]+" ターン");
							break;
						case Piece.freeze:
							System.out.println(friend[i].unit.name+" 凍結 ：残り "+friend[i].condition[j]+" ターン");
							break;
						case Piece.piyopiyo:
							System.out.println(friend[i].unit.name+" 気絶 ：残り "+friend[i].condition[j]+" ターン");
							break;
						case Piece.sleep:
							System.out.println(friend[i].unit.name+" 睡眠 ：残り "+friend[i].condition[j]+" ターン");
							break;
						case Piece.burn:
							System.out.println(friend[i].unit.name+" 燃焼 ：残り "+friend[i].condition[j]+" ターン");
							break;
						case Piece.ToLOVEru:
							System.out.println(friend[i].unit.name+" 暗闇 ：残り "+friend[i].condition[j]+" ターン");
							break;
						}
					}
				}
			}
		} else {
			for (int i = 0; i < enemy.length; i++) {
				for (int j = 0; j < enemy[i].condition.length; j++) {
					if (enemy[i].condition[j] > 0){
						enemy[i].condition[j]--;
						if(j == Piece.poison){
							enemy[i].HP -= enemy[i].unit.HP * 10 / 100;
							System.out.println(enemy[i].unit.name+" : 毒によるダメージ  -"+(enemy[i].unit.HP * 10 / 100));
						}
						if(j == Piece.freeze){
							enemy[i].HP -= enemy[i].unit.HP * 5 / 100;
							System.out.println(enemy[i].unit.name+" : 凍結によるダメージ  -"+(enemy[i].unit.HP * 5 / 100));
						}
						if(j == Piece.burn){
							enemy[i].HP -= 60;
							System.out.println(enemy[i].unit.name+" : 燃焼によるダメージ  -60");
						}
						deadCheck(enemy[i]);
						switch(j){
						case Piece.poison:
							System.out.println("毒 ：残り "+enemy[i].condition[j]+" ターン");
							break;
						case Piece.paralysis:
							System.out.println("麻痺 ：残り "+enemy[i].condition[j]+" ターン");
							break;
						case Piece.freeze:
							System.out.println("凍結 ：残り "+enemy[i].condition[j]+" ターン");
							break;
						case Piece.piyopiyo:
							System.out.println("気絶 ：残り "+enemy[i].condition[j]+" ターン");
							break;
						case Piece.sleep:
							System.out.println("睡眠 ：残り "+enemy[i].condition[j]+" ターン");
							break;
						case Piece.burn:
							System.out.println("燃焼 ：残り "+enemy[i].condition[j]+" ターン");
							break;
						case Piece.ToLOVEru:
							System.out.println("暗闇 ：残り "+enemy[i].condition[j]+" ターン");
							break;
						}
					}
				}
			}
		}
		if(victoryCheck())panel.victory();
		if(defeatCheck())panel.defeat();
	}

	public void statusPass(boolean myTurn) {
		if (myTurn) {
			for (int i = 0; i < friend.length; i++) {
				for (int j = 0; j < friend[i].status_shift.length; j++) {
					if (friend[i].status_shift[j] > 0) {
						friend[i].status_shift[j]--;
						if (friend[i].status_shift[j] == 0)
							statusReturn(myTurn, i, j);
					}
				}
			}
		} else {
			for (int i = 0; i < enemy.length; i++) {
				for (int j = 0; j < enemy[i].status_shift.length; j++) {
					if (enemy[i].status_shift[j] > 0) {
						enemy[i].status_shift[j]--;
						if (enemy[i].status_shift[j] == 0)
							statusReturn(myTurn, i, j);
					}
				}
			}
		}
	}

	public void statusReturn(boolean myTurn, int ptr, int kind) {
		if (myTurn) {
			switch (kind) {
			case 0:
				friend[ptr].ATK = friend[ptr].unit.ATK
				+ friend[ptr].unit.weapon.ATK;
				break;
			case 1:
				friend[ptr].DEF = friend[ptr].unit.DEF
				+ friend[ptr].unit.weapon.DEF;
				break;
			case 2:
				friend[ptr].INT= friend[ptr].unit.INT
				+ friend[ptr].unit.weapon.INT;
				break;
			case 3:
				friend[ptr].MEN = friend[ptr].unit.MEN
				+ friend[ptr].unit.weapon.MEN;
				break;
			case 4:
				friend[ptr].AGI = friend[ptr].unit.AGI
				+ friend[ptr].unit.weapon.AGI;
				break;
			case 5:
				friend[ptr].DEX = friend[ptr].unit.DEX
				+ friend[ptr].unit.weapon.DEX;
				break;
			case 6:
				friend[ptr].LUCK = friend[ptr].unit.LUCK
				+ friend[ptr].unit.weapon.LUCK;
				break;
			case 7:
				friend[ptr].MOVE = friend[ptr].unit.MOVE
				+ friend[ptr].unit.weapon.MOVE;
				break;
			}
		} else {
			switch (kind) {
			case 0:
				enemy[ptr].ATK = enemy[ptr].unit.ATK
				+ enemy[ptr].unit.weapon.ATK;
				break;
			case 1:
				enemy[ptr].DEF = enemy[ptr].unit.DEF
				+ enemy[ptr].unit.weapon.DEF;
				break;
			case 2:
				enemy[ptr].INT = enemy[ptr].unit.INT
				+ enemy[ptr].unit.weapon.INT;
				break;
			case 3:
				enemy[ptr].MEN = enemy[ptr].unit.MEN
				+ enemy[ptr].unit.weapon.MEN;
				break;
			case 4:
				enemy[ptr].AGI = enemy[ptr].unit.AGI
				+ enemy[ptr].unit.weapon.AGI;
				break;
			case 5:
				enemy[ptr].DEX = enemy[ptr].unit.DEX
				+ enemy[ptr].unit.weapon.DEX;
				break;
			case 6:
				enemy[ptr].LUCK = enemy[ptr].unit.LUCK
				+ enemy[ptr].unit.weapon.LUCK;
				break;
			case 7:
				enemy[ptr].MOVE = enemy[ptr].unit.MOVE
				+ enemy[ptr].unit.weapon.MOVE;
				break;
			}

		}
	}

	public void endPhase() {
		conditionPass(myTurn);
		statusPass(myTurn);
		if (myTurn){
			for (int i = 0; i < friend.length; i++){
				if(field[friend[i].position[1]][friend[i].position[0]] == BattleView.fortress){
					System.out.println("フィールド：リンゴ畑にいるため、体力回復");
					recovery(i,(friend[i].HP+friend[i].unit.weapon.HP)*5/100);
				}
			}
			panel.updatePiece(friend,enemy);
		}else{
			for (int i = 0; i < enemy.length; i++){
				if(field[enemy[i].position[1]][enemy[i].position[0]] == BattleView.fortress){
					System.out.println("フィールド：リンゴ畑にいるため、体力回復");
					recovery(i,(enemy[i].HP+enemy[i].unit.weapon.HP)*5/100);
				}
			}
			panel.updatePiece(friend,enemy);
		}
		myTurn = (myTurn) ? false : true;
		if (myTurn)
			for (int i = 0; i < friend.length; i++)
				friend[i].getTurn();
		else
			for (int i = 0; i < enemy.length; i++)
				enemy[i].getTurn();
		if (myTurn)
			System.out.println("player phase");
		else
			System.out.println("enemy phase");
	}

	public boolean hitJudge(int a_DEX, int a_w_DEX,boolean a_d,int a_field,
			int r_AGI, int r_w_AGI, boolean r_d,int r_field, int a_LUCK) { // a_DEX:
		// attacker
		// DEX/攻撃側の器用さ
		// r_agi:
		// receiver
		// agi/受け側の敏捷
		// a_LUCK:attacker
		// LUCK/攻撃側の運
		Random r = new Random();
		int threshold1 = 80 + (a_DEX - r_AGI) * 5 / 4; // threshold : 閾値( =
		// 命中確率)
		System.out.println("当たる確率 : "+threshold1+"%");
		if(a_d) {
			threshold1 -= 50;
			System.out.println("攻撃側暗闇のため確率変動 : "+threshold1+"%");
		}
		if(r_d){
			threshold1 += 50;
			System.out.println("被攻撃側暗闇のため確率変動 : "+threshold1+"%");
		}

		if(a_field == BattleView.wood){
			threshold1 -= 5;
			System.out.println("攻撃側森の中にいるため確率低下 : "+threshold1+"%");
		}else if(a_field == BattleView.road){
			threshold1 += 5;
			System.out.println("攻撃側道路上にいるため確率上昇 : "+threshold1+"%");
		}
		if(r_field == BattleView.wood){
			threshold1 -= 5;
			System.out.println("被攻撃側森の中にいるため確率低下 : "+threshold1+"%");
		}else if(a_field == BattleView.road){
			threshold1 += 5;
			System.out.println("被攻撃側道路上にいるため確率上昇 : "+threshold1+"%");
		}

		threshold1 += a_w_DEX;
		threshold1 -= r_w_AGI;


		int ran = r.nextInt(100) + 1; // 1~100 の乱数
		boolean judge;
		judge = (threshold1 >= ran) ? true : false;

		if (!judge) {
			int threshold2 = a_LUCK / 4; // threshold : 閾値( = 命中確率)
			ran = r.nextInt(100) + 1; // 1~100 の乱数
			judge = (threshold2 >= ran) ? true : false;
		}
		return judge;
	}

	public boolean criticalJudge(int LUCK , Piece attacker, Piece receiver ,int skill_idx) { // LUCK: 攻撃側の運
		Random r = new Random();
		int threshold = (typeJudge(attacker, receiver ,skill_idx)+LUCK) / 5; // threshold : 閾値( = 会心確率)
		System.out.println("critical率 :"+threshold );
		int ran = r.nextInt(100) + 1; // 1~100 の乱数
		return (threshold >= ran) ? true : false;
	}

	public void PvPMode(boolean PvP) {
		this.PvP = PvP;

	}

	public void setDirection(int idx, int direction) {
		if (myTurn)
			friend[idx].direction = direction;
		else
			enemy[idx].direction = direction;

		panel.updatePiece(friend,enemy);
	}
	public void recovery(int idx, int recovery){
		if(myTurn){
			friend[idx].HP += recovery;
			System.out.println(friend[idx].unit.name+" の HP が"+recovery+"回復");
			if(friend[idx].HP > friend[idx].unit.HP+friend[idx].unit.weapon.HP) friend[idx].HP = friend[idx].unit.HP+friend[idx].unit.weapon.HP;
			System.out.println(friend[idx].unit.name+" の HP :"+friend[idx].HP);
		}else{
			enemy[idx].HP += recovery;
			System.out.println(enemy[idx].unit.name+" の HP が"+recovery+"回復");
			if(enemy[idx].HP > enemy[idx].unit.HP+enemy[idx].unit.weapon.HP) enemy[idx].HP = enemy[idx].unit.HP+enemy[idx].unit.weapon.HP;
			System.out.println(enemy[idx].unit.name+" の HP :"+enemy[idx].HP);
		}
	}
	public void addBadCondition(int idx, int kind, int threshold, int length){
		Random r = new Random();
		int ran = r.nextInt(100) + 1; // 1~100 の乱数
		if(threshold >= ran){
			if(myTurn){
				enemy[idx].condition[kind] = length;
				panel.setScreenParameta_s_condition(kind);
			}else{
				friend[idx].condition[kind] = length;
				panel.setScreenParameta_s_condition(kind);
			}
		}
	}
	public void buff(int idx, int kind, int magnification, int length){
		if(myTurn){
			friend[idx].status_shift[kind] = length;
			boolean buff = false;
			switch (kind) {
			case 0:
				friend[idx].ATK = friend[idx].unit.ATK * magnification /100;
				buff = (friend[idx].ATK > friend[idx].unit.ATK + friend[idx].unit.weapon.ATK);
				break;
			case 1:
				friend[idx].DEF = friend[idx].unit.DEF * magnification /100;
				buff = (friend[idx].DEF > friend[idx].unit.DEF + friend[idx].unit.weapon.DEF);
				break;
			case 2:
				friend[idx].INT = friend[idx].unit.INT * magnification /100;
				buff = (friend[idx].INT > friend[idx].unit.INT + friend[idx].unit.weapon.INT);
				break;
			case 3:
				friend[idx].MEN = friend[idx].unit.MEN * magnification /100;
				buff = (friend[idx].MEN > friend[idx].unit.MEN + friend[idx].unit.weapon.MEN);
				break;
			case 4:
				friend[idx].AGI = friend[idx].unit.AGI * magnification /100;
				buff = (friend[idx].AGI > friend[idx].unit.AGI + friend[idx].unit.weapon.AGI);
				break;
			case 5:
				friend[idx].DEX = friend[idx].unit.DEX * magnification /100;
				buff = (friend[idx].DEF > friend[idx].unit.DEF + friend[idx].unit.weapon.DEF);
				break;
			case 6:
				friend[idx].LUCK = friend[idx].unit.LUCK*magnification /100;
				buff = (friend[idx].LUCK > friend[idx].unit.LUCK + friend[idx].unit.weapon.LUCK);
				break;
			case 7:
				friend[idx].MOVE = friend[idx].unit.MOVE*magnification /100;
				buff = (friend[idx].MOVE > friend[idx].unit.MOVE + friend[idx].unit.weapon.MOVE);
				break;
			}
			System.out.println("at buff/ buff :" +buff);
			panel.setScreenParameta_a_buff(buff);
		}else{
			enemy[idx].status_shift[kind] = length;
			boolean buff = false;
			switch (kind) {
			case 0:
				enemy[idx].ATK = enemy[idx].unit.ATK * magnification /100;
				buff = (enemy[idx].ATK > enemy[idx].unit.ATK + enemy[idx].unit.weapon.ATK);
				break;
			case 1:
				enemy[idx].DEF = enemy[idx].unit.DEF * magnification /100;
				buff = (enemy[idx].DEF > enemy[idx].unit.DEF + enemy[idx].unit.weapon.DEF);
				break;
			case 2:
				enemy[idx].INT = enemy[idx].unit.INT * magnification /100;
				buff = (enemy[idx].INT > enemy[idx].unit.INT + enemy[idx].unit.weapon.INT);
				break;
			case 3:
				enemy[idx].MEN = enemy[idx].unit.MEN * magnification /100;
				buff = (enemy[idx].MEN > enemy[idx].unit.MEN + enemy[idx].unit.weapon.MEN);
				break;
			case 4:
				enemy[idx].AGI = enemy[idx].unit.AGI * magnification /100;
				buff = (enemy[idx].AGI > enemy[idx].unit.AGI + enemy[idx].unit.weapon.AGI);
				break;
			case 5:
				enemy[idx].DEX = enemy[idx].unit.DEX * magnification /100;
				buff = (enemy[idx].DEF > enemy[idx].unit.DEF + enemy[idx].unit.weapon.DEF);
				break;
			case 6:
				enemy[idx].LUCK = enemy[idx].unit.LUCK*magnification /100;
				buff = (enemy[idx].LUCK > enemy[idx].unit.LUCK + enemy[idx].unit.weapon.LUCK);
				break;
			case 7:
				enemy[idx].MOVE = enemy[idx].unit.MOVE*magnification /100;
				buff = (enemy[idx].MOVE > enemy[idx].unit.MOVE + enemy[idx].unit.weapon.MOVE);
				break;
			}
			panel.setScreenParameta_a_buff(buff);
		}
	}
	public void debuff(int idx, int kind, int magnification, int length){
		if(myTurn){
			enemy[idx].status_shift[kind] = length;
			boolean buff = false;
			switch (kind) {
			case 0:
				enemy[idx].ATK = enemy[idx].unit.ATK * magnification /100;
				buff = (enemy[idx].ATK > enemy[idx].unit.ATK + enemy[idx].unit.weapon.ATK);
				break;
			case 1:
				enemy[idx].DEF = enemy[idx].unit.DEF * magnification /100;
				buff = (enemy[idx].DEF > enemy[idx].unit.DEF + enemy[idx].unit.weapon.DEF);
				break;
			case 2:
				enemy[idx].INT = enemy[idx].unit.INT * magnification /100;
				buff = (enemy[idx].INT > enemy[idx].unit.INT + enemy[idx].unit.weapon.INT);
				break;
			case 3:
				enemy[idx].MEN = enemy[idx].unit.MEN * magnification /100;
				buff = (enemy[idx].MEN > enemy[idx].unit.MEN + enemy[idx].unit.weapon.MEN);
				break;
			case 4:
				enemy[idx].AGI = enemy[idx].unit.AGI * magnification /100;
				buff = (enemy[idx].AGI > enemy[idx].unit.AGI + enemy[idx].unit.weapon.AGI);
				break;
			case 5:
				enemy[idx].DEX = enemy[idx].unit.DEX * magnification /100;
				buff = (enemy[idx].DEF > enemy[idx].unit.DEF + enemy[idx].unit.weapon.DEF);
				break;
			case 6:
				enemy[idx].LUCK = enemy[idx].unit.LUCK*magnification /100;
				buff = (enemy[idx].LUCK > enemy[idx].unit.LUCK + enemy[idx].unit.weapon.LUCK);
				break;
			case 7:
				enemy[idx].MOVE = enemy[idx].unit.MOVE*magnification /100;
				buff = (enemy[idx].MOVE > enemy[idx].unit.MOVE + enemy[idx].unit.weapon.MOVE);
				break;
			}
			System.out.println("at debuff/ buff :" +buff);
			panel.setScreenParameta_r_buff(buff);
		}else{
			friend[idx].status_shift[kind] += length;
			boolean buff = false;
			switch (kind) {
			case 0:
				friend[idx].ATK = friend[idx].unit.ATK * magnification /100;
				buff = (friend[idx].ATK > friend[idx].unit.ATK + friend[idx].unit.weapon.ATK);
				break;
			case 1:
				friend[idx].DEF = friend[idx].unit.DEF * magnification /100;
				buff = (friend[idx].DEF > friend[idx].unit.DEF + friend[idx].unit.weapon.DEF);
				break;
			case 2:
				friend[idx].INT = friend[idx].unit.INT * magnification /100;
				buff = (friend[idx].INT > friend[idx].unit.INT + friend[idx].unit.weapon.INT);
				break;
			case 3:
				friend[idx].MEN = friend[idx].unit.MEN * magnification /100;
				buff = (friend[idx].MEN > friend[idx].unit.MEN + friend[idx].unit.weapon.MEN);
				break;
			case 4:
				friend[idx].AGI = friend[idx].unit.AGI * magnification /100;
				buff = (friend[idx].AGI > friend[idx].unit.AGI + friend[idx].unit.weapon.AGI);
				break;
			case 5:
				friend[idx].DEX = friend[idx].unit.DEX * magnification /100;
				buff = (friend[idx].DEF > friend[idx].unit.DEF + friend[idx].unit.weapon.DEF);
				break;
			case 6:
				friend[idx].LUCK = friend[idx].unit.LUCK*magnification /100;
				buff = (friend[idx].LUCK > friend[idx].unit.LUCK + friend[idx].unit.weapon.LUCK);
				break;
			case 7:
				friend[idx].MOVE = friend[idx].unit.MOVE*magnification /100;
				buff = (friend[idx].MOVE > friend[idx].unit.MOVE + friend[idx].unit.weapon.MOVE);
				break;
			}
			panel.setScreenParameta_r_buff(buff);
		}
	}

	public void payCost(int ptr_idx, int skill_idx) {
		if(myTurn){
			friend[ptr_idx].SP -= friend[ptr_idx].unit.skill[skill_idx].cost;
		}else{
			enemy[ptr_idx].SP -= enemy[ptr_idx].unit.skill[skill_idx].cost;
		}
		//panel.updatePiece(friend,enemy);
	}

	public void MagicpayCost(int ptr_idx, int Magic_idx) {
		if(myTurn){
			friend[ptr_idx].SP -= friend[ptr_idx].unit.magic[Magic_idx].cost;
		}else{
			enemy[ptr_idx].SP -= enemy[ptr_idx].unit.magic[Magic_idx].cost;
		}
		//panel.updatePiece(friend,enemy);
	}

	public boolean victoryCheck() {
		for(int i=0 ; i<enemy.length ; i++) if(enemy[i].alive) return false;
		return true;
	}
	public boolean defeatCheck() {
		for(int i=0 ; i<friend.length ; i++) if(friend[i].alive) return false;
		return true;
	}
	public void ItemEffect(int heal , int recovery , boolean cure , boolean myTurn , int receiver_idx ,int user_idx){
		if(myTurn){
			if(heal > 0){
				friend[receiver_idx].HP += heal;
				System.out.println(friend[user_idx].unit.name+"により"+friend[receiver_idx].unit.name+" の HP が"+heal+"回復");
				if(friend[receiver_idx].HP > friend[receiver_idx].unit.HP+friend[receiver_idx].unit.weapon.HP) friend[receiver_idx].HP = friend[receiver_idx].unit.HP+friend[receiver_idx].unit.weapon.HP;
				System.out.println(friend[receiver_idx].unit.name+" の HP :"+friend[receiver_idx].HP);
			}
			if(recovery >0){
				friend[receiver_idx].SP += recovery;
				System.out.println(friend[receiver_idx].unit.name+" の SP が"+heal+"回復");
				if(friend[receiver_idx].SP > friend[receiver_idx].unit.SP+friend[receiver_idx].unit.weapon.SP) friend[receiver_idx].SP = friend[receiver_idx].unit.SP+friend[receiver_idx].unit.weapon.SP;
				System.out.println(friend[receiver_idx].unit.name+" の SP :"+friend[receiver_idx].SP);
			}
			if(cure){
				for(int i = 0; i < friend[receiver_idx].condition.length; i++){
					if(friend[receiver_idx].condition[i] > 0) friend[receiver_idx].condition[i] = 0;
				}
			}
				friend[user_idx].acted();

			}else{
				if(heal > 0){
					enemy[receiver_idx].HP += heal;
					System.out.println(enemy[receiver_idx].unit.name+" の HP が"+heal+"回復");
					if(enemy[receiver_idx].HP > enemy[receiver_idx].unit.HP+enemy[receiver_idx].unit.weapon.HP) enemy[receiver_idx].HP = enemy[receiver_idx].unit.HP+enemy[receiver_idx].unit.weapon.HP;
					System.out.println(enemy[receiver_idx].unit.name+" の HP :"+enemy[receiver_idx].HP);
				}
				if(recovery >0){
					enemy[receiver_idx].SP += recovery;
					System.out.println(enemy[receiver_idx].unit.name+" の SP が"+heal+"回復");
					if(enemy[receiver_idx].SP > enemy[receiver_idx].unit.SP+enemy[receiver_idx].unit.weapon.SP) enemy[receiver_idx].SP = enemy[receiver_idx].unit.SP+enemy[receiver_idx].unit.weapon.SP;
					System.out.println(enemy[receiver_idx].unit.name+" の SP :"+enemy[receiver_idx].SP);
				}
				if(cure){
					for(int i = 0; i < enemy[receiver_idx].condition.length; i++){
						if(enemy[receiver_idx].condition[i] > 0) enemy[receiver_idx].condition[i] = 0;
					}
				}
				enemy[user_idx].acted();
			}
			panel.updatePiece(friend,enemy);
		}

	public void updatePiece() {
		panel.updatePiece(friend, enemy);

	}

	}
