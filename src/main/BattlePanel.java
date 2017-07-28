package main;

import java.applet.AppletContext;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.Clip;
import javax.swing.JApplet;

public class BattlePanel extends JApplet implements ActionListener{
	AppletContext ac; // アプレットのコンテキスト
	Dimension size; // アプレットの大きさ
	MainPanel mp;

	//ここからBattleMain
	BattleView view;
	BattleSystem system;
	BattleCommandView command;
	BattleKeyManagement key;
	BattleAI ai;
	SkillList skill_list ;
	WeaponList weapon_list;
	ItemControl item;

	int[][] map;
	int[][] field;

	Piece[] friend;
	Piece[] enemy;

	boolean PvP = false;

	// コンストラクタ
	public BattlePanel(AppletContext ac1, Dimension size1, MainPanel mp1, int[][] map, int[][] field, Piece[] friend, Piece[] enemy, Clip mapMusic,boolean vsAI) {
		ac = ac1;
		size = size1;
		mp = mp1;

		this.map = map;
		this.field = field;
		this.friend = friend;
		this.enemy = enemy;
		this.PvP = (vsAI) ? false : true;

		// ボタンの配置
		Font f = new Font("SansSerif", Font.BOLD, 20);
		FontMetrics fm = getFontMetrics(f);

		view=new BattleView(this,mp,map,field,enemy,friend,mapMusic);
		ai=new BattleAI(this,map,field,enemy,friend);
		system=new BattleSystem(this,map,field,enemy,friend);
		command=new BattleCommandView(this,enemy,friend);
		key = new BattleKeyManagement(this,mp);
		item = new ItemControl(this);
		if(mp.vsAI)item.readItem(false);
		else item.readItem(MainPanel.everyone);

		setSize(size);
		Container pane = getContentPane();

		pane.setBackground(Color.WHITE);
		pane.setLayout(null);

		view.setBounds(0, 0, 600, 400);
		pane.add(view);

		command.setBounds(0, 400, 600, 200);
		pane.add(command);

		pane.setLayout(null);
		PvPmode();
	}

	public void paint(Graphics g){
		super.paint(g);
	}

	// ボタンがクリックされたときの処理
	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == bt[0]) //GameMenuPanelへ
//			mp.state = 1;
//		else if(e.getSource() == bt[1]){//StoryPanelへ
//			mp.setNextPanel(1);
//			mp.setStoryNum(1);//storyNumに+1をする予定
//			mp.removeP();
//			mp.state = 2;
//		}
//		else if(e.getSource() == bt[2])//MapPanel
//			mp.state = 3;
//		else if(e.getSource() == bt[3])//StatePanel
//			mp.state = 4;
//		else if(e.getSource() == bt[4])//BattlePanel
//			mp.state = 5;
//		else if(e.getSource() == bt[5])//GameOverPanel
//			mp.state = 6;
//		else if(e.getSource() == bt[6])//BattleScreenPanel
//			mp.state = 7;
	}

	//ここからＭａｉｎＢａｔｔｌｅ
	public void updateMap(int[][] map){
		view.updateMap(map);
		ai.updateMap(map);
	}

	public void targetSelect(Piece ptr,int skill_idx){
		view.targetSelect(ptr, skill_idx);
	}

	public void moveSelect(Piece ptr){
		view.moveSelect(ptr);
	}
	public void attack(int ptr_idx, int enemy_idx, int skill_idx){
		system.attack(ptr_idx,enemy_idx, skill_idx);
	}
	public void magicAttack(int ptr_idx, int enemy_idx, int magic_idx){
		system.magicAttack(ptr_idx,enemy_idx, magic_idx);
	}

	public void support(int ptr_idx, int target_idx, int skill_idx){
		system.support(ptr_idx,target_idx, skill_idx);
	}

	public void magicSupport(int ptr_idx, int target_idx, int magic_idx){
		system.magicSupport(ptr_idx,target_idx, magic_idx);
	}
	/*public void nextMotion(){
		command.nextMotion();
	}

	public void getPhase(){
		system.getPhase();
	}

	public void endPhase(){
		command.endPhase();
		system.enemyPhase();
	}
	*/
	public void endPhase(){
		if(!PvP)setState(2);
		else setState(1);

		command.endPhase();
		view.endPhase();
		system.endPhase();
		key.endPhase();
	}

	public void enemyPhase(){
		if(!PvP) ai.enemyPhase();
	}

	public void showState(Piece ptr) {
		command.showState(ptr);
	}

	public void updateCommand(int select_num) {
		command.updateCommand(select_num);

	}
	public void updateView(int[] target_position) {

		view.updateView(target_position);

	}
	public void changeSelect() {

		command.changeSelect();

	}
	public void reselect() {
		key.reselect();

	}
	public void setState(int state) {
		key.setState(state);

	}
	public void changeMode() {
		view.changeMode();

	}
	public boolean cursorMovable(int x, int y) {

		return view.cursorMovable(x,y);

	}
	public boolean selectable(int i) {

		return command.selectable(i);
	}
	public void backSelect() {

		 command.backSelect();

	}
	public void backMode() {
		view.backMode();

	}
	public void ptrLost() {
		command.ptrLost();

	}
	public void changeDirection() {
		view.changeDirection();

	}
	public void setDirection(int idx, int direction) {
		system.setDirection(idx, direction);
	}

	public void updatePiece(Piece[] friend, Piece[] enemy) {
		view.updatePiece(friend,enemy);
		ai.updatePiece(friend, enemy);
		command.updatePiece(friend,enemy);
	}
	public void updatePiece() {
		system.updatePiece();
	}
	public void movePosition(int ptr_idx, int i, int j) {
		system.movePosition(ptr_idx , i ,j);

	}
	public void getPhase() {
		system.getPhase();
		command.getPhase();
		view.getPhase();
	}

