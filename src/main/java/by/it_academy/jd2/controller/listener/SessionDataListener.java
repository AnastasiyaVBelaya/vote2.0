package by.it_academy.jd2.controller.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;

@WebListener
public class SessionDataListener implements HttpSessionAttributeListener {
    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        System.out.println("attributeAdded name: " + se.getName());
        System.out.println("attributeAdded value: " + se.getValue());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {
        System.out.println("attributeReplaced name: " + se.getName());
        System.out.println("attributeReplaced value old: " + se.getValue());
        System.out.println("attributeReplaced value new: " + se.getSession().getAttribute(se.getName()));
    }
}

