public class Gegner {
	double x, y;
	double alpha;
	int iter = 0;
	

	public Gegner (int a, int b) {
		x = a + .5;
		y = b + .5;
	}

	protected void interact () {
		double dx = Wolverine.jack.x - x;
		double dy = Wolverine.jack.y - y;
	
		if (Wolverine.raketen.size() > 20)
			return;

		alpha = Math.atan(dy/dx);
		if (dx < 0)
			alpha += Math.PI;

		if (iter++ % 30 == 0)
			Wolverine.raketen.add(new Rakete(x-Wolverine.sin(alpha)/4, y+Wolverine.cos(alpha)/4, alpha));
	
	}
}
