package ro.tutu.flock;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ro.tutu.flock.boid.Boid;

import java.util.ArrayList;
import java.util.List;

public class FlockExperiment extends ApplicationAdapter {
	private SpriteBatch batch;
	private List<Boid> boidList;
    private OrthographicCamera cam;
    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

	@Override
	public void create () {
        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(false, WIDTH / 2f, HEIGHT / 2f);
		this.batch = new SpriteBatch();
		this.boidList = new ArrayList<>();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		this.boidList.add(new Boid(batch, cam));
	}

	@Override
	public void render () {
	    batch.setProjectionMatrix(cam.combined);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		boidList.forEach(Boid::draw);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
        boidList.forEach(Boid::dispose);
	}
}
