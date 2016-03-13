
public class Evaluation extends Component{
	Account account;
	Contribution contribution;
	double rating;

	public Evaluation(int id, Account acc, Contribution cont, double rat){
		this.id = id;
		account = acc;
		contribution = cont;
		timeCreated = System.currentTimeMillis();
		rating = rat;
	}

	public int getId(){
		return id;
	}

	public double getRating()
	{
		return rating; 
	}

	public Account getCreatedBy()
	{
		return account; 
	}
}