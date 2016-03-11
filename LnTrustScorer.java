import java.util.*;
public class LnTrustScorer extends Scorer{
	double rating_scale, alpha, beta, active_user_time;

	public LnTrustScorer(ClientInterface intrface)
	{
		this.intrface = intrface; 
	}

	public void calculateScore(Evaluation ev, Contribution cont)
	{

	}

    public void acceptContribution(Contribution cont)
    {

    }

    public void rejectContribution(Contribution cont)
    {

    }
    public int adminAccept(Contribution cont)
    {

    }
    public double computeInitialScore(Contribution cont)
    {
    	//first compute for the threshold
        //get number of active users
        double active = Math.max(intrface.getActiveCount(),5);
        //compute for denominator ln(active) ^ degree_of_strictness
        double denom = Math.log(active / Math.log(Math.E)); 
        denom = Math.pow(denom,degree_of_strictness);
        double threshold = active/denom; 
        
        //score is a percentage of the threshold based on trust rating of contributor
        Account contributor = cont.getContributor(); 
        return threshold * contributor.getTrustRating() * contributor.getTrustConfidence(); 
    }
}