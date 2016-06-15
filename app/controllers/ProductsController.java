/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import models.Product;
import models.Tag;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

/**
 * @author s.karleev
 */
@With(CatchAction.class)
public class ProductsController extends Controller{
    @Inject FormFactory formFactory;
    
    //стандартное действие index перенаправляет нас на list() для отображения списка продуктов
	public Result index() {
		return redirect(routes.ProductsController.list(0));
	}

    //действие отображает список продуктов
    public Result list(Integer page){
    	List<Product> products = Product.findAll();
    	return ok(views.html.list.render(products));
    }
    
    //действие создает новый продукт
    public Result newProduct(){
    	return ok(views.html.details.render(formFactory.form(Product.class)));
    }
    
    //действие показывает детализацию для выбранного продукта
    public Result details(Product product){
        if (product == null){
        	return notFound(String.format("Product %s does not exist",	product));
        }
        
        Form<Product>filledForm = formFactory.form(Product.class).fill(product);
        return ok(views.html.details.render(filledForm));
    }
    
    //действие сохраняет продукт
    public Result save(){
    	Form<Product> boundForm = formFactory.form(Product.class).bindFromRequest();
    	if (boundForm.hasErrors()){
    		flash("error", "Please correct the form below");
    		return badRequest(views.html.details.render(boundForm));
    	}
    	
    	Product product = boundForm.get();
    	List<Tag> tags = new ArrayList<>();
    	for(Tag tag : product.tags){
    		if (tag.id != null){
    			tags.add(Tag.findById(tag.id));
    		}
    	}
    	
    	product.tags = tags;
    	product.save();
    	flash("success", String.format("Successfully added product %s", product));
    	return redirect(routes.ProductsController.list(1));
    }
    
    //действие удаляет продукт
    public Result delete(String ean){
    	final Product product = Product.findByEan(ean);
    	if (product == null){
    		return notFound(String.format("Product %s does not exist", ean));
    	}
    	Product.remove(product);
    	return redirect(routes.ProductsController.list(0));
    }
}
