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
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;

public class BattleScreenPanel extends JApplet implements ActionListener, Runnable{
	AppletContext ac; // アプレットのコンテキスト
	Dimension size; // アプレットの大きさ
	MainPanel mp;
	JButton[] bt;

	//ここからBattleScene
	Thread th = null;

	JButton button;
	ImageIcon endButton;
	Image endButtonPic;

	int fps = 20;
	long PERIOD = 1000000000 / fps; // 単位 ナノ秒
	boolean running = false;
	long beforeTime, afterTime, timeDiff, sleepTime;
	long overSleepTime = 0L;
	int noDelays = 0;
	int interval = 1000 / fps;

	int countTime = 0;

	boolean AttackTurn = true;
	boolean DefenseTurn = false;
	boolean CounterTurn = false;
	boolean CounterDefenseTurn = false;

	boolean LeftManDeathFlag = false;
	boolean RightManDeathFlag = false;

	int damageV = 0;
	int damageShowTime = 0;

	int drainV = 0;

	int AddTime1 = 0;//左←右の攻撃時間延長
	int AddTime2 = 0;//左のダメージ時間延長
	int AddTime3 = 0;//左→右の攻撃時間延長

	int width = 0;
	int height = 0;

	int skil01x = 0;
	int skil01y = 0;
	int skil01Count = 0;

	int skil02x = 0;
	int skil02y = 0;
	int skil02Count = 0;

	int skil03x = 0;
	int skil03y = 0;
	int skil03Count = 0;

	int skil04x = 0;
	int skil04y = 0;
	int skil04Count = 0;

	int skil05x = 0;
	int skil05y = 0;
	int skil05Count = 0;

	int skil06x = 0;
	int skil06y = 0;
	int skil06Count = 0;

	int skil07x = 0;
	int skil07y = 0;
	int skil07Count = 0;

	int skil08x = 0;
	int skil08y = 0;
	int skil08Count = 0;

	int skil09x = 0;
	int skil09y = 0;
	int skil09Count = 0;

	int skil10x = 0;
	int skil10y = 0;
	int skil10Count = 0;

	int skil11x = 0;
	int skil11y = 0;
	int skil11Count = 0;

	int skil12x = 0;
	int skil12y = 0;
	int skil12Count = 0;

	int skil13x = 0;
	int skil13y = 0;
	int skil13Count = 0;

	int skil14x = 0;
	int skil14y = 0;
	int skil14Count = 0;

	int skil15x = 0;
	int skil15y = 0;
	int skil15Count = 0;

	int skil16x = 0;
	int skil16y = 0;
	int skil16Count = 0;

	int skil17x = 0;
	int skil17y = 0;
	int skil17Count = 0;

	int skil18x = 0;
	int skil18y = 0;
	int skil18Count = 0;

	int skil19x = 0;
	int skil19y = 0;
	int skil19Count = 0;

	int skil20x = 0;
	int skil20y = 0;
	int skil20Count = 0;

	int skil21x = 0;
	int skil21y = 0;
	int skil21Count = 0;

	int skil22x = 0;
	int skil22y = 0;
	int skil22Count = 0;

	int skil23x = 0;
	int skil23y = 0;
	int skil23Count = 0;

	int skil24x = 0;
	int skil24y = 0;
	int skil24Count = 0;

	int skil25x = 0;
	int skil25y = 0;
	int skil25Count = 0;

	int skil26x = 0;
	int skil26y = 0;
	int skil26Count = 0;

	int skil27x = 0;
	int skil27y = 0;
	int skil27Count = 0;

	int skil28x = 0;
	int skil28y = 0;
	int skil28Count = 0;

	int skil29x = 0;
	int skil29y = 0;
	int skil29Count = 0;

	int skil30x = 0;
	int skil30y = 0;
	int skil30Count = 0;

	int skil31x = 0;
	int skil31y = 0;
	int skil31Count = 0;

	int skil32x = 0;
	int skil32y = 0;
	int skil32Count = 0;

	int skil33x = 0;
	int skil33y = 0;
	int skil33Count = 0;

	int skil34x = 0;
	int skil34y = 0;
	int skil34Count = 0;

	int skil35x = 0;
	int skil35y = 0;
	int skil35Count = 0;

	int skil36x = 0;
	int skil36y = 0;
	int skil36Count = 0;

	int skil37x = 0;
	int skil37y = 0;
	int skil37Count = 0;

	int skil38x = 0;
	int skil38y = 0;
	int skil38Count = 0;

	int skil39x = 0;
	int skil39y = 0;
	int skil39Count = 0;

	int skil40x = 0;
	int skil40y = 0;
	int skil40Count = 0;

	int skil41x = 0;
	int skil41y = 0;
	int skil41Count = 0;

	int skil42x = 0;
	int skil42y = 0;
	int skil42Count = 0;

	int skil43x = 0;
	int skil43y = 0;
	int skil43Count = 0;

	int skil44x = 0;
	int skil44y = 0;
	int skil44Count = 0;

	int skil45x = 0;
	int skil45y = 0;
	int skil45Count = 0;

	int skil46x = 0;
	int skil46y = 0;
	int skil46Count = 0;

	int skil47x = 0;
	int skil47y = 0;
	int skil47Count = 0;

	int skil48x = 0;
	int skil48y = 0;
	int skil48Count = 0;

	int skil49x = 0;
	int skil49y = 0;
	int skil49Count = 0;

	int skil50x = 0;
	int skil50y = 0;
	int skil50Count = 0;

	int skil51x = 0;
	int skil51y = 0;
	int skil51Count = 0;

	int skil52x = 0;
	int skil52y = 0;
	int skil52Count = 0;

	int skil53x = 0;
	int skil53y = 0;
	int skil53Count = 0;

	int skil54x = 0;
	int skil54y = 0;
	int skil54Count = 0;

	int skil55x = 0;
	int skil55y = 0;
	int skil55Count = 0;

	int skil56x = 0;
	int skil56y = 0;
	int skil56Count = 0;

	int skil57x = 0;
	int skil57y = 0;
	int skil57Count = 0;

	int skil58x = 0;
	int skil58y = 0;
	int skil58Count = 0;

	int skil59x = 0;
	int skil59y = 0;
	int skil59Count = 0;

	int skil60x = 0;
	int skil60y = 0;
	int skil60Count = 0;

	int skil61x = 0;
	int skil61y = 0;
	int skil61Count = 0;

	int skil62x = 0;
	int skil62y = 0;
	int skil62Count = 0;

	int skil63x = 0;
	int skil63y = 0;
	int skil63Count = 0;

	int skil64x = 0;
	int skil64y = 0;
	int skil64Count = 0;

	int skil65x = 0;
	int skil65y = 0;
	int skil65Count = 0;

	int skil66x = 0;
	int skil66y = 0;
	int skil66Count = 0;

	int skil67x = 0;
	int skil67y = 0;
	int skil67Count = 0;

	int skil68x = 0;
	int skil68y = 0;
	int skil68Count = 0;

	int skil69x = 0;
	int skil69y = 0;
	int skil69Count = 0;

	int skil70x = 0;
	int skil70y = 0;
	int skil70Count = 0;

	int skil71x = 0;
	int skil71y = 0;
	int skil71Count = 0;

	int skil72x = 0;
	int skil72y = 0;
	int skil72Count = 0;

	int skil73x = 0;
	int skil73y = 0;
	int skil73Count = 0;

	int skil74x = 0;
	int skil74y = 0;
	int skil74Count = 0;

	int skil75x = 0;
	int skil75y = 0;
	int skil75Count = 0;

	int skil76x = 0;
	int skil76y = 0;
	int skil76Count = 0;

	int skil77x = 0;
	int skil77y = 0;
	int skil77Count = 0;

	int[] conditionx = {0,0,0,0,0,0,0};
	int[] conditiony = {0,0,0,0,0,0,0};
	int[] conditionCount = {0,0,0,0,0,0,0};

	int[] RightManBuffx = {0,0};
	int[] RightManBuffy = {0,0};
	int[] RightManBuffCount = {0,0};

	int[] LeftManBuffx = {0,0};
	int[] LeftManBuffy = {0,0};
	int[] LeftManBuffCount = {0,0};

	int lateTime = 0;

	int skil999x = 0;
	int skil999y = 0;
	int skil999Count = 0;

	Image backImage = null;
	Image workImage = null;

	Clip BackMusic = null;

	Image RightMan = null;
	Image LeftMan = null;
	Image RightManFace = null;
	Image LeftManFace = null;
	Image BackGraund = null;
	Image Waku1 = null;

	Image skil01 = null;
	Clip skil011 = null;
	Clip skil012 = null;
	Clip skil013 = null;
	Clip skil014 = null;
	Clip skil015 = null;
	Clip magical01 = null;

	Image skil02 = null;
	Clip skil021 = null;

	Image skil03 = null;
	Clip skil031 = null;
	Clip skil032 = null;

	Image skil04 = null;
	Clip skil041 = null;

	Image skil05 = null;
	Clip skil051 = null;

	Image skil06 = null;
	Clip skil061 = null;
	Clip skil062 = null; 

	Image skil07 = null;
	Clip skil071 = null;

	Image skil08 = null;
	Clip skil081 = null;
	Clip skil082 = null;

	Image skil09 = null;
	Clip skil091 = null;

	Image skil10 = null;
	Clip skil101 = null;

	Image skil11 = null;
	Clip skil111 = null;

	Image skil12 = null;
	Clip skil121 = null;

	Image skil13 = null;
	Clip skil131 = null;

	Image skil14 = null;
	Clip skil141 = null;

	Image skil15 = null;
	Clip skil151 = null;

	Image skil16 = null;
	Clip skil161 = null;;
	Clip skil162 = null;

	Image skil17 = null;
	Clip skil171 = null;
	Clip skil172 = null;

	Image skil18 = null;
	Clip skil181 = null;
	Clip skil182 = null;

	Image skil19 = null;
	Clip skil191 = null;
	Clip skil192 = null;
	Clip skil193 = null;
	Clip skil194 = null;

	Image skil20 = null;
	Clip skil201 = null;
	Clip skil202 = null;
	Clip skil203 = null;
	Clip skil204 = null;
	Clip skil205 = null;

	Image skil21 = null;
	Clip skil211 = null;

	Image skil22 = null;
	Clip skil221 = null;

	Image skil23 = null;
	Clip skil231 = null;

	Image skil24 = null;
	Clip skil241 = null;

	Image skil25 = null;
	Clip skil251 = null;

	Image skil26 = null;
	Clip skil261 = null;

	Image skil27 = null;
	Clip skil271 = null;

	Image skil28 = null;
	Clip skil281 = null;

	Image skil29 = null;
	Clip skil291 = null;
	Clip skil292 = null;
	Clip skil293 = null;
	Clip skil294 = null;
	Clip skil295 = null;
	Clip magical29 = null;

	Image skil30 = null;
	Clip skil301 = null;
	Clip skil302 = null;
	Clip skil303 = null;
	Clip skil304 = null;
	Clip skil305 = null;
	Clip magical30 = null;

	Image skil31 = null;
	Clip skil311 = null;
	Clip skil312 = null;
	Clip skil313 = null;
	Clip skil314 = null;
	Clip skil315 = null;
	Clip magical31 = null;

	Image skil32 = null;
	Clip skil321 = null;
	Clip skil322 = null;
	Clip skil323 = null;
	Clip skil324 = null;
	Clip skil325 = null;
	Clip magical32 = null;

	Image skil33 = null;
	Clip skil331 = null;
	Clip skil332 = null;
	Clip skil333 = null;
	Clip skil334 = null;
	Clip skil335 = null;
	Clip magical33 = null;

	Image skil34=null;
	Clip skil341=null;
	Clip skil342=null;

	Image skil35=null;
	Clip skil351=null;
	Clip skil352=null;

	Image skil36=null;
	Clip skil361=null;

	Image skil37=null;
	Clip skil371=null;

	Image skil38=null;
	Clip skil381=null;
	Clip skil382=null;

	Image skil39=null;
	Clip skil391=null;

	Image skil40=null;
	Clip skil401=null;

	Image skil41=null;
	Clip skil411=null;

	Image skil42=null;
	Clip skil421=null;

	Image skil43=null;
	Clip skil431=null;

	Image skil44=null;
	Clip skil441=null;

	Image skil45=null;
	Clip skil451=null;

	Image skil46=null;
	Clip skil461=null;

	Image skil47=null;
	Clip skil471=null;

	Image skil48=null;
	Clip skil481=null;

	Image skil49=null;
	Clip skil491=null;

	Image skil50=null;
	Clip skil501=null;

	Image skil51=null;
	Clip skil511=null;
	Clip skil512=null;

	Image skil52=null;
	Clip skil521=null;

	Image skil53=null;
	Clip skil531=null;

	Image skil54=null;
	Clip skil541=null;

	Image skil55=null;
	Clip skil551=null;

	Image skil56=null;
	Clip skil561=null;

	Image skil57=null;
	Clip skil571=null;

	Image skil58=null;
	Clip skil581=null;

	Image skil59=null;
	Clip skil591=null;

	Image skil60=null;
	Clip skil601=null;

	Image skil61=null;
	Clip skil611=null;

	Image skil62=null;
	Clip skil621=null;

	Image skil63=null;
	Clip skil631=null;

	Image skil64=null;
	Clip skil641=null;

	Image skil65=null;
	Clip skil651=null;

	Image skil66=null;
	Clip skil661=null;

	Image skil67=null;
	Clip skil671=null;

	Image skil68=null;
	Clip skil681=null;

	Image skil69=null;
	Clip skil691=null;

	Image skil70=null;
	Clip skil701=null;

	Image skil71=null;
	Clip skil711=null;

	Image skil72=null;
	Clip skil721=null;

	Image skil73=null;
	Clip skil731=null;

	Image skil74=null;
	Clip skil741=null;

	Image skil75=null;
	Clip skil751=null;

	Image skil76=null;
	Clip skil761=null;
	Clip skil762=null;

	Image skil77=null;
	Clip skil771=null;

	//////////////////////////
	Image buff=null;
	Image deBuff=null;

	Image condition[] = new Image[7];

	///////////////////////


	Image skil999=null;
	Clip skil9991=null;

	Clip hpDown=null;
	Clip hpDownS=null;
	Clip Death=null;
	Clip Miss=null;

	Image batu=null;
	Image ManAttack=null;

	Container pane;

	String[] PictureFile = { "", "resource/picture/hokkaidou.gif", "resource/picture/aomori.gif", "resource/picture/iwate.gif",
			"resource/picture/miyagi.gif",
			"resource/picture/akita.gif", "resource/picture/yamagata.gif", "resource/picture/hukusima.gif", "resource/picture/ibaragi.gif",
			"resource/picture/totigi.gif", "resource/picture/gunma.gif", "resource/picture/saitama.gif", "resource/picture/tiba.gif",
			"resource/picture/toukyou.gif", "resource/picture/kanagawa.gif", "resource/picture/niigata.gif", "resource/picture/toyama.gif",
			"resource/picture/isikawa.gif", "resource/picture/hukui.gif", "resource/picture/yamanasi.gif", "resource/picture/nagano.gif",
			"resource/picture/gihu.gif", "resource/picture/sizuoka.gif", "resource/picture/aiti.gif", "resource/picture/mie.gif",
			"resource/picture/siga.gif", "resource/picture/kyouto.gif", "resource/picture/oosaka.gif", "resource/picture/hyougo.gif",
			"resource/picture/nara.gif", "resource/picture/wakayama.gif", "resource/picture/tottori.gif", "resource/picture/simane.gif",
			"resource/picture/okayama.gif", "resource/picture/hirosima.gif", "resource/picture/yamaguti.gif", "resource/picture/tokusima.gif",
			"resource/picture/kagawa.gif", "resource/picture/ehime.gif", "resource/picture/kouti.gif", "resource/picture/hukuoka.gif",
			"resource/picture/saga.gif", "resource/picture/nagasaki.gif", "resource/picture/kumamoto.gif", "resource/picture/ooita.gif",
			"resource/picture/miyazaki.gif", "resource/picture/kagosima.gif", "resource/picture/okinawa.gif", "resource/picture/zero.png",
	};
	String[] FacePictureFile = { "", "resource/picture/hokkaidou2.gif", "resource/picture/aomori2.gif", "resource/picture/iwate2.gif",
			"resource/picture/miyagi2.gif",
			"resource/picture/akita2.gif", "resource/picture/yamagata2.gif", "resource/picture/hukusima2.gif", "resource/picture/ibaragi2.gif",
			"resource/picture/totigi2.gif", "resource/picture/gunma2.gif", "resource/picture/saitama2.gif", "resource/picture/tiba2.gif",
			"resource/picture/toukyou2.gif", "resource/picture/kanagawa2.gif", "resource/picture/niigata2.gif", "resource/picture/toyama2.gif",
			"resource/picture/isikawa2.gif", "resource/picture/hukui2.gif", "resource/picture/yamanasi2.gif", "resource/picture/nagano2.gif",
			"resource/picture/gihu2.gif", "resource/picture/sizuoka2.gif", "resource/picture/aiti2.gif", "resource/picture/mie2.gif",
			"resource/picture/siga2.gif", "resource/picture/kyouto2.gif", "resource/picture/oosaka2.gif", "resource/picture/hyougo2.gif",
			"resource/picture/nara2.gif", "resource/picture/wakayama2.gif", "resource/picture/tottori2.gif", "resource/picture/simane2.gif",
			"resource/picture/okayama2.gif", "resource/picture/hirosima2.gif", "resource/picture/yamaguti2.gif", "resource/picture/tokusima2.gif",
			"resource/picture/kagawa2.gif", "resource/picture/ehime2.gif", "resource/picture/kouti2.gif", "resource/picture/hukuoka2.gif",
			"resource/picture/saga2.gif", "resource/picture/nagasaki2.gif", "resource/picture/kumamoto2.gif", "resource/picture/ooita2.gif",
			"resource/picture/miyazaki2.gif", "resource/picture/kagosima2.gif", "resource/picture/okinawa2.gif", "resource/picture/zero.png",
	};
	String[] TalkingMan = { "", "北海道", "青森", "岩手", "宮城", "秋田", "山形", "福島", "茨城", "栃木", "群馬", "埼玉", "千葉", "東京",
			"神奈川", "新潟", "富山", "石川", "福井", "山梨", "長野", "岐阜", "静岡", "愛知", "三重", "滋賀", "京都",
			"大阪", "兵庫", "奈良", "和歌山", "鳥取", "島根", "岡山", "広島", "山口", "徳島", "香川", "愛媛", "高知",
			"福岡", "佐賀", "長崎", "熊本", "大分", "宮崎", "鹿児島", "沖縄", ""
	};

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////

