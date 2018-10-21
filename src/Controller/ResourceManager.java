package Controller;

import GameEngine.GameObject;
import GameEngine.Map;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

import static Controller.Config.GRID_SIZE;

public class ResourceManager {
	/**
	 * field values
	 */

    /**
     * Key is filename without extension,
     * which will be of format classname-state
     * Value is the Image
     */
    private HashMap<String, Image> imgMap;

    private HashMap<String, Bounds> boundsMap;

    private Collection<String> allClasses;

	/**
	 * Constructor for ResourceManager class
	 */
	public ResourceManager() {
        imgMap = new HashMap<>();
        boundsMap = new HashMap<>();
        loadImages();
    }

	/**
	 * loadImages method
	 *
	 * loads images into directory/file
	 */
	private void loadImages(){
        // load all images
        File dir = new File(getClass().getClassLoader().getResource("GameObjects").getPath());
        File[] files = dir.listFiles();
        if(files != null) {
            try {
                for (File file : files) {
                    String filename = file.getName().split("[.]")[0];
                    imgMap.put(filename, new Image(new FileInputStream(file)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	/**
	 * getImage method
	 *
	 * returns a imageMap
	 * @param className
	 * @param state
	 * @return
	 */
    public Image getImage(String className, int state){
        if(imgMap.containsKey(className + "-" + state))
            return imgMap.get(className + "-" + state);
        // rollback to use default state img
        else if(imgMap.containsKey(className + "-0"))
            return imgMap.get(className + "-0");
        else
            return imgMap.get(className);
    }

    public Image getImage(String className){
        return imgMap.get(className);
    }

	/**
	 * createImageViewByClassName method
	 *
	 * creates a imageView through giving a class name
	 * @param cls
	 * @return
	 */
	public ImageView createImageViewByClassName(String cls){
        return new ImageView(getImage(cls));
    }

	/**
	 * createImageViewByGameObject method
	 *
	 * creates a imgaeView through passing in Gameobject
	 * @param obj
	 * @return
	 */
	public ImageView createImageViewByGameObject(GameObject obj){
        ImageView imageView = new ImageView(getImage(obj.getClassName(), obj.getState()));
        imageView.setId(Integer.toString(obj.getObjID()));
        imageView.setTranslateX(obj.getLocation().getX() * GRID_SIZE);
        imageView.setTranslateY(obj.getLocation().getY() * GRID_SIZE);
        return imageView;
    }

	/**
	 * createImageViewByGameObject method
	 *
	 * creates an ImageView through passing in GameObject, ObservableList
	 * @param obj
	 * @param mountPoint
	 * @return
	 */
    public ImageView createImageViewByGameObject(GameObject obj, ObservableList<Node> mountPoint){
        ImageView imageView = createImageViewByGameObject(obj);
        mountPoint.add(imageView);
        return imageView;
    }

	/**
	 * getAllClassNames method
	 *
	 * returns a collection of classnames
	 * @return
	 */
	public Collection<String> getAllClassNames(){
        if(allClasses == null)
            allClasses = imgMap.keySet().stream()
                .map(s -> s.contains("-") ? s.split("-")[1] : s)
                .filter(GameObject::isValidClass).collect(Collectors.toSet());
        return allClasses;
    }

	/**
	 * drawGridLine method
	 *
	 * pass in an observableList
	 * Draws gridlines for the map
	 * @param mountPoint
	 */
	public void drawGridLine(ObservableList<Node> mountPoint) {
        drawGridLine(mountPoint, Map.DEFAULT_DUNGEON_SIZE_X, Map.DEFAULT_DUNGEON_SIZE_Y);
    }

	/**
	 * drawGridLine method
	 *
	 * pass in observableList, size of the Map (X, Y)
	 * draws gridlines for the map
	 * @param mountPoint
	 * @param sizeX
	 * @param sizeY
	 */
    public void drawGridLine(ObservableList<Node> mountPoint, int sizeX, int sizeY){
        // vertical lines
        for(int i = 0; i < sizeX; i++){
            Line line = new Line();
            line.setStartX(i * GRID_SIZE);
            line.setEndX(i * GRID_SIZE);
            line.setStartY(0);
            line.setStartY(GRID_SIZE * sizeY);
            line.setStrokeWidth(1);
            line.setStroke(new Color(0, 0, 0, 0.5));
            mountPoint.add(line);
        }

        // horizontal lines
        for(int i = 0; i < sizeY; i++){
            Line line = new Line();
            line.setStartY(i * GRID_SIZE);
            line.setEndY(i * GRID_SIZE);
            line.setStartX(0);
            line.setStartX(GRID_SIZE * sizeX);
            line.setStrokeWidth(1);
            line.setStroke(new Color(0, 0, 0, 0.5));
            mountPoint.add(line);
        }
    }
}
