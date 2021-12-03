import java.io.IOException;

import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPGrowth;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPMax;
import ca.pfv.spmf.tools.dataset_converter.TransactionDatabaseConverter;
import ca.pfv.spmf.tools.resultConverter.ResultConverter;

public class Q2andQ3 {

	public static void main(String[] args) throws IOException {
		
		//Specify input file for the original input file
		String yes_input_file = "C:\\Users\\timje\\Desktop\\datasets\\bank_yes.arff";
		String no_input_file = "C:\\Users\\timje\\Desktop\\datasets\\bank_no.arff";
		
		//Specify a file for the converted file
		String yes_input_converted = "C:\\Users\\timje\\Desktop\\datasets\\yes_converted_bank.txt";
		String no_input_converted = "C:\\Users\\timje\\Desktop\\datasets\\no_converted_bank.txt";
		
		//Create an object of TransactionDatabaseConverter
		TransactionDatabaseConverter converter = new TransactionDatabaseConverter();
		
		// Convert the ARFF file to text file
		converter.convertARFFandReturnMap(yes_input_file, yes_input_converted, Integer.MAX_VALUE);
		converter.convertARFFandReturnMap(no_input_file, no_input_converted, Integer.MAX_VALUE);
		
		//Specify output files
		String yes_output_fp_Fpt = "C:\\Users\\timje\\Desktop\\datasets\\output\\yes_bank_output_FPT.txt";
		String no_output_fp_Fpt = "C:\\Users\\timje\\Desktop\\datasets\\output\\no_bank_output_FPT.txt";
		
		// Create objects of pattern mining algorithms
		AlgoFPGrowth algo_FPGrowth = new AlgoFPGrowth();
		
		// Set a maximum size for patterns
		algo_FPGrowth.setMaximumPatternLength(3);
		
		// Set a minimum size for patterns
		algo_FPGrowth.setMinimumPatternLength(3);
		
		// Specify minimum support
		double minsup = 0.43;
		
		// Run algorithms to generate patterns
		algo_FPGrowth.runAlgorithm(yes_input_converted, yes_output_fp_Fpt, minsup);
		System.out.println("Yes");
		algo_FPGrowth.printStats();
		
		algo_FPGrowth.runAlgorithm(no_input_converted, no_output_fp_Fpt, minsup);
		System.out.println("No");
		algo_FPGrowth.printStats();
		
		// Specify the final output files
		String yes_final_output_fp_fpt ="C:\\Users\\timje\\Desktop\\datasets\\output\\yes_final_bank_output_FPT.txt";
		String no_final_output_fp_fpt ="C:\\Users\\timje\\Desktop\\datasets\\output\\no_final_bank_output_FPT.txt";
		
		//Convert outputs to include the original names for the items
		ResultConverter output_converter = new ResultConverter();
		output_converter.convert(yes_input_converted, yes_output_fp_Fpt, yes_final_output_fp_fpt, null);
		output_converter.convert(no_input_converted, no_output_fp_Fpt, no_final_output_fp_fpt, null);
		
		// call method doFpMax() to generate maximal patterns
		Q2andQ3 pattern_mining = new Q2andQ3();
		pattern_mining.doMaxFp(yes_input_converted, no_input_converted, yes_output_fp_Fpt,no_output_fp_Fpt, minsup);

		

	}
	//Q3 Generating top 5 most frequent maximum patterns (Maximal Pattern Mining)
	
	public void doMaxFp(String yes_input_converted, String no_input_converted, String yes_output_fp_Fpt, String no_output_fp_Fpt, double minsup) {
		
		// Specify output files
		String yes_max_output = yes_output_fp_Fpt + "fpMax.txt";
		String final_yes_max_output = yes_output_fp_Fpt + "final_fpMax.txt";
		
		String no_max_output = no_output_fp_Fpt + "fpMax.txt";
		String final_no_max_output = no_output_fp_Fpt + "final_fpMax.txt";
		
		// Specify minimum support for maximum patterns
		double minsupp = 0.4705;
		
		try {
		// Object of AlgoFPMax
		AlgoFPMax algo_FpMax = new AlgoFPMax();
		algo_FpMax.runAlgorithm(yes_input_converted, yes_max_output, minsupp);
		System.out.println("Yes");
		algo_FpMax.printStats();
		
		algo_FpMax.runAlgorithm(no_input_converted, no_max_output, minsupp);
		System.out.println("No");
		algo_FpMax.printStats();
		
		//Convert outputs to include item names
		ResultConverter output_converter = new ResultConverter();
		output_converter.convert(yes_input_converted, yes_max_output, final_yes_max_output, null);
		output_converter.convert(no_input_converted, no_max_output, final_no_max_output, null);
		
		} catch (IOException e) {
		e.printStackTrace();
		}
		}


}