	////{"攻撃１","攻撃２","被ダメージ時","カウンター","避ける","死亡"}
	String[][] CommentFile = { {},
			{ "うぉぉぉぉぉ！！", "相手にとって不足なし！！", "おぬし、やるなぁ！！", "反撃といこうか！！", "当たらぬわ！", "…うむ" },//北海道 1
			{ "軽くやってやるぜ！", "もっと熱くなろうぜ！", "このくらいなら大丈夫だ！", "いっちょやるか！", "当たらねえよっ", "最高…だったぜ…" },//青森  2
			{ "……", "…えい", "……", "……", "……", "……" },//岩手   3
			{ "この力、受けてみろ！", "右目が疼く…", "本気で来るがいい！", "反撃開始！", "フッハハハハハ！", "俺の魂は不滅だ…" },//宮城  4
			{ "やっちゃいます～", "えいっ", "痛いです～", "お返しです～", "あれっ？避けちゃいました～", "ふぇぇぇ～" },//秋田   5
			{ "行きます", "雪のように…", "やりますね…", "カウンターですっ", "それでは当たりませんよっ", "完敗です…" },//山形  6
			{ "見えましたっ", "わかりますよっ", "効きますねぇ", "これはいかがですか？", "その攻撃は見えました", "そんな…" },//福島   7
			{ "さて、やってやるか", "くらえっ", "痛えよ！", "やりかえしてやるっ", "当たんねえよ、そんなんじゃ", "マジでか…負けた" },//茨城  8
			{ "これでどうだっ", "くらえよっ", "なんだよ、もう…", "反撃だ！", "へへっ、当たるかよっ", "なんでだよ…" },//栃木   9
			{ "やってさしあげますわ", "お仕置きですわ", "嫌ーですわ！", "反撃ですわっ", "当たりませんわっ", "私が負けるなんて…" },//群馬  10
			{ "えいっ！", "え～いっ", "ま、負けないっ！", "お返しだぁ～", "あれ～？", "負けちゃった～" },//埼玉   11
			{ "こんなんでいいだろ", "あ～めんどくせえ～", "いてえ…", "こんにゃろ！", "当たんねえよ", "いてえよ…" },//千葉  12
			{ "行きます", "やります", "くっ、なかなかですね", "反撃ですっ", "まだまだですね", "さすがですね…" },//東京   13
			{ "行くわよっ", "かわさないでねっ", "やったわね", "反撃よっ", "当たらないわっ", "そんなハズじゃ…" },//神奈川14
			{ "しょうがないわね", "やるわっ", "痛ったいわねぇ", "これはどう？", "見え見えよっ", "私が…負けるの？…" },//新潟   15
			{ "私なりに頑張ります！", "痛かったらすみません！", "ううっ", "反撃、いきますよっ", "見えてますよっ", "私の負けですね…" },//富山  16
			{ "当たってください", "避けないでください", "やはり不幸です…", "お返しします", "珍しく運がよかった…", "やはり不幸ですね…" },//石川   17
			{ "これだどどんな感じかしら", "とっておきよっ", "まだ予想の範囲内ね", "これは予想できるかしら", "予想通りね", "予想の範囲外だわ…" },//福井  18
			{ "やっちゃうよー", "攻撃だー", "ダメージを受けちゃったよ", "僕もやっちゃうよ～", "そんなんじゃ当たらないよっ", "負けちゃったー" },//山梨   19
			{ "スキーでもどう？", "舐めてると痛い目見るよっ", "効くなぁ", "反撃、いくよっ", "危なかったー", "僕の負けです" },//長野  20
			{ "見てろよっ", "俺がやってやるぜっ", "マジかよ…", "お返しだっ", "へへっ、どうだ！", "負けちまったかー" },//岐阜   21
			{ "お茶をどうぞっ", "これは、どうですか？", "お茶が零れちゃいますっ", "お茶をさらにどうぞっ", "ふう、お茶がおいしいです～", "お茶がなくなっちゃいました～" },//静岡  22
			{ "あんた、ウザい", "あんた、邪魔だから", "むっかつく！", "あーしに立てつくわけ？", "何かやったの？", "ホント、むっかつく！" },//愛知   23
			{ "お告げが聞こえました", "全ては神の支配下です", "そんな…", "反撃を開始します", "お告げ通りです", "お告げは正しいはず…" },//三重  24
			{ "やっちゃうよ！", "私の推理は正しいわ！", "なんでっ！", "反撃よっ", "私の推理通りね！", "私が負けるなんて…" },//滋賀   25
			{ "私に勝てるとでも？", "耐えられますか？", "そんなハズは…", "反撃としますか", "次はちゃんと狙ってくださいね", "まさか…" },//京都  26
			{ "フッハハハハハ！！", "開幕といこう！！", "もはや許さん！！", "喰らえ！！", "どうした？", "痴れものが…！" },//大阪   27
			{ "これが最適かな？", "僕の番ですね", "手厳しい攻撃ですね", "今度はこっちから行きますね", "やっぱりそう来たね", "あなたの勝ちです…" },//兵庫  28
			{ "…イライラする", "……なんだよ", "…痛い", "…", "…フン", "…" },//奈良   29
			{ "やっちゃうよ～", "え～い", "いったいよう～", "やっちゃうもんね～", "あれ～？", "和歌山、もう疲れたよ～" },//和歌山30
			{ "やって…いいの？", "大丈夫かなぁ…", "痛いよう…", "反撃って、こうかなぁ…", "ふう、危なかった…", "そんな…" },//鳥取   31
			{ "僕がやるよっ", "僕は子供じゃない！！", "そんなもんなの？", "カウンターだっ", "当たらないよっ", "この僕が…負けるなんて…" },//島根  32
			{ "うぉおおおりゃ！！", "はぁぁぁああ！！", "なにっ！", "反撃といこうか", "見え見えだ！", "バカな…" },//岡山   33
			{ "俺の筋肉が止まらないぜ！", "筋肉最高！", "筋肉のおかげで助かったぜ！", "筋トレついでの反撃だ！", "俺の筋肉の予想通りだな！", "筋肉痛がぁぁぁぁ！！" },//広島  34
			{ "私がやるわ", "これはどう？", "痛いわね…", "反撃よ", "…ふんっ", "そんな…" },//山口   35
			{ "やらせてもらいます", "気を付けてくださいね", "さすがに効きますね", "これはいかがですか？", "危ないところでした", "やられてしまいました…" },//徳島  36
			{ "攻撃しますね", "全力でいきますよ", "ダメージが…", "反撃ですよ", "なんとか避けれました～", "思いは…届かないのか…" },//香川   37
			{ "攻撃ですっ(*‘∀‘)", "やっちゃいます(*´ω｀*)", "痛いですぅ～( ;∀;)", "反撃ですっ(; ･`д･´)", "余裕です(*'▽')", "負けちゃいました～(T_T)" },//愛媛  38
			{ "これでも喰らってろ！", "立て付くなよなぁ！", "マジかよ…", "一発もらっとけ！", "止まって見えるぜ！", "くっそおおおおおおお！！" },//高知   39
			{ "面倒だなぁ…", "しょうがないなぁ…", "え、ダメージを受けたの！？", "仕返しだよ", "ふう、危ない危ない", "ここで終わりかぁ" },//福岡  40
			{ "やるよ", "…", "…", "いくよ", "…", "…ゴメン" },//佐賀   41
			{ "これが、私の力です！", "決めます！", "ひゃあ！", "反撃、やりますよっ", "危ないところでした", "ここで…倒れるわけには…" },//長崎  42
			{ "クマ公よりは、仕事するぜ！", "やってみせるぜ！", "いってえよ！", "カウンターといこうか！", "酒がうめぇ！", "ウオアァァァ！！" },//熊本   43
			{ "やるぞっ", "これでどうだ！", "ぐふっ…やるな！", "お返しだっ！", "もっとちゃんと狙え！", "俺がやられるとは…" },//大分  44
			{ "私がやってあげる！", "やっちゃうよ！", "あなた、やるわね！", "カウンターよっ！", "そんなの、見えてるんだからっ", "みんな、あとはよろしく…" },//宮崎   45
			{ "…行きます", "…これで", "…痛い", "…反撃", "…避けた", "…バイバイ" },//鹿児島46
			{ "行っくよ～", "え～い！", "ダメだよ～", "反撃開始だよっ！", "避けちゃうよ！", "負けちゃった～" },//沖縄   47
			{ "miss", "は倒れた！", "", "" },//（なし）

	};

	/////////////////////////////////////////////////////////////

	/*
	 * スキル詳細
	 * 1,いっぱいの剣で刺して召される
	 * 2,落雷
	 * 3,ルルーシュビーム(全画面)
	 * 4,竜巻
	 * 5,派手な斬撃
	 * 6,あんこ餅落とし
	 * 7,闇的なたくさんの腕攻撃
	 * 8,カーテン(意味深)
	 * 9,めでたいわ(意味浅)
	 * 10,銃(1発)
	 * 11,水属性的な攻撃(1発)
	 * 12,ネットで捕える攻撃
	 * 13,植物が生える
	 * 14,お金攻撃
	 * 15,雪的な攻撃
	 * 16,2回かみつく
	 * 17,2回きりさく
	 * 18,2回尻尾でたたく
	 * 19,銃(4発)
	 * 20,切り刻む(全画面)
	 * 21,薄青い魔法(全画面)
	 * 22,赤い魔法(全画面)
	 * 23,青くて赤い魔法(全範囲)
	 * 24,青い魔法(全範囲)
	 * 25,21に似た魔法(全範囲)
	 * 26,青い爆発
	 * 27,落雷(全範囲)
	 * 28,1回切ります(弱そう)(カウンター用)
	 * 29,1の色違い(薄い青)
	 * 30,1の色違い(薄い橙)
	 * 31,1の色違い(やや濃ゆい青)
	 * 32,1の色違い(薄い緑)
	 * 33,1の色違い(黒)
	 * 34,6のver違い(ずんだ餅)
	 * 35,6のver違い(納豆餅)
	 * 36,キノコが生える
	 * 37,葉っぱで攻撃的ななにか
	 * 38,2回ブーメラン
	 * 39,炎で攻撃(やや全画面)
	 * 40,岩がせり上がってくる(全画面)
	 * 41,氷の魔法的な攻撃(全画面)
	 * 42,音楽で攻撃(全画面)
	 * 43,音楽で攻撃(全画面)ver2
	 * 44,音楽で攻撃(全画面)ver3
	 * 45,花的な何かがはじける
	 * 46,津波
	 * 47,闇的な攻撃
	 * 48,マグマ的な攻撃
	 * 49,かまいたち的ななにか
	 * 50,フラッシュ的な何か
	 * 51,ラブ的な攻撃
	 * 52,宇宙的な攻撃
	 * 53,花火
	 * 54,雷落とし
	 * 55,水の回転アタック
	 * 56,小さい竜巻
	 * 57,爆発(単体用)
	 * 58,小さい魔法陣
	 * 59,毒の霧もどき
	 * 60,召されろ
	 * 61,サンダーソード(仮)
	 * 62,鎌で攻撃
	 * 63,ビリビリしそうな玉
	 * 64,凍結魔法
	 * 65,噴水的ななにか
	 * 66,水の泡攻撃
	 * 67,炎の竜巻
	 * 68,水の竜巻
	 * 69,岩が下から上がってくる
	 * 70,緑の渦
	 * 71,竜巻別ver
	 * 72,光的爆発
	 * 73,ノンディレクショナルレーザー
	 * 74,水の玉がはじける
	 * 75,ヒットしたようなエフェクト
	 * 76,空からビームがいっぱい
	 * 77,回復エフェクト（ドレイン用）
	 * 78,
	 * 79,
	 * 80,
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 */

	//※どっかから引っ張ってくる変数※↓//////////////////////////////////

	int skilNumber = 76;//スキル番号

	int RightManNum = 1;//右側のキャラの番号(キャラ番号)
	int LeftManNum = 2;//左側のキャラの番号(キャラ番号)

	int RightManHP = 600;
	int RightManNowHP = 550;
	int LeftManHP = 400;
	int LeftManNowHP = 390;

	int RightManMP = 100;
	int RightManNowMP = 90;
	int LeftManMP = 100;
	int LeftManNowMP = 100;

	int Damage = 60;//受ける側ののダメージ

	boolean CounterFlag = true;//カウンター判定
	int CounterDamage = 610;//カウンターを受けた時のダメージ

	boolean RightManMiss = false;//右の人が技をmissするならtrue
	boolean LeftManMiss = false;//左の人が技をmissするならtrue

	boolean DrainFlag = true;//ドレイン判定
	int Drain = 10;//ドレイン量

	boolean CriticalFlag = true;//クリティカル判定
	boolean CounterCriticalFlag = true;//カウンター側のクリティカル判定

	int backGraundNum = 10;//背景画像番号(現在ランダム)

	boolean conditionFlag[] = {false,false,false,false,false,false,false};//状態以上かどうか

	boolean[] RightManBuff = {false,false};//0:バフがかかる 1:デバフがかかる 右側の人
	boolean[] LeftManBuff = {false,false};//0:バフがかかる 1:デバフがかかる 左側の人

	//どっかから引っ張ってくる変数↑/////////////////////////////////

	int TrueDrain = Drain;


	int backMusicNum = 10;//BGM番号

	int RightManCommentNum = 0;//プレイヤーのコメント番号

	String[] RightManComment;//プレイヤーのコメント配列
	String[] LeftManComment;//敵のコメント配列


