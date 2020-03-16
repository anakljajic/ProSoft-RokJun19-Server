/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.Linija;
import domain.LinijaMedjustanice;
import domain.LinijaStanica;
import domain.Stanica;
import java.util.List;
import storage.Storage;

/**
 *
 * @author student1
 */
public class Controller {

    private static Controller instance;
    private final Storage storage;

    private Controller() {
        storage = new Storage();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

//    public User logIn(String username, String password) throws Exception{
//        List<User> users=storageUser.getAll();
//        for (User user : users) {
//            if(user.getUsername().equalsIgnoreCase(username)){
//                if(user.getPassword().equals(password)){
//                    return user;
//                }else{
//                    throw new Exception("Lozinka nije odgovarajuća!");
//                }
//            }
//        }
//        throw new Exception("Korisnik nije registrovan!");
//    }
//    
//    public List<Manufacturer> getAllManufacturers() throws Exception{
//        //return storageManufacturer.getAll();
//        return serviceManufacturer.getAll();
//    }
//    
//    public void saveProduct(Product product) throws Exception{
//        //storageProduct.insert(product);
//        SystemOperation so=new SOInsertProduct(product);
//        so.execute();
//    }
//    
//    public List<Product> getAllProducts() throws Exception{
//        return storageProduct.getAll();
//    }
//    
//    public Invoice saveInvoice(Invoice invoice) throws Exception{
//        //return serviceInvoice.save(invoice);
//        SystemOperation so=new SOInsertInvoice(invoice);
//        so.execute();
//        return (Invoice)so.getDomainObject();
//    }
//    
//    
    public List<Stanica> getAllStanice() throws Exception {
        return storage.getAllStanice();
    }

    public Linija sacuvajLiniju(Linija linija) throws Exception {
        return storage.sacuvajLiniju(linija);
    }

    public void sacuvajMedjustanice(List<LinijaStanica> linijeStanice) throws Exception {
        storage.sacuvajMedjustanice(linijeStanice);
    }

    public List<LinijaMedjustanice> getAllLinijeMedjustanice() throws Exception {
        return storage.getAllLinijeMedjustanice();
    }

    public List<LinijaMedjustanice> getAllLinijeMedjustaniceSaFilterom(String naziv) throws Exception {
        return storage.getAllLinijeMedjustaniceSaFilterom(naziv);
    }
}
