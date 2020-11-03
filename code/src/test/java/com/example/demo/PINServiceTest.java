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

public class PINServiceTest{


    @Test
    public void changePINTest(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/rest/changePIN/" + "1234");
        Form form = new Form();
        Response response = target.request().post(Entity.form(form));
        System.out.println(response.getStatus());
        //Assert.assertTrue(response.getStatus() == 200 || response.getStatus() == 204);

        String pin = DatabaseQueries.getSystem().getPin();
        System.out.println(pin);
        Assert.assertEquals(pin, "1234");
    }

}
