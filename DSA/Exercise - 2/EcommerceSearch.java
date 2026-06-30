class Product {

    int productId;
    String productName;
    String category;

    Product(int productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    public void display() {
        System.out.println("Product ID : " + productId);
        System.out.println("Product Name : " + productName);
        System.out.println("Category : " + category);
    }
}

public class EcommerceSearch {

    public static Product linearSearch(Product products[], int id) {

        for(Product p : products) {

            if(p.productId == id)

                return p;
        }

        return null;
    }

    public static Product binarySearch(Product products[], int id) {

        int low = 0;
        int high = products.length - 1;

        while(low <= high) {

            int mid = (low + high) / 2;

            if(products[mid].productId == id)

                return products[mid];

            else if(id < products[mid].productId)

                high = mid - 1;

            else

                low = mid + 1;
        }

        return null;
    }

    public static void main(String[] args) {

        Product products[] = {

            new Product(101,"Laptop","Electronics"),
            new Product(102,"Mobile","Electronics"),
            new Product(103,"Shoes","Fashion"),
            new Product(104,"Watch","Accessories"),
            new Product(105,"Camera","Electronics")

        };

        System.out.println("Linear Search");

        Product result1 = linearSearch(products,104);

        if(result1 != null)

            result1.display();

        else

            System.out.println("Product Not Found");

        System.out.println();

        System.out.println("Binary Search");

        Product result2 = binarySearch(products,104);

        if(result2 != null)

            result2.display();

        else

            System.out.println("Product Not Found");

    }
}