package main;

import java.util.Arrays;

public class BattleAI implements Runnable {

	BattlePanel panel;
	
	float[][] environment ;
	int[][] threat;
	int[][] threat_;
	int[][] checked;
	int[][] attackable;
	int[][] supportable;

	int[][] map;
	int[][] field;
	int[][] move_map;
	int[] target_posi = new int[2];
	Piece[] enemy;
	Piece[] friend;

	boolean diagonally = false;

	final int attack = 0;
	final int item   = 1;
	final int skill  = 2;
	final int magic  = 3;
	final int move   = 4;
	final int wait   = 5;

	Thread th;
	int timer = 0;

	int enemy_idx =0;
	int select_num = 0;

	int direction = 0;

	int first_motion = 0;
	int first_idx = 0;
	int[] first_posi = new int[2];

	int second_motion = 0;
	int second_idx = 0;
	int[] second_posi = new int[2];

	int action = 0;
	int play_motion = 0;
	int play_idx = 0;
	int[] play_posi = new int[2];

	boolean combatting = false;

	///////////////////以下地理
	int[] enemyCanSupportPosition = {0,0};
	int[] enemyCanAttackPosition = {0,0};
	Item[][] itemObj;
	boolean acting = true;//行動可能かどうか
	boolean loop;
	boolean looped;
	//int enemyAttackFriendNum = 0;
	////////////////////以上地理

	boolean stopAction = false;

	public BattleAI(BattlePanel panel, int[][] map,int[][] field, Piece[] enemy, Piece[] friend) {
		this.panel = panel;
		this.map = new int[map.length][map[0].length];
		this.field = new int[field.length][field[0].length];
		this.move_map = new int[map.length][map[0].length];

		environment = new float[map.length][map[0].length];
		threat = new int[map.length][map[0].length];
		threat_ = new int[map.length][map[0].length];
		checked = new int[map.length][map[0].length];
		attackable = new int[map.length][map[0].length];

		supportable = new int[map.length][map[0].length]; ////////地理作

		for(int i=0 ; i<this.map.length ; i++){
			this.map[i] = Arrays.copyOf(map[i], map[i].length);
			this.field[i] = Arrays.copyOf(field[i], field[i].length);
			this.move_map[i] = Arrays.copyOf(map[i], map[i].length);
		}
		this.friend = Arrays.copyOf(friend, friend.length);
		this.enemy = Arrays.copyOf(enemy, enemy.length);

		// for(int i=0 ; i<map.length ;
		// i++)System.out.println(Arrays.toString(map[i]));
	}

	public void updateMap(int[][] map) {
		for (int i = 0; i < this.map.length; i++)
			this.map[i] = Arrays.copyOf(map[i], map[i].length);
		//System.out.println("ai map");
		//for(int i=0 ; i<map.length ; i++)System.out.println(Arrays.toString(this.map[i]));
	}

	public void updatePiece(Piece[] friend, Piece[] enemy) {
		this.friend = Arrays.copyOf(friend, friend.length);
		this.enemy = Arrays.copyOf(enemy, enemy.length);

	}

	public void enemyPhase() {

		boolean actable = (enemy[enemy_idx].alive &&
				enemy[enemy_idx].condition[Piece.freeze] <= 0 &&
				enemy[enemy_idx].condition[Piece.piyopiyo] <= 0 &&
				enemy[enemy_idx].condition[Piece.paralysis] <= 0 &&
				enemy[enemy_idx].condition[Piece.sleep] <= 0) ;
		if(actable){
			action = 0;
			acting = true;
			enemyAction(enemy_idx);
			start();
		}
		else{
			//enemy_idx++;
			//enemyPhase();
			play_motion = wait;
			nextMotion();
		}


	}

