import java.util.Random;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.CostMatrix;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.classifiers.meta.CostSensitiveClassifier;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Q1 {

	public static void main(String[] args) throws Exception {
		
		DataSource source = new DataSource("C:\\Users\\timje\\Desktop\\datasets\\bank.arff");
		Instances data = source.getDataSet();
		data.setClass(data.attribute("subscribed"));
		//System.out.println(data); //to check load the data
		
		Q1 filter = new Q1(); //filter
		System.out.println("Default parameter:");
		filter.doFilterClassification(data);
		
		//classifier
		IBk ibk = new IBk();
		J48 j48 = new J48();
		PART part = new PART();
		RandomForest rf = new RandomForest();
		
		
    	//output for IBk
		System.out.println("IBk");
		filter.doFilteredClassification(data, ibk);
		
		//output for PART
		System.out.println("PART");
		filter.doFilteredClassification(data, part);
		
		//output for J48
		System.out.println("J48");
		filter.doFilteredClassification(data, j48);
		
		
		//output for RandomForest
		System.out.println("RandomForest");
		filter.doFilteredClassification(data, rf);
		

	}
	public void doFilterClassification(Instances data) throws Exception
	{
		AttributeSelectedClassifier asc = new AttributeSelectedClassifier();
		asc.buildClassifier(data);
		Evaluation eval = new Evaluation(data);
		eval.crossValidateModel(asc, data, 10, new Random(1));
		System.out.println("ASC cross Evaluation: "+ eval.correct()/eval.numInstances()+"\r\n");
	}
	
	public void doFilteredClassification(Instances data, AbstractClassifier classifier) throws Exception
	{
		
		//Cost matrix
		String matlab = "[0.0 1.0; 5.0 0.0]";
		CostMatrix costMatrix = CostMatrix.parseMatlab(matlab);
		
		//CostSensitiveClassifier
		CostSensitiveClassifier csc = new CostSensitiveClassifier();
		csc.setClassifier(classifier);
		csc.setCostMatrix(costMatrix);
		csc.buildClassifier(data);
		
		//Output for the classification accuracy
		Evaluation eval = new Evaluation(data);
		eval.crossValidateModel(csc, data, 10, new Random(1));
		System.out.println("Classification accuracy: "+ eval.correct()/eval.numInstances());
		
		//Output for the total cost
		Evaluation eval2 = new Evaluation(data, costMatrix);
		eval2.crossValidateModel(csc, data, 10, new Random(1));
		System.out.println("Total Cost: "+ eval2.totalCost());
		
	}
	

}
