public class RankTrustValidator extends Validator{
	ClientInterface intrface;
	double eps = 1e-3;
	double threshold;

	public RankTrustValidator(ClientInterface ci){
		super(ci);
		threshold = rating_scale/2.0;
	}

	public int validate(Contribution cont){
		// return cont.getContributionScore()>=threshold;
		double score = cont.getContributionScore();
		System.out.println(cont.getId()+" "+score);
		if(score>threshold) return 0;
		else if(score<=eps) return 1;
		return 2;
		// return 0;
	}
}