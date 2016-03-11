import java.util.*;

public class Contribution extends Component{
	Account account;
	double score;
	ArrayList<Evaluation> evaluations;

	public Contribution(int id, Account acc, double sc){
		this.id = id;
		account = acc;
		score = sc;
		evaluations = new ArrayList<Evaluation>();
		timeCreated = System.getCurrentTimeMillis();
	}
}