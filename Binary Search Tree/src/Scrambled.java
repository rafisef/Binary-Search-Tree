/**
 * Created by Rafael on 4/18/2016.
 */

import java.util.Random;

class SBST<Value> {
    private class Node {
        String key;
        Value value;
        Node left;
        Node right;

        public Node(String key, Value value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private String[] keys;
    private Value[] values;
    private Node root;
    private Random rand;
    private int count=0;

    public SBST(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Can't have negative size");
        }
        else {
            keys = new String[size];
            values = (Value[]) new Object[size];
        }
    }

    private void flush() {
        // scrambling the arrays
        rand = new Random();
        String temp;
        Value temp2;
        for (int i = 0; i <= count - 2; i++) {
            int j = Math.abs(rand.nextInt()) % (count-i);
            temp = keys[i];
            temp2 = values[i];
            keys[i] = keys[i + j];
            keys[i+j] = temp;
            values[i] = values[i + j];
            values[i+j] = temp2;
        }
        //putting key-value pairs in to the arrays
        for(int i=0;i<count;i++){
            putting(keys[i],values[i]);
        }

        // restarting from the 0 index
        count = 0;
    }

    public Value get(String key) {
        Node temp = root;
        if (count>0) {
            flush();
        }
        while (temp != null) {
            int test = key.compareTo(temp.key);
            if (test < 0) {
                temp = temp.left;
            }
            else if (test > 0) {
                temp = temp.right;
            }
            else {
                return temp.value;
            }
        }
        throw new IllegalArgumentException("Key couldnt be found");
    }


    public void put(String key, Value value) {
        if(count >= keys.length){ // checking if arrays full
            flush();
        }
        keys[count] = key;
        values[count] = value;
        count++; // sets count to 1

    }

    private void putting(String key, Value value) {
        if (root == null) {
            root = new Node(key, value);
        }
        else {
            Node temp = root;
            while (temp != null) {
                int test = key.compareTo(temp.key);
                if (test < 0) {
                    if (temp.left == null) {
                        temp.left = new Node(key, value);
                        return;
                    } else {
                        temp = temp.left;
                    }
                }
                else if (test > 0) {
                    if (temp.right == null) {
                        temp.right = new Node(key, value);
                        return;
                    }
                    else {
                        temp = temp.right;
                    }
                }
                else {
                   throw new IllegalStateException("Key already exists");
                }
            }
        }
    }

    public int height(Node subtree) {
        Node temp = root;
        if (subtree == null) {
            return 0;
        }
        else {
            int l = height(subtree.left);
            int r = height(subtree.right);
            if (l > r) {
                return l + 1;
            } else {
                return r + 1;
            }

        }
    }

    public int height() {
        if (count>0) {
            flush();
        }
        return height(root);
    }

}


public class Scrambled {
    private final static String[] reserved =
            {"abstract", "assert", "boolean", "break",
                    "byte", "case", "catch", "char",
                    "class", "const", "continue", "default",
                    "do", "double", "else", "extends",
                    "final", "finally", "float", "for",
                    "goto", "if", "implements", "import",
                    "instanceof", "int", "interface", "long",
                    "native", "new", "package", "private",
                    "protected", "public", "return", "short",
                    "static", "super", "switch", "synchronized",
                    "this", "throw", "throws", "transient",
                    "try", "void", "volatile", "while"};

    public static void main(String[] args) {
        SBST<Integer> sbst = new SBST<Integer>(30);
        for (int index = 0; index < reserved.length; index += 1)
        {
            sbst.put(reserved[index], index);
        }
        System.out.println(sbst.height());
        for (int index = 0; index < reserved.length; index += 1)
        {
            System.out.format("%02d %s", sbst.get(reserved[index]), reserved[index]);
            System.out.println();
        }
    }
}

/*
FIRST RUN
11
00 abstract
01 assert
02 boolean
03 break
04 byte
05 case
06 catch
07 char
08 class
09 const
10 continue
11 default
12 do
13 double
14 else
15 extends
16 final
17 finally
18 float
19 for
20 goto
21 if
22 implements
23 import
24 instanceof
25 int
26 interface
27 long
28 native
29 new
30 package
31 private
32 protected
33 public
34 return
35 short
36 static
37 super
38 switch
39 synchronized
40 this
41 throw
42 throws
43 transient
44 try
45 void
46 volatile
47 while

SECOND RUN
10
00 abstract
01 assert
02 boolean
03 break
04 byte
05 case
06 catch
07 char
08 class
09 const
10 continue
11 default
12 do
13 double
14 else
15 extends
16 final
17 finally
18 float
19 for
20 goto
21 if
22 implements
23 import
24 instanceof
25 int
26 interface
27 long
28 native
29 new
30 package
31 private
32 protected
33 public
34 return
35 short
36 static
37 super
38 switch
39 synchronized
40 this
41 throw
42 throws
43 transient
44 try
45 void
46 volatile
47 while
 */
