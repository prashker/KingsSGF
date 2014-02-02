package modelTestSam;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonSingleton {
	   private static ObjectMapper instance = null;
	   
	   protected JacksonSingleton() {
	      // Exists only to defeat instantiation.
	   }
	   
	   public static ObjectMapper getInstance() {
	      if(instance == null) {
	    	  instance = new ObjectMapper();
	    	  //https://github.com/FasterXML/jackson-docs/wiki/JacksonPolymorphicDeserialization
	    	  //Support for polymorphic deserialization
	    	  instance.enableDefaultTyping();
	      }
	      return instance;
	   }
}