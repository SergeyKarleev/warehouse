package models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import play.data.validation.Constraints.Required;

public class Product {
	@Required
	public String ean;
	@Required
	public String name;
	public String description;
	public List<Tag> tags = new LinkedList<>();
	
	private static List<Product> products;
	
	static{
		products = new ArrayList<>();
		products.add(new Product("11111", "Paperclips1", "Paperclips Description"));
		products.add(new Product("22222", "Paperclips2", "Paperclips Description"));
		products.add(new Product("33333", "Paperclips3", "Paperclips Description"));
		products.add(new Product("44444", "Paperclips4", "Paperclips Description"));
		products.add(new Product("55555", "Paperclips5", "Paperclips Description"));
	}
	
	public Product() {}

	public Product(String ean, String name, String description) {
		this.ean = ean;
		this.name = name;
		this.description = description;
	}
	
	public String toString(){
		return String.format("%s - %s", ean, name);
	}
	
	public static List<Product> findAll() {
		return new ArrayList<Product>(products);
	}
	
	public static Product findByEan(String ean){
		for (Product candidate : products){
			if (candidate.ean.equals(ean))
				return candidate;
		}
		return null;
		
	}
	
	public static List<Product> findByName (String term){
		final List<Product> results = new ArrayList<Product>();
		for (Product candidate : products){
			if (candidate.name.toLowerCase().contains(term.toLowerCase()))
				results.add(candidate);
		}
		return results;
	}
	
	public static boolean remove(Product product){
		return products.remove(product);
	}
	
	public void save(){
		products.remove(findByEan(this.ean));
		products.add(this);
	}
}
