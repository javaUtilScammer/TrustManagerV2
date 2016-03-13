import java.util.*;

public class RankTrustScorer extends Scorer{
	final double eps = 1e-6;

	public RankTrustScorer(ClientInterface i){
		super(i);
	}

	public void calculateScore(Evaluation ev, Contribution cont){
		int Nc = intrface.contributionMap.size(), Na = intrface.accountMap.size();
		ArrayList<Account> accList = new ArrayList(intrface.accountMap.values());
		ArrayList<Contribution> conList = new ArrayList(intrface.contributionMap.values());
    	double[][] M = new double[Nc][Na];
    	for(int i=0; i<Nc; i++){
    		Contribution con = conList.get(i);
    		ArrayList<Evaluation> evals = con.getEvaluations();
    		int n = evals.size();
    		if(n==0) M[i][accList.indexOf(con.getContributor())] = 1.0;
    		for(int o=0; o<n; o++){
    			Evaluation eva = evals.get(o);
                double rating = eva.getRating();
    			// double rating = eva.getRating()/rating_scale;
    			Account acc = eva.getCreatedBy();
    			int ind = accList.indexOf(acc);
    			M[i][ind] = (rating/n);
    		}
    	}
    	// System.out.println("PAGERANK");
    	// System.out.println(Nc+" "+Na);
    	// for(int i=0; i<Nc; i++) System.out.println(Arrays.toString(M[i]));

    	// System.out.println();
    	double[][] T = new double[Na][Na];
    	for(int i=0; i<Na; i++){
    		Account acc = accList.get(i);
    		ArrayList<Contribution> conts = acc.getContributions();
    		int n = conts.size();
    		if(n==0) T[i][i] = 1.0;
    		for(int o=0; o<n; o++){
    			Contribution con = conts.get(o);
    			int ind = conList.indexOf(con);
    			for(int u=0; u<Na; u++) T[i][u] += M[ind][u];
    		}
    		if(n!=0) for(int o=0; o<Na; o++) T[i][o]/=n;
    	}
    	// for(int i=0; i<Na; i++) System.out.println(Arrays.toString(T[i]));
    	double[] vec = new double[Na];
    	for(int i=0; i<Na; i++) vec[i] = accList.get(i).getTrustRating();
    	for(int i=0; i<30; i++){
    		double[] vec2 = matrixMult(T,vec);
    		// System.out.println(i+" "+Arrays.toString(vec2));
    		vec = vec2;	
    	}
    	for(int i=0; i<Na; i++){
    		Account acc = accList.get(i);
    		acc.setTrustRating(vec[i]);
    	}
        double[] vec3 = matrixMult(M,vec);
        for(int i=0; i<Nc; i++){
            Contribution con = conList.get(i);
            con.setContributionScore(vec3[i]);
        }
	}

	public int adminAccept(Contribution cont){
		try{
            int Nc = intrface.contributionMap.size(), Na = intrface.accountMap.size();
			ArrayList<Account> accList = new ArrayList(intrface.accountMap.values());
			ArrayList<Contribution> conList = new ArrayList(intrface.contributionMap.values());
            double[][] M = new double[Nc][Na];
            // System.out.println("PLSSSSSS1");
            for(int i=0; i<Nc; i++){
                Contribution con = conList.get(i);
                ArrayList<Evaluation> evals = con.getEvaluations();
                int n = evals.size();
                // System.out.println("CHECK "+i+" "+n);
                if(n==0) M[i][accList.indexOf(con.getContributor())] = 1.0;
                for(int o=0; o<n; o++){
                    Evaluation eva = evals.get(o);
                    double rating = eva.getRating();
                    // double rating = eva.getRating()/rating_scale;
                    Account acc = eva.getCreatedBy();
                    int ind = accList.indexOf(acc);
                    // System.out.println("CHECK2 "+o+" "+ind);
                    M[i][ind] = (rating)/n;
                    // M[i][ind] = (rating*acc.getTrustRating())/n;
                }
            }
            // System.out.println("PAGERANK");
            // System.out.println(Nc+" "+Na);
            // for(int i=0; i<Nc; i++) System.out.println(Arrays.toString(M[i]));

            // System.out.println();
            // System.out.println("PLSSSSSS");
            double[][] T = new double[Na+1][Na+1];
            for(int i=0; i<Na; i++){
                Account acc = accList.get(i);
                ArrayList<Contribution> conts = acc.getContributions();
                int n = conts.size();
                if(n==0) T[i][i] = 1.0;
                for(int o=0; o<n; o++){
                    Contribution con = conts.get(o);
                    int ind = conList.indexOf(con);
                    for(int u=0; u<Na; u++) T[i][u] += M[ind][u];
                }
                if(n!=0) for(int o=0; o<Na; o++) T[i][o]/=n;
            }
            T[conList.indexOf(cont)][Na] = 1.0;
            double[] vec = new double[Na+1];
            for(int i=0; i<Na; i++) vec[i] = accList.get(i).getTrustRating();
            vec[Na] = rating_scale;
            for(int i=0; i<30; i++){
                double[] vec2 = matrixMult(T,vec);
                // out.println(i+" "+Arrays.toString(vec2));
                vec = vec2; 
            }
            for(int i=0; i<Na; i++){
                Account acc = accList.get(i);
                acc.setTrustRating(vec[i]);
            }
        }
        catch(Exception e){e.printStackTrace();return 0;}
        return 1;
	}

	public double computeInitialScore(Contribution cont){
		return 0.5;
	}

	public double[] matrixMult(double[][] mat, double[] vec){
		int n = mat.length;
        // System.out.println(n);
        double[] ret = new double[n];
        for(int i=0; i<n; i++){
            double sum = 0;
            for(int o=0; o<vec.length; o++)sum+=(mat[i][o]*vec[o]);
            ret[i] = sum;
        }
        return ret;
	}
}