	// コンストラクタ
	public BattleScreenPanel(AppletContext ac1, Dimension size1, MainPanel mp1,int effect_id, boolean hit, boolean critical, int damage, boolean drain_flag,
			int drain, boolean counter, boolean c_hit, boolean c_critical, int c_damage, int attack_id, int defense_id,
			int attack_now_hp, int defense_now_hp, int attack_now_sp, int defense_now_sp, int attack_maxhp,
			int defense_maxhp, int attack_maxsp, int defense_maxsp, boolean[] s_condition,boolean[] a_buff, boolean[] r_buff) throws IOException, UnsupportedAudioFileException, LineUnavailableException  {
		/////////////////////////////////////////
		this.BackMusic = null;

		backImage = null;
		workImage = null;
		RightMan = null;
		LeftMan = null;
		RightManFace = null;
		LeftManFace = null;
		BackGraund = null;
		Waku1 = null;

		skil01 = null;
		skil011 = null;
		skil012 = null;
		skil013 = null;
		skil014 = null;
		skil015 = null;
		magical01 = null;

		skil02 = null;
		skil021 = null;

		skil03 = null;
		skil031 = null;
		skil032 = null;

		skil04 = null;
		skil041 = null;

		skil05 = null;
		skil051 = null;

		skil06 = null;
		skil061 = null;
		skil062 = null; 

		skil07 = null;
		skil071 = null;

		skil08 = null;
		skil081 = null;
		skil082 = null;

		skil09 = null;
		skil091 = null;

		skil10 = null;
		skil101 = null;

		skil11 = null;
		skil111 = null;

		skil12 = null;
		skil121 = null;

		skil13 = null;
		skil131 = null;

		skil14 = null;
		skil141 = null;

		skil15 = null;
		skil151 = null;

		skil16 = null;
		skil161 = null;;
		skil162 = null;

		skil17 = null;
		skil171 = null;
		skil172 = null;

		skil18 = null;
		skil181 = null;
		skil182 = null;

		skil19 = null;
		skil191 = null;
		skil192 = null;
		skil193 = null;
		skil194 = null;

		skil20 = null;
		skil201 = null;
		skil202 = null;
		skil203 = null;
		skil204 = null;
		skil205 = null;

		skil21 = null;
		skil211 = null;

		skil22 = null;
		skil221 = null;

		skil23 = null;
		skil231 = null;

		skil24 = null;
		skil241 = null;

		skil25 = null;
		skil251 = null;

		skil26 = null;
		skil261 = null;

		skil27 = null;
		skil271 = null;

		skil28 = null;
		skil281 = null;

		skil29 = null;
		skil291 = null;
		skil292 = null;
		skil293 = null;
		skil294 = null;
		skil295 = null;
		magical29 = null;

		skil30 = null;
		skil301 = null;
		skil302 = null;
		skil303 = null;
		skil304 = null;
		skil305 = null;
		magical30 = null;

		skil31 = null;
		skil311 = null;
		skil312 = null;
		skil313 = null;
		skil314 = null;
		skil315 = null;
		magical31 = null;

		skil32 = null;
		skil321 = null;
		skil322 = null;
		skil323 = null;
		skil324 = null;
		skil325 = null;
		magical32 = null;

		skil33 = null;
		skil331 = null;
		skil332 = null;
		skil333 = null;
		skil334 = null;
		skil335 = null;
		magical33 = null;

		skil34=null;
		skil341=null;
		skil342=null;

		skil35=null;
		skil351=null;
		skil352=null;

		skil36=null;
		skil361=null;

		skil37=null;
		skil371=null;

		skil38=null;
		skil381=null;
		skil382=null;

		skil39=null;
		skil391=null;

		skil40=null;
		skil401=null;

		skil41=null;
		skil411=null;

		skil42=null;
		skil421=null;

		skil43=null;
		skil431=null;

		skil44=null;
		skil441=null;

		skil45=null;
		skil451=null;

		skil46=null;
		skil461=null;

		skil47=null;
		skil471=null;

		skil48=null;
		skil481=null;

		skil49=null;
		skil491=null;

		skil50=null;
		skil501=null;

		skil51=null;
		skil511=null;
		skil512=null;

		skil52=null;
		skil521=null;

		skil53=null;
		skil531=null;

		skil54=null;
		skil541=null;

		skil55=null;
		skil551=null;

		skil56=null;
		skil561=null;

		skil57=null;
		skil571=null;

		skil58=null;
		skil581=null;

		skil59=null;
		skil591=null;

		skil60=null;
		skil601=null;

		skil61=null;
		skil611=null;

		skil62=null;
		skil621=null;

		skil63=null;
		skil631=null;

		skil64=null;
		skil641=null;

		skil65=null;
		skil651=null;

		skil66=null;
		skil661=null;

		skil67=null;
		skil671=null;

		skil68=null;
		skil681=null;

		skil69=null;
		skil691=null;

		skil70=null;
		skil701=null;

		skil71=null;
		skil711=null;

		skil72=null;
		skil721=null;

		skil73=null;
		skil731=null;

		skil74=null;
		skil741=null;

		skil75=null;
		skil751=null;

		skil76=null;
		skil761=null;
		skil762=null;

		skil77=null;
		skil771=null;

		//////////////////////////
		buff=null;
		deBuff=null;

		///////////////////////


		skil999=null;
		skil9991=null;

		hpDown=null;
		hpDownS=null;
		Death=null;
		Miss=null;

		batu=null;
		ManAttack=null;

		/////////////////////////////////////////
		ac = ac1;
		size = size1;
		mp = mp1;
		String[] str ={"メニュー","ストーリー","マップ","ステータス","バトル","ゲームオーバー","バトルスクリーン"};//ボタンの名前の配列
		bt = new JButton[str.length];

		setSize(size);

		// ボタンの配置
		Font f = new Font("SansSerif", Font.BOLD, 20);
		FontMetrics fm = getFontMetrics(f);

		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());


		for(int i = 0 ; i < str.length ; i++){
			int w = fm.stringWidth(str[i]) + 40;
			int h = fm.getHeight() + 10;
			bt[i] = new JButton(str[i]);
			bt[i].setFont(f);
			bt[i].addActionListener(this); // アクションリスナ
			bt[i].setSize(w, h);

			if(i==6)
				continue;

			pane.add(bt[i]);
		}

		this.skilNumber = effect_id;//スキル番号

		this.RightManNum = attack_id;//右側のキャラの番号(キャラ番号)
		this.LeftManNum = defense_id;//左側のキャラの番号(キャラ番号)

		this.RightManHP = attack_maxhp;
		this.RightManNowHP = attack_now_hp+c_damage;
		this.LeftManHP = defense_maxhp;
		this.LeftManNowHP = defense_now_hp+damage;

		//////////////////////////////////////////////aaa
		System.out.println("地理用↓");
		/*if(RightManNowHP>RightManHP){
			RightManNowHP = RightManHP;
		}
		if(LeftManNowHP>LeftManHP){
			LeftManNowHP = LeftManHP;
		}
		 */

		//System.out.println("c_damage"+c_damage);
		System.out.println("");

		System.out.println("地理用↑");
		/////////////////////////////////////////////aaa


		this.RightManMP = attack_maxsp;
		this.RightManNowMP = attack_now_sp;
		this.LeftManMP = defense_maxsp;
		this.LeftManNowMP = defense_now_sp;

		this.Damage = damage;//受ける側ののダメージ

		this.CounterFlag = counter;//カウンター判定
		this.CounterDamage = c_damage;//カウンターを受けた時のダメージ

		this.RightManMiss = (hit) ? false : true;//右の人が技をmissするならtrue
		this.LeftManMiss = (c_hit) ? false : true;//左の人が技をmissするならtrue

		this.DrainFlag = drain_flag;//ドレイン判定
		this.Drain = drain;//ドレイン量

		this.CriticalFlag = critical;//クリティカル判定
		this.CounterCriticalFlag = c_critical;//カウンター側のクリティカル判定

		this.conditionFlag = s_condition;//状態以上かどうか

		this.RightManBuff = Arrays.copyOf(a_buff,2);//0:バフがかかる 1:デバフがかかる 右側の人
		this.LeftManBuff = Arrays.copyOf(r_buff,2);//0:バフがかかる 1:デバフがかかる 左側の人

		System.out.println("RightManBuff"+a_buff[0]);

		if(RightManNowHP + Drain > RightManHP){
			TrueDrain = RightManHP - RightManNowHP;
		}
		else{
			TrueDrain = Drain;
		}
		RightManNowHP -= TrueDrain;
		//System.out.println(TrueDrain);

		/*
		if (LeftManNowHP - Damage >= 0) {
			AddTime2 += 100 - ((LeftManNowHP - Damage) * 100 / LeftManHP);
		}
		else {
			AddTime2 += 100;
		}
		 */
		AddTime2 = 50;

		Random rnd1 = new Random();
		int ran1 = rnd1.nextInt(2);

		Random rnd2 = new Random();//＃
		int ran2 = rnd2.nextInt(backMusicNum) + 1;//＃


		Random rnd3 = new Random();//＃
		int ran3 = rnd3.nextInt(backGraundNum) + 1;//＃

		/*
		Random rnd4 = new Random();//＃
		int ran4 = rnd4.nextInt(skilNumber) + 1;//＃

		Random rnd5 = new Random();//＃
		int ran5 = rnd5.nextInt(47 - 1);//＃

		Random rnd6 = new Random();//＃
		int ran6 = rnd6.nextInt(47 - 1);//＃
		 */
		RightManCommentNum = ran1;

		backMusicNum = ran2;//＃BGM

		backGraundNum = ran3;//＃ 背景画像

		//skilNumber =ran4;//＃ スキル

		//RightManNum = ran5 + 1;//＃
		//LeftManNum = ran6 + 1;//＃

