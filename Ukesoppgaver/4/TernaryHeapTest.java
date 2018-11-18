/** Class TernaryHeapTest **/
public class TernaryHeapTest {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Ternary Heap Test\n\n");
        System.out.println("Enter size and no of nodes each child has");
        /** Make object of TernaryHeapHeap **/
        TernaryHeap th = new TernaryHeap(scan.nextInt(), scan.nextInt() );
 
        char ch;
        /**  Perform Ternary Heap operations **/
        do {
            System.out.println("\nTernary Heap Operations\n");
            System.out.println("1. insert ");
            System.out.println("2. delete");
            System.out.println("3. check full");            
            System.out.println("4. check empty");
            System.out.println("5. clear");
 
            boolean chk;       
            int choice = scan.nextInt();            
            switch (choice) {
            case 1 : 
                try
                {
                    System.out.println("Enter integer element to insert");
                    th.insert( scan.nextInt() ); 
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage() );
                }    
                break;                          
            case 2 : 
                try {
                    System.out.println("Enter delete position");
                    th.delete(scan.nextInt() - 1); 
                }
                catch (Exception e) {
                    System.out.println(e.getMessage() );
                }                 
                break;                         
            case 3 : 
                System.out.println("Full status = "+ th.isFull());
                break;                                   
            case 4 : 
                System.out.println("Empty status = "+ th.isEmpty());
                break; 
            case 5 : 
                th.clear(); 
                System.out.println("Heap Cleared\n");
                break;         
            default : 
                System.out.println("Wrong Entry \n ");
                break;   
            } 
            /** Display heap **/
            th.printHeap();  
 
            System.out.println("\nDo you want to continue (Type y or n) \n");
            ch = scan.next().charAt(0);                        
        } while (ch == 'Y'|| ch == 'y');  
    }
}
