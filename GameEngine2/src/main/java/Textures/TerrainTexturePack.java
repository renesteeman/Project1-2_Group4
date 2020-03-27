package Textures;

public class TerrainTexturePack {

    //TODO remove all uses of brickTexture (including in the shader)
    private TerrainTexture grassTexture;
    private TerrainTexture sandTexture;
    private TerrainTexture brickTexture;

    public TerrainTexturePack(TerrainTexture grassTexture, TerrainTexture sandTexture, TerrainTexture brickTexture) {
        this.grassTexture = grassTexture;
        this.sandTexture = sandTexture;
        this.brickTexture = brickTexture;
    }

    public TerrainTexture getGrassTexture() {
        return grassTexture;
    }

    public TerrainTexture getSandTexture() {
        return sandTexture;
    }

    public TerrainTexture getBrickTexture(){
        return brickTexture;
    }
}
