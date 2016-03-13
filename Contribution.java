import java.util.*;

public class Contribution extends Component{
	Account account;
	double score;
	ArrayList<Evaluation> evaluations;
	ArrayList<Contribution> contributions;
	int state = 0;

	public Contribution(int id, Account acc, double sc){
		this.id = id;
		account = acc;
		score = sc;
		evaluations = new ArrayList<Evaluation>();
		timeCreated = System.currentTimeMillis();
	}

	public int getId()
	{
		return id;
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

	public ArrayList<Evaluation> getEvaluations()
    {
        return evaluations; 
    }
}