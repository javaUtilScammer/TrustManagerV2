
public abstract class Scorer{
	double rating_scale, alpha, beta, active_user_time;
	ClientInterface intrface; 

	abstract void calculateScore(Evaluation ev, Contribution cont);
    abstract void acceptContribution(Contribution cont);
    abstract void rejectContribution(Contribution cont);
    abstract int adminAccept(Contribution cont);
    abstract double computeInitialScore(Contribution cont);
}