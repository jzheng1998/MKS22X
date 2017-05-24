import java.io.*;
import java.io.FileNotFoundException;
import java.util.*;
public class Maze{

    private static final String CLEAR_SCREEN =  "\033[2J";
    private static final String HIDE_CURSOR =  "\033[?25l";
    private static final String SHOW_CURSOR =  "\033[?25h";
    
    Location start,end;
    private char[][]maze;
    private int maxRows,maxCols;
    
    private static String go(int x,int y){
	return ("\033[" + x + ";" + y + "H");
    }
    
    private static String color(int foreground,int background){
	return ("\033[0;" + foreground + ";" + background + "m");
    }
    
    public void clearTerminal(){
	System.out.println(CLEAR_SCREEN+"\033[1;1H");
    }
    
    public static String colorize(String s){
	String ans = "";
	Scanner in = new Scanner(s);
	while(in.hasNext()){
	    String line ="";
	    for(char c : in.nextLine().toCharArray()){
		if(c == '#'){
		    line+= color(37,47)+c;
		}
		else if(c == '@'){
		    line+= color(33,40)+c;
		}
		else if(c == '?'){
		    line+= color(37,42)+c;
		}
		else if(c == '.'){
		    line+= color(36,40)+c;
		}
		else if(c == ' '){
		    line+= color(35,40)+c;
		}else{
		    line+=color(37,40)+c;
		}
		
	    }
	    ans += line+color(37,40)+"\n";
	}
	return ans;
    }
    
    public Maze(String filename){
	ArrayList<char[]> lines = new ArrayList<char[]>();
	int startr=-1, startc=-1;
	int endr=-1,endc=-1;
	try{
	    Scanner in = new Scanner(new File(filename));
	    while(in.hasNext()){
		lines.add(in.nextLine().toCharArray());
	    }
	}catch(FileNotFoundException e){
	    System.out.println("File not found: "+filename);
	    System.exit(1);
	}
	maze = new char[lines.size()][];
	for(int i = 0; i < maze.length; i++){
	    maze[i]=lines.get(i);
	}
	for(int r=0; r<maze.length;r++){
	    for(int c=0; c<maze[r].length;c++){
		if(maze[r][c]=='S'){
		    //erase the S
		    maze[r][c]=' ';
		    if(startr == -1){
			startr=r;
			startc=c;
		    }else{
			System.out.println("Multiple 'S' found!");
			System.exit(0);
		    }
		}
		
		if(maze[r][c]=='E'){
		    maze[r][c]=' ';
		    if(endr == -1){
			endr=r;
			endc=c;
		    }else{
			System.out.println("Multiple 'E' found!");
			System.exit(0);
		    }
		}
	    }
	}
	if(startr == -1 || endr == -1){
	    System.out.println("Missing 'S' or 'E' from maze.");
	    System.exit(0);
	    
	}
	maxRows = maze.length;
	maxCols = maze[0].length;
	
	end = new Location(endr,endc,null,0,0,false);
	int d = Math.abs(endr -startr) + Math.abs(startc - endc);
	start = new Location(startr,startc,null,0,d,false);
	//maze[startr][startc] = 'S';
	//maze[endr][endc] = 'E';
    }
    
    public String toString(int delay){
	try{
	    Thread.sleep(delay);
	}catch(InterruptedException e){
	    
	}
	return HIDE_CURSOR+CLEAR_SCREEN+go(1,1)+colorize(toString())+SHOW_CURSOR;
    }
    
    public String toString(){
	int maxr = maze.length;
	int maxc = maze[0].length;
	String ans = "";
	for(int i = 0; i < maxr * maxc; i++){
	    int row = i/maxc;
	    int col = i%maxc;
	    
	    char c =  maze[row][col];
	    ans+=c;
	    if( col == maxc-1 ){
		ans += "\n";
	    }
	    
	}
	return ans + "\n";
    }

    public Location getStart(){
	return start;
    }
    
    public Location getEnd(){
	return end;
    }
    
    public int getRowLength(){
	return maze.length;
    }
    
    public int getColLength(){
	return maze[0].length;
    }
    
    public char get(int row,int col){
	return maze[row][col];
    }
    
    public void set(int row,int col, char n){
	maze[row][col] = n;
    }
}
