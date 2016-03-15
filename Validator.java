public abstract class Validator{
	ClientInterface intrface;
	double rating_scale, alpha, beta, active_user_time;
	
	public Validator(ClientInterface ci){
		intrface = ci;
		rating_scale = intrface.rating_scale;
		alpha = intrface.alpha;
		beta = intrface.beta;
		active_user_time = intrface.active_user_time;
	}

	abstract int validate(Contribution cont);
}