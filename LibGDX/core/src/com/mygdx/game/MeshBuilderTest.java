package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.StringBuilder;

/** @author Xoppa */
/*
public class MeshBuilderTest {
    Model model;
    Environment environment;

    public void create () {
        MeshBuilder meshBuilder = new MeshBuilder();
        meshBuilder.begin(Usage.Position | Usage.Normal | Usage.ColorPacked | Usage.TextureCoordinates, GL20.GL_TRIANGLES);
        meshBuilder.box(1f, 1f, 1f);
        Mesh mesh = new Mesh(true, meshBuilder.getNumVertices(), meshBuilder.getNumIndices(), meshBuilder.getAttributes());
        mesh = meshBuilder.end(mesh);

        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();

        modelBuilder.node().id = "box";
        MeshPartBuilder mpb = modelBuilder.part("box", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal | Usage.TextureCoordinates
                | Usage.ColorPacked, material);
        mpb.setColor(Color.RED);
        mpb.box(1f, 1f, 1f);

        modelBuilder.node().id = "mesh";
        mpb = modelBuilder.part("mesh", GL20.GL_TRIANGLES, mesh.getVertexAttributes(), material);
        Matrix4 transform = new Matrix4();
        mpb.setVertexTransform(transform.setToTranslation(0, 2, 0));
        mpb.addMesh(mesh);
        mpb.setColor(Color.BLUE);
        mpb.setVertexTransform(transform.setToTranslation(1, 1, 0));
        mpb.addMesh(mesh);
        mpb.setColor(null);
        mpb.setVertexTransform(transform.setToTranslation(-1, 1, 0).rotate(Vector3.X, 45));
        mpb.addMesh(mesh);
        mpb.setVertexTransform(transform.setToTranslation(0, 1, 1));
        mpb.setUVRange(0.75f, 0.75f, 0.25f, 0.25f);
        mpb.addMesh(mesh);

        model = modelBuilder.end();

        instances.add(new ModelInstance(model, new Matrix4().trn(0f, 0f, 0f), "mesh", true));
    }

}*/
