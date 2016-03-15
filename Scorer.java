
public abstract class Scorer{
	double rating_scale, alpha, beta, active_user_time;

	ClientInterface intrface;

	public Scorer(ClientInterface i){
		intrface = i;
		alpha = intrface.alpha;
		beta = intrface.beta;
		rating_scale = intrface.rating_scale;
		active_user_time = intrface.active_user_time;
	}

	abstract void calculateScore(Evaluation ev, Contribution cont);
    abstract int adminAccept(Contribution cont);
    abstract double computeInitialScore(Contribution cont);
}