package main;

import java.applet.AppletContext;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Arrays;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements Runnable {
	AppletContext ac; // アプレットのコンテキスト
	Dimension size; // アプレットの大きさ
	boolean in_game = true; // ゲーム実行中はtrue
	public int state = 0; // ゲーム状態 0:Title，1:GameMenu，2:Story，3:Map，4:State
	int old_state = 0; // 直前のゲーム状態 4:State, 5:Battle, 6:GameOver, 7:BattleScreen
						// 8:SortieUnitSelectPanel

	JPanel p;
	CardLayout layout;

	TitlePanel tp;
	GameMenuPanel gmp;
	StoryPanel storyp;
	MapPanel mp;
	StatePanel statep;
	BattlePanel bp;
	GameOverPanel gop;
	BattleScreenPanel bsp;
	SortieUnitSelectPanel susp;
	SortiePositionSetPanel sps;
	Player1PreparationPanel p1pp;
	Player2PreparationPanel p2pp;
	Player1CharacterSelectPanel p1csp;
	Player2CharacterSelectPanel p2csp;
	Player1ArmamentsPanel p1ap;
	Player2ArmamentsPanel p2ap;
	Player1StatePanel p1sp;
	Player2StatePanel p2sp;
	ShoppingMain shop;
	FieldSelectPanel fsp;
	Thread td;

	int storyNum;
	int next_panel;

	// BattlePanel
	int[][] field;
	int[][] map;
	Piece[] friend;
	Piece[] enemy;
	boolean showP = false;
	boolean showBP = true;
	Clip mapMusic;

	// BattleScreen
	int effect_id = 0;// エフェクト番号
	boolean hit = false;// 当たったかどうか
	boolean critical = true;// クリティカル
	int damage = 0;
	boolean drain_flag = false;
	int drain = 0;
	boolean counter = false;
	boolean c_hit = false;//
	boolean c_critical = false;
	int c_damage = 0;
	int attack_id;// 攻撃側のキャラid
	int defense_id;// 受け側のキャラid
	int attack_now_hp;// 攻撃側の現在のキャラのhp
	int defense_now_hp;// 受け側の現在のキャラのhp
	int attack_now_sp;// 攻撃側の現在のキャラのsp
	int defense_now_sp;// 受け側の現在のキャラのsp
	int attack_maxhp;// 攻撃側のキャラのmaxhp
	int defense_maxhp;// 受け側のキャラのmaxhp
	int attack_maxsp;// 攻撃側のキャラのmaxsp
	int defense_maxsp;// 受け側のキャラのmaxsp
	boolean[] s_condition;
	boolean[] a_buff;
	boolean[] r_buff;
	
	int pvp_num = 0;
	public static final int  everyone = 0;
	public static final int player1 = 1;
	public static final int player2 = 2;
	 boolean vsAI = true;
	
	 String field_name;
	 
		//MapkeyManagement
		boolean[] cleared;
	 
	// コンストラクタ
	public MainPanel(AppletContext ac1, Dimension size1) {
		System.out.print("MainPanel");
		ac = ac1;
		size = size1;
		
		cleared = new boolean[8];

		p = new JPanel();
		p.setSize(size);
		layout = new CardLayout();
		p.setLayout(layout);

		// グリッドレイアウト
		setLayout(null);

		// ゲームパネルの生成
		tp = new TitlePanel(ac, size, this); // スタート（タイトル）
		add(tp);
		tp.requestFocusInWindow();
		// スレッドの生成
		td = new Thread(this);
		td.start();

	}

	// ゲームの状態を変更
	public void run() {
		while (in_game) {
			try {
				Thread.sleep(1); // 100 ms 毎の実施
			} catch (InterruptedException e) {
			}
			if (state != old_state) {
				// 前のパネルの削除
				if (old_state == 0)
					remove(tp);
				else if (old_state == 1)
					remove(gmp);
				else if (old_state == 2)
					remove(storyp);
				else if (old_state == 3)
					remove(mp);
				else if (old_state == 4)
					remove(statep);
				else if (old_state == 5){
					if(showP)remove(p);
					else{
					showBP();
					layout.show(p, "bsp");
					}
				}
				else if (old_state == 6)
					remove(gop);
				else if (old_state == 7){
					p.remove(bsp);
					layout.show(p, "bp");
				}
				else if (old_state == 8)
					remove(susp);
				else if (old_state == 9)
					remove(sps);
				else if(old_state == 10)
					remove(p1pp);
				else if(old_state == 11)
					remove(p2pp);
				else if(old_state == 12)
					remove(p1csp);
				else if(old_state == 13)
					remove(p2csp);
				else if(old_state == 14)
					remove(p1ap);
				else if(old_state == 15)
					remove(p2ap);
				else if(old_state == 16)
					remove(p1sp);
				else if(old_state == 17)
					remove(p2sp);
				else if(old_state == 18)
					remove(shop);
				else if(old_state == 19)
					remove(fsp);
				// 新しいパネルの追加
				if (state == 20) // ゲーム終了(最後の数字に＋１して！)
					in_game = false;
				else {
					if (state == 0) { // TitlePanel
						tp = new TitlePanel(ac, size, this);
						add(tp);
						tp.requestFocusInWindow();
					} else if (state == 1) { // GameMenuPanel
						pvp_num = everyone;
						vsAI = true;
						gmp = new GameMenuPanel(ac, size, this);
						add(gmp);
						gmp.requestFocusInWindow();
					} else if (state == 2) { // StoryPanel
						try {
							storyp = new StoryPanel(ac, size, this, storyNum, next_panel);
						} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}
						add(storyp);
						storyp.requestFocusInWindow();
					} else if (state == 3) { // MapPanel
						mp = new MapPanel(ac, size, this);
						add(mp);
						mp.requestFocusInWindow();
					} else if (state == 4) { // StatePanel
						statep = new StatePanel(ac, size, this);
						add(statep);
						statep.requestFocusInWindow();
					} else if (state == 5) { // BattlePanel
						if(showBP){
						bp = new BattlePanel(ac, size, this, map, field, friend, enemy,mapMusic,vsAI);
						//add(bp);
						add(p);
						p.add(bp,"bp");
						}
						layout.show(p, "bp");
						bp.requestFocusInWindow();
					} else if (state == 6) { // GameOverPanel
						gop = new GameOverPanel(ac, size, this);
						add(gop);
						gop.requestFocusInWindow();
					} else if (state == 7) { // BattleScreenPanel
						if(showBP){
						try {
							System.out.println("2MPa_buff"+this.a_buff[0]);
							bsp = new BattleScreenPanel(ac, size, this, effect_id, hit, critical, damage, drain_flag,
									drain, counter, c_hit, c_critical, c_damage, attack_id, defense_id, attack_now_hp,
									defense_now_hp, attack_now_sp, defense_now_sp, attack_maxhp, defense_maxhp,
									attack_maxsp, defense_maxsp,s_condition,a_buff,r_buff);
						} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}

						p.add(bsp,"bsp");
						bsp.requestFocusInWindow();

						showBP = false;
						}
						layout.show(p, "bsp");

					} else if (state == 8) { // SortieUnitSelectPanel
						susp = new SortieUnitSelectPanel(ac, size, this, storyNum);
						add(susp);
						susp.requestFocusInWindow();
					} else if (state == 9) { // SortiePositionSetPanel
						sps = new SortiePositionSetPanel(ac, size, this, map, field, friend, enemy,mapMusic);
						add(sps);
						sps.requestFocusInWindow();
					} else if (state == 10) {
						pvp_num = player1;
						vsAI = false;
						p1pp = new Player1PreparationPanel(ac, size, this);
						add(p1pp);
						p1pp.requestFocusInWindow();
					} else if (state == 11) {
						pvp_num = player2;
						vsAI = false;
						p2pp = new Player2PreparationPanel(ac, size, this,field_name);
						add(p2pp);
						p2pp.requestFocusInWindow();
					} else if (state == 12) {
						p1csp = new Player1CharacterSelectPanel(ac, size, this ,field_name);
						add(p1csp);
						p1csp.requestFocusInWindow();
					} else if (state == 13) {
						p2csp = new Player2CharacterSelectPanel(ac, size, this , field_name);
						add(p2csp);
						p2csp.requestFocusInWindow();
					} else if (state == 14) {
						try {
							p1ap = new Player1ArmamentsPanel(ac, size, this);
						} catch (LineUnavailableException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						} catch (UnsupportedAudioFileException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}
						add(p1ap);
						p1ap.requestFocusInWindow();
					} else if (state == 15) {
						try {
							p2ap = new Player2ArmamentsPanel(ac, size, this);
						} catch (LineUnavailableException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						} catch (UnsupportedAudioFileException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}
						add(p2ap);
						p2ap.requestFocusInWindow();
					} else if (state == 16) {
						p1sp = new Player1StatePanel(ac, size, this);
						add(p1sp);
						p1sp.requestFocusInWindow();
					} else if (state == 17) {
						p2sp = new Player2StatePanel(ac, size, this);
						add(p2sp);
						p2sp.requestFocusInWindow();
					} else if (state == 18) {
						try {
							shop = new ShoppingMain(ac, size, this);
						} catch (LineUnavailableException
								| UnsupportedAudioFileException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}
						add(shop);
						shop.requestFocusInWindow();
					}else if (state == 19) {
						fsp = new FieldSelectPanel(ac, size, this);
						add(fsp);
						fsp.requestFocusInWindow();
					} 

					validate(); // コンポーネントが有効な配置であることを確認
					old_state = state;
				}
			}
		}
	}

	public void jumpBattlePanel(int[][] map, int[][] field, Piece[] fri, Piece[] ene,Clip mapMusic) {
		this.map = map;
		this.field = field;
		this.friend = fri;
		this.enemy = ene;
		this.mapMusic = mapMusic;
	}

	public void jumpPositionSetPanel(int[][] map, int[][] field, Piece[] fri, Piece[] ene,Clip mapMusic) {
		this.map = map;
		this.field = field;
		this.friend = fri;
		this.enemy = ene;
		this.mapMusic = mapMusic;
	}

	public void jumpBattleScreenPanel(int effect_id, boolean hit, boolean critical, int damage, boolean drain_flag,
			int drain, boolean counter, boolean c_hit, boolean c_critical, int c_damage, int attack_id, int defense_id,
			int attack_now_hp, int defense_now_hp, int attack_now_sp, int defense_now_sp, int attack_maxhp,
			int defense_maxhp, int attack_maxsp, int defense_maxsp,boolean[] s_condition,boolean[] a_buff, boolean[] r_buff) {
		this.effect_id = effect_id;// エフェクト番号
		this.hit = hit;// 当たったかどうか
		this.critical = critical;// クリティカル
		this.damage = damage;
		this.drain_flag = drain_flag;
		this.drain = drain;
		this.counter = counter;
		this.c_hit = c_hit;//
		this.c_critical = c_critical;
		this.c_damage = c_damage;

		this.attack_id = attack_id;// 攻撃側のキャラid
		this.defense_id = defense_id;// 受け側のキャラid
		this.attack_now_hp = attack_now_hp;// 攻撃側の現在のキャラのhp
		this.defense_now_hp = defense_now_hp;// 受け側の現在のキャラのhp
		this.attack_now_sp = attack_now_sp;// 攻撃側の現在のキャラのsp
		this.defense_now_sp = defense_now_sp;// 受け側の現在のキャラのsp
		this.attack_maxhp = attack_maxhp;// 攻撃側のキャラのmaxhp
		this.defense_maxhp = defense_maxhp;// 受け側のキャラのmaxhp
		this.attack_maxsp = attack_maxsp;// 攻撃側のキャラのmaxsp
		this.defense_maxsp = defense_maxsp;// 受け側のキャラのmaxsp

		this.s_condition= Arrays.copyOf(s_condition, 7);

		//System.out.println("1a_buff"+a_buff[0]);
		this.a_buff=Arrays.copyOf(a_buff, 2);

		this.r_buff=Arrays.copyOf(r_buff, 2);
	}

	public Piece[] getFriend() {
		return bp.getFriend();
	}

	public void setStoryNum(int storyNum) {
		this.storyNum = storyNum;
	}

	public void setNextPanel(int next_panel) {
		this.next_panel = next_panel;
	}

	public void showBP() {
		showBP = true;
	}

	public void removeP() {
		showP = true;
	}

	public void showP() {
		showP = false;
	}

	public void preperatePvP(String string) {
		this.field_name = string;
		
	}

}