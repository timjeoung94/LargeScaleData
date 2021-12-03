import java.util.Random;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.HoeffdingTree;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.stemmers.LovinsStemmer;
import weka.core.stopwords.Rainbow;
import weka.core.tokenizers.AlphabeticTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class JavaQuestions {

	public static void main(String[] args) throws Exception {
		
		DataSource source = new DataSource("C:\\Users\\timje\\Desktop\\datasets\\News.arff");
		Instances data = source.getDataSet();
		
		//Sets the class index 
		data.setClass(data.attribute("@@class@@"));

		JavaQuestions jq = new JavaQuestions();
		
		//Create a classifier
		J48 j48 = new J48();
		SMO smo = new SMO();
		IBk ibk = new IBk();
		HoeffdingTree hft = new HoeffdingTree();
		
		//searcher
		Ranker ranking = new Ranker();
		ranking.setNumToSelect(100);  //Selecting number of attributes
		ranking.setThreshold(0); //setting threshold
		
		//evaluator
		InfoGainAttributeEval info = new InfoGainAttributeEval();
		
//		//Output for J48
		long startJ48 = System.nanoTime();
		System.out.println("J48");
		jq.doFilteredClassification(data, j48,info, ranking);
		long endJ48 = System.nanoTime();
		double elapsedJ48 = endJ48 - startJ48;
		elapsedJ48 = elapsedJ48/ 1000000000;
		System.out.println("Executing J48: " + elapsedJ48 + " seconds\n");
		
//		//Output for SMO
		long startSMO = System.nanoTime();
		System.out.println("SMO");
		jq.doFilteredClassification(data, smo, info, ranking);
		long endSMO = System.nanoTime();
		double elapsedSMO = endSMO - startSMO;
		elapsedSMO = elapsedSMO/ 1000000000;
		System.out.println("Executing SMO: " + elapsedSMO + " seconds\n");
		
//		//Output for IBk
		long startIBk = System.nanoTime();
		System.out.println("IBk");
		jq.doFilteredClassification(data, ibk, info, ranking);
		long endIBk = System.nanoTime();
		double elapsedIBk = endIBk - startIBk;
		elapsedIBk = elapsedIBk/ 1000000000;
		System.out.println("Executing IBk: " + elapsedIBk + " seconds\n");
		
		//Output for HoeffdingTree
		long startHFT = System.nanoTime();
		System.out.println("HoeffdingTree");
		jq.doFilteredClassification(data, hft, info, ranking);
		long endHFT = System.nanoTime();
		double elapsedHFT = endHFT - startHFT;
		elapsedHFT = elapsedHFT/ 1000000000;
		System.out.println("Executing HoeffdingTree: " + elapsedHFT + " seconds\n");
		
		
		
	}
	public void doFilteredClassification(Instances data, AbstractClassifier classifier, ASEvaluation evaluator, ASSearch searcher) throws Exception
	{
		
		
		// Create a StringToWordVector filter
		StringToWordVector swFilter = new StringToWordVector();
		
		//Specify range of attributes to act on
		swFilter.setAttributeIndices("first-last"); 
		
		// Configure the filter
		swFilter.setIDFTransform(true);
		swFilter.setNormalizeDocLength(new SelectedTag(StringToWordVector.FILTER_NORMALIZE_ALL,StringToWordVector.TAGS_FILTER));
		swFilter.setTFTransform(true);
		swFilter.setOutputWordCounts(true);
		swFilter.setStemmer(new LovinsStemmer());
		swFilter.setStopwordsHandler(new Rainbow());
		swFilter.setTokenizer(new AlphabeticTokenizer());
		swFilter.setWordsToKeep(100);
		
		
		//Create a FilteredClassifier object
		FilteredClassifier fclassifier = new FilteredClassifier();
		//Set the filter to the filtered classifier
		fclassifier.setFilter(swFilter);
		fclassifier.setClassifier(classifier);
		fclassifier.buildClassifier(data);
	

		//Evaluation
		Evaluation eval = new Evaluation(data);
		eval.crossValidateModel(fclassifier, data, 10, new Random(1));
		System.out.println("Correctly classified instances: "+ (eval.numInstances()-eval.incorrect())+ "\n"+ "Accuracy:" + eval.correct()/eval.numInstances()); 


	}

}
	
