package ro.tutu.flock.boid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import ro.tutu.flock.FlockExperiment;
import ro.tutu.flock.utils.RandomUtils;

import java.util.List;
import java.util.Random;

public class Boid {
    private static final String BOID_IMAGE = "boid.png";
    private static final int HEIGHT = 10;
    private static final int WIDTH  = 10;
    private final Texture img;
    private final SpriteBatch batch;
    private final OrthographicCamera cam;
    private final Sprite sprite;
    private Vector2 postition;
    private Vector2 velocity;
    private Vector2 acceleration;
    private int index;
    private int maxSpeed = 2;

    public Boid(int index, SpriteBatch batch, OrthographicCamera cam){
        this.index = index;
        this.img = new Texture(BOID_IMAGE);
        this.batch = batch;
        this.cam = cam;
        this.sprite = new Sprite(this.img);
        this.sprite.setSize(WIDTH, HEIGHT);
        this.postition = new Vector2(new Random().nextInt(FlockExperiment.WIDTH),
                new Random().nextInt(FlockExperiment.HEIGHT));
        this.velocity = new Vector2(RandomUtils.RandomFloat(), RandomUtils.RandomFloat());
        this.acceleration = new Vector2();
    }

    public void edges(){
        if(this.postition.x > FlockExperiment.WIDTH)
            this.postition.set(0f, this.postition.y);
        else if(this.postition.x < 0)
            this.postition.set(FlockExperiment.WIDTH, this.postition.y);
        if(this.postition.y > FlockExperiment.HEIGHT)
            this.postition.set(this.postition.x, 0f);
        else if(this.postition.y < 0)
            this.postition.set(this.postition.x, FlockExperiment.HEIGHT);
    }

    public void draw(){
        batch.setProjectionMatrix(cam.combined);
        this.postition.add(this.velocity);
        this.velocity.add(this.acceleration);
        this.velocity.limit(this.maxSpeed);
        this.acceleration.scl(0f);
        sprite.setRotation(this.velocity.angle() - 90);
        sprite.setPosition(this.postition.x, this.postition.y);
        this.sprite.draw(batch);
    }

    public void align(List<Boid> boids){
        float perceptionRadius = 10f;
        Vector2 result = new Vector2();
        int total = 0;
        for (Boid other : boids){
            if(index != other.getIndex() && Vector2.dst(this.postition.x, this.postition.y, other.getPosition().x,
                    other.getPosition().y) < perceptionRadius){
                result.add(other.getVelocity());
                total++;
            }
        }
        result.scl(-total);
        result.sub(this.getVelocity());
        this.acceleration.set(result);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getPosition(){
        return postition;
    }

    public int getIndex(){
        return index;
    }

    public void dispose(){
        img.dispose();
    }
}
