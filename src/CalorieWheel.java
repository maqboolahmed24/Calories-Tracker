import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;

class CalorieWheelPanel extends JPanel {
    private int maxCalories;
    private int currentCalories;

    // Constructor that takes maxCalories and currentCalories as parameters
    public CalorieWheelPanel(int maxCalories, int currentCalories) {
        this.maxCalories = maxCalories;
        this.currentCalories = currentCalories;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCalorieWheel(g);
    }

    private void drawCalorieWheel(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int size = Math.min(getWidth(), getHeight()) / 2;
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        // Calculate the angles for the arcs
        double orangeAngle = Math.min(360.0, 360.0 * currentCalories / maxCalories);
        double excessCalories = Math.max(0, currentCalories - maxCalories);
        double redAngle = 360.0 * excessCalories / maxCalories;

        // Drawing the circles for the calorie wheel

        // Draw the background circle
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillOval(x, y, size, size);

        // Draw the calorie progress
        g2d.setColor(Color.ORANGE);
        g2d.fill(new Arc2D.Double(x, y, size, size, 90, -orangeAngle, Arc2D.PIE));

        // Draw the excess calorie progress (red part)
        if (excessCalories > 0) {
            g2d.setColor(Color.RED);
            g2d.fill(new Arc2D.Double(x, y, size, size, 90 - orangeAngle, -redAngle, Arc2D.PIE));
        }

        // Draw the inner circle
        int innerSize = (int) (size * 0.7);
        int innerX = (getWidth() - innerSize) / 2;
        int innerY = (getHeight() - innerSize) / 2;
        g2d.setColor(Color.WHITE);
        g2d.fillOval(innerX, innerY, innerSize, innerSize);

        // Text labels to do with calorie wheel below

        // Draw the calorie text
        String calorieText = String.format("Total Calories: %d/%d", currentCalories, maxCalories);
        FontMetrics metrics = g.getFontMetrics();
        int textX = (getWidth() - metrics.stringWidth(calorieText)) / 2;
        int textY = getHeight() / 2 + metrics.getAscent() / 2;
        g2d.setColor(Color.BLACK);
        g2d.drawString(calorieText, textX, textY);

        if(excessCalories > 0) {
            // Draw the warning label
            String warningText = "Calorie goal exceeded!";
            FontMetrics metricsExceeded = g.getFontMetrics();
            int warningTextX = (getWidth() - metricsExceeded.stringWidth(warningText)) / 2;
            int warningTextY = getHeight() / 2 + metricsExceeded.getAscent() + 20; // Positioned below the "Total Calories" text
            g2d.setColor(Color.RED);
            g2d.drawString(warningText, warningTextX, warningTextY);
        }
    }

    public void setCurrentCalories(int newCalories) {
        this.currentCalories = newCalories;
        //repaint();
    }
    public int getCurrentCalories() {
        return currentCalories;
    }
    public void setMaxCalories(int maxCalories) {
        this.maxCalories = maxCalories;
        repaint();
    }
}