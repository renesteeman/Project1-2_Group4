package Textures;

public class TerrainTexturePack {

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
