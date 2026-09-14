package controllers;

import entities.Recipient;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RecipientController
{
    private final Map<String, Recipient> recipientMap = new ConcurrentHashMap<>();
    private static final Recipient NULL_RECIPIENT = new Recipient();

    public Recipient getRecipientById(String id)
    {
        return this.recipientMap.getOrDefault(id, NULL_RECIPIENT);
    }

    public ArrayList<Recipient> getAllRecipients()
    {
        ArrayList<Recipient> list = new ArrayList<>(recipientMap.values());
        return list;
    }

    public void addRecipient(Recipient recipient)
    {
        this.recipientMap.put(recipient.getId(), recipient);
    }

    public void removeRecipient(String recipientId)
    {
          this.recipientMap.remove(recipientId);
    }

}
