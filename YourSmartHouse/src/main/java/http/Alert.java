package http;

import java.util.ArrayList;

public class Alert {

    private final String type;
    private final ArrayList<String> messages;

    public Alert(ArrayList<String> messages, String type) {
        this.type = type;
        this.messages = messages;
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

}
