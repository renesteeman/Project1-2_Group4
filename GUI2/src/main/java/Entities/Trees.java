package Entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.lang.*;

public class Trees implements Collection<Entity> {

    ArrayList<Tree> trees;

    public Trees() {
        this.trees = new ArrayList<Tree>();
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
        int current_trees_size = trees.size();
        for (Object x : collection) {
            if (Tree.class.isAssignableFrom(x.getClass())) {
                trees.add((Tree)x);
            }
        }
        return current_trees_size != trees.size();
        //return trees.addAll((Collection<? extends Tree>) collection);
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
}
