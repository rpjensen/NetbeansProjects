
package imagemanip;

/**
 * An image manipulation class that can threshold an image.
 * @author Ryan Jensen
 * @version Feb 24, 2014 Exam 1
 */
public class ImageManip {
        private final double upperThresholdValue = 1.0;
        private final double lowerThresholdValue = 0.0;
        private double[][] originalImage;
        private double[][] newImage;
        
    public ImageManip(double[][] initArray){
        originalImage = initArray;
        newImage = new double[initArray.length][initArray[0].length];
    }
    
    private double findAverage(){
        int total = 0;
        for (int i = 0; i < originalImage.length; i++){
            for (int j = 0; j < originalImage[i].length; j++){
                total += originalImage[i][j];
            }
        }
        return total / (originalImage.length * originalImage[0].length);//total divided by # of items
    }
    
/**
 * Sets the new image after applying the threshold to the original image.
 * @param threshold the threshold to be applied
 */
    public void findFigure(double threshold){
        double average = this.findAverage();
        double finalThreshold = threshold * average;
        for (int i = 0; i < originalImage.length; i++){
            for (int j = 0; j < originalImage[i].length; j++){
                if (originalImage[i][j] < finalThreshold){
                    newImage[i][j] = lowerThresholdValue;
                }else {
                    newImage[i][j] = upperThresholdValue;
                }
            }
        }
    }
    
/**
 * Creates a string representation of the original image.
 * @return the string representation of the original image
 */
    public String originalImage(){
        StringBuilder returnString = new StringBuilder();
        returnString.append("\n{");
        for (int i = 0; i < originalImage.length; i++){
            if (i == 0){
                returnString.append("{");
            }else {
                returnString.append(", \n {");
            }
            for (int j = 0; j < originalImage[i].length; j++){
                if (j == 0){
                    returnString.append(originalImage[i][j]);
                }else {
                    returnString.append(",\t");
                    returnString.append(originalImage[i][j]);
                }
            }
            returnString.append("}");
        }
        returnString.append("}");
        return returnString.toString();
    }
    
/**
 * Creates a string representation of the new image found after applying the threshold.
 * @return the string representation of the new image
 */
    public String newImage(){
        StringBuilder returnString = new StringBuilder();
        returnString.append("\n{");
        for (int i = 0; i < newImage.length; i++){
            if (i == 0){
                returnString.append("{");
            }else {
                returnString.append(", \n {");
            }
            for (int j = 0; j < newImage[i].length; j++){
                if (j == 0){
                    returnString.append(newImage[i][j]);
                }else {
                    returnString.append(",\t");
                    returnString.append(newImage[i][j]);
                }
            }
            returnString.append("}");
        }
        returnString.append("}");
        return returnString.toString();
    }
    
/**
 * This is a main method to test the ImageManip class.
 * @param args the command line arguments (no effect)
 */
    public static void main(String[] args) {
        double[][] data = {{1.2, 1.3, 4.5, 6.0},
                            {1.7, 3.3, 4.4, 10.5}, 
                            {1.1, 4.5, 2.1, 25.3},
                            {1.0, 9.5, 8.3, 2.9}};
        ImageManip image = new ImageManip(data);
        System.out.println("The original image array is " + image.originalImage());
        image.findFigure(1.4);
        System.out.println("The image array with a threshold of 1.4 is " + image.newImage());
        image.findFigure(0.6);
        System.out.println("The image array with a threshold of 0.6 is " + image.newImage());
    }
    
}
