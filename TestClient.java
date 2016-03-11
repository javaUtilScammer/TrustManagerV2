import java.util.*;
import java.io.*;

public class TestClient{
	int goodAccounts, badAccounts, neutAccounts;
	ArrayList<Integer> accIds;
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
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			goodAccounts = Integer.parseInt(in.readLine());
			badAccounts = Integer.parseInt(in.readLine());
			neutAccounts = Integer.parseInt(in.readLine());
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

	}
}