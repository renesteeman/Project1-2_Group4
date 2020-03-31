package Textures;

public class TerrainTexturePack {

    //TODO remove all uses of brickTexture (including in the shader)
    private TerrainTexture grassTexture;
    private TerrainTexture sandTexture;

    public TerrainTexturePack(TerrainTexture grassTexture, TerrainTexture sandTexture) {
        this.grassTexture = grassTexture;
        this.sandTexture = sandTexture;
    }

    public TerrainTexture getGrassTexture() {
        return grassTexture;
    }

    public TerrainTexture getSandTexture() {
        return sandTexture;
    }
}
