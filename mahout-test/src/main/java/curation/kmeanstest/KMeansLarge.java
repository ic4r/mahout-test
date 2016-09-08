package curation.kmeanstest;

import java.util.Random;
import java.util.ArrayList;

public class KMeansLarge {
	
    private static final int NUM_CLUSTERS = 5;      // Total clusters.
    private static final int TOTAL_DATA = 100;      // Total data points.
    private static final double MIN_COORDINATE = 0.0;
    private static final double MAX_COORDINATE = 500.0;          // This would make a 500x500 grid
    private static final double BIG_NUMBER = Math.pow(10, 10);   // some big number that's sure to be larger than our data range.
    
    private static ArrayList<Data> dataSet = new ArrayList<Data>();
    private static ArrayList<Centroid> centroids = new ArrayList<Centroid>();
    
    private static void initializeCentroids()
    {
        //Initialize the centroids with random values.
        System.out.println("Centroids initialized at:");

        for(int i = 0; i < NUM_CLUSTERS; i++)
        {
            centroids.add(new Centroid(getRandomDouble(MIN_COORDINATE, MAX_COORDINATE), getRandomDouble(MIN_COORDINATE, MAX_COORDINATE)));
            System.out.println("     (" + centroids.get(i).X() + ", " + centroids.get(i).Y() + ")");
        }
        System.out.print("\n");
        
        return;
    }
    
    private static void kMeanCluster()
    {
        double minimum = BIG_NUMBER;      // The minimum value to beat. 
        double distance = 0.0;            // The current minimum value.
        int cluster = 0;
        boolean isStillMoving = true;
        Data newData = null;
        
        // Add in new data, one at a time, recalculating centroids with each new one. 
        while(dataSet.size() < TOTAL_DATA)
        {
            newData = new Data(getRandomDouble(MIN_COORDINATE, MAX_COORDINATE), getRandomDouble(MIN_COORDINATE, MAX_COORDINATE));
            dataSet.add(newData);
            minimum = BIG_NUMBER;
            for(int i = 0; i < NUM_CLUSTERS; i++)
            {
                distance = dist(newData, centroids.get(i));
                if(distance < minimum){
                    minimum = distance;
                    cluster = i;
                }
            }
            newData.cluster(cluster);
            
            // calculate new centroids.
            for(int i = 0; i < NUM_CLUSTERS; i++)
            {
                int totalX = 0;
                int totalY = 0;
                int totalInCluster = 0;
                for(int j = 0; j < dataSet.size(); j++)
                {
                    if(dataSet.get(j).cluster() == i){
                        totalX += dataSet.get(j).X();
                        totalY += dataSet.get(j).Y();
                        totalInCluster++;
                    }
                }
                if(totalInCluster > 0){
                    centroids.get(i).X(totalX / totalInCluster);
                    centroids.get(i).Y(totalY / totalInCluster);
                }
            }
        }
        
        // Now, keep shifting centroids until equilibrium occurs.
        while(isStillMoving)
        {
            // calculate new centroids.
            for(int i = 0; i < NUM_CLUSTERS; i++)
            {
                int totalX = 0;
                int totalY = 0;
                int totalInCluster = 0;
                for(int j = 0; j < dataSet.size(); j++)
                {
                    if(dataSet.get(j).cluster() == i){
                        totalX += dataSet.get(j).X();
                        totalY += dataSet.get(j).Y();
                        totalInCluster++;
                    }
                }
                if(totalInCluster > 0){
                    centroids.get(i).X(totalX / totalInCluster);
                    centroids.get(i).Y(totalY / totalInCluster);
                }
            }
            
            // Assign all data to the new centroids
            isStillMoving = false;
            
            for(int i = 0; i < dataSet.size(); i++)
            {
                Data tempData = dataSet.get(i);
                minimum = BIG_NUMBER;
                for(int j = 0; j < NUM_CLUSTERS; j++)
                {
                    distance = dist(tempData, centroids.get(j));
                    if(distance < minimum){
                        minimum = distance;
                        cluster = j;
                    }
                }
                tempData.cluster(cluster);
                if(tempData.cluster() != cluster){
                    tempData.cluster(cluster);
                    isStillMoving = true;
                }
            }
        }
        return;
    }
    
    private static double dist(Data d, Centroid c)
    {
        //Calculate Euclidean distance.
        return Math.sqrt(Math.pow((c.Y() - d.Y()), 2) + Math.pow((c.X() - d.X()), 2));
    }
    
    private static double getRandomDouble(double low, double high)
    {
        return Math.round((high - low) * new Random().nextDouble() + low);
    }
    
    
    public static void main(String[] args)
    {
        initializeCentroids();
        kMeanCluster();
        
        // Print out clustering results.
        for(int i = 0; i < NUM_CLUSTERS; i++)
        {
            System.out.println("Cluster " + i + " includes:");
            for(int j = 0; j < TOTAL_DATA; j++)
            {
                if(dataSet.get(j).cluster() == i){
                    System.out.println("     (" + dataSet.get(j).X() + ", " + dataSet.get(j).Y() + ")");
                }
            } // j
            System.out.println();
        } // i
        
        // Print out centroid results.
        System.out.println("Centroids finalized at:");
        for(int i = 0; i < NUM_CLUSTERS; i++)
        {
            System.out.println("     (" + centroids.get(i).X() + ", " + centroids.get(i).Y() + ")");
        } // i
        System.out.print("\n");
        return;
    }
}