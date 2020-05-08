package GUIElements;

import java.util.ArrayList;

public class UIGroup {
    ArrayList<UIElement> elements;

    public UIGroup() {
        this.elements = new ArrayList<>();
    }

    public UIGroup(ArrayList<UIElement> elements) {
        this.elements = elements;
    }

    public void addElement(UIElement element){
        elements.add(element);
    }

    public void removeElement(UIElement element){
        elements.remove(element);
    }

    public void show(){
        for(UIElement element : elements){
            element.show();
        }
    }

    public void hide(){
        for(UIElement element : elements){
            element.hide();
        }
    }

    public int getSize(){
        return elements.size();
    }
}
