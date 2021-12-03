import java.io.IOException;

import ca.pfv.spmf.algorithms.frequentpatterns.apriori.AlgoApriori;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPGrowth;
import ca.pfv.spmf.tools.dataset_converter.TransactionDatabaseConverter;
import ca.pfv.spmf.tools.resultConverter.ResultConverter;

public class Q1 {

	public static void main(String[] args) throws IOException {
	
	//Specify input file for the original input file
	String input_file = "C:\\Users\\timje\\Desktop\\datasets\\bank.arff";
	
	//Specify output files
	String output_fp_Apriori = "C:\\Users\\timje\\Desktop\\datasets\\output\\bank_output_AP.txt";
	String output_fp_Fpt = "C:\\Users\\timje\\Desktop\\datasets\\output\\bank_output_FPT.txt";
	
	//Specify a file for the converted file
	String input_converted = "C:\\Users\\timje\\Desktop\\datasets\\converted_bank.txt";
	
	//Create an object of TransactionDatabaseConverter
	TransactionDatabaseConverter converter = new TransactionDatabaseConverter();
	
	// Convert the ARFF file to text file
	converter.convertARFFandReturnMap(input_file, input_converted, Integer.MAX_VALUE);
	
	// Create objects of pattern mining algorithms
	AlgoApriori algo_Apri = new AlgoApriori();
	AlgoFPGrowth algo_FPGrowth = new AlgoFPGrowth();
	
	// Specify minimum support
	double minsup = 0.5;
	
	// Run algorithms to generate patterns
	algo_Apri.runAlgorithm(minsup, input_converted, output_fp_Apriori);
	algo_Apri.printStats();
	algo_FPGrowth.runAlgorithm(input_converted, output_fp_Fpt, minsup);
	algo_FPGrowth.printStats();
	
	// Specify the final output files
	String final_output_fp_apri ="C:\\Users\\timje\\Desktop\\datasets\\output\\final_bank_output_AP.txt";
	String final_output_fp_fpt ="C:\\Users\\timje\\Desktop\\datasets\\output\\final_bank_output_FPT.txt";
	
	//Convert outputs to include the original names for the items
	ResultConverter output_converter = new ResultConverter();
	output_converter.convert(input_converted, output_fp_Apriori, final_output_fp_apri, null);
	output_converter.convert(input_converted, output_fp_Fpt, final_output_fp_fpt, null);




	}

}
