package main;

public class MagicList {
	Magic[] magics;
	MagicList( int id){
		int magic_length = 0;
//		if(id==0){
//			magic_length = 1;
//			magics = new Magic[magic_length];
//			magics[0] = new DefoMagic();
//		}else if(id==1){
//			magic_length = 1;
//			magics = new Magic[magic_length];
//			magics[0] = new DefoMagic();
//		}else if(id==2){
//			magic_length = 1;
//			magics = new Magic[magic_length];
//			magics[0] = new DefoMagic();
//		}else if(id==3){
//			magic_length = 1;
//			magics = new Magic[magic_length];
//			magics[0] = new DefoMagic();
//		}else if(id==10){
//			magic_length = 1;
//			magics = new Magic[magic_length];
//			magics[0] = new DefoMagic();
//		}else if(id==11){
//			magic_length = 1;
//			magics = new Magic[magic_length];
//			magics[0] = new DefoMagic();
//		}


		if(id==0){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
			//magics[1] = new Ouka();
		}
		else if (id == 1) {
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new hokkaidou1();
//			skills[2] = new hokkaidou2();
//			skills[3] = new hokkaidou3();
		}
		else if(id==2){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new aomori1();
//			magics[2] = new aomori2();
		}
		else if(id==3){
			magic_length = 3;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
			magics[1] = new iwate1();
			magics[2] = new iwate2();
		}
		else if(id==4){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new miyagi1();
//			magics[2] = new miyagi2();
		}
		else if(id==5){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new akita1();
//			magics[2] = new akita2();
		}
		else if(id==6){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new yamagata1();
//			magics[2] = new yamagata2();
		}
		else if(id==7){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new hukusima1();
//			magics[2] = new hukusima2();
		}
		else if(id==8){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new ibaragi1();
//			magics[2] = new ibaragi2();
		}
		else if(id==9){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new totigi1();
//			magics[2] = new totigi2();
		}
		else if(id==10){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new gunma1();
//			magics[2] = new gunma2();
		}
		else if(id==11){
			magic_length = 3;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
			magics[1] = new saitama1();
			magics[2] = new saitama2();
		}
		else if(id==12){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new tiba1();
//			magics[2] = new tiba2();
		}
		else if(id==13){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new toukyou1();
//			magics[2] = new toukyou2();
		}
		else if(id==14){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new kanagawa1();
//			magics[2] = new kanagawa2();
		}
		else if(id==15){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new niigata1();
//			magics[2] = new niigata2();
		}
		else if(id==16){
			magic_length = 3;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
			magics[1] = new toyama1();
			magics[2] = new toyama2();
		}
		else if(id==17){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new isikawa1();
//			magics[2] = new isikawa2();
		}
		else if(id==18){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new hukui1();
//			magics[2] = new hukui2();
		}
		else if(id==19){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new yamanasi1();
//			magics[2] = new yamanasi2();
		}
		else if(id==20){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new nagano1();
//			magics[2] = new nagano2();
		}else if(id==21){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new gihu1();
//			magics[2] = new gihu2();
		}else if(id==22){
			magic_length = 3;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
			magics[1] = new sizuoka1();
			magics[2] = new sizuoka2();
		}else if(id==23){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new aiti1();
//			magics[2] = new aiti2();
		}
		else if(id==24){
			magic_length = 3;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
			magics[1] = new mie1();
			magics[2] = new mie2();
		}
		else if(id==25){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new siga1();
//			magics[2] = new siga2();
		}
		else if(id==26){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new kyouto1();
//			magics[2] = new kyouto2();
		}
		else if(id==27){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new oosaka1();
//			magics[2] = new oosaka2();
		}
		else if(id==28){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new hyougo1();
//			magics[2] = new hyougo2();
		}
		else if(id==29){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new nara1();
//			magics[2] = new nara2();
		}
		else if(id==30){
			magic_length = 3;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
			magics[1] = new wakayama1();
			magics[2] = new wakayama2();
		}
		else if(id==31){
			magic_length = 3;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
			magics[1] = new tottori1();
			magics[2] = new tottori2();
		}
		else if(id==32){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new simane1();
//			magics[2] = new simane2();
		}
		else if(id==33){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new okayama1();
//			magics[2] = new okayama2();
		}
		else if(id==34){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new hirosima1();
//			magics[2] = new hirosima2();
		}
		else if(id==35){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new yamaguti1();
//			magics[2] = new yamaguti2();
		}
		else if(id==36){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new tokusima1();
//			magics[2] = new tokusima2();
		}
		else if(id==37){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new kagawa1();
//			magics[2] = new kagawa2();
		}
		else if(id==38){
			magic_length = 3;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
			magics[1] = new ehime1();
			magics[2] = new ehime2();
		}
		else if(id==39){
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new kouti1();
//			magics[2] = new kouti2();
		}
		else if(id==40){   //福岡
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
			//magics[1] = new Fukuoka1();
			//magics[2] = new Fukuoka2();
		}else if(id==41){   //佐賀
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
			//magics[1] = new Saga1();
			//magics[2] = new Saga2();
		}else if(id==42){   //長崎
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new Nagasaki1();
//			magics[2] = new Nagasaki2();
		}else if(id==43){   //熊本
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new Kumamoto1();
//			magics[2] = new Kumamoto2();
		}else if(id==44){   //大分
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new Oita1();
//			magics[2] = new Oita2();
		}else if(id==45){   //宮崎
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			magics[1] = new Miyazaki1();
//			magics[2] = new Miyazaki2();
		}else if(id==46){   //鹿児島
			magic_length = 3;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
			magics[1] = new Kagoshima1();
			magics[2] = new Kagoshima2();
		}
		else if (id == 47) {
			magic_length = 1;
			magics = new Magic[magic_length];
			magics[0] = new DefoMagic();
//			skills[1] = new okinawa1();
//			skills[2] = new okinawa2();
//			skills[3] = new okinawa3();
		}

	}
}
