package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JApplet;

public class BattleKeyManagement extends JApplet implements KeyListener {

	MainPanel mp;
	BattlePanel panel;

	int state = 1;
	int select_num = 0;
	int[] cursor_position  = new int[2];

	static final int command = 0;
	static final int view = 1;
	static final int enemy_phase = 2;
	static final int victory = 3;

	boolean PvP = false;
	boolean myTurn = true;

	Clip sound_effect;

	BattleKeyManagement(BattlePanel panel, MainPanel mp) {
		this.mp = mp;
		this.panel = panel;


		try{
			sound_effect = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
			sound_effect.open(AudioSystem.getAudioInputStream(new File("../bin/Sound/効果音/select04.wav")));
			FloatControl control = (FloatControl)sound_effect.getControl(FloatControl.Type.MASTER_GAIN);
			controlByLinearScalar(control, 1);

		}
		catch(IOException | LineUnavailableException | UnsupportedAudioFileException er){
			throw new RuntimeException(er);

		}

		panel.setFocusable(true);
		panel.addKeyListener(this);

	}
	private void controlByLinearScalar(FloatControl control, double linearScalar) {
		control.setValue((float)Math.log10(linearScalar) * 20);
	}
	public void setState(int state){
		this.state = state;
	}

	public int getState(){
		return state;
	}

	public void reselect(){
		select_num = 0;
	}

	public void setSelect_num(int s){
		select_num = s;
	}

	public int getSelect_num(){
		return select_num;
	}


	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyChar = e.getKeyCode();

		if(!PvP && myTurn || PvP){
			switch(state){
			case command :
				if (keyChar == KeyEvent.VK_DOWN) {
					if(panel.selectable(select_num+1)) select_num++;
				}

				if (keyChar == KeyEvent.VK_UP) {
					if(panel.selectable(select_num-1)) select_num--;

				}

				/*if (keyChar == KeyEvent.VK_LEFT){}

			if (keyChar == KeyEvent.VK_RIGHT){}
				 */

				if (keyChar == KeyEvent.VK_SPACE){
					panel.changeSelect();
					//	System.out.println(" state ="+state);
				}
				if (keyChar == KeyEvent.VK_BACK_SPACE) {
					panel.backSelect();
				}


				break;

			case view :
				if (keyChar == KeyEvent.VK_DOWN) {
					if(panel.cursorMovable(cursor_position[0],cursor_position[1]+1))cursor_position[1]++;
				}

				if (keyChar == KeyEvent.VK_UP) {
					if(panel.cursorMovable(cursor_position[0],cursor_position[1]-1))cursor_position[1]--;
				}

				if (keyChar == KeyEvent.VK_RIGHT){
					if(panel.cursorMovable(cursor_position[0]+1,cursor_position[1]))cursor_position[0]++;
				}

				if (keyChar == KeyEvent.VK_LEFT){
					if(panel.cursorMovable(cursor_position[0]-1,cursor_position[1]))cursor_position[0]--;
				}
				if (keyChar == KeyEvent.VK_SPACE){
					panel.changeMode();
					//	System.out.println(" state ="+state);
				}
				if (keyChar == KeyEvent.VK_D){
					panel.changeDirection();
				}
				if (keyChar == KeyEvent.VK_E){
					panel.endPhase();
					panel.enemyPhase();
					//setState(2);
				}
				if (keyChar == KeyEvent.VK_BACK_SPACE) {
					panel.backMode();
				}


				break;

			case enemy_phase:

				if (keyChar == KeyEvent.VK_S){
					//panel.stop();
					System.out.println("stop .... 未実装");
				}

				break;

			case victory:
				////////////////////////////////////////////////////////
				//ここで遷移
				mp.setNextPanel(1);
				mp.removeP();
				mp.state = 2;
				////////////////////////////////////////////////
				System.out.println("ストーリーに遷移");
				break;
			}

			if (keyChar == KeyEvent.VK_T){
				panel.PvP = true;
				panel.PvPmode();
				System.out.println("Test Mode");
			}
			if (keyChar == KeyEvent.VK_V){
				panel.victoryCheck();
				panel.defeatCheck();
				System.out.println("win or lose check");
			}

			sound_effect.setFramePosition(0);
			sound_effect.start();
			//System.out.println("state :"+state);
			if(state == view)panel.updateView(cursor_position);
			else 	panel.updateCommand(select_num);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
	public void nextPanel() {
		state = victory;
	}
	public void setCursorPosi(int[] c_p){
		cursor_position = Arrays.copyOf(c_p, 2);
	}

	public void endPhase() {
		myTurn = (myTurn) ? false : true;
		if (myTurn)
			System.out.println("player phase");
		else
			System.out.println("enemy phase");
	}
	public void PvPmode(boolean PvP) {
		this.PvP = PvP;

	}
}
