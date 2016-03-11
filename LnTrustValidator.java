public class LnTrustValidator extends Validator{

	public double computeTrustRating(double accepted, double rejected, double total)
    {
        double ret = 0.50; 
        double mult = (accepted-rejected)/total; 
        ret += (0.50 * mult);
        return ret; 
    }
    
	public boolean validate(Contribution cont){
		return true;
	}
}