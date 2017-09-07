package edu.ap.rd.quotes;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import redis.clients.*;
import redis.clients.jedis.Jedis;

@Path("/index")
public class AuthorResource {

	
	@GET
	@Produces({ "text/html" })
	public String getAllAuthors() {
		// Uncomment voor opvullen
		//this.fillDb(true);

		Jedis jedis = JedisConnection.getInstance().getConnection();
		StringBuilder builder = new StringBuilder();
		String htmlString = "<html><body>";
		
		htmlString +="<title>Movies</title>";
		htmlString +="</head>";
		
		htmlString +="<body>";
		htmlString +="<form action=addMovies() method=POST>";
		htmlString +="<input type=text name=moviename>";
		htmlString +=" <input type=submit value=Submit>";
		htmlString += "</form>";
		htmlString +="<ul>";
		ArrayList<String> movielist = new ArrayList<String>();
		
		for (String key : jedis.keys("movie:*")) {
			movielist.add(jedis.get(key));
		}
		for(String author: movielist){
			//System.out.println(author);
			String[] full_name = author.split(" ");
			
			htmlString +="<li>"+author+"</li>";
			

		}
		
		htmlString +="</ul>";
		htmlString +="</body>";
		htmlString +="</html>";

		return htmlString;	
	}
	
	/*@GET
	@Path("{name}/quotes")
	@Produces({ "text/html" })
	public String getAuthorQuotes(@PathParam("name") String author_name) {
		
		// Uncomment voor opvullen
		//this.fillDb(true);

		Jedis jedis = JedisConnection.getInstance().getConnection();
		StringBuilder builder = new StringBuilder();

		builder.append("<html>");
		builder.append("<head>");
		builder.append("<title>Quotes</title>");
		builder.append("</head>");

		builder.append("<body>");
		builder.append("<h1>List of auhtor's quotes:</h1>");
		builder.append("<ul>");
		ArrayList<String> quotesList = new ArrayList<String>();
		for (String key : jedis.keys("acteur:*")) {		
			String author = jedis.get(key);
			if(author.equals(author_name)){
				
				String id = key.split(":")[1];
				for (String quote : jedis.smembers("acteur:" + id)) {
					quotesList.add(quote);
				}
				
			}
		}
		
		
		for(String quote: quotesList){
			
			builder.append("<li>"+quote+"</li>");
			
		}
		builder.append("<br><br><a href=/JAXRS_REDIS_A/index/>Home</a>");
				
		builder.append("</body>");
		builder.append("</html>");

		return builder.toString();

	
}*/

	@POST
	@Consumes({"application/json"})
	public void addMovies(@FormParam("moviename") String name) {
		Jedis jedis = JedisConnection.getInstance().getConnection();
		jedis.set("movie:2",name);
	
	}
	

/*
	@GET
	@Produces({ "text/html" })
	public String getAllQuotes() {
		// Uncomment voor opvullen
		this.fillDb(true);

		Jedis jedis = JedisConnection.getInstance().getConnection();
		StringBuilder builder = new StringBuilder();

		builder.append("<html>");
		builder.append("<head>");
		builder.append("<title>Quotes</title>");
		builder.append("</head>");

		builder.append("<body>");
		builder.append("<ul>");
		for (String key : jedis.keys("quotes:*")) {
			for (String quote : jedis.smembers(key)) {
				builder.append("<li>" + quote);
			}
		}
		builder.append("</ul>");
		builder.append("</body>");
		builder.append("</html>");

		return builder.toString();
	}

/*
	// Specifieke auteur
	// Data => de data die wordt gepost
	@POST
	public String getAuthorQuotes(String data) {
		// Uncomment voor opvullen
		//this.fillDb(true);

		Jedis jedis = JedisConnection.getInstance().getConnection();
		StringBuilder builder = new StringBuilder();

		builder.append("<html>");
		builder.append("<head>");
		builder.append("<title>Quotes</title>");
		builder.append("</head>");

		builder.append("<body>");
		// Selecteren van correcte author
		for (String author : jedis.keys("author:*")) {
			String tmpAuthor = jedis.get(author);
			if (tmpAuthor.equals(data)) {
				builder.append("testink");
				int authorId = Integer.parseInt(author.split(":")[1]);

				builder.append("<h1>" + tmpAuthor + "</h1>");
				builder.append("<ul>");
				for (String quote : jedis.smembers("quotes:" + authorId)) {
					builder.append("<li>" + quote);
				}
				builder.append("</ul>");

				break; 
			}
		}

		builder.append("</body>");
		builder.append("</html>");

		return builder.toString();
	}



	*/
	private void fillDb(boolean flush) {
		Jedis jedis = JedisConnection.getInstance().getConnection();

		if (flush) {
			jedis.flushDB();
		}

		jedis.set("movie:1", "deadpool1");
		jedis.sadd("acteur:1", "acteur1");
		
	}

}