	public void enemyAction(int idx){
		System.out.println(enemy[enemy_idx].unit.name+"の行動を決める");
		////////////////////////////////////////////////////////////以下地理作
		if(acting){//回復する場合
			System.out.println("1:HPが1/3以下の味方が一人でもいるか？");
			for(int i=0;i<enemy.length;i++){
				if(enemy[i].HP<(enemy[i].unit.HP+enemy[i].unit.weapon.HP)/3&&enemy[i].alive){
					System.out.println("1:yes");
					enemyActionRecovery(idx,i);
					break;
				}
			}
		}

		if(acting){//回復しない場合
			System.out.println("1:no");
			loop = false;
			looped = false;

			do{
				System.out.println("       do");
				loop = false;

				int maxcost=0;
				int maxcostSkillidx = 0;
				int maxcostMagic = 0;
				int maxcostMagicidx = 0;
				for(int i = 1 ; i < enemy[idx].unit.skill.length ; i++ ){
					if(maxcost<enemy[idx].unit.skill[i].cost&&enemy[idx].unit.skill[i].support==false){
						maxcost = enemy[idx].unit.skill[i].cost;
						maxcostSkillidx = i;
					}
				}
				//System.out.println("maxcostSkillidx = "+maxcostSkillidx);

				for(int i = 1 ; i < enemy[idx].unit.magic.length ; i++ ){
					if(maxcostMagic<enemy[idx].unit.magic[i].cost&&enemy[idx].unit.magic[i].support==false){
						maxcostMagic = enemy[idx].unit.magic[i].cost;
						maxcostMagicidx = i;
					}
				}

				if(enemy[idx].unit.magic.length==1){

					makeAttackableMap(idx,maxcostSkillidx);
					System.out.println("2-2:自分のHPが1/2以下か？(skill)");
					if(enemyCanAttack(idx)&&enemy[idx].SP>=enemy[idx].unit.skill[maxcostSkillidx].cost&&enemy[idx].HP<(enemy[idx].unit.HP+enemy[idx].unit.weapon.HP)/2
							&&looped==false){
						System.out.println("2-2:yes(skill)");
						System.out.println("2-3:SPが重い技が当たるか？(skill)");
						System.out.println("2-3:yes(skill)");
						
						for(int i=0;i<attackable.length;i++){
							System.out.println("attackable : " +Arrays.toString(attackable[i]));
						}

						first_motion = skill;
						first_idx = maxcostSkillidx;
						first_posi = Arrays.copyOf(calcCursorPosi(idx,maxcostSkillidx,enemy[idx].position),2);
						second_motion = wait;
						acting =  false;
					}
					else if(enemyCanAttack(idx)&&enemy[idx].SP>=enemy[idx].unit.skill[maxcostSkillidx].cost&&enemy[idx].HP<(enemy[idx].unit.HP+enemy[idx].unit.weapon.HP)/2
							&&looped){
						System.out.println("2-2:yes(skill)");
						System.out.println("2-3:SPが重い技が当たるか？(skill)");
						System.out.println("2-3:yes(skill)");
						for(int i=0;i<attackable.length;i++){
							System.out.println("attackable : " +Arrays.toString(attackable[i]));
						}
						second_motion = skill;
						second_idx = maxcostSkillidx;
						second_posi = Arrays.copyOf(calcCursorPosi(idx,maxcostSkillidx,first_posi),2);
						acting = false;
					}

					else{
						System.out.println("2-2:no or 2-3:no(skill)");
						int maxcostSkillidx2 = 0;
						maxcost = 0;
						//System.out.println("maxcostSkillidx = "+maxcostSkillidx);
						for(int i = 1 ; i < enemy[idx].unit.skill.length ; i++ ){
							if(i == maxcostSkillidx)
								continue;

							if(maxcost<enemy[idx].unit.skill[i].cost && enemy[idx].unit.skill[i].support==false){
								maxcost = enemy[idx].unit.skill[i].cost;
								//System.out.println("maxcostSkillidx2が入ったよ ");
								maxcostSkillidx2 = i;
							}
						}
						for(int i=1;i<enemy[idx].unit.skill.length;i++){
							if(enemy[idx].unit.skill[i].support){
								maxcostSkillidx2 = maxcostSkillidx;
							}
						}
						makeAttackableMap(idx,maxcostSkillidx2);
						System.out.println("2-4:SPが軽い技が当たるか？(skill)");
						if((enemy[idx].unit.name.equals("北海道")||enemy[idx].unit.name.equals("沖縄"))&&enemy[idx].HP>(enemy[idx].unit.HP+enemy[idx].unit.weapon.HP)*80/100){
							maxcostSkillidx2 = 3;
							System.out.println("北海道か沖縄が8割以上");
							makeAttackableMap(idx,maxcostSkillidx2);
						}
						//System.out.println("maxcostSkillidx2 = "+maxcostSkillidx2);
						if(enemyCanAttack(idx)&&enemy[idx].SP>=enemy[idx].unit.skill[maxcostSkillidx2].cost&&looped==false){
							System.out.println("2-4:yes(skill)");
							first_motion = skill;
							first_idx = maxcostSkillidx2;
							first_posi = Arrays.copyOf(calcCursorPosi(idx,maxcostSkillidx2,enemy[idx].position),2);
							second_motion = wait;
							acting = false;
						}
						else if(enemyCanAttack(idx)&&enemy[idx].SP>=enemy[idx].unit.skill[maxcostSkillidx2].cost&&looped){
							second_motion = skill;
							second_idx = maxcostSkillidx2;
							second_posi = Arrays.copyOf(calcCursorPosi(idx,maxcostSkillidx2,first_posi),2);
							acting = false;
						}
						else{
							System.out.println("2-4:no(skill)");
							makeAttackableMap(idx,0);
							System.out.println("2-5:移動して通常技が当たるか？(skill)");
							if(enemyCanMoveAttack(idx)){
								System.out.println("2-5:yes(skill)");
								if(looped==false){
									first_motion = move;
									first_posi = Arrays.copyOf(enemyCanAttackPosition,2);
									//enemy[idx].position = Arrays.copyOf(enemyCanAttackPosition,2);
									second_motion = skill;
									second_idx = 0;
									second_posi = Arrays.copyOf(calcCursorPosi(idx,0,first_posi),2);
								}
								else{
									second_motion = wait;
								}
								acting = false;
							}else{
								System.out.println("2-5:no(skill)");
								//最接近と待機
								if(looped==false){
									first_motion = move;
									first_posi = Arrays.copyOf(searchClosestApproach(idx),2);
									looped = true;
									loop = true;
									//first_posi =  Arrays.copyOf(calcCursorPosi(idx,0,searchClosestApproach(idx)),2);//www
									//enemy[idx].position = Arrays.copyOf(searchClosestApproach(idx),2);
								}
								else{
									second_motion = wait;
								}
								//second_motion = wait;
								//acting = false;



							}

						}
					}

				}

				else{//////////////以下マジック
					makeAttackableMapMagic(idx,maxcostMagicidx);
					System.out.println("2-2:自分のHPが1/2以下か？(magic)");
					if(enemyCanAttack(idx)&&enemy[idx].SP>=enemy[idx].unit.magic[maxcostMagicidx].cost&&enemy[idx].HP<(enemy[idx].unit.HP+enemy[idx].unit.weapon.HP)/2
							&&looped==false){
						System.out.println("2-2:yes(magic)");
						System.out.println("2-3:SPが重い技が当たるか？(magic)");
						System.out.println("2-3:yes(magic)");
						first_motion = magic;
						first_idx = maxcostMagicidx;
						first_posi = Arrays.copyOf(calcCursorPosiMagic(idx,maxcostMagicidx,enemy[idx].position),2);
						second_motion = wait;
						acting =  false;
					}
					else if(enemyCanAttack(idx)&&enemy[idx].SP>=enemy[idx].unit.magic[maxcostMagicidx].cost&&enemy[idx].HP<(enemy[idx].unit.HP+enemy[idx].unit.weapon.HP)/2
							&&looped){
						second_motion = magic;
						second_idx = maxcostMagicidx;
						second_posi = Arrays.copyOf(calcCursorPosiMagic(idx,maxcostMagicidx,first_posi),2);
						acting =  false;
					}

					else{
						System.out.println("2-2:no or 2-3:no(magic)");
						int maxcostMagicidx2 = 0;
						maxcostMagic = 0;
						for(int i = 1 ; i < enemy[idx].unit.magic.length ; i++ ){
							if(i == maxcostMagicidx)
								continue;

							if(maxcostMagic<enemy[idx].unit.magic[i].cost&&enemy[idx].unit.magic[i].support==false){
								maxcostMagic = enemy[idx].unit.magic[i].cost;
								maxcostMagicidx2 = i;
							}
						}
						for(int i=1;i<enemy[idx].unit.magic.length;i++){
							if(enemy[idx].unit.magic[i].support){
								maxcostMagicidx2 = maxcostMagicidx;
							}
						}
						makeAttackableMapMagic(idx,maxcostMagicidx2);
						System.out.println("2-4:SPが軽い技が当たるか？(magic)");
						//System.out.println("maxcostMagicidx2 = "+maxcostMagicidx2);
						if(enemyCanAttack(idx)&&enemy[idx].SP>=enemy[idx].unit.magic[maxcostMagicidx2].cost&&looped == false){
							System.out.println("2-4:yes(magic)");
							//System.out.println("         magic name"+enemy[idx].unit.magic[maxcostMagicidx2].name);

							first_motion = magic;
							first_idx = maxcostMagicidx2;
							first_posi = Arrays.copyOf(calcCursorPosiMagic(idx,maxcostMagicidx2,enemy[idx].position),2);
							second_motion = wait;
							acting = false;
						}
						else if(enemyCanAttack(idx)&&enemy[idx].SP>=enemy[idx].unit.magic[maxcostMagicidx2].cost&&looped){
							System.out.println("2-4-1:yes(magic)");
							//System.out.println("         magic name"+enemy[idx].unit.magic[maxcostMagicidx2].name);
							second_motion = magic;
							second_idx = maxcostMagicidx2;
							second_posi = Arrays.copyOf(calcCursorPosiMagic(idx,maxcostMagicidx2,first_posi),2);
							acting = false;
						}
						else{
							System.out.println("2-4:no(magic)");
							makeAttackableMap(idx,0);
							for(int i=0 ; i<attackable.length ; i++)
								//System.out.println("attackable : "+Arrays.toString(attackable[i]));
								//System.out.println("2-5:移動して通常技が当たるか？(magic-skill)");
								if(enemyCanMoveAttack(idx)){
									System.out.println("2-5:yes(magic-skill)");
									if(looped==false){
										first_motion = move;
										first_posi = Arrays.copyOf(enemyCanAttackPosition,2);
										//enemy[idx].position = Arrays.copyOf(enemyCanAttackPosition,2);
										second_motion = skill;
										second_idx = 0;
										second_posi = Arrays.copyOf(calcCursorPosi(idx,0,first_posi),2);
									}
									else{
										second_motion = wait;
									}
									acting = false;
								}else{
									System.out.println("2-5:no(magic-skill)");
									//最接近と待機
									if(looped==false){
										first_motion = move;
										first_posi = Arrays.copyOf(searchClosestApproach(idx),2);
										looped = true;
										loop = true;
										//first_posi = Arrays.copyOf(calcCursorPosi(idx,0,searchClosestApproach(idx)),2);//ww
										//enemy[idx].position = Arrays.copyOf(searchClosestApproach(idx),2);
									}
									else{
										second_motion = wait;
									}
									//second_motion = wait;
									//acting = false;
								}

						}
					}
				}



				/*
				else{//敵が近くにいない場合//
					System.out.println("2-1:no");
					if(looped==false){
						first_motion = move;
						first_posi = Arrays.copyOf(searchCloserApproach(idx),2);
						//enemy[idx].position = Arrays.copyOf(searchCloserApproach(idx),2);
						loop = true;
						looped = true;
					}
					else{
						second_motion = wait;
						acting = false;
					}

				}//
				 */

				System.out.println("       while");
			}while(loop);
		}
		//////////////////////////////////////////////////////////////////以上地理作
	}

