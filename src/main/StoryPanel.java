package main;

import java.applet.AppletContext;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JApplet;
import javax.swing.JButton;

public class StoryPanel extends JApplet implements ActionListener, KeyListener{
	AppletContext ac; // アプレットのコンテキスト
	Dimension size; // アプレットの大きさ
	MainPanel mp;
	JButton[] bt;

	int next_panel; //0→出撃キャラ選択へ  1→日本地図へ

	//ここからStoryScene
	Thread th = null;

	int fps = 20;
	long PERIOD = 1000000000 / fps; // 単位 ナノ秒
	boolean running = false;
	long beforeTime, afterTime, timeDiff, sleepTime;
	long overSleepTime = 0L;
	int noDelays = 0;
	int interval = 1000 / fps;

	int countTime = 0;

	int width = 0;
	int height = 0;

	int storyNum = 1;///////////////////////※ストーリー番号
	int storyBackNum = 1;//＃

	boolean endFlag = false;

	Image backImage = null;
	Image workImage = null;

	Clip BackMusic = null;
	Clip no = null; 
	Clip yes = null;

	boolean ans = false;

	Image[] rightMan = null;//
	Image[] leftMan = null;//
	Image[] Face = null;//

	Image BackGraund = null;
	Image Waku1 = null;

	Container pane;

	String[] PictureFile = {"","resource/picture/hokkaidou.gif","resource/picture/aomori.gif","resource/picture/iwate.gif","resource/picture/miyagi.gif",
			"resource/picture/akita.gif","resource/picture/yamagata.gif","resource/picture/hukusima.gif","resource/picture/ibaragi.gif",
			"resource/picture/totigi.gif","resource/picture/gunma.gif","resource/picture/saitama.gif","resource/picture/tiba.gif",
			"resource/picture/toukyou.gif","resource/picture/kanagawa.gif","resource/picture/niigata.gif","resource/picture/toyama.gif",
			"resource/picture/isikawa.gif","resource/picture/hukui.gif","resource/picture/yamanasi.gif","resource/picture/nagano.gif",
			"resource/picture/gihu.gif","resource/picture/sizuoka.gif","resource/picture/aiti.gif","resource/picture/mie.gif",
			"resource/picture/siga.gif","resource/picture/kyouto.gif","resource/picture/oosaka.gif","resource/picture/hyougo.gif",
			"resource/picture/nara.gif","resource/picture/wakayama.gif","resource/picture/tottori.gif","resource/picture/simane.gif",
			"resource/picture/okayama.gif","resource/picture/hirosima.gif","resource/picture/yamaguti.gif","resource/picture/tokusima.gif",
			"resource/picture/kagawa.gif","resource/picture/ehime.gif","resource/picture/kouti.gif","resource/picture/hukuoka.gif",
			"resource/picture/saga.gif","resource/picture/nagasaki.gif","resource/picture/kumamoto.gif","resource/picture/ooita.gif",
			"resource/picture/miyazaki.gif","resource/picture/kagosima.gif","resource/picture/okinawa.gif","resource/picture/zero.png",
	};
	String[] FacePictureFile = {"","resource/picture/hokkaidou2.gif","resource/picture/aomori2.gif","resource/picture/iwate2.gif","resource/picture/miyagi2.gif",
			"resource/picture/akita2.gif","resource/picture/yamagata2.gif","resource/picture/hukusima2.gif","resource/picture/ibaragi2.gif",
			"resource/picture/totigi2.gif","resource/picture/gunma2.gif","resource/picture/saitama2.gif","resource/picture/tiba2.gif",
			"resource/picture/toukyou2.gif","resource/picture/kanagawa2.gif","resource/picture/niigata2.gif","resource/picture/toyama2.gif",
			"resource/picture/isikawa2.gif","resource/picture/hukui2.gif","resource/picture/yamanasi2.gif","resource/picture/nagano2.gif",
			"resource/picture/gihu2.gif","resource/picture/sizuoka2.gif","resource/picture/aiti2.gif","resource/picture/mie2.gif",
			"resource/picture/siga2.gif","resource/picture/kyouto2.gif","resource/picture/oosaka2.gif","resource/picture/hyougo2.gif",
			"resource/picture/nara2.gif","resource/picture/wakayama2.gif","resource/picture/tottori2.gif","resource/picture/simane2.gif",
			"resource/picture/okayama2.gif","resource/picture/hirosima2.gif","resource/picture/yamaguti2.gif","resource/picture/tokusima2.gif",
			"resource/picture/kagawa2.gif","resource/picture/ehime2.gif","resource/picture/kouti2.gif","resource/picture/hukuoka2.gif",
			"resource/picture/saga2.gif","resource/picture/nagasaki2.gif","resource/picture/kumamoto2.gif","resource/picture/ooita2.gif",
			"resource/picture/miyazaki2.gif","resource/picture/kagosima2.gif","resource/picture/okinawa2.gif","resource/picture/zero.png",
	};
	String[] TalkingMan = {"","北海道","青森","岩手","宮城","秋田","山形","福島","茨城","栃木","群馬","埼玉","千葉","東京",
			"神奈川","新潟","富山","石川","福井","山梨","長野","岐阜","静岡","愛知","三重","滋賀","京都",
			"大阪","兵庫","奈良","和歌山","鳥取","島根","岡山","広島","山口","徳島","香川","愛媛","高知",
			"福岡","佐賀","長崎","熊本","大分","宮崎","鹿児島","沖縄",""
	};
	int[] faceNum;//読み込み(顔だけ画像)

