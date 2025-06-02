
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Line2D;

public class Asteroid extends Polygon {

    private static int points = 0;
    public boolean isSelected = false;

    public void add(Point pt) {
        points++;
        addPoint(pt.x, pt.y);
        System.out.println(points);
    }

    public void editBoool(boolean result) {
        isSelected = result;
    }

    public int isPointNearLine(Point p, double tolerance) {
        for (int i = 0; i < xpoints.length; i++) {
            Point point1;
            Point point2;

            if (i < xpoints.length - 1) {
                point1 = new Point(xpoints[i], ypoints[i]);
                point2 = new Point(xpoints[i + 1], ypoints[i + 1]);
            } else {
                point1 = new Point(xpoints[i], ypoints[i]);
                point2 = new Point(xpoints[0], ypoints[0]); // close the shape
            }

            double distance = pointToSegmentDistance(p, point1, point2);
            if (distance <= tolerance) {
                return i;
            }
        }

        return -1;
    }

    private double pointToSegmentDistance(Point p, Point a, Point b) {
        Line2D line = new Line2D.Double(a, b);
        return line.ptLineDist(p);
    }

//Chatgpt    
// Add a new point on an edge if click is near that edge
    public void addPointOnEdge(Point click, int index) {
        int i = index;
        int next = (i + 1) % npoints;
        Point a = new Point(xpoints[i], ypoints[i]);
        Point b = new Point(xpoints[next], ypoints[next]);

        // Create new arrays with one extra point
        int[] newX = new int[npoints + 1];
        int[] newY = new int[npoints + 1];

        // Copy points before insertion
        for (int j = 0; j <= i; j++) {
            newX[j] = xpoints[j];
            newY[j] = ypoints[j];
        }

        // Insert new point
        newX[i + 1] = click.x;
        newY[i + 1] = click.y;

        // Copy remaining points
        for (int j = i + 1; j < npoints; j++) {
            newX[j + 1] = xpoints[j];
            newY[j + 1] = ypoints[j];
        }

        xpoints = newX;
        ypoints = newY;
        npoints++;

    }

    public int getPoint(Point point, int tolerance) {
        for (int i = 0; i < npoints; i++) {
            int dx = point.x - xpoints[i];
            int dy = point.y - ypoints[i];
            int dist = (int) Math.round(Math.sqrt(dx * dx + dy * dy));
            if (dist <= tolerance) {
                return i;
            }
        }
        return -1;
    }

    public void setPoint(Point point, int index) {
        if (index >= 0 && index < npoints) {
            xpoints[index] = point.x;
            ypoints[index] = point.y;
        }
    }

    public void delPoint(int index) {
        if (index < 0 || index >= npoints || npoints <= 3) {
            return; // do nothing if invalid index or not enough points
        }
        int[] newX = new int[npoints - 1];
        int[] newY = new int[npoints - 1];
        for (int i = 0, j = 0; i < npoints; i++) {
            if (i != index) {
                newX[j] = xpoints[i];
                newY[j] = ypoints[i];
                j++;
            }
        }
        xpoints = newX;
        ypoints = newY;
        npoints--;
    }

    public void draw(Graphics g) {
        if (isSelected) {
            g.setColor(Color.GRAY);
            g.fillPolygon(this);

            g.setColor(Color.BLACK);
            if(!isSimple()) g.setColor(Color.red);
            for (int i = 0; i < npoints; i++) {
                int next = (i + 1) % npoints;
                g.drawLine(xpoints[i], ypoints[i], xpoints[next], ypoints[next]);
            }

            g.setColor(Color.GREEN);
            for (int i = 0; i < npoints; i++) {
                g.fillOval(xpoints[i] - 3, ypoints[i] - 3, 6, 6);
            }

            Point center = calculateCentroid();
            g.setColor(Color.ORANGE);
            g.fillOval(center.x - 3, center.y - 3, 6, 6);
        }

    }

//ChatGPT    
// Calculate centroid of the polygon (average of points)
    public Point calculateCentroid() {
        int sumX = 0;
        int sumY = 0;
        for (int i = 0; i < npoints; i++) {
            sumX += xpoints[i];
            sumY += ypoints[i];
        }
        if (npoints == 0) {
            return new Point(0, 0);
        }
        return new Point(sumX / npoints, sumY / npoints);
    }

//ChatGPT    
// Rotate polygon by a small angle around its centroid
    public void rotate() {
        Point center = calculateCentroid();
        double angle = Math.toRadians(5); // rotate by 5 degrees
        for (int i = 0; i < npoints; i++) {
            int x = xpoints[i] - center.x;
            int y = ypoints[i] - center.y;

            int newX = (int) Math.round(x * Math.cos(angle) - y * Math.sin(angle)) + center.x;
            int newY = (int) Math.round(x * Math.sin(angle) + y * Math.cos(angle)) + center.y;

            xpoints[i] = newX;
            ypoints[i] = newY;
        }
    }

    @Override
    public String toString() {
        return "Asteroid with " + npoints + " points";
    }

    public void center(int width, int height) {
        int centerX = width / 2;
        int centerY = height / 2;
        Point center = calculateCentroid();

        int translationX = centerX - center.x;
        int translationY = centerY - center.y;

        for (int i = 0; i < xpoints.length; i++) {
            xpoints[i] += translationX;
            ypoints[i] += translationY;
        }
    }

    public String returnFileData() {
        String out = "";
        for (int i = 0; i < xpoints.length; i++) {
            out += xpoints[i] + ";" + ypoints[i] + ";";
        }
        return out;
    }

    public void zoom(double scale) {
        Point center = calculateCentroid();
        for (int i = 0; i < xpoints.length; i++) {
            xpoints[i] = (int) Math.round(center.x + scale * (xpoints[i] - center.x));
            ypoints[i] = (int) Math.round(center.y + scale * (ypoints[i] - center.y));
        }

    }

    public boolean segmentsIntersect(int x1, int y1, int x2, int y2,
            int x3, int y3, int x4, int y4) {
        return ccw(x1, y1, x3, y3, x4, y4) != ccw(x2, y2, x3, y3, x4, y4)
                && ccw(x1, y1, x2, y2, x3, y3) != ccw(x1, y1, x2, y2, x4, y4);
    }

    private boolean ccw(int x1, int y1, int x2, int y2, int x3, int y3) {
        return (y3 - y1) * (x2 - x1) > (y2 - y1) * (x3 - x1);
    }

    public boolean isSimple() {
        int n = this.npoints;

        for (int i = 0; i < n; i++) {
            int i2 = (i + 1) % n;
            for (int j = i + 1; j < n; j++) {
                int j2 = (j + 1) % n;

                if (i == j || i2 == j || i == j2) {
                    continue;
                }

                if (segmentsIntersect(
                        this.xpoints[i], this.ypoints[i],
                        this.xpoints[i2], this.ypoints[i2],
                        this.xpoints[j], this.ypoints[j],
                        this.xpoints[j2], this.ypoints[j2]
                )) {
                    return false;
                }
            }
        }
        return true;
    }
}
