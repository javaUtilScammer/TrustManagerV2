import java.util.*;

public class Contribution extends Component{
	Account account;
	double score;
	ArrayList<Evaluation> evaluations;

	public Contribution(int id, Account acc, double sc){
		this.id = id;
		account = acc;
		score = sc;
		evaluations = new ArrayList<Evaluation>();
		timeCreated = System.currentTimeMillis();
	}

	public Account getContributor()
	{
		return account; 
	}

	public double getContributionScore()
	{
		return score; 
	}

	public void setContributionScore(double score)
	{
		this.score=score;
	}

	public int getNumEv()
	{
		return evaluations.size(); 
	}

}