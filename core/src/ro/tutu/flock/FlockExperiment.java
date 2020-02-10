package ro.tutu.flock;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import ro.tutu.flock.boid.Boid;

import java.util.ArrayList;
import java.util.List;

public class FlockExperiment extends ApplicationAdapter {
	private SpriteBatch batch;
	private List<Boid> boidList;
    private OrthographicCamera cam;
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;

	@Override
	public void create () {
        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(false, WIDTH / 2f, HEIGHT / 2f);
		this.batch = new SpriteBatch();
		this.boidList = new ArrayList<>();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		for(int i=0; i<10; i++){
			this.boidList.add(new Boid(i ,batch, cam));
		}
	}

	@Override
	public void render () {
	    batch.setProjectionMatrix(cam.combined);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		boidList.forEach(boid -> {
		    boid.edges();
		    //boid.align(boidList);
		    boid.draw();
        });
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
        boidList.forEach(Boid::dispose);
	}
}
