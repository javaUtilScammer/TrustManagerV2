public class RankTrustScorer extends Scorer{

	public RankTrustScorer(ClientInterface i){
		super(i);
	}

	public void calculateScore(Evaluation ev, Contribution cont){

	}

	public int adminAccept(Contribution cont){
		return 0;
	}

	public double computeInitialScore(Contribution cont){
		return 0.0;
	}
}