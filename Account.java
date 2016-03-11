import java.util.*;

public class Account extends Component{
	ArrayList<Contribution> contributions;
    ArrayList<Evaluation> evaluations;
	double rating, confidence, cacc, crej, ctotal;
	int num_ev;

	public class Account(int id, double rat){
		this.id = id;
		contributions = new ArrayList<Contribution>();
		evaluations = new ArrayList<Evaluation>();
		rating = rat;
		num_ev = 0;
		cacc = 0;
		crej = 0;
		ctotal = 0;
		timeCreated = System.getCurrentTimeMillis();
	}
}