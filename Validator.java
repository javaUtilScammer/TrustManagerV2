
public abstract class Validator{
	ClientInterface intrface;
	double rating_scale, alpha, beta, active_user_time;

	abstract boolean validate(Contribution cont);
}