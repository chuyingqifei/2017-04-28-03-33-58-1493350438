public class BowlingGame {
	private static final int NOT_VALID = 0;
	private static final int STRIKE = 1;
	private static final int SPARE = 2;
	private static final int MISS = 3;

	public int getBowlingScore(String bowlingCode) {
		String[] frames = bowlingCode.split("\\|");
		if(frames.length < 10){
			return 0;
		}
		Frame[] game = new Frame[12];
		for(int i = 0;i<12;i++){
			game[i] = new Frame();
		}
		for(int i = 0;i<10;i++){
			game[i] = getFrameScore(frames[i]);
		}
		if(game[9].getState() == 1){
			String extra = frames[11];
			if(extra.charAt(0) == 'X'){
				game[10].setState(STRIKE);
				game[10].setFirst_shot(10);
			}
			else if(extra.charAt(0) >= '0' && extra.charAt(0) <= '9'){
				game[10].setState(MISS);
				game[10].setFirst_shot(extra.charAt(0) - '0');
			}
			else{
				game[10].setState(MISS);
			}
			
			if(extra.charAt(1) == 'X'){
				game[11].setState(STRIKE);
				game[11].setFirst_shot(10);
			}
			else if(extra.charAt(1) > '0' && extra.charAt(1) <= '9'){
				game[11].setState(MISS);
				game[11].setFirst_shot(extra.charAt(1) - '0');
			}
			else{
				game[11].setState(MISS);
			}
		}
		if(game[9].getState() == 2){
			String extra = frames[11];
			if(extra.charAt(0) == 'X'){
				game[10].setState(STRIKE);
				game[10].setFirst_shot(10);
			}
			else if(extra.charAt(0) > '0' && extra.charAt(0) <= '9'){
				game[10].setState(MISS);
				game[10].setFirst_shot(extra.charAt(0) - '0');
			}
			else{
				game[10].setState(MISS);
			}
		}
		return getGameScore(game);
    }
    public int getGameScore(Frame[] game){
    	int totalScore = 0;
    	for(int i = 0;i<9;i++){
			if(game[i].getState() == 1){
				totalScore += 10;
				if(game[i+1].getState() == 1){
					totalScore += 10;
					totalScore += game[i+2].getFirst_shot();
				}
				else{
					totalScore += game[i+1].getFirst_shot();
					totalScore += game[i+1].getSecond_shot();
				}
			}
			else if(game[i].getState() == 2){
				totalScore += 10;
				totalScore += game[i+1].getFirst_shot();
			}
			else if(game[i].getState() == 3){
				totalScore += game[i].getFirst_shot();
				totalScore += game[i].getSecond_shot();
			}
		}
    	if(game[9].getState() == 1){
    		totalScore += 10;
    		totalScore += game[10].getFirst_shot();
    		totalScore += game[11].getFirst_shot();
    	}
    	else if(game[9].getState() == 2){
    		totalScore += 10;
    		totalScore += game[10].getFirst_shot();
    	}
    	else{
    		totalScore += game[9].getFirst_shot();
    		totalScore += game[9].getSecond_shot();
    	}
		return totalScore;
    }
    
    public Frame getFrameScore(String score){
    	if(score.length() == 0){
    		return new Frame();
    	}
    	else if(score.length() == 1){
    		if(score.charAt(0) == 'X'){
    			Frame frame = new Frame();
    			frame.setState(STRIKE);
    			frame.setFirst_shot(10);
    			return frame;
    		}
    		else{
    			return new Frame();
    		}
    	}
    	else if(score.length() == 2){
    		if(score.charAt(1) == '/'){
    			Frame frame = new Frame();
    			frame.setState(SPARE);
    			if(score.charAt(0) == '-'){
    				frame.setFirst_shot(0);
    				frame.setSecond_shot(10);
    			}
    			else if(score.charAt(0) - '0' > 0 && score.charAt(0) - '0' < 10){
    				frame.setFirst_shot(score.charAt(0) - '0');
    				frame.setSecond_shot(10 - frame.getFirst_shot());
    			}
    			else{
    				return new Frame();
    			}
    			return frame;
    		}
    		else{
    			Frame frame = new Frame();
    			frame.setState(MISS);
    			if(score.charAt(0) == '-'){
    				frame.setFirst_shot(0);
    			}
    			else if(score.charAt(0) - '0' > 0 && score.charAt(0) - '0' < 10){
    				frame.setFirst_shot(score.charAt(0) - '0');
    			}
    			else{
    				return new Frame();
    			}
    			
    			if(score.charAt(1) == '-'){
    				frame.setSecond_shot(0);
    			}
    			else if(score.charAt(1) - '0' > 0 && score.charAt(1) - '0' < 10){
    				frame.setSecond_shot(score.charAt(1) - '0');
    			}
    			else{
    				return new Frame();
    			}
    			return frame;
    		}
    		
    	}
    	else{
    		return new Frame();
    	}
    	
    }
	
	static class Frame{
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public int getFirst_shot() {
			return first_shot;
		}
		public void setFirst_shot(int first_shot) {
			this.first_shot = first_shot;
		}
		public int getSecond_shot() {
			return second_shot;
		}
		public void setSecond_shot(int second_shot) {
			this.second_shot = second_shot;
		}
		public Frame(){
			state = NOT_VALID;
			first_shot = 0;
			second_shot = 0;
		}
		private int state;
		private int first_shot;
		private int second_shot;
		public String toString(){
			return " " + state +" " + first_shot + " " + second_shot;
		}
		
	
	}
	
}
