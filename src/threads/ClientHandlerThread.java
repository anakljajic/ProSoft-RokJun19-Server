/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import controller.Controller;
import domain.Linija;
import domain.LinijaStanica;
import domain.Stanica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import transfer.RequestObject;
import transfer.ResponseObject;
import util.Operation;

/**
 *
 * @author student1
 */
public class ClientHandlerThread extends Thread {

    private Socket socket;

    public ClientHandlerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        RequestObject request = null;
        ResponseObject response = null;
        while (!socket.isClosed()) {
            try {
                request = receiveRequest();

                switch (request.getOperation()) {
                    case Operation.OPERATION_GET_ALL_STANICE:
                        response = operationGetAllStanice(request);
                        break;
                    case Operation.OPERATION_SAVE_LINIJA:
                        response = operationSacuvajLiniju(request);
                        break;
                    case Operation.OPERATION_SAVE_LINIJE_STANICE:
                        response = operationSacuvajMedjustanice(request);
                        break;
//                    case Operation.OPERATION_GET_ALL_PRODUCTS:
//                        response = operationGetAllProducts(request);
//                        break;
//                    case Operation.OPERATION_SAVE_INVOICE:
//                        response = operationSaveInvoice(request);
//                        break;
                }
                sendResponse(response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public RequestObject receiveRequest() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        return (RequestObject) in.readObject();
    }

    public void sendResponse(ResponseObject response) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(response);
        out.flush();
    }

//    public ResponseObject operationLogIn(RequestObject request) {
//        ResponseObject response = null;
//        Map<String, String> data = (Map) request.getData();
//        String username = data.get("username");
//        String password = data.get("password");
//
//        try {
//            response = new ResponseObject();
//            User user = Controller.getInstance().logIn(username, password);
//            response.setData(user);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            response.setException(ex);
//        }
//        return response;
//    }
//
//    private ResponseObject operationGetAllManufacturers(RequestObject request) {
//        ResponseObject response = null;
//        
//        try {
//            response = new ResponseObject();
//            List<Manufacturer> manufacturers= Controller.getInstance().getAllManufacturers();
//            response.setData(manufacturers);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            response.setException(ex);
//        }
//        return response;
//    }
//
//    private ResponseObject operationSaveProduct(RequestObject request) {
//        ResponseObject response = null;
//        Product product=(Product)request.getData();
//        
//        try {
//            response = new ResponseObject();
//            Controller.getInstance().saveProduct(product);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            response.setException(ex);
//        }
//        return response;
//    }
//
//    private ResponseObject operationGetAllProducts(RequestObject request) {
//        ResponseObject response = null;
//        
//        try {
//            response = new ResponseObject();
//            List<Product> products= Controller.getInstance().getAllProducts();
//            response.setData(products);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            response.setException(ex);
//        }
//        return response;
//    }
//
//    private ResponseObject operationSaveInvoice(RequestObject request) {
//         ResponseObject response = null;
//         Invoice invoice=(Invoice)request.getData();
//        
//        try {
//            response = new ResponseObject();
//            invoice= Controller.getInstance().saveInvoice(invoice);
//            response.setData(invoice);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            response.setException(ex);
//        }
//        return response;
//    }
    public Socket getSocket() {
        return socket;
    }

    private ResponseObject operationGetAllStanice(RequestObject request) {
        ResponseObject response = null;

        try {
            response = new ResponseObject();
            List<Stanica> products = Controller.getInstance().getAllStanice();
            response.setData(products);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setException(ex);
        }
        return response;
    }

    private ResponseObject operationSacuvajLiniju(RequestObject request) {
        ResponseObject response = null;
        Linija linija = (Linija) request.getData();

        try {
            response = new ResponseObject();
            linija = Controller.getInstance().sacuvajLiniju(linija);
            response.setData(linija);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setException(ex);
        }
        return response;
    }

    private ResponseObject operationSacuvajMedjustanice(RequestObject request) {
        ResponseObject response = null;
        List<LinijaStanica> linijeStanice = (List<LinijaStanica>) request.getData();

        try {
            response = new ResponseObject();
            Controller.getInstance().sacuvajMedjustanice(linijeStanice);
            response.setData(linijeStanice);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setException(ex);
        }
        return response;
    }

}
