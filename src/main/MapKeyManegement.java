package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JApplet;

public class MapKeyManegement extends JApplet implements KeyListener{

	MainPanel mp;
	MapPanel mapPanel;
	int map_num;
	boolean space_pressed = false;
	boolean back_pressed = false;

	public MapKeyManegement(MapPanel mapPanel, MainPanel mp){
		map_num = 0;
		this.mapPanel = mapPanel;
		this.mp = mp;
		mapPanel.setFocusable(true);
		mapPanel.addKeyListener(this);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyChar = e.getKeyCode();
		int mini_distance = 200;
		int mini_num = map_num;
		//System.out.println(map_num);

		if (keyChar == KeyEvent.VK_DOWN) {
			for(int i = 0;i < mapPanel.map_max;i++){
				if(mapPanel.stage[i].position[1] > mapPanel.stage[map_num].position[1]){
				 if(mini_distance > Math.abs(mapPanel.stage[map_num].position[1]-mapPanel.stage[i].position[1])) {
					 mini_distance = Math.abs(mapPanel.stage[map_num].position[1]-mapPanel.stage[i].position[1]);
					 mini_num = i;
				}
			}
			}
			//System.out.println(mini_distance+","+mp.stage[mini_num].position[1]+","+mp.stage[map_num].position[1]);
			//if(map_num != mp.map_max-1)
			map_num = mini_num;
		}

		if (keyChar == KeyEvent.VK_UP) {
			for(int i = 0;i < mapPanel.map_max;i++){
				if(mapPanel.stage[i].position[1] < mapPanel.stage[map_num].position[1]){
				 if(mini_distance > Math.abs(mapPanel.stage[map_num].position[1]-mapPanel.stage[i].position[1])) {
					 mini_distance = Math.abs(mapPanel.stage[map_num].position[1]-mapPanel.stage[i].position[1]);
					 mini_num = i;
				 }
				}
			}
			 map_num = mini_num;
			}

		if (keyChar == KeyEvent.VK_RIGHT){
			for(int i = 0;i < mapPanel.map_max;i++){
				if(mapPanel.stage[i].position[0] > mapPanel.stage[map_num].position[0]){
				 if(mini_distance > Math.abs(mapPanel.stage[map_num].position[0]-mapPanel.stage[i].position[0])) {
					 mini_distance = Math.abs(mapPanel.stage[map_num].position[0]-mapPanel.stage[i].position[0]);
					 mini_num = i;
				 }
				}
			}
			//if(map_num != mapPanel.map_max-1)
			map_num = mini_num;
		}

		if (keyChar == KeyEvent.VK_LEFT){
			for(int i = 0;i < mapPanel.map_max;i++){
				if(mapPanel.stage[i].position[0] < mapPanel.stage[map_num].position[0]){
				 if(mini_distance > Math.abs(mapPanel.stage[map_num].position[0]-mapPanel.stage[i].position[0])) {
					 mini_distance = Math.abs(mapPanel.stage[map_num].position[0]-mapPanel.stage[i].position[0]);
					 mini_num = i;
				 }
				}
			}
			map_num = mini_num;
		}

		if (keyChar == KeyEvent.VK_SPACE){
			System.out.println("SPACE");

			if(map_num == 0){
				mp.setNextPanel(0);
				System.out.println(map_num);
				mp.setStoryNum(map_num);//mapnumで代用0 九州 1 中国 2 四国 3 近畿 4 中部 5 関東 6 東北 7 北海道
				mp.state = 2;
				space_pressed = true;
			}else {
				if(mp.cleared[map_num-1]){
					mp.setNextPanel(0);
					System.out.println(map_num);
					mp.setStoryNum(map_num);//mapnumで代用0 九州 1 中国 2 四国 3 近畿 4 中部 5 関東 6 東北 7 北海道
					mp.state = 2;
					space_pressed = true;
				}else{
					System.out.println("押せないよ～");
				}
			}
		}

		if (keyChar == KeyEvent.VK_B){
			System.out.println("B");
			mp.state = 1;
			back_pressed = true;
		}

		//System.out.println("after"+map_num+","+mini_num);
		if(!space_pressed && !back_pressed)mapPanel.updateMap_Num(map_num);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
