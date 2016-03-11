import java.util.*;
public class LnTrustScorer extends Scorer{
	double rating_scale, alpha, beta, active_user_time;

	public LnTrustScorer(ClientInterface intrface)
	{
		super(intrface);
	}

	public void calculateScore(Evaluation ev, Contribution cont){
		double rating_scale = intrface.getRatingScale(); 
		double currScore = cont.getContributionScore(); 
        double rating = ev.getRating(); 
        double scaled;
        if(rating_scale=='1')
        {
            if(rating==0) scaled = -1;
            else scaled = 1; 
        }else
        {
            scaled = -1;
            scaled += (0.33*rating);
            if(1-scaled < (0.000001)) scaled = 1; 
        }
        
        Account submit = ev.getCreatedBy(); 
        double trust_rating = submit.getTrustRating(); 
        double trust_confidence = submit.getTrustConfidence(); 
        currScore += (scaled * trust_rating * trust_confidence); 
        cont.setContributionScore(currScore);
        Account contributor = cont.getContributor(); 

        double conf = contributor.getTrustConfidence(); 
        double active = intrface.getActiveCount();
        double num = Math.log(intrface.getMval())/Math.log(Math.E); 
        num = Math.pow(num,beta_factor); 
        num/=active; 
        num*=contributor.getNumEv(); 
        conf = Math.min(1, 0.50 + 0.50*num );
                    
        contributor.setTrustConfidence(conf); 
	}

	public void calculateScore(Evaluation ev, Contribution cont)
	{

	}

	public int adminAccept(Contribution cont){
		return 0;
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