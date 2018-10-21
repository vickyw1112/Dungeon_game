package Controller;

import GameEngine.GameObject;
import GameEngine.Map;
import GameEngine.WinningCondition.WinningCondition;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
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
    private HashMap<String, Image> objImgMap;

    /**
     * Img map for others
     */
    private HashMap<String, Image> winningConditionImgMap;

    private HashMap<String, Bounds> boundsMap;

    private Collection<String> allClasses;

	/**
	 * Constructor for ResourceManager class
	 */
    public ResourceManager() {
        objImgMap = new HashMap<>();
        boundsMap = new HashMap<>();
        winningConditionImgMap = new HashMap<>();
        loadObjectImages();
        loadWinningConditionImages();
    }

    private void loadWinningConditionImages() {
        // load all images
        File dir = new File(getClass().getClassLoader().getResource("WinningConditions").getPath());
        applyAllFilesUnderDir(dir, file -> {
            String classname = file.getName().split("[.]")[0];
            Image image = new Image(new FileInputStream(file));
            winningConditionImgMap.put(classname, image);
        });
    }

	/**
	 * loads images into directory/file
	 */
    private void loadObjectImages(){
        // load all images
        File dir = new File(getClass().getClassLoader().getResource("GameObjects").getPath());
        applyAllFilesUnderDir(dir, file -> {
            String filename = file.getName().split("[.]")[0];
            objImgMap.put(filename, new Image(new FileInputStream(file)));
        });
    }

    private void applyAllFilesUnderDir(File dir, ConsumerWithException<File> action){
        File[] files = dir.listFiles();
        if(files != null) {
            try {
                for (File file : files) {
                    action.accept(file);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

	/**
	 * Checks and allows setting of various winning conditions
	 * @return
	 */
	public Collection<WinningCondition> getAllWinningConditions(){
        return winningConditionImgMap.keySet().stream().map(classname -> {
            try {
                return (WinningCondition) Class.forName(WinningCondition.class.getPackage().getName() + "." + classname)
                        .getConstructor().newInstance();
            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toSet());
    }

	/**
	 * creates images based on winning conditions
	 * @param condition
	 * @return
	 */
	public ImageView createImageViewByWinningCondition(WinningCondition condition){
        ImageView imageView = new ImageView(winningConditionImgMap.get(condition.getClass().getSimpleName()));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(25);
        return imageView;
    }

	/**
	 * returns a imageMap
	 * @param className
	 * @param state
	 * @return
	 */
    public Image getImage(String className, int state){
        if(objImgMap.containsKey(className + "-" + state))
            return objImgMap.get(className + "-" + state);
        // rollback to use default state img
        else if(objImgMap.containsKey(className + "-0"))
            return objImgMap.get(className + "-0");
        else
            return objImgMap.get(className);
    }

	/**
	 * returns Image
	 * @param className
	 * @return
	 */
	public Image getImage(String className){
        return objImgMap.get(className);
    }

	/**
	 * creates a imageView through giving a class name
	 * @param cls
	 * @return
	 */
	public ImageView createImageViewByClassName(String cls){
        return new ImageView(getImage(cls));
    }

	/**
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
	 * getAllGameObjectClassNames method
	 *
	 *
	 * @return
	 */
    public Collection<String> getAllGameObjectClassNames(){
        if(allClasses == null)
            allClasses = objImgMap.keySet().stream()
                    .map(s -> s.contains("-") ? s.split("-")[1] : s)
                    .filter(GameObject::isValidClass).collect(Collectors.toSet());
        return allClasses;

    }

	/**
	 * pass in an observableList
	 * Draws gridlines for the map
	 * @param mountPoint
	 */
	public void drawGridLine(ObservableList<Node> mountPoint) {
        drawGridLine(mountPoint, Map.DEFAULT_DUNGEON_SIZE_X, Map.DEFAULT_DUNGEON_SIZE_Y);
    }

	/**
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

interface ConsumerWithException<T> {
    public void accept(T t) throws Exception;
}


