package main;


public class WeaponList {
		Weapon weapon;
		WeaponList( int id){
			if(id==0){
				weapon = new NoWeapon();
			}
			else if(id==24){
				weapon = new buki24();
			}
			else if(id==1){
				weapon = new buki1();
			}
			else if(id==2){
				weapon = new buki2();
			}
			else if(id==3){
				weapon = new buki3();
			}
			else if(id==4){
				weapon = new buki4();
			}
			else if(id==5){
				weapon = new buki5();
			}
			else if(id==6){
				weapon = new buki6();
			}
			else if(id==7){
				weapon = new buki7();
			}
			else if(id==8){
				weapon = new buki8();
			}
			else if(id==9){
				weapon = new buki9();
			}
			else if(id==10){
				weapon = new buki10();
			}
			else if(id==11){
				weapon = new buki11();
			}
			else if(id==12){
				weapon = new buki12();
			}
			else if(id==13){
				weapon = new buki13();
			}
			else if(id==14){
				weapon = new buki14();
			}
			else if(id==15){
				weapon = new buki15();
			}
			else if(id==16){
				weapon = new buki16();
			}
			else if(id==17){
				weapon = new buki17();
			}
			else if(id==18){
				weapon = new buki18();
			}
			else if(id==19){
				weapon = new buki19();
			}
			else if(id==20){
				weapon = new buki20();
			}
			else if(id==21){
				weapon = new buki21();
			}
			else if(id==22){
				weapon = new buki22();
			}
			else if(id==23){
				weapon = new buki23();
			}

			/*else if(id==1){
				skill_length = 2;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new Ouka();
			}else if(id==2){
				skill_length = 4;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new Ouka();
				skills[2] = new Kaiten();
				skills[3] = new Takamakuri();
			}else if(id==3){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new Ouka();
				skills[2] = new Syusui();
			}else if(id==10){
				skill_length = 4;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new Ouka();
				skills[2] = new Syusui();
				skills[3] = new Kaiten();
			}else if(id==11){
				skill_length = 3;
				skills = new Skill[skill_length];
				skills[0] = new Defo();
				skills[1] = new Ouka();
				skills[2] = new Takamakuri();
			}*/
		}
}
