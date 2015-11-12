/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;



/**
 *
 * @author yash
 */
import static com.sun.org.apache.xalan.internal.lib.ExsltDynamic.map;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jdk.nashorn.internal.objects.NativeArray.map;
import static jdk.nashorn.internal.objects.NativeDebug.map;
public class populate {

    public populate() {
        
        System.out.println("-------- Oracle JDBC Connection Testing ------");
        System.out.println("-------- Oracle JDBC Connection Testing ------");
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;

        }

        System.out.println("Oracle JDBC Driver Registered!");

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:cdb1", "system",
                    "oracle");

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }

        if (connection != null) {
            System.out.println("Database Connection Successfull!");
//            populate_users(connection,"/Users/yash/Documents/workspace/HW 3/data/yelp_user");
//            populate_business(connection, "");
//            populate_reviews(connection , "");
            populate_checkin(connection , "");
            
            
        } else {
            System.out.println("Failed to make connection!");
        }
    }
    private void populate_checkin(Connection connection, String string) {
    	 String fileName = "/Users/yash/Documents/workspace/HW 3/data/yelp_checkin.json";
         Path path = Paths.get(fileName);
         Scanner scanner = null;
         try {
             scanner = new Scanner(path);
         
         } catch (IOException e) {
             e.printStackTrace();
         }

         //read file line by line
         scanner.useDelimiter("\n");
         int i =0;
         while(scanner.hasNext()){
        	
        	 if(i<20) {
                 JSONParser parser = new JSONParser();
                 String s = (String)scanner.next();
                 s = s.replace("'", "''");
                 
                 JSONObject obj;	
                 
                 
					try {
						obj = (JSONObject)parser.parse(s);
						Map checkin_info = (Map) obj.get("checkin_info");
						Set keys = checkin_info.keySet();
		                   Object[] days = keys.toArray();
		                   Statement statement = connection.createStatement();
		                   for(int j =0 ; j<days.length ; ++j){
//	                        
	                        String thiskey = days[j].toString();
	                        String q3 = "insert into yelp_checkin values ('"+obj.get("business_id")+"','"+obj.get("type")+"','"+thiskey+"','"+checkin_info.get(thiskey)+"')";
//	                        
	                         statement.executeUpdate(q3);
	                        
	                     }
						
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
        		 ++i;
        	 }
        	 else{
        		 break;
        	 }
         }
	}
	private void populate_reviews(Connection connection, String string) {
		
    	  String fileName = "/Users/yash/Documents/workspace/HW 3/data/yelp_review.json";
          Path path = Paths.get(fileName);
          Scanner scanner = null;
          try {
              scanner = new Scanner(path);
             
              
          } catch (IOException e) {
              e.printStackTrace();
          }

          //read file line by line
          scanner.useDelimiter("\n");
          int i =0;
          while(scanner.hasNext()){
              if(i<20) {
                 JSONParser parser = new JSONParser();
                 String s = (String)scanner.next();
                 s = s.replace("'", "''");
                 
                 JSONObject obj;	
                 
                      try {
						obj = (JSONObject)parser.parse(s);
						String text = (String) obj.get("text");
						text = text.replace("\n", "");
						
						Map votes =  (Map) obj.get("votes");
						String query = "insert into yelp_reviews values ("+votes.get("useful")+","+ votes.get("funny")+","+ votes.get("cool")+",'"+obj.get("user_id")+"','"+obj.get("review_id")+"',"+obj.get("stars")+",'"+obj.get("date")+"','"+text+"','"+obj.get("type")+"','"+obj.get("business_id")+"')";
						System.out.println(query); 
						Statement statement = connection.createStatement();
	                    statement.executeUpdate(query);
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                      ++i;
                 
              }
              
          }
	}
	void populate_users(Connection connection,String filename){
         String fileName = "/Users/yash/Documents/workspace/HW 3/data/yelp_user.json";
        Path path = Paths.get(fileName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(path);
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        //read file line by line
        scanner.useDelimiter("\n");
        int i =0;
        while(scanner.hasNext()){
            if(i<500) {
               JSONParser parser = new JSONParser();
      String s = (String)scanner.next();
	JSONObject obj;	
                try {
                    obj = (JSONObject)parser.parse(s);
                    Map votes =  (Map) obj.get("votes");
                    ArrayList friends;
                   friends = (ArrayList) obj.get("friends");
                   Map compliments = (Map) obj.get("compliments");
//                    
                     String query = "INSERT INTO YELP_USERS (user_id,yelping_since,name,fans,average_stars,type,review_count,VOTES_FUNNY,VOTES_COOL,VOTES_USEFUL) VALUES('"+obj.get("user_id")+"','"+obj.get("yelping_since")+"','"+obj.get("name")+"',"+obj.get("fans")+","+obj.get("average_stars")+",'"+obj.get("type")+"',"+obj.get("review_count")+","+votes.get("funny")+","+votes.get("cool")+","+votes.get("useful")+")";
                     System.out.println(query);     
                     Statement statement = connection.createStatement();
                     statement.executeUpdate(query);
                         for(int j =0 ; j<friends.size() ; ++j){
                             String q2 = "insert into users_friends values ('"+obj.get("user_id")+"','"+friends.get(j)+"')";
                             System.out.println(q2);
                              statement.executeUpdate(q2);
                         }
                        Set keys = compliments.keySet();
                   Object[] keys1 = keys.toArray();
                         for(int j =0 ; j<keys1.length ; ++j){
//                             String q2 = "insert into users_friends values ("+obj.get("user_id")+","+compliments.get(j)+")";
                            String thiskey = keys1[j].toString();
                            long val = (long) compliments.get(thiskey);
                            String q3 = "insert into users_compliments values ('"+obj.get("user_id")+"','"+thiskey+"',"+ val +")";
                            System.out.println(q3);
                             statement.executeUpdate(q3);
                         }
                } catch (ParseException ex) {
                    Logger.getLogger(populate.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(populate.class.getName()).log(Level.SEVERE, null, ex);
                }
     
     
                
                i++;
            }
            else
                break;
        }



        scanner.close();

    }
    void populate_business(Connection connection,String filename){
        String fileName = "/Users/yash/Documents/workspace/HW 3/data/yelp_business.json";
       Path path = Paths.get(fileName);
       Scanner scanner = null;
       try {
           scanner = new Scanner(path);
          
           
       } catch (IOException e) {
           e.printStackTrace();
       }

       //read file line by line
       scanner.useDelimiter("\n");
       int i =0;
       while(scanner.hasNext()){
           if(i<500) {
              JSONParser parser = new JSONParser();
              String s = (String)scanner.next();
              s = s.replace("'", "''");
              
              JSONObject obj;	
              
                  try {
					obj = (JSONObject)parser.parse(s);
					String add = (String) obj.get("full_address");
					add = add.replace("\n", "");
					//insertion into business table
					String query = "insert into yelp_business values ('"+obj.get("business_id")+"','"+add+"','"+obj.get("open")+"','"+obj.get("city")+"','"+obj.get("state")+"','"+obj.get("latitude")+"','"+obj.get("longitude")+"','"+obj.get("review_count")+"','"+obj.get("name")+"','"+obj.get("stars")+"','"+obj.get("type")+"')";
					Statement statement = connection.createStatement();
					System.out.println(query);
					statement.executeUpdate(query);
					//end
					
					//inserting into hours table
					Map hours = (Map) obj.get("hours");
					 Set keys = hours.keySet();
	                   Object[] days = keys.toArray();
	                   for(int j =0 ; j<days.length ; ++j){
//                        
                        String thiskey = days[j].toString();
                        Map timings =  (Map) hours.get(thiskey);
//                       
                        String q3 = "insert into business_hours values ('"+obj.get("business_id")+"','"+thiskey+"','"+timings.get("close")+"','"+timings.get("open")+"')";
//                        
                         statement.executeUpdate(q3);
                        
                     }
					//end
	                   
	                   //insertion into cat table
//	                   System.out.println(s);
	                   ArrayList<String> categories = (ArrayList<String>) obj.get("categories");
	                   for(int j = 0 ; j<categories.size() ; ++j){
	                	   String q = "insert into business_cat values ('"+ obj.get("business_id") + "','" +categories.get(j) + "')";
//	                	   System.out.println(q);
                         statement.executeUpdate(q);
	                   }
	                   //end
					
	                   //insertion into neighborhood table
	                   ArrayList<String> hood = (ArrayList<String>) obj.get("neighborhoods");
	                   for(int j = 0 ; j<hood.size() ; ++j){
	                	   String q = "insert into business_hood values ('"+ obj.get("business_id") + "','" +hood.get(j) + "')";
//	                	   System.out.println(q);
                         statement.executeUpdate(q);
	                   }
	                   //end
	                   
	                   //insertion into attributes and ambience table
	                   
	                   Map<String,?> att = (Map <String,?>) obj.get("attributes");
	                   System.out.println(att + "\n\n");
						 Set keys1 = att.keySet();
		                   Object[] attname = keys1.toArray();
		                   for(int j =0 ; j<attname.length ; ++j)
		                   {
		                	   
//	                         
				                        String thiskey = attname[j].toString();
				                        String att_query = new String();
				                        if(att.get(thiskey) instanceof JSONObject){
				                        	
				                        	Map<String,?> subatt = (Map<String,?>) att.get(thiskey);
				                        	
				                        	 Set keys2 = subatt.keySet();
				                        	 
				  		                   Object[] attname2 = keys2.toArray();
						  		                 for(int k =0 ; k<attname2.length ; ++k){
						  		                	 String thiskey2 = attname2[k].toString();
						  		                	 String subcat_value = (String) String.valueOf(subatt.get(thiskey2));
						  		                	 att_query = "insert into business_attributes values ('"+obj.get("business_id")+"','"+thiskey+"','"+thiskey2+"','"+subcat_value+"')";
			//			  		                	 System.out.println("subkey="+thiskey2 + "value = "+subcat_value);
//						  		                	 System.out.println(att_query);
						  		                	 statement.executeUpdate(att_query);
						  		                 }
				                        	
				                        }
				                        
				                        else {
				                        	 String attvalues =   (String) String.valueOf(att.get(thiskey));
				                        	 att_query = "insert into business_attributes values ('"+obj.get("business_id")+"','n/a','"+thiskey+"','"+attvalues+"')";
			//	 	                        System.out.println("key="+thiskey + "value = "+attvalues);
				                        	 statement.executeUpdate(att_query);
				                        }
				                        
	                      
//	                        String q3 = "insert into business_hours values ('"+obj.get("business_id")+"','"+thiskey+"','"+timings.get("close")+"','"+timings.get("open")+"')";
//	                        System.out.println(q3);
//	                         statement.executeUpdate(q3);
	                        
	                     }
					
//                   
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              
              i++;
           }
           else{
        	   break;
           }
       }
    }
    
    public static void main(String[] args){
        new populate();
    }
    
    
}
