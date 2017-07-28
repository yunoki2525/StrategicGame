package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;



class ItemControl {
	Item[][] items = new Item[2][Item.kind];//[] 敵味方 0 味方 1 敵//[] アイテムの種類
	BattlePanel panel;
	Piece ptr;

	ItemControl(BattlePanel panel){
		this.panel = panel;
		items[0][0] = new Posion();
		items[0][1] = new Medicine();
		items[0][2] = new Curia();
		items[1][0] = new Posion();
		items[1][1] =	new Medicine();
		items[1][2] = new Curia();
		
		readItem(false);
		//for(int i = 0; i < Item.kind; i++)System.out.println(items[1][i].num);
	}//battle用
	ItemControl(){
		items[0][0] = new Posion();
		items[0][1] = new Medicine();
		items[0][2] = new Curia();
		items[1][0] = new Posion();
		items[1][1] =	new Medicine();	
		items[1][2] = new Curia();
		
		readItem(false);
	}//その他用

	public String getItemName(int id ,boolean myTurn){
		String name ="";
		if(myTurn){
			for(int i = 0; i < Item.kind;i++){
				if(items[0][i].id == id)name = items[0][i].name;
			}
			return name;
		}else{
			for(int i = 0; i < Item.kind;i++){
				if(items[1][i].id == id)name = items[1][i].name;
			}
			return name;
		}
	}

	public void useItem(int id , boolean myTurn ,int receiver_idx, int user_idx){
		if(myTurn){
			for(int i = 0; i < Item.kind;i++){
				if(items[0][i].id == id){
					items[0][i].num--;
					panel.ItemEffect(items[0][i].heal , items[0][i].recovery,items[0][i].cure,myTurn,receiver_idx, user_idx);
				}
				if(items[0][i].num == 0) items[0][i].empty =true;
			}
		}else{
			for(int i = 0; i < Item.kind;i++){
				if(items[1][i].id == id){
					items[1][i].num--;
					panel.ItemEffect(items[0][i].heal , items[0][i].recovery,items[0][i].cure,myTurn,receiver_idx,user_idx);
				}
				if(items[1][i].num == 0) items[1][i].empty =true;
			}
		}
	}
	public int sellItem(int id){
		return 0;
	}
	public boolean checkEmpty(int id , boolean myTurn){
		if(myTurn){
			for(int i = 0; i < Item.kind; i++)
				if(items[0][i].id == id) return items[0][i].empty;
			return false;
		}else{
			for(int i = 0; i < Item.kind; i++)
				if(items[1][i].id == id) return items[1][i].empty;
			return false;
		}

	}
	public void getItem(int id){
		for(int i = 0; i < Item.kind;i++){
			if(items[0][i].id == id){
				if(items[0][i].num == 0) items[0][i].empty =false;
				items[0][i].num++;
				writeItem(false);
			}
		}
	}
	public void buyItem(int[] buy_num){
		System.out.println(" buy_num : "+Arrays.toString(buy_num));
		for(int i =0; i < Item.kind; i++){
			items[0][i].num += buy_num[i];
			System.out.println(items[0][i].num);
		}
		writeItem(false);
	}
	public void buyItem(int[] buy_num, int pvp_num){
		if(pvp_num == MainPanel.player1){
		System.out.println(" buy_num : "+Arrays.toString(buy_num));
		for(int i =0; i < Item.kind; i++){
			items[0][i].num += buy_num[i];
			System.out.println(items[0][i].num);
		}
		writeItem(pvp_num);
		}
		if(pvp_num == MainPanel.player2){
			System.out.println(" buy_num : "+Arrays.toString(buy_num));
			for(int i =0; i < Item.kind; i++){
				items[1][i].num += buy_num[i];
				System.out.println(items[1][i].num);
			}
			writeItem(pvp_num);
			}
	}
	
	
	public int[] havingItemList(boolean myTurn){
		int having_num = 0;
		int list_num = 0;
		int[] item_list;
		if(myTurn){
			for(int i = 0; i < Item.kind; i++)
				if(!items[0][i].empty)having_num++;
			item_list = new int[having_num];
			for(int i = 0; i < Item.kind;i++){
				if(!items[0][i].empty){
					item_list[list_num] = items[0][i].id;
					list_num++;
				}
			}
			return item_list;
		}else{
			for(int i = 0; i < Item.kind; i++)
				if(!items[1][i].empty)having_num++;
			item_list = new int[having_num];
			for(int i = 0; i < Item.kind;i++){
				if(!items[1][i].empty){
					item_list[list_num] = items[1][i].id;
					list_num++;
				}
			}
			
			System.out.println("item_list :"+Arrays.toString(item_list));
			return item_list;
		}
	}
	public void readItem(boolean init){
		//System.out.println("きたよー");
		if(init){
			try {
				BufferedReader br = new BufferedReader(new FileReader("../src/Item/Item1"));
				for(int i = 0; i < Item.kind;i++ ){
					String str = br.readLine();
					String[] tokens1 = str.split(",");
					System.out.println(" tokens1 : "+Arrays.toString(tokens1));
					if(items[0][i].id == Integer.parseInt(tokens1[0])){
						items[0][i].num = Integer.parseInt(tokens1[1]);
						if(items[0][i].num <= 0) items[0][i].empty = true;
						else items[0][i].empty = false;
					}
				}
				br.close();
			} catch (IOException er) {
			}
			
		}else{
			try {
				BufferedReader br = new BufferedReader(new FileReader("../src/Item/Item2"));
				for(int i = 0; i < Item.kind;i++ ){
					String str = br.readLine();
					String[] tokens1 = str.split(",");
					System.out.println(" tokens1 : "+Arrays.toString(tokens1));
					if(items[0][i].id == Integer.parseInt(tokens1[0])) {
						items[0][i].num = Integer.parseInt(tokens1[1]);
						if(items[0][i].num <= 0) items[0][i].empty = true;
						else items[0][i].empty = false;
					}
				}
				br.close();
			} catch (IOException er) {
			}
			for(int i = 0; i < Item.kind;i++){
				items[1][i].num = 3;
				items[1][i].empty = false;
			}
		}
	}
	public void readItem(int num){
		//System.out.println("きたよー");
		if(num == MainPanel.everyone){
			try {
				BufferedReader br = new BufferedReader(new FileReader("../src/Item/Player1Item"));
				for(int i = 0; i < Item.kind;i++ ){
					String str = br.readLine();
					String[] tokens1 = str.split(",");
					System.out.println(" tokens1 : "+Arrays.toString(tokens1));
					if(items[0][i].id == Integer.parseInt(tokens1[0])){
						items[0][i].num = Integer.parseInt(tokens1[1]);
						if(items[0][i].num <= 0) items[0][i].empty = true;
						else items[0][i].empty = false;
					}
				}
				br.close();
			} catch (IOException er) {
			}
			try {
				BufferedReader br = new BufferedReader(new FileReader("../src/Item/Player2Item"));
				for(int i = 0; i < Item.kind;i++ ){
					String str = br.readLine();
					String[] tokens1 = str.split(",");
					System.out.println(" tokens1 : "+Arrays.toString(tokens1));
					if(items[1][i].id == Integer.parseInt(tokens1[0])) {
						items[1][i].num = Integer.parseInt(tokens1[1]);
						if(items[1][i].num <= 0) items[1][i].empty = true;
						else items[1][i].empty = false;
					}
				}
				br.close();
			} catch (IOException er) {
			}
		}
		if(num == MainPanel.player1){
			try {
				BufferedReader br = new BufferedReader(new FileReader("../src/Item/Player1Item"));
				for(int i = 0; i < Item.kind;i++ ){
					String str = br.readLine();
					String[] tokens1 = str.split(",");
					System.out.println(" tokens1 : "+Arrays.toString(tokens1));
					if(items[0][i].id == Integer.parseInt(tokens1[0])){
						items[0][i].num = Integer.parseInt(tokens1[1]);
						if(items[0][i].num <= 0) items[0][i].empty = true;
						else items[0][i].empty = false;
					}
				}
				br.close();
			} catch (IOException er) {
			}
			
		}
		if(num == MainPanel.player2){
			try {
				BufferedReader br = new BufferedReader(new FileReader("../src/Item/Player2Item"));
				for(int i = 0; i < Item.kind;i++ ){
					String str = br.readLine();
					String[] tokens1 = str.split(",");
					System.out.println(" tokens1 : "+Arrays.toString(tokens1));
					if(items[1][i].id == Integer.parseInt(tokens1[0])) {
						items[1][i].num = Integer.parseInt(tokens1[1]);
						if(items[1][i].num <= 0) items[1][i].empty = true;
						else items[1][i].empty = false;
					}
				}
				br.close();
			} catch (IOException er) {
			}
		}
	}
	
