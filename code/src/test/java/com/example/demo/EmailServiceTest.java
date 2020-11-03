package com.example.demo;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import web.db.DatabaseQueries;
import web.resources.EmailService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class EmailServiceTest{

    @Test
    public void createEmailFromFormTest(){
        Form form = new Form();
        form.param("exampleInputEmail1", "newEmail@gmail.com");
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/rest/registerEmail");
        Response response = target.request().post(Entity.form(form));
        System.out.println(response.getStatus());
        Assert.assertTrue(response.getStatus() == 200 || response.getStatus() == 204);
        String newEmailString = DatabaseQueries.getEmailFromEid(DatabaseQueries.getMaxEid());
        Assert.assertTrue(newEmailString.equals("newEmail@gmail.com"));
    }

    @Test
    public void deleteEmailTest(){
        Client client = ClientBuilder.newClient();
        int eid = DatabaseQueries.getMaxEid(); //We are going to remove the email with that eid.
        WebTarget target = client.target("http://localhost:8080/rest/registerEmail/deleteEmail/" + eid);
        Response response = target.request().delete();
        System.out.println(response.getStatus());
        Assert.assertTrue(response.getStatus() == 200 || response.getStatus() == 204);

        Assert.assertTrue(eid-1 == DatabaseQueries.getMaxEid());
    }

}
