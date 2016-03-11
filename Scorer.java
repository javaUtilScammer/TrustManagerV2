
public abstract class Scorer{
	double rating_scale, alpha, beta, active_user_time;

	ClientInterface intrface;

	public Scorer(ClientInterface i){
		intrface = i;
	}

	abstract void calculateScore(Evaluation ev, Contribution cont);
    abstract int adminAccept(Contribution cont);
    abstract double computeInitialScore(Contribution cont);
}