	void showEnvi(){
		System.out.println("threat :");
		for(int a=0 ; a<map.length ; a++) System.out.println(Arrays.toString(threat[a]));
		System.out.println("attackable :");
		for(int a=0 ; a<map.length ; a++) System.out.println(Arrays.toString(attackable[a]));
		System.out.println("environment :");
		for(int a=0 ; a<map.length ; a++) System.out.println(Arrays.toString(environment[a]));
	}



	public void makeThreatMap(int x, int y, int dist) {

		if (x - 1 >= 0)
			if ( map[y][x - 1] <= 0 && checked[y][x - 1] == 0) {
				threat_[y][x - 1] = dist;
				checked[y][x - 1] = 1;
				// System.out.println(" checked at ( "+(x-1)+" , "+y+" ) , dist = "+dist);
			}
		if (x + 1 < map[y].length)
			if ( map[y][x + 1] <= 0 && checked[y][x + 1] == 0) {
				threat_[y][x + 1] = dist;
				checked[y][x + 1] = 1;
				// System.out.println(" checked at ( "+(x+1)+" , "+y+" ) , dist = "+dist);
			}
		if (y - 1 >= 0)
			if ( map[y - 1][x] <= 0 && checked[y - 1][x] == 0) {
				threat_[y - 1][x] = dist;
				checked[y - 1][x] = 1;
				// System.out.println(" checked at ( "+x+" , "+(y-1)+" ) , dist = "+dist);
			}
		if (y + 1 < map.length)
			if ( map[y + 1][x] <= 0 && checked[y + 1][x] == 0) {
				threat_[y + 1][x] = dist;
				checked[y + 1][x] = 1;
				// System.out.println(" checked at ( "+x+" , "+(y+1)+" ) , dist = "+dist);
			}

		int[] min_cell = searchMinCell();
		if (min_cell[0] != -1) {
			checked[min_cell[1]][min_cell[0]] = 2;
			makeThreatMap(min_cell[0], min_cell[1],
					threat_[min_cell[1]][min_cell[0]] + 1);
		}

	}

