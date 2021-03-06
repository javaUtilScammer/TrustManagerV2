import java.util.*;
import java.io.*;

public class TestClient{
	int goodAccounts, badAccounts, neutAccounts, totalAccounts;
	int contsTotal, evalsTotal;
	int correctVerdict, adminConts;
	int contsAC, contsRC, contsAI, contsRI;
	ArrayList<AccountTest> accs = new ArrayList<AccountTest>();
	ArrayList<Integer> contIds = new ArrayList<Integer>();
	HashMap<Integer, ContributionTest> contMap = new HashMap<Integer,ContributionTest>();
	double rating_scale, alpha, beta;
	int active_user_time, validation_time;
	String validation_type;
	ClientInterface intrface;
	boolean adminAccept = !false;

	public static void main(String[] args){
		new TestClient();
	}

	public TestClient(){
		readInput();
		simulate();
		try{
			Thread.sleep(validation_time * 1000 + 500);
		}
		catch(Exception e){e.printStackTrace();}
		printSummary();
	}

	public void readInput(){
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
			rating_scale = Double.parseDouble(in.readLine());
			alpha = Double.parseDouble(in.readLine());
			beta = Double.parseDouble(in.readLine());
			active_user_time = Integer.parseInt(in.readLine());
			validation_time = Integer.parseInt(in.readLine());
			validation_type = in.readLine();
			intrface = new ClientInterface(rating_scale,alpha,beta,active_user_time,validation_time, validation_type,this);
			accs = new ArrayList<AccountTest>();
			goodAccounts = Integer.parseInt(in.readLine());
			String[] g = in.readLine().split(" ");
			int contribute = Integer.parseInt(g[0]);
			int evaluate = Integer.parseInt(g[1]);
			int correct = Integer.parseInt(g[2]);
			for(int i=0; i<goodAccounts; i++){
				int id = intrface.createAccount("Trustee");
				accs.add(new AccountTest(id, contribute, evaluate, correct));
			}
			badAccounts = Integer.parseInt(in.readLine());
			g = in.readLine().split(" ");
			contribute = Integer.parseInt(g[0]);
			evaluate = Integer.parseInt(g[1]);
			correct = Integer.parseInt(g[2]);
			for(int i=0; i<badAccounts; i++){
				int id = intrface.createAccount("Troll");
				accs.add(new AccountTest(id, contribute, evaluate, correct));
			}
			neutAccounts = Integer.parseInt(in.readLine());
			g = in.readLine().split(" ");
			contribute = Integer.parseInt(g[0]);
			evaluate = Integer.parseInt(g[1]);
			correct = Integer.parseInt(g[2]);
			for(int i=0; i<neutAccounts; i++){
				int id = intrface.createAccount("Netural");
				accs.add(new AccountTest(id, contribute, evaluate, correct));
			}
			totalAccounts = goodAccounts+badAccounts+neutAccounts;
			contsAC = 0;
			contsRC = 0;
			contsAI = 0;
			contsRI = 0;
			contsTotal = 0;
			evalsTotal = 0;
			correctVerdict = 0;
			adminConts = 0;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void simulate(){
		for(int o=0; o<1000; o++){
			if(adminAccept) adminAccept();
			for(int i=0; i<totalAccounts; i++){
				AccountTest curAcc = accs.get(i);
				int roll = curAcc.move();
				if(roll==0) continue;
				else if(roll==1){
					boolean crrct = RNG(curAcc.chance_Correct);
					int ind = intrface.createContribution(curAcc.id,crrct);
					// accs.get(i).contributions.add(ind);
					contIds.add(ind);
					contsTotal++;
					// if(crrct) contsCorrect++;
					// else contsWrong++;
					contMap.put(ind,new ContributionTest(ind, i, crrct));
				}
				else if(roll==2){
					int ind = 0;
					if(contIds.size()==0) continue;
					ContributionTest cont = null;
					while(true){
						if(ind>=contIds.size()) break;
						int id = contIds.get(ind);
						cont = contMap.get(id);
						if((cont.account_id==curAcc.id)||
							(curAcc.sent.contains(id))){
							ind++;
							continue;
						}
						break;
					}
					if(ind>=contIds.size()) continue;
					boolean correct = RNG(curAcc.chance_Correct);
	    			double score = 1.0;
	    			if(correct^cont.correct) score = 0.0;
	    			int id = cont.id;
	    			int ind2 = intrface.createEvaluation(i,id,score);
					evalsTotal++;
	    			curAcc.sent.add(id);
				}
			}
			if(adminAccept) adminAccept();
		}
	}

	public void adminAccept(){
		if(contIds.size()!=0){	
			adminConts++;
			int conID = contIds.get(0);
			intrface.adminAccept(conID);
			acceptContribution(conID);
			System.out.println("Accepts so far: "+(contsAC+contsAI));
		}
	}

	public void printSummary(){
		System.out.println("*************");
		System.out.printf("Total Contributions: %d\n", contsTotal);
		System.out.printf("%10s|%6s|%6s|\n", "Class", "Acc", "Rej");
		System.out.printf("%10s|%6d|%6d|\n", "Correct", contsAC, contsRC);
		System.out.printf("%10s|%6d|%6d|\n", "Incorrect",contsAI, contsRI);
		System.out.printf("Total Evaluations: %d\n", evalsTotal);
		System.out.printf("Total Contributions: %d\n", contsTotal);
		System.out.printf("Correct Verdicts: %d\n", correctVerdict);
		double per_cor = ((contsAC+contsRI)/(double)contsTotal)*100;
		double per_wrong = ((contsAI+contsRC)/(double)contsTotal)*100;
		System.out.printf("Percent Correct: %.2f\n",per_cor);
		System.out.printf("Percent Incorrect: %.2f\n",per_wrong);
		if(adminAccept) System.out.printf("Admin Accepts: %d\n", adminConts);
	}

	public void acceptContribution(int ci){
		if(contMap.get(ci).correct){
			correctVerdict++;
			contsAC++;
		}
		else contsAI++;
		removeContribution(ci);
	}

	public void rejectContribution(int ci){
		try{	
			if(!contMap.get(ci).correct){
				correctVerdict++;
				contsRC++;
			}
			else contsRI++;
		}
		catch(Exception e){e.printStackTrace();System.out.println("huehue");}
		removeContribution(ci);
	}

	public void removeContribution(int ci){
		contIds.remove((Integer)ci);
		contMap.remove(ci);
	}

	public void adminAccept(int contID){
		//assumes that cont is "correct"
		// contsAccepted++;
		adminConts++;
		removeContribution(contID);
		intrface.removeContribution(contID);
	}

	private int randInRange(int min, int max){
    	return (int)(Math.random() * (max - min) + min);
    }

    public int pickRandomID(int n){
    	return (int)(Math.random() * (n-1));
    }

    public boolean RNG(int chance){
    	int roll = (int)(Math.random() * 100)+1;
    	if(chance>=roll) return true;
    	return false;
    }

}

class AccountTest{
	int chance_Contribute, chance_Evaluate, chance_Correct;
	int chance_Cont, id;
	// ArrayList<Integer> contributions = new ArrayList<Integer>();
	HashSet<Integer> sent;

	public AccountTest(int i, int cc, int ce, int ccc){
		id = i;
		chance_Contribute = cc;
		chance_Evaluate = ce;
		chance_Correct = ccc;
		chance_Cont = chance_Contribute+chance_Evaluate;
		sent = new HashSet<Integer>();
	}
	public int move(){
		int roll = (int) (Math.random()*100)+1;
		int ret = 0;
		if(roll<=chance_Evaluate) ret = 2; //make an eval
		else if(roll<=chance_Cont) ret = 1; //make a cont
		// System.out.println(roll+" "+chance_Eval+" "+ret);
		return ret; //0 = do nothing
	}
}

class ContributionTest{
	int account_id, id;
	boolean correct;

	public ContributionTest(int i, int a, boolean c){
		account_id = a;
		id = i;	
		correct = c;
	}
}