package qz.charlenght.Model;
import qz.charlenght.Model.TextItems;

public class TextItems {
    public String Text;
    public int TextCount;
    
    public TextItems(){
        
    }
    public TextItems(String text, int TextCount){
        this.Text = text;
        this.TextCount = TextCount;
    }
    public String getText() {
        return this.Text;
    }

    public void setText(String Text) {
        this.Text = Text;
    }

    public int getTextCount() {
        return this.TextCount;
    }

    public void setTextCount(int TextCount) {
        this.TextCount = TextCount;
    }
}