	public void writeItem(boolean init){
		if(init){
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("../src/Item/Item1")));
				for(int i = 0; i < Item.kind;i++){
					pw.println(items[0][i].id+","+items[0][i].num);
				}
				pw.close();
			} catch (IOException er) {
			}
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("../src/Item/Item2")));
				for(int i = 0; i < Item.kind;i++){
					pw.println(items[0][i].id+","+items[0][i].num);
				}
				pw.close();
			} catch (IOException er) {
			}
		}
		else{
			try {
				
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("../src/Item/Item2")));
				for(int i = 0; i < Item.kind;i++){
					System.out.println(items[0][i].id+","+items[0][i].num);
					pw.println(items[0][i].id+","+items[0][i].num);
				}
				pw.close();
			} catch (IOException er) {
			}

		}
	}
	public void writeItem(int num){
		if(num == MainPanel.everyone){
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("../src/Item/Player1Item")));
				for(int i = 0; i < Item.kind;i++){
					pw.println(items[0][i].id+","+items[0][i].num);
				}
				pw.close();
			} catch (IOException er) {
			}
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("../src/Item/Player2Item")));
				for(int i = 0; i < Item.kind;i++){
					pw.println(items[1][i].id+","+items[1][i].num);
				}
				pw.close();
			} catch (IOException er) {
			}
		}
		if(num == MainPanel.player1){
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("../src/Item/Player1Item")));
				for(int i = 0; i < Item.kind;i++){
					pw.println(items[0][i].id+","+items[0][i].num);
				}
				pw.close();
			} catch (IOException er) {
			}
		}
		if(num == MainPanel.player2){
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("../src/Item/Player2Item")));
				for(int i = 0; i < Item.kind;i++){
					pw.println(items[1][i].id+","+items[1][i].num);
				}
				pw.close();
			} catch (IOException er) {
			}
		}
	}
}
		
	


