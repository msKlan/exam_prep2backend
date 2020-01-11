package facades;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Klan
 */
public class ApiFacade {
    
    private static ApiFacade instance;
    
    private ApiFacade(){}
    
    /**
     * 
     * @return the instance of this facade.
     */
    public static ApiFacade getApiFacade() {
        if (instance == null) {
            instance = new ApiFacade();
        }
        return instance;
    }

    //This fetch method returns a string with json format
    public String fetch(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json;charset=UTF-8");
            con.setRequestProperty("User-Agent", "server"); //remember if you are using SWAPI
            String jsonStr = "";
            try ( Scanner scan = new Scanner(con.getInputStream())) {
                while (scan.hasNext()) {
                    jsonStr += scan.nextLine();
                }
            }
            return jsonStr;
        } catch (IOException e) {
            return null;
        }
    }

    //This fetch method returns a string + specific with json format
    public String fetchWithSpec(String urlStr, String specific) {
        try {
            URL url = new URL(urlStr + specific);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json;charset=UTF-8");
            con.setRequestProperty("User-Agent", "server"); //remember if you are using SWAPI
            String jsonStr = "";
            try ( Scanner scan = new Scanner(con.getInputStream())) {
                while (scan.hasNext()) {
                    jsonStr += scan.nextLine();
                }
            }
            return jsonStr;
        } catch (IOException e) {
            return null;
        }
    }

    //This fetch method returns a list of strings with json format
    public List<String> fetch(String urlStr, List<String> specificList) {
        final ExecutorService executor = Executors.newCachedThreadPool();
        try {
            Queue<Future<String>> queue = new ArrayBlockingQueue(specificList.size());
            List<String> res = new ArrayList();
            for (String specifc : specificList) {
                Future<String> future = executor.submit(() -> {
                    return fetch(urlStr + specifc);
                });
                queue.add(future);
            }
            while (!queue.isEmpty()) {
                Future<String> specific = queue.poll();
                if (specific.isDone()) {
                    res.add(specific.get());
                } else {
                    queue.add(specific);
                }
            }
            return res;
        } catch (InterruptedException | ExecutionException e) {
            return null;
        } finally {
            executor.shutdown();
        }
    }

    
}
