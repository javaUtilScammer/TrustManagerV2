
public abstract class Validator{
	double rating_scale, alpha, beta, active_user_time;

	public abstract boolean validate(Contribution cont);
}