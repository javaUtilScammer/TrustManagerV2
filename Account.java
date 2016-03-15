import java.util.*;

public class Account extends Component{
	ArrayList<Contribution> contributions;
    ArrayList<Evaluation> evaluations;
	double rating, confidence, cacc, crej, ctotal;
	int num_ev;

	public Account(int id, double rat){
		this.id = id;
		contributions = new ArrayList<Contribution>();
		evaluations = new ArrayList<Evaluation>();
		rating = rat;
		confidence = rat;
		num_ev = 0;
		cacc = 0;
		crej = 0;
		ctotal = 0;
		timeCreated = System.currentTimeMillis();
	}

	public double getTrustRating()
	{
		return rating; 
	}

	public void setTrustRating(double r)
	{
		rating = r; 
	}

	public double getTrustConfidence()
	{
		return confidence;
	}

	public ArrayList<Contribution> getContributions()
    {
        return contributions; 
    }

    public ArrayList<Evaluation> getEvaluations()
    {
        return evaluations; 
    }

	public void setTrustConfidence(double conf)
	{
		confidence = conf; 
	}


	public double getAccepted()
	{
		return cacc; 
	}

	public double getRejected()
	{
		return crej; 
	}

	public double getTotal()
	{
		return ctotal; 
	}

	public void incTotal(double temp)
	{
		ctotal += temp; 
	}

	public void incRejected(double temp)
	{
		crej += temp; 
	}

	public void incAccepted(double temp)
	{
		cacc += temp; 
	}

	public int getNumEv()
	{
		int temp = 0; 
		for(int i=0; i<contributions.size();i++){
			Contribution c = contributions.get(i);
			if(c==null) System.out.println("LOL WUUUT");
			try{	
				temp+=c.getNumEv();
			}
			catch(Exception e){
				e.printStackTrace();
				System.out.println("PLLLLLLSSSS");
			}
		}
		return temp;
	}
}