//	public void enemy_attack(int idx, int i, int j) {
//		system.enemy_attack(idx, i, j);
//
//	}
//	public void enemy_movePosition(int idx, int i, int j) {
//		system.enemy_movePosition(idx, i, j);
//
//	}

	public void counterAttack(int ptr_idx, int enemy_idx) {
		system.counterAttack(ptr_idx, enemy_idx);

	}
	public void turnAround(int attacker_idx, int receiver_idx) {
		system.turnAround(attacker_idx,receiver_idx);

	}
	public void movable(int[] start_posi,int ptr_x, int ptr_y, int move) {
		system.movable(start_posi,ptr_x,ptr_y,move);

	}
	public void initMoveMap() {
		system.initMoveMap();

	}
	public void updateMoveMap(int[][] move_map) {
		view.updateMoveMap(move_map);
		ai.updateMoveMap(move_map);
	}
//	public void enemy_counterAttack(int ptr_idx, int enemy_idx) {
//		system.enemy_counterAttack(ptr_idx, enemy_idx);
//
//	}
//	public void enemy_turnAround(Piece[] friend, Piece[] enemy, int ptr_idx,int i) {
//		system.enemy_turnAround(friend, enemy, ptr_idx, i);
//
//	}


	 //ここから変更


	public Piece[] getFriend(){
		System.out.println("OK");
		return friend;
	}

	public void setMpState(int num){
		mp.state = num;
	}

	//ここから山下変更
	public void magicSelect(Piece ptr, int select_num) {
		view.magicSelect(ptr,select_num);
	}
	public void PvPmode() {
		//vsAI = true;
		view.PvPmode(PvP);
		command.PvPMode(PvP);
		system.PvPMode(PvP);
		key.PvPmode(PvP);
	}

	public void setScreenParameta_hit(boolean b) {
		view.setScreenParameta_hit(b);
	}

	public void setScreenParameta_critical(boolean b) {
		view.setScreenParameta_critical(b);
	}

	public void setScreenParameta_damage(int damage) {
		view.setScreenParameta_damage(damage);
	}

	public void setScreenParameta_drain(int i) {
		view.setScreenParameta_drain(i);
	}

	public void setScreenParameta_c_hit(boolean b) {
		view.setScreenParameta_c_hit(b);
	}
	public void setScreenParameta_c_critical(boolean b) {
		view.setScreenParameta_critical(b);
	}

	public void setScreenParameta_c_damage(int damage) {
		view.setScreenParameta_c_damage(damage);
	}
	public void setScreenParameta_s_condition(int kind) {
		view.setScreenParameta_s_condition(kind);
	}
	public void setScreenParameta_a_buff(boolean buff) {
		view.setScreenParameta_a_buff(buff);
	}
	public void setScreenParameta_r_buff(boolean buff) {
		view.setScreenParameta_r_buff(buff);
	}
	public void payCost(int ptr_idx, int skill_idx) {
		system.payCost(ptr_idx,skill_idx);

	}
	public void MagicpayCost(int ptr_idx, int Magic_idx) {
		system.MagicpayCost(ptr_idx,Magic_idx);

	}

	public void victoryCheck() {
		if(system.victoryCheck()) view.victory();
	}

	public void defeatCheck() {
		if(system.defeatCheck()) view.defeat();
	}
	public void victory() {
		view.victory();
	}
	public void defeat() {
		view.defeat();
	}

	public void nextPanel() {
		key.nextPanel();
	}

	public int[] havingItemList(boolean myTurn){
		return item.havingItemList( myTurn);
	}
	public String getItemName(int id , boolean myTurn){
		return item.getItemName(id , myTurn);
	}
	public void itemSelect(Piece ptr, int item_id){
		view.itemSelect(ptr, item_id);
	}
	public void useItem(int item_id , boolean myTurn , int receiver_idx ,int user_idx){
		item.useItem(item_id , myTurn , receiver_idx,user_idx);
	}
	public void ItemEffect(int heal , int recovery, boolean cure , boolean myTurn ,int receiver_idx ,int user_idx){
		system.ItemEffect(heal,recovery,cure,myTurn,receiver_idx,user_idx);
	}
	public void peekEnemyStatus(int i) {
		command.peekEnemyStatus(i);
	}
	public void peekFriendStatus(int k) {
		command.peekFriendStatus(k);
	}
	public void missTargetStatus() {
		command.missTargetStatus();
	}

	public void setSelect_num(int s) {
		key.setSelect_num(s);
	}

	public void setCursorPosi(int[] c_p){
		key.setCursorPosi(c_p);
	}
	public void openCombat(){
		ai.openCombat();
	}
	public void endCombat(){
		ai.endCombat();
	}

	public void aiStop() {
		ai.stopAction();
	}

}