	String[] CommentFile1;//読み込み(コメント1行目)
	String[] CommentFile2;//読み込み(コメント2行目)
	String[] CommentFile3;//読み込み(コメント3行目)

	int CommentNum = 0;

	int[] rightManNum;//読み込み(右の人の画像)
	int[] leftManNum;//読み込み(左の人の画像)

	int[] prize = {10000,10000,10000,10000,10000,10000,10000,10000};
	BufferedReader br;

	// コンストラクタ
	public StoryPanel(AppletContext ac1, Dimension size1, MainPanel mp1, int storyNum, int next_panel) throws IOException, UnsupportedAudioFileException, LineUnavailableException  {
		ac = ac1;
		size = size1;
		mp = mp1;
		
		this.BackMusic = null;
		this.storyNum = storyNum;
		this.next_panel = next_panel;
		
		backImage = null;
		workImage = null;

		BackMusic = null;
		no = null; 
		yes = null;

		rightMan = null;//
		leftMan = null;//
		Face = null;//

		BackGraund = null;
		Waku1 = null;


		setSize(size);

		String[] str0 ={"メニュー","ストーリー","マップ","ステータス","バトル","ゲームオーバー",
				"バトルスクリーン","出撃キャラ選択","出撃位置設定"};//ボタンの名前の配列
		bt = new JButton[str0.length];

		// ボタンの配置
		Font f = new Font("SansSerif", Font.BOLD, 20);
		FontMetrics fm = getFontMetrics(f);

		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());

		for(int i = 0 ; i < str0.length ; i++){
			int w = fm.stringWidth(str0[i]) + 40;
			int h = fm.getHeight() + 10;
			bt[i] = new JButton(str0[i]);
			bt[i].setFont(f);
			bt[i].addActionListener(this); // アクションリスナ
			bt[i].setSize(w, h);

			if(i==1)
				continue;

			//	pane.add(bt[i]);
		}

		/*int w = size.width / 2 - (w1 + w2 + 5) / 2;
		bt1.setLocation(w, size.height - h1 - 20);
		add(bt1);
		bt2.setLocation(w + w1 + 5, size.height - h1 - 20);
		add(bt2);*/

		//ここからStoryScene
		this.setFocusable(true);
		this.addKeyListener(this);

