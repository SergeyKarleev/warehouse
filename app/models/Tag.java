package models;

import java.util.LinkedList;
import java.util.List;

import play.data.validation.Constraints.Required;

public class Tag {
	public Long id;
	@Required
	public String name;
	public List<Product> products;
	
	
	private static List<Tag> tags = new LinkedList<>();
	static {
		tags.add(new Tag(1L, "lightweight",Product.findByName("paperclips 1")));
		tags.add(new Tag(2L, "metal",Product.findByName("paperclips")));
		tags.add(new Tag(3L, "plastic",Product.findByName("paperclips")));
	}
	
	public Tag() {
		//Left empty
	}
	
	public Tag(Long id, String name, List<Product> products) {
		this.id = id;
		this.name = name;
		this.products = new LinkedList<Product>(products);
		for (Product product : products){
			product.tags.add(this);
		}
	}
	
	public static Tag findById(Long id){
		for (Tag tag : tags){
			if (tag.id == id) return tag;
		}
		return null;
	}
}
