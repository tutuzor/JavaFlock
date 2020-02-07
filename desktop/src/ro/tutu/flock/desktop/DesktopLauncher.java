package ro.tutu.flock.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ro.tutu.flock.FlockExperiment;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = FlockExperiment.WIDTH;
		config.height = FlockExperiment.HEIGHT;
		new LwjglApplication(new FlockExperiment(), config);
	}
}
