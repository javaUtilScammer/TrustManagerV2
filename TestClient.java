import java.util.*;
import java.io.*;

public class TestClient{
	int goodAccounts, badAccounts, neutAccounts;
	int[] accIds;
	int[][] actChance;
	ArrayList<Integer> contIds;
	double rating_scale, alpha, beta;
	int active_user_time, validation_time;
	ClientInterface intrface;

	public static void main(String[] args){
		new TestClient();
	}

	public TestClient(){
		readInput();
		intrface = new ClientInterface(rating_scale,alpha,beta,active_user_time,validation_time);
		setup();
	}

	public void readInput(){
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
			actChance = new int[3][3];
			String[] g = in.readLine().split(" ");
			goodAccounts = Integer.parseInt(g[0]);
			actChance[0][0] = Integer.parseInt(g[1]);
			actChance[0][1] = Integer.parseInt(g[2]);
			actChance[0][2] = Integer.parseInt(g[3]);
			g = in.readLine().split(" ");
			badAccounts = Integer.parseInt(g[0]);
			actChance[1][0] = Integer.parseInt(g[1]);
			actChance[1][1] = Integer.parseInt(g[2]);
			actChance[1][2] = Integer.parseInt(g[3]);
			g = in.readLine().split(" ");
			neutAccounts = Integer.parseInt(g[0]);
			actChance[2][0] = Integer.parseInt(g[1]);
			actChance[2][1] = Integer.parseInt(g[2]);
			actChance[2][2] = Integer.parseInt(g[3]);
			rating_scale = Double.parseDouble(in.readLine());
			alpha = Double.parseDouble(in.readLine());
			beta = Double.parseDouble(in.readLine());
			active_user_time = Integer.parseInt(in.readLine());
			validation_time = Integer.parseInt(in.readLine());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void setup(){
		int total = goodAccounts+badAccounts+neutAccounts;
		accIds = new int[total];
		for(int i=0; i<total; i++) accIds[i] = intrface.createAccount();
	}
}