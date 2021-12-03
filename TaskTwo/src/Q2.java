import java.util.Random;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GainRatioAttributeEval;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.OneRAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class Q2  {

	public static void main(String[] args) throws Exception {
		
		//loading the data bank.arff
		DataSource source = new DataSource("C:\\Users\\timje\\Desktop\\datasets\\bank.arff");
		
		Instances data = source.getDataSet();
		data.setClass(data.attribute("subscribed"));
		
		Q2 filter = new Q2(); //filter
		System.out.println("Default parameter correctly classified instances");
		filter.filterClass(data);
		
						
		//classifier
		J48 j48 = new J48();
		PART part = new PART();
		RandomForest rf = new RandomForest();
		
		//searcher
		Ranker ranking = new Ranker();
		
		//Selecting number of attributes from 4 to 8
		ranking.setNumToSelect(4); 
		
		//output for PART with selected number
		System.out.println("PART:");
		filter.filteredClass(data, part, new GainRatioAttributeEval(), ranking);
		
		//output for J48 selected number
		System.out.println("J48:");
		filter.filteredClass(data, j48, new GainRatioAttributeEval(), ranking);
		
		//output for RandomForest selected number
		System.out.println("RandomForest:");
		filter.filteredClass(data, rf, new GainRatioAttributeEval(), ranking);

			
	}
	
	public void filterClass(Instances data) throws Exception {
		
		AttributeSelectedClassifier asc = new AttributeSelectedClassifier();
		asc.buildClassifier(data);
		Evaluation eval = new Evaluation(data);
		eval.crossValidateModel(asc, data, 10, new Random(1));
		System.out.println("ASC cross Evaluation: "+ eval.correct()/eval.numInstances() + "\r\n");
	}
	
	public void filteredClass(Instances data, AbstractClassifier classifier, ASEvaluation evaluator, ASSearch searcher) throws Exception {
		
		//AttributeSelectedClassifier
				AttributeSelectedClassifier asc = new AttributeSelectedClassifier();
				asc.setClassifier(classifier);
				asc.setEvaluator(evaluator);
				asc.setSearch(searcher);
				asc.buildClassifier(data);
		
				//Outputting the correctly classified instances and the accuracy
				Evaluation eval = new Evaluation(data);
				eval.crossValidateModel(asc, data, 10, new Random(1));
				System.out.println("Correctly classified instances: "+ (eval.numInstances()-eval.incorrect())+ "\n"+ "Accuracy:" + eval.correct()/eval.numInstances());
	}

}