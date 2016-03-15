import java.util.*;
import java.io.*;

public class ClientInterface{
	HashMap<Integer,Account> accountMap;
	HashMap<Integer,Contribution> contributionMap;
	HashMap<Integer, Evaluation> evaluationMap;
	int nextAccId, nextContId, nextEvalId;
	int active; 
	double rating_scale, alpha, beta, active_user_time;
	long validation_time;
	String validation_type;
	Timer timer;
	Scorer scorer;
	Validator validator;
	TestClient client;
	HashSet<Account> active_users;
	boolean logging = false;

	public ClientInterface(double rs, double a, double b, int aut, int vt, String type, TestClient cl){
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
		timer = new Timer();
		client = cl;
		if(type.equals("LnTrust")){
			scorer = new LnTrustScorer(this);
			validator = new LnTrustValidator(this);
		}
		else if(type.equals("RankTrust")){
			scorer = new RankTrustScorer(this);
			validator = new RankTrustValidator(this);
		}
		active_users = new HashSet<Account>(); 
	}

	public void checkContribution(Contribution cont){
		// scorer.calculateScore(ev,cont);
		int decision = validator.validate(cont);
		if(decision==0) client.acceptContribution(cont.getId());
		else client.rejectContribution(cont.getId());
		removeContribution(cont);
	}

	public int createAccount(String kind){
		int ind = nextAccId;
		Account ac = new Account(ind, 0.5, kind);
		accountMap.put(ind, ac);
		if(logging) System.out.println("ClientInterface: Account "+ind+" made.");
		return nextAccId++;
	}

	public int createContribution(int accId,boolean crrct){
		int ind = nextContId;
		Account contributor = accountMap.get(accId); 
		Contribution co = new Contribution(ind, contributor, rating_scale, crrct);
		double score = scorer.computeInitialScore(co);
		co.setContributionScore(score);
		contributionMap.put(ind, co);
		active_users.add(contributor);
		contributor.contributions.add(co);
		addTimerTask(co);
		if(logging) System.out.println("ClientInterface: Contribution "+ind+" made by Account "+accId+".");
		return nextContId++;
	}

	public int createEvaluation(int accId, int contId, double rating){
		int ind = nextEvalId;
		Account evaluator = accountMap.get(accId); 
		Contribution cont = contributionMap.get(contId); 
		Evaluation ev = new Evaluation(ind, evaluator, cont, rating);
		active_users.add(evaluator);
		if(logging) System.out.println("ClientInterface: Evaluation "+ind+" made by Account "+accId+" on Contribution "+contId+" with rating "+rating+".");
		scorer.calculateScore(ev,cont);
		cont.evaluations.add(ev);
		evaluationMap.put(ind, ev);
		int decision = validator.validate(cont);
		if(decision==0){
			client.acceptContribution(contId);
			cont.state = 1;
			removeContribution(cont);
		}
		else if(decision==1){
			client.rejectContribution(contId);
			removeContribution(cont);
			cont.state = 1;
		}
		return nextEvalId++;
	}

	public void removeContribution(Contribution c){
		c.getContributor().contributions.remove(c);
		contributionMap.remove(c.getId());
		for(int i=0; i<c.evaluations.size(); i++){
			Evaluation eval = c.evaluations.get(i);
			eval.account.evaluations.remove(eval);
			evaluationMap.remove(c.evaluations.get(i).getId());
		}
	}

	public void removeContribution(int ci){
		Contribution c = contributionMap.get(ci);
		c.getContributor().contributions.remove(c);
		contributionMap.remove(c.getId());
		for(int i=0; i<c.evaluations.size(); i++){
			Evaluation eval = c.evaluations.get(i);
			eval.account.evaluations.remove(eval);
			evaluationMap.remove(c.evaluations.get(i).getId());
		}
	}

	public void addTimerTask(Contribution c){
        timer.schedule(new ExpiryCheckerTask(c,this),validation_time*1000);
    }

	public int getActiveCount()
	{
		return active_users.size(); 
	}

	public double getRatingScale()
	{
		return rating_scale; 
	}

	public int getMval()
	{
		int ans = -1; 
		for(Integer k: accountMap.keySet())
		{
			Account temp = accountMap.get(k);
			ans = Math.max(ans,temp.getNumEv()); 
		}
		return ans; 
	}
}