	public int[] searchMinCell() {
		int[] min_cell = { -1, -1 };
		int min_dist = 100;
		for (int i = 0; i < map[0].length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (checked[j][i] == 1) {
					if (min_dist > threat_[j][i]) {
						min_dist = (int) threat_[j][i];
						min_cell[0] = i;
						min_cell[1] = j;
					}
				}
			}
		}
		// System.out.println(" minimam cell position = " +
		// Arrays.toString(min_cell));
		return min_cell;

	}

	public void mapNomalize() {
		/*
		float max = 0;
		float min = 100;
		for (int i = 0; i < threat.length; i++) {
			for (int j = 0; j < threat[i].length; j++) {
				if (max < threat[j][i])
					max = threat[j][i];
				if (min > threat[j][i])
					min = threat[j][i];
			}
		}

		for (int i = 0; i < threat.length; i++) {
			for (int j = 0; j < threat[i].length; j++) {
				threat[j][i] = (threat[j][i] - min) / (max - min);
			}
		}
		 */
		for (int i = 0; i < threat[0].length; i++) {
			for (int j = 0; j < threat.length; j++) {
				threat[j][i] /= 10;
			}
		}
	}

	public void makeEnvironment(int idx, float threat, float attackable) {
		//panel.movable(enemy[idx].position, enemy[idx].position[0],enemy[idx].position[1], enemy[idx].move);
		// for(int i = 0; i <
		// move_map.length;i++)System.out.println(Arrays.toString(move_map[i]));
		for (int n = 0; n < environment[0].length; n++)
			for (int m = 0; m < environment.length; m++)
				environment[m][n] = this.threat[m][n] * threat
				+ this.attackable[m][n] * attackable;

		for (int n = 0; n < environment[0].length; n++)
			for (int m = 0; m < environment.length; m++){
				if(move_map[m][n] != 5) environment[m][n] = 0;
				if(map[m][n] != 0)  environment[m][n] = 0;
			}


	}

	public void updateMoveMap(int[][] move_map) {
		for (int i = 0; i < this.move_map.length; i++)
			this.move_map[i] = Arrays.copyOf(move_map[i], move_map[i].length);
		/*
		 * for(int i=0 ; i<map.length ; i++){ for(int j=0; j<map[i].length ;
		 * j++) System.out.print(map[i][j]+"  ,  "); System.out.println(); }
		 */
	}



	public void showEnemyTrack(int time){

		if(play_motion == attack ){
			if(time == 0){
				//	System.out.println("行動する敵にカーソル");
				int[] cursor_posi = {enemy[enemy_idx].position[0], enemy[enemy_idx].position[1]};
				//	System.out.println("corsor_posi : "+Arrays.toString(cursor_posi));
				panel.setCursorPosi(cursor_posi);
				panel.updateView(cursor_posi);
			}else if(time ==  500){                                //ここ変更
				System.out.println("向き変更");
				panel.setDirection(enemy_idx, direction);
			}else if(time ==  1000){
				// 行動選択画面を表示する
				//	System.out.println("行動選択画面を表示");
				panel.changeMode();
			}else if(time ==  1500){
				//行動決定
				//	System.out.println("攻撃するか");
				panel.changeSelect();
				select_num = 0;
			}else if(time == 2000 ){
				// 行動に合わせてカーソル移動
				//	System.out.println("ぶっぱなす位置決定");
				panel.setCursorPosi(play_posi);
				panel.updateView(play_posi);
			}else if(time == 2500 ){
				// 実行
				//	System.out.println("行動実行");
				panel.changeMode();
			}
		}
		else if(play_motion == item || play_motion == skill || play_motion == magic ){
			if(time == 0){
				//	System.out.println("行動する敵にカーソル");
				int[] cursor_posi = {enemy[enemy_idx].position[0], enemy[enemy_idx].position[1]};
				//	System.out.println("corsor_posi : "+Arrays.toString(cursor_posi));
				panel.setCursorPosi(cursor_posi);
				panel.updateView(cursor_posi);
			}else if(time ==  500){                            //ここ変更
				//	System.out.println("向き変更");               //View,Command の updateView にrepaint()追加
				panel.setDirection(enemy_idx, direction);
			}else if(time ==  1000){
				// 行動選択画面を表示する
				//	System.out.println("行動選択画面を表示");
				panel.changeMode();
			}else if(time >  1500 && time <= 2500){
				//skill or magic or item の選択
				//	System.out.println("行動選択中...");
				if(select_num < play_motion)select_num++;
				else timer = 2500;
				panel.updateCommand(select_num);
				//	System.out.println("select_num : "+select_num);
			}else if(time ==  2750){
				//行動決定
				//	System.out.println("行動決定");
				panel.changeSelect();
				select_num = 0;
			}else if(time >  3000 && time <= 4500 ){
				//どの skill or magic or item を使うかを選択
				//	System.out.println("どれをつかおうか...");
				if(play_motion == skill && select_num < play_idx-1)select_num++;
				else if(play_motion != skill && select_num < play_idx)select_num++;
				else timer = 4500;
				panel.updateCommand(select_num);
				//	System.out.println("select_num : "+select_num);
			}else if(time == 4750 ){
				//	System.out.println("これだ！");
				panel.changeSelect();
				select_num =0;
			}else if(time == 5000 ){
				// 行動に合わせてカーソル移動
				//	System.out.println("ぶっぱなす位置決定");
				panel.setCursorPosi(play_posi);
				panel.updateView(play_posi);
			}else if(time == 5500 ){
				// 実行
				//	System.out.println("行動実行");
				panel.changeMode();
			}
		}
		else if(play_motion == move){
			if(time == 0){
				//	System.out.println("行動する敵にカーソル");
				int[] cursor_posi = {enemy[enemy_idx].position[0], enemy[enemy_idx].position[1]};
				//	System.out.println("corsor_posi : "+Arrays.toString(cursor_posi));
				panel.setCursorPosi(cursor_posi);
				panel.updateView(cursor_posi);
			}else if(time ==  500){
				// 行動選択画面を表示する
				//	System.out.println("行動選択画面を表示");
				panel.changeMode();
			}else if(time >  500 && time <= 2000){
				//skill or magic or item の選択
				//	System.out.println("移動したい...");
				if(select_num < play_motion)select_num++;
				else timer = 2000;
				panel.updateCommand(select_num);
				//	System.out.println("select_num : "+select_num);
			}else if(time ==  2250){
				//行動決定
				//	System.out.println("移動するぞ！");
				panel.changeSelect();
				select_num = 0;
			}else if(time == 2500 ){
				// 行動に合わせてカーソル移動
				//	System.out.println("移動後の位置決定");
				panel.setCursorPosi(play_posi);
				panel.updateView(play_posi);
			}else if(time == 3000 ){
				// 実行
				//	System.out.println("移動開始");
				panel.changeMode();
			}
		}
		else{ // play_motion == wait
			if(time == 0){
				//	System.out.println("行動する敵にカーソル");
				int[] cursor_posi = {enemy[enemy_idx].position[0], enemy[enemy_idx].position[1]};
				//	System.out.println("corsor_posi : "+Arrays.toString(cursor_posi));
				panel.setCursorPosi(cursor_posi);
				panel.updateView(cursor_posi);
			}else if(time ==  500){
				// 行動選択画面を表示する
				//	System.out.println("行動選択画面を表示");
				panel.changeMode();
			}else if(time >  500 && time <= 2000){
				//skill or magic or item の選択
				//	System.out.println("エシリア、もう疲れちゃった～");
				if(select_num < play_motion)select_num++;
				else time = 2000;
				panel.updateCommand(select_num);
				//	System.out.println("select_num : "+select_num);
			}else if(time ==  2250){
				//行動決定
				//	System.out.println("まったね～");
				panel.changeSelect();
				select_num = 0;
			}
		}
	}


	public void nextMotion(){
		panel.updateMap(map);
		panel.updatePiece(friend, enemy);

		th = null;
		timer = 0;
		panel.initMoveMap();
		if(enemy_idx == enemy.length-1 && play_motion == wait){    //ここ変更
			System.out.println("敵ターン 終了");
			enemy_idx = 0;
			panel.endPhase();
			panel.setState(BattleKeyManagement.view);

			panel.updateView(friend[0].position);
			panel.setCursorPosi(friend[0].position);
			panel.ptrLost();
			panel.showState(friend[0]);
		}
		else if(play_motion != wait){
			action++;
			start();
		}else{
			enemy_idx++;
			//System.out.println("次の人 : "+enemy[enemy_idx].unit.name);
			enemyPhase();
		}
	}

	public void start() {
		if(th == null){
			th = new Thread(this);
			th.start();
		}
	}

	@Override
	public void run() {
		if(action == 0) {
			play_motion = first_motion;
			play_idx = first_idx;
			play_posi = Arrays.copyOf(first_posi, 2);
		}else if(action == 1){
			play_motion = second_motion;
			play_idx = second_idx;
			play_posi = Arrays.copyOf(second_posi, 2);
		}else if(action == 2){
			play_motion = wait;
		}
		System.out.println("play_motion : "+ play_motion);
		System.out.println("play_idx : "+ play_idx);
		System.out.println("play_posi : "+ Arrays.toString(play_posi));

		int limit = 0;
		if(play_motion == item || play_motion == skill || play_motion == magic )
			limit = 5500;
		else if(play_motion == move)
			limit = 3500;
		else // play_motion == wait
			limit = 2500;

		while((timer <= limit || combatting) && !stopAction){
			showEnemyTrack(timer);
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timer += 250;
			//if(timer % 1000 == 0)System.out.println(timer+"ミリ秒経過");
		}
		//stop();
		if(!stopAction)nextMotion();

	}

	public void openCombat(){
		combatting = true;
	}

	public void endCombat(){
		combatting = false;
	}


	////////////////////////////////////////////////以下地理作
	public boolean enemyCanMoveSupport(int idx){
		panel.movable(enemy[idx].position, enemy[idx].position[0], enemy[idx].position[1], enemy[idx].MOVE);
		for(int i=0;i<supportable.length;i++){
			for(int j=0;j<supportable[0].length;j++){
				if(supportable[i][j]==1&&move_map[i][j]==5){
					enemyCanSupportPosition[0] = j;
					enemyCanSupportPosition[1] = i;
					return true;
				}
			}
		}
		return false;
	}

	public boolean enemyCanMoveAttack(int idx){
		panel.movable(enemy[idx].position, enemy[idx].position[0], enemy[idx].position[1], enemy[idx].MOVE);
		for(int i=0;i<move_map.length;i++){
			System.out.println("move_map 2:"+Arrays.toString(move_map[i]));
		}

		for(int i=0;i<attackable.length;i++){
			for(int j=0;j<attackable[0].length;j++){
				if(attackable[i][j]==1.0){
					//System.out.println("attackable["+i+"]["+j+"]==1.0");
					System.out.println("move_map["+i+"]["+j+"]=="+move_map[i][j]);
					if(move_map[i][j]==5){
						System.out.println("move_map[i][j]==5");
						enemyCanAttackPosition[0] = j;
						enemyCanAttackPosition[1] = i;
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean enemyCanAttack(int idx){
		if(looped==false){
			for(int i=0;i<attackable.length;i++){
				for(int j=0;j<attackable[0].length;j++){
					if(attackable[i][j]==1&& enemy[idx].position[0] == j && enemy[idx].position[1] == i){
						enemyCanAttackPosition[0] = j;
						enemyCanAttackPosition[1] = i;
						return true;
					}
				}
			}
			return false;
		}
		else{
			for(int i=0;i<attackable.length;i++){
				for(int j=0;j<attackable[0].length;j++){
					if(attackable[i][j]==1&& first_posi[0] == j && first_posi[1] == i){
						enemyCanAttackPosition[0] = j;
						enemyCanAttackPosition[1] = i;
						return true;
					}
				}
			}
			return false;
		}
	}



	public boolean enemyCanSupport(int idx){
		if(looped==false){
			for(int i=0;i<supportable.length;i++){
				for(int j=0;j<supportable[0].length;j++){
					if(supportable[i][j]==1&& enemy[idx].position[0] == j && enemy[idx].position[1] == i){
						enemyCanAttackPosition[0] = j;
						enemyCanAttackPosition[1] = i;
						return true;
					}
				}
			}
			return false;
		}
		else{
			for(int i=0;i<supportable.length;i++){
				for(int j=0;j<supportable[0].length;j++){
					if(supportable[i][j]==1&& first_posi[0] == j && first_posi[1] == i){
						enemyCanAttackPosition[0] = j;
						enemyCanAttackPosition[1] = i;
						return true;
					}
				}
			}
			return false;
		}

	}

	public int[] calcCursorPosi(int idx , int skillidx, int[] now_posi){		
		System.out.println("calcCursorPosi");
		int max_unit_attack=0;
		int unit_attack=0;
		int[] position = new int[2];
		for(int i = 0;i<4;i++){//方向
			System.out.println("方向 : "+i+" をチェック");
			for(int j = 0;j<map.length;j++){
				for(int k = 0;k<map[0].length;k++){//inRangeの操作
					if(enemy[idx].unit.skill[skillidx].inRange(now_posi, i, k, j)){
						unit_attack = 0;
						int[] c_p = {k,j};
						System.out.println( Arrays.toString(c_p)+" is inRange!( "+enemy[idx].unit.skill[skillidx].name+")");
						for(int l = 0;l<friend.length;l++){//attackRangeの操作
							if(enemy[idx].unit.skill[skillidx].attackRange(i, c_p, friend[l].position[0],friend[l].position[1] )&&friend[l].alive){
								unit_attack++;
								System.out.println("敵発見 "+unit_attack+"人目");
							}
						}
						if(unit_attack>max_unit_attack){
							System.out.println("攻撃範囲内に最大数の敵 "+unit_attack+"人");
							direction = i;
							max_unit_attack = unit_attack;
							position[0]=k;
							position[1]=j;
							System.out.println("スキル着弾点候補 :  "+ Arrays.toString(position));
						}
						//enemyDirection(now_posi,position);
						System.out.println("calcCursorPosi:向き"+direction);
					}
				}
			}
		}
		System.out.println("スキル着弾点 :  "+ Arrays.toString(position));
		return position;

	}
	public int[] calcCursorPosiMagic(int idx,int skillidx, int[] now_posi){
		System.out.println("calcCursorPosiMagic");
		int max_unit_attack=0;
		int unit_attack=0;
		int[] position = new int[2];
		for(int i = 0;i<4;i++){//方向
			System.out.println("方向 : "+i+" をチェック");
			for(int j = 0;j<map.length;j++){
				for(int k = 0;k<map[0].length;k++){//inRangeの操作
					if(enemy[idx].unit.magic[skillidx].inRange(now_posi, i, k, j)){
						unit_attack =0;
						int[] c_p = {k,j};
						System.out.println( Arrays.toString(c_p)+" is inRange!( "+enemy[idx].unit.magic[skillidx].name+")");
						for(int l = 0;l<friend.length;l++){//attackRangeの操作
							if(enemy[idx].unit.magic[skillidx].attackRange(i, c_p, friend[l].position[0],friend[l].position[1] )&&friend[l].alive){
								unit_attack++;
								System.out.println("敵発見 "+unit_attack+"人目");
							}
						}
						if(unit_attack>max_unit_attack){
							System.out.println("攻撃範囲内に最大数の敵 "+unit_attack+"人");
							direction = i;
							max_unit_attack = unit_attack;
							position[0]=k;
							position[1]=j;
							System.out.println("スキル着弾点候補 :  "+ Arrays.toString(position));
						}

						//enemyDirection(now_posi,position);
					}
				}
			}
		}
		System.out.println("スキル着弾点 :  "+ Arrays.toString(position));
		return position;

	}

	public void enemyDirection(int[] seme,int[] uke){
		int xDistance = uke[0]- seme[0];
		int yDistance = uke[1]- seme[1];
		if(Math.abs(xDistance)>Math.abs(yDistance)){
			if(xDistance>=0){
				direction = Piece.right;
			}
			else{
				direction = Piece.left;
			}
		}
		else{
			if(yDistance>=0){
				direction = Piece.down;
			}
			else{
				direction = Piece.up;
			}
		}
	}

	public void enemyActionRecovery(int idx,int hpDownidx){
		System.out.println("1-1:AはHPが1/3以下か？");
		if(idx==hpDownidx&& panel.item.items[1][0].num!=0){
			System.out.println("1-1:yes");
			first_motion = item;
			first_idx =0;
			first_posi = Arrays.copyOf(enemy[idx].position, enemy[idx].position.length);
			second_motion = wait;
			acting = false;
		}
		else{
			System.out.println("1-1:no");
			System.out.println("1-2:Aは回復技があるか？");
			if(enemy[idx].unit.magic.length==1){
				for(int i=0;i<enemy[idx].unit.skill.length;i++){
					enemyDirection(enemy[idx].position,enemy[hpDownidx].position);
					makeSupportableMap(idx,hpDownidx,i);
					//if(enemy[idx].unit.skill[i].support&&enemyCanMoveSupport(idx)&&enemy[idx].SP>=enemy[idx].unit.skill[i].cost){
					
					if(enemy[idx].unit.skill[i].support&&enemyCanSupport(idx)&&enemy[idx].SP>=enemy[idx].unit.skill[i].cost){
						System.out.println("1-2:yes(skill)");
						//first_motion = move;
						//first_posi = Arrays.copyOf(enemyCanSupportPosition,2);
						//enemy[i].position = Arrays.copyOf(enemyCanSupportPosition,2);
						first_motion = skill;
						first_idx = i;
						first_posi = Arrays.copyOf(enemy[hpDownidx].position, enemy[hpDownidx].position.length);
						second_motion = wait;
						acting = false;
					}
					

				}
			}

			else{
				for(int i=0;i<enemy[idx].unit.magic.length;i++){
					enemyDirection(enemy[idx].position,enemy[hpDownidx].position);
					makeSupportableMapMagic(idx,hpDownidx,i);
					//if(enemy[idx].unit.skill[i].support&&enemyCanMoveSupport(idx)&&enemy[idx].SP>=enemy[idx].unit.skill[i].cost){
					System.out.println("1-2:yes(skill)");
					if(enemy[idx].unit.magic[i].support&&enemyCanSupport(idx)&&enemy[idx].SP>=enemy[idx].unit.magic[i].cost){
						//first_motion = move;
						//first_posi = Arrays.copyOf(enemyCanSupportPosition,2);
						//enemy[i].position = Arrays.copyOf(enemyCanSupportPosition,2);
						first_motion = magic;
						first_idx = i;
						first_posi = Arrays.copyOf(enemy[hpDownidx].position, enemy[hpDownidx].position.length);
						second_motion = wait;
						acting = false;
					}
					

				}
			}

		}


	}

	public int[] searchClosestApproach(int idx){
		panel.movable(enemy[idx].position, enemy[idx].position[0], enemy[idx].position[1], enemy[idx].MOVE);
		makeThreatMap();
		for(int i=0;i<move_map.length;i++){
			System.out.println("move_map :"+Arrays.toString(move_map[i]));
		}
		for(int i=0;i<threat.length;i++){
			System.out.println("threat_map :"+Arrays.toString(threat[i]));
		}


		int min = 100;
		int[] posi = new int[2];
		for(int i=0 ; i<threat[0].length ; i++){
			for(int j=0 ; j<threat.length ; j++){
				if(move_map[j][i] == 5){
					if(min > threat[j][i]  && !cross(i,j)){
						min = (int)threat[j][i];
						posi[0] = i; posi[1] = j;
					}
				}

			}
		}
		System.out.println("atsearchClosestApproach /return posi :"+Arrays.toString(posi));

		return posi;
	}
	public int[] searchCloserApproach(int idx){
		panel.movable(enemy[idx].position, enemy[idx].position[0], enemy[idx].position[1], enemy[idx].MOVE);
		makeThreatMap();


		for(int i=0;i<move_map.length;i++){
			System.out.println("move_map :"+Arrays.toString(move_map[i]));
		}
		for(int i=0;i<threat.length;i++){
			System.out.println("threat_map :"+Arrays.toString(threat[i]));
		}


		int min = 10;
		int[] posi = new int[2];
		for(int i=0 ; i<threat[0].length ; i++){
			for(int j=0 ; j<threat.length ; j++){
				if(move_map[j][i] == 5){
					if(min > threat[j][i] && !near(i,j) && !cross(i,j)){
						min = (int)threat[j][i];
						posi[0] = i; posi[1] = j;
					}
				}

			}
		}
		System.out.println("posi :"+Arrays.toString(posi));

		return posi;
	}

	public boolean near(int x, int y){
		//	System.out.println("near? -> ( "+x+" , "+y+" )");
		for(int i=0 ; i<friend.length ; i++){
			if(x == friend[i].position[0]+1 && y ==  friend[i].position[1] ||
					x == friend[i].position[0]-1 && y ==  friend[i].position[1] ||
					x == friend[i].position[0] && y ==  friend[i].position[1]+1 ||
					x == friend[i].position[0] && y ==  friend[i].position[1]-1 )
				return true;
		}
		return false;
	}

	public void makeAttackableMap(int ene, int skillindex) {/////////////////////////////////////地理作
		// 初期化
		for (int i = 0; i < attackable.length; i++) {
			for (int j = 0; j < attackable[0].length; j++)
				attackable[i][j] = 0;

		}

		// スキルはDefoを使う Defo→skill[0]
		for (int i = 0; i < attackable.length; i++) {
			for (int j = 0; j < attackable[0].length; j++) {
				for (int fri = 0; fri < friend.length; fri++) {// プレイヤーの人数
					for (int dir = 0; dir <= 3; dir++) { // 方向の数
						if(friend[fri].alive)
							if (enemy[ene].unit.skill[skillindex].inRange(//////////////////////////////////地理作
									friend[fri].position, dir, j, i)) {
								target_posi[0] = j;
								target_posi[1] = i;
								for (int k = 0; k < attackable.length; k++) {
									for (int l = 0; l < attackable[0].length; l++) {

										if (enemy[ene].unit.skill[skillindex].attackRange(//////////////////////////////////地理作
												dir,target_posi, l, k)) {
											attackable[k][l] = 1;
										}

									}
								}
							}

					}
					attackable[friend[fri].position[1]][friend[fri].position[0]] = 0;
				}
			}
		}

		// System.out.println("attackable" + ene);
		// for (int i = 0; i < attackable.length; i++)
		// System.out.println(Arrays.toString(attackable[i]));
	}
	public void makeAttackableMapMagic(int ene, int skillindex) {/////////////////////////////////////地理作
		// 初期化
		for (int i = 0; i < attackable.length; i++) {
			for (int j = 0; j < attackable[0].length; j++)
				attackable[i][j] = 0;

		}

		// スキルはDefoを使う Defo→skill[0]
		for (int i = 0; i < attackable.length; i++) {
			for (int j = 0; j < attackable[0].length; j++) {
				for (int fri = 0; fri < friend.length; fri++) {// プレイヤーの人数
					for (int dir = 0; dir <= 3; dir++) { // 方向の数
						if(friend[fri].alive)
							if (enemy[ene].unit.magic[skillindex].inRange(//////////////////////////////////地理作
									friend[fri].position, dir, j, i)) {
								target_posi[0] = j;
								target_posi[1] = i;
								for (int k = 0; k < attackable.length; k++) {
									for (int l = 0; l < attackable[0].length; l++) {

										if (enemy[ene].unit.magic[skillindex].attackRange(//////////////////////////////////地理作
												dir,target_posi, l, k)) {
											attackable[k][l] = 1;
										}

									}
								}
							}

					}
					attackable[friend[fri].position[1]][friend[fri].position[0]] = 0;
				}
			}
		}

		// System.out.println("attackable" + ene);
		// for (int i = 0; i < attackable.length; i++)
		// System.out.println(Arrays.toString(attackable[i]));
	}

	public void makeSupportableMap(int ene, int reene, int skillindex) {/////////////////////////////////////地理作
		// 初期化
		for (int i = 0; i < supportable.length; i++) {
			for (int j = 0; j < supportable[0].length; j++)
				supportable[i][j] = 0;

		}

		// スキルはDefoを使う Defo→skill[0]
		for (int i = 0; i < supportable.length; i++) {
			for (int j = 0; j < supportable[0].length; j++) {
				for (int dir = 0; dir <= 3; dir++) { // 方向の数
					if (enemy[ene].unit.skill[skillindex].inRange(//////////////////////////////////地理作
							enemy[reene].position, dir, j, i)) {
						target_posi[0] = j;
						target_posi[1] = i;
						for (int k = 0; k < supportable.length; k++) {
							for (int l = 0; l < supportable[0].length; l++) {

								if (enemy[ene].unit.skill[skillindex].attackRange(//////////////////////////////////地理作
										dir,target_posi, l, k)) {
									supportable[k][l] = 1;
								}

							}
						}
					}

				}
				//	supportable[enemy[reene].position[1]][enemy[reene].position[0]] = 0;

			}
		}

		// System.out.println("supportable" + ene);
		// for (int i = 0; i < supportable.length; i++)
		// System.out.println(Arrays.toString(supportable[i]));
	}
	public void makeSupportableMapMagic(int ene, int reene, int skillindex) {/////////////////////////////////////地理作
		// 初期化
		for (int i = 0; i < supportable.length; i++) {
			for (int j = 0; j < supportable[0].length; j++)
				supportable[i][j] = 0;

		}

		// スキルはDefoを使う Defo→skill[0]
		for (int i = 0; i < supportable.length; i++) {
			for (int j = 0; j < supportable[0].length; j++) {
				for (int dir = 0; dir <= 3; dir++) { // 方向の数
					if (enemy[ene].unit.magic[skillindex].inRange(//////////////////////////////////地理作
							enemy[reene].position, dir, j, i)) {
						target_posi[0] = j;
						target_posi[1] = i;
						for (int k = 0; k < supportable.length; k++) {
							for (int l = 0; l < supportable[0].length; l++) {

								if (enemy[ene].unit.magic[skillindex].attackRange(//////////////////////////////////地理作
										dir,target_posi, l, k)) {
									supportable[k][l] = 1;
								}

							}
						}
					}

				}
				//	supportable[enemy[reene].position[1]][enemy[reene].position[0]] = 0;

			}
		}

		// System.out.println("supportable" + ene);
		// for (int i = 0; i < supportable.length; i++)
		// System.out.println(Arrays.toString(supportable[i]));
	}

	public boolean into2(int idx){///////地理作（敵がニマス以内にいるどうか）
		for(int fri = 0 ; fri < friend.length ; fri++){
			for(int i = -2 ; i <= 2 ; i++){
				if((enemy[idx].position[1]+i) >= 0 && (enemy[idx].position[1]+i) < map.length){
					System.out.println("into2up "+(enemy[idx].position[1]+i));
					if(map[enemy[idx].position[1]+i][enemy[idx].position[0]] == 1)return true;
				}
			}
		}

		for(int fri = 0 ; fri < friend.length ; fri++){
			for(int i = -2 ; i <= 2 ; i++){
				if((enemy[idx].position[0]+i) >= 0 && (enemy[idx].position[0]+i) < map[0].length){
					System.out.println("into2down "+(enemy[idx].position[0]+i));
					if(map[enemy[idx].position[1]][enemy[idx].position[0]+i] == 1)return true;
				}
			}
		}
		return false;
	}
	public boolean into2(){
		for(int fri = 0 ; fri < friend.length ; fri++){
			for(int i = -2 ; i <= 2 ; i++){
				if((first_posi[1]+i) >= 0 && (first_posi[1]+i) < map.length){
					System.out.println("into2up "+(first_posi[1]+i));
					if(map[first_posi[1]+i][first_posi[0]] == 1)return true;
				}
			}
		}

		for(int fri = 0 ; fri < friend.length ; fri++){
			for(int i = -2 ; i <= 2 ; i++){
				if((first_posi[0]+i) >= 0 && (first_posi[0]+i) < map[0].length){
					System.out.println("into2down "+(first_posi[0]+i));
					if(map[first_posi[1]][first_posi[0]+i] == 1)return true;
				}
			}
		}
		return false;
	}

	public void makeThreatMap(){
		for (int n = 0; n < threat[0].length; n++)
			for (int m = 0; m < threat.length; m++) threat[m][n] =100;

		for (int i = 0; i < friend.length; i++) {
			if(friend[i].alive){
				checked[friend[i].position[1]][friend[i].position[0]] = 2;
				makeThreatMap(friend[i].position[0], friend[i].position[1], 1);

				//			 for(int a=0 ; a<map.length ;
				//			 a++)System.out.println(Arrays.toString(threat_[a]));
				//			 System.out.println();

				for (int n = 0; n < threat[0].length; n++)
					for (int m = 0; m < threat.length; m++)
						if(threat[m][n] > threat_[m][n]) threat[m][n] = threat_[m][n];
				for (int n = 0; n < threat[0].length; n++)
					for (int m = 0; m < threat.length; m++)
						threat_[m][n] = 0;
				for (int n = 0; n < checked[0].length; n++)
					for (int m = 0; m < checked.length; m++)
						checked[m][n] = 0;
				//
				//			 for(int a=0 ; a<map.length ;
				//			 a++)System.out.println(Arrays.toString(threat[a]));
				//			 System.out.println();
			}
		}
		//for(int i=0 ; i<threat.length ; i++) System.out.println("threat : "+Arrays.toString(threat[i]));
	}
	////////////////////////////////////////////////以上地理作
	public boolean cross(int x, int y){
		//System.out.println("cross? -> ( "+x+" , "+y+" )");
		for(int i=0 ; i<enemy.length ; i++){
			if(x == enemy[i].position[0] && y == enemy[i].position[1])
				return true;
		}
		for(int i=0 ; i<friend.length ; i++){
			if(x == friend[i].position[0] && y == friend[i].position[1])
				return true;
		}

		return false;
	}

	public void stopAction() {
		stopAction = true;
	}
}
