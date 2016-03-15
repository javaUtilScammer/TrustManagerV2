import java.util.*;
public class LnTrustScorer extends Scorer{

	public LnTrustScorer(ClientInterface intrface)
	{
		super(intrface);
	}

	public void calculateScore(Evaluation ev, Contribution cont){
		double rating_scale = intrface.getRatingScale(); 
		double currScore = cont.getContributionScore(); 
        double rating = ev.getRating(); 
        double scaled;
        //System.out.println("HELLO " + rating_scale);
        if(rating_scale==1)
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
        currScore += (scaled * trust_rating); 
        cont.setContributionScore(currScore);
        Account contributor = cont.getContributor(); 

        double conf = contributor.getTrustConfidence(); 
        double active = intrface.getActiveCount();
        double num = Math.log(intrface.getMval())/Math.log(Math.E); 
        num = Math.pow(num,beta); 
        num/=active; 
        num*=contributor.getNumEv(); 
        conf = Math.min(1, 0.50 + 0.50*num );
                    
        contributor.setTrustConfidence(conf); 
	}

	public int adminAccept(Contribution cont){
		return 0;
	}


    public double computeInitialScore(Contribution cont)
    {
    	double active = Math.max(intrface.getActiveCount(),5);
        double denom = Math.log(active) / Math.log(Math.E); 
        denom = Math.pow(denom,alpha);
        double threshold = active/denom;
        
        //score is a percentage of the threshold based on trust rating of contributor
        Account contributor = cont.getContributor();
        // System.out.println(active+" "+denom+" "+threshold+" "+alpha);
        System.out.println("Threshold: " + threshold + " Trust: " + contributor.getTrustRating()  + " Confidence: " + contributor.getTrustConfidence());
        return threshold * contributor.getTrustRating() * contributor.getTrustConfidence(); 
    }
}