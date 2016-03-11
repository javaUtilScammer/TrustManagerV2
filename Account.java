import java.util.*;

public class Account extends Component{
	ArrayList<Contribution> contributions;
    ArrayList<Evaluation> evaluations;
	double rating, confidence, cacc, crej, ctotal;
	int num_ev;

	public Account(int id, double rat){
		this.id = id;
		contributions = new ArrayList<Contribution>();
		evaluations = new ArrayList<Evaluation>();
		rating = rat;
		num_ev = 0;
		cacc = 0;
		crej = 0;
		ctotal = 0;
		timeCreated = System.currentTimeMillis();
	}

	public double getTrustRating()
	{
		return rating; 
	}

	public void setTrustRating(double r)
	{
		rating = r; 
	}

	public double getTrustConfidence()
	{
		return confidence;
	}

	public ArrayList<Contribution> getContributions()
    {
        return contributions; 
    }

    public ArrayList<Evaluation> getEvaluations()
    {
        return evaluations; 
    }

	public void setTrustConfidence(double conf)
	{
		confidence = conf; 
	}

	public int getNumEv()
	{
		int temp = 0; 
		for(int i=0; i<contributions.size();i++)
			temp+=contributions.get(i).getNumEv();
		return temp;
	}
}