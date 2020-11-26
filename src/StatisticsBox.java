import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import rainfall.Record;
import javafx.geometry.Point2D;

/**
 * The StatisticsBox class represents a statistics box containing
 * the information of a single record.
 *
 * @author Owen Herbert
 */
public class StatisticsBox {
    private static final int STAT_BOX_WIDTH = 130; // width in pixels of the statistics box
    private static final int STAT_BOX_HEIGHT = 60; // height in pixels of the statistics box
    private static final int MOUSE_MARGIN_LEFT = 10; // pixels between mouse origin and statistics box

    private final Pane chartPane;
    private final Record record;
    private Rectangle backgroundRectangle;
    private Rectangle monthTextRectangle;
    private Text monthText;
    private Text totalRainfallText;
    private Text minRainfallText;
    private Text maxRainfallText;

    /**
     * Sets the required chartPane node and record object to use
     * in the statistics box upon construction.
     *
     * @param chartPane the chart pane
     * @param record the data record object
     */
    public StatisticsBox(Pane chartPane, Record record) {

        this.chartPane = chartPane;
        this.record = record;
    }

    /**
     * Adds statistics box components to the chartPane.
     *
     * @param x the x co-ordinate of the mouse
     * @param y the y co-ordinate of the mouse
     */
    public void show(double x, double y) {

        Point2D topLeftPos = new Point2D(x + MOUSE_MARGIN_LEFT, y);

        // background rectangle
        backgroundRectangle = new Rectangle(topLeftPos.getX(), topLeftPos.getY(), STAT_BOX_WIDTH, STAT_BOX_HEIGHT);
        backgroundRectangle.setFill(Color.BLACK);
        backgroundRectangle.setStroke(Color.TOMATO);
        backgroundRectangle.setOpacity(.75);

        // month text
        monthText = new Text(topLeftPos.getX() + 8, topLeftPos.getY() + 16,
                record.getHumanMonth() + ", " + record.getYear());
        monthText.setFill(Color.WHITE);
        monthText.setFont(RainfallVisualiser.FONT_INTERFACE_NORMAL);

        // month text background rectangle
        monthTextRectangle = new Rectangle(topLeftPos.getX() + 8, topLeftPos.getY() + 7,
                monthText.getLayoutBounds().getWidth(), monthText.getLayoutBounds().getHeight());
        monthTextRectangle.setFill(Color.WHITE);
        monthTextRectangle.setOpacity(.40);

        // total rainfall text
        totalRainfallText = new Text(topLeftPos.getX() + 8, topLeftPos.getY() + 27,
                "Rainfall: " + String.valueOf(Math.floor(record.getRainfallTotal())));
        totalRainfallText.setFill(Color.WHITE);
        totalRainfallText.setFont(RainfallVisualiser.FONT_INTERFACE_NORMAL);

        // minimum rainfall text
        minRainfallText = new Text(topLeftPos.getX() + 8, topLeftPos.getY() + 38,
                "Rainfall min: " + record.getRainfallMin());
        minRainfallText.setFill(Color.WHITE);
        minRainfallText.setFont(RainfallVisualiser.FONT_INTERFACE_NORMAL);

        // maximum rainfall text
        maxRainfallText = new Text(topLeftPos.getX() + 8, topLeftPos.getY() + 49,
                "Rainfall max: " + record.getRainfallMax());
        maxRainfallText.setFill(Color.WHITE);
        maxRainfallText.setFont(RainfallVisualiser.FONT_INTERFACE_NORMAL);

        // add components to interface
        chartPane.getChildren().addAll(backgroundRectangle, monthTextRectangle, monthText, totalRainfallText,
                minRainfallText, maxRainfallText);
    }

    /**
     * hides the statistics box
     */
    public void hide() {

        // remove components from interface
        chartPane.getChildren().removeAll(backgroundRectangle, monthTextRectangle, monthText, totalRainfallText,
                minRainfallText, maxRainfallText);

    }
}
