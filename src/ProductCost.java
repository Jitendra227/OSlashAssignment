import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

public class ProductCost {
    static double total_disc = 0;
    static double total_cost = 0;

    static void calculateTotal(int qty, int cost, int disc) {
        double summ = qty * cost;
        double prod_disc = 0;
        prod_disc = (disc * summ) / 100;

        total_disc += prod_disc;
        total_cost += summ;
        System.out.println("ITEM_ADDED");
    }

    public static void main(String[] args) {
        try {
            int disc = 0;
            int cost = 0;
            int qty = 0;

            HashMap<String, Integer> discountMap = new HashMap<String, Integer>();
            discountMap.put("TSHIRT", 10);
            discountMap.put("JACKET", 5);
            discountMap.put("CAP", 20);
            discountMap.put("NOTEBOOK", 20);
            discountMap.put("PENS", 10);
            discountMap.put("MARKERS", 5);

            HashMap<String, Integer> clothing = new HashMap<String, Integer>();
            clothing.put("TSHIRT", 1000);
            clothing.put("JACKET", 2000);
            clothing.put("CAP", 500);

            HashMap<String, Integer> stationary = new HashMap<String, Integer>();
            stationary.put("NOTEBOOK", 200);
            stationary.put("PENS", 300);
            stationary.put("MARKERS", 500);

            DecimalFormat df = new DecimalFormat("0.00");

            FileInputStream fis = new FileInputStream(args[0]);
            Scanner sc = new Scanner(fis);

            while (sc.hasNextLine()) {
                String inputCommand = sc.nextLine();
                String nameAndQty = inputCommand.substring(9);

                if (nameAndQty.length() < 2)
                    break;

                String[] data = nameAndQty.split("\\s+");
                for (String key : clothing.keySet()) {
                    if (key.equals(data[0])) {
                        qty = Integer.parseInt(data[1]);
                        if (qty > 2) {
                            System.out.println("ERROR_QUANTITY_EXCEEDED");
                            break;
                        }
                        cost = clothing.get(key);
                        disc = discountMap.get(key);
                        calculateTotal(qty, cost, disc);
                    }
                }
                for (String key : stationary.keySet()) {
                    if (key.equals(data[0])) {
                        qty = Integer.parseInt(data[1]);
                        if (qty > 3) {
                            System.out.println("ERROR_QUANTITY_EXCEEDED");
                            break;
                        }
                        cost = stationary.get(key);
                        disc = discountMap.get(key);
                        calculateTotal(qty, cost, disc);
                    }
                }
            }
            sc.close();

            if (total_cost < 1000) {
                total_disc = 0;
            }

            if (total_cost >= 1000) {
                total_cost -= total_disc;
            }

            if (total_cost >= 3000) {
                total_disc += (5 * total_cost) / 100;
                total_cost -= (5 * total_cost) / 100;
            }

            total_cost += (total_cost * 10) / 100;

            System.out.println("TOTAL_DISCOUNT " + df.format(total_disc) +
                    "\nTOTAL_AMOUNT_TO_PAY " + df.format(total_cost));

        } catch (IOException e) {
        }
    }
}