		if(next_panel == 0){

			//br = new BufferedReader(new FileReader("../story/end"+0+".txt"));
			//this.storyNum = 0;
			//endFlag = true;
			br = new BufferedReader(new FileReader("../story/story"+storyNum+".txt"));
			endFlag = false;
		}else{
			br = new BufferedReader(new FileReader("../story/end"+storyNum+".txt"));
			endFlag = true;
		}
		String str = br.readLine();
		int CommentFileLength = Integer.parseInt(str);

		CommentFile1 = new String[CommentFileLength];
		CommentFile2 = new String[CommentFileLength];
		CommentFile3 = new String[CommentFileLength];
		faceNum = new int[CommentFileLength];
		rightManNum = new int[CommentFileLength];
		leftManNum = new int[CommentFileLength];

		for(int i=0;i<CommentFileLength;i++){
			String str1 = br.readLine();
			CommentFile1[i] = str1;
			String str2 = br.readLine();
			CommentFile2[i] = str2;
			String str3 = br.readLine();
			CommentFile3[i] = str3;
		}

		for(int i=0;i<CommentFileLength;i++){
			String str1 = br.readLine();
			String[] tokens = str1.split(",");
			int leftManNumKARI = Integer.parseInt(tokens[0]);
			int faceNumKARI = Integer.parseInt(tokens[1]);
			int rightManNumKARI = Integer.parseInt(tokens[2]);
			faceNum[i] = faceNumKARI;
			leftManNum[i] = leftManNumKARI;
			rightManNum[i] = rightManNumKARI;
		}


		br.close();


		rightMan = new Image[CommentFile1.length];
		leftMan = new Image[CommentFile1.length];
		Face = new Image[CommentFile1.length];
		try {

			for (int i = 0; i < CommentFile1.length; i++) {
				rightMan[i] = ImageIO.read(new File(PictureFile[rightManNum[i]]));
				Face[i] = ImageIO.read(new File(FacePictureFile[faceNum[i]]));
				leftMan[i] = ImageIO.read(new File(PictureFile[leftManNum[i]]));

			}


			BackGraund = ImageIO.read(new File("resource/picture/BackGraund"+storyBackNum+".jpg"));//＃
			Waku1 = ImageIO.read(new File("resource/picture/waku001.png"));

			BackMusic = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			BackMusic.open(AudioSystem.getAudioInputStream(new File("resource/music/healing.wav")));
			yes = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			yes.open(AudioSystem.getAudioInputStream(new File("resource/music/okok.wav")));
			no = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			no.open(AudioSystem.getAudioInputStream(new File("resource/music/nono.wav")));

		} catch (IOException e) {
			throw new RuntimeException(e);
			

		}

		BackMusic.setFramePosition(0);


