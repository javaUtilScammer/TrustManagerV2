public class RankTrustValidator extends Validator{
	ClientInterface intrface;
	double threshold;

	public RankTrustValidator(ClientInterface ci){
		super(ci);
		threshold = rating_scale/2.0;
	}

	public boolean validate(Contribution cont){
		return cont.getContributionScore()>=threshold;
	}
}