public class RankTrustValidator extends Validator{
	ClientInterface intrface;
	double threshold;

	public RankTrustValidator(ClientInterface ci){
		super(ci);
		threshold = rating_scale/2.0;
	}

	public int validate(Contribution cont){
		// return cont.getContributionScore()>=threshold;
		if(cont.getContributionScore()>=threshold) return 0;
		return 2;
		// return 0;
	}
}