		pane = getContentPane(); // 台紙の準備
		setLayout(null);//レイアウトをnullに

	}

	public void start() {
		if (th == null) {
			th = new Thread();
			th.start();
		}
		running = true;
	}

	public void stop() {
		if (th != null) {
			//th.stop();
			th = null;
		}
		running = false;
	}

	public void run() {

		beforeTime = System.nanoTime();

		while (running) {

			repaint();
			updateState();

			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;
			// 前回のフレームの休止時間誤差も引いておく
			sleepTime = (PERIOD - timeDiff) - overSleepTime;

			if (sleepTime > 0) {
				// 休止時間がとれる場合
				try {
					Thread.sleep(sleepTime / 1000000L); // nano->ms
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// sleep()の誤差
				overSleepTime =
						(System.nanoTime() - afterTime) - sleepTime;
			} else {
				// 状態更新・レンダリングで時間を使い切ってしまい
				// 休止時間がとれない場合
				overSleepTime = 0L;
				// 休止なしが16回以上続いたら
				if (++noDelays >= 16) {
					Thread.yield(); // 他のスレッドを強制実行
					noDelays = 0;
				}
			}

			beforeTime = System.nanoTime();

		}

	}

	public void updateState() {
		countTime++;

	}

	public void paint(Graphics g) {
		//super.paint(g);
		drawFigure(g);

	}

	public void drawFigure(Graphics g) {
		if (backImage == null || width != getWidth() || height != getHeight()) {
			width = getWidth();
			height = getHeight();
			createBackImage();
		}

		createWorkImage();
		g.drawImage(workImage, 0, 0, this);

	}

	public void createBackImage() {
		backImage = createImage(width, height);
		Graphics g = backImage.getGraphics();

		g.drawImage(BackGraund, 0, 0, 600, 600, this);
		BackMusic.loop(999);

		g.drawImage(Waku1, 0, 390, 600, 200, this);

	}

	public void createWorkImage() {

		workImage = createImage(width, height);
		Graphics g = workImage.getGraphics();

		g.drawImage(backImage, 0, 0, this);

		g.drawImage(rightMan[CommentNum], 350, 100, 200, 300, this);
		g.drawImage(leftMan[CommentNum], 50, 100, 200, 300, this);

		g.setColor(Color.WHITE);//スコアの色
		g.setFont(new Font("Monospaced", Font.PLAIN, 30));
		g.drawImage(Face[CommentNum], 40, 430, 100, 100, this);

		//コメント文

		g.drawString(CommentFile1[CommentNum], 160, 460);
		g.drawString(CommentFile2[CommentNum], 160, 460+35);
		g.drawString(CommentFile3[CommentNum], 160, 460+70);
		g.setColor(Color.RED);//スコアの色
		if(ans){
			g.drawString("賞金"+String.valueOf(prize[CommentNum]+"円を獲得"), 160, 460+70);
		}
		g.setColor(Color.WHITE);//スコアの色

		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		g.drawString(TalkingMan[faceNum[CommentNum]], 60, 550);

	}

	// ボタンがクリックされたときの処理
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt[0]) //GameMenuPanelへ
			mp.state = 1;
		else if(e.getSource() == bt[1])//StoryPanelへ
			mp.state = 2;
		else if(e.getSource() == bt[2])//MapPanel
			mp.state = 3;
		else if(e.getSource() == bt[3])//StatePanel
			mp.state = 4;
		else if(e.getSource() == bt[4])//BattlePanel
			mp.state = 5;
		else if(e.getSource() == bt[5])//GameOverPanel
			mp.state = 6;
		else if(e.getSource() == bt[6])//BattleScreenPanel
			mp.state = 7;
		else if(e.getSource() == bt[7])//SortieUnitSelectPanel
			mp.state = 8;
		else if(e.getSource() == bt[8])//SortieUnitSetPanel
			mp.state = 9;
	}

	public void keyPressed(KeyEvent e) {
		ans = false;
		int keyCode = e.getKeyCode();
		if(endFlag==false){
			if (keyCode == KeyEvent.VK_SPACE && CommentNum < CommentFile1.length - 1) {
				CommentNum++;
			}
			else if (keyCode == KeyEvent.VK_BACK_SPACE && CommentNum != 0) {
				CommentNum--;
			}

			else if (keyCode == KeyEvent.VK_RIGHT && CommentNum < CommentFile1.length - 1) {
				CommentNum++;
			}

			else if (keyCode == KeyEvent.VK_LEFT && CommentNum != 0) {
				CommentNum--;
			}
			else if(CommentNum == CommentFile1.length - 1){
				BackMusic.stop();
				System.out.println("story end"+next_panel);/////////////////////※場面転換
				if(next_panel == 0){
					this.BackMusic = null;
					mp.state = 8;

				}
				else{
					this.BackMusic = null;
					mp.state = 3;
				}
			}
			repaint();
		}
		else{
			if(CommentNum <= 2||CommentNum >= 4){
				if (keyCode == KeyEvent.VK_SPACE && CommentNum < CommentFile1.length - 1) {
					CommentNum++;
				}
				else if (keyCode == KeyEvent.VK_BACK_SPACE && CommentNum != 0) {
					CommentNum--;
				}

				else if (keyCode == KeyEvent.VK_RIGHT && CommentNum < CommentFile1.length - 1) {
					CommentNum++;
				}

				else if (keyCode == KeyEvent.VK_LEFT && CommentNum != 0) {
					CommentNum--;
				}
				else if(CommentNum == CommentFile1.length - 1){
					BackMusic.stop();
					System.out.println("story end"+next_panel);/////////////////////※場面転換
					if(next_panel == 0){
						//////////////////
						int get;
						try{
							BufferedReader br = new BufferedReader(new FileReader("resource/Money/Money1"));
							//PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("resource/Money/Money1")));
							String str = br.readLine();
							System.out.println(str);
							get = Integer.parseInt(str)+ prize[storyNum];
							System.out.println("所持金："+get);
							//pw.println(get);
							br.close();
							//pw.close();
						}catch(IOException er){
							throw new RuntimeException(er);
						}
						
						try{
							PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("resource/Money/Money1")));
							pw.println(get);
							pw.close();
						}catch(IOException er){
							throw new RuntimeException(er);
						}
						/////////////////////
						this.BackMusic = null;
						mp.state = 8;
					}
					else{
						int get;
						try{
							BufferedReader br = new BufferedReader(new FileReader("resource/Money/Money1"));
							 get = Integer.parseInt(br.readLine())+ prize[storyNum];
							

							System.out.println("所持金："+get);
							//pw.println(get);
							br.close();
							//pw.close();
						}catch(IOException er){
							throw new RuntimeException(er);
						}
						try{
							PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("resource/Money/Money1")));
							pw.println(get);
							pw.close();
						}catch(IOException er){
							throw new RuntimeException(er);
						}
						this.BackMusic = null;
						mp.state = 3;
					}
				}
				repaint();
			}
			else if(CommentNum==3){
				if(storyNum==1||storyNum==5||storyNum==7){
					if(keyCode==KeyEvent.VK_1){
						System.out.println("正解！");
						ans = true;
						yes.setMicrosecondPosition(0);
						yes.start();
						CommentNum++;
					}
					else if(keyCode==KeyEvent.VK_2||keyCode==KeyEvent.VK_3){
						System.out.println("不正解");
						no.setMicrosecondPosition(0);
						no.start();
						CommentNum++;
					}
					else{
						if (keyCode == KeyEvent.VK_BACK_SPACE && CommentNum != 0) {
							CommentNum--;
						}
						else if (keyCode == KeyEvent.VK_LEFT && CommentNum != 0) {
							CommentNum--;
						}

					}
				}
				else if(storyNum==3||storyNum==4){
					if(keyCode==KeyEvent.VK_2){
						System.out.println("正解！");
						ans =  true;
						yes.setMicrosecondPosition(0);
						yes.start();
						CommentNum++;
					}
					else if(keyCode==KeyEvent.VK_1||keyCode==KeyEvent.VK_3){
						System.out.println("不正解");
						no.setMicrosecondPosition(0);
						no.start();
						CommentNum++;
					}
					else{
						if (keyCode == KeyEvent.VK_BACK_SPACE && CommentNum != 0) {
							CommentNum--;
						}
						else if (keyCode == KeyEvent.VK_LEFT && CommentNum != 0) {
							CommentNum--;
						}

					}
				}
				else if(storyNum==0||storyNum==2||storyNum==6){
					if(keyCode==KeyEvent.VK_3){
						System.out.println("正解！");
						ans = true;
						yes.setMicrosecondPosition(0);
						yes.start();
						CommentNum++;
					}
					else if(keyCode==KeyEvent.VK_1||keyCode==KeyEvent.VK_2){
						System.out.println("不正解");
						no.setMicrosecondPosition(0);
						no.start();
						CommentNum++;
					}
					else{
						if (keyCode == KeyEvent.VK_BACK_SPACE && CommentNum != 0) {
							CommentNum--;
						}
						else if (keyCode == KeyEvent.VK_LEFT && CommentNum != 0) {
							CommentNum--;
						}

					}
				}
				repaint();
			}

		}
		//System.out.println(CommentNum);
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}
}