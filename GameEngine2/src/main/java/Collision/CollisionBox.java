package Collision;

import org.joml.Vector3f;

//A collision box is the smallest possible box that still contains the entire model
public class CollisionBox {
    private float left;
    private float front;
    private float back;
    private float right;
    private float bottom;
    private float top;

    public CollisionBox(float left, float front, float back, float right, float bottom, float top) {
        this.left = left;
        this.front = front;
        this.back = back;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
    }

    public boolean overlapsWithPointGivenMargin(Vector3f collisionBoxTranslation, Vector3f point, float margin){
        float leftTranslated = left + collisionBoxTranslation.x - margin;
        float rightTranslated = right + collisionBoxTranslation.x + margin;
        float backTranslated = back + collisionBoxTranslation.y - margin;
        float frontTranslated = front + collisionBoxTranslation.y + margin;
        float bottomTranslated = bottom + collisionBoxTranslation.z - margin;
        float topTranslated = top + collisionBoxTranslation.z + margin;

        if(point.x > leftTranslated && point.x < rightTranslated && point.z < topTranslated && point.z > bottomTranslated && point.y < backTranslated && point.y > frontTranslated){
            return true;
        } else {
            return false;
        }
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getFront() {
        return front;
    }

    public void setFront(float front) {
        this.front = front;
    }

    public float getBack() {
        return back;
    }

    public void setBack(float back) {
        this.back = back;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

}
