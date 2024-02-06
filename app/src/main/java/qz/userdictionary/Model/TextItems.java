package qz.userdictionary.Model;

import qz.userdictionary.Model.TextItems;

public class TextItems {
    public int _id;
    public String Text;
    public String Short;
    public String frek;

    public TextItems(String Text, String Short, String frek, int _id) {
        this._id = _id;
        this.Text = Text;
        this.Short = Short;
        this.frek = frek;
    }

    public TextItems(String Text, String Short, String frek) {
        this.Text = Text;
        this.Short = Short;
        this.frek = frek;
    }

    public TextItems() {}

    public String getText() {
        return this.Text;
    }

    public void setText(String Text) {
        this.Text = Text;
    }

    public String getFrek() {
        return this.frek;
    }

    public void setFrek(String frek) {
        this.frek = frek;
    }

    public String getShort() {
        return this.Short;
    }

    public void setShort(String Short) {
        this.Short = Short;
    }

    public int get_id() {
        return this._id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
