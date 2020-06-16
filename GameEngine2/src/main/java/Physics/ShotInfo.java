package Physics;

public class ShotInfo {
    private Vector3d position;
    private Vector3d velocity;

    public ShotInfo(Vector3d position, Vector3d velocity){
        this.position = new Vector3d(position.x,position.y,position.z);
        this.velocity = new Vector3d(velocity.x,velocity.y,velocity.z);
    }

    public ShotInfo(ShotInfo shotInfo) {
        this.position = shotInfo.getPosition3D();
        this.velocity = shotInfo.getVelocity3D();
    }

    /**
     * @return new Vector3d, position
     */
    public Vector3d getPosition3D() {
        return new Vector3d(position.x,position.y,position.z);
    }

    /**
     * @return new Vector3d, velocity
     */
    public Vector3d getVelocity3D() {
        return new Vector3d(velocity.x,velocity.y,velocity.z);
    }

    /**
     * Set new Vector3d position
     * @param position
     */
    public void setPosition3D(Vector3d position) {
        this.position = new Vector3d(position.x,position.y,position.z);
    }

    /**
     * Set new Vector3d velocity
     * @param velocity
     */
    public void setVelocity3D(Vector3d velocity) {
        this.velocity = new Vector3d(velocity.x,velocity.y,velocity.z);
    }

    //TODO These two methods are probably temporary
    public Vector2d getPosition2D() {
        return new Vector2d(position.x,position.z);
    }
    public Vector2d getVelocity2D() {
        return new Vector2d(velocity.x,velocity.z);
    }
}
