import java.util.*;
import java.io.*;

public class ClientInterface{
	HashMap<Integer,Account> accountMap;
	HashMap<Integer,Contribution> contributionMap;
	HashMap<Integer, Evaluation> evaluationMap;
	int nextAccId, nextContId, nextEvalId;
	Scorer scorer;
	Validator validator;
	int contsAccepted, contsRejected, contsTotal;
	double rating_scale, alpha, beta, active_user_time, active_eval_time;

	public ClientInterface(){
		accountMap = new HashMap<Integer, Account>();
		contributionMap = new HashMap<Integer, Contribution>();
		evaluationMap = new HashMap<Integer, Evaluation>();
		nextAccId = 0;
		nextContId = 0;
		nextEvalId = 0;
		readConfig();
	}

	public void readConfig(){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//read config input file here
	}

	public void printSummary(){
		//stuff
	}

	private void contTimerUp(int contId){
		// stuff
	}

	public Account createAccount(){
		int ind = nextAccId;
		Account ac = new Account(ind, accountMap.get(accId), contributionMap.get(contId), rating);
		accMap.put(ind++, ac);
		return ac;
	}

	public Contribution createContribution(int accId){
		int ind = nextContId;
		Contribution co = new Contribution(ind, accountMap.get(accId), contributionMap.get(contId), rating);
		contMap.put(ind++, co);
		return co;
	}

	public Evaluation createEvaluation(int accId, int contId, double rating){
		int ind = nextEvalId;
		Evaluation ev = new Evaluation(ind, accountMap.get(accId), contributionMap.get(contId), rating);
		evalMap.put(ind++, ev);
		// scorer.evaluate();
		return ev;
	}
}