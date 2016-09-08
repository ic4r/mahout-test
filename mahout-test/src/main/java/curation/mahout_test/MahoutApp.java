package curation.mahout_test;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.common.distance.CosineDistanceMeasure;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;

/**
 * Hello world!
 *
 */
public class MahoutApp 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        
//        DistanceMeasure measure = new CosineDistanceMeasure();
        
     // now run the KMeansDriver job
//        KMeansDriver.runJob("testdata", "output/clusters-0", "output", CosineDistanceMeasure.class.getName(), "0.001", "10", true);
        
        
        
        DataModel model = new FileDataModel(new File("data/dataset.csv"));
        
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
        
        UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
        
        List<RecommendedItem> recommendations = recommender.recommend(2, 3);
        for (RecommendedItem recommendation : recommendations) {
          System.out.println(recommendation);
        }
        
        
    }
}
