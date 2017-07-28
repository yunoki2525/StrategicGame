package main;

import java.util.Arrays;


public class MapStage {

	int position[]; 
	
	public MapStage(int[] position ){
		this.position	= Arrays.copyOf(position,position.length);
	}
	
}
