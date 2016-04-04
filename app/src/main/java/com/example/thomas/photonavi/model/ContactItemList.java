package com.example.thomas.photonavi.model;

import java.util.ArrayList;

/**
 * Created by thomas on 2016-04-04.
 */
public class ContactItemList {

    private ArrayList contactItemList = new ArrayList();

    private static ContactItemList ourInstance = new ContactItemList();

    public static ContactItemList getInstance() {
        return ourInstance;
    }

    private ContactItemList() {
    }

    public void setContactItemList(ContactItem contactItem) {
        contactItemList.add(contactItem);
    }

    public ContactItem getContactItemList(int index) {
        return (ContactItem)contactItemList.get(index);
    }
}
