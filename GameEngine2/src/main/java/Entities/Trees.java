package Entities;

import Models.CollisionModel;
import Models.RawModel;
import Models.TexturedModel;
import OBJConverter.ModelData;
import OBJConverter.OBJFileLoader;
import RenderEngine.Loader;
import Textures.ModelTexture;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

//Collection of trees
public class Trees implements Collection<Entity> {
    ArrayList<Tree> trees;

    public Trees() {
        this.trees = new ArrayList<Tree>();
    }

    public Tree get(int i){
        return trees.get(i);
    }

    @Override
    public int size() {
        return trees.size();
    }

    @Override
    public boolean isEmpty() {
        return size()==0;
    }

    @Override
    public boolean contains(Object o) {
        for(int i=0; i<trees.size(); i++){
            if(trees.get(i).equals(o)){
                return true;
            }
        }

        return false;
    }

    @Override
    public Iterator<Entity> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return trees.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return trees.toArray(ts);
    }

    public boolean add(Tree tree){
        return add((Entity) tree);
    }

    @Override
    public boolean add(Entity entity) {
        return trees.add((Tree) entity);
    }

    @Override
    public boolean remove(Object o) {
        return trees.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return trees.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends Entity> collection) {
        return trees.addAll((Collection<? extends Tree>) collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return trees.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return trees.retainAll(collection);
    }

    @Override
    public void clear() {
        trees = new ArrayList<Tree>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trees entities = (Trees) o;
        return Objects.equals(trees, entities.trees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trees);
    }

    public String getTreeInfoAsString(){
        StringBuilder info = new StringBuilder();
        info.append("trees = ");

        for(int i=0; i<trees.size()-1; i++){
            Tree tree = trees.get(i);
            Vector3f pos = tree.getPosition();
            info.append("("+pos.x+", "+pos.y+", "+pos.z+"), ");
        }

        Tree lastTree = trees.get(trees.size()-1);
        Vector3f lastPos = lastTree.getPosition();
        info.append("("+lastPos.x+", "+lastPos.y+", "+lastPos.z+")");

        info.append(";");

        return info.toString();
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public void loadFromString(ArrayList<Vector3f> treeLocations, Loader loader){
        //Remove existing trees
        trees.clear();

        //Add trees from file
        for(Vector3f treeLocation : treeLocations){
            Tree loadedTree = new Tree(getTreeCollisionModel(loader), treeLocation, 0, 0, 0, 1);
            trees.add(loadedTree);
        }
    }

    private CollisionModel getTreeCollisionModel(Loader loader){
        ModelData treeModelData = OBJFileLoader.loadOBJ("tree");
        RawModel treeModel = loader.loadToVAO(treeModelData.getVertices(), treeModelData.getTextureCoords(), treeModelData.getNormals(), treeModelData.getIndices());
        TexturedModel texturedTree = new TexturedModel(treeModel, new ModelTexture(loader.loadTexture("models/TreeTexture")));
        CollisionModel collisionTree = new CollisionModel(texturedTree, treeModelData.getVertices(), treeModelData.getNormals(), treeModelData.getIndices());
        return collisionTree;
    }
}
