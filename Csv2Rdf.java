import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;


public class Csv2Rdf {
	private static String FILE_PATH = "E:\\WebSemantics\\Assignement 1\\data.csv"; 
	private ArrayList<String>dataList, actorList, movieList, countryList;
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter File Path");
		FILE_PATH = scanner.nextLine();
		Csv2Rdf cr = new Csv2Rdf();
		cr.Read();
		cr.Converting();

	}
	private void Read() {
		int i = 0;
		dataList = new ArrayList<>();
		actorList = new ArrayList<>();
		movieList = new ArrayList<>();
		countryList = new ArrayList<>();
		File file = new File(FILE_PATH);
		if(file.exists()) {
			try {
			FileReader filereader = new FileReader(file);
			BufferedReader bufferreader = new BufferedReader(filereader);
			String read = "";
			while((read = bufferreader.readLine())!=null) {
				dataList.add(read);
					String temp[] = dataList.get(i).split(",");
					for(int j=0;j<temp.length;j++) {
						if(j==0) {
						actorList.add(temp[j]);
						}
						if(j==1) {
							movieList.add(temp[j]);
						}
						if(j==2) {
							countryList.add(temp[j]);
						}
					}
				
				i++;
			}
			}
			catch(IOException io) {
				io.printStackTrace();
			}
		}
		
		else {
			System.out.println("File is missing");
		}
	}
	
	private void Converting() {
		Model mymodel = ModelFactory.createDefaultModel();
		Property movie = mymodel.createProperty("http://megana.org/label/movie"); //Movie
		Property country =  mymodel.createProperty("http://megana.org/label/country"); //Country
		for(int i = 1;i<dataList.size();i++) {
			Resource actor = mymodel.createResource("http://megana.org/resource/"+actorList.get(i));
			actor.addProperty(movie, movieList.get(i));
			actor.addProperty(country, countryList.get(i));
		}
		mymodel.write(System.out, "TURTLE");
		try {
			Writer wr = new FileWriter("c:/users/megana/eclipse-workspace/Assignment1/data/megana_data.ttl");
			mymodel.write(wr,"TURTLE");}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
