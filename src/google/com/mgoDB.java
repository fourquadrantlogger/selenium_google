package google.com;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import org.bson.BsonDocument;
import org.bson.Document;

/**
 * Created by timeloveboy on 16-7-6.
 */
public class mgoDB {
    static Mongo m;
    static DB db;
    public static DB getDB(){
        if(m!=null&&db!=null)return db;

        try {
            System.out.println("运行开始:");
            m = new Mongo("127.0.0.1", 27017);
            //m.dropDatabase("test");
            db= m.getDB("spider");
            return db;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void insertData(DBObject document){
        getDB().getCollection("google").insert(document);
    }
}
