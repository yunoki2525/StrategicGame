package main;

abstract public class Item {
	String name;
	int id;
	boolean empty = true;
	int num ;//所持数
	int money;
	int heal; //HP回復量
	int recovery;//SP回復量
	boolean cure;//状態異常回復の有無
	int[][] buff = new int[8][2];//バフの種類とターン数

	static final int kind = 3;//アイテムの種類
	
	Item(String name , int id ,int money , int heal ,int recovery ,boolean cure ){
		this.name = name;
		this.id = id;
		this.money = money;
		this.heal = heal;
		this.recovery = recovery;
		this.cure = cure;
	}
	
	
}

class Posion extends Item{
	Posion(){
		super("ポーション", 0 ,300,200, 0 ,false);
	}
	
	public String toString(){
		return "HPを200回復する";
		
	}
}

class  Medicine extends Item{
	Medicine(){
		super("霊薬", 1 ,500,0,  100 ,false);
	}
	public String toString(){
		return "SPを100回復する";
		
	}
}
class Curia extends Item{
	Curia(){
		super("キュリアの薬",2,500,0,0,true);
	}
	public String toString(){
		return "状態異常を回復する";
		
	}
}
