package com.gdx.game.components;

public class ModelComponent extends Component {
    public Model model;
    public ModelInstance instance;

    public ModelComponent(Model model, float x, float y, float z) {
        this.model = model;
        this.instance = new ModelInstance(model, new
                Matrix4().setToTranslation(x, y, z));
    }
}