		try {
			if (RightManNum != LeftManNum) {
				RightMan = ImageIO.read(new File(PictureFile[RightManNum]));
				RightManFace = ImageIO.read(new File(FacePictureFile[RightManNum]));
				LeftMan = ImageIO.read(new File(PictureFile[LeftManNum]));
				LeftManFace = ImageIO.read(new File(FacePictureFile[LeftManNum]));
			}
			RightManComment = CommentFile[RightManNum];
			LeftManComment = CommentFile[LeftManNum];

			BackGraund = ImageIO.read(new File("resource/picture/BackGraund" + backGraundNum + ".jpg"));/////////////＃背景
			BackMusic = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			BackMusic.open(AudioSystem.getAudioInputStream(new File("resource/music/Battle" + backMusicNum + ".wav")));///////////////////////////＃音楽

			Waku1 = ImageIO.read(new File("resource/picture/waku001.png"));
			batu = ImageIO.read(new File("resource/picture/batu.png"));
			ManAttack = ImageIO.read(new File("resource/picture/yazirusi.png"));

			skil999 = ImageIO.read(new File("resource/picture/kirimasuyo.png"));
			skil9991 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			skil9991.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));

			skil77 = ImageIO.read(new File("resource/picture/drain.png"));
			skil771 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			skil771.open(AudioSystem.getAudioInputStream(new File("resource/music/drainoto.wav")));

			hpDownS = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			hpDownS.open(AudioSystem.getAudioInputStream(new File("resource/music/kuri.wav")));

			Death = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			Death.open(AudioSystem.getAudioInputStream(new File("resource/music/death.wav")));

			Miss = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			Miss.open(AudioSystem.getAudioInputStream(new File("resource/music/miss.wav")));

			/////////////////////////condition
			for(int i=0;i<7;i++){
				condition[i] = ImageIO.read(new File("resource/picture/condition" + i + ".png"));
			}
			buff = ImageIO.read(new File("resource/picture/buff.png"));
			deBuff = ImageIO.read(new File("resource/picture/deBuff.png"));
			//////////////////////////

			if (skilNumber == 1) {
				skil01 = ImageIO.read(new File("resource/picture/sword.png"));
				skil011 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil011.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil012 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil012.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil013 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil013.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil014 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil014.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil015 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil015.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				magical01 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				magical01.open(AudioSystem.getAudioInputStream(new File("resource/music/magical01.wav")));

			}

			else if (skilNumber == 2) {
				skil02 = ImageIO.read(new File("resource/picture/PLight.png"));
				skil021 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil021.open(AudioSystem.getAudioInputStream(new File("resource/music/PLight.wav")));

			}
			else if (skilNumber == 3) {
				skil03 = ImageIO.read(new File("resource/picture/bim.png"));
				skil031 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil031.open(AudioSystem.getAudioInputStream(new File("resource/music/explosion01.wav")));
				skil032 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil032.open(AudioSystem.getAudioInputStream(new File("resource/music/gun01.wav")));

			}
			else if (skilNumber == 4) {
				skil04 = ImageIO.read(new File("resource/picture/tornado.png"));
				skil041 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil041.open(AudioSystem.getAudioInputStream(new File("resource/music/wind.wav")));

			}
			else if (skilNumber == 5) {
				skil05 = ImageIO.read(new File("resource/picture/slash.png"));
				skil051 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil051.open(AudioSystem.getAudioInputStream(new File("resource/music/sword_f.wav")));

			}
			else if (skilNumber == 6) {
				skil06 = ImageIO.read(new File("resource/picture/mochi.png"));
				skil061 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil061.open(AudioSystem.getAudioInputStream(new File("resource/music/fallmochi.wav")));
				skil062 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil062.open(AudioSystem.getAudioInputStream(new File("resource/music/fallmochi1.wav")));

			}

			else if (skilNumber == 7) {
				skil07 = ImageIO.read(new File("resource/picture/manyhands.png"));
				skil071 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil071.open(AudioSystem.getAudioInputStream(new File("resource/music/monstergrowl.wav")));

			}
			else if (skilNumber == 8) {
				skil08 = ImageIO.read(new File("resource/picture/curtain.png"));
				skil081 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil081.open(AudioSystem.getAudioInputStream(new File("resource/music/curtainopen.wav")));
				skil082 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil082.open(AudioSystem.getAudioInputStream(new File("resource/music/chanchan.wav")));

			}
			else if (skilNumber == 9) {
				skil09 = ImageIO.read(new File("resource/picture/kamifubuki.png"));
				skil091 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil091.open(AudioSystem.getAudioInputStream(new File("resource/music/clappinghands.wav")));

			}

			else if (skilNumber == 10) {
				skil10 = ImageIO.read(new File("resource/picture/wareta.png"));
				skil101 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil101.open(AudioSystem.getAudioInputStream(new File("resource/music/gun02.wav")));

			}

			else if (skilNumber == 11) {
				skil11 = ImageIO.read(new File("resource/picture/ryushi.png"));
				skil111 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil111.open(AudioSystem.getAudioInputStream(new File("resource/music/water.wav")));

			}

			else if (skilNumber == 12) {
				skil12 = ImageIO.read(new File("resource/picture/nawa.png"));
				skil121 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil121.open(AudioSystem.getAudioInputStream(new File("resource/music/nawaoto.wav")));

			}

			else if (skilNumber == 13) {
				skil13 = ImageIO.read(new File("resource/picture/kusa.png"));
				skil131 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil131.open(AudioSystem.getAudioInputStream(new File("resource/music/kusahaeru.wav")));

			}

			else if (skilNumber == 14) {
				skil14 = ImageIO.read(new File("resource/picture/money.png"));
				skil141 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil141.open(AudioSystem.getAudioInputStream(new File("resource/music/money1.wav")));

			}

			else if (skilNumber == 15) {
				skil15 = ImageIO.read(new File("resource/picture/kirayuki.png"));
				skil151 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil151.open(AudioSystem.getAudioInputStream(new File("resource/music/ice.wav")));

			}

			else if (skilNumber == 16) {
				skil16 = ImageIO.read(new File("resource/picture/kamu.png"));
				skil161 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil161.open(AudioSystem.getAudioInputStream(new File("resource/music/gabu.wav")));
				skil162 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil162.open(AudioSystem.getAudioInputStream(new File("resource/music/gabu.wav")));

			}

			else if (skilNumber == 17) {
				skil17 = ImageIO.read(new File("resource/picture/hikkaku.png"));
				skil171 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil171.open(AudioSystem.getAudioInputStream(new File("resource/music/hikkakuyo.wav")));
				skil172 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil172.open(AudioSystem.getAudioInputStream(new File("resource/music/hikkakuyo.wav")));

			}

			else if (skilNumber == 18) {
				skil18 = ImageIO.read(new File("resource/picture/sippo.png"));
				skil181 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil181.open(AudioSystem.getAudioInputStream(new File("resource/music/binta.wav")));
				skil182 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil182.open(AudioSystem.getAudioInputStream(new File("resource/music/binta.wav")));

			}

			else if (skilNumber == 19) {
				skil19 = ImageIO.read(new File("resource/picture/banban.png"));
				skil191 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil191.open(AudioSystem.getAudioInputStream(new File("resource/music/gun03.wav")));
				skil192 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil192.open(AudioSystem.getAudioInputStream(new File("resource/music/gun03.wav")));
				skil193 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil193.open(AudioSystem.getAudioInputStream(new File("resource/music/gun03.wav")));
				skil194 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil194.open(AudioSystem.getAudioInputStream(new File("resource/music/gun03.wav")));
			}

			else if (skilNumber == 20) {
				skil20 = ImageIO.read(new File("resource/picture/midaregiri.png"));
				skil201 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil201.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil202 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil202.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil203 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil203.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil204 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil204.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil205 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil205.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));

			}

			else if (skilNumber == 21) {
				skil21 = ImageIO.read(new File("resource/picture/hani1.png"));
				skil211 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil211.open(AudioSystem.getAudioInputStream(new File("resource/music/hani1.wav")));

			}

			else if (skilNumber == 22) {
				skil22 = ImageIO.read(new File("resource/picture/hani2.png"));
				skil221 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil221.open(AudioSystem.getAudioInputStream(new File("resource/music/hani2.wav")));

			}

			else if (skilNumber == 23) {
				skil23 = ImageIO.read(new File("resource/picture/hani3.png"));
				skil231 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil231.open(AudioSystem.getAudioInputStream(new File("resource/music/hani3.wav")));

			}

			else if (skilNumber == 24) {
				skil24 = ImageIO.read(new File("resource/picture/hani4.png"));
				skil241 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil241.open(AudioSystem.getAudioInputStream(new File("resource/music/hani4.wav")));

			}

			else if (skilNumber == 25) {
				skil25 = ImageIO.read(new File("resource/picture/hani5.png"));
				skil251 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil251.open(AudioSystem.getAudioInputStream(new File("resource/music/hani1.wav")));

			}

			else if (skilNumber == 26) {
				skil26 = ImageIO.read(new File("resource/picture/bakuhatu1.png"));
				skil261 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil261.open(AudioSystem.getAudioInputStream(new File("resource/music/explosion01.wav")));

			}

			else if (skilNumber == 27) {
				skil27 = ImageIO.read(new File("resource/picture/rakurai.png"));
				skil271 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil271.open(AudioSystem.getAudioInputStream(new File("resource/music/manykaminari.wav")));

			}

			else if (skilNumber == 28) {
				skil28 = ImageIO.read(new File("resource/picture/kirimasuyo.png"));
				skil281 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil281.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));

			}

			else if (skilNumber == 29) {
				skil29 = ImageIO.read(new File("resource/picture/sword1.png"));
				skil291 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil291.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil292 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil292.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil293 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil293.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil294 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil294.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil295 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil295.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				magical29 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				magical29.open(AudioSystem.getAudioInputStream(new File("resource/music/magical01.wav")));

			}

			else if (skilNumber == 30) {
				skil30 = ImageIO.read(new File("resource/picture/sword2.png"));
				skil301 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil301.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil302 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil302.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil303 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil303.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil304 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil304.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil305 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil305.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				magical30 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				magical30.open(AudioSystem.getAudioInputStream(new File("resource/music/magical01.wav")));

			}

			else if (skilNumber == 31) {
				skil31 = ImageIO.read(new File("resource/picture/sword3.png"));
				skil311 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil311.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil312 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil312.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil313 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil313.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil314 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil314.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil315 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil315.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				magical31 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				magical31.open(AudioSystem.getAudioInputStream(new File("resource/music/magical01.wav")));

			}

			else if (skilNumber == 32) {
				skil32 = ImageIO.read(new File("resource/picture/sword4.png"));
				skil321 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil321.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil322 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil322.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil323 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil323.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil324 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil324.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil325 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil325.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				magical32 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				magical32.open(AudioSystem.getAudioInputStream(new File("resource/music/magical01.wav")));

			}

			else if (skilNumber == 33) {
				skil33 = ImageIO.read(new File("resource/picture/sword5.png"));
				skil331 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil331.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil332 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil332.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil333 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil333.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil334 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil334.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				skil335 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil335.open(AudioSystem.getAudioInputStream(new File("resource/music/sword.wav")));
				magical33 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				magical33.open(AudioSystem.getAudioInputStream(new File("resource/music/magical01.wav")));

			}

			else if (skilNumber == 34) {
				skil34 = ImageIO.read(new File("resource/picture/mochi1.png"));
				skil341 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil341.open(AudioSystem.getAudioInputStream(new File("resource/music/fallmochi.wav")));
				skil342 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil342.open(AudioSystem.getAudioInputStream(new File("resource/music/fallmochi1.wav")));

			}

			else if (skilNumber == 35) {
				skil35 = ImageIO.read(new File("resource/picture/mochi2.png"));
				skil351 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil351.open(AudioSystem.getAudioInputStream(new File("resource/music/fallmochi.wav")));
				skil352 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil352.open(AudioSystem.getAudioInputStream(new File("resource/music/fallmochi1.wav")));

			}

			else if (skilNumber == 36) {
				skil36 = ImageIO.read(new File("resource/picture/kusa1.png"));
				skil361 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil361.open(AudioSystem.getAudioInputStream(new File("resource/music/kusahaeru.wav")));

			}

			else if (skilNumber == 37) {
				skil37 = ImageIO.read(new File("resource/picture/kusa2.png"));
				skil371 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil371.open(AudioSystem.getAudioInputStream(new File("resource/music/kusahaeru.wav")));

			}

			else if (skilNumber == 38) {
				skil38 = ImageIO.read(new File("resource/picture/sippo1.png"));
				skil381 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil381.open(AudioSystem.getAudioInputStream(new File("resource/music/binta.wav")));
				skil382 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil382.open(AudioSystem.getAudioInputStream(new File("resource/music/binta.wav")));

			}

			else if (skilNumber == 39) {
				skil39 = ImageIO.read(new File("resource/picture/moeteru.png"));
				skil391 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil391.open(AudioSystem.getAudioInputStream(new File("resource/music/moeteru.wav")));

			}

			else if (skilNumber == 40) {
				skil40 = ImageIO.read(new File("resource/picture/iwa.png"));
				skil401 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil401.open(AudioSystem.getAudioInputStream(new File("resource/music/iwa.wav")));

			}

			else if (skilNumber == 41) {
				skil41 = ImageIO.read(new File("resource/picture/koori.png"));
				skil411 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil411.open(AudioSystem.getAudioInputStream(new File("resource/music/koori.wav")));

			}

			else if (skilNumber == 42) {
				skil42 = ImageIO.read(new File("resource/picture/onpu.png"));
				skil421 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil421.open(AudioSystem.getAudioInputStream(new File("resource/music/onpu1.wav")));

			}

			else if (skilNumber == 43) {
				skil43 = ImageIO.read(new File("resource/picture/onpu.png"));
				skil431 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil431.open(AudioSystem.getAudioInputStream(new File("resource/music/onpu2.wav")));

			}

			else if (skilNumber == 44) {
				skil44 = ImageIO.read(new File("resource/picture/onpu.png"));
				skil441 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil441.open(AudioSystem.getAudioInputStream(new File("resource/music/onpu3.wav")));

			}

			else if (skilNumber == 45) {
				skil45 = ImageIO.read(new File("resource/picture/sakura.png"));
				skil451 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil451.open(AudioSystem.getAudioInputStream(new File("resource/music/hana.wav")));

			}

			else if (skilNumber == 46) {
				skil46 = ImageIO.read(new File("resource/picture/nami.png"));
				skil461 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil461.open(AudioSystem.getAudioInputStream(new File("resource/music/nami.wav")));

			}

			else if (skilNumber == 47) {
				skil47 = ImageIO.read(new File("resource/picture/yami.png"));
				skil471 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil471.open(AudioSystem.getAudioInputStream(new File("resource/music/yami.wav")));

			}

			else if (skilNumber == 48) {
				skil48 = ImageIO.read(new File("resource/picture/bakuhatu.png"));
				skil481 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil481.open(AudioSystem.getAudioInputStream(new File("resource/music/bakuhatu.wav")));

			}

			else if (skilNumber == 49) {
				skil49 = ImageIO.read(new File("resource/picture/kaze.png"));
				skil491 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil491.open(AudioSystem.getAudioInputStream(new File("resource/music/shippu.wav")));

			}

			else if (skilNumber == 50) {
				skil50 = ImageIO.read(new File("resource/picture/lightburst.png"));
				skil501 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil501.open(AudioSystem.getAudioInputStream(new File("resource/music/up2.wav")));

			}

			else if (skilNumber == 51) {
				skil51 = ImageIO.read(new File("resource/picture/lovelove.png"));
				skil511 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil511.open(AudioSystem.getAudioInputStream(new File("resource/music/love1.wav")));
				skil512 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil512.open(AudioSystem.getAudioInputStream(new File("resource/music/love2.wav")));

			}

			else if (skilNumber == 52) {
				skil52 = ImageIO.read(new File("resource/picture/hoshihikari.png"));
				skil521 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil521.open(AudioSystem.getAudioInputStream(new File("resource/music/sora.wav")));

			}

			else if (skilNumber == 53) {
				skil53 = ImageIO.read(new File("resource/picture/fireflower.png"));
				skil531 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil531.open(AudioSystem.getAudioInputStream(new File("resource/music/firework.wav")));

			}

			else if (skilNumber == 54) {
				skil54 = ImageIO.read(new File("resource/picture/kaminari.png"));
				skil541 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil541.open(AudioSystem.getAudioInputStream(new File("resource/music/kaminari.wav")));

			}

			else if (skilNumber == 55) {
				skil55 = ImageIO.read(new File("resource/picture/uzumaki.png"));
				skil551 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil551.open(AudioSystem.getAudioInputStream(new File("resource/music/water2.wav")));

			}

			else if (skilNumber == 56) {
				skil56 = ImageIO.read(new File("resource/picture/kazemodoki.png"));
				skil561 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil561.open(AudioSystem.getAudioInputStream(new File("resource/music/wind.wav")));

			}

			else if (skilNumber == 57) {
				skil57 = ImageIO.read(new File("resource/picture/don.png"));
				skil571 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil571.open(AudioSystem.getAudioInputStream(new File("resource/music/don.wav")));

			}

			else if (skilNumber == 58) {
				skil58 = ImageIO.read(new File("resource/picture/mahouzin.png"));
				skil581 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil581.open(AudioSystem.getAudioInputStream(new File("resource/music/mahouzin.wav")));

			}

			else if (skilNumber == 59) {
				skil59 = ImageIO.read(new File("resource/picture/poison.png"));
				skil591 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil591.open(AudioSystem.getAudioInputStream(new File("resource/music/dokudoku.wav")));

			}

			else if (skilNumber == 60) {
				skil60 = ImageIO.read(new File("resource/picture/mesarero.png"));
				skil601 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil601.open(AudioSystem.getAudioInputStream(new File("resource/music/kiero.wav")));

			}

			else if (skilNumber == 61) {
				skil61 = ImageIO.read(new File("resource/picture/thundersword.png"));
				skil611 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil611.open(AudioSystem.getAudioInputStream(new File("resource/music/yami2.wav")));

			}

			else if (skilNumber == 62) {
				skil62 = ImageIO.read(new File("resource/picture/kama.png"));
				skil621 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil621.open(AudioSystem.getAudioInputStream(new File("resource/music/swing3.wav")));

			}

			else if (skilNumber == 63) {
				skil63 = ImageIO.read(new File("resource/picture/sugoibakuhatu.png"));
				skil631 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil631.open(AudioSystem.getAudioInputStream(new File("resource/music/magicwave3.wav")));

			}

			else if (skilNumber == 64) {
				skil64 = ImageIO.read(new File("resource/picture/ice2.png"));
				skil641 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil641.open(AudioSystem.getAudioInputStream(new File("resource/music/ice2.wav")));

			}

			else if (skilNumber == 65) {
				skil65 = ImageIO.read(new File("resource/picture/hunsui.png"));
				skil651 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil651.open(AudioSystem.getAudioInputStream(new File("resource/music/watahuru.wav")));

			}

			else if (skilNumber == 66) {
				skil66 = ImageIO.read(new File("resource/picture/wat03.png"));
				skil661 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil661.open(AudioSystem.getAudioInputStream(new File("resource/music/pukupuku.wav")));

			}

			else if (skilNumber == 67) {
				skil67 = ImageIO.read(new File("resource/picture/firetatumaki.png"));
				skil671 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil671.open(AudioSystem.getAudioInputStream(new File("resource/music/firetatumaki.wav")));

			}

			else if (skilNumber == 68) {
				skil68 = ImageIO.read(new File("resource/picture/watertatumaki.png"));
				skil681 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil681.open(AudioSystem.getAudioInputStream(new File("resource/music/watertatumaki.wav")));

			}

			else if (skilNumber == 69) {
				skil69 = ImageIO.read(new File("resource/picture/iwaagari.png"));
				skil691 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil691.open(AudioSystem.getAudioInputStream(new File("resource/music/iwaagari.wav")));

			}

			else if (skilNumber == 70) {
				skil70 = ImageIO.read(new File("resource/picture/uzu.png"));
				skil701 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil701.open(AudioSystem.getAudioInputStream(new File("resource/music/ayasi.wav")));

			}

			else if (skilNumber == 71) {
				skil71 = ImageIO.read(new File("resource/picture/wind11.png"));
				skil711 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil711.open(AudioSystem.getAudioInputStream(new File("resource/music/wind.wav")));

			}

			else if (skilNumber == 72) {
				skil72 = ImageIO.read(new File("resource/picture/expro.png"));
				skil721 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil721.open(AudioSystem.getAudioInputStream(new File("resource/music/attack2.wav")));

			}

			else if (skilNumber == 73) {
				skil73 = ImageIO.read(new File("resource/picture/patye.png"));
				skil731 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil731.open(AudioSystem.getAudioInputStream(new File("resource/music/beam.wav")));

			}

			else if (skilNumber == 74) {
				skil74 = ImageIO.read(new File("resource/picture/mizuawa.png"));
				skil741 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil741.open(AudioSystem.getAudioInputStream(new File("resource/music/water.wav")));

			}

			else if (skilNumber == 75) {
				skil75 = ImageIO.read(new File("resource/picture/hit.png"));
				skil751 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil751.open(AudioSystem.getAudioInputStream(new File("resource/music/chui.wav")));

			}

			else if (skilNumber == 76) {
				skil76 = ImageIO.read(new File("resource/picture/giruga.png"));
				skil761 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil761.open(AudioSystem.getAudioInputStream(new File("resource/music/explosion01.wav")));
				skil762 = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
				skil762.open(AudioSystem.getAudioInputStream(new File("resource/music/gun01.wav")));

			}

			hpDown = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			hpDown.open(AudioSystem.getAudioInputStream(new File("resource/music/fall.wav")));

			endButton = new ImageIcon("resource/picture/skip.gif");
			endButtonPic = ImageIO.read(new File("resource/picture/skip.gif"));

		} catch (IOException e) {
			throw new RuntimeException(e);

		}

		pane = getContentPane(); // 台紙の準備
		setLayout(null);//レイアウトをnullに

		button = new JButton("", endButton); // ボタン生成
		button.setBounds(510, 570, 90, 30);
		button.addActionListener(this);
		pane.add(button);

		start();
		mp.bp.openCombat();

	}

	public void start() {
		if (th == null) {
			th = new Thread(this);
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
		//System.out.println("run");

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

		if (CounterTurn) {
			if (countTime >= 170 + AddTime2 + AddTime1) {
				if (skil999x != 640 * 2) {
					skil999x += 640;
				}
				else if (skil999x == 640 * 2) {
					skil999x = 0;
					skil999y += 480;
				}
			}
		}

		if(DefenseTurn && DrainFlag){
			if(countTime >= 100 + AddTime1){
				if (skil77x != 240 * 9 && countTime %2 == 0) {
					skil77x += 240;
				}

			}
		}
		///////////////////////////////condition

		if(DefenseTurn&&countTime%2==0){

			for(int i=0;i<2;i++){
				if(RightManBuff[i]){
					RightManBuffx[i]+=240;
				}
				if(countTime>100+lateTime)
					if(LeftManBuff[i]){
						LeftManBuffx[i]+=240;
					}
			}

			if(conditionFlag[0]){
				conditionx[0]+=240;
				//System.out.println("conditionFlag");
			}
			if(conditionFlag[1]){
				conditionx[1]+=240;
			}
			if(conditionFlag[2]){
				conditionx[2]+=240;
			}
			if(conditionFlag[3]&&conditiony[3]!=192*1){
				if(conditionx[3]!=192*4){
					conditionx[3]+=192;
				}
				else{
					conditionx[3]=0;
					conditiony[3]+=192;
				}
			}
			if(conditionFlag[4]){
				conditionx[4]+=128;
			}
			if(conditionFlag[5]){
				conditionx[5]+=240;
			}
			if(conditionFlag[6]){
				conditionx[6]+=200;
			}
		}
		//////////////////////////////////////

		if (AttackTurn) {

			if (skilNumber == 1 && skil01Count != 20) {
				if (countTime >= 50) {
					if (skil01x != 240 * 4) {
						skil01x += 240;
					}
					else if (skil01x == 240 * 4) {
						skil01x = 0;
						skil01y += 240;
					}
				}
			}

			else if (skilNumber == 2 && skil02Count != 20) {
				if (countTime >= 50) {
					if (skil02x != 320 * 1) {
						skil02x += 320;
					}
					else if (skil02x == 320 * 1) {
						skil02x = 0;
						skil02y += 360;
					}
				}
			}

			else if (skilNumber == 3 && skil03Count != 30) {
				if (countTime >= 50) {
					if (skil03x != 640 * 1) {
						skil03x += 640;
					}
					else if (skil03x == 640 * 1) {
						skil03x = 0;
						skil03y += 480;
					}
				}
			}

			else if (skilNumber == 4 && skil04Count != 30) {
				if (countTime >= 50) {
					if (skil04x != 256 * 4) {
						skil04x += 256;
					}
					else if (skil04x == 256 * 4) {
						skil04x = 0;
						skil04y += 256;
					}
				}
			}

			else if (skilNumber == 5 && skil05Count != 30) {
				if (countTime >= 50) {
					if (skil05y != 480 * 10) {
						skil05y += 480;
					}

				}
			}

			else if (skilNumber == 6 && skil06Count != 30) {
				if (countTime >= 50) {
					if (skil06x != 240 * 4) {
						skil06x += 240;
					}
					else if (skil06x == 240 * 4) {
						skil06x = 0;
						skil06y += 240;
					}
				}
			}

			else if (skilNumber == 7 && skil07Count != 30) {
				if (countTime >= 50) {
					if (skil07x != 240 * 4) {
						skil07x += 240;
					}
					else if (skil07x == 240 * 4) {
						skil07x = 0;
						if (skil07y != 240)
							skil07y += 240;
						else
							skil07y = 0;
					}
				}
			}

			else if (skilNumber == 8 && skil08Count != 30) {
				if (countTime >= 50) {
					if (skil08y != 480 * 17) {
						skil08y += 480;
					}

				}
			}

			else if (skilNumber == 9 && skil09Count != 30) {
				if (countTime >= 50) {
					if (skil09x != 640 * 2) {
						skil09x += 640;
					}
					else if (skil09x == 640 * 2) {
						skil09x = 0;
						skil09y += 480;
					}

				}
			}

			else if (skilNumber == 10 && skil10Count != 30) {
				if (countTime >= 50) {
					if (skil10x != 100 * 9) {
						skil10x += 100;
					}

				}
			}

			else if (skilNumber == 11 && skil11Count != 30) {
				if (countTime >= 50) {
					if (skil11y != 480 * 14) {
						skil11y += 480;
					}

				}
			}

			else if (skilNumber == 12 && skil12Count != 30) {
				if (countTime >= 50) {
					if (skil12x != 640 * 2) {
						skil12x += 640;
					}
					else if (skil12x == 640 * 2) {
						skil12x = 0;
						skil12y += 480;
					}
				}
			}

			else if (skilNumber == 13 && skil13Count != 30) {
				if (countTime >= 50) {
					if (skil13x != 240 * 4) {
						skil13x += 240;
					}
					else if (skil13x == 240 * 4) {
						skil13x = 0;
						skil13y += 240;
					}
				}
			}

			else if (skilNumber == 14 && skil14Count != 30) {
				if (countTime >= 50) {
					if (skil14x != 240 * 4) {
						skil14x += 240;
					}
					else if (skil14x == 240 * 4) {
						skil14x = 0;
						skil14y += 240;
					}
				}
			}

			else if (skilNumber == 15 && skil15Count != 30) {
				if (countTime >= 50) {
					if (skil15x != 640 * 2) {
						skil15x += 640;
					}
					else if (skil15x == 640 * 2) {
						skil15x = 0;
						skil15y += 480;
					}
				}
			}

			else if (skilNumber == 16 && skil16Count != 20) {
				if (countTime >= 50) {
					if (skil16x != 240 * 4) {
						skil16x += 240;
					}
					else if (skil16x == 240 * 4) {
						skil16x = 0;
						skil16y += 240;
						if (skil16y > 240)
							skil16y = 0;
					}
				}
			}

			else if (skilNumber == 17 && skil17Count != 20) {
				if (countTime >= 50) {
					if (skil17x != 240 * 4) {
						skil17x += 240;
					}
					else if (skil17x == 240 * 4) {
						skil17x = 0;
						skil17y += 240;
						if (skil17y > 240)
							skil17y = 0;
					}
				}
			}

			else if (skilNumber == 18 && skil18Count != 20) {
				if (countTime >= 50) {
					if (skil18x != 320 * 4) {
						skil18x += 320;
					}
					else if (skil18x == 320 * 4) {
						skil18x = 0;
						skil18y += 240;
						if (skil18y > 240)
							skil18y = 0;
					}
				}
			}

			else if (skilNumber == 19 && skil19Count != 15) {
				if (countTime >= 50) {
					if (skil19x != 240 * 4) {
						skil19x += 240;
					}
					else if (skil19x == 240 * 4) {
						skil19x = 0;
						skil19y += 240;
					}
				}
			}

			else if (skilNumber == 20 && skil20Count != 24) {
				if (countTime >= 50) {
					if (skil20x != 640 * 1) {
						skil20x += 640;
					}
					else if (skil20x == 640 * 1) {
						skil20x = 0;
						skil20y += 480;
					}
				}
			}

			else if (skilNumber == 21 && skil21Count != 10) {
				if (countTime >= 50) {
					if (skil21x != 640 * 1) {
						skil21x += 640;
					}
					else if (skil21x == 640 * 1) {
						skil21x = 0;
						skil21y += 480;
					}
				}
			}

			else if (skilNumber == 22 && skil22Count != 10) {
				if (countTime >= 50) {
					if (skil22x != 640 * 1) {
						skil22x += 640;
					}
					else if (skil22x == 640 * 1) {
						skil22x = 0;
						skil22y += 480;
					}
				}
			}

			else if (skilNumber == 23 && skil23Count != 10) {
				if (countTime >= 50) {
					if (skil23x != 640 * 1) {
						skil23x += 640;
					}
					else if (skil23x == 640 * 1) {
						skil23x = 0;
						skil23y += 480;
					}
				}
			}

			else if (skilNumber == 24 && skil24Count != 10) {
				if (countTime >= 50) {
					if (skil24x != 640 * 1) {
						skil24x += 640;
					}
					else if (skil24x == 640 * 1) {
						skil24x = 0;
						skil24y += 480;
					}
				}
			}

			else if (skilNumber == 25 && skil25Count != 10) {
				if (countTime >= 50) {
					if (skil25x != 640 * 1) {
						skil25x += 640;
					}
					else if (skil25x == 640 * 1) {
						skil25x = 0;
						skil25y += 480;
					}
				}
			}

			else if (skilNumber == 26 && skil26Count != 7) {
				if (countTime >= 50) {
					skil26y += 240;
				}
			}

			else if (skilNumber == 27 && skil27Count != 15) {
				if (countTime >= 50) {
					if (skil27y == 240 * 4)
						skil27y = 0;
					else
						skil27y += 240;
				}
			}

			else if (skilNumber == 28 && skil28Count != 9) {
				if (countTime >= 50) {
					if (skil28x != 640 * 2) {
						skil28x += 640;
					}
					else if (skil28x == 640 * 2) {
						skil28x = 0;
						skil28y += 480;
					}
				}
			}

			else if (skilNumber == 29 && skil29Count != 20) {
				if (countTime >= 50) {
					if (skil29x != 240 * 4) {
						skil29x += 240;
					}
					else if (skil29x == 240 * 4) {
						skil29x = 0;
						skil29y += 240;
					}
				}
			}

			else if (skilNumber == 30 && skil30Count != 20) {
				if (countTime >= 50) {
					if (skil30x != 240 * 4) {
						skil30x += 240;
					}
					else if (skil30x == 240 * 4) {
						skil30x = 0;
						skil30y += 240;
					}
				}
			}

			else if (skilNumber == 31 && skil31Count != 20) {
				if (countTime >= 50) {
					if (skil31x != 240 * 4) {
						skil31x += 240;
					}
					else if (skil31x == 240 * 4) {
						skil31x = 0;
						skil31y += 240;
					}
				}
			}

			else if (skilNumber == 32 && skil32Count != 20) {
				if (countTime >= 50) {
					if (skil32x != 240 * 4) {
						skil32x += 240;
					}
					else if (skil32x == 240 * 4) {
						skil32x = 0;
						skil32y += 240;
					}
				}
			}

			else if (skilNumber == 33 && skil33Count != 20) {
				if (countTime >= 50) {
					if (skil33x != 240 * 4) {
						skil33x += 240;
					}
					else if (skil33x == 240 * 4) {
						skil33x = 0;
						skil33y += 240;
					}
				}
			}

			else if (skilNumber == 34 && skil34Count != 30) {
				if (countTime >= 50) {
					if (skil34x != 240 * 4) {
						skil34x += 240;
					}
					else if (skil34x == 240 * 4) {
						skil34x = 0;
						skil34y += 240;
					}
				}
			}

			else if (skilNumber == 35 && skil35Count != 30) {
				if (countTime >= 50) {
					if (skil35x != 240 * 4) {
						skil35x += 240;
					}
					else if (skil35x == 240 * 4) {
						skil35x = 0;
						skil35y += 240;
					}
				}
			}

			else if (skilNumber == 36 && skil36Count != 30) {
				if (countTime >= 50) {
					if (skil36x != 240 * 4) {
						skil36x += 240;
					}
					else if (skil36x == 240 * 4) {
						skil36x = 0;
						skil36y += 240;
					}
				}
			}

			else if (skilNumber == 37 && skil37Count != 30) {
				if (countTime >= 50) {
					if (skil37x != 240 * 4) {
						skil37x += 240;
					}
					else if (skil37x == 240 * 4) {
						skil37x = 0;
						skil37y += 240;
					}
				}
			}

			else if (skilNumber == 38 && skil38Count != 20) {
				if (countTime >= 50) {
					if (skil38x != 640 * 4) {
						skil38x += 640;
					}
					else if (skil38x == 640 * 4) {
						skil38x = 0;
						skil38y += 480;
						if (skil38y > 480)
							skil38y = 0;
					}
				}
			}

			else if (skilNumber == 39 && skil39Count != 16) {
				if (countTime >= 50) {
					if (skil39y != 240 * 8) {
						skil39y += 240;
					}
					else
						skil39y = 0;

				}
			}

			else if (skilNumber == 40 && skil40Count != 16) {
				if (countTime >= 50) {
					if (skil40y != 240 * 8) {
						skil40y += 240;
					}
					else
						skil40y = 0;

				}
			}

			else if (skilNumber == 41 && skil41Count != 16) {
				if (countTime >= 50) {
					if (skil41y != 240 * 8) {
						skil41y += 240;
					}
					else
						skil41y = 0;

				}
			}

			else if (skilNumber == 42 && skil42Count != 30) {
				if (countTime >= 50) {
					if (skil42y != 240 * 15) {
						skil42y += 240;
					}
					else
						skil42y = 0;

				}
			}

			else if (skilNumber == 43 && skil43Count != 30) {
				if (countTime >= 50) {
					if (skil43y != 240 * 15) {
						skil43y += 240;
					}
					else
						skil43y = 0;

				}
			}

			else if (skilNumber == 44 && skil44Count != 30) {
				if (countTime >= 50) {
					if (skil44y != 240 * 15) {
						skil44y += 240;
					}
					else
						skil44y = 0;

				}
			}

			else if (skilNumber == 45 && skil45Count != 30 && skil45Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil45y != 240 * 15) {
						skil45y += 240;
					}
					else
						skil45y = 0;

				}
			}

			else if (skilNumber == 46 && skil46Count != 16) {
				if (countTime >= 50) {
					if (skil46y != 240 * 8) {
						skil46y += 240;
					}
					else
						skil46y = 0;

				}
			}

			else if (skilNumber == 47 && skil47Count != 32 && skil47Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil47y != 240 * 8) {
						skil47y += 240;
					}
					else
						skil47y = 0;

				}
			}

			else if (skilNumber == 48 && skil48Count != 32) {
				if (countTime >= 50) {
					if (skil48y != 240 * 8) {
						skil48y += 240;
					}
					else
						skil48y = 0;

				}
			}

			else if (skilNumber == 49 && skil49Count != 16 && skil49Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil49y != 240 * 7) {
						skil49y += 240;
					}
					else
						skil49y = 0;

				}
			}

			else if (skilNumber == 50 && skil50Count != 16) {
				if (countTime >= 50) {
					if (skil50y != 480 * 10) {
						skil50y += 480;
					}
					else
						skil50y = 0;

				}
			}

			else if (skilNumber == 51 && skil51Count != 40 && skil51Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil51x != 240 * 4) {
						skil51x += 240;
					}
					else if (skil51x == 240 * 4) {
						skil51x = 0;
						skil51y += 240;
					}

				}
			}

			else if (skilNumber == 52 && skil52Count != 40 && skil52Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil52y != 480 * 20) {
						skil52y += 480;
					}
					else
						skil52y = 0;

				}
			}

			else if (skilNumber == 53 && skil53Count != 12 && skil53Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil53x != 400 * 14) {
						skil53x += 400;
					}
					else
						skil53x = 0;

				}
			}

			else if (skilNumber == 54 && skil54Count != 16 && skil54Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil54x != 480 * 14) {
						skil54x += 480;
					}
					else
						skil54x = 0;

				}
			}

			else if (skilNumber == 55 && skil55Count != 30 && skil55Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil55x != 312 * 15) {
						skil55x += 312;
					}
					else
						skil55x = 0;

				}
			}

			else if (skilNumber == 56 && skil56Count != 25 && skil56Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil56x != 188 * 13) {
						skil56x += 188;
					}
					else
						skil56x = 0;

				}
			}

			else if (skilNumber == 57 && skil57Count != 22 && skil57Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil57x != 360 * 11) {
						skil57x += 360;
					}
					else
						skil57x = 0;

				}
			}

			else if (skilNumber == 58 && skil58Count != 24 && skil58Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil58x != 480 * 11) {
						skil58x += 480;
					}
					else
						skil58x = 0;

				}
			}

			else if (skilNumber == 59 && skil59Count != 26 && skil59Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil59x != 480 * 13) {
						skil59x += 480;
					}
					else
						skil59x = 0;

				}
			}

			else if (skilNumber == 60 && skil60Count != 20 && skil60Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil60x != 240 * 10) {
						skil60x += 240;
					}
					else
						skil60x = 0;

				}
			}

			else if (skilNumber == 61 && skil61Count != 20 && skil61Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil61x != 192 * 10) {
						skil61x += 192;
					}
					else
						skil61x = 0;

				}
			}

			else if (skilNumber == 62 && skil62Count != 21 && skil62Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil62x != 192 * 4) {
						skil62x += 192;
					}
					else if (skil62x == 192 * 4) {
						skil62x = 0;
						skil62y += 192;
					}

				}
			}

			else if (skilNumber == 63 && skil63Count != 20 && skil63Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil63x != 192 * 4) {
						skil63x += 192;
					}
					else if (skil63x == 192 * 4) {
						skil63x = 192 * 2;
					}

				}
			}

			else if (skilNumber == 64 && skil64Count != 20 && skil64Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil64x != 192 * 4) {
						skil64x += 192;
					}
					else if (skil64x == 192 * 4) {
						skil64x = 192 * 4;
					}

				}
			}

			else if (skilNumber == 65 && skil65Count != 17 && skil65Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil65x != 80 * 8) {
						skil65x += 80;
					}
					else if (skil65x == 80 * 8) {
						skil65x = 0;
					}

				}
			}

			else if (skilNumber == 66 && skil66Count != 17 && skil66Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil66x != 96 * 7) {
						skil66x += 96;
					}
					else if (skil66x == 96 * 7) {
						skil66x = 0;
					}

				}
			}

			else if (skilNumber == 67 && skil67Count != 18 && skil67Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil67x != 128 * 8) {
						skil67x += 128;
					}
					else if (skil67x == 128 * 8) {
						skil67x = 0;
					}

				}
			}

			else if (skilNumber == 68 && skil68Count != 18 && skil68Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil68x != 128 * 8) {
						skil68x += 128;
					}
					else if (skil68x == 128 * 8) {
						skil68x = 0;
					}

				}
			}

			else if (skilNumber == 69 && skil69Count != 16 && skil69Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil69x != 96 * 7) {
						skil69x += 96;
					}
					else if (skil69x == 96 * 7) {
						skil69x = 0;
					}

				}
			}

			else if (skilNumber == 70 && skil70Count != 10 && skil70Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil70x != 192 * 4) {
						skil70x += 192;
					}
					else if (skil70x == 192 * 4) {
						skil70x = 0;
					}

				}
			}

			else if (skilNumber == 71 && skil71Count != 20 && skil71Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil71x != 96 * 7) {
						skil71x += 96;
					}
					else if (skil71x == 96 * 7) {
						skil71x = 96 * 8;
					}

				}
			}

			else if (skilNumber == 72 && skil72Count != 16 && skil72Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil72x != 192 * 4) {
						skil72x += 192;
						if (skil72y == 192 && skil72x == 192 * 3) {
							skil72y = 0;
							skil72x = 0;
						}

					}
					else if (skil72x == 192 * 4) {
						skil72x = 0;
						skil72y += 192;
					}

				}
			}

			else if (skilNumber == 73 && skil73Count != 40) {
				if (countTime >= 50) {
					if (skil73x != 192 * 4) {
						skil73x += 192;
					}
					else if (skil73x == 192 * 4) {
						skil73x = 0;
						skil73y += 192;
					}

				}
			}

			else if (skilNumber == 74 && skil74Count != 12 && skil74Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil74x != 192 * 4) {
						skil74x += 192;
					}
					else if (skil74x == 192 * 4) {
						skil74x = 0;
						skil74y += 192;
					}

				}
			}

			else if (skilNumber == 75 && skil75Count != 20 && skil75Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil75x != 192 * 4) {
						skil75x += 192;
					}
					else if (skil75x == 192 * 4) {
						skil75x = 0;
						skil75y += 192;
					}

				}
			}

			else if (skilNumber == 76 && skil76Count != 24 && skil76Count % 2 == 0) {
				if (countTime >= 50) {
					if (skil76y != 480 * 12) {
						skil76y += 480;
					}
					else if (skil76y == 480 * 12) {
						skil76y = 0;
					}

				}
			}

		}

		if (DefenseTurn) {

			if (damageV <= Damage && LeftManNowHP - damageV >= 0) {

				if (Damage < 20) {
					damageV += 2;
				}
				else {
					damageV += 5 * Damage / 100;
				}

			}

			else {
				damageShowTime++;
			}

			if (drainV <= Drain && RightManNowHP + drainV >= 0) {

				if (Drain < 20) {
					drainV += 2;
				}
				else {
					drainV += 5 * TrueDrain / 100;
				}
			}



		}

		if (CounterDefenseTurn) {

			if (damageV <= CounterDamage && RightManNowHP + TrueDrain - damageV >= 0) {

				if (CounterDamage < 20) {
					damageV += 2;
				}
				else {
					damageV += 5 * CounterDamage / 100;
				}

			}

			else {
				damageShowTime++;
			}

		}
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
		g.drawImage(Waku1, -10, 0, 620, 100, this);

		g.setColor(Color.WHITE);//スコアの色
		g.setFont(new Font("Monospaced", Font.PLAIN, 30));
		g.drawString("HP", 30, 40);
		g.drawString("SP", 30, 80);
		g.drawString("HP", 330, 40);
		g.drawString("SP", 330, 80);

		g.setColor(Color.WHITE);
		g.fillRect(80, 22, 200, 20);
		g.fillRect(380, 22, 200, 20);
		g.fillRect(80, 60, 200, 20);
		g.fillRect(380, 60, 200, 20);

		g.setColor(Color.GREEN);
		g.fillRect(380, 22, 200 * (RightManNowHP * 100 / RightManHP) / 100, 20);
		g.fillRect(80, 22, 200 * (LeftManNowHP * 100 / LeftManHP) / 100, 20);
		g.setColor(Color.PINK);
		g.fillRect(380, 60, 200 * (RightManNowMP * 100 / RightManMP) / 100, 20);
		g.fillRect(80, 60, 200 * (LeftManNowMP * 100 / LeftManMP) / 100, 20);

		System.out.println("LeftManNowHP"+LeftManNowHP);

		g.setColor(Color.BLACK);//スコアの色
		g.setFont(new Font("Monospaced", Font.PLAIN, 20));
		g.drawString(String.valueOf(RightManNowHP) + "/" + String.valueOf(RightManHP), 330 + 55, 40);
		g.drawString(String.valueOf(LeftManNowHP) + "/" + String.valueOf(LeftManHP), 30 + 53, 40);
		g.drawString(String.valueOf(RightManNowMP) + "/" + String.valueOf(RightManMP), 330 + 55, 78);
		g.drawString(String.valueOf(LeftManNowMP) + "/" + String.valueOf(LeftManMP), 30 + 53, 78);

		g.drawImage(endButtonPic, 510, 570, 90, 30, this);

	}

	public void createWorkImage() {

		//System.out.println(damageShowTime);

		workImage = createImage(width, height);
		Graphics g = workImage.getGraphics();

		g.drawImage(backImage, 0, 0, this);

		if (AttackTurn) {
			//戦闘前のステータス↓

			g.drawImage(RightMan, 350, 100, 200, 300, this);
			g.drawImage(LeftMan, 50, 100, 200, 300, this);

			g.drawImage(ManAttack, 280 + 50, 40 + 25, -50, -25, this);

			g.setColor(Color.WHITE);//スコアの色
			g.setFont(new Font("Monospaced", Font.PLAIN, 30));
			g.drawImage(RightManFace, 40, 430, 100, 100, this);
			g.drawString(RightManComment[RightManCommentNum], 160, 460);
			g.setFont(new Font("Monospaced", Font.PLAIN, 20));
			//g.drawString(RightManComment[0], 40, 550);
			g.drawString(TalkingMan[RightManNum], 60, 550);

			//戦闘前のステータス↑
			if (skil01Count != 20 && countTime >= 50 && skilNumber == 1) {
				magical01.start();

				g.drawImage(skil01, -120, 100, 420, 420, skil01x, skil01y, skil01x + 240, skil01y + 240, this);
				if (skil01Count == 8)
					skil011.start();
				else if (skil01Count == 9)
					skil012.start();
				else if (skil01Count == 10)
					skil013.start();
				else if (skil01Count == 11)
					skil014.start();
				else if (skil01Count == 12)
					skil015.start();

				skil01Count++;
				//System.out.println(skil01Count);
			}

			else if (skil02Count != 20 && countTime >= 50 && skilNumber == 2) {

				g.drawImage(skil02, -180, 100, 480, 480, skil02x, skil02y, skil02x + 320, skil02y + 360, this);
				if (skil02Count == 7)
					skil021.start();
				skil02Count++;
			}

			else if (skil03Count != 30 && countTime >= 50 && skilNumber == 3) {

				g.drawImage(skil03, 0, -100, 600, 600, skil03x, skil03y, skil03x + 640, skil03y + 480, this);
				if (skil03Count == 1) {
					skil032.start();
				}
				else if (skil03Count == 7)
					skil031.start();
				skil03Count++;
			}

			else if (skil04Count != 30 && countTime >= 50 && skilNumber == 4) {

				g.drawImage(skil04, -120, 100, 400, 480, skil04x, skil04y, skil04x + 256, skil04y + 256, this);
				if (skil04Count == 1) {
					skil041.start();
				}
				if (skil04Count == 29) {
					skil041.stop();
				}
				skil04Count++;
			}

			else if (skil05Count != 30 && countTime >= 50 && skilNumber == 5) {

				g.drawImage(skil05, -70, 80, 350, 420, skil05x, skil05y, skil05x + 640, skil05y + 480, this);
				if (skil05Count == 1) {
					skil051.start();
				}
				skil05Count++;
			}

			else if (skil06Count != 25 && countTime >= 50 && skilNumber == 6) {

				g.drawImage(skil06, -90, 80, 380, 420, skil06x, skil06y, skil06x + 240, skil06y + 240, this);
				if (skil06Count == 1) {
					skil061.start();
				}
				else if (skil06Count == 8) {
					skil061.stop();
					skil062.start();
				}
				skil06Count++;
			}

			else if (skil07Count != 20 && countTime >= 50 && skilNumber == 7) {

				g.drawImage(skil07, -30, 100, 300, 450, skil07x, skil07y, skil07x + 240, skil07y + 240, this);
				if (skil07Count == 1) {
					skil071.start();
				}
				skil07Count++;
			}

			else if (skil08Count != 30 && countTime >= 50 && skilNumber == 8) {

				g.drawImage(skil08, 0, 0, 600, 610, skil08x, skil08y, skil08x + 640, skil08y + 480, this);
				if (skil08Count == 1) {
					skil081.start();
				}
				else if (skil08Count == 11) {
					skil082.start();
				}
				skil08Count++;
			}

			else if (skil09Count != 32 && countTime >= 50 && skilNumber == 9) {

				g.drawImage(skil09, 0, 0, 600, 610, skil09x, skil09y, skil09x + 640, skil09y + 480, this);
				if (skil09Count == 1) {
					skil091.start();
				}
				else if (skil09Count == 31) {
					skil091.stop();
				}
				skil09Count++;
			}

			else if (skil10Count != 10 && countTime >= 50 && skilNumber == 10) {

				g.drawImage(skil10, -30, 100, 350, 350, skil10x, skil10y, skil10x + 100, skil10y + 100, this);
				skil101.start();
				skil10Count++;
			}

			else if (skil11Count != 15 && countTime >= 50 && skilNumber == 11) {

				g.drawImage(skil11, -30, 100, 350, 350, skil11x, skil11y, skil11x + 640, skil11y + 480, this);
				skil111.start();
				skil11Count++;
			}

			else if (skil12Count != 15 && countTime >= 50 && skilNumber == 12) {

				g.drawImage(skil12, -30, 100, 350, 350, skil12x, skil12y, skil12x + 640, skil12y + 480, this);
				skil121.start();
				skil12Count++;
			}

			else if (skil13Count != 15 && countTime >= 50 && skilNumber == 13) {
				g.drawImage(skil13, 20, 100, 300, 450, skil13x, skil13y, skil13x + 240, skil13y + 240, this);
				skil131.start();
				if (skil13Count == 14)
					skil131.stop();
				skil13Count++;
			}

			else if (skil14Count != 30 && countTime >= 50 && skilNumber == 14) {
				g.drawImage(skil14, 20, 100, 300, 450, skil14x, skil14y, skil14x + 240, skil14y + 280, this);
				skil141.start();
				//if (skil14Count == 14)
				//skil141.stop();
				skil14Count++;
			}

			else if (skil15Count != 30 && countTime >= 50 && skilNumber == 15) {
				g.drawImage(skil15, 20, 100, 300, 420, skil15x, skil15y, skil15x + 640, skil15y + 480, this);
				skil151.start();
				if (skil15Count == 14)
					skil151.stop();
				skil15Count++;
			}

			else if (skil16Count != 19 && countTime >= 50 && skilNumber == 16) {
				g.drawImage(skil16, 20, 100, 300, 420, skil16x, skil16y, skil16x + 240, skil16y + 240, this);
				if (skil16Count == 4)
					skil161.start();
				if (skil16Count == 14)
					skil162.start();
				skil16Count++;
			}

			else if (skil17Count != 19 && countTime >= 50 && skilNumber == 17) {
				g.drawImage(skil17, 20, 100, 300, 420, skil17x, skil17y, skil17x + 240, skil17y + 240, this);
				if (skil17Count == 4)
					skil171.start();
				if (skil17Count == 14)
					skil172.start();
				skil17Count++;
			}

			else if (skil18Count != 19 && countTime >= 50 && skilNumber == 18) {
				g.drawImage(skil18, 20, 100, 300, 420, skil18x, skil18y, skil18x + 240, skil18y + 240, this);
				if (skil18Count == 4)
					skil181.start();
				if (skil18Count == 14)
					skil182.start();
				skil18Count++;
			}

			else if (skil19Count != 15 && countTime >= 50 && skilNumber == 19) {
				g.drawImage(skil19, 20, 100, 300, 420, skil19x, skil19y, skil19x + 240, skil19y + 240, this);
				if (skil19Count == 1)
					skil191.start();
				if (skil19Count == 3)
					skil192.start();
				if (skil19Count == 5)
					skil193.start();
				if (skil19Count == 7)
					skil194.start();

				skil19Count++;
			}

			else if (skil20Count != 24 && countTime >= 50 && skilNumber == 20) {

				g.drawImage(skil20, 0, 0, 600, 610, skil20x, skil20y, skil20x + 640, skil20y + 480, this);
				if (skil20Count == 5) {
					skil201.start();
				}
				if (skil20Count == 7) {
					skil202.start();
				}
				if (skil20Count == 9) {
					skil203.start();
				}
				if (skil20Count == 11) {
					skil204.start();
				}
				if (skil20Count == 13) {
					skil205.start();
				}

				skil20Count++;
			}

			else if (skil21Count != 10 && countTime >= 50 && skilNumber == 21) {

				g.drawImage(skil21, 0, 0, 600, 610, skil21x, skil21y, skil21x + 640, skil21y + 480, this);
				if (skil21Count == 1) {
					skil211.start();
				}

				skil21Count++;
			}

			else if (skil22Count != 10 && countTime >= 50 && skilNumber == 22) {
				g.drawImage(skil22, 0, 0, 600, 610, skil22x, skil22y, skil22x + 640, skil22y + 480, this);
				if (skil22Count == 1) {
					skil221.start();
				}

				skil22Count++;
			}

			else if (skil23Count != 10 && countTime >= 50 && skilNumber == 23) {
				g.drawImage(skil23, 0, 0, 600, 610, skil23x, skil23y, skil23x + 640, skil23y + 480, this);
				if (skil23Count == 1) {
					skil231.start();
				}

				skil23Count++;
			}

			else if (skil24Count != 10 && countTime >= 50 && skilNumber == 24) {
				g.drawImage(skil24, 0, 0, 600, 610, skil24x, skil24y, skil24x + 640, skil24y + 480, this);
				if (skil24Count == 1) {
					skil241.start();
				}

				skil24Count++;
			}

			else if (skil25Count != 10 && countTime >= 50 && skilNumber == 25) {
				g.drawImage(skil25, 0, 0, 600, 610, skil25x, skil25y, skil25x + 640, skil25y + 480, this);
				if (skil25Count == 1) {
					skil251.start();
				}

				skil25Count++;
			}

			else if (skil26Count != 11 && countTime >= 50 && skilNumber == 26) {
				g.drawImage(skil26, -180, 100, 500, 410, skil26x, skil26y, skil26x + 640, skil26y + 240, this);
				if (skil26Count == 1) {
					skil261.start();
				}

				skil26Count++;
			}

			else if (skil27Count != 15 && countTime >= 50 && skilNumber == 27) {
				g.drawImage(skil27, 0, 0, 600, 610, skil27x, skil27y, skil27x + 640, skil27y + 240, this);
				if (skil27Count == 1) {
					skil271.start();
				}

				skil27Count++;
			}

			else if (skil28Count != 9 && countTime >= 50 && skilNumber == 28) {
				g.drawImage(skil28, 0, 100, 320, 420, skil28x, skil28y, skil28x + 640, skil28y + 480, this);
				if (skil28Count == 1) {
					skil281.start();
				}

				skil28Count++;
			}

			else if (skil29Count != 20 && countTime >= 50 && skilNumber == 29) {
				magical29.start();

				g.drawImage(skil29, -120, 100, 420, 420, skil29x, skil29y, skil29x + 240, skil29y + 240, this);
				if (skil29Count == 8)
					skil291.start();
				else if (skil29Count == 9)
					skil292.start();
				else if (skil29Count == 10)
					skil293.start();
				else if (skil29Count == 11)
					skil294.start();
				else if (skil29Count == 12)
					skil295.start();

				skil29Count++;
			}

			else if (skil30Count != 20 && countTime >= 50 && skilNumber == 30) {
				magical30.start();

				g.drawImage(skil30, -120, 100, 420, 420, skil30x, skil30y, skil30x + 240, skil30y + 240, this);
				if (skil30Count == 8)
					skil301.start();
				else if (skil30Count == 9)
					skil302.start();
				else if (skil30Count == 10)
					skil303.start();
				else if (skil30Count == 11)
					skil304.start();
				else if (skil30Count == 12)
					skil305.start();

				skil30Count++;
			}

			else if (skil31Count != 20 && countTime >= 50 && skilNumber == 31) {
				magical31.start();

				g.drawImage(skil31, -120, 100, 420, 420, skil31x, skil31y, skil31x + 240, skil31y + 240, this);
				if (skil31Count == 8)
					skil311.start();
				else if (skil31Count == 9)
					skil312.start();
				else if (skil31Count == 10)
					skil313.start();
				else if (skil31Count == 11)
					skil314.start();
				else if (skil31Count == 12)
					skil315.start();

				skil31Count++;
			}

			else if (skil32Count != 20 && countTime >= 50 && skilNumber == 32) {
				magical32.start();

				g.drawImage(skil32, -120, 100, 420, 420, skil32x, skil32y, skil32x + 240, skil32y + 240, this);
				if (skil32Count == 8)
					skil321.start();
				else if (skil32Count == 9)
					skil322.start();
				else if (skil32Count == 10)
					skil323.start();
				else if (skil32Count == 11)
					skil324.start();
				else if (skil32Count == 12)
					skil325.start();

				skil32Count++;
			}

			else if (skil33Count != 20 && countTime >= 50 && skilNumber == 33) {
				magical33.start();

				g.drawImage(skil33, -120, 100, 420, 420, skil33x, skil33y, skil33x + 240, skil33y + 240, this);
				if (skil33Count == 8)
					skil331.start();
				else if (skil33Count == 9)
					skil332.start();
				else if (skil33Count == 10)
					skil333.start();
				else if (skil33Count == 11)
					skil334.start();
				else if (skil33Count == 12)
					skil335.start();

				skil33Count++;
			}

			else if (skil34Count != 25 && countTime >= 50 && skilNumber == 34) {

				g.drawImage(skil34, -90, 80, 380, 420, skil34x, skil34y, skil34x + 240, skil34y + 240, this);
				if (skil34Count == 1) {
					skil341.start();
				}
				else if (skil34Count == 8) {
					skil341.stop();
					skil342.start();
				}
				skil34Count++;
			}

			else if (skil35Count != 25 && countTime >= 50 && skilNumber == 35) {

				g.drawImage(skil35, -90, 80, 380, 420, skil35x, skil35y, skil35x + 240, skil35y + 240, this);
				if (skil35Count == 1) {
					skil351.start();
				}
				else if (skil35Count == 8) {
					skil351.stop();
					skil352.start();
				}
				skil35Count++;
			}

			else if (skil36Count != 15 && countTime >= 50 && skilNumber == 36) {
				g.drawImage(skil36, 20, 100, 300, 450, skil36x, skil36y, skil36x + 240, skil36y + 240, this);
				skil361.start();
				if (skil36Count == 14)
					skil361.stop();
				skil36Count++;
			}

			else if (skil37Count != 15 && countTime >= 50 && skilNumber == 37) {
				g.drawImage(skil37, 20, 100, 300, 450, skil37x, skil37y, skil37x + 240, skil37y + 240, this);
				skil371.start();
				if (skil37Count == 14)
					skil371.stop();
				skil37Count++;
			}

			else if (skil38Count != 19 && countTime >= 50 && skilNumber == 38) {
				g.drawImage(skil38, 20, 100, 300, 420, skil38x, skil38y, skil38x + 480, skil38y + 480, this);
				if (skil38Count == 4)
					skil381.start();
				if (skil38Count == 14)
					skil382.start();
				skil38Count++;
			}

			else if (skil39Count != 16 && countTime >= 50 && skilNumber == 39) {
				g.drawImage(skil39, 0, 0, 600, 420, skil39x, skil39y, skil39x + 640, skil39y + 240, this);
				if (skil39Count == 1)
					skil391.start();
				skil39Count++;
			}

			else if (skil40Count != 16 && countTime >= 50 && skilNumber == 40) {
				g.drawImage(skil40, 0, 0, 600, 620, skil40x, skil40y, skil40x + 640, skil40y + 240, this);
				if (skil40Count == 1)
					skil401.start();
				skil40Count++;
			}

			else if (skil41Count != 16 && countTime >= 50 && skilNumber == 41) {
				g.drawImage(skil41, 0, 0, 600, 420, skil41x, skil41y, skil41x + 640, skil41y + 240, this);
				if (skil41Count == 1)
					skil411.start();
				skil41Count++;
			}

			else if (skil42Count != 30 && countTime >= 50 && skilNumber == 42) {
				g.drawImage(skil42, 0, 0, 600, 420, skil42x, skil42y, skil42x + 640, skil42y + 240, this);
				if (skil42Count == 1)
					skil421.start();
				skil42Count++;
			}

			else if (skil43Count != 30 && countTime >= 50 && skilNumber == 43) {
				g.drawImage(skil43, 0, 0, 600, 420, skil43x, skil43y, skil43x + 640, skil43y + 240, this);
				if (skil43Count == 1)
					skil431.start();
				skil43Count++;
			}

			else if (skil44Count != 30 && countTime >= 50 && skilNumber == 44) {
				g.drawImage(skil44, 0, 0, 600, 420, skil44x, skil44y, skil44x + 640, skil44y + 240, this);
				if (skil44Count == 1)
					skil441.start();
				skil44Count++;
			}

			else if (skil45Count != 30 && countTime >= 50 && skilNumber == 45) {
				g.drawImage(skil45, 0, 0, 600, 420, skil45x, skil45y, skil45x + 640, skil45y + 240, this);
				if (skil45Count == 1)
					skil451.start();
				skil45Count++;
			}

			else if (skil46Count != 16 && countTime >= 50 && skilNumber == 46) {
				g.drawImage(skil46, 0, 0, 600, 620, skil46x, skil46y, skil46x + 640, skil46y + 240, this);
				if (skil46Count == 1)
					skil461.start();
				skil46Count++;
			}

			else if (skil47Count != 32 && countTime >= 50 && skilNumber == 47) {
				g.drawImage(skil47, 0, 0, 600, 620, skil47x, skil47y, skil47x + 640, skil47y + 240, this);
				if (skil47Count == 1)
					skil471.start();
				skil47Count++;
			}

			else if (skil48Count != 32 && countTime >= 50 && skilNumber == 48) {
				g.drawImage(skil48, 0, 0, 600, 620, skil48x, skil48y, skil48x + 640, skil48y + 240, this);
				if (skil48Count == 1)
					skil481.start();
				skil48Count++;
			}

			else if (skil49Count != 30 && countTime >= 50 && skilNumber == 49) {
				g.drawImage(skil49, 0, 0, 600, 620, skil49x, skil49y, skil49x + 640, skil49y + 240, this);
				if (skil49Count == 1)
					skil491.start();
				skil49Count++;
			}

			else if (skil50Count != 10 && countTime >= 50 && skilNumber == 50) {
				g.drawImage(skil50, 0, 0, 600, 620, skil50x, skil50y, skil50x + 640, skil50y + 480, this);
				if (skil50Count == 1)
					skil501.start();
				skil50Count++;
			}

			else if (skil51Count != 40 && countTime >= 50 && skilNumber == 51) {
				g.drawImage(skil51, 0, 150, 300, 450, skil51x, skil51y, skil51x + 240, skil51y + 240, this);
				if (skil51Count == 1)
					skil511.start();
				else if (skil51Count == 20)
					skil512.start();
				skil51Count++;
			}

			else if (skil52Count != 40 && countTime >= 50 && skilNumber == 52) {
				g.drawImage(skil52, 0, 0, 600, 620, skil52x, skil52y, skil52x + 640, skil52y + 480, this);
				if (skil52Count == 1)
					skil521.start();
				else if (skil52Count == 39)
					skil521.stop();
				skil52Count++;
			}

			else if (skil53Count != 11 && countTime >= 50 && skilNumber == 53) {
				g.drawImage(skil53, -50, 50, 390, 490, skil53x, skil53y, skil53x + 400, skil53y + 400, this);
				if (skil53Count == 1)
					skil531.start();
				skil53Count++;
			}

			else if (skil54Count != 16 && countTime >= 50 && skilNumber == 54) {
				g.drawImage(skil54, -150, 50, 430, 490, skil54x, skil54y, skil54x + 480, skil54y + 480, this);
				if (skil54Count == 1)
					skil541.start();
				skil54Count++;
			}

			else if (skil55Count != 22 && countTime >= 50 && skilNumber == 55) {
				g.drawImage(skil55, -150, 50, 430, 490, skil55x, skil55y, skil55x + 312, skil55y + 312, this);
				if (skil55Count == 1)
					skil551.start();
				skil55Count++;
			}

			else if (skil56Count != 23 && countTime >= 50 && skilNumber == 56) {
				g.drawImage(skil56, -150, -100, 430, 500, skil56x, skil56y, skil56x + 188, skil56y + 300, this);
				if (skil56Count == 1)
					skil561.start();
				else if (skil56Count == 22)
					skil561.stop();
				skil56Count++;
			}

			else if (skil57Count != 22 && countTime >= 50 && skilNumber == 57) {
				g.drawImage(skil57, -50, 550, 330, 0, skil57x, skil57y, skil57x + 360, skil57y + 360, this);
				if (skil57Count == 1)
					skil571.start();
				skil57Count++;
			}

			else if (skil58Count != 22 && countTime >= 50 && skilNumber == 58) {
				g.drawImage(skil58, -30, 550, 330, 50, skil58x, skil58y, skil58x + 480, skil58y + 480, this);
				if (skil58Count == 1)
					skil581.start();
				skil58Count++;
			}

			else if (skil59Count != 22 && countTime >= 50 && skilNumber == 59) {
				g.drawImage(skil59, -30, 450, 330, 50, skil59x, skil59y, skil59x + 480, skil59y + 480, this);
				if (skil59Count == 1)
					skil591.start();
				skil59Count++;
			}

			else if (skil60Count != 20 && countTime >= 50 && skilNumber == 60) {
				g.drawImage(skil60, -30, 100, 330, 450, skil60x, skil60y, skil60x + 240, skil60y + 480, this);
				if (skil60Count == 1)
					skil601.start();
				skil60Count++;
			}

			else if (skil61Count != 10 && countTime >= 50 && skilNumber == 61) {
				g.drawImage(skil61, 0, 100, 300, 400, skil61x, skil61y, skil61x + 192, skil61y + 192, this);
				if (skil61Count == 1)
					skil611.start();
				skil61Count++;
			}

			else if (skil62Count != 16 && countTime >= 50 && skilNumber == 62) {
				g.drawImage(skil62, 0, 100, 300, 400, skil62x, skil62y, skil62x + 192, skil62y + 192, this);
				if (skil62Count == 1)
					skil621.start();
				skil62Count++;
			}

			else if (skil63Count != 20 && countTime >= 50 && skilNumber == 63) {
				g.drawImage(skil63, 0, 100, 300, 400, skil63x, skil63y, skil63x + 192, skil63y + 192, this);
				if (skil63Count == 1)
					skil631.start();
				skil63Count++;
			}

			else if (skil64Count != 20 && countTime >= 50 && skilNumber == 64) {
				g.drawImage(skil64, 0, 100, 300, 400, skil64x, skil64y, skil64x + 192, skil64y + 192, this);
				if (skil64Count == 1)
					skil641.start();
				skil64Count++;
			}

			else if (skil65Count != 16 && countTime >= 50 && skilNumber == 65) {
				g.drawImage(skil65, 0, 100, 300, 400, skil65x, skil65y, skil65x + 80, skil65y + 128, this);
				if (skil65Count == 1)
					skil651.start();
				skil65Count++;
			}

			else if (skil66Count != 16 && countTime >= 50 && skilNumber == 66) {
				g.drawImage(skil66, 0, 100, 300, 400, skil66x, skil66y, skil66x + 96, skil66y + 96, this);
				if (skil66Count == 1)
					skil661.start();
				else if (skil66Count == 15)
					skil661.stop();
				skil66Count++;
			}

			else if (skil67Count != 16 && countTime >= 50 && skilNumber == 67) {
				g.drawImage(skil67, 0, 100, 300, 400, skil67x, skil67y, skil67x + 128, skil67y + 128, this);
				if (skil67Count == 1)
					skil671.start();
				skil67Count++;
			}

			else if (skil68Count != 16 && countTime >= 50 && skilNumber == 68) {
				g.drawImage(skil68, 0, 100, 300, 400, skil68x, skil68y, skil68x + 128, skil68y + 128, this);
				if (skil68Count == 1)
					skil681.start();
				skil68Count++;
			}

			else if (skil69Count != 14 && countTime >= 50 && skilNumber == 69) {
				g.drawImage(skil69, 0, 100, 300, 400, skil69x, skil69y, skil69x + 96, skil69y + 96, this);
				if (skil69Count == 1)
					skil691.start();
				skil69Count++;
			}

			else if (skil70Count != 10 && countTime >= 50 && skilNumber == 70) {
				g.drawImage(skil70, 0, 100, 300, 400, skil70x, skil70y, skil70x + 192, skil70y + 192, this);
				if (skil70Count == 1)
					skil701.start();
				skil70Count++;
			}

			else if (skil71Count != 20 && countTime >= 50 && skilNumber == 71) {
				g.drawImage(skil71, 0, 100, 300, 400, skil71x, skil71y, skil71x + 96, skil71y + 128, this);
				if (skil71Count == 1)
					skil711.start();
				else if (skil71Count == 18)
					skil711.stop();
				skil71Count++;
			}

			else if (skil72Count != 16 && countTime >= 50 && skilNumber == 72) {
				g.drawImage(skil72, 0, 100, 300, 400, skil72x, skil72y, skil72x + 192, skil72y + 192, this);
				if (skil72Count == 1)
					skil721.start();
				skil72Count++;
			}

			else if (skil73Count != 40 && countTime >= 50 && skilNumber == 73) {
				g.drawImage(skil73, 0, 0, 600, 620, skil73x, skil73y, skil73x + 192, skil73y + 192, this);
				if (skil73Count == 1)
					skil731.start();
				skil73Count++;
			}

			else if (skil74Count != 10 && countTime >= 50 && skilNumber == 74) {
				g.drawImage(skil74, 0, 100, 300, 400, skil74x, skil74y, skil74x + 192, skil74y + 192, this);
				if (skil74Count == 1)
					skil741.start();
				skil74Count++;
			}

			else if (skil75Count != 10 && countTime >= 50 && skilNumber == 75) {
				g.drawImage(skil75, 0, 100, 300, 400, skil75x, skil75y, skil75x + 192, skil75y + 192, this);
				if (skil75Count == 1)
					skil751.start();
				skil75Count++;
			}

			else if (skil76Count != 24 && countTime >= 50 && skilNumber == 76) {
				g.drawImage(skil76, 0, -100, 600, 600, skil76x, skil76y, skil76x + 640, skil76y + 480, this);
				if (skil76Count == 1)
					skil761.start();
				else if (skil76Count == 8)
					skil762.start();
				skil76Count++;
			}

		}

		if (DefenseTurn) {

			if (RightManMiss) {
				Damage = 0;
				AddTime2 = -10;

				g.drawImage(RightMan, 350, 100, 200, 300, this);
				g.drawImage(LeftMan, 50, 100, 200, 300, this);

				g.setColor(Color.WHITE);
				g.fillRect(80, 22, 200, 20);
				g.setColor(Color.GREEN);
				g.fillRect(80, 22, 200 * ((LeftManNowHP - Damage) * 100 / LeftManHP) / 100, 20);
				g.setColor(Color.BLACK);//スコアの色
				g.setFont(new Font("Monospaced", Font.PLAIN, 20));
				g.drawString(String.valueOf(LeftManNowHP - Damage) + "/" + String.valueOf(LeftManHP), 30 + 53, 40);

				g.setColor(Color.WHITE);//スコアの色
				g.setFont(new Font("Monospaced", Font.PLAIN, 20));
				g.drawImage(LeftManFace, 40, 430, 100, 100, this);
				g.drawString(TalkingMan[LeftManNum], 60, 550);

				g.setColor(Color.WHITE);//スコアの色
				g.setFont(new Font("Monospaced", Font.PLAIN, 30));
				g.drawString(LeftManComment[4], 160, 460);

				if (damageShowTime < 30 && damageShowTime != 0) {
					g.setColor(Color.BLUE);//スコアの色
					g.setFont(new Font("Serif", Font.PLAIN, 50));
					g.drawString("miss", 170, 130);
				}

				Miss.start();

			}

			else if (!LeftManDeathFlag) {

				g.drawImage(RightMan, 350, 100, 200, 300, this);

				if (Damage > damageV && LeftManNowHP - damageV >= 0) {

					if(DrainFlag){
						skil771.start();
						g.drawImage(skil77, 0 + 300, 100, 320 + 300, 420, skil77x, skil77y, skil77x + 240, skil77y + 240,this);
						skil77Count++;
					}
					else if(CriticalFlag){
						hpDownS.start();
					}
					else{
						hpDown.start();
					}

					//g.drawImage(LeftMan, 50 + EnemyPicMove, 100, 200, 300, this);
					if (countTime % 3 == 0) {
						g.drawImage(LeftMan, 50, 100, 200, 300, this);
					}


				}
				else {
					g.drawImage(LeftMan, 50, 100, 200, 300, this);

				}



				for(int i=0;i<7;i++){
					if(conditionFlag[i]){
						for(int j=0;j<2;j++){
							if(LeftManBuff[j]){
								lateTime = 20;
								break;
							}
						}
					}
				}

				//System.out.println("lateTime"+lateTime);

				for(int i=0;i<2;i++){
					if(RightManBuff[i]&&RightManBuffCount[i]!=10){
						switch(i){
						case 0:
							g.drawImage(buff, 0 + 300, 100, 320 + 300, 420, RightManBuffx[i], RightManBuffy[i], RightManBuffx[i] + 240, RightManBuffy[i] + 240,this);
							RightManBuffCount[i]++;
							break;
						case 1:
							g.drawImage(deBuff, 0 + 300, 100, 320 + 300, 420, RightManBuffx[i], RightManBuffy[i], RightManBuffx[i] + 240, RightManBuffy[i] + 240,this);
							RightManBuffCount[i]++;
							break;
						}
					}
					if(LeftManBuff[i]&&LeftManBuffCount[i]!=10&&countTime>100+lateTime){
						switch(i){
						case 0:
							g.drawImage(buff, 0 , 100, 320 , 420, LeftManBuffx[i], LeftManBuffy[i], LeftManBuffx[i] + 240, LeftManBuffy[i] + 240,this);
							LeftManBuffCount[i]++;
							break;
						case 1:
							g.drawImage(deBuff, 0 , 100, 320 , 420, LeftManBuffx[i], LeftManBuffy[i], LeftManBuffx[i] + 240, LeftManBuffy[i] + 240,this);
							LeftManBuffCount[i]++;
							break;
						}
					}


				}

				/////////////////////////////
				if(conditionFlag[0]){
					//System.out.println("conditionFlag");
					g.drawImage(condition[0], -200, -200, 500, 500, conditionx[0], conditiony[0], conditionx[0] + 240, conditiony[0] + 240, this);
					conditionCount[0]++;
				}
				if(conditionFlag[1]){
					g.drawImage(condition[1], -200, 0, 500, 500, conditionx[1], conditiony[1], conditionx[1] + 240, conditiony[1] + 240, this);
					conditionCount[1]++;
				}
				if(conditionFlag[2]){
					g.drawImage(condition[2], -200, 0, 500, 500, conditionx[2], conditiony[2], conditionx[2] + 240, conditiony[2] + 240, this);
					conditionCount[2]++;
				}
				if(conditionFlag[3]&&conditionCount[3]!=7){
					g.drawImage(condition[3], -50, 0, 450, 400, conditionx[3], conditiony[3], conditionx[3] + 192, conditiony[3] + 192, this);
					conditionCount[3]++;
				}
				if(conditionFlag[4]){
					g.drawImage(condition[4], 0, 0, 300, 300, conditionx[4], conditiony[4], conditionx[4] + 128, conditiony[4] + 128, this);
					conditionCount[4]++;
				}
				if(conditionFlag[5]){
					g.drawImage(condition[5], -200, 0, 500, 500, conditionx[5], conditiony[5], conditionx[5] + 240, conditiony[5] + 240, this);
					conditionCount[5]++;
				}
				if(conditionFlag[6]){
					g.drawImage(condition[6], -100, 0, 400, 500, conditionx[6], conditiony[6], conditionx[6] + 200, conditiony[6] + 200, this);
					conditionCount[6]++;
				}
				////////////////////////////

				g.setColor(Color.WHITE);
				g.fillRect(80, 22, 200, 20);
				g.setColor(Color.GREEN);
				if (LeftManNowHP - damageV < 0) {
					g.fillRect(80, 22, 200 * 0, 20);
				}
				else if (Damage <= damageV) {
					g.fillRect(80, 22, 200 * ((LeftManNowHP - Damage) * 100 / LeftManHP) / 100, 20);
				}
				else {
					g.fillRect(80, 22, 200 * ((LeftManNowHP - damageV) * 100 / LeftManHP) / 100, 20);
				}

				g.setColor(Color.BLACK);//スコアの色
				g.setFont(new Font("Monospaced", Font.PLAIN, 20));
				if (LeftManNowHP - damageV < 0) {
					g.drawString(String.valueOf(0) + "/" + String.valueOf(LeftManHP), 30 + 53, 40);
				}
				else if (Damage <= damageV) {
					g.drawString(String.valueOf(LeftManNowHP - Damage) + "/" + String.valueOf(LeftManHP), 30 + 53, 40);
				}
				else {
					g.drawString(String.valueOf(LeftManNowHP - damageV) + "/" + String.valueOf(LeftManHP), 30 + 53, 40);
				}

				//////////////
				if (DrainFlag) {
					g.setColor(Color.WHITE);
					g.fillRect(380, 22, 200, 20);
					g.setColor(Color.GREEN);
					if (RightManNowHP + drainV > RightManHP) {
						g.fillRect(380, 22, 200, 20);
					}
					else if (TrueDrain <= drainV) {
						g.fillRect(380, 22, 200 * ((RightManNowHP + TrueDrain) * 100 / RightManHP) / 100, 20);
					}
					else {
						g.fillRect(380, 22, 200 * ((RightManNowHP + drainV) * 100 / RightManHP) / 100, 20);
					}

					g.setColor(Color.BLACK);//スコアの色
					g.setFont(new Font("Monospaced", Font.PLAIN, 20));
					if (RightManNowHP + drainV > RightManHP) {
						g.drawString(String.valueOf(RightManHP) + "/" + String.valueOf(RightManHP), 330 + 53, 40);
					}
					else if (TrueDrain <= drainV) {
						g.drawString(String.valueOf(RightManNowHP + TrueDrain) + "/" + String.valueOf(RightManHP),
								330 + 53, 40);
						System.out.println("TrueDrain"+TrueDrain);
					}
					else {
						g.drawString(String.valueOf(RightManNowHP + drainV) + "/" + String.valueOf(RightManHP),
								330 + 53, 40);
					}
				}
				//////////////

				g.setColor(Color.WHITE);//スコアの色
				g.setFont(new Font("Monospaced", Font.PLAIN, 30));
				g.drawImage(LeftManFace, 40, 430, 100, 100, this);
				g.drawString(LeftManComment[2], 160, 460);
				g.setFont(new Font("Monospaced", Font.PLAIN, 20));
				g.drawString(TalkingMan[LeftManNum], 60, 550);



				if (damageShowTime < 30 && damageShowTime != 0) {

					if(CriticalFlag){
						g.setColor(Color.RED);//スコアの色
						g.setFont(new Font("Serif", Font.BOLD, 40));
						g.drawString(String.valueOf("CRITICAL!!"), 0, 130);
						g.setFont(new Font("Serif", Font.BOLD, 60));
						g.drawString(String.valueOf(-Damage), 170 + 20, 160);
					}
					else{
						g.setColor(Color.RED);//スコアの色
						g.setFont(new Font("Serif", Font.BOLD, 50));
						g.drawString(String.valueOf(-Damage), 170 + 20, 130);
					}
					if (DrainFlag) {
						g.setColor(Color.GREEN);//スコアの色
						g.setFont(new Font("Serif", Font.BOLD, 50));
						g.drawString(String.valueOf("+"+Drain), 470 + 20, 130);
					}

				}
				else if (damageShowTime == 30 && LeftManNowHP - Damage <= 0) {

					LeftManDeathFlag = true;

				}
			}

			else if (LeftManDeathFlag && damageShowTime >= 30) {

				g.drawImage(RightMan, 350, 100, 200, 300, this);

				g.setColor(Color.WHITE);
				g.fillRect(80, 22, 200, 20);
				g.setColor(Color.GREEN);
				g.fillRect(80, 22, 0, 20);
				g.setColor(Color.BLACK);//スコアの色
				g.setFont(new Font("Monospaced", Font.PLAIN, 20));
				g.drawString(String.valueOf(0) + "/" + String.valueOf(LeftManHP), 30 + 53, 40);

				g.setColor(Color.WHITE);//スコアの色
				g.setFont(new Font("Monospaced", Font.PLAIN, 20));
				g.drawImage(LeftManFace, 40, 430, 100, 100, this);
				g.drawString(TalkingMan[LeftManNum], 60, 550);
				g.drawImage(batu, 40, 430, 100, 100, this);

				g.setColor(Color.WHITE);//スコアの色
				g.setFont(new Font("Monospaced", Font.PLAIN, 30));
				//g.drawString(TalkingMan[LeftManNum], 160, 460);
				g.drawString(LeftManComment[5], 160, 460);

				Death.start();
			}

		}

		if (CounterTurn) {
			g.drawImage(RightMan, 350, 100, 200, 300, this);
			g.drawImage(LeftMan, 50, 100, 200, 300, this);

			g.drawImage(ManAttack, 285, 40, 50, 25, this);

			g.setColor(Color.WHITE);
			g.fillRect(80, 22, 200, 20);
			g.setColor(Color.GREEN);
			g.fillRect(80, 22, 200 * ((LeftManNowHP - Damage) * 100 / LeftManHP) / 100, 20);
			g.setColor(Color.BLACK);//スコアの色
			g.setFont(new Font("Monospaced", Font.PLAIN, 20));
			g.drawString(String.valueOf(LeftManNowHP - Damage) + "/" + String.valueOf(LeftManHP), 30 + 53, 40);
			////////////////
			g.setColor(Color.WHITE);
			g.fillRect(380, 22, 200, 20);
			g.setColor(Color.GREEN);
			g.fillRect(380, 22, 200 * ((RightManNowHP + TrueDrain) * 100 / RightManHP) / 100, 20);

			g.setColor(Color.BLACK);//スコアの色
			g.setFont(new Font("Monospaced", Font.PLAIN, 20));
			g.drawString(String.valueOf(RightManNowHP + TrueDrain) + "/" + String.valueOf(RightManHP),
					330 + 53, 40);
			////////////////

			g.setColor(Color.WHITE);//スコアの色
			g.setFont(new Font("Monospaced", Font.PLAIN, 30));
			g.drawImage(LeftManFace, 40, 430, 100, 100, this);
			g.drawString(LeftManComment[3], 160, 460);
			g.setFont(new Font("Monospaced", Font.PLAIN, 20));
			g.drawString(TalkingMan[LeftManNum], 60, 550);
			if (countTime > 170 + AddTime2 + AddTime1) {
				g.drawImage(skil999, 0 + 300, 100, 320 + 300, 420, skil999x, skil999y, skil999x + 640, skil999y + 480,
						this);
				if (skil999Count == 1) {
					skil9991.start();
				}

				skil999Count++;
			}

		}

		if (CounterDefenseTurn) {

			if (LeftManMiss) {
				CounterDamage = 0;

				g.drawImage(RightMan, 350, 100, 200, 300, this);
				g.drawImage(LeftMan, 50, 100, 200, 300, this);

				g.setColor(Color.WHITE);
				g.fillRect(80, 22, 200, 20);
				g.setColor(Color.GREEN);
				g.fillRect(80, 22, 200 * ((LeftManNowHP - Damage) * 100 / LeftManHP) / 100, 20);

				g.setColor(Color.BLACK);//スコアの色
				g.setFont(new Font("Monospaced", Font.PLAIN, 20));
				g.drawString(String.valueOf(LeftManNowHP - Damage) + "/" + String.valueOf(LeftManHP), 30 + 53, 40);

				g.setColor(Color.WHITE);
				g.fillRect(380, 22, 200, 20);
				g.setColor(Color.GREEN);
				g.fillRect(380, 22, 200 * ((RightManNowHP + TrueDrain - CounterDamage ) * 100 / RightManHP) / 100, 20);

				g.setColor(Color.BLACK);//スコアの色
				g.setFont(new Font("Monospaced", Font.PLAIN, 20));
				g.drawString(String.valueOf(RightManNowHP + TrueDrain - CounterDamage ) + "/" + String.valueOf(RightManHP),
						330 + 53, 40);

				g.setColor(Color.WHITE);//スコアの色
				g.setFont(new Font("Monospaced", Font.PLAIN, 30));
				g.drawString(RightManComment[4], 160, 460);

				g.setColor(Color.WHITE);//スコアの色
				g.setFont(new Font("Monospaced", Font.PLAIN, 20));
				g.drawImage(RightManFace, 40, 430, 100, 100, this);
				g.drawString(TalkingMan[RightManNum], 60, 550);

				if (damageShowTime < 30 && damageShowTime != 0) {
					g.setColor(Color.BLUE);//スコアの色
					g.setFont(new Font("Serif", Font.PLAIN, 50));
					g.drawString("miss", 470, 130);
				}

				Miss.start();

			}

			else if (!RightManDeathFlag) {
				g.drawImage(LeftMan, 50, 100, 200, 300, this);

				if (CounterDamage > damageV && RightManNowHP - damageV >= 0) {

					if(CounterCriticalFlag){
						hpDownS.start();
					}
					else{
						hpDown.start();
					}
					if (countTime % 3 == 0) {
						g.drawImage(RightMan, 350, 100, 200, 300, this);
					}

				}
				else {
					g.drawImage(RightMan, 350, 100, 200, 300, this);

				}

				g.setColor(Color.WHITE);
				g.fillRect(80, 22, 200, 20);
				g.setColor(Color.GREEN);
				g.fillRect(80, 22, 200 * ((LeftManNowHP - Damage) * 100 / LeftManHP) / 100, 20);

				g.setColor(Color.BLACK);//スコアの色
				g.setFont(new Font("Monospaced", Font.PLAIN, 20));
				g.drawString(String.valueOf(LeftManNowHP - Damage) + "/" + String.valueOf(LeftManHP), 30 + 53, 40);

				g.setColor(Color.WHITE);
				g.fillRect(380, 22, 200, 20);
				g.setColor(Color.GREEN);
				if (RightManNowHP + TrueDrain - damageV < 0) {
					g.fillRect(380, 22, 0, 20);
				}
				else if (CounterDamage <= damageV) {
					g.fillRect(380, 22, 200 * ((RightManNowHP + TrueDrain - CounterDamage ) * 100 / RightManHP) / 100, 20);
				}
				else {
					g.fillRect(380, 22, 200 * ((RightManNowHP + TrueDrain - damageV ) * 100 / RightManHP) / 100, 20);
				}
				//System.out.println("damageV"+damageV);
				g.setColor(Color.BLACK);//スコアの色
				g.setFont(new Font("Monospaced", Font.PLAIN, 20));
				if (RightManNowHP + TrueDrain - damageV < 0) {
					g.drawString(String.valueOf(0) + "/" + String.valueOf(RightManHP), 330 + 53, 40);
				}
				else if (CounterDamage <= damageV) {
					g.drawString(String.valueOf(RightManNowHP + TrueDrain - CounterDamage ) + "/" + String.valueOf(RightManHP),330 + 53, 40);
				}
				else {
					g.drawString(String.valueOf(RightManNowHP + TrueDrain - damageV) + "/" + String.valueOf(RightManHP), 330 + 53,40);
				}

				g.setColor(Color.WHITE);//スコアの色
				g.setFont(new Font("Monospaced", Font.PLAIN, 30));
				g.drawImage(RightManFace, 40, 430, 100, 100, this);
				g.drawString(RightManComment[2], 160, 460);
				g.setFont(new Font("Monospaced", Font.PLAIN, 20));
				g.drawString(TalkingMan[RightManNum], 60, 550);

				if (damageShowTime < 30 && damageShowTime != 0) {
					if(CounterCriticalFlag){
						g.setColor(Color.RED);//スコアの色
						g.setFont(new Font("Serif", Font.BOLD, 40));
						g.drawString(String.valueOf("CRITICAL!!"), 300, 130);
						g.setFont(new Font("Serif", Font.BOLD, 60));
						g.drawString(String.valueOf(-CounterDamage), 470 + 20, 160);
					}
					else{
						g.setColor(Color.RED);//スコアの色
						g.setFont(new Font("Serif", Font.BOLD, 50));
						g.drawString(String.valueOf(-CounterDamage), 470 + 20, 130);
					}
				}

				else if (damageShowTime == 30 && RightManNowHP - CounterDamage <= 0) {

					RightManDeathFlag = true;

				}
			}

			else if (RightManDeathFlag && damageShowTime >= 30) {

				g.drawImage(LeftMan, 50, 100, 200, 300, this);

				g.setColor(Color.WHITE);
				g.fillRect(80, 22, 200, 20);
				g.setColor(Color.GREEN);
				g.fillRect(80, 22, 200 * ((LeftManNowHP - Damage) * 100 / LeftManHP) / 100, 20);

				g.setColor(Color.BLACK);//スコアの色
				g.setFont(new Font("Monospaced", Font.PLAIN, 20));
				g.drawString(String.valueOf(LeftManNowHP - Damage) + "/" + String.valueOf(LeftManHP), 30 + 53, 40);

				g.setColor(Color.WHITE);
				g.fillRect(380, 22, 200, 20);
				g.setColor(Color.GREEN);
				g.fillRect(380, 22, 0, 20);
				g.setColor(Color.BLACK);//スコアの色
				g.setFont(new Font("Monospaced", Font.PLAIN, 20));
				g.drawString(String.valueOf(0) + "/" + String.valueOf(RightManHP), 330 + 53, 40);

				g.setColor(Color.WHITE);//スコアの色
				g.setFont(new Font("Monospaced", Font.PLAIN, 20));
				g.drawImage(RightManFace, 40, 430, 100, 100, this);
				g.drawString(TalkingMan[RightManNum], 60, 550);
				g.drawImage(batu, 40, 430, 100, 100, this);

				g.setColor(Color.WHITE);//スコアの色
				g.setFont(new Font("Monospaced", Font.PLAIN, 30));
				//g.drawString(TalkingMan[RightManNum], 160, 460);
				g.drawString(RightManComment[5], 160, 460);

				Death.start();
			}
		}

		if (countTime > 300 + AddTime3 + AddTime2 + AddTime1 || countTime > 150 + AddTime2 + AddTime1
				&& LeftManDeathFlag || !CounterFlag && countTime > 150 + AddTime2 + AddTime1) {//＃

			System.out.println("end");//////////////////////※画面転換＃
			BackMusic.stop();//＃
			BackMusic.setMicrosecondPosition(0);//＃
			this.BackMusic = null;
			hpDown.stop();//＃
			hpDown.setMicrosecondPosition(0);//＃
			mp.state = 5;
			/////////////////////////////////////////////
			mp.bp.view.musicRestart();
			mp.bp.victoryCheck();
			mp.bp.defeatCheck();
			mp.bp.endCombat();
			//音楽始める
			//if(!mp.bp.view.victory && !mp.bp.view.defeat)mp.bp.view.musicRestart();
			//System.out.println("checked ...? 1");
			////////////////////////////////////////////////
		}

		if (countTime > 200 + AddTime3 + AddTime2 + AddTime1 && CounterFlag && !LeftManDeathFlag) {
			CounterTurn = false;
			CounterDefenseTurn = true;
		}

		else if (countTime > 150 + AddTime2 + AddTime1 && CounterFlag && !LeftManDeathFlag) {
			DefenseTurn = false;
			CounterTurn = true;
			damageV = 0;
			damageShowTime = 0;
			hpDown.stop();
			hpDown.setMicrosecondPosition(0);
			Miss.stop();
			Miss.setMicrosecondPosition(0);
		}

		else if (countTime > 100 + AddTime1) {
			AttackTurn = false;
			DefenseTurn = true;
		}

	}

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == button) {
			System.out.println("end");//////////////////////※画面転換
			BackMusic.stop();//＃
			BackMusic.setMicrosecondPosition(0);//＃
			this.BackMusic = null;
			hpDown.stop();//＃
			hpDown.setMicrosecondPosition(0);//＃
			mp.state = 5;
			/////////////////////////////////////////////
			mp.bp.view.musicRestart();
			mp.bp.victoryCheck();
			mp.bp.defeatCheck();
			mp.bp.endCombat();
			//音楽始める
			//if(!mp.bp.view.victory &&  !mp.bp.view.defeat)mp.bp.view.musicRestart();
			//System.out.println("checked ...? 2");
			////////////////////////////////////////////////
		}
		repaint();
	}
}
