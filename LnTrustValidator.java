import java.util.*;

public class LnTrustValidator extends Validator{

    public LnTrustValidator(ClientInterface ci){
        super(ci);
    }

	public double computeTrustRating(double accepted, double rejected, double total)
    {
        double ret = 0.50; 
        double mult = (accepted-rejected)/total; 
        ret += (0.50 * mult);
        return ret; 
    }


	public boolean validate(Contribution cont)
    {
        double score = cont.getContributionScore(); 
        double active = Math.max(intrface.getActiveCount(),5);
        double denom = Math.log(active) / Math.log(Math.E); 
        denom = Math.pow(denom,alpha);
        double threshold = active/denom; 
        System.out.println("Threshold for "+cont.getId()+" "+threshold+", Score: "+score);
        //If accepted, modify the scores of Evaluators that Evaluated this accepted function
        if(score>=threshold)
        {
            ArrayList<Evaluation> evals = cont.getEvaluations();
            for(int i=0;i<evals.size();i++)
            {
                Evaluation temp = evals.get(i);
                
                double rating = temp.getRating(); 
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
                
                Account sub = temp.getCreatedBy(); 
                double acc = sub.getAccepted(); 
                double rej = sub.getRejected();
                double total = sub.getTotal(); 
                
                //increment number of Accepted or Rejected and Total number of Contributions by half a point
                if(scaled>=0)
                    sub.incAccepted(acc+(scaled*0.50));
                else
                    sub.incRejected(rej+(scaled*0.50)); 
                sub.incTotal(total+0.50); 
                
                double newTrust = computeTrustRating(sub.getAccepted(), sub.getRejected(), sub.getTotal()); 
                sub.setTrustConfidence(newTrust); 
                //update the database
            }
            
            //Contributor gets trust rating score modified also
            Account user = cont.getContributor();
            user.incAccepted(user.getAccepted()+1); 
            user.incTotal(user.getTotal()+1);
            double newTrust = computeTrustRating(user.getAccepted(), user.getRejected(), user.getTotal()); 
            user.setTrustConfidence(newTrust); 
            return true; 
        }else return false; 
    }
}