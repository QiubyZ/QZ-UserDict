package qz.charlenght.Model;

import qz.charlenght.Model.TextItems;

public class TextItems {
    public String Text;
    public String Short;
    public String frek;

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

}
