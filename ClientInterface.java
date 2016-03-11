import java.util.*;
import java.io.*;

public class ClientInterface{
	HashMap<Integer,Account> accountMap;
	HashMap<Integer,Contribution> contributionMap;
	HashMap<Integer, Evaluation> evaluationMap;
	int nextAccId, nextContId, nextEvalId;
	int contsAccepted, contsRejected, contsTotal;
	int active; 
	double rating_scale, alpha, beta, active_user_time, validation_time;
	String validation_type;
	Scorer scorer;
	Validator validator;

	public ClientInterface(double rs, double a, double b, int aut, int vt, String type){
		accountMap = new HashMap<Integer, Account>();
		contributionMap = new HashMap<Integer, Contribution>();
		evaluationMap = new HashMap<Integer, Evaluation>();
		nextAccId = 0;
		nextContId = 0;
		nextEvalId = 0;
		rating_scale = rs;
		alpha = a;
		beta = b;
		active_user_time = aut;
		validation_time = vt;
		active = 0;
		validation_type = type;
		if(type.equals("LnTrust")){
			scorer = new LnTrustScorer(this);
			validator = new LnTrustValidator();
		}
		else if(type.equals("RankTrust")){
			scorer = new RankTrustScorer(this);
			validator = new RankTrustValidator();
		}
	}

	public void printSummary(){
		//stuff
	}

	private void contTimerUp(int contId){
		// stuff
	}

	public int createAccount(){
		int ind = nextAccId;
		Account ac = new Account(ind, rating_scale);
		accountMap.put(ind, ac);
		System.out.println("ClientInterface: Account "+ind+" made.");
		return nextAccId++;
	}

	public int createContribution(int accId){
		int ind = nextContId;
		Contribution co = new Contribution(ind, accountMap.get(accId), rating_scale);
		double score = scorer.computeInitialScore(co);
		contributionMap.put(ind, co);
		return nextContId++;
	}

	public int createEvaluation(int accId, int contId, double rating){
		int ind = nextEvalId;
		Evaluation ev = new Evaluation(ind, accountMap.get(accId), contributionMap.get(contId), rating);
		evaluationMap.put(ind, ev);
		// scorer.evaluate();
		return nextEvalId++;
	}

	public int getActiveCount()
	{
		return active; 
	}

	public double getRatingScale()
	{
		return rating_scale; 
	}
}