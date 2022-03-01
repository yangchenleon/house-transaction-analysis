package org.example.controller;

import org.example.dao.MongoDao;
import org.example.domain.AvgPriceModel;
import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AvgPriceControler extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<AvgPriceModel> list = new ArrayList<AvgPriceModel>();
        MongoDao mongoDao = new MongoDao("mongoproject","avgprice");
        FindIterable<Document> documents = mongoDao.findAll();
        documents.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                String district = document.get("district").toString();
                double avg_price =  new Double(document.get("avg_price").toString());
                list.add(new AvgPriceModel(district, avg_price));
            }
        });
        String json = new Gson().toJson(list);
        System.out.println(json);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().write(json);
        mongoDao.close();
    }
}
