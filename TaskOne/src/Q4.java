import java.io.IOException;

import ca.pfv.spmf.algorithms.frequentpatterns.apriori_close.AlgoAprioriClose;
import ca.pfv.spmf.algorithms.frequentpatterns.charm.AlgoCharm_Bitset;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPClose;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPGrowth;
import ca.pfv.spmf.input.transaction_database_list_integers.TransactionDatabase;
import ca.pfv.spmf.tools.dataset_converter.TransactionDatabaseConverter;

public class Q4 {

	public static void main(String[] args) throws IOException {
		
	//Specify input file for the original input file
	String input_file = "C:\\Users\\timje\\Desktop\\datasets\\bank.arff";
		
	//Specify output files	
	String output_fp_Fpt = "C:\\Users\\timje\\Desktop\\datasets\\output\\Q4\\fp_Fpt.txt";
	String output_fcp_Apriori = "C:\\Users\\timje\\Desktop\\datasets\\output\\Q4\\fcp_Apriori.txt";
	String output_fcp_Fpt = "C:\\Users\\timje\\Desktop\\datasets\\output\\Q4\\fcp_Fpt.txt";
	String output_fcp_Charm = "C:\\Users\\timje\\Desktop\\datasets\\output\\Q4\\fcp_Charm.txt";
	
	//Specify a file for the converted file
	String input_converted = "C:\\Users\\timje\\Desktop\\datasets\\converted_bank.txt";
		
	//Create an object of TransactionDatabaseConverter
	TransactionDatabaseConverter converter = new TransactionDatabaseConverter();
		
	// Convert the ARFF file to text file
	converter.convertARFFandReturnMap(input_file, input_converted, Integer.MAX_VALUE);
	
	// Create objects of pattern mining algorithms
	AlgoFPGrowth algo_FPGrowth = new AlgoFPGrowth();
	AlgoAprioriClose algo_AprioriClose = new AlgoAprioriClose();
	AlgoFPClose algo_FCP_Growth = new AlgoFPClose();
	AlgoCharm_Bitset algo_FCP_Charm = new AlgoCharm_Bitset();
	
	//Specify minimum support
	double minsup = 0.5;
	
	//Run algorithms to generate patterns
	algo_FPGrowth.runAlgorithm(input_converted, output_fp_Fpt, minsup);
	algo_FPGrowth.printStats();
	algo_AprioriClose.runAlgorithm(minsup, input_converted, output_fcp_Apriori);
	algo_AprioriClose.printStats();
	algo_FCP_Growth.runAlgorithm(input_converted, output_fcp_Fpt, minsup);
	algo_FCP_Growth.printStats();
	
	TransactionDatabase tdb = new TransactionDatabase();
	tdb.loadFile(input_converted);
	algo_FCP_Charm.runAlgorithm(output_fcp_Charm, tdb, minsup, false, 10000);
	algo_FCP_Charm.printStats();


	}

}
