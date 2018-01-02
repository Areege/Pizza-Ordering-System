/* 
 * Name: Areege Chaudhary
 * Student Number: 10197607 
 */

package application;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PizzaController {

    @FXML
    private RadioButton tripleHam;

    @FXML
    private RadioButton mediumSize;

    @FXML
    private RadioButton regularPepperoni;

    @FXML
    private RadioButton regularHam;

    @FXML
    private RadioButton noneHam;

    @FXML
    private RadioButton regularCheese;

    @FXML
    private RadioButton doubleCheese;

    @FXML
    private RadioButton largeSize;

    @FXML
    private RadioButton tripleCheese;

    @FXML
    private TextField quantity;
    
    @FXML
    private TextField singleCost;
    
    @FXML
    private TextField errorBox;
    
    @FXML
    private TextField totalPizzaCost;
    
    @FXML
    private TextArea orderList;

    @FXML
    private TextField totalOrderCost;
    
    @FXML
    private RadioButton doubleHam;

    @FXML
    private RadioButton smallSize;

    @FXML
    private RadioButton nonePepperoni;

    @FXML
    private RadioButton doublePepperoni;

    @FXML
    private RadioButton triplePepperoni;
    
    private static ArrayList<LineItem> orders = new ArrayList<>();
	private String pSize = "null";
    private int pCheese = 1;
    private int pPepperoni = 1;
    private int pHam = -1; 
    private int pQuantity = -1;
    Pizza pizza = null; 

    @FXML
    void sizeSelection(ActionEvent event) { 
    	if (smallSize.isSelected())
    		pSize = "small";
    	if (mediumSize.isSelected())
    		pSize = "medium";
    	if (largeSize.isSelected())
    		pSize = "large";
    }//end sizeSelection
    	
    @FXML
    void cheeseSelection(ActionEvent event) {
    	if (regularCheese.isSelected()) 
    		pCheese = 1;
    	if (doubleCheese.isSelected())
    		pCheese = 2;
    	if (tripleCheese.isSelected())
    		pCheese = 3;
    }//end cheeseSelection
    
    @FXML
    void hamSelection(ActionEvent event) {
    	if (noneHam.isSelected())
    		pHam = 0;
    	if (regularHam.isSelected())
    		pHam = 1;
    	if (doubleHam.isSelected())
    		pHam = 2;
    	if (tripleHam.isSelected())
    		pHam = 3;  
    }//end hamSelection
    
    @FXML
    void pepperoniSelection(ActionEvent event) {
    	if (nonePepperoni.isSelected())
    		pPepperoni = 0;
    	else if (regularPepperoni.isSelected())
    		pPepperoni = 1;
    	else if (doublePepperoni.isSelected())
    		pPepperoni = 2;
    	else if (triplePepperoni.isSelected())
    		pPepperoni = 3;
    }//end pepperoniSelection
    
    @FXML
    void quantitySelection(ActionEvent event) {
    	try {
    		String pizzaQuantity = quantity.getText();
    		//change string to integer
    		pQuantity = Integer.valueOf(pizzaQuantity);
    	} catch (NumberFormatException e) {
    		errorBox.setText("Please enter a valid quantity value between 1 and 100!");
    	} //end catch
    }//end quantitySelection

    void checkQuantity(int quantity) throws IllegalPizza {
    	if (quantity == -1) 
    		throw new IllegalPizza("Make sure you press the enter key after typing in a quantity value!");
    	if ((quantity < 1) || (quantity > 100)) 
    		throw new IllegalPizza("Illegal Pizza Quantity!");
    }//end checkQuantity
    
    @FXML
    boolean checkPriceClicked (ActionEvent event) { 
    	checkPrice();
    	return true;
    }//end checkPriceClicked
    
    boolean checkPrice() {
    	float singlePizzaCost;
    	float totalCost;
    	String showSC; //show single cost
    	String showTPC; //show total pizza cost
    	
    	try {
    		//create new pizza object
			pizza = new Pizza(pSize, pCheese, pHam, pPepperoni);
			checkQuantity(pQuantity);
		} catch (IllegalPizza ip) {
			//display error in error box
			errorBox.setText(ip.getMessage());
			return false;
		}//end catch	
    	//reset error box text
    	errorBox.setText("");
    	singlePizzaCost = pizza.getCost();
    	//show single pizza cost in GUI
    	showSC = "$" + String.format("%.2f", singlePizzaCost);
    	singleCost.setText(showSC);
    	//show total pizza(s) cost in GUI
    	totalCost = (singlePizzaCost * pQuantity);
    	showTPC = "$" + String.format("%.2f", totalCost);
    	totalPizzaCost.setText(showTPC);	
    	return true;
    }//end checkPrice
    
    @FXML
    boolean addBtnClicked (ActionEvent event) { 
    	float totalCost = 0;
    	int line = 1;
    	
    	checkPrice();
    	if (checkPrice()) {
    	//add a new LineItem
    	try {
			orders.add(new LineItem(pQuantity, pizza));
		} catch (IllegalPizza ip) {
			errorBox.setText(ip.getMessage());
			return false;
		} //end catch
    	orderList.clear();
		for (LineItem order : orders) {
			totalCost += order.getCost();
			orderList.replaceSelection("\n" + line + "\t" + order + " each.");
			line++;
		}//end for loop
		//show total order cost
		String showTC = "$" + String.format("%.2f", totalCost);
		totalOrderCost.setText(showTC);
		return true;
    	}//end if statement
    	else {
    		return false;
    	}//end else
    }//end checkPriceClicked

}//end PizzaController
