package Entities;

import EngineTester.MainGameLoop;
import org.joml.Vector3f;

import java.util.*;

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

        for(Tree tree : trees){
            Vector3f pos = tree.getPosition();
            info.append("("+pos.x+" "+pos.y+" "+pos.z+")");
        }

        return info.toString();
    }

    public void loadFromString(String treeInfo){
        //Remove existing trees
        trees.clear();

        //Separate the string into a separate string for each tree
        treeInfo = treeInfo.replace(")", "");
        String[] treeStrings = treeInfo.split("\\(");

        //Convert each treeString into an actual tree (ignore i=0 since that's empty
        for(int i=1; i<treeStrings.length; i++){
            String treeString = treeStrings[i];
            String[] treeCoordinates = treeString.split(" ");
            float xPos = Float.parseFloat(treeCoordinates[0]);
            float yPos = Float.parseFloat(treeCoordinates[1]);
            float zPos = Float.parseFloat(treeCoordinates[2]);

            Tree loadedTree = new Tree(MainGameLoop.texturedTree, new Vector3f(xPos, yPos, zPos), 0, 0, 0, 1);
            trees.add(loadedTree);
        }
    }
}
