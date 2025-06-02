package asteroid_drawer;

import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Asteroid extends Polygon {

	public Point2D getCentroid() {
		double area = 0;
        double centroidX = 0;
        double centroidY = 0;
        int n = super.npoints;

        for (int i = 0; i < n; i++) {
            int j = (i + 1) % n;
            double xi = super.xpoints[i];
            double yi = super.ypoints[i];
            double xj = super.xpoints[j];
            double yj = super.ypoints[j];

            double cross = xi * yj - xj * yi;
            area += cross;
            centroidX += (xi + xj) * cross;
            centroidY += (yi + yj) * cross;
        }

        area *= 0.5;
        centroidX /= (6 * area);
        centroidY /= (6 * area);

        return new Point2D.Double((int) Math.round(centroidX), (int) Math.round(centroidY));
	}

	public boolean isIntersecting() {
		for (int i = 0; i < npoints; i += 2) {
			Line2D line = new Line2D.Double(xpoints[i], ypoints[i], xpoints[i + 1], ypoints[i + 1]);
			for (int j = 0; j < npoints; j += 2) {
				if (i != j) {
					Line2D line2 = new Line2D.Double(xpoints[j], ypoints[j], xpoints[j + 1], ypoints[j + 1]);
					if (line.intersectsLine(line2)) {
						if (line.getP1().equals(line2.getP1()) || line.getP1().equals(line2.getP2()) || line.getP2().equals(line2.getP1()) || line.getP2().equals(line2.getP2())) {
							return false;
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	public void rotate(double angle) {
	}

	public void click(int x, int y) {
		final Point2D.Double pt = new Point2D.Double(x, y);
		addPoint((int) pt.getX(), (int) pt.getY());
	}

}
