import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MySearchTree<T extends Comparable<T>> {

    public class BSTNode<T extends Comparable<T>> {
        public BSTNode<T> left;
        public BSTNode<T> right;
        public T data;

        public BSTNode(){}
        public BSTNode(T newData){
            data = newData;
        }
        public BSTNode(T newData, BSTNode<T> leftChild, BSTNode<T> rightChild){
            data = newData;
            left = leftChild;
            right = rightChild;
        }

        public T getData(){
            return data;
        }
        public BSTNode<T> getLeft(){
            return left;
        }
        public BSTNode<T> getRight(){
            return right;
        }

        public void setData(T newData){
            data = newData;
        }
        public void setLeft(BSTNode<T> newLeft){
            left = newLeft;
        }
        public void setRight(BSTNode<T> newRight){
            right = newRight;
        }
    }


    private BSTNode<T> root;

    public MySearchTree(){}

    public void add(T data){
        if(root == null)
            root = new BSTNode(data);   //If empty, make it the root
        else
            add(root, data);    //Call other add function
    }
    private void add(BSTNode node, T data){
        if(data.compareTo((T) node.data) < 0) { //If less than root
            if (node.left == null)
                node.left = new BSTNode(data);  //If left child doesn't exist it becomes left child
            else
                add(node.left, data);   //Call again with just left half
        }
        else{   //Same thing but with the right side
            if(node.right == null)
                node.right = new BSTNode(data);
            else
                add(node.right, data);
        }
    }

    public boolean find(T data){
        if(root == null)
            return false;   //If empty, it's not in there
        if(root.data == data)
            return true;
        else
            return find(root, data);    //run the other find function through the tree
    }
    private boolean find(BSTNode node, T data){
        if(node.data == null || (node.right == null && node.left == null))   //When you reach the end of the list
            return false;
        if(data == node.data)
            return true;
        if(data.compareTo((T) node.data) < 0)   //Recursive call with the left side
            return find(node.left, data);
        if(data.compareTo((T) node.data) > 0)   //Recursive call with the left side
            return find(node.right, data);

        return false;
    }

    public int leafCount(){
        return leafCount(root);
    }
    private int leafCount(BSTNode<T> root){
        if(root == null)    //If tree is empty
            return 0;
        else if(root.left == null && root.right == null)    //If both null then that is a leaf
            return 1;
        else
            return leafCount(root.left) + leafCount(root.right);    //Recursively iterate through every node in list
    }

    public int parentCount(){
       return parentCount(root);
    }
    private int parentCount(BSTNode<T> node)
    {
        if(node == null || (node.left == null && node.right == null))   //If empty or has no children, not a parent so return 0
            return 0;
        else {
            if (node.left == null && node.right != null)
                return 1 + parentCount(node.right);
            else if (node.right == null && node.left != null)
                return 1 + parentCount(node.left);
            else
                return 1 + parentCount(node.left) + parentCount(node.right);    //return 1 + all the parents beneath it
        }
    }


    public int height(){
        return height(root);
    }
    private int height(BSTNode<T> node){
        int leftHeight = 0, rightHeight = 0, height = 0;
        if(node == null)
            return 0;
        leftHeight = height(node.left);     //find the max height on the left side of the tree
        rightHeight = height(node.right);   //find the max height on the right side of the tree

        if(leftHeight > rightHeight)
            height = 1 + leftHeight;
        else
            height = 1 + rightHeight;
        //take the highest height of the side and add 1 to it(since you need to account for the node itself

        return height;
    }

    public boolean isPerfect(){
        return isPerfect(root);
    }
    private boolean isPerfect(BSTNode<T> node){
        if(root == null)    //empty trees are perfect
            return true;
        if(node.left == null && node.right == null) //checking if it's a leaf
            return(height(node.left) == height(node.right));    //check if heights of children are the same
        if(node.left == null || node.right == null) //see if one is null and one is not
            return false;   //if only one child has data, then it is not perfect

        return isPerfect(node.left) && isPerfect(node.right);    //check for all subtrees
    }

    public ArrayList<T> ancestors(T data){
        ArrayList<T> ancestorList = new ArrayList<T>(); //holding the ancestor values from findAncestors
        findAncestors(root, data, ancestorList);
        return ancestorList;
    }
    private void findAncestors(BSTNode<T> node, T data, ArrayList<T> ancestorList){
        if(node.data == null)
            return;
        if(node.data == data)
            return;

        //functions as the find function from before, but instead of returning true/false, it saves the node values it passes through getting there
        if(data.compareTo(node.data) < 0){
            ancestorList.add(node.data);
            findAncestors(node.left, data, ancestorList);
        }
        if(data.compareTo(node.data) > 0){
            ancestorList.add(node.data);
            findAncestors(node.right, data, ancestorList);
        }
        return;
    }


    public void inOrderPrint(){
        inOrderPrint(root);
    }
    private void inOrderPrint(BSTNode<T> node){
        if (node.left!=null) inOrderPrint(node.left);
        System.out.print(node.data + " ");
        if (node.right!=null) inOrderPrint(node.right);
    }

    public void preOrderPrint(){
        preOrderPrint(root);
    }
    private void preOrderPrint(BSTNode<T> node){
        System.out.print(node.data + " ");
        if (node.left!=null) preOrderPrint(node.left);
        if (node.right!=null) preOrderPrint(node.right);
    }



    public static void main(String[] args){
        MySearchTree<Integer> list = new MySearchTree<>();

        list.add(5);
        list.add(3);
        list.add(7);
        list.add(1);
        list.add(4);
        list.add(6);
        list.add(9);

        /*
                              5
                           /    \
                          3      7
                         / \    / \
                        1   4  6   9
         */

        System.out.print("Preorder Traversal: ");
        list.preOrderPrint();
        System.out.println("\n");

        System.out.print("Inorder Traversal: ");
        list.inOrderPrint();
        System.out.println("\n");

        if(list.find(7))
            System.out.println("7 is in the tree.\n");
        else
            System.out.println("7 is not in the tree.\n");

        System.out.println("The number of leaves in this tree is: " + list.leafCount() + "\n");

        System.out.println("The number of parents in this tree is: " + list.parentCount() + "\n");

        System.out.println("The height of the tree is: " + list.height() + "\n");

        if(list.isPerfect())
            System.out.println("The tree is perfect.\n");
        else
            System.out.println("The tree is not perfect\n");

        list.add(12);

        if(list.isPerfect())
            System.out.println("After adding 12, the tree is perfect.\n");
        else
            System.out.println("After adding 12, the tree is not perfect\n");

        System.out.print("The ancestors of the passed value 12 is: ");

        ArrayList ancestorList = list.ancestors(12);
        for(Object x : ancestorList)
            System.out.print(x + " ");

        System.out.println("Now another example, but with strings: \n---------------------------------" +
                        "----------------------------------------------------------------");

        MySearchTree<String> listWithNames = new MySearchTree<>();

        listWithNames.add("Steven");
        listWithNames.add("Lily");
        listWithNames.add("Chris");
        listWithNames.add("Mike");
        listWithNames.add("Tyler");
        listWithNames.add("Tracy");

        /*
                              Steven
                              /     \
                           Lily     Tyler
                           /  \      /
                      Chris  Mike  Tracy
         */

        System.out.print("Preorder Traversal: ");
        listWithNames.preOrderPrint();
        System.out.println("\n");

        System.out.print("Inorder Traversal: ");
        listWithNames.inOrderPrint();
        System.out.println("\n");

        if(listWithNames.find("Joshua"))
            System.out.println("Joshua is in the tree.\n");
        else
            System.out.println("Joshua is not in the tree.\n");

        System.out.println("The number of leaves in this tree is: " + listWithNames.leafCount() + "\n");

        System.out.println("The number of parents in this tree is: " + listWithNames.parentCount() + "\n");

        System.out.println("The height of the tree is: " + listWithNames.height() + "\n");

        if(listWithNames.isPerfect())
            System.out.println("The tree is perfect.\n");
        else
            System.out.println("The tree is not perfect\n");

        listWithNames.add("Wyatt");

        if(listWithNames.isPerfect())
            System.out.println("After adding Wyatt, the tree is perfect.\n");
        else
            System.out.println("After adding Wyatt, the tree is not perfect\n");

        System.out.print("The ancestors of the passed value Wyatt is: ");

        ArrayList ancestorListWithNames = listWithNames.ancestors("Chris");
        for(Object x : ancestorListWithNames)
            System.out.print(x + " ");

        System.out.println();
    }
}
