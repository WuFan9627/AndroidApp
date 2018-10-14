package bad.twitterfriendschannel.gson.response;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.Function;

import io.fabric.sdk.android.services.concurrency.Dependency;

/**
 * Created by Leo on 5/29/2017.
 * Edited by Fan on 4/24/2018
 */

public class Channels {
    private String SubscriptionsDatasetName;
    private String DataverseName;
    private String Duration;
    private String ResultsDatasetName;
    private ArrayList<String> Function;
   // private String Function;
    private String ChannelName;
    private ArrayList<ArrayList<ArrayList<String>>> DependencyList;

    public Channels( String dataverseName, String channelName, String subscriptionsDatasetName,String resultsDatasetName, ArrayList<String> function,String duration,ArrayList<ArrayList<ArrayList<String>>> dependencyList ) {
        SubscriptionsDatasetName = subscriptionsDatasetName;
        DataverseName = dataverseName;
        Duration = duration;
        ResultsDatasetName = resultsDatasetName;
        for(int i = 0; i <function.size();++i){
            Function.set(i,function.get(i));
        }
        //Function = function;
        ChannelName = channelName;

        for (int i = 0; i < dependencyList.size(); ++i) {
            for(int j = 0; j < dependencyList.get(i).size(); ++j) {
                for(int k = 0; k< dependencyList.get(i).get(j).size(); ++k) {
                    DependencyList.set(k,dependencyList.get(k));
                    DependencyList.set(j,dependencyList.get(j));
                    DependencyList.set(i,dependencyList.get(i));
                }
            }
        }


    }

    public String getSubscriptionsDatasetName() {
        return SubscriptionsDatasetName;
    }

    public void setSubscriptionsDatasetName(String subscriptionsDatasetName) {
        SubscriptionsDatasetName = subscriptionsDatasetName;
    }

    public String getDataverseName() {
        return DataverseName;
    }

    public void setDataverseName(String dataverseName) {
        DataverseName = dataverseName;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getResultsDatasetName() {
        return ResultsDatasetName;
    }

    public void setResultsDatasetName(String resultsDatasetName) {
        ResultsDatasetName = resultsDatasetName;
    }

    public ArrayList<String> getFunction() {return Function;}

    public void setFunction(ArrayList<String> function) {

        for (int i = 0; i < function.size(); ++i) {
            Function.set(i,function.get(i));
        }
    }
   /*public String getFunction() {return Function;}
   public void setFunction(String function){
       Function = function;
   } */
    public String getChannelName() {
        return ChannelName;
    }

    public void setChannelName(String channelName) {
        ChannelName = channelName;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getDependency() {
        return DependencyList;
    }

    public void setDependencyList (ArrayList<ArrayList<ArrayList<String>>> dependencyList) {
        for (int i = 0; i < dependencyList.size(); ++i) {

            for(int j = 0; j < dependencyList.get(i).size(); ++j) {
                for(int k = 0; k< dependencyList.get(i).get(j).size(); ++k) {
                    DependencyList.set(k,dependencyList.get(k));
                    DependencyList.set(j,dependencyList.get(j));
                    DependencyList.set(i,dependencyList.get(i));
                }
            }
        }
    }


}
